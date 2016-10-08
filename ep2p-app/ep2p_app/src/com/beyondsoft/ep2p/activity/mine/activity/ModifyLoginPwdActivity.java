package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.LoginActivity;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.view.gesture.LockUtil;

/**
 * @author Ivan.Lu
 * @description 修改登录密码
 */
public class ModifyLoginPwdActivity extends BaseFragmentActivity implements OnClickListener{
	
	private static final int UPDATE_LOGIN_PWD=1;
	private Button modify_ok;
	private EditText origin_pwd_et;
	private EditText new_pwd_et;
	private EditText again_new_pwd_et;
	private SecurityCenterService securityCenterService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_login_password);
		securityCenterService=new SecurityCenterService();
		init();
		initListener();
	}
	
	private void init(){
		setTitle(getResources().getString(R.string.security_center_modify_pwd));
		modify_ok=(Button) findViewById(R.id.modify_ok);
		origin_pwd_et=(EditText) findViewById(R.id.origin_pwd_et);
		new_pwd_et=(EditText) findViewById(R.id.new_pwd_et);
		again_new_pwd_et=(EditText) findViewById(R.id.again_new_pwd_et);
		((Button)findViewById(R.id.modify_ok)).setOnClickListener(this);
	}
	
	private void initListener(){
		modify_ok.setOnClickListener(this);
		origin_pwd_et.addTextChangedListener(textWatcher);
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
			
		}
	};
	
	private boolean isEnable(){
		if(origin_pwd_et.getText().toString().trim().length()>0&&
		   new_pwd_et.getText().toString().trim().length()>0&&
		   again_new_pwd_et.getText().toString().trim().length()>0){
			return true;
		}
		
		return false;
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.modify_ok:
			if(isValidate()){
				HashMap<String,Object> hashMap=new HashMap<String, Object>();
				hashMap.put("passWord",origin_pwd_et.getText().toString().trim());
				hashMap.put("newPassWord",new_pwd_et.getText().toString().trim());
				connection(securityCenterService.updateLoginPassword(UPDATE_LOGIN_PWD, hashMap, this));
			}
			break;
		}
	}

	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		LockUtil.clearPwd(this);
		LockUtil.setPwdStatus(this,false);
		CommonUtils.deleteConfigInfo(this, Constants.EP2P_TOKEN);
		CommonUtils.toastMsgShortForStyle(this, getResources().getString(R.string.security_center_modify_pwd_success), dip2px(-75));
		pushActivity(new Intent(this,LoginActivity.class).putExtra("openType","login_out"));
		finish();
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
	}
	
	
}
