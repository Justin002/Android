package com.beyondsoft.ep2p.activity;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.home.NewUserRegisterActivity;
import com.beyondsoft.ep2p.activity.mine.activity.ForgetPasswordActivity;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.request.LoginRequest;
import com.beyondsoft.ep2p.model.response.LoginResponse;
import com.beyondsoft.ep2p.model.response.PersonalInfoResponse;
import com.beyondsoft.ep2p.service.LoginService;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.view.LineEditText;

/**
 * @author Ivan.Lu
 * @description 登录
 * 
 */
public class LoginActivity extends BaseFragmentActivity implements OnClickListener
{
	private static final int LOGIN=1;
	private static final int GETPERSONALINFO=2;
	private LineEditText username_et;
	private LineEditText pwd_et;
	private TextView tv_reg_xy;
	private ImageView iv_username_delete;
	private ImageView iv_pwd_delete;
	private Button login_btn;
	private TextView forget_pwd_tv;
	private CheckBox password_switch_cb;
	private LoginService loginService;
	private CheckBox agrees_cb;
	private String mType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		loginService =new LoginService();
		mType=getIntent().getStringExtra("openType");
		if(savedInstanceState!=null){
			mType=savedInstanceState.getString("type");
		}
		init();
		initListener();
	}
	
	private void init(){
		setTitle(getResources().getString(R.string.login_ok));
		setRightTitle(getResources().getString(R.string.login_right_txt));
		agrees_cb=(CheckBox) findViewById(R.id.checkBox1);
		tv_reg_xy=(TextView) findViewById(R.id.tv_reg_xy);
		username_et=(LineEditText) findViewById(R.id.username_et);
		pwd_et=(LineEditText) findViewById(R.id.password_et);
		login_btn=(Button) findViewById(R.id.login_btn);
		forget_pwd_tv=(TextView) findViewById(R.id.forget_pwd_tv);
		password_switch_cb=(CheckBox) findViewById(R.id.password_switch_cb);
		iv_username_delete=(ImageView) findViewById(R.id.iv_username_delete);
		iv_pwd_delete=(ImageView) findViewById(R.id.iv_pwd_delete);
		username_et.setText(CommonUtils.getStringByKey(this,Constants.EP2P_LOGIN_NAME));
	}
	
	private void initListener(){
		login_btn.setOnClickListener(this);
		tv_reg_xy.setOnClickListener(this);
		forget_pwd_tv.setOnClickListener(this);
		iv_username_delete.setOnClickListener(this);
		iv_pwd_delete.setOnClickListener(this);
		((ImageView) findViewById(R.id.title_left_btn)).setOnClickListener(this);
		((TextView)findViewById(R.id.title_right)).setOnClickListener(this);
		password_switch_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					pwd_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				} else {
					pwd_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
				}
				setEditTextCursorLocation(pwd_et);
			}
		});
		username_et.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if(username_et.length()>0){
					iv_username_delete.setVisibility(View.VISIBLE);
					if(pwd_et.length()>0){
						login_btn.setEnabled(true);
					}else{
						login_btn.setEnabled(false);
					}
				}else{
					iv_username_delete.setVisibility(View.GONE);
					login_btn.setEnabled(false);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		pwd_et.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub		
				if(username_et.length()>0){
					if(pwd_et.length()>0){
						iv_pwd_delete.setVisibility(View.VISIBLE);
						login_btn.setEnabled(true);
					}else{
						iv_pwd_delete.setVisibility(View.GONE);
						login_btn.setEnabled(false);
					}
				}else{
					if(pwd_et.length()>0){
						iv_pwd_delete.setVisibility(View.VISIBLE);
					}else{
						iv_pwd_delete.setVisibility(View.GONE);
					}
					login_btn.setEnabled(false);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub			
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		pwd_et.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus){
					iv_username_delete.setVisibility(View.GONE);
				}
				if(pwd_et.length()>0){
					iv_pwd_delete.setVisibility(View.VISIBLE);	
				}
			}
		});
		username_et.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus){
					iv_pwd_delete.setVisibility(View.GONE);				
				}
				if(username_et.length()>0){
					iv_username_delete.setVisibility(View.VISIBLE);	
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.title_right:
		    Intent intent = new Intent(mContext, NewUserRegisterActivity.class);
            intent.putExtra(Constants.EP2P, 55);//标记是从登陆页面调整过来的
			startActivity(intent);
			break;
		case R.id.login_btn:
			if(agrees_cb.isChecked()){
				LoginRequest loginRequestModel=new LoginRequest();
				loginRequestModel.setLoginName(username_et.getText().toString().trim());
				loginRequestModel.setPassword(pwd_et.getText().toString().trim());//#TODO 登录密码MD5 加密
				loginRequestModel.setLoginFlag("no");
				connection(loginService.login(LOGIN, loginRequestModel, this));			
			}else{
				CommonUtils.toastMsgShort(this,getResources().getString(R.string.cerditor_rights_transf_agreement2));
			}
			break;
		case R.id.tv_reg_xy:
             pushActivity(new Intent(mContext, EServicerAgreementActivity.class).putExtra(Constants.AGREEMENT_TYPE, 1));
			break;
		case R.id.forget_pwd_tv:
			pushActivity(ForgetPasswordActivity.class);
			break;
		case R.id.title_left_btn:
			onBackPressed();
			break;
		case R.id.iv_username_delete:
			username_et.getText().clear();
			break;
		case R.id.iv_pwd_delete:
			pwd_et.getText().clear();
			break;
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString("type",mType);
	}

	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		switch (tag) {
		case LOGIN:
			LoginResponse loginResponse=gson.fromJson(values.toString(), LoginResponse.class);
			CommonUtils.addConfigInfo(this,Constants.EP2P_TOKEN, loginResponse.getResult().getToken());
			connection(new SecurityCenterService().getPersonalInfo(GETPERSONALINFO, this,this));
			break;
		case GETPERSONALINFO:
			PersonalInfoResponse response=gson.fromJson(values.toString(),PersonalInfoResponse.class);
			CommonUtils.addConfigInfo(this,Constants.EP2P_LOGIN_NAME, username_et.getText().toString().trim());
			CommonUtils.setUserInfo(response);
			if("login_out".equals(mType)){
				Intent intent=new Intent(this,MainActivity.class);
				intent.putExtra("openType", mType);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				pushActivity(intent);
				finish();
			}else{
				setResult(Activity.RESULT_OK);
				finish();
			}
			HashMap<String,String> hashMap=new HashMap<String, String>();
			hashMap.put("mobileDeviceMachineCode",CommonUtils.getStringByKey(this, Constants.EP2P_REGISTRATION_ID));
			connection(loginService.setRegistrationID(3, hashMap, this));
			break;
		}
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if("login_out".equals(mType)){
			Intent intent=new Intent(this,MainActivity.class);
			intent.putExtra("openType", mType);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			pushActivity(intent);
			finish();
		}
	}
	
    // 将EditText的光标定位到字符的最后面
    public void setEditTextCursorLocation(EditText editText) {
        CharSequence text = editText.getText();
        if(text instanceof Spannable){
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }
    }
}
