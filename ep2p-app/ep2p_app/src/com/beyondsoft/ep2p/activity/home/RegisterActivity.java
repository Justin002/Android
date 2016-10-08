package com.beyondsoft.ep2p.activity.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beyondsoft.ep2p.ActivityManager;
import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.LoginActivity;
import com.beyondsoft.ep2p.activity.core.MainHolder;
import com.beyondsoft.ep2p.activity.home.adapter.NewStandarUniqueAdapter;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.response.NewStandarUniqueResponse;
import com.beyondsoft.ep2p.model.response.NewStandarUniqueResponse.Result;
import com.beyondsoft.ep2p.model.response.RegisterJDResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.Logs;
import com.beyondsoft.ep2p.utils.StringRequest2;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.beyondsoft.ep2p.widget.CustomDialog2;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * 首页注册
 * @ClassName:RegisterActivity 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2015年12月15日
 *
 */
public class RegisterActivity extends BaseActivity implements OnClickListener,OnItemClickListener
{

    private Context mContext;
    private TextView tv_title;
    private ImageView iv_right_falg;
    private ListView iv_listview_other_recommend;
    private Button bn_register;
    private List<Result> mList = new ArrayList<Result>();
    @SuppressWarnings("rawtypes")
    private NewStandarUniqueAdapter mRecommendListAdapter = null;
    
    private TextView tv_text_zc,tv_text_rl,tv_text_tz,tv_text_bhint;
    private ImageView iv_zc_flow1,iv_zc_flow2,iv_zc_flow3;
    private TextView tv_fenge_line1,tv_fenge_line2;
   
