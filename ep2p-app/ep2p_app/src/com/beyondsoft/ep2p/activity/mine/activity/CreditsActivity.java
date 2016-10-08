package com.beyondsoft.ep2p.activity.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.UserPersonalInfo;

/**
 * @author Ivan.Lu
 * @description "我的"-积分
 */
public class CreditsActivity extends BaseFragmentActivity implements OnClickListener {
	private ImageView title_content_im;
	private TextView look_detail;
	private TextView credits_tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_credits);
		init();
		initListener();
	}
	
	private void init(){
		setTitle(getResources().getString(R.string.mine_credits));
		title_content_im=(ImageView) findViewById(R.id.title_content_im);
		title_content_im.setVisibility(View.VISIBLE);
		look_detail=(TextView) findViewById(R.id.look_detail);
		credits_tv=(TextView) findViewById(R.id.credits_tv);
	}
	
	private void initListener(){
		look_detail.setOnClickListener(this);
		((TextView)findViewById(R.id.vip_tv)).setOnClickListener(this);
		((TextView)findViewById(R.id.phone_bill_tv)).setOnClickListener(this);
		((TextView)findViewById(R.id.ticket_tv)).setOnClickListener(this);
		((TextView)findViewById(R.id.cash_tv)).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.vip_tv:
			startActivity(new Intent(this,VipActivity.class));
			break;
		case R.id.phone_bill_tv:
			startActivity(new Intent(this, PhoneBillExchangeActivity.class));
			break;
		case R.id.ticket_tv:
			startActivity(new Intent(this, TicketActivity.class));
			break;
		case R.id.cash_tv:
			startActivity(new Intent(this, CashActivity.class));
			break;
		case R.id.look_detail://查看明细
			startActivity(new Intent(this, CreditsExplainActivity.class));
			break;
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		credits_tv.setText(UserPersonalInfo.getAvailablePoint()+"");
	}
}
