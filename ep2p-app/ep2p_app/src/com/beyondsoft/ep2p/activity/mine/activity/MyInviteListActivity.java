package com.beyondsoft.ep2p.activity.mine.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.adapter.InviteListAdapter;
import com.beyondsoft.ep2p.model.response.InvitePrizeListResponse;
import com.beyondsoft.ep2p.service.SecurityCenterService;

/**
 * @author Ivan.Lu
 * @description "我的"-我的邀请
 */
public class MyInviteListActivity extends BaseFragmentActivity implements OnItemClickListener{
	
	private static final int GET_INVITELIST=1;
	private ListView invite_lv;
	private InviteListAdapter inviteListAdapter;
	private SecurityCenterService securityCenterService;
	private LinearLayout ll_invent_null;
	private LinearLayout ll_tab;
	private View view_invent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mine_my_invite);
		securityCenterService=new SecurityCenterService();
		init();
		initListener();
		initData();
	}
	
	private void init(){
		setTitle(getResources().getString(R.string.prize_title_right));
		inviteListAdapter=new InviteListAdapter(this);
		invite_lv=(ListView) findViewById(R.id.invite_lv);
		ll_invent_null=(LinearLayout) findViewById(R.id.ll_invent_null);
		view_invent=findViewById(R.id.view_invent);
		ll_tab=(LinearLayout) findViewById(R.id.ll_tab);
		invite_lv.setOnItemClickListener(this);
		invite_lv.setAdapter(inviteListAdapter);
	}
	
	private void initListener(){
		
	}
	
	private void initData(){
		connection(securityCenterService.getInviteList(GET_INVITELIST, null, this));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		InvitePrizeListResponse invitePrizeListResponse=gson.fromJson(values.toString(), InvitePrizeListResponse.class);
		inviteListAdapter.addData(invitePrizeListResponse.getResult().getList());
		if("".equals(invitePrizeListResponse.getResult().getList())||invitePrizeListResponse.getResult().getList()==null){
			ll_invent_null.setVisibility(View.VISIBLE);
			ll_tab.setVisibility(View.GONE);
			view_invent.setVisibility(View.GONE);
		}else {
			ll_invent_null.setVisibility(View.GONE);
			view_invent.setVisibility(View.VISIBLE);
			ll_tab.setVisibility(View.VISIBLE);
			
		}
		inviteListAdapter.notifyDataSetChanged();
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
	}
}
