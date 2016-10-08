package com.beyondsoft.ep2p.activity.mine.activity;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.mine.adapter.SelectBankTypeAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 选择银行类型页面
 * 
 * @author hardy.zhou
 *
 */
public class SelectBankTypeActivity extends BaseActivity {

	private TextView title_content_tv;
	private ListView lv_content;

	private SelectBankTypeAdapter selectBankTypeAdapter;

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
		setContentView(R.layout.activity_select_bank_type);
		initView();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		title_content_tv = (TextView) findViewById(R.id.title_content_tv);
		title_content_tv.setText(R.string.select_bank);
		lv_content = (ListView) findViewById(R.id.lv_content);
		selectBankTypeAdapter = new SelectBankTypeAdapter(getApplicationContext(), mHandler);
		lv_content.setAdapter(selectBankTypeAdapter);
	}

}
