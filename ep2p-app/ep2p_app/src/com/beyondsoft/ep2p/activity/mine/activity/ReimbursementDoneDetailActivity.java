package com.beyondsoft.ep2p.activity.mine.activity;

import java.text.NumberFormat;
import java.util.HashMap;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.ReimbursementDoneDetailResult;
import com.beyondsoft.ep2p.model.ReimbursementDoneDetailsBean;
import com.beyondsoft.ep2p.model.response.ReimbursementDoneDetailResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.utils.URL;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 还款-已还款-还款详情页面
 * 
 * @author Jason.Hwang
 *
 */
public class ReimbursementDoneDetailActivity extends BaseActivity implements OnClickListener {

	private BaseService baseService;
	private TextView tv_title;
	private LinearLayout ll_look_agreement;
	private ReimbursementDoneDetailsBean bean;
	
	private TextView tv_borrowCode;
	private TextView tv_borrowName;
	private TextView tv_borrowMoney;
	private TextView tv_borrowRate;
	private TextView tv_manageExpenseRate;
	private TextView tv_deadline;
	private TextView tv_repaymentType;
	
	private TextView tv_repaidCapital;
	private TextView tv_repaidInterest;
	private TextView tv_advanceRepayInterest;
	private TextView tv_repaidManagementCost;
	private TextView tv_advanceRepayManagementCost;
	private TextView tv_repaidPenalty;
	
	private static final int FLAG_REIMBURSEMENT_DONE_DETAIL = 100;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reimbursement_done_detail);
		baseService = new BaseService();
		initView();
		doGetReimbursementDoneDetail();
	}
	
	
	
	/**
	 * 初始化组件
	 */
	private void initView() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText(R.string.reimbursement_detail);
		ll_look_agreement = (LinearLayout) findViewById(R.id.ll_look_agreement);
		ll_look_agreement.setOnClickListener(this);
		
		tv_borrowCode = (TextView) findViewById(R.id.tv_borrowCode);
		tv_borrowName = (TextView) findViewById(R.id.tv_borrowName);
		tv_borrowMoney = (TextView) findViewById(R.id.tv_borrowMoney);
		tv_borrowRate = (TextView) findViewById(R.id.tv_borrowRate);
		tv_manageExpenseRate = (TextView) findViewById(R.id.tv_manageExpenseRate);
		tv_deadline = (TextView) findViewById(R.id.tv_deadline);
		tv_repaymentType = (TextView) findViewById(R.id.tv_repaymentType);
		
		tv_repaidCapital = (TextView) findViewById(R.id.tv_repaidCapital);
		tv_repaidInterest = (TextView) findViewById(R.id.tv_repaidInterest);
		tv_advanceRepayInterest = (TextView) findViewById(R.id.tv_advanceRepayInterest);
		tv_repaidManagementCost = (TextView) findViewById(R.id.tv_repaidManagementCost);
		tv_advanceRepayManagementCost = (TextView) findViewById(R.id.tv_advanceRepayManagementCost);
		tv_repaidPenalty = (TextView) findViewById(R.id.tv_repaidPenalty);

		
		
	}
	
	private void setData(){
		NumberFormat ft= NumberFormat.getPercentInstance();
		ft.setMaximumFractionDigits(2);
		tv_borrowCode.setText(bean.getBorrowCode());
		tv_borrowName.setText(bean.getBorrowName());
		tv_borrowMoney.setText(StringUtils.formatMoney(bean.getBorrowMoney())+"元");
		tv_borrowRate.setText(ft.format(bean.getBorrowRate()));
		tv_manageExpenseRate.setText(ft.format(bean.getManageExpenseRate()));
		if(bean.getBorrowCode().substring(0, 1).equals("X")||bean.getBorrowCode().substring(0, 1).equals("X")){
			tv_deadline.setText(bean.getDeadline()+"天");
		}else{
			tv_deadline.setText(bean.getDeadline()+"个月");
		}
		tv_repaymentType.setText(bean.getRepaymentType());
		tv_repaidCapital.setText(StringUtils.formatMoney(bean.getRepaidCapital())+"元");
		tv_repaidInterest.setText(StringUtils.formatMoney(bean.getRepaidInterest())+"元");
		tv_advanceRepayInterest.setText("提前还款少支付"+StringUtils.formatMoney(bean.getAdvanceRepayInterest())+"元利息");
		tv_repaidManagementCost.setText(StringUtils.formatMoney(bean.getRepaidManagementCost())+"元");
		tv_advanceRepayManagementCost.setText("提前还款少支付"+StringUtils.formatMoney(bean.getAdvanceRepayManagementCost())+"元管理费");
		tv_repaidPenalty.setText(StringUtils.formatMoney(bean.getRepaidPenalty())+"元");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_look_agreement:
			Toast.makeText(mContext, "ok", 3000).show();
			break;
		default:
			break;
		}
	}
	
	private void doGetReimbursementDoneDetail()
	{
		String pid = getIntent().getStringExtra("pid");
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		params.put("borrowId", pid);	    
		connection(baseService.getStringRequest(FLAG_REIMBURSEMENT_DONE_DETAIL, URL.URL_REIMBURSEMENT_DONE_DETAIL, params, mContext));
	}

	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		super.onResponse(tag, values);
		switch(tag)
		{
		case FLAG_REIMBURSEMENT_DONE_DETAIL:
			ReimbursementDoneDetailResponse response = gson.fromJson(values.toString(), ReimbursementDoneDetailResponse.class);
			if(response!=null){
				ReimbursementDoneDetailResult result = response.getResult();
				if(result!=null){
				bean =	result.getReimbursementDoneDetails();
				setData();
				}
			}
		}
	}
}
