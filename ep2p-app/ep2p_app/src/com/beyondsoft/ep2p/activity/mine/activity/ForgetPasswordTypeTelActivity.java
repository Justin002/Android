package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyondsoft.ep2p.ActivityManager;
import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.view.password.GridPasswordConfigView;
import com.beyondsoft.ep2p.view.password.GridPasswordConfigView.OnPasswordChangedListener;


/**
 * @author Ivan.Lu
 * @description 忘记密码
 */
public class ForgetPasswordTypeTelActivity extends BaseFragmentActivity
		implements OnClickListener, OnPasswordChangedListener {

	private static final int GETFORGETSMSCODE=1;
	private static final int VALIDATECODE=2;
	private static final int GETVOICECODE=3;
	private Button bt_press_message_next;
	private Button voice_sms_btn;
	private GridPasswordConfigView gp_yazm;
	private TextView tv_pass_message;
	private TextView telphone_tv;
	private Timer timer;
	private int timeCount = 60;
	private RelativeLayout rl_foget_pw;
	private SecurityCenterService securityCenterService;
	private String mSmsCode;
	/**0:短信验证    1:语音验证**/
	private int validateType=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password_type_yanzm);
		securityCenterService=new SecurityCenterService();
		ActivityManager activityManager = ActivityManager.getInstance();
		activityManager.pushActivity(this);
		init();
		initListener();
		initData();

	}
	
	private void init() {
		setTitle(getResources().getString(R.string.login_forget));
		bt_press_message_next = (Button) findViewById(R.id.bt_press_message_next);
		voice_sms_btn=(Button) findViewById(R.id.voice_sms_btn);
		gp_yazm = (GridPasswordConfigView) findViewById(R.id.gp_yazm);
		telphone_tv = (TextView) findViewById(R.id.telphone_tv);
		tv_pass_message = (TextView) findViewById(R.id.tv_pass_message);
		rl_foget_pw = (RelativeLayout) findViewById(R.id.rl_foget_pw);
		showKeyBoard();
	}

	private void initListener() {
		bt_press_message_next.setOnClickListener(this);
		gp_yazm.setOnPasswordChangedListener(this);
		tv_pass_message.setOnClickListener(this);
		voice_sms_btn.setOnClickListener(this);
	}

	private void initData() {
		String num=getIntent().getStringExtra("phoneNum");
		telphone_tv.setTag(num);
		telphone_tv.setText(StringUtils.getShowPhoneNum(num));
		HashMap<String,Object> hashMap=new HashMap<String, Object>();
		hashMap.put("mobile", telphone_tv.getTag().toString());
		connection(securityCenterService.getForgetPasswordCode(GETFORGETSMSCODE, hashMap, this));
	}
	
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 0:
				if(validateType==0){
					tv_pass_message.setText(msg.arg1+ "s"+ getResources().getText(R.string.security_center_get_sms_reload_again));				
				}else{
					tv_pass_message.setText(msg.arg1+ "s"+ getResources().getText(R.string.security_center_get_sms_reload_again2));
				}
				break;
			case 1:
				if (timer != null) {
					timer.cancel();
					timer = null;
					timeCount=60;
				}
				tv_pass_message.setText(getResources().getText(
						R.string.again_pass_message));
				tv_pass_message.setTextColor(getResources().getColor(
						R.color.e_main_bg_title));
				tv_pass_message.setEnabled(true);
				voice_sms_btn.setEnabled(true);
			}
		}

	};

	private void showKeyBoard() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				inputMethodManager.toggleSoftInput(0,
						InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}, 300);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_press_message_next:
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("mobile", telphone_tv.getTag().toString());
			params.put("verifyCode", mSmsCode);
			connection(securityCenterService.validteSMSCode(VALIDATECODE, params, this));
			break;
		case R.id.tv_pass_message:
			validateType=0;
			rl_foget_pw.setVisibility(View.VISIBLE);
			tv_pass_message.setTextColor(getResources().getColor(R.color.e_text_bg_huise_black));
			tv_pass_message.setEnabled(false);
			voice_sms_btn.setEnabled(false);
			HashMap<String,Object> hashMap=new HashMap<String, Object>();
			hashMap.put("mobile", telphone_tv.getTag().toString());
			connection(securityCenterService.getForgetPasswordCode(GETFORGETSMSCODE, hashMap, this));
			showTime();
			break;
			
		case R.id.voice_sms_btn:
			validateType=1;
			tv_pass_message.setTextColor(getResources().getColor(R.color.e_text_bg_huise_black));
			voice_sms_btn.setEnabled(false);
			HashMap<String,Object> voiceMap=new HashMap<String, Object>();
			voiceMap.put("mobile", telphone_tv.getTag().toString());
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
	public void onChanged(String psw) {
		if (psw.length() == 4) {
			bt_press_message_next.setEnabled(true);
			mSmsCode=psw;
		} else {
			bt_press_message_next.setEnabled(false);
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
		case GETFORGETSMSCODE:
			
			break;
		case VALIDATECODE:
			pushActivity(new Intent(this,ModifyLoginPassWordActivity.class).putExtra("tel_phone", telphone_tv.getTag().toString()));
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
