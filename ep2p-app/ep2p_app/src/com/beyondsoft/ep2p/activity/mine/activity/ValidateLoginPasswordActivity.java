package com.beyondsoft.ep2p.activity.mine.activity;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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

import com.beyondsoft.ep2p.ActivityManager;
import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.StringUtils;

/**
 * @author Ivan.Lu
 * @description 登录密码验证
 */
public class ValidateLoginPasswordActivity extends BaseFragmentActivity implements OnClickListener{
	
	private static final int LOGINPASSWORD=1;
	private EditText login_password_et;
	private CheckBox password_switch_cb;
	private Button next_btn;
	private SecurityCenterService securityCenterService;
	private int mSetType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_validate_login_password);
		ActivityManager activityManager=ActivityManager.getInstance();
		activityManager.pushActivity(this);
		securityCenterService=new SecurityCenterService();
		init();
		initListener();
		initData();
	}
	
	private void init(){
		setTitle(getResources().getString(R.string.txt_login_verification));
		login_password_et=(EditText) findViewById(R.id.login_password_et);
		password_switch_cb=(CheckBox) findViewById(R.id.password_switch_cb);
		next_btn=(Button) findViewById(R.id.next_btn);
	}
	
	private void initListener(){
		next_btn.setOnClickListener(this);
		password_switch_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					login_password_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				} else {
					login_password_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
				}
			}
		});
		login_password_et.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if(login_password_et.length()>0){
					next_btn.setEnabled(true);
				}else{
					next_btn.setEnabled(false);
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
	}
	
	private void initData(){
		mSetType=getIntent().getIntExtra("setType", 0);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.next_btn:
			HashMap<String,Object> hashMap=new HashMap<String, Object>();
			hashMap.put("passWord",login_password_et.getText().toString());
			connection(securityCenterService.validateLoginPassword(LOGINPASSWORD,hashMap, this));
			break;
		}
	}

	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		if(mSetType==0){
			pushActivity(new Intent(ValidateLoginPasswordActivity.this,ModifyPayPwdActivity.class).putExtra("ModifyPay_type", 2));
		}else if(mSetType==1){
			pushActivity(new Intent(ValidateLoginPasswordActivity.this,PhoneBoundAuthActivity.class).putExtra("type", 2));			
		}else{// mSetType =2  就是设置交易密码
		    pushActivity(new Intent(ValidateLoginPasswordActivity.this,ModifyPayPwdActivity.class).putExtra("ModifyPay_type", 2));//ModifyPay_type=2设置交易密码
		}
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
	}
}
