package com.beyondsoft.ep2p.activity.mine.activity;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.common.EnvConstants;
import com.beyondsoft.ep2p.model.BankCardInfoBean;
import com.beyondsoft.ep2p.model.NewAddRechargeBean;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.model.response.NewAddRechargeResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.BaseHelper;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.Constants;
import com.beyondsoft.ep2p.utils.Md5Algorithm;
import com.beyondsoft.ep2p.utils.MobileSecurePayer;
import com.beyondsoft.ep2p.utils.PayOrder;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.widget.RechargeMoneyDialog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 非首次充值页面
 * 
 * @author hanxiaohui
 *
 */
public class RechargeTimesActivity extends BaseActivity implements OnClickListener {

	private static final int TAG_NEW_ADD_RECHARGE = 100;
//	private static final int RECHARGETIMESACTIVITY = 6;
	private TextView tv_title;
	private TextView tv_title_right;
	private TextView tv_bankcardnumber_times;
	private ImageView iv_bank_belong_icon;
	private TextView tv_bankcard_info;
	private TextView wifi_load_again;
	private LinearLayout iv_info;
	private LinearLayout ll_wifi_off;
	private LinearLayout ll_recharge_times;
	private EditText et_money;
	private Button bt_next_step;
	private RechargeMoneyDialog rechargeMoneyDialog;
	
