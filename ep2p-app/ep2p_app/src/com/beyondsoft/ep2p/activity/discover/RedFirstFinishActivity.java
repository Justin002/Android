package com.beyondsoft.ep2p.activity.discover;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * @description 红包领完了
 */
public class RedFirstFinishActivity extends BaseActivity implements OnClickListener{
	private TextView tv_look_redbag_check;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_receive_redenvelope_first_finish);
		initView();
		setListener();
	}

	private void initView() {
		tv_look_redbag_check = (TextView)findViewById(R.id.tv_look_redbag_check);
		
	}
	private void setListener() {
		tv_look_redbag_check.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.tv_look_redbag_check) {
			pushActivity(RedResultFinishResultActivity.class);
		}
	}


}
