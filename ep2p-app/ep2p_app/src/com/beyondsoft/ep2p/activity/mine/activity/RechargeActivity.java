package com.beyondsoft.ep2p.activity.mine.activity;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.BankCardInfoBean;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.view.BankCardNumEditText;
import com.beyondsoft.ep2p.widget.BindBankCardDialog;
import com.beyondsoft.ep2p.widget.BindBankCardDialog.ButtonOnClickListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 首次充值绑定银行卡页面
 * 
 * @author hanxiaohui
 *
 */
public class RechargeActivity extends BaseActivity implements OnClickListener {
	
	private TextView tv_title;
	private TextView tv_title_right;

	private EditText et_bank_card_username;
	private BankCardNumEditText et_bank_card_number;

	private ImageView iv_bank_card_prompt;
	private Button bt_next;
	
	private LinearLayout ll_input_hint;
	private TextView tv_card_no_1, tv_card_no_2, tv_card_no_3, tv_card_no_4;
	private ImageView iv_delete;
	
	private BankCardInfoBean bankCardInfoBean;

	private static final int REQUEST_SELECT_BANK_CARD = 100;

	private InputMethodManager imm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge);
		initView();
		initData();
		initListener();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText(R.string.mine_top_up);
		tv_title_right = (TextView) findViewById(R.id.tv_title_right);
		tv_title_right.setText("说明");
		tv_title_right.setVisibility(View.VISIBLE);

		et_bank_card_username = (EditText) findViewById(R.id.et_recharge_username);
		et_bank_card_username.setText(userNameStyle(UserPersonalInfo.getSname()));
		iv_bank_card_prompt = (ImageView) findViewById(R.id.iv_bank_card_prompt);
		et_bank_card_number = (BankCardNumEditText) findViewById(R.id.et_bank_card_number);
		ll_input_hint = (LinearLayout) findViewById(R.id.ll_input_bank_card_number);
		tv_card_no_1 = (TextView) findViewById(R.id.tv_bank_card_no_1);
		tv_card_no_2 = (TextView) findViewById(R.id.tv_bank_card_no_2);
		tv_card_no_3 = (TextView) findViewById(R.id.tv_bank_card_no_3);
		tv_card_no_4 = (TextView) findViewById(R.id.tv_bank_card_no_4);
		iv_delete = (ImageView) findViewById(R.id.iv_bank_card_delete);
		bt_next = (Button) findViewById(R.id.bt_recharge_first_next);
		
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	/* 对是否已绑定有银行卡进行处理 */
	private void initData(){
		bankCardInfoBean =(BankCardInfoBean) getIntent().getSerializableExtra("bankcardinfo");
		if(bankCardInfoBean!=null){
			et_bank_card_number.setText(bankCardInfoBean.getBankcardNo());
			et_bank_card_number.setEnabled(false);
			bt_next.setEnabled(true);
		}else{
			et_bank_card_number.setEnabled(true);
		}
	}
	
	private void initListener() {
		tv_title_right.setOnClickListener(this);
		et_bank_card_username.setOnClickListener(this);
		iv_bank_card_prompt.setOnClickListener(this);
		et_bank_card_number.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				et_bank_card_number.setCursorVisible(true);
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String cardNo = s.toString();
				setCardNoState(cardNo);
				if(!("".equals(et_bank_card_number.getText().toString()))){
					bt_next.setEnabled(true);
				}else{
					bt_next.setEnabled(false);
				}
			}
		});
		iv_delete.setOnClickListener(this);
		bt_next.setOnClickListener(this);
	}
	
	/**
	 * 用户名显示处理
	 * 
	 * @param username
	 */
	private String userNameStyle(String username){
		String name = "";
		if (TextUtils.isEmpty(username)) {
			name = username;
		} else {
			int length = username.length();
			if (length <= 1) {
				name = username;
			} else {
				name = username.substring(0, 1);
				for (int i = 1; i <= (length - 1); i++) {
					name += "*";
				}
			} 
		}
		return name;
	}
	
	/**
	 * 设置卡号输入框状态
	 * 
	 * @param cardNo
	 */
	private void setCardNoState(String cardNo) {
		if (null != cardNo && !"".equals(cardNo = cardNo.trim())) {
			String s = cardNo.replaceAll(" ", "");
			int length = s.length();
			if (length <= 4) {
				tv_card_no_1.setText(s.substring(0, length));
				tv_card_no_2.setText("");
				tv_card_no_3.setText("");
				tv_card_no_4.setText("");
			} else if (length <= 8) {
				tv_card_no_1.setText(s.substring(0, 4));
				tv_card_no_2.setText(s.substring(4, length));
				tv_card_no_3.setText("");
				tv_card_no_4.setText("");
			} else if (length <= 12) {
				tv_card_no_1.setText(s.substring(0, 4));
				tv_card_no_2.setText(s.substring(4, 8));
				tv_card_no_3.setText(s.substring(8, length));
				tv_card_no_4.setText("");
			} else {
				tv_card_no_1.setText(s.substring(0, 4));
				tv_card_no_2.setText(s.substring(4, 8));
				tv_card_no_3.setText(s.substring(8, 12));
				tv_card_no_4.setText(s.substring(12, length));
			}
			iv_delete.setVisibility(View.VISIBLE);
			ll_input_hint.setVisibility(View.VISIBLE);
		} else {
			tv_card_no_1.setText("");
			tv_card_no_2.setText("");
			tv_card_no_3.setText("");
			tv_card_no_4.setText("");
			ll_input_hint.setVisibility(View.GONE);
			iv_delete.setVisibility(View.INVISIBLE);
			et_bank_card_number.setText("");
			bt_next.setEnabled(false);
		}

	}
	

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQUEST_SELECT_BANK_CARD: {
			if (resultCode == RESULT_OK) {
				String name = data.getStringExtra("name");
				et_bank_card_number.setText(name);
			}
		}
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_title_right: {
			startActivity(new Intent(mContext, RechargeInstructionsActivity.class));
		}
			break;
		case R.id.iv_bank_card_prompt: {
			BindBankCardDialog bindBankCardDialog = new BindBankCardDialog(mContext);
			bindBankCardDialog.setButtonClickListener(new ButtonOnClickListener() {

				@Override
				public void onButton1Click(View v) {
					
				}

				@Override
				public void onButton2Click(View v) {

				}

			});
			bindBankCardDialog.show();
		}
			break;
		case R.id.iv_bank_card_delete: {
			setCardNoState("");
		}
		break;
			
		case R.id.et_bank_card_number: {
			// TODO
			imm.hideSoftInputFromWindow(et_bank_card_number.getWindowToken(), 0);

//			et_bank_card_number.setInputType(InputType.TYPE_NULL); // disable
																	// soft
																	// input

		}
			break;
		case R.id.bt_recharge_first_next: {
			Intent intent = new Intent(getApplicationContext(), RechargeMoneyActivity.class);
			intent.putExtra("bankcardnumber", et_bank_card_number.getTextWithoutSpace());
			startActivity(intent);
		}
			break;
		default:
			break;
		}
	}

}
