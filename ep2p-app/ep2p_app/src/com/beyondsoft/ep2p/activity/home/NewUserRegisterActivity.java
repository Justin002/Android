package com.beyondsoft.ep2p.activity.home;


import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyondsoft.ep2p.ActivityManager;
import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.EServicerAgreementActivity;
import com.beyondsoft.ep2p.activity.core.MainHolder;
import com.beyondsoft.ep2p.activity.mine.activity.GesturePasswordActivity;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.request.LoginRequest;
import com.beyondsoft.ep2p.model.response.LoginResponse;
import com.beyondsoft.ep2p.model.response.PersonalInfoResponse;
import com.beyondsoft.ep2p.model.response.RegisteredResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.service.LoginService;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.Logs;
import com.beyondsoft.ep2p.utils.StringRequest2;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.LineEditText;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.google.gson.reflect.TypeToken;


/**
 * 
 * 新用户注册
 * @ClassName:NewUserRegisterActivity 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2015年12月16日
 *
 */
public class NewUserRegisterActivity extends BaseActivity implements OnClickListener
{

    //Title
    private Context mContext;
    private TextView tv_title;
    private ImageView iv_right_falg;

    private LineEditText et_phone,et_passwrod,et_phones_yqm;
    private EditText et_phones_yzm;
    private TextView tv_gety_yzm, tv_reg_xy;
    private CheckBox checkBox1;
    private ImageButton ib_mw_pass;
    private Button bn_finish ,bn_luang_yzm;

    private boolean isMingPass = true;
    private boolean isChecked_xy = true;
    private TimeCount time;
    private BaseService baseService;
    //同一手机单日最多能接收N次注册手机验证码（N=5，N系统后台参数配置），
    private int SendVoiceValidationCode_count = 0;//N=120秒，N系统后台配置
    private boolean isSendVoiceValidationCode = false; //是否发送成功验证码
    private RelativeLayout rl_layout_dx;
    
    class TimeCount extends CountDownTimer
    {
        public TimeCount(long millisInFuture, long countDownInterval)
        {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish()
        {
            tv_gety_yzm.setText("重新验证");
            tv_gety_yzm.setClickable(true);
            
            if(isSendVoiceValidationCode){
                rl_layout_dx.setVisibility(View.VISIBLE);
            }else{
                rl_layout_dx.setVisibility(View.GONE);
            }
        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            tv_gety_yzm.setClickable(false);
            tv_gety_yzm.setText("(" + millisUntilFinished / 1000 + "s)" + "重新验证");
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser_register);
        baseService = new BaseService();
        mContext = NewUserRegisterActivity.this;
        ActivityManager.getInstance().pushActivity(NewUserRegisterActivity.this);
        initTitle();
        initView();
        initData();
    }

    private void initTitle()
    {
        tv_title = (TextView) findViewById(R.id.title_content_tv);
        tv_title.setText("注册");
        iv_right_falg = (ImageView) findViewById(R.id.title_right);
        iv_right_falg.setVisibility(View.GONE);
    }

    private void initView()
    {
        et_phone = (LineEditText) findViewById(R.id.et_phone);
        et_phones_yzm = (EditText) findViewById(R.id.et_phones_yzm);
        et_passwrod = (LineEditText) findViewById(R.id.et_passwrod);
        et_phones_yqm = (LineEditText) findViewById(R.id.et_phones_yqm);

        tv_gety_yzm = (TextView) findViewById(R.id.tv_gety_yzm);
        tv_gety_yzm.setOnClickListener(this);

        ib_mw_pass = (ImageButton) findViewById(R.id.ib_mw_pass);
        ib_mw_pass.setOnClickListener(this);

        tv_reg_xy = (TextView) findViewById(R.id.tv_reg_xy);
        tv_reg_xy.setOnClickListener(this);

        checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        bn_finish = (Button) findViewById(R.id.bn_finish);
        bn_finish.setOnClickListener(this);
        
        bn_luang_yzm = (Button) findViewById(R.id.bn_luang_yzm);
        bn_luang_yzm.setOnClickListener(this);
        rl_layout_dx = (RelativeLayout) findViewById(R.id.rl_layout_dx);
        rl_layout_dx.setVisibility(View.GONE);
    }

