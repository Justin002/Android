package com.beyondsoft.ep2p.activity.mine.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyondsoft.ep2p.ActivityManager;
import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.UserPersonalInfo;

/**
 * @description 加息卷兑换成功
 */
public class TicketDetailSuccessActivity extends BaseFragmentActivity implements OnClickListener{
	private TextView title_text_sure;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket_success);
		init();
		setListener();
		initData();
	}

	private void init(){
		setTitle(getResources().getString(R.string.credits_ticket_title));
		title_text_sure=(TextView) findViewById(R.id.title_text_sure);
		title_text_sure.setVisibility(View.VISIBLE);
	}
	
	private void setListener() {
		title_text_sure.setOnClickListener(this);
	}
	
	private void initData(){
		((TextView)findViewById(R.id.tv_ticket_year)).setText("+"+getIntent().getStringExtra("percentage")+"%");
		UserPersonalInfo.setAvailablePoint(UserPersonalInfo.getAvailablePoint()-getIntent().getIntExtra("score",0));
		UserPersonalInfo.setCardVoucherCount(UserPersonalInfo.getCardVoucherCount()+1);
	}


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		ActivityManager activityManager=ActivityManager.getInstance();
		activityManager.popAllActivity();
		finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		onBackPressed();
	}
}
