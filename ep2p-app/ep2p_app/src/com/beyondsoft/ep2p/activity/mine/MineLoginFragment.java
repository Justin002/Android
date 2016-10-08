package com.beyondsoft.ep2p.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import com.beyondsoft.ep2p.BaseFragment;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.LoginActivity;
import com.beyondsoft.ep2p.activity.home.AuthenticationActivity;
import com.beyondsoft.ep2p.activity.mine.activity.AssetStatisticsActivity;
import com.beyondsoft.ep2p.activity.mine.activity.BankAuthenticationActivity;
import com.beyondsoft.ep2p.activity.mine.activity.BankCardActivity;
import com.beyondsoft.ep2p.activity.mine.activity.CardPackageActivity;
import com.beyondsoft.ep2p.activity.mine.activity.CreditsActivity;
import com.beyondsoft.ep2p.activity.mine.activity.PersonalInfomationActivity;
import com.beyondsoft.ep2p.activity.mine.activity.RechargeActivity;
import com.beyondsoft.ep2p.activity.mine.activity.RechargeTimesActivity;
import com.beyondsoft.ep2p.activity.mine.activity.WithdrawalActivity;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.BankCardInfoBean;
import com.beyondsoft.ep2p.model.BankCardInfoListBean;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.model.response.BankCardListResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.widget.CustomDialog;
import com.beyondsoft.ep2p.widget.CustomDialog.ButtonOnClickListener;

/**
 * @author Ivan.Lu
 * @description "我的"-已登录
 */
public class MineLoginFragment extends BaseFragment implements OnClickListener {

	private TextView total_money_tv;
	private TextView balance_money_tv;
	private TextView card_package_tv;
	private TextView credit_tv;
	private TextView bank_card_tv;
	
