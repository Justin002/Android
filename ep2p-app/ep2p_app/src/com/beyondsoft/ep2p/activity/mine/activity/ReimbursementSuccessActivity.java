package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.HashMap;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.ReimbursementForProfitSuccessInfoBean;
import com.beyondsoft.ep2p.model.ReimbursementForProfitSuccessResult;
import com.beyondsoft.ep2p.model.response.ReimbursementForProfitSuccessResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 还款成功页面
 * 
 * @author hardy.zhou
 *
 */
public class ReimbursementSuccessActivity extends BaseActivity implements OnClickListener {

	private TextView tv_title, tv_right;
	private Button bt_look_reimbursement_finish;
	private BaseService baseService;
	private static final int FLAG_DO_REIMBURSEMENT_FOR_PROFIT = 100;
	
	private TextView tv_borrowCode;
	private TextView tv_repaidTime;
	private TextView tv_capital;
	private TextView tv_interest;
	private TextView tv_managementCost;
	private TextView tv_latefee;
	private TextView tv_repay_result;
	private ImageView title_left_btn;
	private String borrowCode;
	private boolean repayResult;
	private boolean lastRepay;
	private ReimbursementForProfitSuccessInfoBean bean;
	
	private LinearLayout ll_repay_details;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reimbursement_success);
		baseService = new BaseService();
		initView();
		doReibuersementForProfit();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText(R.string.reimbursement_success);
		tv_right = (TextView) findViewById(R.id.title_right);
		tv_right.setText(R.string.ok);
		tv_right.setOnClickListener(this);
		bt_look_reimbursement_finish = (Button) findViewById(R.id.bt_look_reimbursement_finish);
		bt_look_reimbursement_finish.setOnClickListener(this);
		title_left_btn = (ImageView) findViewById(R.id.title_left_btn);
		title_left_btn.setVisibility(View.GONE);
		tv_borrowCode = (TextView) findViewById(R.id.tv_borrowCode);
		tv_repaidTime = (TextView) findViewById(R.id.tv_repaidTime);
		tv_capital = (TextView) findViewById(R.id.tv_capital);
		tv_interest = (TextView) findViewById(R.id.tv_interest);
		tv_managementCost = (TextView) findViewById(R.id.tv_managementCost);
		tv_latefee = (TextView) findViewById(R.id.tv_latefee);
		tv_repay_result = (TextView) findViewById(R.id.tv_repay_result);
		
		ll_repay_details = (LinearLayout) findViewById(R.id.ll_repay_details);
	
	}
	
	private void setData(){
		tv_borrowCode.setText(borrowCode);
		String[] rePaidTimes = bean.getRepaidTime().split(" ");
		tv_repaidTime.setText(rePaidTimes[0].trim());
		tv_capital.setText(""+bean.getCapital()+"元");
		tv_interest.setText(""+bean.getInterest()+"元");
		tv_managementCost.setText(""+bean.getManagementCost()+"元");
		tv_latefee.setText(""+bean.getLatefee()+"元");
		if(lastRepay){
			tv_repay_result.setText("恭喜您，欠款已全部还清！");
		}
	}
	
	private void doReibuersementForProfit(){
		String borrowId = getIntent().getStringExtra("borrowId");
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		params.put("borrowId", borrowId);
		connection(baseService.getStringRequest(FLAG_DO_REIMBURSEMENT_FOR_PROFIT, URL.URL_DO_REIMBURSEMENT_FOR_PROFIT, params, mContext));
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_right: {
			Intent intent = new Intent();
			intent.setClass(mContext, ReimbursementActivity.class);
			intent.putExtra(ReimbursementActivity.TAB_SELECT, ReimbursementActivity.TAB_DONE);
			startActivity(intent);
			finish();
		}
			break;
		case R.id.bt_look_reimbursement_finish: {
			Intent intent = new Intent();
			intent.setClass(mContext, ReimbursementActivity.class);
			intent.putExtra(ReimbursementActivity.TAB_SELECT, ReimbursementActivity.TAB_DONE);
			startActivity(intent);
			finish();
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
		switch(tag)
		{
		case FLAG_DO_REIMBURSEMENT_FOR_PROFIT:
			ReimbursementForProfitSuccessResponse response =gson.fromJson(values.toString(), ReimbursementForProfitSuccessResponse.class);
			if(response!=null){
				ReimbursementForProfitSuccessResult result = response.getResult();
				if(result!=null){
					if(repayResult=result.isRepayResult())
					{
					lastRepay = result.isLastRepay();
					borrowCode = result.getBorrowCode();
					bean = result.getBean();
					setData();
					}else
					{
						tv_repay_result.setText("还款失败，请稍后再试！");
						ll_repay_details.setVisibility(View.GONE);
					}
				}
			}
		}
	}

}
