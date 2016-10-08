package com.beyondsoft.ep2p.activity.mine.activity;


import java.text.DecimalFormat;
import java.text.NumberFormat;
/**
 * 为当期还款详情页面
 * @author Jason.Hwang
 * */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.mine.adapter.ReibursementDetailGridViewAdapter;
import com.beyondsoft.ep2p.activity.mine.adapter.ReimbursementAmountDetailItemAdapter;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.ReimbursementForProfitDetailBean;
import com.beyondsoft.ep2p.model.ReimbursementForProfitDetialResult;
import com.beyondsoft.ep2p.model.ReimbursementForProfitOverduInfoBean;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.model.response.PayValidateErrorResponse;
import com.beyondsoft.ep2p.model.response.ReimbursementForProfitDetailResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.widget.CustomDialog;
import com.beyondsoft.ep2p.widget.PayHintDialog;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog.ButtonOnClickListener;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog.PayPasswrodClickListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 为当期还款页面
 * @author Jasin.Hwang
 *
 */
public class ReimbursementForProfit extends BaseActivity implements OnClickListener{

	
	
	private Button bt_reibuersement_imedietely;
	private BaseService baseService;
	private LinearLayout reimbursement_money_profit;
	private LinearLayout reimbursement_money_details;
	private TextView title_content_tv;
	private ImageView reimbursement_money_advance_biaoji;
	private static final int FLAG_DO_REIMBURSEMENT_FOR_PROFIT = 100;
	private static final int FLAG_GET_REIMBURSEMENT_FOR_PROFIT_DETAIL = 200;
	private static final int FLAG_VILIDATE_PASSWORD = 300;
	private PayPasswrodDialog passwrodDialog;
	private String mPayPassword;

	
	private TextView tv_borrowCode;
	private TextView tv_borrowName;
	private TextView tv_borrowMoney;
	private TextView tv_borrowRate;
	private TextView tv_manageExpenseRate;
	private TextView tv_deadline;
	private TextView tv_repaymentType;
	private TextView tv_capital;
	private TextView tv_interest;
	private TextView tv_managementCost;
	private TextView tv_latefee;
	private TextView tv_repayment_amount;
	private ListView lt_money_amount;
	private ListView lt_interest;
	private ListView lt_manage_money;
	private ListView lt_amount_faxi;
	private String borrowId;
	private GridView gv_period_time;
	private double repay_amount;
	private ReimbursementForProfitDetailBean bean;
	private ArrayList<ReimbursementForProfitOverduInfoBean> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reimbursementforprofit);
		baseService = new BaseService();
		initView();
		getReimbursementForProitDetail();
	}
	//初始化控件
	private void initView(){
		
		reimbursement_money_profit = (LinearLayout) findViewById(R.id.reimbursement_money_profit);
		reimbursement_money_details = (LinearLayout) findViewById(R.id.reimbursement_money_details);
		title_content_tv = (TextView) findViewById(R.id.title_content_tv);
		title_content_tv.setText("当期还款");
		reimbursement_money_advance_biaoji = (ImageView) findViewById(R.id.reimbursement_money_advance_biaoji);
		bt_reibuersement_imedietely = (Button) findViewById(R.id.bt_reibuersement_imedietely);
		reimbursement_money_profit.setOnClickListener(this);
		bt_reibuersement_imedietely.setOnClickListener(this);
		
		
		tv_borrowCode = (TextView) findViewById(R.id.tv_borrowCode);
		tv_borrowName = (TextView) findViewById(R.id.tv_borrowName);
		tv_borrowMoney = (TextView) findViewById(R.id.tv_borrowMoney);
		tv_borrowRate = (TextView) findViewById(R.id.tv_borrowRate);
		tv_manageExpenseRate = (TextView) findViewById(R.id.tv_manageExpenseRate);
		tv_deadline = (TextView) findViewById(R.id.tv_deadline);
		tv_repaymentType = (TextView) findViewById(R.id.tv_repaymentType);
		tv_capital = (TextView) findViewById(R.id.tv_capital);
		tv_interest = (TextView) findViewById(R.id.tv_interest);
		tv_managementCost = (TextView) findViewById(R.id.tv_managementCost);
		tv_latefee = (TextView) findViewById(R.id.tv_latefee);
        
		tv_repayment_amount = (TextView) findViewById(R.id.tv_repayment_amount);
		lt_money_amount = (ListView) findViewById(R.id.lt_money_amount);
		lt_interest = (ListView) findViewById(R.id.lt_interest);
		lt_manage_money = (ListView) findViewById(R.id.lt_manage_money);
		lt_amount_faxi = (ListView) findViewById(R.id.lt_amount_faxi);
		
		gv_period_time = (GridView) findViewById(R.id.gv_period_time);
		
		lt_money_amount.setEnabled(false);
		lt_interest.setEnabled(false);
		lt_manage_money.setEnabled(false);
		lt_amount_faxi.setEnabled(false);
		
		lt_money_amount.setVerticalScrollBarEnabled(true);
		lt_interest.setVerticalScrollBarEnabled(true);
		lt_manage_money.setVerticalScrollBarEnabled(true);
		lt_amount_faxi.setVerticalScrollBarEnabled(true);
		
		gv_period_time.setEnabled(false);
		
	}
	
	private void setData(){
		
		NumberFormat ft = NumberFormat.getPercentInstance();
		ft.setMaximumFractionDigits(2);
		DecimalFormat df = new DecimalFormat("########0.00");
		tv_borrowCode.setText(bean.getBorrowCode());
		tv_borrowName.setText(bean.getBorrowName());
		tv_borrowMoney.setText(df.format(bean.getBorrowMoney())+"元");
		tv_borrowRate.setText(ft.format(bean.getBorrowRate()));
		tv_manageExpenseRate.setText(ft.format(bean.getManageExpenseRate()));
		tv_deadline.setText(bean.getDeadline()+"个月");
		tv_repaymentType.setText(bean.getRepaymentType());
		tv_capital.setText(df.format(bean.getCapital())+"元");
		tv_interest.setText(df.format(bean.getInterest())+"元");
		tv_managementCost.setText(df.format(bean.getManagementCost())+"元");
		tv_latefee.setText(df.format(bean.getLatefee())+"元");
		repay_amount = bean.getCapital()+bean.getInterest()+bean.getManagementCost()+bean.getLatefee();
		tv_repayment_amount.setText(""+df.format(repay_amount)+"元");
		ArrayList<String> money_amount_list = new ArrayList<String>();
		ArrayList<String> interest_list = new ArrayList<String>();
		ArrayList<String> manage_money_list = new ArrayList<String>();
		ArrayList<String> amount_faxi_list = new ArrayList<String>();
		
		ArrayList<String> period_time_list = new ArrayList<String>();
		if(list!=null){
			for(int i=0;i<list.size();i++){
				money_amount_list.add("含"+list.get(i).getCurrentPlanindex()+"/"+list.get(i).getMaxPlanindex()+"期本金"+list.get(i).getCapital()+"元");
				interest_list.add("含"+list.get(i).getCurrentPlanindex()+"/"+list.get(i).getMaxPlanindex()+"期利息"+list.get(i).getInterest()+"元");
				manage_money_list.add("含"+list.get(i).getCurrentPlanindex()+"/"+list.get(i).getMaxPlanindex()+"管理费"+list.get(i).getManagementCost()+"元");
				amount_faxi_list.add("含"+list.get(i).getCurrentPlanindex()+"/"+list.get(i).getMaxPlanindex()+"罚息"+list.get(i).getLatefee()+"元");
				period_time_list.add("含"+list.get(i).getCurrentPlanindex()+"/"+list.get(i).getMaxPlanindex()+"期");
			
			}
			
			lt_money_amount.setAdapter(new MyAdapter(mContext, money_amount_list));
			lt_interest.setAdapter(new MyAdapter(mContext,interest_list));
			lt_manage_money.setAdapter(new MyAdapter(mContext,manage_money_list));
			lt_amount_faxi.setAdapter(new MyAdapter(mContext,amount_faxi_list));
			
			gv_period_time.setAdapter(new MyGridViewAdapter(mContext, period_time_list));

			
			AdjustListViewheight(lt_money_amount);
			AdjustListViewheight(lt_interest);
			AdjustListViewheight(lt_manage_money);
			AdjustListViewheight(lt_amount_faxi);
		}
		
		
	}
	
	//控制待还金额详情控件显隐
	private void setDetailView(){
		if(reimbursement_money_details.getVisibility()==View.VISIBLE){
			reimbursement_money_details.setVisibility(View.GONE);
			reimbursement_money_advance_biaoji.setBackgroundResource(R.drawable.wait_receive_line_shouqi);
		}else{
			reimbursement_money_details.setVisibility(View.VISIBLE);
			reimbursement_money_advance_biaoji.setBackgroundResource(R.drawable.wait_receive_line_zhankai);
		}
	}
	//解决scrollView内嵌套listView高度失效冲突
	private void AdjustListViewheight(ListView listView)
	{
		ListAdapter myAadapter =  listView.getAdapter();
		int totalcount = myAadapter.getCount();
		int totalHeight = 0;
		for(int i=0;i<totalcount;i++){
			View item = myAadapter.getView(i, null, listView);
			item.measure(0, 0);
			totalHeight += item.getMeasuredHeight();
		}
		LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight;
		listView.setLayoutParams(params);
	}
	//期次适配器
	class MyGridViewAdapter extends ReibursementDetailGridViewAdapter{

		public MyGridViewAdapter(Context context, ArrayList<String> list) {
			super(context, list);
			// TODO Auto-generated constructor stub
		}
		
	}
	//详情适配器
	class MyAdapter extends ReimbursementAmountDetailItemAdapter{

		public MyAdapter(Context context, ArrayList<String> list) {
			super(context, list);
			// TODO Auto-generated constructor stub
		}}
	
	
	
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
					connection(new SecurityCenterService().validatePayPassword(FLAG_VILIDATE_PASSWORD, hashMap, ReimbursementForProfit.this));
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
	
	//请求立即还款详情接口
	private void getReimbursementForProitDetail(){
		borrowId = getIntent().getStringExtra("borrowId");
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		params.put("borrowId", borrowId);
		connection(baseService.getStringRequest(FLAG_GET_REIMBURSEMENT_FOR_PROFIT_DETAIL, URL.URL_REIMBURSEMENT_FOR_PROFIT, params, mContext));
	}
	//立即还款接口请求
	
	
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		  case R.id.reimbursement_money_profit:
		  {
			setDetailView();
		  }
		  break;
		  case R.id.bt_reibuersement_imedietely:{
			  if(bean!=null){
					if(UserPersonalInfo.getAvailableBalance()>repay_amount){
						showInputPwdDialog();
					}else{
						CustomDialog topUpDialog = new CustomDialog(mContext);
						topUpDialog.setButtonClickListener(new CustomDialog.ButtonOnClickListener() {
							
							@Override
							public void onButton2Click(View v) {
								
								startActivity(new Intent(mContext, RechargeActivity.class));
							}
							
							@Override
							public void onButton1Click(View v) {
								
							}
						});
						topUpDialog.show();
						topUpDialog.setTitle("提示");
						topUpDialog.setDescri("您的余额不足，请充值");
						topUpDialog.hideView();
						topUpDialog.setButtonText("取消", "去充值");
					}
				}else{
					CommonUtils.toastMsgShort(mContext, "服务器或网络异常！");
				}
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
		switch(tag){
		case FLAG_GET_REIMBURSEMENT_FOR_PROFIT_DETAIL:
			ReimbursementForProfitDetailResponse response =	gson.fromJson(values.toString(), ReimbursementForProfitDetailResponse.class);
			if(response!=null){
				ReimbursementForProfitDetialResult result =	response.getReimbursementForProfitDetialResult();
				if(result!=null){
					 bean=	result.getReimbursementForProfitDetailBean();
					 list = result.getReimbursementForProfitOverduList();
					 setData();
				}
			}
			break;
		case FLAG_VILIDATE_PASSWORD:
			Intent intent = new Intent();
			intent.setClass(mContext, ReimbursementSuccessActivity.class);
			intent.putExtra("borrowId", borrowId);
			startActivity(intent);
			finish();
		break;
		case FLAG_DO_REIMBURSEMENT_FOR_PROFIT:
			
			break;
		default :
			break;
		}
		
	}
	
	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
		switch(tag){
		case FLAG_VILIDATE_PASSWORD:
			PayValidateErrorResponse response=gson.fromJson(error, PayValidateErrorResponse.class);
			showErrorDialog(response);
			break;
		case FLAG_GET_REIMBURSEMENT_FOR_PROFIT_DETAIL:
			
		}
	}
	
}
