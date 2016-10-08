package com.beyondsoft.ep2p.activity.mine.activity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.URL;

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
import android.widget.Toast;

/**
 * 提现验证页面
 * 
 * @author hardy.zhou
 *
 */
public class WithdrawalValicationActivity extends BaseActivity implements OnClickListener {

	public static final String PARAMS_MONEY = "params_money";
	public static final String PARAMS_PHONE_NUM = "params_phone_num";
	public static final String PARAMS_BANK_ID = "params_bank_id";

	private float mMoney;
	// private BigDecimal mMoney;
	private String mPhoneNum;
	private String mBankId;
	private String mVerifyCode;

	private TextView tv_title;
	private Timer timer;
	private int timeCount = 60;
	private TextView tv_get_validation_code;
	private TextView tv_tel_no_show;
	private EditText et_sms_code;
	private Button bt_next_step,bt_voice_validate;

	private BaseService baseService;

	private static final int TAG_SEND_SMS_CODE = 100;
	private static final int TAG_WITHDRAWAL = 200;
    private static final int TAG_SEND_VOICE_VALIDATION_CODE = 300;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				tv_get_validation_code.setTextColor(getResources().getColor(R.color.text_grey_color));
				tv_get_validation_code.setText(
						"(" + msg.arg1 + "s)" + getResources().getString(R.string.security_center_get_sms_reload));
				break;
			case 1:
				if (timer != null) {
					timer.cancel();
					timer = null;
					timeCount = 60;
				}
				tv_get_validation_code.setText(getResources().getString(R.string.txt_input_phone_hint3));
				tv_get_validation_code.setTextColor(getResources().getColor(R.color.e_main_bg_title));
				tv_get_validation_code.setEnabled(true);
				bt_voice_validate.setEnabled(true);
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawal_validation);
		baseService = new BaseService();
		initData();
		initView();
		setListener();
		doSendSMSCodeRequest();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		Intent intent = getIntent();
		mMoney = intent.getFloatExtra(PARAMS_MONEY, 0f);
		mPhoneNum = intent.getStringExtra(PARAMS_PHONE_NUM);
		mBankId = intent.getStringExtra(PARAMS_BANK_ID);
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText(R.string.mine_bank_card);
		tv_get_validation_code = (TextView) findViewById(R.id.tv_get_validation_code);
		tv_get_validation_code.setOnClickListener(this);
		bt_next_step = (Button) findViewById(R.id.bt_next_step);
		bt_voice_validate = (Button) findViewById(R.id.bt_voice_validate);
		bt_next_step.setOnClickListener(this);
		bt_voice_validate.setOnClickListener(this);
		et_sms_code = (EditText) findViewById(R.id.et_sms_code);
		tv_tel_no_show = (TextView) findViewById(R.id.tv_tel_no_show);
		String phone_1 = mPhoneNum.substring(0, 3);
		String phone_2 = mPhoneNum.substring(7, 11);
		tv_tel_no_show.setText(phone_1+"****"+phone_2);
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				if (timeCount < 2) {
					handler.sendEmptyMessage(1);
				} else {
					handler.obtainMessage(0, --timeCount, 0).sendToTarget();
				}
			}
		}, 0, 1000);
	}

	/**
	 * 设置监听器
	 */
	private void setListener() {
		et_sms_code.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override 
			public void afterTextChanged(Editable s) {
				String smsCode = s.toString();
				if (null != smsCode && !"".equals(smsCode = smsCode.trim())) {
					bt_next_step.setEnabled(true);
				} else {
					bt_next_step.setEnabled(false);
				}
			}
		});
	}

	/**
	 * 执行发送验证码请求
	 */
	private void doSendSMSCodeRequest() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		params.put("mobile", mPhoneNum);
		params.put("money", "" + mMoney);
		connection(baseService.getStringRequest(TAG_SEND_SMS_CODE, URL.URL_WITHDRAWAL_SMS_CODE, params, mContext));

	}

	/**
	 * 执行提现请求
	 */
	private void doWithdrawalRequest() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("mobile", mPhoneNum);
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		params.put("bankId", mBankId);
		params.put("money", BigDecimal.valueOf(mMoney));
		params.put("verifyCode", mVerifyCode);
		connection(baseService.getStringRequest(TAG_WITHDRAWAL, URL.URL_WITHDRAWAL_APPLICATION, params, mContext));
	}
	
	//发送语音验证码请求
	private void doSendVoiceValidationCode(){
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("mobile", mPhoneNum);
		connection(baseService.getStringRequest(TAG_SEND_VOICE_VALIDATION_CODE, URL.MINE_GETVOICE_CODE, params,mContext));
	}

	@Override
	public void onResponse(int tag, Object values) {
		super.onResponse(tag, values);
		switch (tag) {
		case TAG_SEND_SMS_CODE: {
			CommonUtils.toastMsgShort(mContext, "短信验证码发送成功");
		}
			break;
		case TAG_WITHDRAWAL: {
			//Toast.makeText(mContext, "获取", 3000).show();
			Intent intent = new Intent();
			intent.setClass(mContext, EnchashmentSuccessActivity.class);
			intent.putExtra(PARAMS_MONEY,mMoney);
			startActivity(intent);
			finish();
		}
			break;

		default:
			break;
		}
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		super.onErrorResponse(tag, error);
		CommonUtils.toastMsgShort(mContext, error);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_get_validation_code: {
			timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					if (timeCount < 2) {
						handler.sendEmptyMessage(1);
					} else {
						handler.obtainMessage(0, --timeCount, 0).sendToTarget();
					}
				}
			}, 0, 1000);
			
			doSendSMSCodeRequest();
		}
			break;
		case R.id.bt_next_step: {
			mVerifyCode = et_sms_code.getText().toString();
			if (null != mVerifyCode && !"".equals(mVerifyCode = mVerifyCode.trim())) {
				doWithdrawalRequest();
			} else {
				CommonUtils.toastMsgShort(mContext, "请输入手机验证码");
			}
		}
			break;
		case R.id.bt_voice_validate:
			doSendVoiceValidationCode();
			bt_voice_validate.setEnabled(false);
			break;
		default:
			break;
		}

	}

}
