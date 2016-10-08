package com.beyondsoft.ep2p.activity.mine.activity;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.mine.adapter.SelectBankCardAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 选择银行卡页面
 * 
 * @author hardy.zhou
 *
 */
public class SelectBankCardActivity extends BaseActivity implements OnClickListener {

	private TextView tv_title;

	private ImageView title_right;

	private ListView lv_content;

	private SelectBankCardAdapter selectBankCardAdapter;

	private static final int REQUEST_ADD = 100;

	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: {
				String name = (String) msg.obj;
				Intent intent = new Intent();
				intent.putExtra("name", name);
				setResult(RESULT_OK, intent);
				onBackPressed();
			}
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_bank_card);
		initView();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText(R.string.select_bank_card);
		title_right = (ImageView) findViewById(R.id.title_right);
		title_right.setOnClickListener(this);
		lv_content = (ListView) findViewById(R.id.lv_content);
		selectBankCardAdapter = new SelectBankCardAdapter(getApplicationContext(), mHandler);
		lv_content.setAdapter(selectBankCardAdapter);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQUEST_ADD: {
			// TODO
			Toast.makeText(getApplicationContext(), "添加银行卡成功", Toast.LENGTH_SHORT).show();
		}
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_right: {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), AddBankCardActivity.class);
			startActivityForResult(intent, REQUEST_ADD);
		}
			break;
		default:
			break;
		}
	}

}
