package com.beyondsoft.ep2p.activity.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.beyondsoft.ep2p.ActivityManager;
import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.StringUtils;

/**
 * @author Ivan.Lu
 * @description 忘记密码
 */
public class ForgetPasswordActivity extends BaseFragmentActivity implements
		OnClickListener {

	private EditText et_forget_telphone;
	private Button bt_forget_next;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password);
		ActivityManager activityManager = ActivityManager.getInstance();
		activityManager.pushActivity(this);
		init();
		initListener();

	}

	private void initListener() {
		bt_forget_next.setOnClickListener(this);
		et_forget_telphone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length()==11) {
					bt_forget_next.setEnabled(true);
				} else {
					bt_forget_next.setEnabled(false);
				}
			}
		});
	}

	private void init() {
		setTitle(getResources().getString(R.string.login_forget));
		et_forget_telphone = (EditText) findViewById(R.id.et_forget_telphone);
		bt_forget_next = (Button) findViewById(R.id.bt_forget_next);
	}
	
	private boolean isValidate(){
		if(StringUtils.isMobileNO(et_forget_telphone.getText().toString())){
			return true;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_forget_next:
			if(isValidate()){
				pushActivity(new Intent(this, ForgetPasswordTypeTelActivity.class).putExtra("phoneNum", et_forget_telphone.getText().toString()));
			}else{
				CommonUtils.toastMsgShort(this, getResources().getString(R.string.error_phone));
			}
			break;
		}
	}
}
