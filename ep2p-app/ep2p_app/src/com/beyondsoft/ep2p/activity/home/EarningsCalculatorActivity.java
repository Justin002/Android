package com.beyondsoft.ep2p.activity.home;


import java.text.DecimalFormat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.beyondsoft.ep2p.ActivityManager;
import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.LoginActivity;
import com.beyondsoft.ep2p.activity.core.MainHolder;
import com.beyondsoft.ep2p.activity.mine.activity.RechargeActivity;
import com.beyondsoft.ep2p.activity.mine.activity.RechargeTimesActivity;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.widget.AuthenPopDialog;
import com.beyondsoft.ep2p.widget.AuthenPopDialog.AuthenButtonOnClickListener;

/**
 * 
 * 产品计算器
 * @ClassName:EarningsCalculatorActivity 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2015年12月18日
 *
 */
public class EarningsCalculatorActivity extends BaseActivity implements OnClickListener
{
    //Title
    private Context mContext;
    private TextView tv_title;
    private ImageView iv_right_falg;

    private TextView tv_yq_earnings, tv_yq_rate, tv_yq_deadline;
    private TextView tv_available_balance; //可用余额
    private TextView tv_no_login,tv_edit_biaoj;
    private EditText ed_input_sy;
    private Button bn_calculator;
    private String mToken = "";
    private int isLogin = 0;
    private int UI_falg = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earnings_calculator);
        ActivityManager.getInstance().pushActivity(EarningsCalculatorActivity.this);
        mContext = EarningsCalculatorActivity.this;
        initTitle();
        initView();
        initData();
    }

    private void initTitle()
    {
        tv_title = (TextView) findViewById(R.id.title_content_tv);
        tv_title.setText("收益计算器");
        iv_right_falg = (ImageView) findViewById(R.id.title_right);
        iv_right_falg.setVisibility(View.GONE);

    }

    private void initView()
    {
        tv_edit_biaoj = (TextView) findViewById(R.id.tv_edit_biaoj);
        tv_yq_earnings = (TextView) findViewById(R.id.tv_yq_earnings);
        tv_yq_rate = (TextView) findViewById(R.id.tv_yq_rate);
        tv_yq_deadline = (TextView) findViewById(R.id.tv_yq_deadline);
        tv_available_balance = (TextView) findViewById(R.id.tv_available_balance);
        
        //没有登录显示  未登录而且不显示金额，，登录显示 金额和文本显示为 充值
        tv_no_login = (TextView) findViewById(R.id.tv_no_login);
        tv_no_login.setOnClickListener(this);
        ed_input_sy = (EditText) findViewById(R.id.ed_input_sy);
        bn_calculator = (Button) findViewById(R.id.bn_calculator);
        bn_calculator.setOnClickListener(this);
    }

    private String mNhl="",mYue = "",mMoney= "";
    private int  mDateFlag = 0; //50 
    private void initData()
    {
        
        if(getIntent() != null){
            UI_falg = getIntent().getIntExtra(Constants.EP2P, 0);
            mNhl = (String) getIntent().getExtras().get("js_nhl");
            mYue =(String) getIntent().getExtras().get("js_yue");
            mMoney = (String) getIntent().getExtras().get("js_money");
            mMoney = (String) getIntent().getExtras().get("js_money");
            mDateFlag =  getIntent().getExtras().getInt("mDateFlag");
        }
        
        mToken = CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN);
        
        tv_yq_rate.setText(mNhl+"");
        if(mDateFlag == 50){
            tv_yq_deadline.setText(mYue+"日");
        }else{
            tv_yq_deadline.setText(mYue+"月");
        }
     
        //
