package com.beyondsoft.ep2p.activity.mine.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.widget.EmptyDialog;

/**
 * @author Ivan.Lu
 * @description "我的"-实名认证
 */
public class RealNameAuthActivity extends BaseFragmentActivity implements OnClickListener  {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_real_name_auth);
		init();
		initListener();
	}
	
	private void init(){
		setTitle(getResources().getString(R.string.security_center_real_name).toString());
	}
	
	private void initListener(){
		((Button)findViewById(R.id.submit_auth_btn)).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.submit_auth_btn:
			EmptyDialog emptyDialog=new EmptyDialog(this);
			emptyDialog.show();
			break;
		}
	}
}
