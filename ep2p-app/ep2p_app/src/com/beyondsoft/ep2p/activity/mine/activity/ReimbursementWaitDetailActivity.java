package com.beyondsoft.ep2p.activity.mine.activity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.ReimbursementWaitDetailResult;
import com.beyondsoft.ep2p.model.ReimbursementWaitDetailsBean;
import com.beyondsoft.ep2p.model.response.ReimbursementWaitDetailsListBeanResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 还款-待还款-还款详情页面
 * 
 * @author hardy.zhou
 *
 */
public class ReimbursementWaitDetailActivity extends BaseActivity implements OnClickListener {

	private BaseService baseService;
	private TextView tv_title;
    private ImageView wait_amount_image;
	private LinearLayout waitrembursement_amount,waitrembursement_amount_details;
	private ReimbursementWaitDetailsBean bean;
	private static final int FLAG_REIMBURSEMENT_WAIT_DETAIL = 100;
	
	private TextView tv_borrowCode;
	private TextView tv_borrowName;
	private TextView tv_borrowMoney;
	private TextView tv_borrowRate;
	private TextView tv_manageExpenseRate;
	private TextView tv_deadline;
	private TextView tv_repaymentType;
	
	private TextView tv_waitRepayMoney;
	private TextView tv_capital;
	private TextView tv_interest;
	private TextView tv_managementCost;
	private TextView tv_latefee;
	private TextView tv_qici;
	private TextView tv_waitRepayTime;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reimbursement_detail);
		baseService = new BaseService();
		initView();
		doGetReimbursementWaitInfolist();
	}
	
	/**
	 * 待还款明细显示/隐藏
	 * */

	private void ControlViewShow(){
		if(waitrembursement_amount_details.getVisibility()==View.VISIBLE){
			waitrembursement_amount_details.setVisibility(View.GONE);
			wait_amount_image.setImageResource(R.drawable.wait_receive_line_shouqi);
		}else{
			waitrembursement_amount_details.setVisibility(View.VISIBLE);
			wait_amount_image.setImageResource(R.drawable.wait_receive_line_zhankai);
		}
	}
	/**
	 * 初始化组件
	 */
	private void initView() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText(R.string.reimbursement_detail);
		waitrembursement_amount = (LinearLayout) findViewById(R.id.waitrembursement_amount);
		waitrembursement_amount_details = (LinearLayout) findViewById(R.id.waitrembursement_amount_details);
		waitrembursement_amount.setOnClickListener(this);
		wait_amount_image = (ImageView) findViewById(R.id.wait_amount_image);
		
		
		tv_borrowCode = (TextView) findViewById(R.id.tv_borrowCode);
		tv_borrowName = (TextView) findViewById(R.id.tv_borrowName);
		tv_borrowMoney = (TextView) findViewById(R.id.tv_borrowMoney);
		tv_borrowRate = (TextView) findViewById(R.id.tv_borrowRate);
		tv_manageExpenseRate = (TextView) findViewById(R.id.tv_manageExpenseRate);
		tv_deadline = (TextView) findViewById(R.id.tv_deadline);
		tv_repaymentType = (TextView) findViewById(R.id.tv_repaymentType);
		
		tv_waitRepayMoney = (TextView)findViewById(R.id.tv_waitRepayMoney);
		tv_capital = (TextView)findViewById(R.id.tv_capital);
		tv_interest = (TextView)findViewById(R.id.tv_interest);
		tv_managementCost = (TextView)findViewById(R.id.tv_managementCost);
		tv_latefee = (TextView)findViewById(R.id.tv_latefee);
		tv_qici = (TextView)findViewById(R.id.tv_qici);
		tv_waitRepayTime = (TextView)findViewById(R.id.tv_waitRepayTime);
		
		
	}
	
	private void setData(){
		NumberFormat ft= NumberFormat.getPercentInstance();
		ft.setMaximumFractionDigits(2);
		DecimalFormat df = new DecimalFormat("########0.00");
		tv_borrowCode.setText(bean.getBorrowCode());
		tv_borrowName.setText(bean.getBorrowName());
		tv_borrowMoney.setText(df.format(bean.getBorrowMoney())+"元");
		tv_borrowRate.setText(ft.format(bean.getBorrowRate()));
		tv_manageExpenseRate.setText(ft.format(bean.getManageExpenseRate()));
		tv_deadline.setText(bean.getDeadline()+"个月");
		tv_repaymentType.setText(bean.getRepaymentType());
		
		tv_waitRepayMoney.setText(df.format(bean.getWaitRepayMoney())+"元");
		tv_capital.setText(df.format(bean.getCapital())+"元");
		tv_interest.setText(df.format(bean.getInterest())+"元");
		tv_managementCost.setText(df.format(bean.getManagementCost())+"元");
		tv_latefee.setText(df.format(bean.getLatefee())+"元");
		tv_qici.setText(bean.getCurrentPlanindex()+"/"+bean.getMaxPlanindex()+"期");
		String[] waitRepayTimes = bean.getWaitRepayTime().split(" ");
		tv_waitRepayTime.setText(waitRepayTimes[0].trim());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.waitrembursement_amount:
			ControlViewShow();
		default:
			break;
		}
	}
	
	private void doGetReimbursementWaitInfolist()
	{
		Intent intent = getIntent();
		String pid = intent.getStringExtra("pid");
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
	    //待还款
		params.put("repayId", pid);
	    connection(baseService.getStringRequest(FLAG_REIMBURSEMENT_WAIT_DETAIL, URL.URL_REIMBURSEMENT_WAIT_DETAIL, params, mContext));
	    //已结清
		/*params.put("borrowId", "FFFFFFFFEF0724CA!AE6F094D0B394FF9AEE0C4BE05C8F0");
		connection(baseService.getStringRequest(FLAG_REIMBURSEMENT_WAIT_DETAIL, URL.URL_REIMBURSEMENT_DONE_DETAIL, params, mContext));
	*/}

	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		super.onResponse(tag, values);
		switch(tag)
		{
		  case FLAG_REIMBURSEMENT_WAIT_DETAIL:
		  {
			  ReimbursementWaitDetailsListBeanResponse reimbursementWaitListBeanResponse = gson.fromJson(values.toString(), ReimbursementWaitDetailsListBeanResponse.class);
			  if(reimbursementWaitListBeanResponse!=null){
				  ReimbursementWaitDetailResult result = reimbursementWaitListBeanResponse.getReimbursementWaitDetailResult();
			      if(result!=null){
			    	  bean = result.getReimbursementWaitDetailsBean();
			    	  
			    	  setData();
			      }
			  }
		  }
		}
	}
}
