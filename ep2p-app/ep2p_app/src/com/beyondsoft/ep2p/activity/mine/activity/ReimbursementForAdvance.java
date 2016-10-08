package com.beyondsoft.ep2p.activity.mine.activity;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.home.AuthenticationActivity;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.ReimbursementForAdvanceDetailInfoBean;
import com.beyondsoft.ep2p.model.ReimbursementForAdvanceDetailResult;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.model.response.PayValidateErrorResponse;
import com.beyondsoft.ep2p.model.response.ReimbursementForAdvanceDetailResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.widget.CustomDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 提前还款详情页面
 * @author Jason.Hwang
 */
public class ReimbursementForAdvance extends BaseActivity implements OnClickListener{

	private TextView tv_title;
	private Button bt_reibuersement_advance;
	private BaseService baseService;
	private static final int FLAG_REIMBURSEMENT_FOR_ADVANCE = 100;
	private static final int FLAG_VILIDATE_PASSWORD = 300;
	private static final int FLAG_DO_REIMBURSEMENT_FOR_ADVANCE=200;
	private PayPasswrodDialog passwrodDialog;
	private String mPayPassword;
  
	private LinearLayout reimbursement_money_advance,reimbursement_money_advance_details;
	private ImageView reimbursement_money_advance_biaoji;
	private ReimbursementForAdvanceDetailInfoBean bean;
	
