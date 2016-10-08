package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.beyondsoft.ep2p.ActivityManager;
import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.view.password.GridPasswordView;
import com.beyondsoft.ep2p.view.password.GridPasswordView.OnPasswordChangedListener;

/**
 * @author Ivan.Lu
 * @description 设置(修改)交易密码
 */
public class ModifyPayPwdActivity extends BaseFragmentActivity implements OnPasswordChangedListener {
	
	private static final int RESETPAYPASSWORD=1;
	private static final int UPDATEPAYPASSWORD=2;
	private static final int VALIDATEPAYPASSWORD=3;
	private GridPasswordView gpv_normal;
	private TextView pay_password_hint_tv;
	private SecurityCenterService securityCenterService;
	/**默认:1:修改交易密码    2:设置密码**/
	private int mType;
	/**原交易密码是否验证完成**/
	private boolean isValidatePwd;
	/**首次交易密码**/
	private String mNewPayPassword;
	/**确认交易密码**/
	private String mNewPayPassword_again;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_pay_pwd);
		init();
		initListener();
		initData();
	}
	
	private void init(){
		securityCenterService=new SecurityCenterService();
		mType=getIntent().getIntExtra("ModifyPay_type", -1);
		setTitle(getResources().getString(R.string.security_center_setting_pay_pwd));
		gpv_normal=(GridPasswordView) findViewById(R.id.gpv_normal);
		pay_password_hint_tv=(TextView) findViewById(R.id.pay_password_hint_tv);
		if(mType==2){
			pay_password_hint_tv.setText(getResources().getString(R.string.security_center_origin_pay_auth));
		}
		showKeyBoard();
	}
	
	private void initListener(){
		gpv_normal.setOnPasswordChangedListener(this);
	}
	
	private void initData(){
		
	}
	
	private void showKeyBoard(){
		Timer timer=new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
				inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		},300);
	}

	@Override
	public void onChanged(final String psw) {
		// TODO Auto-generated method stub		
		 if (psw.length()==6){
			 Log.e("debug", psw);
			 new Handler().postDelayed(new Runnable() {		
				@Override
				public void run() {
					// TODO Auto-generated method stub
					switch (mType) {
					case 1:
						if(!isValidatePwd){
							HashMap<String,Object> hashMap=new HashMap<String, Object>();
							hashMap.put("tradPassWord",psw);
							connection(securityCenterService.validatePayPassword(VALIDATEPAYPASSWORD, hashMap, ModifyPayPwdActivity.this));
						}else{
							if(mNewPayPassword==null){
								mNewPayPassword=psw;
								pay_password_hint_tv.setText(getResources().getString(R.string.security_center_origin_pay_auth_again_again));								
								gpv_normal.clearPassword();
							}else{
								mNewPayPassword_again=psw;
								if(mNewPayPassword.equals(mNewPayPassword_again)){
									HashMap<String,Object> hashMap=new HashMap<String, Object>();
									hashMap.put("tradPassWord",psw);
									connection(securityCenterService.updatePayPassword(UPDATEPAYPASSWORD, hashMap, ModifyPayPwdActivity.this));
								}else{
									mNewPayPassword=null;
									mNewPayPassword_again=null;
									gpv_normal.clearPassword();
									pay_password_hint_tv.setText(getResources().getString(R.string.security_center_origin_pay_auth_again));									
									CommonUtils.toastMsgShort(ModifyPayPwdActivity.this, "两次密码输入不一致，请重新输入新的交易密码!");
								}
							}
						}
						break;
					case 2:
						if(mNewPayPassword==null){
							mNewPayPassword=psw;
							pay_password_hint_tv.setText(getResources().getString(R.string.security_center_origin_pay_auth_again_again2));
							gpv_normal.clearPassword();						
						}else{
							mNewPayPassword_again=psw;
							if(mNewPayPassword.equals(mNewPayPassword_again)){
								HashMap<String,Object> hashMap=new HashMap<String, Object>();
								hashMap.put("tradPassWord",psw);
								connection(securityCenterService.updatePayPassword(UPDATEPAYPASSWORD, hashMap, ModifyPayPwdActivity.this));
							}else{
								mNewPayPassword=null;
								mNewPayPassword_again=null;
								gpv_normal.clearPassword();
								pay_password_hint_tv.setText(getResources().getString(R.string.security_center_origin_pay_auth));
								CommonUtils.toastMsgShort(ModifyPayPwdActivity.this, "两次密码输入不一致，请重新输入新的交易密码!");
							}
						}
						break;
					default:
						break;
					}
				}
			}, 300);
         }
	}

	@Override
	public void onMaxLength(String psw) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		switch (tag) {
		case VALIDATEPAYPASSWORD:
			pay_password_hint_tv.setText(getResources().getString(R.string.security_center_origin_pay_auth_again));
			gpv_normal.clearPassword();	
			isValidatePwd=true;
			break;
		case RESETPAYPASSWORD:
			
			break;
		case UPDATEPAYPASSWORD:
			UserPersonalInfo.setIsSetTradePwd("1");
			if(mType==2){
				CommonUtils.toastMsgShortForStyle(ModifyPayPwdActivity.this,getResources().getString(R.string.security_center_modify_set_success), dip2px(-55));
			}else{
				CommonUtils.toastMsgShortForStyle(ModifyPayPwdActivity.this,getResources().getString(R.string.security_center_modify_pwd_success), dip2px(-55));
			}
			ActivityManager activityManager=ActivityManager.getInstance();
			activityManager.popAllActivity();
			finish();
			break;
		}
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		//super.onErrorResponse(tag, error);
		switch (tag) {
		case VALIDATEPAYPASSWORD:
			CommonUtils.toastMsgShort(this,"交易密码错误!");
			gpv_normal.clearPassword();	
			break;
		}
	}
}
