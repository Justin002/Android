package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.beyondsoft.ep2p.ActivityManager;
import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.model.response.PayValidateErrorResponse;
import com.beyondsoft.ep2p.model.response.ValidatePayPwdResponse;
import com.beyondsoft.ep2p.model.response.ExchangeResponse.Result.ExchangeDetail;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.widget.PayHintDialog;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog.ButtonOnClickListener;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog.PayPasswrodClickListener;

/**
 * @author Ivan.Lu
 * @description 话费兑换详情
 */
public class PhoneBillExchangeDetailActivity extends BaseFragmentActivity implements OnClickListener{
	private static final int VALIDATEPAYPWD=1;
	private static final int PHONEEXCHANGE=2;
	private TextView tv_phone_bill_jifen_count;
	private TextView tv_phone_count;
	private TextView tv_phone_bill_count;
	private TextView tv_phone_bill_hint;
	private EditText et_phone_bill;
	private Button bt_exchange;
	private String mPayPassword;
	private ExchangeDetail mDetail;
	private PayPasswrodDialog passwrodDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActivityManager activityManager=ActivityManager.getInstance();
		activityManager.pushActivity(this);
		setContentView(R.layout.activity_phone_bill_exchange_detail);
		init();
		setListener();
		initData();
	}

	private void init(){
		setTitle(getResources().getString(R.string.credits_phone_bill_title));
		bt_exchange=(Button) findViewById(R.id.bt_phone_bill_detail_exchange);
		tv_phone_bill_jifen_count=(TextView) findViewById(R.id.tv_phone_bill_jifen_count);
		tv_phone_count=(TextView) findViewById(R.id.tv_phone_count);
		tv_phone_bill_count=(TextView) findViewById(R.id.tv_phone_bill_count);
		et_phone_bill=(EditText) findViewById(R.id.et_phone_bill);
		tv_phone_bill_hint=(TextView) findViewById(R.id.tv_phone_bill_hint);
	}

	private void setListener() {
		bt_exchange.setOnClickListener(this);
		et_phone_bill.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if(et_phone_bill.getText().toString().trim().length()>0){
					et_phone_bill.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
				}else{
					et_phone_bill.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
				}
				if(s.length()==11){
					if(et_phone_bill.getText().toString().trim().equals(UserPersonalInfo.getPhoneNo())&&"1".equals(UserPersonalInfo.getIsBingPhone())){
						tv_phone_bill_hint.setText(getResources().getString(R.string.phone_exchange));
					}else{
						tv_phone_bill_hint.setText(getResources().getString(R.string.phone_exchange2));
					}
					bt_exchange.setEnabled(true);
				}else{
					bt_exchange.setEnabled(false);
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
		mDetail=(ExchangeDetail) getIntent().getSerializableExtra("detail");
		tv_phone_bill_jifen_count.setText(mDetail.getDictContDesc());
		tv_phone_count.setText(mDetail.getDictContName()+"元");
		tv_phone_bill_count.setText(mDetail.getDictContName()+"元");
		et_phone_bill.setText(UserPersonalInfo.getPhoneNo());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_phone_bill_detail_exchange:
			if(UserPersonalInfo.getAvailablePoint()<Integer.parseInt(mDetail.getDictContDesc())){
				bt_exchange.setEnabled(false);
			}else{
				showInputPwdDialog();
			}
			break;
		}
	}
	
	/**
	 * 交易密码输入框
	 */
	private void showInputPwdDialog(){
		if(passwrodDialog==null){
			passwrodDialog=new PayPasswrodDialog(mContext);
			passwrodDialog.setOnPayPasswrodClickListener(new PayPasswrodClickListener() {
				
				@Override
				public void OnClick(String payPasswrod) {
					// TODO Auto-generated method stub
					mPayPassword=payPasswrod;
				}
			});
			passwrodDialog.setButtonClickListener(new ButtonOnClickListener() {
				
				@Override
				public void onButton1Click(View v) {
					// TODO Auto-generated method stub
					HashMap<String,Object> hashMap=new HashMap<String, Object>();
					hashMap.put("tradPassWord", mPayPassword);
					connection(new SecurityCenterService().validatePayPassword(VALIDATEPAYPWD, hashMap, PhoneBillExchangeDetailActivity.this));
					passwrodDialog=null;
				}
			});
			passwrodDialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
					passwrodDialog=null;
				}
			});
			passwrodDialog.show();
			showKeyBoard();
		}
	}
	
	/**交易密码错误框
	 * @param response
	 */
	private void showErrorDialog(PayValidateErrorResponse response){
		if(response.getResult().getNum()>0){
			PayHintDialog payclearDialog = new PayHintDialog(mContext);
			payclearDialog.show();
			payclearDialog.setDescri(getResources().getString(R.string.error_paypwd_hint,response.getResult().getNum()));
			payclearDialog.setButtonClickListener(new PayHintDialog.ButtonOnClickListener() {

				@Override
				public void onButton2Click(View v) {
					showInputPwdDialog();
				}

				@Override
				public void onButton1Click(View v) {
					pushActivity(ForgetPasswordActivity.class);
				}
			});							
		}else{
			CommonUtils.toastMsgShort(this,getResources().getString(R.string.error_paypwd_hint2,response.getResult().getTime()));
		}
	}

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
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		switch (tag) {
		case VALIDATEPAYPWD:
			ValidatePayPwdResponse validatePayPwdResponse=gson.fromJson(values.toString(), ValidatePayPwdResponse.class);
			HashMap<String,Object> hashMap=new HashMap<String, Object>();
			hashMap.put("tranPKey", validatePayPwdResponse.getResult().getKey());
			hashMap.put("dictionaryContentId", mDetail.getPid());
			hashMap.put("phoneNo", et_phone_bill.getText().toString().trim());
			connection(new SecurityCenterService().exchangeTelephoneFare(PHONEEXCHANGE, hashMap, PhoneBillExchangeDetailActivity.this));
			break;
		case PHONEEXCHANGE:
			pushActivity(new Intent(this, PhoneBillDetailSuccessActivity.class).putExtra("price", mDetail.getDictContName()).putExtra("score",Integer.parseInt(mDetail.getDictContDesc())));
			break;
		}
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		switch (tag) {
		case VALIDATEPAYPWD:
			PayValidateErrorResponse response=gson.fromJson(error, PayValidateErrorResponse.class);
			showErrorDialog(response);
			break;
		default:
			super.onErrorResponse(tag, error);
			break;
		}
	}
}

