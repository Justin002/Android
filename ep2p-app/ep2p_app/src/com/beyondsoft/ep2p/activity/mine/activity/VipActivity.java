package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.beyondsoft.ep2p.ActivityManager;
import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.adapter.VipExchangeAdapter;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.response.ExchangeResponse;
import com.beyondsoft.ep2p.service.SecurityCenterService;

/**
 * @author Ivan.Lu
 * @description VIP兑换
 */
public class VipActivity extends BaseFragmentActivity implements OnItemClickListener {
	
	private static final int GETVIPLIST=1;
	private ListView vip_exchange_lv;
	private VipExchangeAdapter vipExchangeAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vip);
		init();
		initListener();
		loadData();
	}

	private void init(){
		setTitle(getResources().getString(R.string.credits_vip_echange));
		vipExchangeAdapter=new VipExchangeAdapter(this);
		vip_exchange_lv=(ListView) findViewById(R.id.vip_exchange_lv);
		vip_exchange_lv.setAdapter(vipExchangeAdapter);
	}
	
	private void initListener() {
		vip_exchange_lv.setOnItemClickListener(this);
	}
	
	private void loadData(){
		SecurityCenterService securityCenterService=new SecurityCenterService();
		HashMap<String,Object> hashMap=new HashMap<String, Object>();
		hashMap.put("dictCode",Constants.EXCHANGE_VIP);
		connection(securityCenterService.getExchangeIntegral(GETVIPLIST, hashMap, this));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		pushActivity(new Intent(this,VipExchangeDetailActivity.class).putExtra("detail", vipExchangeAdapter.getExchangeDetail(position)));
	}

	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		ExchangeResponse response=gson.fromJson(values.toString(),ExchangeResponse.class);
		vipExchangeAdapter.addData(response.getResult().getList());
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
	}
}
