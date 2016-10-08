package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.HashMap;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.BankCardInfoBean;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.URL;

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
import android.widget.TextView;

/**
 * 银行卡认证页面
 * 
 * @author hardy.zhou
 *
 */
public class BankAuthenticationActivity extends BaseActivity implements OnClickListener {

	public static final String FLAG_IS_FIRST = "flag_is_first";

	private static final int REQUEST_REGION = 100;

	private TextView tv_title;
    private ImageView img_bank_card;
	private TextView tv_open_city,tv_bank_card_name,tv_bank_card_no,tv_real_name;
	private EditText et_open_bank_info;

	private Button bt_next_step_bank;
	private BankCardInfoBean bankCardInfoBean;
	private BaseService baseService;
	private static final int REQUEST_SELECT_BANK_CARD = 200;
    private static final int FLAG_VALIDATE_BANKCARD = 300;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bank_authentication);
		baseService = new BaseService();
		initView();
		initData();
		setListener();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText(R.string.mine_carry_cash);
		tv_open_city = (TextView) findViewById(R.id.tv_open_city);
		tv_open_city.setOnClickListener(this);
		bt_next_step_bank = (Button) findViewById(R.id.bt_next_step_bank);
		bt_next_step_bank.setOnClickListener(this);
		et_open_bank_info = (EditText) findViewById(R.id.et_open_bank_info);
		tv_bank_card_name = (TextView) findViewById(R.id.tv_bank_card_name);
		tv_bank_card_no = (TextView) findViewById(R.id.tv_bank_card_no);
		tv_real_name = (TextView) findViewById(R.id.tv_real_name);
		img_bank_card = (ImageView) findViewById(R.id.img_bank_card);
	}
	
	private void initData(){
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
			String sname = UserPersonalInfo.getSname();
			if(null!=sname&&""!=sname)
			{
				String firstNmae = sname.substring(0, 1);
				String lastName = "******************".substring(0, sname.length()-1);
				tv_real_name.setText(firstNmae+lastName);
			}
		
	}
		}}
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
	 * 执行验证请求
	 */
	
	private void perfectBankInfo(){
		HashMap<String,Object> params = new HashMap<String,Object>();
		//params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		params.put("bankCardNo", bankCardInfoBean.getBankcardNo());
		params.put("belongingCity",tv_open_city.getText().toString().trim());
		params.put("openAddress",et_open_bank_info.getText().toString().trim());
		connection(baseService.getStringRequest(FLAG_VALIDATE_BANKCARD, URL.URL_PERFECTBANKINFO, params, mContext));
	}

	/**
	 * 设置监听器
	 */
	private void setListener() {
		et_open_bank_info.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String open_bank = s.toString();
				if (open_bank != null && !"".equals(open_bank = open_bank.trim())&&!"请选择".equals(tv_open_city.getText())) {
					bt_next_step_bank.setEnabled(true);
				} else {
					bt_next_step_bank.setEnabled(false);
				}
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case REQUEST_REGION: {
			String cityNname = data.getStringExtra("cityName");
			if(""!=cityNname&&null!=cityNname)
				tv_open_city.setText(cityNname);
			if(!"请填写开户行信息".equals(et_open_bank_info.getText().toString())){
				bt_next_step_bank.setEnabled(true);
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
		case R.id.bt_next_step_bank: {
			
			perfectBankInfo();
		}
			break;
		case R.id.tv_open_city: {
			Intent intent = new Intent();
			intent.setClass(mContext, SelectLoactionActivity.class);
			startActivityForResult(intent, REQUEST_REGION);
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
		  case FLAG_VALIDATE_BANKCARD:
		  {
			    Intent intent = new Intent();
				intent.putExtra("bankcardinfo", bankCardInfoBean);
				intent.setClass(mContext, WithdrawalActivity.class);
				startActivity(intent);
		  }
		  break;
		  default: 
			  break;
		}
		
	}
}
