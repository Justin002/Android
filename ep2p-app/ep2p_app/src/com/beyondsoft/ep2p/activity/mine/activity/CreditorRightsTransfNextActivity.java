package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.ValidatePasswordReplyBean;
import com.beyondsoft.ep2p.model.response.PayValidateErrorResponse;
import com.beyondsoft.ep2p.model.response.ValidatePasswordReplyResponse;
import com.beyondsoft.ep2p.model.response.ValidatePayPwdResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.widget.PayHintDialog;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog.ButtonOnClickListener;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog.PayPasswrodClickListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * 债权转让确认页面
 * 
 * @author Jhuang
 *
 */
public class CreditorRightsTransfNextActivity extends BaseActivity implements OnClickListener {

	private TextView tv_title;
	private Button bt_ensure_transf;
	private BaseService baseService;
	private int input_count = 3;
    private static final int FLAG_VILIDATE_PASSWORD =100;
    private static final int FLAG_CREDITOR_TRANSFER = 200;
    private TextView tv_transfer_capital;
    private TextView tv_transfer_price;
    private TextView tv_transfer_fee;
    private PayPasswrodDialog passwrodDialog;
	private String mPayPassword;
	
	private String key;
	private String surperCapital;
	private String transferPrice;
	private String tansferFee;
	private String borrowId;
	private String transfId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creditor_rights_transf_next);
		baseService = new BaseService();
		initView();
		initData();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText(R.string.creditor_rights_transf);
		//tv_cerditor_rights_transf_agreement = (TextView) findViewById(R.id.tv_cerditor_rights_transf_agreement);
		//tv_cerditor_rights_transf_agreement.setOnClickListener(this);
		bt_ensure_transf = (Button) findViewById(R.id.bt_ensure_transf_next);
		bt_ensure_transf.setOnClickListener(this);
		tv_transfer_capital = (TextView) findViewById(R.id.tv_transfer_capital);
		tv_transfer_price = (TextView) findViewById(R.id.tv_transfer_price);
		tv_transfer_fee = (TextView) findViewById(R.id.tv_transfer_fee);
	}
	
	private void initData(){
		Intent intent = getIntent();
		surperCapital = intent.getStringExtra("surperCapital");
		transferPrice = intent.getStringExtra("transferPrice");
		tansferFee = intent.getStringExtra("tansferFee");
		tv_transfer_capital.setText(surperCapital+"元");
		tv_transfer_price.setText(transferPrice+"元");
		tv_transfer_fee.setText(tansferFee+"元");
	}
	
	private void doCreditorTransferRequest(){
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		params.put("borrowId", getIntent().getStringExtra("borrowId"));
		params.put("transferId", getIntent().getStringExtra("transferId"));
		params.put("tranPKey", key);
		params.put("amount", getIntent().getStringExtra("amount"));
		connection(baseService.getStringRequest(FLAG_CREDITOR_TRANSFER, URL.URL_CREDITOR_TRANSFER, params, mContext));
	}
	
	
	/**
	 * 交易密码输入框
	 */
	private void showInputPwdDialog(){
		
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
					connection(new SecurityCenterService().validatePayPassword(FLAG_VILIDATE_PASSWORD, hashMap, CreditorRightsTransfNextActivity.this));
					passwrodDialog=null;
				}
			});
			passwrodDialog.show();
			showKeyBoard();
		
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_ensure_transf_next: {
			showInputPwdDialog();
		}
			break;
		/*case R.id.tv_cerditor_rights_transf_agreement: {
			Toast.makeText(mContext, "查看债券转让协议", Toast.LENGTH_SHORT).show();
		}
			break;*/
		default:
			break;
		}

	}
	
	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		super.onResponse(tag, values);
		switch(tag)
		{
		  case FLAG_VILIDATE_PASSWORD:
		  {
			  
			  ValidatePayPwdResponse validatePayPwdResponse=gson.fromJson(values.toString(), ValidatePayPwdResponse.class);
			  key = validatePayPwdResponse.getResult().getKey();
			  doCreditorTransferRequest();
		  }
		  break;
		  case FLAG_CREDITOR_TRANSFER:{
			   Intent intent = new Intent();
			   intent.putExtra("surperCapital", surperCapital);
			   intent.putExtra("transferPrice", transferPrice);
			   intent.putExtra("tansferFee", tansferFee);
			   intent.setClass(mContext, CreditorRightsTransfSuccessActivity.class);
				startActivity(intent);
		  }
			    break;
			    default:
			    	break;
	    }
		
	}
	
	
	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
		switch(tag)
		{
		case FLAG_VILIDATE_PASSWORD:
		{
			PayValidateErrorResponse response=gson.fromJson(error, PayValidateErrorResponse.class);
			showErrorDialog(response);
		}
		break;
		case FLAG_CREDITOR_TRANSFER:
			CommonUtils.toastMsgShort(mContext, "债权转让失败，请稍后再试！");
			break;
		default:
			break;
		}
	}


}
