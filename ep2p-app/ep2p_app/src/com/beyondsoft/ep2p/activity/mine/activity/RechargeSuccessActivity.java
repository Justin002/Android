package com.beyondsoft.ep2p.activity.mine.activity;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.MainActivity;
import com.beyondsoft.ep2p.activity.home.AuthenticationActivity;
import com.beyondsoft.ep2p.activity.mine.MineFragment;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.widget.BindBankCardDialog;
import com.beyondsoft.ep2p.widget.BindBankCardDialog.ButtonOnClickListener;

import android.app.Notification.Action;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 充值成功
 * 
 * @author hanxiaohui
 *
 */
public class RechargeSuccessActivity extends BaseActivity implements OnClickListener {

	private ImageView iv_title_left;
	private TextView tv_title;
	private TextView tv_title_right;
	private TextView tv_recharge_money_success;
	
	private String recharge_money_success;
	private String bankcardnumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge_success);
		recharge_money_success = getIntent().getStringExtra("rechargemoney");
//		bankcardnumber = getIntent().getStringExtra("bankcardnumber");
//		UserPersonalInfo.setBankcardnumber(bankcardnumber);
		initView();
		initListener();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		iv_title_left = (ImageView) findViewById(R.id.title_left_btn);
		iv_title_left.setVisibility(View.GONE);
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText("充值成功");
		tv_title_right = (TextView) findViewById(R.id.tv_title_right);
		tv_title_right.setText("确定");
		tv_title_right.setVisibility(View.VISIBLE);
		tv_recharge_money_success = (TextView) findViewById(R.id.tv_recharge_money_success);
		tv_recharge_money_success.setText("￥" + recharge_money_success);
	}

	private void initListener() {
		tv_title_right.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_title_right: {
			startActivity(new Intent(mContext,MainActivity.class));
		}
			break;
			
		default:
			break;
		}
	}

}
