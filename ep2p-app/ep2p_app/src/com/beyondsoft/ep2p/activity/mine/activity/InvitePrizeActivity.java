package com.beyondsoft.ep2p.activity.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.response.InvitePrizeResponse;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.view.SharePopupWindow;
import com.umeng.socialize.UMShareAPI;

/**
 * @author Ivan.Lu
 * @description "我的"-邀请有奖
 */
public class InvitePrizeActivity extends BaseFragmentActivity implements OnClickListener {
	
	private static final int GET_INVITECODE=1;
	private TextView invite_code_tv;
	private SharePopupWindow sharePopupWindowLine;
	private SecurityCenterService securityCenterService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite_prize);
		securityCenterService=new SecurityCenterService();
		init();
		initListener();
		initData();
	}
	
	private void init(){
		setTitle(getResources().getString(R.string.mine_invite_prize));
		setRightTitle(getResources().getString(R.string.prize_title_right));
		invite_code_tv=(TextView) findViewById(R.id.invite_code_tv);
		((TextView) findViewById(R.id.title_right)).setOnClickListener(this);
		((Button)findViewById(R.id.invite_friend_btn)).setOnClickListener(this);
	}
	
	private void initListener(){
		
	}
	
	private void initData(){
		connection(securityCenterService.getInviteCode(GET_INVITECODE, null, this));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.title_right:
			startActivity(new Intent(this, MyInviteListActivity.class));
			break;
		case R.id.invite_friend_btn:
			sharePopupWindowLine=new SharePopupWindow(this);
			sharePopupWindowLine.showWindow();
			sharePopupWindowLine.setOnDismissListener(new OnDismissListener() {
				
				@Override
				public void onDismiss() {
				}
			});
			sharePopupWindowLine.showAtLocation(findViewById(R.id.invite_friend_btn),Gravity.BOTTOM,0,0);
			break;
		}
	}

	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		InvitePrizeResponse invitePrizeResponse=gson.fromJson(values.toString(), InvitePrizeResponse.class);
		invite_code_tv.setText(invitePrizeResponse.getResult().getReferralCode());
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		 UMShareAPI.get(this).onActivityResult( requestCode, resultCode, data);
	}
}
