package com.beyondsoft.ep2p.activity.home;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.EServicerAgreementActivity;
import com.beyondsoft.ep2p.activity.LoginActivity;
import com.beyondsoft.ep2p.activity.core.MainHolder;
import com.beyondsoft.ep2p.activity.home.adapter.HaiKJIhuaListAdapter;
import com.beyondsoft.ep2p.activity.mine.activity.ForgetPasswordActivity;
import com.beyondsoft.ep2p.activity.mine.activity.ValidateLoginPasswordActivity;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.TzZRSucceedInfoBean;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.model.response.PayValidateErrorResponse;
import com.beyondsoft.ep2p.model.response.TransferInfoResponse;
import com.beyondsoft.ep2p.model.response.TransferInfoResponse.ReceiptPlanList;
import com.beyondsoft.ep2p.model.response.ValidatePayPwdResponse;
import com.beyondsoft.ep2p.model.response.VerificationBuyPwdResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.Logs;
import com.beyondsoft.ep2p.utils.StringRequest2;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.utils.TimeUtil;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.beyondsoft.ep2p.view.TimerTextView;
import com.beyondsoft.ep2p.widget.CustomDialog;
import com.beyondsoft.ep2p.widget.PayHintDialog;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog.ButtonOnClickListener;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog.PayPasswrodClickListener;
import com.google.gson.reflect.TypeToken;


/**
 * 
 * 债权详情
 * @ClassName:CreditorRightsDetailsActivity 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2015年12月21日
 *
 */
