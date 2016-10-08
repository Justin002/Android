package com.beyondsoft.ep2p.activity.discover;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.discover.adapter.RedEnvelopDetailFinishAdapter;

import android.os.Bundle;
import android.widget.ListView;

/**
 * @description 红包领完了详情界面
 */
public class RedResultFinishResultActivity extends BaseActivity {
	private ListView lv_content;
	private RedEnvelopDetailFinishAdapter redEnvelopDetailAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_receive_redenvelope_result_finish);
		init();
	}

	private void init() {
		lv_content = (ListView) findViewById(R.id.lv_content);
		redEnvelopDetailAdapter = new RedEnvelopDetailFinishAdapter(mContext, 1);
		lv_content.setAdapter(redEnvelopDetailAdapter);

	}

}
