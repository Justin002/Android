package com.beyondsoft.ep2p.activity.home;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.LoginActivity;
import com.beyondsoft.ep2p.activity.home.adapter.ExperienceListAdapter;
import com.beyondsoft.ep2p.activity.mine.activity.ForgetPasswordActivity;
import com.beyondsoft.ep2p.activity.mine.activity.ValidateLoginPasswordActivity;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.model.response.ExperienceInvestResponse;
import com.beyondsoft.ep2p.model.response.ExperienceListResponse;
import com.beyondsoft.ep2p.model.response.PayValidateErrorResponse;
import com.beyondsoft.ep2p.model.response.ValidatePayPwdResponse;
import com.beyondsoft.ep2p.model.response.ExperienceListResponse.ExperienceListItem;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.Logs;
import com.beyondsoft.ep2p.utils.StringRequest2;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.beyondsoft.ep2p.widget.CustomDialog;
import com.beyondsoft.ep2p.widget.PayHintDialog;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog.ButtonOnClickListener;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog.PayPasswrodClickListener;
import com.google.gson.reflect.TypeToken;


/**
 * 
 * 体验金
 * @ClassName:ExperienceActivity 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2015年12月16日
 *
 */
public class ExperienceActivity extends BaseActivity implements OnClickListener
{
    private Context mContext;
    private TextView tv_title;
    private ImageView iv_right_falg;

    private ListView listview_experience;
    private ExperienceListAdapter mExperienceListAdapter;
    private List<ExperienceListItem> mList = new ArrayList<ExperienceListItem>();

    private Button bn_sy_ok;
    private LinearLayout ll_null_data_ui;//空数据的UI
    private LinearLayout ll_wifi_off_ui;//无wifi的UI
    private TextView wifi_load_again;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience);

        mContext = ExperienceActivity.this;
        initTitle();
        initView();
        initData();
    }

    private void initTitle()
    {
        tv_title = (TextView) findViewById(R.id.title_content_tv);
        tv_title.setText("使用体验金");
        iv_right_falg = (ImageView) findViewById(R.id.title_right);
        iv_right_falg.setVisibility(View.GONE);
    }

    private void initView()
    {
        ll_null_data_ui =  (LinearLayout) findViewById(R.id.ll_null_data_ui);
        ll_null_data_ui.setVisibility(View.GONE);
        ll_wifi_off_ui =  (LinearLayout) findViewById(R.id.ll_wifi_off_ui); 
        ll_wifi_off_ui.setVisibility(View.GONE);
        wifi_load_again =  (TextView) findViewById(R.id.wifi_load_again); 
        wifi_load_again.setOnClickListener(this);
        
        listview_experience = (ListView) findViewById(R.id.listview_experience);
        bn_sy_ok = (Button) findViewById(R.id.bn_sy_ok);
        bn_sy_ok.setOnClickListener(this);
    }

    private String mpid = "";
    private String mToken = "";

    private void initData()
    {
        if (getIntent() != null)
        {
            mpid = getIntent().getStringExtra("pid");
            Logs.d("pid=" + mpid);
        }
        mToken = CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN);
        if (!TextUtils.isEmpty(mToken))
        {
            getUseExperienceData();
        }
        else
        {
            //跳转登录
            pushActivityForResult(new Intent(mContext, LoginActivity.class), 30);
            return;
        }
    }

    //获取体验金数据接口
    private void getUseExperienceData()
    {
        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("token", mToken);//用户标识
        params.put("borrowId", mpid);//借款Id
        connection(registerService.getStringRequest(1, URL.API_USE_EXPERIENCE, params,
            this));
    }

    private String[] mepces = null;
//    private List<String> strList = new ArrayList<String>();
    private boolean isGetInputCount = true;

    //体验金加入成功接口
    private void getUseExperienceInvestData(String pKey)
    {
        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));//用户标识
        params.put("epces", mepces);//选中的体验金卷String[]
        params.put("borrowId", mpid);//借款Id
