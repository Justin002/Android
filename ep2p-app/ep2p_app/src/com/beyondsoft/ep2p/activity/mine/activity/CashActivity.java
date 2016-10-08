package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.adapter.CashAdapter;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.response.ExchangeResponse;
import com.beyondsoft.ep2p.service.SecurityCenterService;


/**
 * @author Ivan.Lu
 * @description 现金
 */
public class CashActivity extends BaseFragmentActivity implements OnItemClickListener{
	
	private static final int GETCASHLIST=1;
	private ListView cash_lv;
	private CashAdapter cashAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cash);
		init();
		initListener();
		loadData();
	}

	private void init(){
		setTitle(getResources().getString(R.string.credits_cash_title));
		cashAdapter=new CashAdapter(this);
		cash_lv=(ListView) findViewById(R.id.cash_lv);
		cash_lv.setAdapter(cashAdapter);
	}
	private void initListener() {
		cash_lv.setOnItemClickListener(this);
	}
	
	private void loadData(){
		SecurityCenterService securityCenterService=new SecurityCenterService();
		HashMap<String,Object> hashMap=new HashMap<String, Object>();
		hashMap.put("dictCode",Constants.EXCHANGE_CASH);
		connection(securityCenterService.getExchangeIntegral(GETCASHLIST, hashMap, this));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		pushActivity(new Intent(this,CashExchangeDetailActivity.class).putExtra("detail", cashAdapter.getExchangeDetail(position)));
	}
	
	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		ExchangeResponse response=gson.fromJson(values.toString(),ExchangeResponse.class);
		cashAdapter.addData(response.getResult().getList());
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
	}
	
}