	private TextView tv_borrowCode;
	private TextView tv_borrowName;
	private TextView tv_borrowMoney;
	private TextView tv_borrowRate;
	private TextView tv_manageExpenseRate;
	private TextView tv_borDeadline;
	private TextView tv_repaymentTypeName;
	private TextView tv_paymentAmount;
	private TextView tv_capital;
	private TextView tv_interest;
	private TextView tv_managementCost;
	private TextView tv_latefee;
	private TextView tv_prepaymentInterest,tv_prepaymentManage,tv_reimbursement_advance_count;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reimbursementforadvance);
		baseService = new BaseService();
		initView();
		getReimbursementForAdvanceDetails();
		
	}
	//初始化控件
	private void initView(){
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText("提前还清所有");
		bt_reibuersement_advance = (Button) findViewById(R.id.bt_reibuersement_advance);
		bt_reibuersement_advance.setOnClickListener(this);
		reimbursement_money_advance = (LinearLayout) findViewById(R.id.reimbursement_money_advance);
		reimbursement_money_advance_details = (LinearLayout) findViewById(R.id.reimbursement_money_advance_details);
		reimbursement_money_advance.setOnClickListener(this);
		reimbursement_money_advance_biaoji = (ImageView) findViewById(R.id.reimbursement_money_advance_biaoji);
		tv_borrowCode = (TextView) findViewById(R.id.tv_borrowCode);
		tv_borrowName = (TextView) findViewById(R.id.tv_borrowName);
		tv_borrowMoney = (TextView) findViewById(R.id.tv_borrowMoney);
		tv_borrowRate = (TextView) findViewById(R.id.tv_borrowRate);
		tv_manageExpenseRate = (TextView) findViewById(R.id.tv_manageExpenseRate);
		tv_borDeadline = (TextView) findViewById(R.id.tv_borDeadline);
		tv_repaymentTypeName = (TextView) findViewById(R.id.tv_repaymentTypeName);
		tv_paymentAmount = (TextView) findViewById(R.id.tv_paymentAmount);
		tv_capital = (TextView) findViewById(R.id.tv_capital);
		tv_interest = (TextView) findViewById(R.id.tv_interest);
		tv_managementCost = (TextView) findViewById(R.id.tv_managementCost);
		tv_latefee = (TextView) findViewById(R.id.tv_latefee);
		tv_prepaymentInterest = (TextView) findViewById(R.id.tv_prepaymentInterest);
		tv_prepaymentManage = (TextView) findViewById(R.id.tv_prepaymentManage);
		tv_reimbursement_advance_count = (TextView) findViewById(R.id.tv_reimbursement_advance_count);
	}
	//初始化数据
	private void setData(){
		NumberFormat ft = NumberFormat.getPercentInstance();
		ft.setMaximumFractionDigits(2);
		DecimalFormat df = new DecimalFormat("########0.00");
		tv_borrowCode.setText(bean.getBorrowCode());
		tv_borrowName.setText(bean.getBorrowName());
		tv_borrowMoney.setText(df.format(bean.getBorrowMoney())+"元");
		tv_borrowRate.setText(ft.format(bean.getBorrowRate()));
		tv_manageExpenseRate.setText(ft.format(bean.getManageExpenseRate()));
		tv_borDeadline.setText(bean.getBorDeadline()+"个月");
		tv_repaymentTypeName.setText(bean.getRepaymentTypeName());
		tv_paymentAmount.setText(df.format(bean.getPaymentAmount())+"元");
		tv_capital.setText(df.format(bean.getCapital())+"元");
		tv_interest.setText(df.format(bean.getInterest())+"元");
		tv_managementCost.setText(df.format(bean.getManagementCost())+"元");
		tv_latefee.setText(df.format(bean.getLatefee()+bean.getPrepaymentFee())+"元");
		tv_prepaymentInterest.setText("提前还款少支付"+StringUtils.formatMoney(bean.getPrepaymentInterest())+"元利息");
		tv_prepaymentManage.setText("提前还款少支付"+StringUtils.formatMoney(bean.getPrepaymentManage())+"元管理费");
		tv_reimbursement_advance_count.setText("提前还款可少支付"+StringUtils.formatMoney(bean.getPrepaymentInterest()+bean.getPrepaymentManage()-bean.getPrepaymentFee())+"元");
	}
	
	
	//请求提前还款详情接口获取详情数据
	private void getReimbursementForAdvanceDetails(){
		String borrowId = CommonUtils.getStringByKey(mContext, "borrowId");
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		params.put("borrowId", borrowId);
		connection(baseService.getStringRequest(FLAG_REIMBURSEMENT_FOR_ADVANCE, URL.URL_REIMBURSEMENT_FOR_ADVANCE, params,mContext));
	}
	
	/*private void doReibuersementForAdvance(){
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		params.put("borrowId", "FFFFFFFFEF0724CA!AE6F094D0B394FF9AEE0C4BE05C8F0");
		params.put("repaymentAmount", bean.getPaymentAmount());
		connection(baseService.getStringRequest(FLAG_DO_REIMBURSEMENT_FOR_ADVANCE, URL.URL_DO_REIMBURSEMENT_FOR_ADVANCE, params,mContext));
	}*/

	

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
					connection(new SecurityCenterService().validatePayPassword(FLAG_VILIDATE_PASSWORD, hashMap, ReimbursementForAdvance.this));
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
	
	//控制还款详情显隐
	private void ControlViewShow(){
		if(reimbursement_money_advance_details.getVisibility()==View.VISIBLE){
			reimbursement_money_advance_details.setVisibility(View.GONE);
			reimbursement_money_advance_biaoji.setImageResource(R.drawable.wait_receive_line_shouqi);
		}else{
			reimbursement_money_advance_details.setVisibility(View.VISIBLE);
			reimbursement_money_advance_biaoji.setImageResource(R.drawable.wait_receive_line_zhankai);
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.bt_reibuersement_advance:{
			Double d = new Double(UserPersonalInfo.getAvailableBalance());
			if(bean!=null){
				if(UserPersonalInfo.getAvailableBalance()>bean.getPaymentAmount()){
					showInputPwdDialog();
				}else{
					CustomDialog topUpDialog = new CustomDialog(mContext);
					topUpDialog.setButtonClickListener(new CustomDialog.ButtonOnClickListener() {
						
						@Override
						public void onButton2Click(View v) {
							
							startActivity(new Intent(mContext, RechargeActivity.class));
						}
						
						@Override
						public void onButton1Click(View v) {
							
						}
					});
					topUpDialog.show();
					topUpDialog.setTitle("提示");
					topUpDialog.setDescri("您的余额不足，请充值");
					topUpDialog.hideView();
					topUpDialog.setButtonText("取消", "去充值");
				}
			}else{
				CommonUtils.toastMsgShort(mContext, "服务器或网络异常！");
			}
			
			
			  
		}
		break;
		case R.id.reimbursement_money_advance:{
			ControlViewShow();
		}
		break;
		default:
			break;
		}
	}
	
	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		super.onResponse(tag, values);
		switch(tag){
		case FLAG_REIMBURSEMENT_FOR_ADVANCE:
			ReimbursementForAdvanceDetailResponse response = gson.fromJson(values.toString(), ReimbursementForAdvanceDetailResponse.class);
			if(response!=null){
				ReimbursementForAdvanceDetailResult result = response.getResult();
				if(result!=null){
					bean = result.getBean();
					if(bean!=null)
					setData();
				}
			}
			break;
		case FLAG_VILIDATE_PASSWORD:
			Intent intent = new Intent();
			intent.setClass(mContext,ReimbursementSuccessAllActivity.class);
			intent.putExtra("bean", bean);
		    startActivity(intent);
		break;
		default :
			break;
		}
	}
	
	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
		switch(tag){
		case FLAG_VILIDATE_PASSWORD:
			PayValidateErrorResponse response=gson.fromJson(error, PayValidateErrorResponse.class);
			showErrorDialog(response);
			break;
			
			default:
				break;
		}
	}
	
}