//        params.put("tradePwd", pay_pass);//交易密码
        params.put("tranPKey", pKey);
        connection(registerService.getStringRequest(3, URL.API_EXPERIENCE_INVEST, params,
            this));
    }

    @Override
    public void onResponse(int tag, Object values)
    {
        super.onResponse(tag, values);
        RefreshDialog.stopProgressDialog();
        switch (tag)
        {
            case 1 :
                ExperienceListResponse mExperienceListResponse = (ExperienceListResponse) StringRequest2
                        .Json2Object(values.toString(),
                            new TypeToken<ExperienceListResponse>()
                            {
                            });
                
                if(mExperienceListResponse!= null)
                {
                    mList = mExperienceListResponse.result.experiences;

                    if (mList.size() < 0 || mList.isEmpty())
                    {
                        listview_experience.setVisibility(View.GONE);
//                        CommonUtils.toastMsgShort(mContext, "暂无数据!");
                        ll_null_data_ui.setVisibility(View.VISIBLE);
                        ll_wifi_off_ui.setVisibility(View.GONE);
                        return;
                    }
                    ll_null_data_ui.setVisibility(View.GONE);
                    ll_wifi_off_ui.setVisibility(View.GONE);
                    listview_experience.setVisibility(View.VISIBLE);
                    mExperienceListAdapter = new ExperienceListAdapter(mContext, mList);
                    listview_experience.setAdapter(mExperienceListAdapter);

                    mExperienceListAdapter
                            .setOnClickListener(new ExperienceListAdapter.OnClickListener()
                            {

                                @Override
                                public void OnClick(int flag,List<String> strList) //使用体验劵
                                {
//                                    String pid = mList.get(flag).pid;
//                                    strList.add(pid);
                                    if (strList.size() > 0)
                                    {
                                        mepces = (String[]) strList
                                                .toArray(new String[strList.size()]);
                                        Logs.d("选中的体验金卷String[]=:" + mepces);
                                    }else{
                                        mepces = null;
                                    }
                                }
                            });
                }
              
                break;
            case 2 : //验证原始交易密码
                ValidatePayPwdResponse validatePayPwdResponse = gson.fromJson(
                    values.toString(), ValidatePayPwdResponse.class);
                if (validatePayPwdResponse != null)
                {
                    getUseExperienceInvestData(validatePayPwdResponse.getResult().getKey());
                }
                break;
            case 3 ://加入成功回调方法
                ExperienceInvestResponse mExperienceInvestResponse = (ExperienceInvestResponse) StringRequest2
                        .Json2Object(values.toString(),
                            new TypeToken<ExperienceInvestResponse>()
                            {
                            });

                Intent intent = new Intent(mContext, InvestSucceedActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("Bean_mExperienceInvestResponse",
                    mExperienceInvestResponse);
                intent.putExtras(mBundle);
                intent.putExtra("result_finish",
                    mExperienceInvestResponse.result.investExperienceResult);
                intent.putExtra(Constants.EP2P, 4);
                pushActivity(intent);
                break;
            default :
                break;
        }
    }

    @Override
    public void onErrorResponse(int tag, String error)
    {
        super.onErrorResponse(tag, error);
        RefreshDialog.stopProgressDialog();

        switch (tag)
        {
            case 1 :
                ll_wifi_off_ui.setVisibility(View.VISIBLE);
                listview_experience.setVisibility(View.GONE);
                ll_null_data_ui.setVisibility(View.GONE);
                CommonUtils.toastMsgShort(mContext, error);
                break;
            case 2 :
                PayValidateErrorResponse response = gson.fromJson(error,
                    PayValidateErrorResponse.class);
                showErrorDialog(response);
                break;
            case 3 :
                CommonUtils.toastMsgShort(mContext, error);
                break;
            default :
                break;
        }
    }

    private String pay_pass = "";
    private PayPasswrodDialog passwrodDialog;

    @Override
    public void onClick(View arg0)
    {
        switch (arg0.getId())
        {
            case R.id.bn_sy_ok :
                if (mepces != null && mepces.length > 0)
                {
                    //是否设置交易密码【/** 是否设置交易密码 1 已设置 2 未设置 **/】
                    if (UserPersonalInfo.getIsSetTradePwd()!=null&&!UserPersonalInfo.getIsSetTradePwd().equals("1"))
                    {
                        CustomDialog clearDialog = new CustomDialog(mContext);
                        clearDialog
                                .setButtonClickListener(new CustomDialog.ButtonOnClickListener()
                                {

                                    @Override
                                    public void onButton2Click(View v)
                                    {
//                                        CommonUtils.toastMsgShort(mContext, "取消");
                                    }

                                    @Override
                                    public void onButton1Click(View v)
                                    {
//                                        CommonUtils.toastMsgShort(mContext, "去设置");
                                        Intent intent = new Intent(mContext,
                                            ValidateLoginPasswordActivity.class);
                                        intent.putExtra("setType", 2);
                                        pushActivity(intent);
                                    }
                                });
                        clearDialog.show();
                        clearDialog.setTitle(getResources().getString(
                            R.string.txt_setting_transaction_hint1));
                        clearDialog.setDescri(getResources().getString(
                            R.string.txt_setting_transaction_hint2));
                        clearDialog.setButtonText(
                            getResources().getString(R.string.txt_setting_transaction_hint4),
                            getResources().getString(R.string.txt_setting_transaction_hint3));
                        return;
                    }
                    //输入密码
                    showInputPwdDialog();
                }
                else
                {
                    CommonUtils.toastMsgShort(mContext, "未选择请体验金券或暂无数据!");
                }
                break;
            case R.id.wifi_load_again :
                getUseExperienceData();
                break;
            default :
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== 30){
            if(resultCode==Activity.RESULT_OK){
                Logs.d("登录后，初始化");
                mToken = CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN);
                getUseExperienceData();
            }
        }
    }
    
    /**
     * 交易密码输入框
     */
    private void showInputPwdDialog(){
        if(passwrodDialog==null){
            passwrodDialog=new PayPasswrodDialog(mContext);
            passwrodDialog.setOnPayPasswrodClickListener(new PayPasswrodClickListener() {
                
                @Override
                public void OnClick(String payPasswrod) {
                    // TODO Auto-generated method stub
                    pay_pass = payPasswrod;
                }
            });
            passwrodDialog.setButtonClickListener(new ButtonOnClickListener() {
                
                @Override
                public void onButton1Click(View v) {
                    // TODO Auto-generated method stub
                    HashMap<String,Object> hashMap=new HashMap<String, Object>();
                    hashMap.put("tradPassWord", pay_pass);
                    connection(new SecurityCenterService().validatePayPassword(2, hashMap, ExperienceActivity.this));
                    passwrodDialog=null;
                }
            });
            passwrodDialog.show();
            showKeyBoard();
        }else{
            passwrodDialog.show();
            showKeyBoard();
        }
    }
    
    /**交易密码错误框
     * @param response
     */
    private void showErrorDialog(PayValidateErrorResponse response){
        if(response.getResult().getNum()>0){
            PayHintDialog payclearDialog = new PayHintDialog(mContext);
            payclearDialog.show();
            payclearDialog.setDescri(getResources().getString(R.string.error_paypwd_hint,response.getResult().getNum()));
            payclearDialog.setButtonClickListener(new PayHintDialog.ButtonOnClickListener() {

                @Override
                public void onButton2Click(View v) {
                    showInputPwdDialog();
                }

                @Override
                public void onButton1Click(View v) {
                    pushActivity(ForgetPasswordActivity.class);
                }
            });                         
        }else{
            CommonUtils.toastMsgShort(this,getResources().getString(R.string.error_paypwd_hint2,response.getResult().getTime()));
        }
    }
    private void showKeyBoard()
    {
        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 300);
    }

}
