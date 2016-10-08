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
import com.beyondsoft.ep2p.adapter.PhoneBillExchangeAdapter;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.response.ExchangeResponse;
import com.beyondsoft.ep2p.service.SecurityCenterService;

/**
 * @author Ivan.Lu
 * @description 话费兑换
 */
public class PhoneBillExchangeActivity extends BaseFragmentActivity implements OnItemClickListener {

	private static final int GETPHONEBILLLIST=1;
	private ListView phone_bill_lv;
	private PhoneBillExchangeAdapter phoneBillExchangeAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phone_bill_exchange);
		init();
		initListener();
		loadData();
	}


	private void init(){
		setTitle(getResources().getString(R.string.credits_phone_bill_title));
		phoneBillExchangeAdapter=new PhoneBillExchangeAdapter(this);
		phone_bill_lv=(ListView) findViewById(R.id.phone_bill_lv);
		phone_bill_lv.setAdapter(phoneBillExchangeAdapter);
	}

	private void initListener() {
		phone_bill_lv.setOnItemClickListener(this);
	}
	
	private void loadData(){
		SecurityCenterService securityCenterService=new SecurityCenterService();
		HashMap<String,Object> hashMap=new HashMap<String, Object>();
		hashMap.put("dictCode",Constants.EXCHANGE_TELEPHONE_FARE);
		connection(securityCenterService.getExchangeIntegral(GETPHONEBILLLIST, hashMap, this));
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		pushActivity(new Intent(this,PhoneBillExchangeDetailActivity.class).putExtra("detail", phoneBillExchangeAdapter.getExchangeDetail(position)));
	}


	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		ExchangeResponse response=gson.fromJson(values.toString(),ExchangeResponse.class);
		phoneBillExchangeAdapter.addData(response.getResult().getList());
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
	}
}