    private String isAttestation = ""; //是否实名认证    【1已认证，2未认证】
    private String isRegistered = "";//是否注册【1已注册，2未注册】
    private String isInvestment = "";//是否投资【1有投资，2没有投资】
//    private int isGesturePass = 0;
//    private int isSharedPreferences = 0;  //是否手势设置【10：手势设置,11：认证】
    private String mToken = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = RegisterActivity.this;
        ActivityManager.getInstance().pushActivity(RegisterActivity.this);
        initTitle();
        initView();
        initData();
    }
    private void initTitle()
    {
        tv_title = (TextView) findViewById(R.id.title_content_tv);
        tv_title.setText("新手专享");
        iv_right_falg = (ImageView) findViewById(R.id.title_right);
        iv_right_falg.setVisibility(View.GONE);
    }
    private void initView()
    {
        bn_register  = (Button) findViewById(R.id.bn_register);
        bn_register.setOnClickListener(this);
        
        tv_text_zc =  (TextView) findViewById(R.id.tv_text_zc);
        tv_text_rl =  (TextView) findViewById(R.id.tv_text_rl);
        tv_text_tz =  (TextView) findViewById(R.id.tv_text_tz);
        tv_text_bhint  = (TextView) findViewById(R.id.tv_text_bhint);
        
        iv_zc_flow1 =  (ImageView) findViewById(R.id.iv_zc_flow1);
        iv_zc_flow2 =  (ImageView) findViewById(R.id.iv_zc_flow2);
        iv_zc_flow3 =  (ImageView) findViewById(R.id.iv_zc_flow3);
        tv_fenge_line1 =  (TextView) findViewById(R.id.tv_fenge_line1);
        tv_fenge_line2 =  (TextView) findViewById(R.id.tv_fenge_line2);
        
        iv_listview_other_recommend = (ListView) findViewById(R.id.listview_other_recommend);
        iv_listview_other_recommend.setOnItemClickListener(this);
    }
    
    private void initData()
    {
//        isGesturePass = getIntent().getIntExtra(Constants.EP2P, 0);
        //是否手势设置【10：手势设置】
//        isSharedPreferences= CommonUtils.getIntByKey(mContext, Constants.IS_REGISTER_CHECK);
//        Logs.d("======" + isSharedPreferences);
        
        mToken = CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN);
        
        if(!TextUtils.isEmpty(mToken)){
            getTaskScheduleData();
        }else{
//            CommonUtils.toastMsgShort(mContext, mContext.getResources().getString(R.string.login_hint));
          //跳转登录
            pushActivityForResult(new Intent(mContext,
                LoginActivity.class), 30);
        }
        //获取新手标
        getNewstandarUniqueData();
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        mToken = CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN);
        Logs.d("isDLAuthentication");
        if(MainHolder.Instance(mContext).isDLAuthentication()){
            if(!TextUtils.isEmpty(mToken)){
                getTaskScheduleData();
            }
        }
    }
    
    //注册成功
    private void RegistrationSuccessful(){
//        MainHolder.Instance(mContext).setRegistered(true);
        CommonUtils.addConfigInfo(mContext, Constants.IS_REGISTER_CHECK, true);
        bn_register.setText(getResources().getString(R.string.txt_register_authentication));
        iv_zc_flow1.setImageResource(R.drawable.e_news_img_checkon2_03);
        tv_fenge_line1.setBackgroundResource(R.color.e_syshint_bg);
        iv_zc_flow2.setImageResource(R.drawable.e_news_img_check1_03);
        tv_text_zc.setTextColor(mContext.getResources().getColor(R.color.e_syshint_bg));
        tv_text_bhint.setText("实名认证送888体验金");
        final CustomDialog2 clearDialog=new CustomDialog2(RegisterActivity.this,1);
        clearDialog.show();
        clearDialog.setTitle("注册成功");
//        clearDialog.setDescri("获得188元红包!");
        String Red_packInfo = CommonUtils.getStringByKey(mContext, Constants.KEY_RED_PACT_USER);
        clearDialog.setDescri(Red_packInfo);
        Logs.d("注册成功的红包信息:"+Red_packInfo);
        clearDialog.setButtonClickListener(new CustomDialog2.ButtonOnClickListener()
        {
            
            @Override
            public void onLinearLayoutClick(View v)
            {
                clearDialog.dismiss();
            }
            
            @Override
            public void onButton2Click(View v)
            {
                
            }
            
            @Override
            public void onButton1Click(View v)
            {
                
            }
        });
    }
    
    //认证成功
    private void IdentitySuccess(){
//        MainHolder.Instance(mContext).setAttestation(true);
        CommonUtils.addConfigInfo(mContext, Constants.IS_Certification_CHECK, true);
        bn_register.setText(getResources().getString(R.string.txt_register_authentication_tz));
        iv_zc_flow1.setImageResource(R.drawable.e_news_img_checkon2_03);
        iv_zc_flow2.setImageResource(R.drawable.e_news_img_checkon2_03);
        tv_fenge_line1.setBackgroundResource(R.color.e_syshint_bg);
        tv_fenge_line2.setBackgroundResource(R.color.e_syshint_bg);
        iv_zc_flow3.setImageResource(R.drawable.e_news_img_check1_03);
        tv_text_zc.setTextColor(mContext.getResources().getColor(R.color.e_syshint_bg));
        tv_text_rl.setTextColor(mContext.getResources().getColor(R.color.e_syshint_bg));
        tv_text_bhint.setText("首次投资额>=10000元现金即送38元现金红包");
        
        final CustomDialog2 clearDialog=new CustomDialog2(RegisterActivity.this,1);
        clearDialog.show();
        clearDialog.setTitle("认证成功");
//        clearDialog.setDescri("获得888元体验金!");
        String Authen_Red_packInfo = CommonUtils.getStringByKey(mContext, Constants.KEY_RED_PACT_AUTNEN);
        clearDialog.setDescri(Authen_Red_packInfo);
        Logs.d("实名认证成功后的红包信息:"+Authen_Red_packInfo);
        clearDialog.setButtonClickListener(new CustomDialog2.ButtonOnClickListener()
        {
            
            @Override
            public void onLinearLayoutClick(View v)
            {
                clearDialog.dismiss();
            }
            
            @Override
            public void onButton2Click(View v)
            {
            }
            
            @Override
            public void onButton1Click(View v)
            {
            }
        });
    }
    
    //投资成功
    private void InvestmentSuccess(){
        bn_register.setText("进入投资页");
        bn_register.setTextColor(mContext.getResources().getColor(R.color.white));
        bn_register.setBackgroundResource(R.color.e_syshint_bg);
        iv_zc_flow1.setImageResource(R.drawable.e_news_img_checkon2_03);
        iv_zc_flow2.setImageResource(R.drawable.e_news_img_checkon2_03);
        iv_zc_flow3.setImageResource(R.drawable.e_news_img_checkon2_03);
        tv_fenge_line1.setBackgroundResource(R.color.e_syshint_bg);
        tv_fenge_line2.setBackgroundResource(R.color.e_syshint_bg);
        tv_text_zc.setTextColor(mContext.getResources().getColor(R.color.e_syshint_bg));
        tv_text_rl.setTextColor(mContext.getResources().getColor(R.color.e_syshint_bg));
        tv_text_tz.setTextColor(mContext.getResources().getColor(R.color.e_syshint_bg));
        tv_text_bhint.setText("恭喜完成所有任务,敬请了解其他投资产品");
    }
    
    //获取新手标数据接口
    private void getNewstandarUniqueData()
    {
        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
//        params.put("webTag", webTag);
        connection(registerService.getStringRequest(1, URL.API_NEWSTANDAR_UNIQUE, params,this));
    }
    
    //获取体验标数据接口
    private void getExperienceborrowUniqueData()
    {
//        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
//        params.put("webTag", webTag);
        connection(registerService.getStringRequest(2, URL.API_EXPERIENCEBORROW_UNIQUE, params,this));
    }
    //获取新手任务进度信息接口
    private void getTaskScheduleData()
    {
        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("token", mToken);
        connection(registerService.getStringRequest(3, URL.API_CUSTOMER_TASKSCHEDULE, params,this));
    }
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void onResponse(int tag, Object values)
    {
        super.onResponse(tag, values);
        NewStandarUniqueResponse mNewStandarUniqueResponse =null;
        switch (tag)
        {
            case 1 ://新手标
                 mNewStandarUniqueResponse = (NewStandarUniqueResponse) StringRequest2
                .Json2Object(values.toString(),
                    new TypeToken<NewStandarUniqueResponse>()
                    {
                    });
                mNewStandarUniqueResponse.result.isType = "9";//新手标
                mList.add(mNewStandarUniqueResponse.result);
                getExperienceborrowUniqueData();
                break;
            case 2 : //体验标
                RefreshDialog.stopProgressDialog();

                mNewStandarUniqueResponse = (NewStandarUniqueResponse) StringRequest2
                        .Json2Object(values.toString(),
                            new TypeToken<NewStandarUniqueResponse>()
                            {
                            });
                mList.add(mNewStandarUniqueResponse.result);
                mNewStandarUniqueResponse.result.isType = "10";//体验标

                mRecommendListAdapter = new NewStandarUniqueAdapter(
                    RegisterActivity.this, mList);
                iv_listview_other_recommend.setAdapter(mRecommendListAdapter);

                mRecommendListAdapter
                        .setOnClickListener(new NewStandarUniqueAdapter.OnClickListener()
                        {

                            @Override
                            public void OnClick(int flag)
                            {
                                if (TextUtils.isEmpty(mToken))
                                {
                                    //跳转登录
                                    pushActivityForResult(new Intent(mContext,
                                        LoginActivity.class), 30);
                                    return;
                                }
                                Intent intent = null;
                                if (flag == 0)
                                { //抢购按钮：招标中时正常颜色，点按打开新手标立即投资页面。结束时不可用颜色，不可点按
                                    intent = new Intent(mContext,
                                        ImmediatelyInvestActivity.class);
                                    intent.putExtra("pid", mList.get(flag).pid);
                                    intent.putExtra("isDay", 3);
                                    pushActivity(intent);
                                }
                                else
                                {//抢购按钮：招标中时正常颜色，点按打开体验标立即投资页面。结束时不可用颜色，不可点按。

                                    intent = new Intent(mContext,
                                        ExperienceActivity.class);
                                    intent.putExtra("pid", mList.get(flag).pid);
                                    pushActivity(intent);//ExperienceDetailsActivity
                                }
                            }
                        });
                break;
                case 3://新手任务进度
                    RefreshDialog.stopProgressDialog();
                    RegisterJDResponse mRegisterJDResponse = (RegisterJDResponse) StringRequest2
                            .Json2Object(values.toString(),
                                new TypeToken<RegisterJDResponse>()
                                {
                                });
                    MainHolder.Instance(mContext).setDLAuthentication(false);
                    if(mRegisterJDResponse!=null){
                        isAttestation = mRegisterJDResponse.result.isAttestation;
                        isRegistered= mRegisterJDResponse.result.isRegistered;
                        isInvestment= mRegisterJDResponse.result.isInvestment;
                        
                        if(isRegistered.equals("1") && isAttestation.equals("1") && isInvestment.equals("1")){
                            InvestmentSuccess();
                        }else if(isRegistered.equals("1") && isAttestation.equals("1")&& !isInvestment.equals("1")){
                            if(!CommonUtils.getBooleanByKey(mContext, Constants.IS_Certification_CHECK)){//MainHolder.Instance(mContext).isAttestation()
                                IdentitySuccess();
                            }else{
                                bn_register.setText(getResources().getString(R.string.txt_register_authentication_tz));
                                iv_zc_flow1.setImageResource(R.drawable.e_news_img_checkon2_03);
                                iv_zc_flow2.setImageResource(R.drawable.e_news_img_checkon2_03);
                                tv_fenge_line1.setBackgroundResource(R.color.e_syshint_bg);
                                tv_fenge_line2.setBackgroundResource(R.color.e_syshint_bg);
                                iv_zc_flow3.setImageResource(R.drawable.e_news_img_check1_03);
                                tv_text_zc.setTextColor(mContext.getResources().getColor(R.color.e_syshint_bg));
                                tv_text_rl.setTextColor(mContext.getResources().getColor(R.color.e_syshint_bg));
                                tv_text_bhint.setText("首次投资额>=10000元现金即送38元现金红包");
                            }
                        }else{
                            if(isRegistered.equals("1")){//已注册
                                if(!CommonUtils.getBooleanByKey(mContext, Constants.IS_REGISTER_CHECK+"")){//MainHolder.Instance(mContext).isRegistered()
                                    RegistrationSuccessful();  
                                }else{
                                    bn_register.setText(getResources().getString(R.string.txt_register_authentication));
                                    iv_zc_flow1.setImageResource(R.drawable.e_news_img_checkon2_03);
                                    tv_fenge_line1.setBackgroundResource(R.color.e_syshint_bg);
                                    iv_zc_flow2.setImageResource(R.drawable.e_news_img_check1_03);
                                    tv_text_zc.setTextColor(mContext.getResources().getColor(R.color.e_syshint_bg));
                                    tv_text_bhint.setText("实名认证送888体验金");
                                }
                               
                            }else if(isAttestation.equals("1")){//已认证
                                if(!CommonUtils.getBooleanByKey(mContext, Constants.IS_Certification_CHECK)){//MainHolder.Instance(mContext).isAttestation()
                                    IdentitySuccess();
                                }else{
                                    bn_register.setText(getResources().getString(R.string.txt_register_authentication_tz));
                                    iv_zc_flow1.setImageResource(R.drawable.e_news_img_checkon2_03);
                                    iv_zc_flow2.setImageResource(R.drawable.e_news_img_checkon2_03);
                                    tv_fenge_line1.setBackgroundResource(R.color.e_syshint_bg);
                                    tv_fenge_line2.setBackgroundResource(R.color.e_syshint_bg);
                                    iv_zc_flow3.setImageResource(R.drawable.e_news_img_check1_03);
                                    tv_text_zc.setTextColor(mContext.getResources().getColor(R.color.e_syshint_bg));
                                    tv_text_rl.setTextColor(mContext.getResources().getColor(R.color.e_syshint_bg));
                                    tv_text_bhint.setText("首次投资额>=10000元现金即送38元现金红包");
                                }
                            }else {
                                if(isInvestment.equals("1")){//已投资
                                    InvestmentSuccess();
                                }else{
                                    bn_register.setText(getResources().getString(R.string.txt_register_authentication_tz));
                                    iv_zc_flow1.setImageResource(R.drawable.e_news_img_checkon2_03);
                                    iv_zc_flow2.setImageResource(R.drawable.e_news_img_checkon2_03);
                                    tv_fenge_line1.setBackgroundResource(R.color.e_syshint_bg);
                                    tv_fenge_line2.setBackgroundResource(R.color.e_syshint_bg);
                                    iv_zc_flow3.setImageResource(R.drawable.e_news_img_check1_03);
                                    tv_text_zc.setTextColor(mContext.getResources().getColor(R.color.e_syshint_bg));
                                    tv_text_rl.setTextColor(mContext.getResources().getColor(R.color.e_syshint_bg));
                                    tv_text_bhint.setText("首次投资额>=10000元现金即送38元现金红包");
                                }
                            }
                        }
                    }
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
        CommonUtils.toastMsgShort(mContext, error);
        if(tag == 3){
            MainHolder.Instance(mContext).setDLAuthentication(false);
        }
    }
    
    @Override
    public void onClick(View arg0)
    {
        switch (arg0.getId())
        {
            case R.id.bn_register ://isGesturePass
                if(!isRegistered.equals("1")){//未注册
                    Intent intent = new Intent(RegisterActivity.this, NewUserRegisterActivity.class);
                    intent.putExtra(Constants.EP2P, 10);
                    pushActivity(intent); 
                }else if(!isAttestation.equals("1")){//未认证
                    pushActivity(AuthenticationActivity.class); 
                    ActivityManager.getInstance().pushActivity(RegisterActivity.this);
                }else{  //去投资
                    pushActivity(CFPTypeActivity.class); 
                }
                break;

            default :
                break;
        }
    }
    
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
    {
        Intent intent = null;
        String borrowId = mList.get(arg2).pid;
        Logs.d("borrowId="+borrowId);
        switch (arg2)
        {
            case 0 ://新手标items 。新手标方块：点按打开新手标详情。
                intent = new Intent(mContext, ProductDetailsActivity.class);
//                intent.putExtra("isType", 9); 
                intent.putExtra("borrowId", borrowId); 
                pushActivity(intent);
                break;
            case 1 ://体验标items 体验标方块：点按打开体验标详情。
                intent = new Intent(mContext, ExperienceDetailsActivity.class);
//                intent.putExtra("isType", 10);
                intent.putExtra("borrowId", borrowId); 
                pushActivity(intent);
                break;
            default :
                break;
        }
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 30)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                Logs.d("登录后，初始化");
                mToken = CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN);
            }
        }
    }   
}