    private void initData()
    {
        loginService =new LoginService();
        tv_reg_xy.setText("《e生财富注册协议》");
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象 60秒

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

        
        et_phone.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (et_phone.getText().toString().trim().length()>0&& et_passwrod.getText().toString().trim().length()>0
                        && et_phones_yzm.getText().toString().trim().length()>0)
                    {
                        bn_finish.setEnabled(true);
                    }
                    else
                    {
                        bn_finish.setEnabled(false);
                    }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
        et_passwrod.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (et_phone.getText().toString().trim().length()>0&& et_passwrod.getText().toString().trim().length()>0
                        && et_phones_yzm.getText().toString().trim().length()>0)
                    {
                        bn_finish.setEnabled(true);
                    }
                    else
                    {
                        bn_finish.setEnabled(false);
                    }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
        et_phones_yzm.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (et_phone.getText().toString().trim().length()>0&& et_passwrod.getText().toString().trim().length()>0
                        && et_phones_yzm.getText().toString().trim().length()>0)
                    {
                        bn_finish.setEnabled(true);
                    }
                    else
                    {
                        bn_finish.setEnabled(false);
                    }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
        
        if(getIntent() != null){
            finish_getsture = (int) getIntent().getExtras().get(Constants.EP2P);
            Logs.d("登录页面跳转过来：55="+finish_getsture);
        }
    }

    //注册接口   #TODO 接口少一个验证码的入参 
    private void getNewUserRegisterData(String phoneNo, String password,
            String phoneCode, String referralCode)
    {
        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("phoneNo", phoneNo);
        params.put("password",password); //#TODO 密码md5加密传入服务器
        params.put("referralCode", referralCode);
        params.put("phoneCode", phoneCode);
        connection(registerService.getStringRequest(1, URL.URL_REGISTER, params,this));
    }

    //注册发送短信
    private void getSendSmsCodeData(String mobile)
    {
//        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", mobile);
        connection(registerService.getStringRequest(2, URL.URL_SEND_SMS_CODE, params,this));
    }
    private void doSendVoiceValidationCode(String mobile){
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("mobile", mobile);
        connection(baseService.getStringRequest(5, URL.MINE_GETVOICE_CODE, params,this));
    }
    
