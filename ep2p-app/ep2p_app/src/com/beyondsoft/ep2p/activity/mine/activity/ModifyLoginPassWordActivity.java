package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.HashMap;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.beyondsoft.ep2p.ActivityManager;
import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.view.gesture.LockUtil;

/**
 * @author Ivan.Lu
 * @description 修改登录密码,从登陆过来的
 */
public class ModifyLoginPassWordActivity extends BaseFragmentActivity implements OnClickListener{
	
	private static final int RESET_LOGIN_PWD=1;
	private Button modify_ok;
	private EditText new_pwd_et;
	private EditText again_new_pwd_et;
	private SecurityCenterService securityCenterService;
	private String telPhone;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_login_pwd);
		securityCenterService=new SecurityCenterService();
		init();
		initListener();
	}
	
	private void init(){
		telPhone=getIntent().getStringExtra("tel_phone");
		setTitle(getResources().getString(R.string.security_center_reset_login_pwd));
		modify_ok=(Button) findViewById(R.id.modify_ok_password);
		new_pwd_et=(EditText) findViewById(R.id.new_password_et);
		again_new_pwd_et=(EditText) findViewById(R.id.again_new_password_et);
		((Button)findViewById(R.id.modify_ok_password)).setOnClickListener(this);
	}
	
	private void initListener(){
		modify_ok.setOnClickListener(this);
		new_pwd_et.addTextChangedListener(textWatcher);
		again_new_pwd_et.addTextChangedListener(textWatcher);
	}
	
	TextWatcher textWatcher=new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			if(isEnable()){
				modify_ok.setEnabled(true);
			}else{
				modify_ok.setEnabled(false);
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
	};
	
	private boolean isEnable() {
		if (new_pwd_et.getText().toString().trim().length() > 0&& again_new_pwd_et.getText().toString().trim().length() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.modify_ok_password:
			if(isValidate()){
				HashMap<String,Object> hashMap=new HashMap<String, Object>();
				hashMap.put("loginName",telPhone);
				hashMap.put("password",new_pwd_et.getText().toString().trim());
				connection(securityCenterService.resetForgetLoginPassword(RESET_LOGIN_PWD, hashMap, this));					
			}
			break;
		}
	}
	
	private boolean isValidate(){
		if(StringUtils.isChinese(new_pwd_et.getText().toString().trim())){	
			CommonUtils.toastMsgShort(this,getResources().getString(R.string.error_input_hint3));
		}else {
			if(!StringUtils.isPwd(new_pwd_et.getText().toString().trim())){
				CommonUtils.toastMsgShort(this,getResources().getString(R.string.error_input_hint4));
			}else{
				if(new_pwd_et.getText().toString().trim().equals(again_new_pwd_et.getText().toString().trim())){
					return true;
				}else{
					CommonUtils.toastMsgShort(this,getResources().getString(R.string.error_reset_login_pwd));
				}
			}
		}
		return false;
	}

	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		switch (tag) {
		case RESET_LOGIN_PWD:
			LockUtil.clearPwd(this);
			LockUtil.setPwdStatus(this,false);
			CommonUtils.deleteConfigInfo(this, Constants.EP2P_TOKEN);
			CommonUtils.toastMsgShortForStyle(this, getResources().getString(R.string.security_center_modify_pwd_success), dip2px(-75));
			ActivityManager activityManager=ActivityManager.getInstance();
			activityManager.popAllActivity();
			finish();
			break;
		}
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
	}
}
