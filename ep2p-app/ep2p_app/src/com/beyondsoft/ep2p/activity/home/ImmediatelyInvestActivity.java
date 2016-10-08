package com.beyondsoft.ep2p.activity.home;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beyondsoft.ep2p.ActivityManager;
import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.EServicerAgreementActivity;
import com.beyondsoft.ep2p.activity.LoginActivity;
import com.beyondsoft.ep2p.activity.core.MainHolder;
import com.beyondsoft.ep2p.activity.mine.activity.ForgetPasswordActivity;
import com.beyondsoft.ep2p.activity.mine.activity.RechargeActivity;
import com.beyondsoft.ep2p.activity.mine.activity.ValidateLoginPasswordActivity;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.TzSucceedInfoBean;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.model.response.ImmediatelyInvestResponse;
import com.beyondsoft.ep2p.model.response.PayValidateErrorResponse;
import com.beyondsoft.ep2p.model.response.SaveBorrowResponse;
import com.beyondsoft.ep2p.model.response.ValidatePayPwdResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.Logs;
import com.beyondsoft.ep2p.utils.StringRequest2;
import com.beyondsoft.ep2p.utils.StringUtils;
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
 * 新手标详情-->立即投标页面
 * @ClassName:ImmediatelyInvestActivity 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2015年12月18日
 *
 */
public class ImmediatelyInvestActivity extends BaseActivity implements OnClickListener
{