public class CreditorRightsDetailsActivity extends BaseActivity
    implements
        OnClickListener
{

    private Context mContext;
    //title
    private TextView tv_title;
    private ImageView iv_right_falg;
    private RelativeLayout rl_relayout,rl_layout_buttom_data;

    private TextView tv_gud_text1,tv_gud_text2;
    private TextView tv_re_hint1,tv_name_code;
    private TimerTextView tv_tv_timertextview;
    private TextView tv_zq_year, tv_zq_returned_money;
    private TextView tv_zq_total, tv_zq_income, tv_zq_price,tv_zq_time;
    private TextView tv_zq_bzfs,tv_zq_dbjg;
    private TextView tv_hkjh_falg,tv_zr_details_benxi,tv_zr_details_benjin,tv_zr_details_lixi;
    private ListView lv_listview_hk_schedule;
    private CheckBox checkBox1;
    private TextView tv_reg_xy;
    private Button bn_zq_buy;
    private boolean isChecked_xy = true;
    private boolean isShowButom_data = true; //是否显示底部的UI
    private String mTransferId ="";
    private String bl_falg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditorrights_details);
        mContext = CreditorRightsDetailsActivity.this;
        initTitle();
        initView();
        initData();
        initListeners();
    }
    
    private void initTitle()
    {
        tv_title = (TextView) findViewById(R.id.title_content_tv);
        tv_title.setText("债权详情");
        iv_right_falg = (ImageView) findViewById(R.id.title_right);
        iv_right_falg.setVisibility(View.GONE);
    }

    private void initView()
    {
        rl_relayout = (RelativeLayout) findViewById(R.id.rl_relayout);
        rl_layout_buttom_data = (RelativeLayout) findViewById(R.id.rl_layout_buttom_data);
        tv_tv_timertextview = (TimerTextView) findViewById(R.id.tv_tv_timertextview);
        
        tv_gud_text1 = (TextView) findViewById(R.id.tv_gud_text1);
        tv_gud_text2 = (TextView) findViewById(R.id.tv_gud_text2);
        tv_re_hint1 = (TextView) findViewById(R.id.tv_re_hint1);
        tv_name_code= (TextView) findViewById(R.id.tv_name_code);
        tv_zq_year = (TextView) findViewById(R.id.tv_zq_year); //年化率
        tv_zq_returned_money = (TextView) findViewById(R.id.tv_zq_returned_money); //期限[月]

        tv_zq_total = (TextView) findViewById(R.id.tv_zq_total); //项目价格
        tv_zq_price = (TextView) findViewById(R.id.tv_zq_price); //转让价格
        tv_zq_income = (TextView) findViewById(R.id.tv_zq_income); //还款方式
        tv_zq_time = (TextView) findViewById(R.id.tv_zq_time); //起息日
        tv_zq_bzfs = (TextView) findViewById(R.id.tv_zq_bzfs); //保障方式
        tv_zq_dbjg = (TextView) findViewById(R.id.tv_zq_dbjg); //担保机构
        
        //还款计划
        tv_hkjh_falg = (TextView) findViewById(R.id.tv_hkjh_falg); //还款计划title
        tv_hkjh_falg.setOnClickListener(this);
        lv_listview_hk_schedule= (ListView) findViewById(R.id.lv_listview_hk_schedule);//还款计划列表数据
        tv_zr_details_benxi = (TextView) findViewById(R.id.tv_zr_details_benxi); //应收本息:
        tv_zr_details_benjin = (TextView) findViewById(R.id.tv_zr_details_benjin); //应收本金:
        tv_zr_details_lixi = (TextView) findViewById(R.id.tv_zr_details_lixi); //应收利息:
        
        checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        tv_reg_xy = (TextView) findViewById(R.id.tv_reg_xy);
        tv_reg_xy.setOnClickListener(this);
        
        bn_zq_buy = (Button) findViewById(R.id.bn_zq_buy);
        bn_zq_buy.setOnClickListener(this);

    }
    private String mToken = "";
    private TzZRSucceedInfoBean mTzZRSucceedInfoBean;//转让专区  债权投资成功数据
    private void initData()
    {
        if(getIntent()!=null)
        {
            mTransferId = getIntent().getStringExtra("transferId");
            Logs.d("mTransferId=:"+mTransferId);
        }

        //        if (tv_tv_timertextview.isRun())
        //        {
        //            tv_tv_timertextview.stopRun();//停止倒计时
        //        }
        mToken = CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN);
        getTrabsferListData();
        //        详情----》判断是否登陆----》校验余额---》校验交易密码---》提交到购买
        //        可用余额>转让金额？余额够：余额不足
        mTzZRSucceedInfoBean = new TzZRSucceedInfoBean();
    }
    
    //获取转让专区{债权购买详情}
    private void getTrabsferListData()
    {
        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("transferId", mTransferId); //债权转让id
        if(!TextUtils.isEmpty(mToken)){
            params.put("token", mToken);//不是必填【已登陆才传】
        }
        connection(registerService.getStringRequest(1, URL.API_TRABSFER_LIST_INFO, params,this));
    }
    
    private String mTradePwd = "";
    private List<ReceiptPlanList> mReceiptPlanList = new ArrayList<ReceiptPlanList>();
    private HaiKJIhuaListAdapter mHaiKJIhuaListAdapter;
    //购买 ==3.13.3购买债权接口
    private void getTrabsferBuyData(String pKey)
    {
        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("token",mToken);
        params.put("transferId", mTransferId); //债权转让id
//        params.put("tradePwd", mTradePwd); //交易密码
        params.put("tranPKey", pKey); //交易密码
        connection(registerService.getStringRequest(3, URL.API_TRABSFER_LIST_BUY, params,this));
    }
    
    private double availableAmount = 0.0,successAmount=  0.0;
    @Override
    public void onResponse(int tag, Object values)
    {
        super.onResponse(tag, values);
        RefreshDialog.stopProgressDialog();
        switch (tag)
        {
            case 1 :
                TransferInfoResponse  mTransferInfoResponse = (TransferInfoResponse) StringRequest2
                .Json2Object(values.toString(), new TypeToken<TransferInfoResponse>()
                {
                });
                
                if(mTransferInfoResponse!=null)
                {
                    tv_gud_text1.setText(getResources().getString(R.string.txt_tv_zq_year));
                    tv_gud_text2.setText(getResources().getString(R.string.txt_tv_zq_returned_money2));
                    //status  == 2  已转让  不可点击
                    if(mTransferInfoResponse.result.transferModel.status.equals("2")){
                        bn_zq_buy.setText("已转让");
                        bn_zq_buy.setEnabled(false);
                        tv_tv_timertextview.setVisibility(View.GONE);
                    }else{
                        bn_zq_buy.setEnabled(true);
                        tv_tv_timertextview.setVisibility(View.VISIBLE);
                    }
                    
                    availableAmount =  mTransferInfoResponse.result.availableAmount;// 可用余额
                    successAmount = mTransferInfoResponse.result.transferModel.successAmount;//,转让价格
                    tv_re_hint1.setText(StringUtils.isTestNull(mTransferInfoResponse.result.transferModel.createUserName));
                    tv_name_code.setText(StringUtils.isTestNull(mTransferInfoResponse.result.transferModel.borrowCode));
                    mTzZRSucceedInfoBean.setTzCode(StringUtils.isTestNull(mTransferInfoResponse.result.transferModel.borrowCode));
                    if(StringUtils.isTestNull(String.valueOf(mTransferInfoResponse.result.transferModel.profitRate)).equals("")){
                        tv_zq_year.setText("0.0%");
                        mTzZRSucceedInfoBean.setTznhl("0.0");
                    }else{
                        Float mFinalProgress = Float.parseFloat((mTransferInfoResponse.result.transferModel.profitRate * 100)+"") ;//目标进度
                        BigDecimal bd = new BigDecimal(mFinalProgress);
                        mFinalProgress = bd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();//取2位小数[取值条件：四舍五入]
                        if(mFinalProgress % 1 == 0){// 是这个整数，小数点后面是0
                            bl_falg = "#,##0";
                        }else{//不是整数，小数点后面不是0
                            bl_falg = "#,##0.00";
                        }
//                      tv_zq_year.setText(""+mFinalProgress+"%");
                        tv_zq_year.setText(new DecimalFormat(bl_falg).format(mFinalProgress)+"%");//保留后面两位，不足用0代替
                        
                        mTzZRSucceedInfoBean.setTznhl(new DecimalFormat(bl_falg).format(mFinalProgress)+"");
                    }
                    tv_zq_returned_money.setText(""+StringUtils.isTestNull(mTransferInfoResponse.result.transferModel.timeRemaining+"")+"个月");  //期限【还款计划期限】
                    mTzZRSucceedInfoBean.setTzqx(""+StringUtils.isTestNull(mTransferInfoResponse.result.transferModel.timeRemaining+""));
                    
                    String expireDate =  TimeUtil.longToString(mTransferInfoResponse.result.expireDate, "yyyy-MM-dd HH:mm:ss");//债权失效时间
                    String sysDate  = TimeUtil.longToString(mTransferInfoResponse.result.sysDate, "yyyy-MM-dd HH:mm:ss");//系统时间
                    tv_tv_timertextview.setTimes( StringUtils.CountDown(expireDate, sysDate));
                    if (!tv_tv_timertextview.isRun())
                    {
                        tv_tv_timertextview.beginRun();//开始倒计时
                    }
                    
                    tv_zq_total.setText("￥"+StringUtils.formatMoney(mTransferInfoResponse.result.transferModel.projectValue));
//                    tv_zq_income.setText("￥"+StringUtils.isTestNull(mTransferInfoResponse.result.model.successAmount+""));
                    tv_zq_income.setText(StringUtils.isTestNull(mTransferInfoResponse.result.transferModel.repayTypeName+""));//还款方式
                    
                    tv_zq_price.setText("￥"+StringUtils.formatMoney(mTransferInfoResponse.result.transferModel.successAmount));
                    mTzZRSucceedInfoBean.setTzXMMoney(StringUtils.formatMoney(mTransferInfoResponse.result.transferModel.projectValue));
                    mTzZRSucceedInfoBean.setTzGMMoney(StringUtils.formatMoney(mTransferInfoResponse.result.transferModel.successAmount));//投资金额
                    tv_zq_time.setText(""+StringUtils.isTestNull(mTransferInfoResponse.result.qixiDate+"")); //#TODO 后面部署放开
                    mTzZRSucceedInfoBean.setTzqxDate(""+StringUtils.isTestNull(mTransferInfoResponse.result.qixiDate+""));//起息日期
                    
//                    tv_zq_bzfs.setText(StringUtils.isTestNull(mTransferInfoResponse.result.transferModel.repayTypeName)); //保障方式
                    tv_zq_bzfs.setText("100%本息保障");
                    
                    tv_zq_dbjg.setText(StringUtils.isTestNull(mTransferInfoResponse.result.transferModel.org)); //担保机构
                    
                    
                    if(mTransferInfoResponse.result.receiptPlanList.size()>0)
                    {
                        Logs.d("还款计划:=="+mTransferInfoResponse.result.receiptPlanList.size());
                        mReceiptPlanList = mTransferInfoResponse.result.receiptPlanList;
                        mHaiKJIhuaListAdapter = new HaiKJIhuaListAdapter(mContext, mReceiptPlanList);
                        lv_listview_hk_schedule.setAdapter(mHaiKJIhuaListAdapter);
                        setListViewHeightBasedOnChildren(lv_listview_hk_schedule);
                    }
                    
                    tv_zr_details_benxi.setText("应收本息:" +"￥"+StringUtils.formatMoney(mTransferInfoResponse.result.benxi)+"元");
                    tv_zr_details_benjin.setText("应收本金:" +"￥"+StringUtils.formatMoney(mTransferInfoResponse.result.benjin)+"元");
                    tv_zr_details_lixi.setText("应收利息:"+ "￥"+StringUtils.formatMoney(mTransferInfoResponse.result.lixi)+"元");
                }
                
                break;
            case 2 :   //验证交易密码
                ValidatePayPwdResponse validatePayPwdResponse = gson.fromJson(values.toString(), ValidatePayPwdResponse.class);
                if(validatePayPwdResponse!=null){
                    getTrabsferBuyData(validatePayPwdResponse.getResult().getKey());
                }
                break;
            case 3 : //购买 接口回调
                VerificationBuyPwdResponse mVerificationBuyPwdResponse = (VerificationBuyPwdResponse) StringRequest2
                        .Json2Object(values.toString(),
                            new TypeToken<VerificationBuyPwdResponse>()
                            {
                            });
                MainHolder.Instance(mContext).setmTzZRSucceedInfoBean(mTzZRSucceedInfoBean);
                
                Intent intent = new Intent(mContext, ZRInvestSucceedActivity.class);
                intent.putExtra(Constants.EP2P, 3);
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
            case 1:
                CommonUtils.toastMsgShort(mContext,error);
                break;
            case 2 :
                PayValidateErrorResponse response = gson.fromJson(error,
                    PayValidateErrorResponse.class);
                showErrorDialog(response);
                break;
            case 3:
                CommonUtils.toastMsgShort(mContext,error);
                break;
            default :
                break;
        }
    }
    
    private void initListeners()
    {
        //绑定监听器
        checkBox1.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1)
            {
                isChecked_xy = arg1;
//                CommonUtils.toastMsgShort(mContext, arg1 ? "选中了" : "取消了选中");
            }
        });        
    }
    private PayPasswrodDialog passwrodDialog;
    @Override
    public void onClick(View arg0)
    {
        switch (arg0.getId())
        {
            case R.id.bn_zq_buy ://立即购买
                if (!isChecked_xy)
                {
                    CommonUtils.toastMsgShort(mContext, "请同意e生财富协议相关内容!");
                    return;
                }
                if (TextUtils.isEmpty(mToken))
                {
                  //跳转登录
                    pushActivityForResult(new Intent(mContext, LoginActivity.class), 30);
                    return;
                }
                //可用余额>转让金额？余额够：余额不足
                if (availableAmount < successAmount)
                {
                    CommonUtils.toastMsgShort(mContext, "余额不足,请充值!");
                    return;
                }
                
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
//                                    CommonUtils.toastMsgShort(mContext, "取消");
                                }

                                @Override
                                public void onButton1Click(View v)
                                {
//                                    CommonUtils.toastMsgShort(mContext, "去设置");
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
                break;
            case R.id.tv_reg_xy :
                Intent intent = new Intent(mContext, EServicerAgreementActivity.class);
                intent.putExtra(Constants.AGREEMENT_TYPE, 1);
                pushActivity(intent);
                break;
            case R.id.tv_hkjh_falg :

                if (isShowButom_data)
                {
                    //图标更改
                    Drawable right = getResources().getDrawable(
                        R.drawable.e_img_ct_falg_11);
                    right.setBounds(0, 0, right.getMinimumWidth(),
                        right.getMinimumHeight());
                    tv_hkjh_falg.setCompoundDrawables(null, null, right, null);
                    lv_listview_hk_schedule.setVisibility(View.VISIBLE);
                    rl_layout_buttom_data.setVisibility(View.VISIBLE);
                    isShowButom_data = false;
                }
                else
                {
                    //图标更改
                    Drawable right = getResources().getDrawable(
                        R.drawable.e_img_ct_falg_buttom11);
                    right.setBounds(0, 0, right.getMinimumWidth(),
                        right.getMinimumHeight());
                    tv_hkjh_falg.setCompoundDrawables(null, null, right, null);
                    lv_listview_hk_schedule.setVisibility(View.GONE);
                    rl_layout_buttom_data.setVisibility(View.GONE);
                    isShowButom_data = true;
                }
                break;
            default :
                break;
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
                    mTradePwd = payPasswrod;
                }
            });
            passwrodDialog.setButtonClickListener(new ButtonOnClickListener() {
                
                @Override
                public void onButton1Click(View v) {
                    // TODO Auto-generated method stub
                    HashMap<String,Object> hashMap=new HashMap<String, Object>();
                    hashMap.put("tradPassWord", mTradePwd);
                    connection(new SecurityCenterService().validatePayPassword(2, hashMap, CreditorRightsDetailsActivity.this));
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
    
    private void setListViewHeightBasedOnChildren(ListView listView)
    {
        // 获取ListView对应的Adapter   
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
        {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++)
        {
            // listAdapter.getCount()返回数据项的数目   
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高   
            listItem.measure(0, 0);
            // 统计所有子项的总高度   
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
            + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度   
        // params.height最后得到整个ListView完整显示需要的高度   
        listView.setLayoutParams(params);
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
