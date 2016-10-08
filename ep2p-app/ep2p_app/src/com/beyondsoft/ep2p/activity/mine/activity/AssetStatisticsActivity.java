package com.beyondsoft.ep2p.activity.mine.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.response.AssetStatisticsResponse;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.StringUtils;


/**
 * @author Ivan.Lu
 * @description 资产统计页面
 */
public class AssetStatisticsActivity extends BaseFragmentActivity {

	private static final int GETPERSONALASSETS=1;
	private TextView availableBalance_tv;
	private TextView freezingAmount_tv;
	private TextView dueinAmount_tv;
	private TextView dueinInterest_tv;
	private TextView investInterest_tv;
	private TextView rateInterest_tv;
	private TextView investmentIncentive_tv;
	private TextView redEnvelope_tv;
	private TextView recommendedAwards_tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asset_statistics);
		initView();
		loadData();
	}

	/**
	 * 初始化组件爱
	 */
	private void initView() {
		setTitle(getResources().getString(R.string.asset_statistics));
		availableBalance_tv=(TextView) findViewById(R.id.availableBalance_tv);
		freezingAmount_tv=(TextView) findViewById(R.id.freezingAmount_tv);
		dueinAmount_tv=(TextView) findViewById(R.id.dueinAmount_tv);
		dueinInterest_tv=(TextView) findViewById(R.id.dueinInterest_tv);
		investInterest_tv=(TextView) findViewById(R.id.investInterest_tv);
		rateInterest_tv=(TextView) findViewById(R.id.rateInterest_tv);
		investmentIncentive_tv=(TextView) findViewById(R.id.investmentIncentive_tv);
		redEnvelope_tv=(TextView) findViewById(R.id.redEnvelope_tv);
		recommendedAwards_tv=(TextView) findViewById(R.id.recommendedAwards_tv);
	}
	
	private void loadData(){
		SecurityCenterService securityCenterService=new SecurityCenterService();
		connection(securityCenterService.getPersonalAssets(GETPERSONALASSETS, this));
	}

	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		AssetStatisticsResponse response=gson.fromJson(values.toString(), AssetStatisticsResponse.class);
		availableBalance_tv.setText(getResources().getString(R.string.mine_balance_hint,StringUtils.formatMoney(response.getResult().getAccountinfovo().getAvailableBalance())));
		freezingAmount_tv.setText(getResources().getString(R.string.mine_balance_hint,StringUtils.formatMoney(response.getResult().getAccountinfovo().getFreezingAmount())));
		dueinAmount_tv.setText(getResources().getString(R.string.mine_balance_hint,StringUtils.formatMoney(response.getResult().getAccountinfovo().getDueinAmount())));
		dueinInterest_tv.setText(getResources().getString(R.string.mine_balance_hint,StringUtils.formatMoney(response.getResult().getAccountinfovo().getDueinInterest())));
		investInterest_tv.setText(getResources().getString(R.string.mine_balance_hint,StringUtils.formatMoney(response.getResult().getAccountinfovo().getInvestInterest())));
		rateInterest_tv.setText(getResources().getString(R.string.mine_balance_hint,StringUtils.formatMoney(response.getResult().getAccountinfovo().getRateInterest())));
		investmentIncentive_tv.setText(getResources().getString(R.string.mine_balance_hint,StringUtils.formatMoney(response.getResult().getAccountinfovo().getInvestmentIncentive())));
		redEnvelope_tv.setText(getResources().getString(R.string.mine_balance_hint,StringUtils.formatMoney(response.getResult().getAccountinfovo().getRedEnvelope())));
		recommendedAwards_tv.setText(getResources().getString(R.string.mine_balance_hint,StringUtils.formatMoney(response.getResult().getAccountinfovo().getRecommendedAwards())));
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
	}
}