//        tv_available_balance.setText(getResources().getString(R.string.txt_tv_available_balance)+mMoney+"元");dfdf
        tv_available_balance.setText("可用余额:"+StringUtils.formatMoney(UserPersonalInfo.getAvailableBalance())); //通过用户信息对象获取
        //预期收益：
        //根据输入的金额、项目年化率、项目期限、项目还款方式（投资人）
        //等参数计算出预计收益。预期收益（月标）=年化率/12*期限*输入金额；【1月标】
        //预期收益（天标）=年化率/360*期限*输入金额。【2年标】 
        if(!TextUtils.isEmpty(mToken)){
            tv_no_login.setText(Html.fromHtml("<u>充值<u/>"));
            isLogin = 1;
        }else{
            tv_no_login.setText(Html.fromHtml("<u>未登录<u/>"));
            isLogin = 0;
        }
      
        
        if(TextUtils.isEmpty(ed_input_sy.getText().toString().trim())){
            bn_calculator.setEnabled(false);
        }else{
//            bn_calculator.setEnabled(true);
            isButtonEnabled();
        }

        ed_input_sy.addTextChangedListener(new TextWatcher()
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
//                CommonUtils.toastMsgShort(mContext, "计算中...");
                
                if (!TextUtils.isEmpty(ed_input_sy.getText().toString().trim()))
                {
                    double ttxt_nhl  = Double.parseDouble(ed_input_sy.getText().toString().trim())/100;
                    double txt_date= 0.0;
                    if(mDateFlag == 50){
                        txt_date =  Double.parseDouble(tv_yq_deadline.getText().toString().trim()
                            .replace("日", "").trim());
                    }else{
                        txt_date =  Double.parseDouble(tv_yq_deadline.getText().toString().trim()
                            .replace("月", "").trim());
                    }
                    float txt_yq_earnings = (float) (Float.parseFloat(tv_yq_rate.getText()
                            .toString().trim().replace("%", "").trim()) / 12 * txt_date* ttxt_nhl);
                    
                    tv_yq_earnings.setText("￥"+new DecimalFormat("#,##0.00").format(txt_yq_earnings) );//保留后面两位，不足用0代替
//                    tv_yq_earnings.setText("￥" + txt_yq_earnings);
//                    bn_calculator.setEnabled(true);
                    isButtonEnabled();
                    tv_edit_biaoj.setVisibility(View.VISIBLE);
                }else{
                    tv_edit_biaoj.setVisibility(View.GONE);
                    bn_calculator.setEnabled(false);
                    tv_yq_earnings.setText("￥0.00");
                }
            }
        });

        //软键盘 ==搜索按钮事件
        ed_input_sy.setOnEditorActionListener(new OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                // 先隐藏键盘
                ((InputMethodManager) ed_input_sy.getContext().getSystemService(
                    Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    EarningsCalculatorActivity.this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
                if (actionId == EditorInfo.IME_ACTION_SEARCH) //搜索
                {
                    return true;
                }
                else if (actionId == EditorInfo.IME_ACTION_SEND)
                {//发送
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View arg0)
    {
        switch (arg0.getId())
        {
            case R.id.bn_calculator ://立即投资...
                if(TextUtils.isEmpty(ed_input_sy.getText().toString().trim()))
                {
                    CommonUtils.toastMsgShort(mContext, "请输入您想投资的金额!");
                    return;
                }
//                CommonUtils.toastMsgShort(mContext, "立即投资...");
//                MainHolder.Instance(mContext).setTzjlPid(mCFPTypeDetailsResponse.result.pid);
                Intent intent = new Intent(mContext, ImmediatelyInvestActivity.class);
                if(UI_falg == 1){
                    intent.putExtra(Constants.EP2P, 1);
                }else if(UI_falg == 2)
                {
                    intent.putExtra(Constants.EP2P, 2);
                }
                intent.putExtra("pid",  MainHolder.Instance(mContext).getTzjlPid());
                intent.putExtra("edit_moeny",  ed_input_sy.getText().toString().trim());
                pushActivity(intent);
                break;
            case R.id.tv_no_login :
                //没有登录显示  未登录，，登录显示  充值
                if(isLogin==1){
                    if (UserPersonalInfo.getIsAttestation()!=null&&UserPersonalInfo.getIsAttestation().equals("2"))
                    {
                        AuthenPopDialog authenPopDialog = new AuthenPopDialog(mContext);
                        authenPopDialog
                                .setButtonClickListener(new AuthenButtonOnClickListener()
                                {

                                    @Override
                                    public void onButton2Click(View v)
                                    {
                                        // TODO Auto-generated method stub
                                        pushActivity(AuthenticationActivity.class);
                                    }

                                    @Override
                                    public void onButton1Click(View v)
                                    {
                                    }
                                });
                        authenPopDialog.show();
                        authenPopDialog.setTitle("提示");
                        authenPopDialog.setDescri("为了您的资金安全，请先进行实名登记");
                        authenPopDialog.setButtonText("取消", "去认证");
                    }
                    else
                    {
                        if (UserPersonalInfo.getIsFirstPay() == 0)
                        {
                            pushActivity(RechargeActivity.class);
                        }
                        else
                        {
                            pushActivity(RechargeTimesActivity.class);
                        }
                    }
                }else{
//                    CommonUtils.toastMsgShort(mContext, "到登录页面登录");
                    pushActivity(LoginActivity.class);
                }
                break;
               
            default :
                break;
        }
    }
    
    private void isButtonEnabled(){
        if(MainHolder.Instance(mContext).isEarnings){  //全局的是否可以点击投资按钮
            bn_calculator.setEnabled(true);
        }else{
            bn_calculator.setEnabled(false);
        }
    }

}
