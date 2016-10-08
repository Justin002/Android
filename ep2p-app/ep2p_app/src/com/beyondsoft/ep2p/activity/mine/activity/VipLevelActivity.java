package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.SimpleWebActivity;
import com.beyondsoft.ep2p.activity.home.AuthenticationActivity;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.model.response.BuyVipDuringResponse;
import com.beyondsoft.ep2p.model.response.PayValidateErrorResponse;
import com.beyondsoft.ep2p.model.response.PersonalInfoResponse;
import com.beyondsoft.ep2p.model.response.ValidatePayPwdResponse;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.utils.TimeUtil;
import com.beyondsoft.ep2p.view.ASelectWheelPopupWindow;
import com.beyondsoft.ep2p.view.ASelectWheelPopupWindow.OnComfirmListener;
import com.beyondsoft.ep2p.widget.AuthenPopDialog;
import com.beyondsoft.ep2p.widget.AuthenPopDialog.AuthenButtonOnClickListener;
import com.beyondsoft.ep2p.widget.CustomDialog;
import com.beyondsoft.ep2p.widget.PayHintDialog;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog.ButtonOnClickListener;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog.PayPasswrodClickListener;

/**
 * @author Ivan.Lu
 * @description "我的"-会员等级
 */
public class VipLevelActivity extends BaseFragmentActivity  implements  OnClickListener{
	private static final int BUYVIPSELECT=1;
	private static final int VALIDATEPAYPWD=2;
	private static final int OPENVIP=3;
	private static final int GETUSERINFO=4;
	private ImageView title_content_im;
	private ImageView vip_level_bg_iv;
	private TextView tv_vip_charge,tv_phone_bill;
	private TextView start_vip_level;
	private TextView end_vip_level;
	private TextView vip_level_hint_tv;
	private TextView tv_vip_time;
	private Button open_vip;
	private ProgressBar vip_progress;
	private RelativeLayout rl_seect_time;
	private TextView tv_vip_username;//账号
	private TextView tv_avail_money;//可用余额
	private TextView level_count_tv;
	private ASelectWheelPopupWindow sharePopupWindowLine;
	private SecurityCenterService securityCenterService;
	private ArrayList<String> mSelectTimeList;
	private String mPayPassword;
	private PayPasswrodDialog passwrodDialog;
	private int mMount=12;
	private double mAmount=120;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vip_level);
		securityCenterService=new SecurityCenterService();
		init();
		initListenter();
		initData();
		loadData();
	}

	private void init(){
		setTitle(getResources().getString(R.string.personal_vip_level));
		title_content_im=(ImageView) findViewById(R.id.title_content_im);
		title_content_im.setVisibility(View.VISIBLE);
		tv_vip_charge=(TextView) findViewById(R.id.tv_vip_charge);
		tv_vip_charge.setText(Html.fromHtml("<u>"+getResources().getString(R.string.vip_recharge)+"<u/>"));
		open_vip=(Button) findViewById(R.id.open_vip);
		vip_progress=(ProgressBar) findViewById(R.id.vip_progress);
		rl_seect_time=(RelativeLayout) findViewById(R.id.rl_seect_time);
		tv_vip_username=(TextView) findViewById(R.id.tv_vip_username);
		tv_avail_money=(TextView) findViewById(R.id.tv_avail_money);
		vip_level_bg_iv=(ImageView) findViewById(R.id.vip_level_bg_iv);
		level_count_tv=(TextView) findViewById(R.id.level_count_tv);
		tv_phone_bill=(TextView) findViewById(R.id.tv_phone_bill);
		start_vip_level=(TextView) findViewById(R.id.start_vip_level);
		end_vip_level=(TextView) findViewById(R.id.end_vip_level);
		vip_level_hint_tv=(TextView) findViewById(R.id.vip_level_hint_tv);
		tv_vip_time=(TextView) findViewById(R.id.tv_vip_time);
	}

	private void initListenter() {
		tv_vip_charge.setOnClickListener(this);
		open_vip.setOnClickListener(this);
		rl_seect_time.setOnClickListener(this);
		title_content_im.setOnClickListener(this);
	}
	private void initData() {
		vip_progress.setMax(UserPersonalInfo.getExperienceEnd()-UserPersonalInfo.getExperienceStart());
		vip_progress.setSecondaryProgress(UserPersonalInfo.getEmpiricalValue()-UserPersonalInfo.getExperienceStart());
		tv_avail_money.setText(getResources().getString(R.string.mine_balance_hint, StringUtils.formatMoney(UserPersonalInfo.getAvailableBalance())));
		tv_vip_username.setText(StringUtils.getShowPhoneNum(UserPersonalInfo.getPhoneNo()));
		level_count_tv.setText(UserPersonalInfo.getVipLevel());
		if("0".equals(UserPersonalInfo.getIsVip())){
			vip_level_bg_iv.setImageResource(R.drawable.personal_vip_level_no_icon);
		}else if ("1".equals(UserPersonalInfo.getIsVip())) {
			((LinearLayout)findViewById(R.id.vip_end_time_layout)).setVisibility(View.VISIBLE);
			vip_level_bg_iv.setImageResource(R.drawable.personal_vip_level_icon);
			start_vip_level.setText("VIP"+UserPersonalInfo.getVipLevel());
			end_vip_level.setText("VIP"+(Integer.parseInt(UserPersonalInfo.getVipLevel())+1));
			vip_level_hint_tv.setText(getResources().getString(R.string.vip_level_hint,(UserPersonalInfo.getExperienceEnd()-UserPersonalInfo.getEmpiricalValue()),(Integer.parseInt(UserPersonalInfo.getVipLevel())+1)));
			tv_vip_time.setText(TimeUtil.longToString(UserPersonalInfo.getSystemTime()+(UserPersonalInfo.getVipTime()*24*60*60*1000L), TimeUtil.FORMAT_DATE));
		}
	}

	private void loadData(){
		connection(securityCenterService.buyVipSelect(BUYVIPSELECT, null, this));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.open_vip://开通Vip
			if("2".equals(UserPersonalInfo.getIsSetTradePwd())){
				 CustomDialog clearDialog = new CustomDialog(mContext);
                 clearDialog
                         .setButtonClickListener(new CustomDialog.ButtonOnClickListener()
                         {

                             @Override
                             public void onButton2Click(View v){
                            	 
                             }

                             @Override
                             public void onButton1Click(View v){
                                 Intent intent = new Intent(mContext,
                                     ValidateLoginPasswordActivity.class);
                                 intent.putExtra("setType", 2);
                                 pushActivity(intent);
                             }
                         });
                 clearDialog.show();
                 clearDialog.setTitle(getResources().getString(
                     R.string.txt_setting_transaction_hint1));
                 clearDialog.setDescri(getResources().getString(
                     R.string.txt_setting_transaction_hint2));
                 clearDialog.setButtonText(
                     getResources().getString(R.string.txt_setting_transaction_hint4),
                     getResources().getString(R.string.txt_setting_transaction_hint3));
			}else{
				if(isValidation()){
					showInputPwdDialog();				
				}else{
					CommonUtils.toastMsgShort(this,getResources().getString(R.string.error_available_balance));
				}
			}
			break;
		case R.id.tv_vip_charge://充值
		{
			if (UserPersonalInfo.getIsAttestation().equals("2")) {

				AuthenPopDialog authenPopDialog = new AuthenPopDialog(mContext);
				authenPopDialog.setButtonClickListener(new AuthenButtonOnClickListener() {
					
					@Override
					public void onButton2Click(View v) {
						// TODO Auto-generated method stub
						startActivity(new Intent(mContext, AuthenticationActivity.class));
					}
					
					@Override
					public void onButton1Click(View v) {
						// TODO Auto-generated method stub
						
					}
				});
				authenPopDialog.show();
				authenPopDialog.setTitle("提示");
				authenPopDialog.setDescri("为了您的资金安全，请先进行实名登记");
				authenPopDialog.setButtonText("取消", "去认证");
			} else {
				if(UserPersonalInfo.getIsFirstPay() == 0){
					startActivity(new Intent(mContext, RechargeActivity.class));
				}else{
					startActivity(new Intent(mContext, RechargeTimesActivity.class));
				}
			}
		}
            
			break;
		case R.id.rl_seect_time:
			if(mSelectTimeList!=null)
				setPopwindowsOut(VipLevelActivity.this, mSelectTimeList,new MyNianhuaOnComfirmListener(),"选择期限");
			break;
		case R.id.title_content_im:
		{

			Intent intent = new Intent();
			intent.setClass(mContext, SimpleWebActivity.class);
			intent.putExtra(SimpleWebActivity.TITLE, getString(R.string.personal_vip_level));
			intent.putExtra(SimpleWebActivity.URL, "http://www.baidu.com");
			startActivity(intent);
		}

		break;
		}

	}
	private void setPopwindowsOut(Context cx,ArrayList<String> selectTime,
			OnComfirmListener mlistener, String title) {
		// 传入上下文，数据，选择按钮监听器
		sharePopupWindowLine = new ASelectWheelPopupWindow(cx, selectTime, mlistener,title,tv_phone_bill.getText().toString());
		sharePopupWindowLine.showWindow();
		sharePopupWindowLine.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
			}
		});
		
		sharePopupWindowLine.showAtLocation(
				findViewById(R.id.title_content_tv), Gravity.TOP, 0, 0);

	}
	
	private boolean isValidation(){
		if(UserPersonalInfo.getAvailableBalance()>=mAmount){
			return true;
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
				connection(new SecurityCenterService().validatePayPassword(VALIDATEPAYPWD, hashMap, VipLevelActivity.this));
				passwrodDialog=null;
				mPayPassword=null;
			}
		});
		passwrodDialog.show();
		showKeyBoard();

	}

	/**交易密码错误框
	 * @param response
	 */
	private void showErrorDialog(PayValidateErrorResponse response){
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
		{
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
	}

	class MyNianhuaOnComfirmListener implements OnComfirmListener {
		@Override
		public void onArticleSelected(String ms) {
			mMount=Integer.parseInt(ms.split("个月")[0]);
			mAmount=Double.parseDouble(ms.split("￥")[1]);
			tv_phone_bill.setText(ms);
		}
	}
	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		switch (tag) {
		case BUYVIPSELECT:
			mSelectTimeList=new ArrayList<String>();
			BuyVipDuringResponse buyVipDuringResponse=gson.fromJson(values.toString(), BuyVipDuringResponse.class);
			for (int i = 0; i < buyVipDuringResponse.getResult().getBuySelect().size(); i++) {
				if(i==0){
					tv_phone_bill.setText(buyVipDuringResponse.getResult().getBuySelect().get(i).getDictContCode()+"个月/￥"+StringUtils.formatMoney(Double.parseDouble(buyVipDuringResponse.getResult().getBuySelect().get(i).getDictContName())));
				}
				mSelectTimeList.add(buyVipDuringResponse.getResult().getBuySelect().get(i).getDictContCode()+"个月/￥"+StringUtils.formatMoney(Double.parseDouble(buyVipDuringResponse.getResult().getBuySelect().get(i).getDictContName())));
			}
			break;
		case VALIDATEPAYPWD:
			ValidatePayPwdResponse validatePayPwdResponse=gson.fromJson(values.toString(), ValidatePayPwdResponse.class);
			HashMap<String,Object> hashMap=new HashMap<String, Object>();
			hashMap.put("tranPKey", validatePayPwdResponse.getResult().getKey());
			hashMap.put("mount", mMount);
			hashMap.put("amount", mAmount);
			connection(securityCenterService.buyVip(OPENVIP, hashMap,VipLevelActivity.this));
			break;
		case OPENVIP:
			connection(securityCenterService.getPersonalInfo(GETUSERINFO, this, this));
			break;
		case GETUSERINFO:
			PersonalInfoResponse response= gson.fromJson(values.toString(),PersonalInfoResponse.class);
			CommonUtils.setUserInfo(response);
			CommonUtils.toastMsgShort(this,getResources().getString(R.string.txt_tv_buy_cg));
			finish();
			break;
		}
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		switch (tag) {
		case VALIDATEPAYPWD:
			PayValidateErrorResponse response=gson.fromJson(error, PayValidateErrorResponse.class);
			showErrorDialog(response);
			break;
		default:
			super.onErrorResponse(tag, error);
			break;
		}
	}
}