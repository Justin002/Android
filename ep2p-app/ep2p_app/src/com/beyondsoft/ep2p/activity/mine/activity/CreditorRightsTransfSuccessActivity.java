package com.beyondsoft.ep2p.activity.mine.activity;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 债权转让成功页面
 * 
 * @author hardy.zhou
 *
 */
public class CreditorRightsTransfSuccessActivity extends BaseActivity implements OnClickListener {

	private TextView title_content_tv, title_right;

	private ImageView title_left_btn;
	private Button bt_look_transf;

	private TextView tv_transferCode;
	private TextView tv_transfer_capital;
	private TextView tv_transfer_price;
	private TextView tv_transfer_fee;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creditor_rights_transf_success);
		initView();
		initData();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		title_content_tv = (TextView) findViewById(R.id.title_content_tv);
		title_right = (TextView) findViewById(R.id.title_right);
		title_content_tv.setText(R.string.creditor_rights_transf_success);
		title_right.setText(R.string.look_can_transf);
		title_right.setText(R.string.ensure);
		title_right.setOnClickListener(this);
		bt_look_transf = (Button) findViewById(R.id.bt_look_transf);
		bt_look_transf.setOnClickListener(this);
		title_left_btn = (ImageView) findViewById(R.id.title_left_btn);
		title_left_btn.setVisibility(View.GONE);
		
		tv_transferCode = (TextView) findViewById(R.id.tv_transferCode);
		tv_transfer_capital = (TextView) findViewById(R.id.tv_transfer_capital);
		tv_transfer_price = (TextView) findViewById(R.id.tv_transfer_price);
		tv_transfer_fee = (TextView) findViewById(R.id.tv_transfer_fee);
		
		
	}
	
	private void initData(){
		Intent intent = getIntent();
		tv_transfer_capital.setText(intent.getStringExtra("surperCapital")+"元");
		tv_transfer_price.setText(intent.getStringExtra("transferPrice")+"元");
		tv_transfer_fee.setText(intent.getStringExtra("tansferFee")+"元");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_right: {
			Intent intent = new Intent();
			intent.setClass(mContext, InvestmentedProjectActivity.class);
			intent.putExtra(InvestmentedProjectActivity.TAB_SELECT,InvestmentedProjectActivity.TAB_WAIT);
			startActivity(intent);
		}
			break;
		case R.id.bt_look_transf: {
			Intent intent = new Intent();
			intent.setClass(mContext, InvestmentedProjectActivity.class);
			intent.putExtra(InvestmentedProjectActivity.TAB_SELECT,InvestmentedProjectActivity.TAB_TRANSFER);
			startActivity(intent);
		}
			break;
		default:
			break;
		}

	}

}