	private BaseService baseService;
	private static final int TAG_GET_BANK_LIST = 100;
	private static final int TAG_BIND_BINK_INFO = 200;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_mine_login_fragment, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		init();
		initListener();
	}

	private void init() {
		total_money_tv=(TextView) getActivity().findViewById(R.id.total_money_tv);
		balance_money_tv=(TextView) getActivity().findViewById(R.id.balance_money_tv);
		card_package_tv=(TextView) getActivity().findViewById(R.id.card_package_tv);
		credit_tv=(TextView) getActivity().findViewById(R.id.credit_tv);
		bank_card_tv=(TextView) getActivity().findViewById(R.id.bank_card_tv);
		baseService = new BaseService();
	}
	
	private void initListener(){
		((TextView) mContext.findViewById(R.id.top_up_tv)).setOnClickListener(this);
		((TextView) mContext.findViewById(R.id.withdrawal_tv)).setOnClickListener(this);
		((LinearLayout) mContext.findViewById(R.id.personal_info_layout)).setOnClickListener(this);
		((LinearLayout) mContext.findViewById(R.id.ll_asset_info)).setOnClickListener(this);
		((LinearLayout)getActivity().findViewById(R.id.card_package_layout)).setOnClickListener(this);
		((LinearLayout)getActivity().findViewById(R.id.credits_layout)).setOnClickListener(this);
		((LinearLayout)getActivity().findViewById(R.id.bank_card_layout)).setOnClickListener(this);
	}
	
	private void initData(){
		total_money_tv.setText(getResources().getString(R.string.mine_balance_hint,StringUtils.formatMoney(UserPersonalInfo.getTotalAssets())));
		balance_money_tv.setText(getResources().getString(R.string.mine_balance,StringUtils.formatMoney(UserPersonalInfo.getAvailableBalance())));
		card_package_tv.setText(Html.fromHtml("<u>"+UserPersonalInfo.getCardVoucherCount()+"<u/>"));
		credit_tv.setText(Html.fromHtml("<u>"+UserPersonalInfo.getAvailablePoint()+"</u>"));
		bank_card_tv.setText(Html.fromHtml("<u>"+UserPersonalInfo.getBankCount()+"</u>"));
	}
	
	private void doGetBankCardListRequest(int tag) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		connection(baseService.getStringRequest(tag, URL.URL_BANK_CARD_LIST, params,this, mContext));
	}

	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_up_tv: {
			if(!"".equals(CommonUtils.getStringByKey(getActivity(),Constants.EP2P_TOKEN))){

				if ("2".equals(UserPersonalInfo.getIsAttestation())) {
					
					CustomDialog topUpDialog = new CustomDialog(mContext);
					topUpDialog.setButtonClickListener(new ButtonOnClickListener() {
						
						@Override
						public void onButton2Click(View v) {
							
							startActivity(new Intent(mContext, AuthenticationActivity.class));
						}
						
						@Override
						public void onButton1Click(View v) {
							
						}
					});
					topUpDialog.show();
					topUpDialog.setTitle("提示");
					topUpDialog.setDescri("为了您的资金安全，请先进行实名登记");
					topUpDialog.hideView();
					topUpDialog.setButtonText("取消", "去认证");
				} else {
//					if(UserPersonalInfo.getIsFirstPay() == 0){
//						startActivity(new Intent(mContext, RechargeActivity.class));
//					}else{
						doGetBankCardListRequest(TAG_BIND_BINK_INFO);
//						startActivity(new Intent(mContext, RechargeTimesActivity.class));
//					}
				}
			}else{
				pushActivityForResult(LoginActivity.class,MineNoLoginFragment.LOGIN_REQUESTCODE);
			}
		}
			break;
		case R.id.withdrawal_tv: {
			if ("2".equals(UserPersonalInfo.getIsAttestation())) {

				CustomDialog topUpDialog = new CustomDialog(mContext);
				topUpDialog.setButtonClickListener(new ButtonOnClickListener() {

					@Override
					public void onButton2Click(View v) {

						startActivity(new Intent(mContext, AuthenticationActivity.class));
					}

					@Override
					public void onButton1Click(View v) {
						
					}
				});
				topUpDialog.show();
				topUpDialog.setTitle("提示");
				topUpDialog.setDescri("为了您的资金安全，请先进行实名登记");
				topUpDialog.setButtonText("取消", "去认证");
			} else {
				doGetBankCardListRequest(TAG_GET_BANK_LIST);
			}
			
		}
			break;
		case R.id.personal_info_layout: {
			pushActivityForResult(PersonalInfomationActivity.class, MineNoLoginFragment.LOGIN_REQUESTCODE);
		}
			break;
		case R.id.ll_asset_info: {
			startActivity(new Intent(mContext, AssetStatisticsActivity.class));
		}
			break;
		case R.id.card_package_layout://卡包
			startActivity(new Intent(getActivity(), CardPackageActivity.class));
			break;
			
		case R.id.credits_layout:
			startActivity(new Intent(getActivity(), CreditsActivity.class));
			break;
			
		case R.id.bank_card_layout:
			if ("2".equals(UserPersonalInfo.getIsAttestation())) {

				CustomDialog topUpDialog = new CustomDialog(mContext);
				topUpDialog.setButtonClickListener(new ButtonOnClickListener() {

					@Override
					public void onButton2Click(View v) {

						startActivity(new Intent(mContext, AuthenticationActivity.class));
					}

					@Override
					public void onButton1Click(View v) {
						
					}
				});
				topUpDialog.show();
				topUpDialog.setTitle("提示");
				topUpDialog.setDescri("为了您的资金安全，请先进行实名登记");
				topUpDialog.setButtonText("取消", "去认证");
			} else {
				startActivity(new Intent(getActivity(), BankCardActivity.class));
			}
			break;
		}
	}
	
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initData();
	}
	
	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		super.onResponse(tag, values);
		switch(tag){
		case TAG_GET_BANK_LIST:{
			BankCardListResponse bankCardListResponse = gson.fromJson(values.toString(), BankCardListResponse.class);
			if (null != bankCardListResponse) {
				BankCardInfoListBean bankCardInfoListBean = bankCardListResponse.getBankCardInfoListBean();
				if (null != bankCardInfoListBean) {
					ArrayList<BankCardInfoBean> bankCardInfoBeanList = bankCardInfoListBean.getBankCardInfoBeanList();
					if (null != bankCardInfoBeanList && bankCardInfoBeanList.size() > 0) {
						BankCardInfoBean bankCardInfoBean = bankCardInfoBeanList.get(0);
						Intent intent = new Intent();
						intent.putExtra("bankcardinfo", bankCardInfoBean);
						String withdrawals = bankCardInfoBean.getIsWithdrawals();
						if("1".equals(withdrawals))
						{
							intent.setClass(mContext, WithdrawalActivity.class);
							//intent.setClass(mContext, BankAuthenticationActivity.class);
							startActivity(intent);
						}else if("0".equals(withdrawals))
						{
							/*intent.setClass(mContext, BankAuthenticationActivity.class);
						    startActivity(intent);*/
							CustomDialog topUpDialog = new CustomDialog(mContext);
							topUpDialog.setButtonClickListener(new ButtonOnClickListener() {

								@Override
								public void onButton2Click(View v) {

									startActivity(new Intent(mContext, BankAuthenticationActivity.class));
								}

								@Override
								public void onButton1Click(View v) {
									
								}
							});
							topUpDialog.show();
							topUpDialog.setTitle("提示");
							topUpDialog.setDescri("请完善银行卡信息");
							topUpDialog.setButtonText("取消", "确定");
						}    
					}else
					{
						//CommonUtils.toastMsgShort(mContext, getResources().getString(R.string.please_add_bank_card));
						CustomDialog topUpDialog = new CustomDialog(mContext);
						topUpDialog.setButtonClickListener(new ButtonOnClickListener() {

							@Override
							public void onButton2Click(View v) {

								startActivity(new Intent(mContext, BankCardActivity.class));
							}

							@Override
							public void onButton1Click(View v) {
								
							}
						});
						topUpDialog.show();
						topUpDialog.setTitle("提示");
						topUpDialog.setDescri("请完善银行卡信息");
						topUpDialog.setButtonText("取消", "确定");
					}
				}
			}}
			break;
		case TAG_BIND_BINK_INFO:{
			BankCardListResponse bankCardListResponse = gson.fromJson(values.toString(), BankCardListResponse.class);
			if (null != bankCardListResponse) {
				BankCardInfoListBean bankCardInfoListBean = bankCardListResponse.getBankCardInfoListBean();
				if (null != bankCardInfoListBean) {
					ArrayList<BankCardInfoBean> bankCardInfoBeanList = bankCardInfoListBean.getBankCardInfoBeanList();
					if (null != bankCardInfoBeanList && bankCardInfoBeanList.size() > 0) {
						BankCardInfoBean bankCardInfoBean = bankCardInfoBeanList.get(0);
						if(UserPersonalInfo.getIsFirstPay() == 0){
							Intent intent = new Intent();
							intent.putExtra("bankcardinfo", bankCardInfoBean);
							intent.setClass(mContext, RechargeActivity.class);
							startActivity(intent);
						}else{
							Intent intent = new Intent();
							intent.putExtra("bankcardinfo", bankCardInfoBean);
							intent.setClass(mContext, RechargeTimesActivity.class);
							startActivity(intent);
						}
					}else{
						startActivity(new Intent(mContext, RechargeActivity.class));
					}
				}
			}}
			break;
			default: 
				break;
		}
	}
	
	@Override
	public void onErrorResponse(int tag, String error) {
		super.onErrorResponse(tag, error);
		switch (tag) {
		case TAG_BIND_BINK_INFO:
			CommonUtils.toastMsgShort(mContext, error);
			break;

		default:
			break;
		}
	}
}
