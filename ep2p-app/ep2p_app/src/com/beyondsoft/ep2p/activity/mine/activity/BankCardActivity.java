package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.BankCardInfoBean;
import com.beyondsoft.ep2p.model.BankCardInfoListBean;
import com.beyondsoft.ep2p.model.response.BankCardListResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.widget.RechargeMoneyDialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 银行卡页面
 * 
 * @author hardy.zhou
 *
 */
public class BankCardActivity extends BaseActivity implements OnClickListener {

	public static final String FLAG_HAS_CARD = "flag_has_card";
	private boolean hasBankCard = false;

	private TextView tv_title,tv_title_right;

	private TextView tv_bank_name;
	private TextView tv_bank_card_1, tv_bank_card_2, tv_bank_card_3, tv_bank_card_4;
    private TextView tv_is_withdrawals,tv_quick_payment;
	private Button bt_go_add_bank_card;
	private LinearLayout ll_no_bank_card;
	private LinearLayout ll_bank_card;
    private ImageView mine_bankcard_notice,iv_bank_belong_icon;
	private BankCardInfoBean mBankCardInfoBean;

	private static final int REQUEST_ADD_BANK_CARD = 100;

	private BaseService baseService;

	private static final int TAG_GET_BANK_LIST = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bank_card);
		baseService = new BaseService();
		initData();
		initView();
		doGetBankCardListRequest();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		Intent intent = getIntent();
		hasBankCard = intent.getBooleanExtra(FLAG_HAS_CARD, false);
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText(R.string.mine_bank_card);
		tv_title_right = (TextView) findViewById(R.id.tv_title_right);
		//tv_title_right.setText("添加银行卡");
		//tv_title_right.setOnClickListener(this);
		bt_go_add_bank_card = (Button) findViewById(R.id.bt_go_add_bank_card);
		mine_bankcard_notice = (ImageView) findViewById(R.id.mine_bankcard_notice);
		iv_bank_belong_icon = (ImageView) findViewById(R.id.iv_bank_belong_icon);
		
		bt_go_add_bank_card.setOnClickListener(this);
		mine_bankcard_notice.setOnClickListener(this);
		tv_is_withdrawals = (TextView) findViewById(R.id.tv_is_withdrawals);
		tv_quick_payment = (TextView) findViewById(R.id.tv_quick_payment);
		tv_bank_name = (TextView) findViewById(R.id.tv_bank_name);
		tv_bank_card_1 = (TextView) findViewById(R.id.tv_bank_card_1);
		tv_bank_card_2 = (TextView) findViewById(R.id.tv_bank_card_2);
		tv_bank_card_3 = (TextView) findViewById(R.id.tv_bank_card_3);
		tv_bank_card_4 = (TextView) findViewById(R.id.tv_bank_card_4);
  
		ll_bank_card = (LinearLayout) findViewById(R.id.ll_bank_card);
		ll_no_bank_card = (LinearLayout) findViewById(R.id.ll_no_bank_card);
		
	}

	/**
	 * 显示银行卡信息
	 */
	private void showBankCardInfo() {
		if (null != mBankCardInfoBean) {
			String bankcardNo = mBankCardInfoBean.getBankcardNo();
			String bankname = mBankCardInfoBean.getBelongingBank();
			tv_bank_name.setText(bankname);
			int[] imageids = new int[]{R.drawable.nonghang_icon,R.drawable.shangpu_icon,R.drawable.jiaotong_icon,R.drawable.gongshang_icon,R.drawable.youzheng_icon,R.drawable.guangfa_icon,R.drawable.minsheng_icon,R.drawable.pingan_icon,R.drawable.zhaoshang_icon,R.drawable.zhonghang_icon,R.drawable.jianhang_icon,R.drawable.guangda_icon,R.drawable.xingye_icon,R.drawable.zhongxin_icon,R.drawable.huaxia_icon};
			String[] banknames = new String[]{"中国农业银行","上海浦东发展银行","交通银行","工商银行","中国邮政储蓄银行","广东发展银行","中国民生银行","平安银行（深发展）","招商银行","中国银行","中国建设银行","中国光大银行","兴业银行","中信银行","华夏银行"};
			
			for (int i = 0; i < banknames.length; i++){
				if(banknames[i].equals(bankname)){
					iv_bank_belong_icon.setImageResource(imageids[i]);
				}
			if (null != bankcardNo && bankcardNo.length() > 4) {
				tv_bank_card_4.setText(bankcardNo.substring(bankcardNo.length() - 4, bankcardNo.length()));
			}
			
			if("1".equals(mBankCardInfoBean.getQuickPayment())){
				tv_quick_payment.setVisibility(View.VISIBLE);
			}else if("0".equals(mBankCardInfoBean.getQuickPayment())){
				tv_quick_payment.setVisibility(View.GONE);
			}
			if("1".equals(mBankCardInfoBean.getIsWithdrawals())){
				tv_is_withdrawals.setVisibility(View.VISIBLE);
			}else if("0".equals(mBankCardInfoBean.getIsWithdrawals())){
				tv_is_withdrawals.setVisibility(View.GONE);
			}
			
			
			}
		}
	}

	/**
	 * 执行获取银行卡列表请求
	 */
	private void doGetBankCardListRequest() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		connection(baseService.getStringRequest(TAG_GET_BANK_LIST, URL.URL_BANK_CARD_LIST, params, mContext));
	}

	@Override
	public void onResponse(int tag, Object values) {
		super.onResponse(tag, values);
		switch (tag) {
		case TAG_GET_BANK_LIST: {
			BankCardListResponse bankCardListResponse = gson.fromJson(values.toString(), BankCardListResponse.class);
			if (null != bankCardListResponse) {
				BankCardInfoListBean bankCardInfoListBean = bankCardListResponse.getBankCardInfoListBean();
				if (null != bankCardInfoListBean) {
					ArrayList<BankCardInfoBean> bankCardInfoBeanList = bankCardInfoListBean.getBankCardInfoBeanList();
					if (null != bankCardInfoBeanList && bankCardInfoBeanList.size() > 0) {
						BankCardInfoBean bankCardInfoBean = bankCardInfoBeanList.get(0);
						mBankCardInfoBean = bankCardInfoBean;
						
					}
				}
			}
			if (null != mBankCardInfoBean) {
				ll_bank_card.setVisibility(View.VISIBLE);
				ll_no_bank_card.setVisibility(View.GONE);
			} else {
				ll_bank_card.setVisibility(View.GONE);
				ll_no_bank_card.setVisibility(View.VISIBLE);
			}
			showBankCardInfo();
		}
			break;
		default:
			break;
		}
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		super.onErrorResponse(tag, error);
		switch (tag) {
		case TAG_GET_BANK_LIST: {

		}
			break;
		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQUEST_ADD_BANK_CARD: {
			if (resultCode == RESULT_OK) {
				// TODO
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
		case R.id.bt_go_add_bank_card: {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), AddBankCardActivity.class);
			startActivityForResult(intent, REQUEST_ADD_BANK_CARD);

		}
			break;
		case R.id.mine_bankcard_notice:{
			final RechargeMoneyDialog dialog = new RechargeMoneyDialog(mContext);
			dialog.show();
			dialog.setButtonClickListener(new RechargeMoneyDialog.ButtonOnClickListener() {
				
				@Override
				public void onButton2Click(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
				
				@Override
				public void onButton1Click(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			break;
		}
		case R.id.tv_title_right:
			startActivity(new Intent(getApplicationContext(),AddBankCardActivity.class));
		default:
			break;
		}
	}
}
