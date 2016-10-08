package com.beyondsoft.ep2p.activity.mine.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.beyondsoft.ep2p.ActivityManager;
import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.model.response.VipSuccessResponse;
import com.beyondsoft.ep2p.utils.StringUtils;

/**
 * @description vip兑换成功
 */
public class VipDetailSuccessActivity extends BaseFragmentActivity implements OnClickListener {
	private TextView title_text_sure;
	private TextView tv_vip_year;
	private TextView tv_vip_time;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vip_success);
		init();
		setListener();
		initData();
	}

	private void init(){
		setTitle(getResources().getString(R.string.credits_vip_echange));
		tv_vip_year=(TextView) findViewById(R.id.tv_vip_year);
		tv_vip_time=(TextView) findViewById(R.id.tv_vip_time);
		title_text_sure=(TextView) findViewById(R.id.title_text_sure);
		title_text_sure.setVisibility(View.VISIBLE);
	}
	
	private void setListener() {
		title_text_sure.setOnClickListener(this);
	}
	
	private void initData(){
		VipSuccessResponse response=(VipSuccessResponse) getIntent().getSerializableExtra("detail");		
		tv_vip_year.setText(StringUtils.formatYear(response.getResult().getDictContName()));
		tv_vip_time.setText(response.getResult().getVipEndTime().split(" ")[0]);
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