	private BankCardInfoBean bankCardInfoBean;
	private String rechargemoney;//充值金额
	private String bankcardnumber;//银行卡号
	private String noorder;
	BaseService baseService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge_times);
		baseService = new BaseService();
		initView();
		initData();
		initListener();
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		rechargemoney = et_money.getText().toString().trim();
		if(!("".equals(rechargemoney))){
			bt_next_step.setEnabled(true);
		}
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
		wifi_load_again = (TextView) findViewById(R.id.wifi_load_again);
		ll_wifi_off = (LinearLayout) findViewById(R.id.ll_wifi_off);
		ll_recharge_times = (LinearLayout) findViewById(R.id.ll_recharge_times);
		
		iv_bank_belong_icon = (ImageView) findViewById(R.id.iv_bank_belong_icon);
		tv_bankcardnumber_times = (TextView) findViewById(R.id.tv_bankcardnumber_times);
		tv_bankcard_info = (TextView) findViewById(R.id.tv_bankcard_info);
		
		iv_info = (LinearLayout) findViewById(R.id.iv_recharge_times_info);
		et_money = (EditText) findViewById(R.id.et_recharge_times_money);
		bt_next_step = (Button) findViewById(R.id.bt_recharge_times_next);
	}
	
	private void initData(){
		bankCardInfoBean =(BankCardInfoBean) getIntent().getSerializableExtra("bankcardinfo");
		if(bankCardInfoBean!=null){
			ll_wifi_off.setVisibility(View.GONE);
			ll_recharge_times.setVisibility(View.VISIBLE);
			int[] imageids = new int[]{R.drawable.nonghang_icon,R.drawable.shangpu_icon,R.drawable.jiaotong_icon,R.drawable.gongshang_icon,R.drawable.youzheng_icon,R.drawable.guangfa_icon,R.drawable.minsheng_icon,R.drawable.pingan_icon,R.drawable.zhaoshang_icon,R.drawable.zhonghang_icon,R.drawable.jianhang_icon,R.drawable.guangda_icon,R.drawable.xingye_icon,R.drawable.zhongxin_icon,R.drawable.huaxia_icon};
			String[] banknames = new String[]{"中国农业银行","上海浦东发展银行","交通银行","中国工商银行","中国邮政储蓄银行","广东发展银行","中国民生银行","平安银行（深发展）","招商银行","中国银行","中国建设银行","中国光大银行","兴业银行","中信银行","华夏银行"};
			String bankname = bankCardInfoBean.getBelongingBank();
			for (int i = 0; i < banknames.length; i++){
				if(banknames[i].equals(bankname)){
					iv_bank_belong_icon.setImageResource(imageids[i]);
				}
			}
			tv_bankcard_info.setText(bankname);
			bankcardnumber = bankCardInfoBean.getBankcardNo();
			tv_bankcardnumber_times.setText(bankcardnumber.substring(bankcardnumber.length()-4,bankcardnumber.length()));
		}else{
			CommonUtils.toastMsgShort(mContext, "shujuweikong");
			ll_wifi_off.setVisibility(View.VISIBLE);
			ll_recharge_times.setVisibility(View.GONE);
		}
	}
	
	private void initListener() {
		tv_title_right.setOnClickListener(this);
		iv_info.setOnClickListener(this);
		wifi_load_again.setOnClickListener(this);
		et_money.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.toString().contains(".")) {
					if (s.length() - 1 - s.toString().indexOf(".") > 2) {
						s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
						et_money.setText(s);
						et_money.setSelection(s.length());
					}
				}
				if (s.toString().trim().substring(0).equals(".")) {
					s = "0" + s;
					et_money.setText(s);
					et_money.setSelection(2);
				}

				if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
					if (!s.toString().substring(1, 2).equals(".")) {
						et_money.setText(s.subSequence(0, 1));
						et_money.setSelection(1);
						return;
					}
				}
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(!("".equals(et_money.getText().toString()))){
					bt_next_step.setEnabled(true);
				}else{
					bt_next_step.setEnabled(false);
				}
				
			}
		});
		bt_next_step.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_title_right: {
			startActivity(new Intent(mContext, RechargeInstructionsActivity.class));
		}
			break;
		case R.id.iv_recharge_times_info: {
			rechargeMoneyDialog = new RechargeMoneyDialog(mContext);
			rechargeMoneyDialog.setButtonClickListener(new RechargeMoneyDialog.ButtonOnClickListener() {

				@Override
				public void onButton1Click(View v) {
				}

				@Override
				public void onButton2Click(View v) {

				}

			});
			rechargeMoneyDialog.show();
		}
		break;
		case R.id.bt_recharge_times_next: {
			rechargemoney = et_money.getText().toString().trim();
//			bankcardnumber = null;
			bt_next_step.setEnabled(false);
			newAddRecharge();
			
		}
			break;
		case R.id.wifi_load_again:{
			CommonUtils.toastMsgShort(mContext, "重新连接数据");
		}
			break;
		default:
			break;
		}
	}
	
	private void newAddRecharge() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("amount", rechargemoney);
		params.put("payconfigId", "2");
		params.put("bankCard", bankcardnumber);
		connection(baseService.getStringRequest(TAG_NEW_ADD_RECHARGE, URL.URL_NEW_ADD_RECHARGE, params, mContext));
	}

	@Override
	public void onResponse(int tag, Object values) {
		super.onResponse(tag, values);
		switch (tag) {
		case TAG_NEW_ADD_RECHARGE:
//			CommonUtils.toastMsgShort(mContext, "新增充值记录成功！");
			NewAddRechargeResponse newAddRechargeResponse = gson.fromJson(values.toString(),NewAddRechargeResponse.class);
			NewAddRechargeBean newAddRechargeBean = newAddRechargeResponse.getNewAddRechargeBean();
			noorder = newAddRechargeBean.getNoOrder();
			if(newAddRechargeBean.isAddRechargeResult()){
				recharge(0, bankcardnumber, rechargemoney);
			}else{
				CommonUtils.toastMsgShort(mContext, "充值申请失败！");
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
		case TAG_NEW_ADD_RECHARGE:
			CommonUtils.toastMsgShort(mContext, "新增充值记录失败！"+error);
			break;

		default:
			break;
		}
	}

	// 连连支付相关
	
		private void recharge(int pay_type_flag, String bankcardnumber, String rechargemoney) {
			PayOrder order = null;
			if (pay_type_flag == 0) {
				// 标准支付
				order = constructGesturePayOrder();
				String content4Pay = BaseHelper.toJSONString(order);
				// 关键 content4Pay 用于提交到支付SDK的订单支付串，如遇到签名错误的情况，请将该信息帖给我们的技术支持
				Log.i(RechargeTimesActivity.class.getSimpleName(), content4Pay);

				MobileSecurePayer msp = new MobileSecurePayer();
				boolean bRet = msp.pay(content4Pay, mHandler, Constants.RQF_PAY, mContext, false);

				Log.i(RechargeTimesActivity.class.getSimpleName(), String.valueOf(bRet));

			} else if (pay_type_flag == 1) {
				// 卡前置方式, 如果传入的是卡号，卡号必须大于等于14位
				if (bankcardnumber.length() < 14) {
					Toast.makeText(mContext, "卡前置模式，必须填入正确银行卡卡号或者协议号", Toast.LENGTH_LONG).show();
					return;
				}
				order = constructPreCardPayOrder();
				String content4Pay = BaseHelper.toJSONString(order);
				Log.i(RechargeTimesActivity.class.getSimpleName(), content4Pay);

				MobileSecurePayer msp = new MobileSecurePayer();
				boolean bRet = msp.pay(content4Pay, mHandler, Constants.RQF_PAY, mContext, false);

				Log.i(RechargeTimesActivity.class.getSimpleName(), String.valueOf(bRet));

			}
		}

		private Handler mHandler = createHandler();

		private Handler createHandler() {
			return new Handler() {
				public void handleMessage(Message msg) {
					String strRet = (String) msg.obj;
					switch (msg.what) {
					case Constants.RQF_PAY: {
						JSONObject objContent = BaseHelper.string2JSON(strRet);
						String retCode = objContent.optString("ret_code");
						String retMsg = objContent.optString("ret_msg");
						//先判断状态码，状态码为 成功或处理中 的需要 验签
						if (Constants.RET_CODE_SUCCESS.equals(retCode)) {
							//  卡前置模式返回的银行卡绑定协议号，用来下次支付时使用，此处仅作为示例使用。正式接入时去掉
							// if (pay_type_flag == 1) {
							// TextView tv_agree_no = (TextView)
							// findViewById(R.id.tv_agree_no);
							// tv_agree_no.setVisibility(View.VISIBLE);
							// tv_agree_no.setText(objContent.optString(
							// "agreementno", ""));
							//
							// }

							String resulPay = objContent.optString("result_pay");
							if (Constants.RESULT_PAY_SUCCESS.equalsIgnoreCase(resulPay)) {
//								BaseHelper.showDialog(mContext, "提示", "支付成功，交易状态码：" + retCode,
//										android.R.drawable.ic_dialog_alert);
								//支付成功后续处理
								UserPersonalInfo.setTotalAssets(UserPersonalInfo.getTotalAssets() + Double.parseDouble(rechargemoney));
								UserPersonalInfo.setAvailableBalance(UserPersonalInfo.getAvailableBalance() + Double.parseDouble(rechargemoney));
								Intent intent = new Intent(mContext,RechargeSuccessActivity.class);
								intent.putExtra("rechargemoney", rechargemoney);
//								intent.putExtra("bankcardnumber", bankcardnumber);
								startActivity(intent);
							} else {
								BaseHelper.showDialog(mContext, "提示", retMsg + "，交易状态码:" + retCode,
										android.R.drawable.ic_dialog_alert);
							}

						} else if (Constants.RET_CODE_PROCESS.equals(retCode)) {
							String resulPay = objContent.optString("result_pay");
							if (Constants.RESULT_PAY_PROCESSING.equalsIgnoreCase(resulPay)) {
								BaseHelper.showDialog(mContext, "提示",
										objContent.optString("ret_msg") + "交易状态码：" + retCode,
										android.R.drawable.ic_dialog_alert);
							}

						} else {
							BaseHelper.showDialog(mContext, "提示", retMsg + "，交易状态码:" + retCode,
									android.R.drawable.ic_dialog_alert);
						}
					}
						break;
					}
					super.handleMessage(msg);
				}
			};

		}

		// 标准支付
		private PayOrder constructGesturePayOrder() {
			SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
			Date date = new Date();
			String timeString = dataFormat.format(date);

			PayOrder order = new PayOrder();
			order.setBusi_partner("101001");
			order.setNo_order(noorder);
			order.setDt_order(timeString);
			order.setName_goods("充值");
			order.setNotify_url(URL.URL_RECHARGE);
			// MD5 签名方式
			order.setSign_type(PayOrder.SIGN_TYPE_MD5);
			// RSA 签名方式
			order.setValid_order("100");
			// 设置用户名
			order.setUser_id(StringUtils.getMD5(CommonUtils.getStringByKey(mContext, com.beyondsoft.ep2p.common.Constants.EP2P_TOKEN)));
			// 设置身份证号
			 order.setId_no(UserPersonalInfo.getIdentificationNo());
			// 设置姓名
			order.setAcct_name(UserPersonalInfo.getSname());
			// 设置金额
			order.setMoney_order(et_money.getText().toString().trim());

			order.setFlag_modify("1");
			String sign = "";
			order.setOid_partner(EnvConstants.PARTNER);
			String content = BaseHelper.sortParam(order);
			// MD5 签名方式, 签名方式包括两种，一种是MD5，一种是RSA 这个在商户站管理里有对验签方式和签名Key的配置。
			sign = Md5Algorithm.getInstance().sign(content, EnvConstants.MD5_KEY);
			// RSA 签名方式
			// sign = Rsa.sign(content, EnvConstants.RSA_PRIVATE);
			order.setSign(sign);
			return order;
		}

		// 卡前置方式, 如果传入的是卡号，卡号必须大于等于14位
		private PayOrder constructPreCardPayOrder() {

			SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
			Date date = new Date();
			String timeString = dataFormat.format(date);

			PayOrder order = new PayOrder();
			order.setBusi_partner("101001");
			order.setNo_order(noorder);
			order.setDt_order(timeString);
			order.setName_goods("充值");
			order.setNotify_url(URL.URL_RECHARGE);
			// MD5 签名方式
			order.setSign_type(PayOrder.SIGN_TYPE_MD5);
			// RSA 签名方式
			// order.setSign_type(PayOrder.SIGN_TYPE_RSA);

			order.setValid_order("100");

			// 设置用户名
			order.setUser_id(StringUtils.getMD5(CommonUtils.getStringByKey(mContext, com.beyondsoft.ep2p.common.Constants.EP2P_TOKEN)));
			// 设置身份证号
			order.setId_no(UserPersonalInfo.getIdentificationNo());
			// 设置姓名
			order.setAcct_name(UserPersonalInfo.getSname());
			// 设置金额
			order.setMoney_order(et_money.getText().toString().trim());

			// 银行卡卡号，该卡首次支付时必填
//			order.setCard_no(bankcardnumber);
			// // 银行卡历次支付时填写，可以查询得到，协议号匹配会进入SDK，
			// order.setNo_agree(((EditText) findViewById(R.id.agree_no)).getText()
			// .toString());
			// 风险控制参数。
			order.setRisk_item(constructRiskItem());

			String sign = "";
			order.setOid_partner(EnvConstants.PARTNER);
			String content = BaseHelper.sortParam(order);
			// MD5 签名方式
			sign = Md5Algorithm.getInstance().sign(content, EnvConstants.MD5_KEY);
			// RSA 签名方式
			// sign = Rsa.sign(content, EnvConstants.RSA_PRIVATE);
			order.setSign(sign);
			return order;
		}

		/**
		 * 风险控制参数生成例子，请根据文档动态填写。最后返回时必须调用.toString()
		 */
		private String constructRiskItem() {
			JSONObject mRiskItem = new JSONObject();
			try {
				String token = CommonUtils.getStringByKey(mContext, com.beyondsoft.ep2p.common.Constants.EP2P_TOKEN);
				mRiskItem.put("user_info_bind_phone", UserPersonalInfo.getPhoneNo());//绑定手机号
				mRiskItem.put("user_info_dt_register", "20160113141959");//注册时间
				mRiskItem.put("frms_ware_category", "2009");//*商品类目
				mRiskItem.put("user_info_mercht_userno", StringUtils.getMD5(token));//商户用户唯一标示
				mRiskItem.put("user_info_full_name", UserPersonalInfo.getSname());//用户注册姓名
				mRiskItem.put("user_info_id_no", UserPersonalInfo.getIdentificationNo());//用户注册证件号码
				mRiskItem.put("user_info_identify_state", UserPersonalInfo.getIsAttestation());//是否实名认证 1是0非
				mRiskItem.put("user_info_identify_type", "3");//实名认证方式  1：银行卡；2：现场；3：身份证；4：其他
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return mRiskItem.toString();
		}

		private PayOrder constructSignCard() {

			PayOrder order = new PayOrder();
			order.setSign_type(PayOrder.SIGN_TYPE_MD5);
			// RSA 签名方式
			// order.setSign_type(PayOrder.SIGN_TYPE_RSA);

			// 设置用户名
			order.setUser_id(UserPersonalInfo.getCustomerName());
			// 设置身份证号
			 order.setId_no(UserPersonalInfo.getIdentificationNo());
			// 设置姓名
			order.setAcct_name(UserPersonalInfo.getSname());

			String sign = "";
			order.setOid_partner(EnvConstants.PARTNER);
			String content = BaseHelper.sortParamForSignCard(order);
			// MD5 签名方式
			sign = Md5Algorithm.getInstance().sign(content, EnvConstants.MD5_KEY);
			// RSA 签名方式
			// sign = Rsa.sign(content, EnvConstants.RSA_PRIVATE);
			order.setSign(sign);
			return order;
		}
}