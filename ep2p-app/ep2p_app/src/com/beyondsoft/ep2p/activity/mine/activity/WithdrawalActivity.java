package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.BankCardInfoBean;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.model.response.PayValidateErrorResponse;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.widget.CustomDialog;
import com.beyondsoft.ep2p.widget.PayHintDialog;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog.ButtonOnClickListener;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog.PayPasswrodClickListener;
import com.beyondsoft.ep2p.widget.WithDrawalInstructionsDialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 提现页面
 * 
 * @author hardy.zhou
 *
 */
public class WithdrawalActivity extends BaseActivity implements OnClickListener {

	public static final String FLAG_IS_FIRST = "flag_is_first";

	private boolean isFirst = true;

	private TextView tv_title;
	private LinearLayout ll_balance_type;

	private TextView tv_balance_type,tv_bank_card_name,tv_bank_card_no,tv_availablebalance,tv_commamont,tv_recharge_detail_amount;
	private LinearLayout ll_withdrawal_instructions;

	private EditText et_money;
	private double money;
	private String mMoneyAmout;
    private ImageView img_bank_card;
	private Button bt_next_step;
private TextView tv_withdral_fee;
	private PayPasswrodDialog passwrodDialog;
	private String mPayPassword;
	private BankCardInfoBean bankCardInfoBean;
	private static final int REQUEST_SELECT_BANK_CARD = 100;
	private static final int REQUEST_VALIDATION = 200;
	 private static final int FLAG_VILIDATE_PASSWORD =300;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawal);
		initView();
		initData();
		setListener();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		bankCardInfoBean =(BankCardInfoBean) getIntent().getSerializableExtra("bankcardinfo");
		if(bankCardInfoBean!=null){
			String bankname = bankCardInfoBean.getBelongingBank();
			tv_bank_card_name.setText(bankname);
			int[] imageids = new int[]{R.drawable.nonghang_icon,R.drawable.shangpu_icon,R.drawable.jiaotong_icon,R.drawable.gongshang_icon,R.drawable.youzheng_icon,R.drawable.guangfa_icon,R.drawable.minsheng_icon,R.drawable.pingan_icon,R.drawable.zhaoshang_icon,R.drawable.zhonghang_icon,R.drawable.jianhang_icon,R.drawable.guangda_icon,R.drawable.xingye_icon,R.drawable.zhongxin_icon,R.drawable.huaxia_icon};
			String[] banknames = new String[]{"中国农业银行","上海浦东发展银行","交通银行","工商银行","中国邮政储蓄银行","广东发展银行","中国民生银行","平安银行（深发展）","招商银行","中国银行","中国建设银行","中国光大银行","兴业银行","中信银行","华夏银行"};
			
			for (int i = 0; i < banknames.length; i++){
				if(banknames[i].equals(bankname)){
					img_bank_card.setImageResource(imageids[i]);
				}
			
			String bankcardNo = bankCardInfoBean.getBankcardNo();
			tv_bank_card_no.setText(bankcardNo.substring(bankcardNo.length()-4,bankcardNo.length()));
			tv_availablebalance.setText(StringUtils.formatMoney(UserPersonalInfo.getAvailableBalance()));
			tv_commamont.setText(StringUtils.formatMoney(UserPersonalInfo.getCommonAmount()));
			tv_recharge_detail_amount.setText(StringUtils.formatMoney(UserPersonalInfo.getRechargeDetaiAmount()));
		}
		}
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText(R.string.mine_carry_cash);
		ll_balance_type = (LinearLayout) findViewById(R.id.ll_balance_type);
		ll_withdrawal_instructions = (LinearLayout) findViewById(R.id.ll_withdrawal_instructions);
		ll_withdrawal_instructions.setOnClickListener(this);
		tv_balance_type = (TextView) findViewById(R.id.tv_balance_type);
		tv_balance_type.setOnClickListener(this);
		et_money = (EditText) findViewById(R.id.et_money);
		bt_next_step = (Button) findViewById(R.id.bt_next_step);
		bt_next_step.setOnClickListener(this);
		tv_bank_card_name = (TextView) findViewById(R.id.tv_bank_card_name);
		tv_bank_card_no = (TextView) findViewById(R.id.tv_bank_card_no);
		tv_availablebalance = (TextView) findViewById(R.id.tv_availablebalance);
		tv_commamont = (TextView) findViewById(R.id.tv_commamont);
		tv_recharge_detail_amount = (TextView) findViewById(R.id.tv_recharge_detail_amount);
		img_bank_card = (ImageView) findViewById(R.id.img_bank_card);
		tv_withdral_fee = (TextView) findViewById(R.id.tv_withdral_fee);
	}

	/**
	 * 设置监听器
	 */
	private void setListener() {
		et_money.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String money_str = s.toString();
				if (money_str != null & !"".equals(money_str = money_str.trim())) {
				   money = Double.parseDouble(money_str);
				   //提现手续费算法
				   if(money>=100){
					   if(money>UserPersonalInfo.getAvailableBalance())
					   {
						   CommonUtils.toastMsgShort(mContext, "提现金额大于可用余额");
						   bt_next_step.setEnabled(false);
					   }else 
					   {
						   if(money<UserPersonalInfo.getCommonAmount())
						   {
							   bt_next_step.setEnabled(true);
						   }else
						   {
							   if(((money-UserPersonalInfo.getCommonAmount())*Double.parseDouble(UserPersonalInfo.getWithdrawalFee())+money)<=UserPersonalInfo.getAvailableBalance()){
								   bt_next_step.setEnabled(true);
								   tv_withdral_fee.setText(""+StringUtils.formatMoney((money-UserPersonalInfo.getCommonAmount())*Double.parseDouble(UserPersonalInfo.getWithdrawalFee()))+"元");
							   }else{
								   CommonUtils.toastMsgShort(mContext, "提现金额大于可用余额");
								   bt_next_step.setEnabled(false);
							   }
						   }
						  
					   }
					  
					   
				   }else{
					   bt_next_step.setEnabled(false);
				   }
				}
			}
		});
	}
	
	// 隐藏软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }

        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);

    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    
    /**
	 * 交易密码输入框
	 */
	private void showInputPwdDialog(){
		
			passwrodDialog=new PayPasswrodDialog(mContext);
			passwrodDialog.setOnPayPasswrodClickListener(new PayPasswrodClickListener() {
				
				@Override
				public void OnClick(String payPasswrod) {
					// TODO Auto-generated method stub
					mPayPassword=payPasswrod;
				}
			});
			passwrodDialog.setButtonClickListener(new ButtonOnClickListener() {
				
				@Override
				public void onButton1Click(View v) {
					// TODO Auto-generated method stub
					HashMap<String,Object> hashMap=new HashMap<String, Object>();
					hashMap.put("tradPassWord", mPayPassword);
					connection(new SecurityCenterService().validatePayPassword(FLAG_VILIDATE_PASSWORD, hashMap, WithdrawalActivity.this));
					passwrodDialog=null;
				}
			});
			passwrodDialog.show();
			showKeyBoard();
		
	}
	
	/**交易密码错误框
	 * @param response
	 */
	private void showErrorDialog(PayValidateErrorResponse response){
		if("交易密码为空".equals(response.getMessage()))
		{
			com.beyondsoft.ep2p.widget.CustomDialog topUpDialog = new CustomDialog(mContext);
			topUpDialog.setButtonClickListener(new com.beyondsoft.ep2p.widget.CustomDialog.ButtonOnClickListener() {

				@Override
				public void onButton2Click(View v) {

					startActivity(new Intent(mContext, SecurityCenterActivity.class));
				}

				@Override
				public void onButton1Click(View v) {
					
				}
			});
			topUpDialog.show();
			topUpDialog.setTitle("提示");
			topUpDialog.setDescri("您未设定交易密码，请设置交易密码");
			topUpDialog.setButtonText("取消", "去设置");
		}
		
		if(response.getResult().getNum()>0){
			PayHintDialog payclearDialog = new PayHintDialog(mContext);
			payclearDialog.show();
			payclearDialog.setDescri(getResources().getString(R.string.error_paypwd_hint,response.getResult().getNum()));
			payclearDialog.setButtonClickListener(new PayHintDialog.ButtonOnClickListener() {

				@Override
				public void onButton2Click(View v) {
					showInputPwdDialog();
				}

				@Override
				public void onButton1Click(View v) {
					pushActivity(ForgetPasswordActivity.class);
				}
			});							
		}else{
			CommonUtils.toastMsgShort(this,getResources().getString(R.string.error_paypwd_hint2,response.getResult().getTime()));
		}
	}

	private void showKeyBoard() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				inputMethodManager.toggleSoftInput(0,
						InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}, 300);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQUEST_SELECT_BANK_CARD: {
			if (resultCode == RESULT_OK) {
				String name = data.getStringExtra("name");
				tv_balance_type.setText(name);
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
		case R.id.tv_balance_type: {
			boolean isSelected = tv_balance_type.isSelected();
			if (!isSelected) {
				ll_balance_type.setVisibility(View.VISIBLE);
			} else {
				ll_balance_type.setVisibility(View.GONE);
			}
			tv_balance_type.setSelected(!isSelected);
		}
			break;
		case R.id.ll_withdrawal_instructions: {
			WithDrawalInstructionsDialog dialog = new WithDrawalInstructionsDialog(mContext);
			dialog.show();
		}
			break;
		case R.id.bt_next_step: {
			mMoneyAmout = et_money.getText().toString();
			if (null != mMoneyAmout && !"".equals(mMoneyAmout.trim())) {
				Intent intent = new Intent();
				intent.setClass(mContext, WithdrawalValicationActivity.class);
				intent.putExtra(WithdrawalValicationActivity.PARAMS_MONEY, Float.parseFloat(mMoneyAmout));
				intent.putExtra(WithdrawalValicationActivity.PARAMS_PHONE_NUM, UserPersonalInfo.getPhoneNo());
				intent.putExtra(WithdrawalValicationActivity.PARAMS_BANK_ID, bankCardInfoBean.getBankcardNo());
				startActivityForResult(intent, REQUEST_VALIDATION);
			} else {
				CommonUtils.toastMsgShort(mContext, "请输入金额");
			}
			//showInputPwdDialog();
		}
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		super.onResponse(tag, values);
		switch(tag)
		{
		case FLAG_VILIDATE_PASSWORD:
		{
			Intent intent = new Intent();
			intent.setClass(mContext, WithdrawalValicationActivity.class);
			intent.putExtra(WithdrawalValicationActivity.PARAMS_MONEY, money);
			intent.putExtra("bankcardinfo", bankCardInfoBean);
			startActivity(intent);
		}
		}
	}
	
	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
		switch(tag)
		{
		case FLAG_VILIDATE_PASSWORD:
		{
			PayValidateErrorResponse response=gson.fromJson(error, PayValidateErrorResponse.class);
			if("交易密码为空".equals(response.getMessage())){
				CommonUtils.toastMsgShort(mContext, "您未设定交易密码，请设置交易密码");
				Toast.makeText(mContext, "您未设定交易密码，请设置交易密码", 3000).show();
				
			}
			showErrorDialog(response);
		}
		}
	}

}
