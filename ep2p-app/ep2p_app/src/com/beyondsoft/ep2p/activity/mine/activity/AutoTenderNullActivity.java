package com.beyondsoft.ep2p.activity.mine.activity;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class AutoTenderNullActivity extends BaseActivity implements OnClickListener {

	private TextView tv_title;
	private ImageView iv_right_falg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_tender_null);
		initView();
	}

	private void initView() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText("自动投标");
		iv_right_falg = (ImageView) findViewById(R.id.title_right);
		iv_right_falg.setImageResource(R.drawable.mine_auto_tender_add_icon);
		iv_right_falg.setVisibility(View.VISIBLE);
		iv_right_falg.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_right:
			startActivity(new Intent(this, AutoTenderAddMoneyActivity.class));
			finish();
			break;

		default:
			break;
		}
		
	}
	
	
}
