package com.beyondsoft.ep2p.activity.mine.activity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.SimpleWebActivity;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.ProjectFinishedDetailInfoBean;
import com.beyondsoft.ep2p.model.ProjectWaitDetailInfoBean;
import com.beyondsoft.ep2p.model.ProjectWaitDetailResult;
import com.beyondsoft.ep2p.model.response.ProjectFinishedDetailResponse;
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
 * @author Jason.Hwang
 *
 */
public class ProjectFinishedDetailActivity extends BaseActivity implements OnClickListener {

	private TextView tv_title;
	private LinearLayout ll_look_agreement,ll_look_creditor;
	private LinearLayout lll_transferCreditor;
	private LinearLayout transfer_info_detail;
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
    private TextView tv_pid;
    private TextView tv_residualPrincipal;
    private TextView tv_successAmount;
    private TextView tv_fee;
    private TextView tv_transferDeadline;
    
    private final static int FLAG_GET_PROJECT_FINISHED_DETAIL = 100;
    private boolean idTransfered = false;
	private BaseService baseService;
	private ProjectFinishedDetailInfoBean projectDetailBean;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_finished_detail);
		baseService = new BaseService();
		initView();
		doGetProjectWaitDetail();
	}
	
	private void doGetProjectWaitDetail(){
		String pid = getIntent().getStringExtra("pid");
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("borrowId",pid);
		params.put("transferId",getIntent().getStringExtra("transferId"));
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		connection(baseService.getStringRequest(FLAG_GET_PROJECT_FINISHED_DETAIL, URL.URL_PROJECT_FINISHED_DETAIL, params, mContext));
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText(R.string.project_detail);
		ll_look_agreement = (LinearLayout) findViewById(R.id.ll_look_agreement);
		ll_look_creditor = (LinearLayout) findViewById(R.id.ll_look_creditor);
		ll_look_agreement.setOnClickListener(this);
		ll_look_creditor.setOnClickListener(this);
		transfer_info_detail = (LinearLayout) findViewById(R.id.transfer_info_detail);
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
	    tv_receivedWek = (TextView) findViewById(R.id.tv_receivedWek);
	    progressBar = (ProgressBar) findViewById(R.id.progressBar);
	    tv_pid = (TextView) findViewById(R.id.tv_pid);
	    tv_residualPrincipal = (TextView) findViewById(R.id.tv_residualPrincipal);
	    tv_successAmount = (TextView) findViewById(R.id.tv_successAmount);
	    tv_fee = (TextView) findViewById(R.id.tv_fee);
	    tv_transferDeadline = (TextView) findViewById(R.id.tv_transferDeadline);
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
			tv_borDeadline.setText(projectDetailBean.getDeadline()+"天");
		}else{
			tv_borDeadline.setText(projectDetailBean.getDeadline()+"个月");
		}
		
		tv_repaymentTypeName.setText(projectDetailBean.getRepaymentTypeName());
		tv_intementMoney.setText(df.format(projectDetailBean.getInvestmentAmount())+"元");
		tv_receivableInterest.setText(df.format(projectDetailBean.getInterest())+"元");
		tv_awardAmount.setText(df.format(projectDetailBean.getInvestmentReward())+"元");
		tv_receivableHike.setText(df.format(projectDetailBean.getHike())+"元");
		tv_investmentTime.setText(projectDetailBean.getInvestmentTime().split(" ")[0].trim());
		tv_receivedMoney.setText(df.format(projectDetailBean.getTotalAmount())+"元");
		progressBar.setSecondaryProgress(projectDetailBean.getReceivedDeadline()*100/Integer.parseInt(projectDetailBean.getDeadline()));
		tv_receivedWek.setText(projectDetailBean.getReceivedDeadline()+"/"+projectDetailBean.getDeadline()+"期");
	}	
	
	private void showTransferView(){
		transfer_info_detail.setVisibility(View.VISIBLE);
		tv_pid.setText(projectDetailBean.getBrt().getPid());
		tv_residualPrincipal.setText(""+projectDetailBean.getBrt().getResidualPrincipal());
		tv_successAmount.setText(""+projectDetailBean.getBrt().getSuccessAmount());
		tv_fee.setText(""+projectDetailBean.getBrt().getFee());
		tv_transferDeadline.setText(projectDetailBean.getTransferDeadline());
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
		case R.id.ll_look_creditor:{
			Intent intent = new Intent();
			intent.setClass(mContext, SimpleWebActivity.class);
			intent.putExtra(SimpleWebActivity.TITLE, getString(R.string.transfer_agreement));
			intent.putExtra(SimpleWebActivity.URL, "http://www.baidu.com");
			startActivity(intent);
		}
		break;
		case R.id.ll_transferCreditor:{
			Intent intent = new Intent();
			intent.setClass(mContext, CreditorRightsTransfActivity.class);
			startActivity(intent);
		}
		default:
			break;
		}

	}
	
	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		super.onResponse(tag, values);
		switch(tag){
		case FLAG_GET_PROJECT_FINISHED_DETAIL:
			ProjectFinishedDetailResponse response = gson.fromJson(values.toString(), ProjectFinishedDetailResponse.class);
			if(response!=null){
				ProjectFinishedDetailInfoBean infoBean = response.getProjectFinishedDetailInfoBean();
				if(infoBean!=null){
					projectDetailBean = infoBean;
					initData();
					if(infoBean.getBrt()!=null){
						showTransferView();
					}
				}
			}
			break;
			
			default:
				break;
		
		}
	}

}
