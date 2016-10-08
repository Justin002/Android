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

import com.beyondsoft.ep2p.ActivityManager;
import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.LoginActivity;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.view.gesture.LockUtil;

/**
 * @author Ivan.Lu
 * @description 设置新密码
 */
public class ResetLoginPwdActivity extends BaseFragmentActivity implements OnClickListener {
	
	private static final int RESET_LOGIN_PWD=1;
	private Button ok_btn;
	private EditText new_pwd_et;
	private EditText again_new_pwd_et;
	private SecurityCenterService securityCenterService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_login_pwd);
		securityCenterService=new SecurityCenterService();
		init();
		initListener();
	}
	
	private void init(){
		setTitle(getResources().getString(R.string.security_center_setting_new_pwd));
		new_pwd_et=(EditText) findViewById(R.id.new_pwd_et);
		again_new_pwd_et=(EditText) findViewById(R.id.again_new_pwd_et);
		ok_btn=(Button) findViewById(R.id.ok_btn);
		
	}
	
	private void initListener(){
		((Button)findViewById(R.id.ok_btn)).setOnClickListener(this);
		new_pwd_et.addTextChangedListener(textWatcher);
		again_new_pwd_et.addTextChangedListener(textWatcher);
	}
	
	TextWatcher textWatcher=new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			if(isEnable()){
				ok_btn.setEnabled(true);
			}else{
				ok_btn.setEnabled(false);
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
	
	private boolean isEnable(){
		if(new_pwd_et.getText().toString().trim().length()>0&&
		   again_new_pwd_et.getText().toString().trim().length()>0){
			return true;
		}	
		return false;
	}
	
	private boolean isValidate(){
		if(new_pwd_et.getText().toString().trim().equals(again_new_pwd_et.getText().toString().trim())){
			return true;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ok_btn:
			if(isValidate()){
				HashMap<String,Object> hashMap=new HashMap<String, Object>();
				hashMap.put("newPassWord",new_pwd_et.getText().toString().trim());
				connection(securityCenterService.resetLoginPassword(RESET_LOGIN_PWD, hashMap, this));			
			}else{
				CommonUtils.toastMsgShort(this,getResources().getString(R.string.error_reset_login_pwd));
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
		CommonUtils.toastMsgShortForStyle(this, getResources().getString(R.string.security_center_reset_login_pwd_success),dip2px(-75));
//		ActivityManager activityManager=ActivityManager.getInstance();
//		activityManager.popAllActivity();
		pushActivity(new Intent(this,LoginActivity.class).putExtra("openType","login_out"));
		finish();
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
	}
}
