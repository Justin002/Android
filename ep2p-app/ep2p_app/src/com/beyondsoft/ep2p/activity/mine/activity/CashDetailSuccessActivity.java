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
 * @author Ivan.Lu
 * @description 现金兑换成功
 */
public class CashDetailSuccessActivity extends BaseFragmentActivity implements OnClickListener {
	private TextView title_text_sure;
	private TextView tv_cash_year;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cash_success);
		init();
		setListener();
		initData();
	}

	private void init(){
		setTitle(getResources().getString(R.string.credits_cash_title));
		tv_cash_year=(TextView) findViewById(R.id.tv_cash_year);
		title_text_sure=(TextView) findViewById(R.id.title_text_sure);
		title_text_sure.setVisibility(View.VISIBLE);
	}
	
	private void setListener() {
		title_text_sure.setOnClickListener(this);
	}
	
	private void initData(){
		tv_cash_year.setText(getIntent().getStringExtra("price")+"元");
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