    private int finish_getsture = 10; //完成手势密码[新手专享页面入口]
    private LoginService loginService;
    @Override
    public void onResponse(int tag, Object values)
    {
        super.onResponse(tag, values);
        switch (tag)
        {
            case 1 :
                RefreshDialog.stopProgressDialog();
                RegisteredResponse lRegisteredResponse = (RegisteredResponse) StringRequest2
                        .Json2Object(values.toString(),
                            new TypeToken<RegisteredResponse>()
                            {
                            });
                
                if(lRegisteredResponse.isStatus()){
                    if(!TextUtils.isEmpty(lRegisteredResponse.result.registeredSuccess)){
                        CommonUtils.addConfigInfo(this,Constants.KEY_RED_PACT_USER,lRegisteredResponse.result.registeredSuccess);
                    }
                    
//                  MainHolder.Instance(mContext).setRegistered(false);
                    CommonUtils.addConfigInfo(mContext, Constants.IS_REGISTER_CHECK, false);
                    
                    //注册成功--自动登录
                    LoginRequest loginRequestModel=new LoginRequest();
                    loginRequestModel.setLoginName(et_phone.getText().toString().trim());
                    loginRequestModel.setPassword(et_passwrod.getText().toString().trim());
                    loginRequestModel.setLoginFlag("no");
                    connection(loginService.login(3, loginRequestModel, this));
                }
                break;
            case 2 :
                RefreshDialog.stopProgressDialog();
                isSendVoiceValidationCode = true;
                break;
            case 3 :
                LoginResponse loginResponse = gson.fromJson(values.toString(), LoginResponse.class);
                CommonUtils.addConfigInfo(this,Constants.EP2P_TOKEN, loginResponse.getResult().getToken());
                connection(new SecurityCenterService().getPersonalInfo(4, this,this));
                break;
            case 4 :
                PersonalInfoResponse response=gson.fromJson(values.toString(),PersonalInfoResponse.class);
                CommonUtils.setUserInfo(response);
                
                //手势输入密码
                Intent intent = new Intent(mContext, GesturePasswordActivity.class);
                intent.putExtra(Constants.EP2P, finish_getsture);
                pushActivity(intent);
                break;
            case 5 :  //发送语音验证码
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
        switch (tag)
        {
            case 2 :
                isSendVoiceValidationCode = false;
                if(error.equals("该手机号码已经注册")){
                    if(time!=null){
                        time.onFinish();
                        time.cancel();
                    }
                }
                break;
            default :
                break;
        }
    }

    @Override
    public void onClick(View arg0)
    {
        Intent intent = null;
        switch (arg0.getId())
        {
            case R.id.ib_mw_pass :
                if (isMingPass)
                {
                    //设置为明文显示
                    ib_mw_pass.setImageDrawable(getResources().getDrawable(R.drawable.password_on_icon));
                    et_passwrod.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isMingPass = false;
                }
                else
                {
                    //设置为密文显示
                    ib_mw_pass.setImageDrawable(getResources().getDrawable(R.drawable.password_off_icon));
                    et_passwrod.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isMingPass = true;
                }
                setEditTextCursorLocation(et_passwrod);
                break;
            case R.id.tv_gety_yzm ://"验证码"
                SendVoiceValidationCode_count++;
                if(SendVoiceValidationCode_count >= 5){
                    CommonUtils.toastMsgShort(mContext, "每日获取验证码已超过上限!");
                    return;
                }
                
                if (!TextUtils.isEmpty(et_phone.getText().toString().trim()))
                {
                    if (StringUtils.isPhone(et_phone.getText().toString().trim()))
                    {
                        time.start();//开始计时

                        getSendSmsCodeData(et_phone.getText().toString().trim());
                    }
                    else
                    {
                        CommonUtils.toastMsgShort(mContext, "手机号不符合规范!");
                    }

                }
                else
                {
                    CommonUtils.toastMsgShort(mContext, "手机号不能为空!");
                }
                break;
            case R.id.bn_finish :
                if (TextUtils.isEmpty(et_phone.getText().toString().trim()))
                {
                    CommonUtils.toastMsgShort(mContext, "手机号不能为空!");
                    return;
                }
                if (!StringUtils.isPhone(et_phone.getText().toString().trim()))
                {
                    CommonUtils.toastMsgShort(mContext, "手机号不符合规范!");
                    return;
                }
                if (TextUtils.isEmpty(et_passwrod.getText().toString().trim()))
                {
                    CommonUtils.toastMsgShort(mContext, "密码不能为空!");
                    return;
                }
//                格式不正确：“登录密码为 6 - 32 位，至少包含一位字母且不能与用户名一样”。
//                登录密码只能为 6 - 32 位，至少包含一位字母且不能与用户名一样。
//                用户注册和用户修改登录密码时要根据以上规则验证。
                if(!isPasswordNo(et_passwrod.getText().toString().trim())){
                    CommonUtils.toastMsgShort(mContext, "登录密码为6-32位,至少包含一位字母!");
                    return;
                }
                if (TextUtils.isEmpty(et_phones_yzm.getText().toString().trim()))
                {
                    CommonUtils.toastMsgShort(mContext, "手机验证码不能为空!");
                    return;
                }

                if (!isChecked_xy)
                {
                    CommonUtils.toastMsgShort(mContext, "您确定阅读了注册协议了吗?");
                    return;
                }
                
                if(!TextUtils.isEmpty(et_phones_yqm.getText().toString().trim())){
                    
                    if(!isInviteCheck(et_phones_yqm.getText().toString().trim())){
                        CommonUtils.toastMsgShort(mContext, "邀请码:4位数字和大写字母开头的组合");
                        return;
                    }
                }
                
                getNewUserRegisterData(et_phone.getText().toString().trim(), et_passwrod
                        .getText().toString().trim(), et_phones_yzm.getText().toString()
                        .trim(), StringUtils.isTestNull(et_phones_yqm.getText().toString().trim()));
                break;
            case R.id.tv_reg_xy :
//                CommonUtils.toastMsgShort(mContext, "生财富注册协议");
                intent = new Intent(mContext, EServicerAgreementActivity.class);
                intent.putExtra(Constants.AGREEMENT_TYPE, 1);
                pushActivity(intent);
                break;
            case R.id.bn_luang_yzm :
                if (!TextUtils.isEmpty(et_phone.getText().toString().trim()))
                {
                    if (StringUtils.isPhone(et_phone.getText().toString().trim()))
                    {
                        doSendVoiceValidationCode(et_phone.getText().toString().trim());
                    }
                    else
                    {
                        CommonUtils.toastMsgShort(mContext, "手机号不符合规范!");
                    }
                }
                else
                {
                    CommonUtils.toastMsgShort(mContext, "手机号不能为空!");
                }
                break;
            default :
                break;
        }
    }
    // 验证密码
    private  boolean isPasswordNo(String password) {
        String pwd = "(?![^a-zA-Z0-9]+$)(?![^a-zA-Z/D]+$).{6,32}$";
        if (TextUtils.isEmpty(password)) {
            return false;
        } else
            return password.matches(pwd);
    }
    
    // 将EditText的光标定位到字符的最后面
    public void setEditTextCursorLocation(EditText editText) {
        CharSequence text = editText.getText();
        if(text instanceof Spannable){
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }
    }
    
    // ^[A-Z][0-9]{4}$   4位数字和大写字母的组合的正则表达式[第一个是大写字母]
    private  boolean isInviteCheck(String text) {
        Pattern pattern = Pattern.compile("^[A-Z][0-9]{4}$");//邀请
        Matcher m = pattern.matcher(text.trim());
        if (m.matches()) {
            return true;
        }
        return false;
    }
}
