package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.beyondsoft.ep2p.ActivityManager;
import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.StringUtils;

/**
 * @author Ivan.Lu
 * @description 验证原手机绑定
 */
public class PhoneBoundAuthActivity extends BaseFragmentActivity implements OnClickListener {
	
	private static final int GETSMSCODE=1;
	private static final int GETVOICECODE=2;
	private static final int VALIDATECODE=3;
	private EditText sms_code_et;
	private TextView get_sms_code_btn;
	private TextView phone_tv;
	private Button next_btn;
	private Button voice_sms_btn;
	private Timer timer;
	private int timeCount=60;
	private int mType;
	private SecurityCenterService securityCenterService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_bound_auth);
		securityCenterService=new SecurityCenterService();
		ActivityManager activityManager=ActivityManager.getInstance();
		activityManager.pushActivity(this);
		init();
		initListener();
		initData();
	}
	
	private void init(){
		phone_tv=(TextView) findViewById(R.id.phone_tv);
		sms_code_et=(EditText) findViewById(R.id.sms_code_et);
		get_sms_code_btn=(TextView) findViewById(R.id.get_sms_code_btn);
		next_btn=(Button) findViewById(R.id.next_btn);
		voice_sms_btn=(Button) findViewById(R.id.voice_sms_btn);
	}
	
	private void initListener(){
		((Button)findViewById(R.id.next_btn)).setOnClickListener(this);
		get_sms_code_btn.setOnClickListener(this);
		((TextView)findViewById(R.id.get_sms_code_btn)).setOnClickListener(this);
		next_btn.setOnClickListener(this);
		voice_sms_btn.setOnClickListener(this);
		sms_code_et.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if(s.length()>0){
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
		mType=getIntent().getIntExtra("type",-1);
		switch (mType) {
		case 0:
			setTitle(getResources().getString(R.string.security_center_phone_auth));			
			break;
		case 1://重置登陆密码
			setTitle(getResources().getString(R.string.security_center_reset_login_pwd));			
			break;
		case 2://重置交易密码
			setTitle(getResources().getString(R.string.security_center_reset_pay_auth));
			break;
		}
		phone_tv.setText(StringUtils.getShowPhoneNum(UserPersonalInfo.getPhoneNo()));
		phone_tv.setTag(UserPersonalInfo.getPhoneNo());
	}

	@SuppressLint("HandlerLeak")
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 0:
				get_sms_code_btn.setEnabled(false);
				get_sms_code_btn.setTextColor(getResources().getColor(R.color.text_grey_color));
				get_sms_code_btn.setText("("+msg.arg1+"s)"+getResources().getText(R.string.security_center_get_sms_reload));
				break;
			case 1:
				if(timer!=null){
					timer.cancel();
					timer=null;
					timeCount=60;				
				}
				get_sms_code_btn.setText(getResources().getText(R.string.txt_input_phone_hint3));
				get_sms_code_btn.setTextColor(getResources().getColor(R.color.e_main_bg_title));
				get_sms_code_btn.setEnabled(true);
				voice_sms_btn.setEnabled(true);
			}
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.get_sms_code_btn:
			voice_sms_btn.setEnabled(false);
			HashMap<String,Object> hashMap=new HashMap<String, Object>();
			hashMap.put("mobile",phone_tv.getTag().toString());
			if(mType==0){
				connection(securityCenterService.getBoundPhoneSMSCode(GETSMSCODE, hashMap, this));
			}else if(mType==2){
				connection(securityCenterService.getResetPayPasswordCode(GETSMSCODE, hashMap, this));
			}else{
				connection(securityCenterService.getResetLoginPasswordCode(GETSMSCODE, hashMap, this));
			}
			showTime();
			break;
		case R.id.next_btn:
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("mobile", phone_tv.getTag().toString());
			params.put("verifyCode", sms_code_et.getText().toString().trim());
			connection(securityCenterService.validteSMSCode(VALIDATECODE, params, this));
			break;
		case R.id.voice_sms_btn:
			voice_sms_btn.setEnabled(false);
			HashMap<String,Object> voiceMap=new HashMap<String, Object>();
			voiceMap.put("mobile", phone_tv.getTag().toString());
			connection(securityCenterService.getVoiceCode(GETVOICECODE, voiceMap, this));
			showTime();
			break;
		}
	}
	
	private void showTime(){
		if(timer==null){
			timer=new Timer();
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(timeCount<2){
						handler.sendEmptyMessage(1);
					}else{
						handler.obtainMessage(0, --timeCount, 0).sendToTarget();					
					}
				}
			}, 0, 1000);
		}
	}

	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		switch (tag) {
		case GETSMSCODE:
			
			break;
		case GETVOICECODE:
			
			break;
		case VALIDATECODE:
			switch (mType) {
			case 0:
				startActivity(new Intent(this,PhoneBoundActivity.class));
				break;
			case 1:
				startActivity(new Intent(this,ResetLoginPwdActivity.class));
				break;
			case 2:
				 startActivity(new Intent(PhoneBoundAuthActivity.this,ModifyPayPwdActivity.class).putExtra("ModifyPay_type", 2));
				break;
			}
			break;
		}
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(timer!=null){
			timer.cancel();
			timer=null;			
		}
	}
}