    //Title
    private Context mContext;
    private TextView tv_title;
    private TextView tv_title_right;
    private LinearLayout  ll_layout_yqsy,ll_layout_kt_amount;//ll_lineaylayout,

    
    private TextView tv_available_balance, tv_yt_amount, tv_kt_amount, //tv_jx_raise,
            tv_yq_ear, tv_reg_xy;
    private TextView tv_moderepayment2,tv_moderepayment;
    private EditText et_tz_amount;
    private CheckBox checkBox1;
    private Button bn_immediately_tz, bn_yuqisy;
    private boolean isShowLinearLayout = true;
    private int isShowUI = 0;
    private boolean isChecked_xy = true;
    private int count_jxq = 0;//加息券
    private String mToken = "";
    private int mIsDay_sign = 0;  //是否是天标
    private int mPeriod = 12;
    private double jxq_ratio = 0.0;//加息券比例

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immediately_invest);
        mContext = ImmediatelyInvestActivity.this;
        ActivityManager.getInstance().pushActivity(ImmediatelyInvestActivity.this);
        initTitle();
        initView();
        initData();
        initListener();
    }

    private void initTitle()
    {
        tv_title = (TextView) findViewById(R.id.title_content_tv);
        tv_title.setText("立即投资");
        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
        tv_title_right.setText("充值");
        tv_title_right.setOnClickListener(this);
    }

    private void initView()
    {
//        ll_lineaylayout = (LinearLayout) findViewById(R.id.ll_lineaylayout);
        ll_layout_yqsy = (LinearLayout) findViewById(R.id.ll_layout_yqsy); //预期收益布局
        tv_available_balance = (TextView) findViewById(R.id.tv_available_balance);
        tv_yt_amount = (TextView) findViewById(R.id.tv_yt_amount);
        tv_kt_amount = (TextView) findViewById(R.id.tv_kt_amount);
        
        ll_layout_kt_amount = (LinearLayout) findViewById(R.id.ll_layout_kt_amount);
        ll_layout_kt_amount.setVisibility(View.GONE);//没有选择加息券 不显示
//        tv_jx_raise = (TextView) findViewById(R.id.tv_jx_raise);
        tv_yq_ear = (TextView) findViewById(R.id.tv_yq_ear);
        tv_reg_xy = (TextView) findViewById(R.id.tv_reg_xy);
        tv_reg_xy.setOnClickListener(this);
        tv_moderepayment = (TextView) findViewById(R.id.tv_moderepayment);//倍投
        tv_moderepayment2 = (TextView) findViewById(R.id.tv_moderepayment2);//加息券
        tv_moderepayment2.setText("0 "+mContext.getResources().getString(R.string.txt_tv_zs_number2));
        tv_moderepayment2.setOnClickListener(this);

        et_tz_amount = (EditText) findViewById(R.id.et_tz_amount);
        checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        bn_yuqisy = (Button) findViewById(R.id.bn_yuqisy);
        bn_yuqisy.setOnClickListener(this);
        bn_immediately_tz = (Button) findViewById(R.id.bn_immediately_tz);
        bn_immediately_tz.setOnClickListener(this);
    }

    private TzSucceedInfoBean mTzSucceedInfoBean;
    private String mPid = "",mEdit_money = "",borrowName = "";

    private void initData()
    {
        /**
         * isTimesInvest =1  就是 需要倍投投资
         * isTimesInvest =2 或者其它值   就是不需要倍数投资
         *        startMoney   tv_moderepayment.setText("100");
         * 投资金额%起投金额=0 为起投金额的倍数   这个验证通过
         */
        mToken = CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN);
        //可用余额：
        tv_available_balance.setText("￥" + StringUtils.formatMoney(UserPersonalInfo.getAvailableBalance())); //通过用户信息对象获取
        
        if (getIntent() != null)
        {
            mIsDay_sign = getIntent().getIntExtra("isDay", 0);// isDay
            isShowUI = getIntent().getIntExtra(Constants.EP2P, 0);
            mPid = getIntent().getStringExtra("pid");
            borrowName  = getIntent().getStringExtra("borrowName");//项目名称
            mEdit_money = getIntent().getStringExtra("edit_moeny");
            Logs.d("pid =:" + mPid);
        }
        if(!TextUtils.isEmpty(mEdit_money)){
            et_tz_amount.setText(""+mEdit_money);
        }
        
        if(mIsDay_sign == 3){//天标
            mPeriod = 360;
        }else{
            mPeriod = 12;
        }
        
        mTzSucceedInfoBean = new TzSucceedInfoBean();
        if(!TextUtils.isEmpty(borrowName)){
            mTzSucceedInfoBean.setTzXMName(borrowName);
        }else{
            mTzSucceedInfoBean.setTzXMName("");
        }
        getEplanNowBorrowData();
        
        Logs.d("mPeriod =:" + mPeriod);
    }

    //获取立即投资信息接口
    private void getEplanNowBorrowData()
    {
        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("pid", mPid); //标id
        connection(registerService.getStringRequest(1, URL.API_EPLAN_NOWBORROW, params,
            this));
    }

    private String mtradePwd = "";
    private PayPasswrodDialog passwrodDialog;
    private double yqsy_value = 0.00;
    
    //预期收益默认值
    private void YQsyButtom()
    {
        //预期收益（天标）=年化率/360*期限*输入金额
        tv_yq_ear.setText("￥" + StringUtils.formatMoney(yqsy_value)); //预期收益：

        //投资收益 （月标）=年化率/12*期限*输入金额
        tv_yt_amount.setText("￥" + StringUtils.formatMoney(yqsy_value)); //投资收益

        tv_kt_amount.setText("￥" + StringUtils.formatMoney(yqsy_value));//加息收益
    }

    private double mFinalTz = 0.0,mFinalJX=0.0;
    private void initListener()
    {
        et_tz_amount.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {//在输入数据时监听
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,//输入数据之前的监听
                    int after)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {//输入数据之后监听

                if (et_tz_amount.getText().toString().trim().length() > 7)
                {

                }

                if (TextUtils.isEmpty(et_tz_amount.getText().toString().trim()))
                {
                    YQsyButtom();
                    mTzSucceedInfoBean.setTzyqsy("0");
                }
                else
                {
                    if (mImmediatelyInvestResponse != null)
                    {
                        //投资奖励
//                      投资金额发生改变时，根据投资奖励比例等计算投资奖励。
//                      投资奖励=输入金额*投资奖励
                        double tz_reward = Double.parseDouble(et_tz_amount.getText().toString().trim()) *mImmediatelyInvestResponse.result.investRewardScale;
                        mTzSucceedInfoBean.setTzjl(StringUtils.isTestNull(tz_reward + "")); //投资奖励
                        Logs.d("投资奖励:"+StringUtils.isTestNull(tz_reward + ""));
                        if (!TextUtils.isEmpty(et_tz_amount.getText().toString().trim()))
                        {
                            double tz_sy = mImmediatelyInvestResponse.result.borrowRate
                                / mPeriod
                                * Double.parseDouble(mImmediatelyInvestResponse.result.borDeadline)
                                * Double.parseDouble(et_tz_amount.getText().toString()
                                        .trim());
                            mFinalTz = tz_sy;//目标值
                            BigDecimal bdtz = new BigDecimal(tz_sy);
                            mFinalTz = bdtz.setScale(2, BigDecimal.ROUND_HALF_UP)
                                    .doubleValue();//取2位小数[取值条件：四舍五入]
                            //投资收益 （月标）=年化率/12*期限*输入金额
                            tv_yt_amount.setText("￥" + new DecimalFormat("#,##0.00").format(mFinalTz)); //投资收益
                            
                            //加息收益 =  投资金额 * 加息券比例
                            //加息券比例通过接口获取【发现模块，加息券接口】
                            //double jxq_bi = 0.05;//加息券比例
                            mFinalJX = Double.parseDouble(et_tz_amount.getText().toString().trim())*  jxq_ratio;
                            tv_kt_amount.setText("￥" + new DecimalFormat("#,##0.00").format(mFinalJX));//加息收益
                            mTzSucceedInfoBean.setTzMoney(StringUtils
                                    .isTestNull(et_tz_amount.getText().toString().trim()));
                            /**
                            //预期收益（月标）=年化率/12*期限*输入金额；
                            //预期收益（天标）=年化率/360*期限*输入金额。
                            double yq_sy = mImmediatelyInvestResponse.result.borrowRate
                                / mPeriod
                                * Double.parseDouble(mImmediatelyInvestResponse.result.borDeadline)
                                * Double.parseDouble(et_tz_amount.getText().toString()
                                        .trim());
                            double mFinalProgress = yq_sy;//目标值
                            BigDecimal bd = new BigDecimal(yq_sy);
                            mFinalProgress = bd.setScale(2, BigDecimal.ROUND_HALF_UP)
                                    .doubleValue();//取2位小数[取值条件：四舍五入]
                            //预期收益（天标）=年化率/360*期限*输入金额  [利息收益（月标）=年化率/12*期限*输入金额]
                            tv_yq_ear.setText("￥" + mFinalProgress); //预期收益：
                            mTzSucceedInfoBean.setTzyqsy("" + mFinalProgress);//Math.round(mFinalProgress)
                            
                            //预期收益=投资收益+加息收益  
                             * mFinalTz+ddd
                            */
                            tv_yq_ear.setText("￥" + new DecimalFormat("#,##0.00").format(mFinalTz+mFinalJX)); //预期收益：
                            mTzSucceedInfoBean.setTzyqsy("" + new DecimalFormat("#,##0.00").format(mFinalTz+mFinalJX));//Math.round(mFinalProgress)
                        }
                        else
                        {
                            YQsyButtom();
                        }
                    }

                }
            }
        });

        //绑定监听器
        checkBox1.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1)
            {
                // TODO Auto-generated method stub
                isChecked_xy = arg1;
//                CommonUtils.toastMsgShort(mContext, arg1 ? "选中了" : "取消了选中");
            }
        });
    }

    @Override
    public void onClick(View arg0)
    {
        Intent intent = null;
        switch (arg0.getId())
        {
            case R.id.bn_immediately_tz :
                if(TextUtils.isEmpty(mToken)){
                    //跳转登录
                    pushActivityForResult(new Intent(mContext, LoginActivity.class), 30);
                    return;
                }
                
                if(!isChecked_xy){
                    CommonUtils.toastMsgShort(mContext, "请您阅读协议后才能投资!");
                    return;
                }
                
                if (TextUtils.isEmpty(et_tz_amount.getText().toString().trim()))
                {
                    CommonUtils.toastMsgShort(mContext, "投资金额不能为空，请重新输入!");
                    return;
                }

                if(!TextUtils.isEmpty(mImmediatelyInvestResponse.result.isTimesInvest)&&mImmediatelyInvestResponse.result.isTimesInvest.equals("1"))
                {
//                    tv_moderepayment.setText(""+mImmediatelyInvestResponse.result.startMoney+"整数倍");
                    if (Float.parseFloat(et_tz_amount.getText().toString().trim()) % mImmediatelyInvestResponse.result.startMoney != 0)
                    {

                        CommonUtils.toastMsgShort(mContext, "投资金额在可投范围外，请重新输入!");
                        return;
                    }
                }
                //投资少于200时，弹出提示“低于起投金额￥200.00”
                //投资高于6000时，弹出提示“您的投资总额已超出￥6000.00”
//                "startMoney": 100,//起投金额
//                "endMoney": 100,//投资上限 
                if(mImmediatelyInvestResponse!=null){
                    int sm = mImmediatelyInvestResponse.result.startMoney;
                    int em = mImmediatelyInvestResponse.result.endMoney;
                    if(sm>0){
                        if(Float.parseFloat(et_tz_amount.getText().toString().trim()) < sm){
                            CommonUtils.toastMsgShort(mContext, "低于起投金额￥"+sm+"，请重新输入!");
                            return;
                        } 
                    }
                    if(em>0){
                        if(Float.parseFloat(et_tz_amount.getText().toString().trim()) > em){
                            CommonUtils.toastMsgShort(mContext, "您的投资总额已超出￥"+em+"，请重新输入!");
                            return;
                        } 
                    }
                    if(Float.parseFloat(et_tz_amount.getText().toString().trim()) > UserPersonalInfo.getAvailableBalance()){
                        CommonUtils.toastMsgShort(mContext, "您的投资总额已超出可用余额，请充值或者重新输入!");
                        return;
                    } 
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
                showInputPwdDialog();
                break;

            case R.id.tv_title_right :
//                CommonUtils.toastMsgShort(mContext, "充值");
                if ("2".equals(UserPersonalInfo.getIsAttestation()))
                {

                    CustomDialog topUpDialog = new CustomDialog(mContext);
                    topUpDialog
                            .setButtonClickListener(new CustomDialog.ButtonOnClickListener()
                            {

                                @Override
                                public void onButton2Click(View v)
                                {

                                    startActivity(new Intent(mContext,
                                        AuthenticationActivity.class));
                                }

                                @Override
                                public void onButton1Click(View v)
                                {

                                }
                            });
                    topUpDialog.show();
                    topUpDialog.setTitle("提示");
                    topUpDialog.setDescri("为了您的资金安全，请先进行实名登记");
                    topUpDialog.hideView();
                    topUpDialog.setButtonText("取消", "去认证");
                }
                else
                {
                    pushActivity(RechargeActivity.class);
                }
                break;
            case R.id.bn_yuqisy :
                if (isShowLinearLayout)
                {
                    ll_layout_yqsy.setVisibility(View.VISIBLE);
                    bn_yuqisy.setBackgroundResource(R.drawable.bn_prospective_shape);
                    bn_yuqisy.setTextColor(getResources().getColor(R.color.white));
                    isShowLinearLayout = false;
                }
                else
                {
                    isShowLinearLayout = true;
                    ll_layout_yqsy.setVisibility(View.GONE);
                    bn_yuqisy.setBackgroundResource(R.drawable.bn_prospective_shape_off);
                    bn_yuqisy.setTextColor(getResources().getColor(R.color.e_button_bg));
                }
                break;

            case R.id.tv_reg_xy : //协议
                intent = new Intent(mContext, EServicerAgreementActivity.class);
                intent.putExtra(Constants.AGREEMENT_TYPE, 1);
                pushActivity(intent);
                break;

            case R.id.tv_moderepayment2 ://加息
                if (count_jxq > 0)
                {
                    intent = new Intent(mContext, RaiseInterestActivity.class);
                    intent.putExtra("pid", mPid);
//                    pushActivity(intent);
                    pushActivityForResult(intent, 50);
                }
                else
                {
                    CommonUtils.toastMsgShort(mContext, "没有加息券!");
                }
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
//                initData();
            }
        }
        if(requestCode== 50){
            if(resultCode==Activity.RESULT_OK){
                
                if(data != null){
                    mInvestAwardId = data.getStringExtra("jxq_id");
                    
                    jxq_ratio = data.getDoubleExtra("jxq_bl", 0.0);
                    Logs.d("返回的加息券id==:"+mInvestAwardId);
                    ll_layout_kt_amount.setVisibility(View.VISIBLE);//显示
                    //保留后面两位，不足用0代替
                    tv_moderepayment2.setText(new DecimalFormat("#,##0.00").format(jxq_ratio*100) + "%");//黄色的“38张加息券可用”按钮 
                    
                    //加息收益 =  投资金额 * 加息券比例
                    //加息券比例通过接口获取【发现模块，加息券接口】
                    //double jxq_bi = 0.05;//加息券比例
                    if(!TextUtils.isEmpty(et_tz_amount.getText().toString().trim())){
                        mFinalJX = Double.parseDouble(et_tz_amount.getText().toString().trim())*  jxq_ratio;
                    }else{
                        mFinalJX = 0.0 *  jxq_ratio;
                    }
                    tv_kt_amount.setText("￥" + new DecimalFormat("#,##0.00").format(mFinalJX));//加息收益
                    mTzSucceedInfoBean.setTzMoney(StringUtils
                            .isTestNull(et_tz_amount.getText().toString().trim()));
                    
                    tv_yq_ear.setText("￥" + new DecimalFormat("#,##0.00").format(Double.parseDouble(tv_yt_amount.getText().toString().replace("￥", ""))+mFinalJX)); //预期收益：
                    mTzSucceedInfoBean.setTzyqsy("" + new DecimalFormat("#,##0.00").format(mFinalTz+mFinalJX));//Math.round(mFinalProgress)
                }
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
                    mtradePwd=payPasswrod;
                }
            });
            passwrodDialog.setButtonClickListener(new ButtonOnClickListener() {
                
                @Override
                public void onButton1Click(View v) {
                    // TODO Auto-generated method stub
                    HashMap<String,Object> hashMap=new HashMap<String, Object>();
                    hashMap.put("tradPassWord", mtradePwd);
                    connection(new SecurityCenterService().validatePayPassword(2, hashMap, ImmediatelyInvestActivity.this));
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
    
    private String mInvestAwardId = "";
    //获取立即投资===支付
    private void getEplanSaveBorrowData(String pkey)
    {
        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("borrowId", mPid); //标id【必填】
        params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));//用户id【必填】
//        params.put("tradePwd", mtradePwd);//交易密码
        params.put("tranPKey", pkey);//【必填】
        params.put("amount", et_tz_amount.getText().toString());//投标金额【必填】
        if(!TextUtils.isEmpty(mInvestAwardId)){
            params.put("investAwardId",mInvestAwardId);//加息券id【非必填】
        }
        connection(registerService.getStringRequest(3, URL.API_EPLAN_SAVEBORROW, params,
            this));
    }

    private ImmediatelyInvestResponse mImmediatelyInvestResponse = null;
    @Override
    public void onResponse(int tag, Object values)
    {
        super.onResponse(tag, values);
        RefreshDialog.stopProgressDialog();
        switch (tag)
        {
            case 1 ://获取立即投资信息
                RefreshDialog.stopProgressDialog();
                mImmediatelyInvestResponse = (ImmediatelyInvestResponse) StringRequest2
                        .Json2Object(values.toString(),
                            new TypeToken<ImmediatelyInvestResponse>()
                            {
                            });
                //               isJiaxiTicket = 1 代表允许使用加息券  不等于1  代表不允许使用加息券

                if (mImmediatelyInvestResponse != null)
                {
                    if(!TextUtils.isEmpty(mImmediatelyInvestResponse.result.isTimesInvest)&&mImmediatelyInvestResponse.result.isTimesInvest.equals("1"))
                    {
                        tv_moderepayment.setText(""+mImmediatelyInvestResponse.result.startMoney+"整数倍");
                    }else{
                        tv_moderepayment.setVisibility(View.GONE);
                    }
                    
//                    tv_available_balance //可用余额：
//                            .setText("￥"
//                                + StringUtils.formatMoney(mImmediatelyInvestResponse.result.surplusMoney)); //调整获取对象
                    //                    setCFPUITextColor(tv_available_balance);

                    if(!TextUtils.isEmpty(mImmediatelyInvestResponse.result.isJiaxiTicket)){
                        if (mImmediatelyInvestResponse.result.isJiaxiTicket.equals("1"))
                        {
                            tv_moderepayment2.setVisibility(View.VISIBLE);
                            count_jxq = 10;
                            //通过用户信息接口获取
                            count_jxq = UserPersonalInfo.getCardVoucherCount();
                            tv_moderepayment2.setText(count_jxq
                                + getResources().getString(R.string.txt_tv_zs_number2) + "");
                        }
                        else
                        {
                            tv_moderepayment2.setVisibility(View.GONE);
                        } 
                    }else{
                        tv_moderepayment2.setVisibility(View.GONE);
                    }

                    mTzSucceedInfoBean.setTzCode(StringUtils
                            .isTestNull(mImmediatelyInvestResponse.result.borrowCode));
                    
                    mTzSucceedInfoBean.setTzMoney(StringUtils.isTestNull(et_tz_amount
                            .getText().toString().trim()));
                    mTzSucceedInfoBean.setTzqx(StringUtils
                            .isTestNull(mImmediatelyInvestResponse.result.borDeadline));
                    mTzSucceedInfoBean
                            .setTznhl(StringUtils
                                    .isTestNull(mImmediatelyInvestResponse.result.borrowRate
                                        + ""));

                    //投资金额
                    //                    et_tz_amount.setText(StringUtils.isTestNull(mImmediatelyInvestResponse.result.alreadyMoney+"")+"");

                    if (!TextUtils.isEmpty(et_tz_amount.getText().toString().trim()))
                    {
                        double yq_sy = mImmediatelyInvestResponse.result.borrowRate
                            / mPeriod
                            * Double.parseDouble(mImmediatelyInvestResponse.result.borDeadline)
                            * Double.parseDouble(et_tz_amount.getText().toString().trim());
                        double mFinalProgress = yq_sy;//目标值
                        BigDecimal bd = new BigDecimal(yq_sy);
                        mFinalProgress = bd.setScale(2, BigDecimal.ROUND_HALF_UP)
                                .doubleValue();//取2位小数[取值条件：四舍五入]
                        //预期收益（天标）=年化率/360*期限*输入金额
                        tv_yq_ear.setText("￥" + mFinalProgress); //预期收益：
                        mTzSucceedInfoBean.setTzyqsy("" + mFinalProgress);

                        double tz_sy = mImmediatelyInvestResponse.result.borrowRate
                            / mPeriod
                            * Double.parseDouble(mImmediatelyInvestResponse.result.borDeadline)
                            * Double.parseDouble(et_tz_amount.getText().toString().trim());
                        double mFinalTz = tz_sy;//目标值
                        BigDecimal bdtz = new BigDecimal(tz_sy);
                        mFinalTz = bdtz.setScale(2, BigDecimal.ROUND_HALF_UP)
                                .doubleValue();//取2位小数[取值条件：四舍五入]
                        //投资收益 （月标）=年化率/12*期限*输入金额
                        tv_yt_amount.setText("￥" + mFinalTz); //投资收益
                    }
                    else
                    {
                        YQsyButtom();
                        mTzSucceedInfoBean.setTzyqsy("0.00");
                    }
                    break;
                }
            case 2: //验证原交易密码
                ValidatePayPwdResponse validatePayPwdResponse = gson.fromJson(values.toString(), ValidatePayPwdResponse.class);
                if(validatePayPwdResponse!=null){
                    getEplanSaveBorrowData(validatePayPwdResponse.getResult().getKey());
                }
                break;
            case 3 ://支付，确认投资
                RefreshDialog.stopProgressDialog();
                SaveBorrowResponse mSaveBorrowResponse = (SaveBorrowResponse) StringRequest2
                        .Json2Object(values.toString(),
                            new TypeToken<SaveBorrowResponse>()
                            {
                            });
                if (mSaveBorrowResponse != null)
                {
                    MainHolder.Instance(mContext).setmTzSucceedInfoBean(
                        mTzSucceedInfoBean);
                    Intent intent = null;
                    if(isShowUI == 2){ //散标投资
                        intent = new Intent(mContext, SBTZInvestSucceedActivity.class);
                        intent.putExtra(Constants.EP2P, 2);
                    }else{//isShowUI =1  ,4 
                        intent = new Intent(mContext, InvestSucceedActivity.class);
                        int ui_id = isShowUI == 1 ? 1 : 4;
                        intent.putExtra(Constants.EP2P, ui_id);
                    }
                    intent.putExtra("result_finish", mSaveBorrowResponse.result.result); //是否投资成功  boolean
                    pushActivity(intent);
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
        switch (tag)
        {
            case 1 :
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
                super.onErrorResponse(tag, error);
                break;
        }
    }

}
