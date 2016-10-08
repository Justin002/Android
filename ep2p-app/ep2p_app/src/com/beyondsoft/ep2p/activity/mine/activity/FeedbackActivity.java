package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.HashMap;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.service.MoreService;
import com.beyondsoft.ep2p.utils.CommonUtils;

/**
 * @author Ivan.Lu
 * @description "我的"-意见反馈
 */
public class FeedbackActivity extends BaseFragmentActivity implements OnClickListener {
	
	private static final int FEEDBACK=1;
	private EditText feedback_et;
	private TextView char_count_tv;
	private Button submit_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		init();
		initListener();
	}
	
	private void init(){
		setTitle(getResources().getString(R.string.mine_setting_feedback));
		feedback_et=(EditText) findViewById(R.id.feedback_et);
		char_count_tv=(TextView) findViewById(R.id.char_count_tv);
		submit_btn=(Button) findViewById(R.id.submit_btn);
	}
	
	private void initListener(){
		submit_btn.setOnClickListener(this);
		feedback_et.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				char_count_tv.setText("("+s.length()+"/"+100+")");
				if(s.length()>0){
					submit_btn.setEnabled(true);
				}else{
					submit_btn.setEnabled(false);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.submit_btn:
			HashMap<String, Object> hashMap=new HashMap<String, Object>();
			hashMap.put("submitContent", feedback_et.getText().toString().trim());
			connection(new MoreService().feedback(FEEDBACK, hashMap, this));
			break;
		}
	}

	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		CommonUtils.toastMsgShortForStyle(this,getResources().getString(R.string.mine_setting_feedback_submit_success), dip2px(-75));
		finish();
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
	}
}
