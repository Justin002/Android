package com.beyondsoft.ep2p.activity.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;

/**
 * @author Ivan.Lu
 * @description 重置登录(交易)密码(验证)
 */
public class ResetLoginPwdAuthActivity extends BaseFragmentActivity implements OnClickListener {
	
	private int mResetType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_login_auth_pwd);
		init();
	}
	
	private void init(){
		mResetType=getIntent().getIntExtra("reset_type",-1);
		if(mResetType==0){
			setTitle(getResources().getString(R.string.security_center_reset_login_pwd));		
		}else{
			setTitle(getResources().getString(R.string.security_center_reset_pay_pwd));	
		}
		((Button)findViewById(R.id.next_btn)).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.next_btn:
			if(mResetType==0){
				startActivity(new Intent(this,ResetLoginPwdActivity.class));
			}else{
				startActivity(new Intent(this,ModifyPayPwdActivity.class));
			}
			
			break;
		}
	}
}
