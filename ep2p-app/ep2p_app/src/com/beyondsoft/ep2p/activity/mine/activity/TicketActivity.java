package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.beyondsoft.ep2p.ActivityManager;
import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.adapter.TicketAdapter;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.response.ExchangeResponse;
import com.beyondsoft.ep2p.service.SecurityCenterService;

/**
 * @author Ivan.Lu
 * @description 加息券
 */
public class TicketActivity extends BaseFragmentActivity implements OnItemClickListener {
	
	private static final int GETTICKETLIST=1;
	private ListView ticket_lv;
	private TicketAdapter ticketAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket);
		init();
		initListener();
		loadData();
	}

	private void init(){
		setTitle(getResources().getString(R.string.credits_ticket_title));
		ticketAdapter=new TicketAdapter(this);
		ticket_lv=(ListView) findViewById(R.id.ticket_lv);
		ticket_lv.setAdapter(ticketAdapter);
	}
	private void initListener() {
		ticket_lv.setOnItemClickListener(this);
	}
	
	private void loadData(){
		SecurityCenterService securityCenterService=new SecurityCenterService();
		HashMap<String,Object> hashMap=new HashMap<String, Object>();
		hashMap.put("dictCode",Constants.EXCHANGE_INTEREST_TICKET);
		connection(securityCenterService.getExchangeIntegral(GETTICKETLIST, hashMap, this));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		pushActivity(new Intent(this,TicketExchangeDetailActivity.class).putExtra("detail", ticketAdapter.getExchangeDetail(position)));
	}



	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		ExchangeResponse response=gson.fromJson(values.toString(),ExchangeResponse.class);
		if(response.getResult().getList()!=null){
			if(response.getResult().getList().size()>0){
				for (int i = 0; i <response.getResult().getList().size(); i++) {
					response.getResult().getList().get(i).setInterestTicketValidity(response.getResult().getInterestTicketValidity());
				}
			}
		}
		ticketAdapter.addData(response.getResult().getList());
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
	}
	
	
}
