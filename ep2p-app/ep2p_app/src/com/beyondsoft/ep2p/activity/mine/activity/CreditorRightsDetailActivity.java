package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.HashMap;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.SimpleWebActivity;
import com.beyondsoft.ep2p.activity.core.MainHolder;
import com.beyondsoft.ep2p.activity.home.CreditorRightsDetailsActivity;
import com.beyondsoft.ep2p.activity.home.HomeProductDetailsActivity;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.TransferDetailInfoBean;
import com.beyondsoft.ep2p.model.response.TransferDetailResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.utils.URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 转让详情页面
 * 
 * @author Jason.Hwang
 *
 */
public class CreditorRightsDetailActivity extends BaseActivity implements OnClickListener {

	private BaseService baseService;
    private static final int FLAG_GET_TRANSFER_DETAIL = 100;
	private TextView title_content_tv;
	private LinearLayout ll_look_borrow_detail,ll_look_transfer_detail,ll_look_agreement,ll_look_creditor;

	private TextView tv_borrowCode;
	private TextView tv_borrowMoney;
	private TextView tv_qici;
	private TextView tv_alreadyBenxi;
	private TextView tv_surperCapital;
	private TextView tv_transferCode;
	private TextView tv_transferCaptital;
	private TextView tv_transferAmount;
	private TextView tv_transferFee;
	
	private TransferDetailInfoBean bean = new TransferDetailInfoBean();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creditor_rights_detail);
		baseService = new BaseService();
		initView();
		getTransferDetail();
	}

	

	/**
	 * 初始化组件
	 */
	private void initView() {
		title_content_tv = (TextView) findViewById(R.id.title_content_tv);	
		title_content_tv.setText("转让详情");
		
		tv_borrowCode = (TextView) findViewById(R.id.tv_borrowCode);
		tv_borrowMoney = (TextView) findViewById(R.id.tv_borrowMoney);
		tv_qici = (TextView) findViewById(R.id.tv_qici);
		tv_alreadyBenxi = (TextView) findViewById(R.id.tv_alreadyBenxi);
		tv_surperCapital = (TextView) findViewById(R.id.tv_surperCapital);
		tv_transferCode = (TextView) findViewById(R.id.tv_transferCode);
		tv_transferCaptital = (TextView) findViewById(R.id.tv_transferCaptital);
		tv_transferAmount = (TextView) findViewById(R.id.tv_transferAmount);
		tv_transferFee = (TextView) findViewById(R.id.tv_transferFee);
		
		ll_look_borrow_detail = (LinearLayout) findViewById(R.id.ll_look_borrow_detail);
		ll_look_transfer_detail = (LinearLayout) findViewById(R.id.ll_look_transfer_detail);
		ll_look_agreement = (LinearLayout) findViewById(R.id.ll_look_agreement);
		ll_look_creditor = (LinearLayout) findViewById(R.id.ll_look_creditor);
		ll_look_borrow_detail.setOnClickListener(this);
		ll_look_transfer_detail.setOnClickListener(this);
		ll_look_agreement.setOnClickListener(this);
		ll_look_creditor.setOnClickListener(this);
		
	}

	private void initData(){
		tv_borrowCode.setText(bean.getBorrowCode());
		tv_borrowMoney.setText(StringUtils.formatMoney(bean.getInvestCapital())+"元");
		tv_qici.setText(bean.getAlreadyDead()+"/"+bean.getTotalDead());
		tv_alreadyBenxi.setText(StringUtils.formatMoney(bean.getAlreadyBenxi())+"元");
		tv_surperCapital.setText(StringUtils.formatMoney(bean.getSurperCapital())+"元");
		tv_transferCode.setText(bean.getTransferCode());
		tv_transferCaptital.setText(StringUtils.formatMoney(bean.getSurperCapital())+"元");
		tv_transferAmount.setText(StringUtils.formatMoney(bean.getTransferAmount())+"元");
		tv_transferFee.setText(StringUtils.formatMoney(bean.getTransferFee())+"元");
	}

	private void getTransferDetail(){
		Intent inetnt = getIntent();
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		params.put("borrowId",inetnt.getStringExtra("borrowId"));
		params.put("transferId",inetnt.getStringExtra("transferId"));
		connection(baseService.getStringRequest(FLAG_GET_TRANSFER_DETAIL, URL.URL_PROJECT_TRANSFER_DETAIL, params, mContext));
	}

	
	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		super.onResponse(tag, values);
		switch(tag){
		case FLAG_GET_TRANSFER_DETAIL:
			TransferDetailResponse response = gson.fromJson(values.toString(), TransferDetailResponse.class);
			if(response!=null){
				TransferDetailInfoBean transferDetailInfoBean =response.getTransferDetailInfoBean();
				if(transferDetailInfoBean!=null){
					bean = transferDetailInfoBean;
					initData();
				}
			}
			break;
			
			default:
				break;
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
		case R.id.ll_look_creditor:{
			Intent intent = new Intent();
			intent.setClass(mContext, SimpleWebActivity.class);
			intent.putExtra(SimpleWebActivity.TITLE, getString(R.string.transfer_agreement));
			intent.putExtra(SimpleWebActivity.URL, "http://www.baidu.com");
			startActivity(intent);
		}
			break;
		case R.id.ll_look_borrow_detail:
			Intent intent = new Intent();
			if("D".equals(bean.getBorrowCode().substring(0, 1))){
				//显示四个页面
				intent.putExtra(Constants.EP2P, 2);
				MainHolder.Instance(mContext).setProjectInformation2ejh_pid(bean.getBorrowId());
				MainHolder.Instance(mContext).setProjectInformation_Pid(bean.getBorrowId());
			}else if("C".equals(bean.getBorrowCode().substring(0, 1)))
			{
				//显示两个页面
				intent.putExtra(Constants.EP2P, 1);
				MainHolder.Instance(mContext).setProjectInformation2ejh_pid(bean.getBorrowId());
			}
			intent.putExtra(Constants.EJH_PID, bean.getBorrowId());
			//intent.putExtra("index_data", arg2+4);
			intent.setClass(mContext, HomeProductDetailsActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_look_transfer_detail:
			 intent = new Intent(mContext, CreditorRightsDetailsActivity.class);
	            intent.putExtra(Constants.EP2P, 3);
	            intent.putExtra("transferId",bean.getTransferId());
	            pushActivity(intent);
			break;
		default:
			break;
		}

	}
}
