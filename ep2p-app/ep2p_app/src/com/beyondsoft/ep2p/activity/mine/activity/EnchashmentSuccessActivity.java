package com.beyondsoft.ep2p.activity.mine.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * @author
 * @description 提现成功
 */
public class EnchashmentSuccessActivity extends BaseActivity implements OnClickListener{
	private ImageView title_left_btn;
	private TextView title_text_sure;
    private TextView tv_enchash_money,tv_enchash_date;
    private float mMoney;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enchashment_success);
		init();
		initData();
		setListener();
	}


	private void initData(){
		mMoney = getIntent().getFloatExtra(WithdrawalValicationActivity.PARAMS_MONEY, 0f);
		tv_enchash_money.setText("您已成功申请提现￥"+mMoney+"元");
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
		String today = sdf.format(new Date());
		tv_enchash_date.setText(today);
	}

	private void init(){
		setTitle(getResources().getString(R.string.enchashment_success));
		title_left_btn=(ImageView) findViewById(R.id.title_left_btn);
		title_text_sure=(TextView) findViewById(R.id.title_text_sure);
		title_text_sure.setOnClickListener(this);
		title_text_sure.setVisibility(View.VISIBLE);
		title_left_btn.setVisibility(View.GONE);
		tv_enchash_money = (TextView) findViewById(R.id.tv_enchash_money);
		tv_enchash_date = (TextView) findViewById(R.id.tv_enchash_date);
		
	}
	private void setListener() {

	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.title_text_sure:
			startActivity(new Intent(mContext,MainActivity.class));
			
			break;
			
			default :
			break;
		}
	}
}
