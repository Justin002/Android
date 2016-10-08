package com.beyondsoft.ep2p.activity.mine.activity;

import java.text.DecimalFormat;
import java.util.HashMap;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.WaitReceiveDetialInfoBean;
import com.beyondsoft.ep2p.model.response.WaitReceiveDetailResponse;
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
import android.widget.Toast;

/**
 * 待收详情页面
 * 
 * @author hardy.zhou
 *
 */
public class WaittingRecieveDetailActivity extends BaseActivity implements OnClickListener {

	public static final String FLAG_STATE = "flag_state";

	public static final int STATE_DOING = 0;
	public static final int STATE_DONE = 1;

	public static final int FLAG_GET_WAIT_RECEIVE_DETAIL = 100;
	private BaseService baseService;
	private WaitReceiveDetialInfoBean waitReceiveDetialInfoBean;
	
	private int state = 0;

	private ImageView wait_receive_amount_image;
	private TextView tv_title;

	private LinearLayout ll_project_detail, ll_creditor_rights_transf, item_wait_income_amount,amount_detail;

	private TextView tv_borrowCode,tv_borrowName,tv_totalAmount,tv_capital,tv_netInterest,tv_netHike,tv_yuqipeifu,tv_qici,tv_expireTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_waitting_recieve_detail);
		baseService = new BaseService();
		initData();
		initView();
		getWaitReceiveDetail();
		//setViewState();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		Intent intent = getIntent();
		state = intent.getIntExtra(FLAG_STATE, STATE_DOING);
	}
	
	private void getWaitReceiveDetail(){
		Intent intent = getIntent();
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		params.put("receiptPlanId", intent.getStringExtra("receiptPlanId"));
		connection(baseService.getStringRequest(FLAG_GET_WAIT_RECEIVE_DETAIL,URL.URL_WAIT_RECEIVE_DETAIL, params, mContext));
	}
	
	/**
	 * 初始化组件
	 */
	private void initView() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText(R.string.wait_receive_detail);
		ll_project_detail = (LinearLayout) findViewById(R.id.ll_project_detail);
		//ll_creditor_rights_transf = (LinearLayout) findViewById(R.id.ll_creditor_rights_transf);
		ll_project_detail.setOnClickListener(this);
	//	ll_creditor_rights_transf.setOnClickListener(this);
		item_wait_income_amount = (LinearLayout) findViewById(R.id.item_wait_income_amount);
		item_wait_income_amount.setOnClickListener(this);
		amount_detail = (LinearLayout) findViewById(R.id.amount_detail);
		wait_receive_amount_image = (ImageView) findViewById(R.id.wait_receive_amount_image);
		
		tv_borrowCode = (TextView) findViewById(R.id.tv_borrowCode);
		tv_borrowName = (TextView) findViewById(R.id.tv_borrowName);
		tv_totalAmount = (TextView) findViewById(R.id.tv_totalAmount);
		tv_capital = (TextView) findViewById(R.id.tv_capital);
		tv_netInterest = (TextView) findViewById(R.id.tv_netInterest);
		tv_netHike = (TextView) findViewById(R.id.tv_netHike);
		tv_yuqipeifu = (TextView) findViewById(R.id.tv_yuqipeifu);
		tv_qici = (TextView) findViewById(R.id.tv_qici);
		tv_expireTime = (TextView) findViewById(R.id.tv_expireTime);
		
		
		}
	
	private void setViewData(){
		DecimalFormat df = new DecimalFormat("########0.00");
		tv_borrowCode.setText(waitReceiveDetialInfoBean.getBorrowCode());
		tv_borrowName.setText(waitReceiveDetialInfoBean.getBorrowName());
		tv_totalAmount.setText(df.format(waitReceiveDetialInfoBean.getTotalAmount())+"元");
		tv_capital.setText(df.format(waitReceiveDetialInfoBean.getCapital())+"元");
		tv_netInterest.setText(df.format(waitReceiveDetialInfoBean.getNetInterest())+"元");
		tv_netHike.setText(df.format(waitReceiveDetialInfoBean.getNetHike())+"元");
		tv_yuqipeifu.setText(df.format(waitReceiveDetialInfoBean.getLateFee())+"元");
		tv_qici.setText(waitReceiveDetialInfoBean.getPlanIndex()+"/"+waitReceiveDetialInfoBean.getDeadline()+"期");
		tv_expireTime.setText(waitReceiveDetialInfoBean.getExpireTime());
	}

	/**
	 * 设置页面组件状态
	 */
	/*private void setViewState() {
		if (state == STATE_DOING) {
			ll_creditor_rights_transf.setVisibility(View.VISIBLE);
		} else {
			ll_creditor_rights_transf.setVisibility(View.GONE);
		}
	}*/
	
	private void setDetailView(){
		if(amount_detail.getVisibility()==View.VISIBLE){
			amount_detail.setVisibility(View.GONE);
			wait_receive_amount_image.setImageResource(R.drawable.wait_receive_line_shouqi);
		}else{
			amount_detail.setVisibility(View.VISIBLE);
			wait_receive_amount_image.setImageResource(R.drawable.wait_receive_line_zhankai);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_project_detail: {
			Intent intent = new Intent();
			intent.putExtra("pid", waitReceiveDetialInfoBean.getBorrowId());
			intent.putExtra("source_flag", "waitReceiveActivity");
			intent.setClass(mContext, ProjectDetailActivity.class);
			startActivity(intent);
		}
			break;
		/*case R.id.ll_creditor_rights_transf: {
			Intent intent = new Intent();
			intent.setClass(mContext, CreditorRightsTransfActivity.class);
			startActivity(intent);
		}
			break;*/
		case R.id.item_wait_income_amount :
			setDetailView();
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
		case FLAG_GET_WAIT_RECEIVE_DETAIL:
			WaitReceiveDetailResponse response = gson.fromJson(values.toString(),WaitReceiveDetailResponse.class);
		    if(response!=null){
		    	waitReceiveDetialInfoBean = response.getWaitReceiveDetialInfoBean();
		    	if(waitReceiveDetialInfoBean!=null)
		    	{
		    	setViewData();
		    	}
		    }
		}
	}

}
