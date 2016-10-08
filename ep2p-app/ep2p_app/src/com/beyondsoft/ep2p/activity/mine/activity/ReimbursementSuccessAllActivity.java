package com.beyondsoft.ep2p.activity.mine.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.ReimbursementForAdvanceDetailInfoBean;
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
import android.widget.Toast;

/**
 * 还款成功页面
 * 
 * @author hardy.zhou
 *
 */
public class ReimbursementSuccessAllActivity extends BaseActivity implements OnClickListener {

	private TextView tv_title, tv_right;
	private Button bt_look_reimbursement_finish;
	private BaseService baseService;
	private static final int FLAG_DO_REIMBURSEMENT_FOR_ADVANCE = 100;
	private TextView tv_prepaymentManage;
	private TextView tv_prepaymentInterest;
	private TextView tv_borrowCode;
	private TextView tv_repaidTime;
	private TextView tv_capital;
	private TextView tv_interest;
	private TextView tv_managementCost;
	private TextView tv_latefee;
	private TextView tv_repay_result;
	private ReimbursementForAdvanceDetailInfoBean bean;
	private String borrowCode;
	private boolean repayResult;
	private ImageView title_left_btn;
	private LinearLayout ll_repay_details;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reimbursement_success);
		baseService = new BaseService();
		bean = (ReimbursementForAdvanceDetailInfoBean) getIntent().getSerializableExtra("bean");
		initView();
		doReibuersementForAdvance();
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
		title_left_btn = (ImageView) findViewById(R.id.title_left_btn);
		title_left_btn.setVisibility(View.GONE);
		bt_look_reimbursement_finish = (Button) findViewById(R.id.bt_look_reimbursement_finish);
		bt_look_reimbursement_finish.setOnClickListener(this);
		tv_prepaymentInterest = (TextView) findViewById(R.id.tv_prepaymentInterest);
		tv_prepaymentManage = (TextView) findViewById(R.id.tv_prepaymentManage);
		tv_borrowCode = (TextView) findViewById(R.id.tv_borrowCode);
		tv_repaidTime = (TextView) findViewById(R.id.tv_repaidTime);
		tv_capital = (TextView) findViewById(R.id.tv_capital);
		tv_interest = (TextView) findViewById(R.id.tv_interest);
		tv_managementCost = (TextView) findViewById(R.id.tv_managementCost);
		tv_latefee = (TextView) findViewById(R.id.tv_latefee);
		tv_repay_result = (TextView) findViewById(R.id.tv_repay_result);
		tv_repay_result.setText(R.string.congratulations_reimbursement_success_all);
		ll_repay_details = (LinearLayout) findViewById(R.id.ll_repay_details);
	}
	
	private void setData(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		tv_borrowCode.setText(bean.getBorrowCode());
		tv_repaidTime.setText(df.format(new Date()));
		tv_capital.setText(""+bean.getCapital()+"元");
		tv_interest.setText(""+bean.getInterest()+"元");
		tv_managementCost.setText(""+bean.getManagementCost()+"元");
		tv_latefee.setText(""+bean.getLatefee()+"元");
		tv_prepaymentInterest.setText("（提前还款少支付"+bean.getPrepaymentInterest()+"元利息）");
		tv_prepaymentManage.setText("（提前还款少支付"+bean.getPrepaymentManage()+"元管理费）");
	}
	
	private void setFailureData(){
		tv_repay_result.setText("还款失败，请稍后再试！");
		tv_borrowCode.setText(bean.getBorrowCode());
	}
	
	private void doReibuersementForAdvance(){
		
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		//params.put("repaymentAmount", getIntent().getExtras().getDouble("repaymentAmount"));
		params.put("repaymentAmount",bean.getPaymentAmount());
		params.put("borrowId", bean.getBorrowId());
		connection(baseService.getStringRequest(FLAG_DO_REIMBURSEMENT_FOR_ADVANCE, URL.URL_DO_REIMBURSEMENT_FOR_ADVANCE, params, mContext));
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_right: {
			Intent intent = new Intent();
			intent.setClass(mContext, ReimbursementActivity.class);
			intent.putExtra(ReimbursementActivity.TAB_SELECT, ReimbursementActivity.TAB_DONE);
			startActivity(intent);
		}
			break;
		case R.id.bt_look_reimbursement_finish: {
			Intent intent = new Intent();
			intent.setClass(mContext, ReimbursementActivity.class);
			intent.putExtra(ReimbursementActivity.TAB_SELECT, ReimbursementActivity.TAB_DONE);
			startActivity(intent);
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
		case FLAG_DO_REIMBURSEMENT_FOR_ADVANCE:
			setData();
		}
	}
	
	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
		setFailureData();
	}

}
