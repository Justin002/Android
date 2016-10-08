package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.StringUtils;

/**
 * @author Ivan.Lu
 * @description 修改用户名
 */
public class ModifyUserNameActivity extends BaseFragmentActivity  implements OnClickListener{
	
	private static final int MODIFYUSERNAME=1;
	private EditText et_my_username;
	private TextView title_right;
	private ImageView iv_username_delete;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_username);
		init();
		initListener();
		initData();
	}
	
	private void init(){
		setRightTitle(getResources().getString(R.string.mine_save));
		setTitle(getResources().getString(R.string.personal_username));
		et_my_username=(EditText) findViewById(R.id.et_my_username);
		et_my_username.setText(UserPersonalInfo.getCustomerName2());
		iv_username_delete=(ImageView) findViewById(R.id.iv_username_delete);
		title_right=(TextView) findViewById(R.id.title_right);	
		if(!"".equals(et_my_username.getText().toString())&&et_my_username.getText().toString()!=null){
			et_my_username.setTextColor(getResources().getColor(R.color.text_grey_color));
			title_right.setTextColor(getResources().getColor(R.color.text_grey_color));
			title_right.setEnabled(false);
			et_my_username.setEnabled(false);
		}else{
			title_right.setTextColor(getResources().getColor(R.color.white));
			title_right.setEnabled(true);
		}
	}

	private void initListener() {
		title_right.setOnClickListener(this);
		iv_username_delete.setOnClickListener(this);
		((TextView)findViewById(R.id.title_left)).setOnClickListener(this);
		et_my_username.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (s.length()>0) {
					iv_username_delete.setVisibility(View.VISIBLE);
				}else{
					iv_username_delete.setVisibility(View.GONE);
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
	
	private void initData(){
		
	}
	
	/**
	 * 验证用户名合法性
	 * @return
	 */
	private boolean isValidate(){
		if(StringUtils.isUserName(et_my_username.getText().toString().trim())){
			if(StringUtils.getLength(et_my_username.getText().toString().trim())>20){
				CommonUtils.toastMsgShort(this, getResources().getString(R.string.error_input_hint));
				return false;
			}
			if(StringUtils.getLength(et_my_username.getText().toString().trim())<6){
				CommonUtils.toastMsgShort(this, getResources().getString(R.string.error_input_hint2));
				return false;
			}
		}else{
			CommonUtils.toastMsgShort(this, getResources().getString(R.string.error_input_hint3));
			return false;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.title_right:
			if(isValidate()){
				SecurityCenterService securityCenterService=new SecurityCenterService();
				HashMap<String,Object> hashMap=new HashMap<String, Object>();
				hashMap.put("customerName", et_my_username.getText().toString().trim());
				connection(securityCenterService.modifyUserName(MODIFYUSERNAME, hashMap, this));
				title_right.setTextColor(getResources().getColor(R.color.text_grey_color));
				title_right.setEnabled(false);
				
			}
			break;
		case R.id.iv_username_delete:
			et_my_username.getText().clear();
			break;
			
		case R.id.title_left:
			onBackPressed();
			break;
		}
	}

	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		switch (tag) {
		case MODIFYUSERNAME:
			UserPersonalInfo.setCustomerName(et_my_username.getText().toString().trim());
			CommonUtils.toastMsgShort(this, getResources().getString(R.string.success_username_hint));
			setResult(Activity.RESULT_OK);
			finish();
			break;
		}
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		CommonUtils.toastMsgShort(this, getResources().getString(R.string.error_username_hint));
	}
}
