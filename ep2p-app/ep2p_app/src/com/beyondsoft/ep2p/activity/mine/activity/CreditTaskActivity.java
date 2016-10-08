package com.beyondsoft.ep2p.activity.mine.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.adapter.CreditTaskAdapter;

/**
 * @author Ivan.Lu
 * @description 积分任务
 */
public class CreditTaskActivity extends BaseFragmentActivity {
	
	private ListView credit_task_lv;
	private CreditTaskAdapter creditTaskAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credit_task);
		init();
	}
	
	private void init(){
		setTitle(getResources().getString(R.string.credits_task_title));
		credit_task_lv=(ListView) findViewById(R.id.credit_task_lv);
		creditTaskAdapter=new CreditTaskAdapter(this);
		credit_task_lv.setAdapter(creditTaskAdapter);
	}
}
