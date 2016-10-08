package com.beyondsoft.ep2p.activity.mine.activity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.SimpleWebActivity;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.ProjectWaitDetailInfoBean;
import com.beyondsoft.ep2p.model.ProjectWaitDetailResult;
import com.beyondsoft.ep2p.model.response.ProjectWaitDetailResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 项目详情页面
 * 
 * @author hardy.zhou
 *
 */
public class ProjectDetailActivity extends BaseActivity implements OnClickListener {

	private TextView tv_title;
	private LinearLayout ll_look_agreement,ll_look_creditor;
	private LinearLayout ll_transferCreditor,ll_creditor_transfer_title;
	private LinearLayout ll_progress_bar;
	private ImageView img_into;
	private TextView tv_borrowCode;
	private TextView tv_borrowName;
	private TextView tv_borrowMoney;
    private TextView tv_borrowRate;
    private TextView tv_borDeadline;
    private TextView tv_repaymentTypeName;
    private TextView tv_intementMoney;
    private TextView tv_receivableInterest;
    private TextView tv_awardAmount;
    private TextView tv_receivableHike;
    private TextView tv_investmentTime;
    private TextView tv_receivedMoney;
    private TextView tv_dueinMoney;
    private TextView tv_receivedWek;
    private ProgressBar progressBar;
    private TextView tv_tansfer_info;
    private String source_flag;
    private final static int FLAG_GET_PROJECT_WAIT_DETAIL = 100;
	private BaseService baseService;
	private ProjectWaitDetailInfoBean projectDetailBean;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_detail);
		baseService = new BaseService();
		initView();
		doGetProjectWaitDetail();
	}
	
	private void doGetProjectWaitDetail(){
		String pid = getIntent().getStringExtra("pid");
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("pid",pid);
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		connection(baseService.getStringRequest(FLAG_GET_PROJECT_WAIT_DETAIL, URL.URL_PROJECT_WAIT_DETAIL, params, mContext));
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText(R.string.project_detail);
		ll_look_agreement = (LinearLayout) findViewById(R.id.ll_look_agreement);
		ll_transferCreditor = (LinearLayout) findViewById(R.id.ll_transferCreditor);
		ll_creditor_transfer_title = (LinearLayout) findViewById(R.id.ll_creditor_transfer_title);
		img_into = (ImageView) findViewById(R.id.img_into);
		ll_look_agreement.setOnClickListener(this);
		ll_look_creditor = (LinearLayout) findViewById(R.id.ll_look_creditor);
		ll_look_creditor.setOnClickListener(this);
		tv_tansfer_info = (TextView) findViewById(R.id.tv_tansfer_info);
	    tv_borrowCode = (TextView) findViewById(R.id.tv_borrowCode);
	    tv_borrowName = (TextView) findViewById(R.id.tv_borrowName);
	    tv_borrowMoney = (TextView) findViewById(R.id.tv_borrowMoney);
	    tv_borrowRate = (TextView) findViewById(R.id.tv_borrowRate);
	    tv_borDeadline = (TextView) findViewById(R.id.tv_borDeadline);
	    tv_repaymentTypeName = (TextView) findViewById(R.id.tv_repaymentTypeName);
	    tv_intementMoney = (TextView) findViewById(R.id.tv_intementMoney);
	    tv_receivableInterest = (TextView) findViewById(R.id.tv_receivableInterest);
	    tv_awardAmount = (TextView) findViewById(R.id.tv_awardAmount);
	    tv_receivableHike = (TextView) findViewById(R.id.tv_receivableHike);
	    tv_investmentTime = (TextView) findViewById(R.id.tv_investmentTime);
	    tv_receivedMoney = (TextView) findViewById(R.id.tv_receivedMoney);
	    tv_dueinMoney = (TextView) findViewById(R.id.tv_dueinMoney);
	    tv_receivedWek = (TextView) findViewById(R.id.tv_receivedWek);
	    progressBar = (ProgressBar) findViewById(R.id.progressBar);
	    ll_progress_bar = (LinearLayout) findViewById(R.id.ll_progress_bar);
	    
	}
	
	private void initData(){
		NumberFormat ft = NumberFormat.getPercentInstance();
		ft.setMaximumFractionDigits(2);
		DecimalFormat df = new DecimalFormat("########0.00");
		tv_borrowCode.setText(projectDetailBean.getBorrowCode());
		tv_borrowName.setText(projectDetailBean.getBorrowName());
		tv_borrowMoney.setText(df.format(projectDetailBean.getBorrowMoney())+"元");
		tv_borrowRate.setText(ft.format(projectDetailBean.getBorrowRate()));
		if(projectDetailBean.getBorrowCode().substring(0, 1).equals("X")||projectDetailBean.getBorrowCode().substring(0, 1).equals("T")){
			tv_borDeadline.setText(projectDetailBean.getBorDeadline()+"天");
		}else{
			tv_borDeadline.setText(projectDetailBean.getBorDeadline()+"个月");
		}
		tv_repaymentTypeName.setText(projectDetailBean.getRepaymentTypeName());
		tv_intementMoney.setText(df.format(projectDetailBean.getIntementMoney())+"元");
		tv_receivableInterest.setText(df.format(projectDetailBean.getReceivableInterest())+"元");
		tv_awardAmount.setText(df.format(projectDetailBean.getAwardAmount())+"元");
		tv_receivableHike.setText(df.format(projectDetailBean.getReceivableHike())+"元");
		tv_investmentTime.setText(projectDetailBean.getInvestmentTime());
		tv_receivedMoney.setText(df.format(projectDetailBean.getReceivedMoney())+"元");
		tv_dueinMoney.setText(df.format(projectDetailBean.getDueinMoney())+"元");
		tv_receivedWek.setText(projectDetailBean.getReceivedWek()+"/"+projectDetailBean.getBorDeadline()+"期");
		progressBar.setSecondaryProgress(projectDetailBean.getReceivedWek()*100/Integer.parseInt(projectDetailBean.getBorDeadline()));
		
		int transferStatus = projectDetailBean.getTranferStatus();
		if(transferStatus==0){
			ll_transferCreditor.setOnClickListener(this);
		}else if(transferStatus>0){
			tv_tansfer_info.setText(""+transferStatus+"天后可转让");
			img_into.setVisibility(View.GONE);
		}else{
			tv_tansfer_info.setText("不可转让");
			img_into.setVisibility(View.GONE);
		}
		
		source_flag = getIntent().getStringExtra("source_flag");
		if("waitReceiveActivity".equals(source_flag))
		{
			ll_progress_bar.setVisibility(View.GONE);
			ll_creditor_transfer_title.setVisibility(View.GONE);
			ll_transferCreditor.setVisibility(View.GONE);
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_look_agreement: {
			Intent intent = new Intent();
			intent.setClass(mContext, SimpleWebActivity.class);
			intent.putExtra(SimpleWebActivity.TITLE, getString(R.string.loan_agreement));
			intent.putExtra(SimpleWebActivity.URL, "http://www.baidu.com");
			startActivity(intent);
		}
			break;
		case R.id.ll_transferCreditor:{
			Intent intent = new Intent();
			intent.putExtra("borrowId",projectDetailBean.getPid());
			intent.putExtra("transferId",projectDetailBean.getTransferId());
			intent.setClass(mContext, CreditorRightsTransfActivity.class);
			startActivity(intent);
		}
		    break;
		case R.id.ll_look_creditor:
			Intent intent = new Intent();
			intent.setClass(mContext, SimpleWebActivity.class);
			intent.putExtra(SimpleWebActivity.TITLE, getString(R.string.transfer_agreement));
			intent.putExtra(SimpleWebActivity.URL, "http://www.baidu.com");
			startActivity(intent);
		default:
			break;
		}

	}
	
	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		super.onResponse(tag, values);
		switch(tag){
		case FLAG_GET_PROJECT_WAIT_DETAIL:{
			ProjectWaitDetailResponse response = gson.fromJson(values.toString(), ProjectWaitDetailResponse.class);
			if(response!=null){
			ProjectWaitDetailResult result = 	response.getProjectWaitDetailResult();
			if(result!=null){
			ProjectWaitDetailInfoBean bean = 	result.getProjectWaitDetailInfoBean();
			  if(bean!=null){
				  projectDetailBean = bean;
				  initData();
			  }
			}
			}
		}
		}
	}

}
