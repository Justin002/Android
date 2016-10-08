package com.beyondsoft.ep2p.activity.mine.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.beyondsoft.ep2p.ActivityManager;
import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.UserPersonalInfo;

/**
 * @description 话费兑换成功
 */
public class PhoneBillDetailSuccessActivity extends BaseFragmentActivity implements OnClickListener {
	private TextView title_text_sure;
	private TextView tv_phone_bill_success_count;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_bill_success);
		init();
		setListener();
		initData();
	}


	private void init(){
		setTitle(getResources().getString(R.string.credits_phone_bill_title));
		tv_phone_bill_success_count=(TextView) findViewById(R.id.tv_phone_bill_success_count);
		title_text_sure=(TextView) findViewById(R.id.title_text_sure);
		title_text_sure.setVisibility(View.VISIBLE);
	}
	
	private void setListener() {
		title_text_sure.setOnClickListener(this);
	}
	
	private void initData(){
		tv_phone_bill_success_count.setText(getIntent().getStringExtra("price")+"元");
		UserPersonalInfo.setAvailablePoint(UserPersonalInfo.getAvailablePoint()-getIntent().getIntExtra("score",0));
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
