package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.AddBankCardInfoBean;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.Config;
import com.beyondsoft.ep2p.utils.URL;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 银行卡验证页面
 * 
 * @author hardy.zhou
 *
 */
public class BankCardValidationActivity extends BaseActivity implements OnClickListener {

	public static final String PARAMS_PHONE_NUM = "phone_num";
	private String mPhoneNum;
	private TextView tv_title,tv_user_phone_no;
	private Timer timer;
	private int timeCount = 60;
	private TextView tv_get_validation_code;
	private AddBankCardInfoBean bankCardInfo;
	private EditText et_sms_code;
	private Button bt_next_step,bt_get_voice_validation_code;

	private String mVerifyCode;

	private BaseService baseService;

	private static final int TAG_SEND_SMS_CODE = 100;
	private static final int TAG_VALIDATION_SMS_CODE = 200;
	private static final int TAG_ADD_BANK_CARD = 300;
	private static final int TAG_SEND_VOICE_VALIDATION_CODE = 400;
	
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
				bt_get_voice_validation_code.setEnabled(true);
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bank_card_validation);
		baseService = new BaseService();
		initData();
		initView();
		setListener();
		//doSendSmsCodeRequest();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		mPhoneNum = UserPersonalInfo.getPhoneNo();
		bankCardInfo = (AddBankCardInfoBean) getIntent().getExtras().getSerializable("bank_card_info");
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText(R.string.mine_bank_card);
		tv_user_phone_no = (TextView) findViewById(R.id.tv_user_phone_no);
		String showNum1 = mPhoneNum.substring(0,3);
		String showNum2 = mPhoneNum.substring(7, 11);
		tv_user_phone_no.setText(showNum1+"****"+showNum2);
		tv_get_validation_code = (TextView) findViewById(R.id.tv_get_validation_code);
		tv_get_validation_code.setOnClickListener(this);
		et_sms_code = (EditText) findViewById(R.id.et_sms_code);
		bt_next_step = (Button) findViewById(R.id.bt_next_step);
		bt_get_voice_validation_code = (Button) findViewById(R.id.bt_get_voice_validation_code);
		bt_next_step.setOnClickListener(this);
		bt_get_voice_validation_code.setOnClickListener(this);
		/*timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				if (timeCount < 2) {
					handler.sendEmptyMessage(1);
				} else {
					handler.obtainMessage(0, --timeCount, 0).sendToTarget();
				}
			}
		}, 0, 1000);*/
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
	 * 显示成功提示
	 */
	private void showSuccessHint() {
		View view = LayoutInflater.from(mContext).inflate(R.layout.feedback_submit_success, null);
		TextView textView = (TextView) view.findViewById(R.id.info_tv);
		textView.setText(getResources().getText(R.string.add_success));
		Toast toast = new Toast(mContext);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, dip2px(-75));
		toast.setView(view);
		toast.show();
	}

	/**
	 * 执行发送短信验证码请求
	 */
	private void doSendSmsCodeRequest() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("mobile", UserPersonalInfo.getPhoneNo());
		connection(baseService.getStringRequest(TAG_SEND_SMS_CODE, URL.URL_SEND_BANK_CARD_SMS_CODE, params, mContext));
	}

	/**
	 * 执行校验短信验证码请求
	 */
	private void doVerifySmsCodeRequest() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("mobile", UserPersonalInfo.getPhoneNo());
		params.put("verifyCode", mVerifyCode);
		connection(baseService.getStringRequest(TAG_VALIDATION_SMS_CODE, URL.URL_VERIFY_SMS_CODE, params, mContext));
	}

	/**
	 * 添加银行卡请求
	 * */
	private void doAddBankCardRequest() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		params.put("bankCardNo", bankCardInfo.getBankcardNo());
		params.put("bankcardName", bankCardInfo.getBankcardName());
		params.put("belongingBank", bankCardInfo.getBelongingBank());
		params.put("belongingProvince", bankCardInfo.getBelongingProvince());
		params.put("openAddress", bankCardInfo.getOpenAddress());
		connection(baseService.getStringRequest(TAG_ADD_BANK_CARD,URL.URL_ADD_BANK_CARD, params, mContext));
	}
	
	private void doSendVoiceValidationCode(){
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("mobile", UserPersonalInfo.getPhoneNo());
		connection(baseService.getStringRequest(TAG_SEND_VOICE_VALIDATION_CODE, URL.MINE_GETVOICE_CODE, params,mContext));
	}
	
	@Override
	public void onResponse(int tag, Object values) {
		super.onResponse(tag, values);
		switch (tag) {
		case TAG_SEND_SMS_CODE: {
			CommonUtils.toastMsgShort(mContext, "短信验证码已经发送到您的手机");
		}
			break;
		case TAG_VALIDATION_SMS_CODE: 
			CommonUtils.toastMsgShort(mContext, "短信验证成功");
			doAddBankCardRequest();
			break;     
		case TAG_ADD_BANK_CARD:
			showSuccessHint();
			/*new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					onBackPressed();
				}
			}, 3000);*/
			UserPersonalInfo.setCardVoucherCount(UserPersonalInfo.getCardVoucherCount()+1);
			startActivity(new Intent(mContext,BankCardActivity.class));
		break;
		case TAG_SEND_VOICE_VALIDATION_CODE:
			
		default:
			break;
		}
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		super.onErrorResponse(tag, error);
		switch (tag) {
		case TAG_SEND_SMS_CODE: {
			CommonUtils.toastMsgShort(mContext, "短信验证码发送失败");
		}
			break;
		case TAG_VALIDATION_SMS_CODE: {
			CommonUtils.toastMsgShort(mContext, "短信验证码校验失败");
		}
			break;
		case TAG_ADD_BANK_CARD:
			CommonUtils.toastMsgShort(mContext, "银行卡添加失败，请稍后再试");
			break;
		default:
			break;
		}
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
			tv_get_validation_code.setEnabled(false);
			bt_get_voice_validation_code.setEnabled(false);
			doSendSmsCodeRequest();
		}
			break;
		case R.id.bt_next_step: {
			mVerifyCode = et_sms_code.getText().toString();
			if (null != mVerifyCode && !"".equals(mVerifyCode)) {
				doVerifySmsCodeRequest();
			} else {
				CommonUtils.toastMsgShort(mContext, "请输入短信验证码");
			}
		}
			break;
			
		case R.id.bt_get_voice_validation_code:{
			doSendVoiceValidationCode();
		}
		break;
		default:
			break;
		}

	}

}
