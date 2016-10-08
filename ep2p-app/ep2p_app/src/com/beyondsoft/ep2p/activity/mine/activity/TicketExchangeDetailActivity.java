package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.beyondsoft.ep2p.ActivityManager;
import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.response.ExchangeResponse.Result.ExchangeDetail;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.model.response.PayValidateErrorResponse;
import com.beyondsoft.ep2p.model.response.ValidatePayPwdResponse;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.widget.PayHintDialog;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog.ButtonOnClickListener;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog.PayPasswrodClickListener;

/**
 * @author Ivan.Lu
 * @description 加息卷兑换详情
 */
public class TicketExchangeDetailActivity extends BaseFragmentActivity implements OnClickListener {
	private static final int VALIDATEPAYPWD=1;
	private static final int TICKETEXCHANGE=2;
    private Button bt_exchange;
    private TextView tv_ticket_year;
    private TextView tv_vip_count;
    private TextView tv_ticket_percentage;
    private TextView tv_ticket_time;
    private String mPayPassword;
    private ExchangeDetail mDetail;
    private PayPasswrodDialog passwrodDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActivityManager activityManager=ActivityManager.getInstance();
		activityManager.pushActivity(this);
		setContentView(R.layout.activity_ticket_exchange_detail);
		init();
		setListener();
		initData();
	}

	private void init(){
		setTitle(getResources().getString(R.string.credits_ticket_title));
		bt_exchange=(Button) findViewById(R.id.bt_ticket_detail_exchange);
		tv_ticket_year=(TextView) findViewById(R.id.tv_ticket_year);
		tv_vip_count=(TextView) findViewById(R.id.tv_vip_count);
		tv_ticket_percentage=(TextView) findViewById(R.id.tv_ticket_percentage);
		tv_ticket_time=(TextView) findViewById(R.id.tv_ticket_time);
	}

	private void setListener() {
		bt_exchange.setOnClickListener(this);
	}
	
	private void initData(){
		mDetail=(ExchangeDetail) getIntent().getSerializableExtra("detail");
		tv_ticket_year.setText("+"+mDetail.getDictContName()+"%");
		tv_vip_count.setText(mDetail.getDictContDesc());
		tv_ticket_percentage.setText("+"+mDetail.getDictContName()+"%");
		tv_ticket_time.setText(mDetail.getInterestTicketValidity());
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_ticket_detail_exchange:
			if(UserPersonalInfo.getAvailablePoint()<Integer.parseInt(mDetail.getDictContDesc())){
				CommonUtils.toastMsgShort(mContext, "您当前积分不足，无法兑换");
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
					connection(new SecurityCenterService().validatePayPassword(VALIDATEPAYPWD, hashMap, TicketExchangeDetailActivity.this));
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
			connection(new SecurityCenterService().exchangeInterestTicket(TICKETEXCHANGE, hashMap, TicketExchangeDetailActivity.this));
			break;
		case TICKETEXCHANGE:
			pushActivity(new Intent(this, TicketDetailSuccessActivity.class).putExtra("percentage", mDetail.getDictContName()).putExtra("score",Integer.parseInt(mDetail.getDictContDesc())));
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