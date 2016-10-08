package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
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
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.StringUtils;

/**
 * @author Ivan.Lu
 * @description 手机绑定
 */
public class PhoneBoundActivity extends BaseFragmentActivity implements
		OnClickListener {

	private static final int VALIDATESMSCODE = 1;
	private static final int GETPHONECODE = 2;
	private static final int BOUNDPHONE = 3;
	private EditText sms_code_et;
	private EditText phone_num_et;
	private TextView get_sms_code_btn;
	private Button submit_btn;
	private Timer timer;
	private int timeCount = 60;
	private SecurityCenterService securityCenterService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_bound);
		securityCenterService = new SecurityCenterService();
		init();
		initListener();
	}

	private void init() {
		setTitle(getResources()
				.getString(R.string.security_center_phone_bound2));
		submit_btn = (Button) findViewById(R.id.submit_btn);
		sms_code_et = (EditText) findViewById(R.id.sms_code_et);
		phone_num_et = (EditText) findViewById(R.id.phone_num_et);
		get_sms_code_btn = (TextView) findViewById(R.id.get_sms_code_btn);
	}

	private void initListener() {
		get_sms_code_btn.setOnClickListener(this);
		submit_btn.setOnClickListener(this);
		sms_code_et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (s.length() > 0) {
					submit_btn.setEnabled(true);
				} else {
					submit_btn.setEnabled(false);
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

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 0:
				get_sms_code_btn.setEnabled(false);
				get_sms_code_btn.setTextColor(getResources().getColor(
						R.color.text_grey_color));
				get_sms_code_btn.setText("("
						+ msg.arg1
						+ "s)"
						+ getResources().getString(
								R.string.security_center_get_sms_reload));
				break;
			case 1:
				if (timer != null) {
					timer.cancel();
					timer = null;
					timeCount = 10;
				}
				get_sms_code_btn.setText(getResources().getString(
						R.string.txt_input_phone_hint3));
				get_sms_code_btn.setTextColor(getResources().getColor(
						R.color.e_main_bg_title));
				get_sms_code_btn.setEnabled(true);
			}
		}

	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.get_sms_code_btn:
			if (StringUtils.isMobileNO(phone_num_et.getText().toString().trim())) {
				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("mobile", phone_num_et.getText().toString());
				connection(securityCenterService.getBoundPhoneSMSCode(GETPHONECODE, params, this));
				timer = new Timer();
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (timeCount < 2) {
							handler.sendEmptyMessage(1);
						} else {
							handler.obtainMessage(0, --timeCount, 0)
									.sendToTarget();
						}
					}
				}, 0, 1000);
			} else {
				CommonUtils.toastMsgShort(this,
						getResources()
								.getString(R.string.txt_input_phone_hint4));
			}
			break;
		case R.id.submit_btn:
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("mobile", phone_num_et.getText().toString());
			params.put("verifyCode", sms_code_et.getText().toString().trim());
			connection(securityCenterService.validteSMSCode(VALIDATESMSCODE,
					params, this));
			break;
		}
	}

	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		switch (tag) {
		case GETPHONECODE:
			break;

		case VALIDATESMSCODE:
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("phoneNo", phone_num_et.getText().toString());
			connection(securityCenterService.boundPhone(BOUNDPHONE, params,this));
			break;
		case BOUNDPHONE:
			 CommonUtils.toastMsgShortForStyle(this,
			 getResources().getString(R.string.security_center_phone_bound_ok),dip2px(-75));
			 ActivityManager activityManager=ActivityManager.getInstance();
			 activityManager.popAllActivity();
			 finish();
			break;
		}
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
	}
}
