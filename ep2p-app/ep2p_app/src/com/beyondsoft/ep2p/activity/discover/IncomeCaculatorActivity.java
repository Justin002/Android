package com.beyondsoft.ep2p.activity.discover;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.view.ASelectWheelPopupWindow;
import com.beyondsoft.ep2p.view.ASelectWheelPopupWindow.OnComfirmListener;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author xiaoliang
 * @description 收益计算器
 */
public class IncomeCaculatorActivity extends BaseActivity {

	private TextView tv_title;
	private ImageView iv_right_falg;

	private TextView p_income_tv;
	private RelativeLayout rl_year_rate;
	private RelativeLayout rl_dealine;
	private RelativeLayout rl_repayment_method;

	private TextView tv_year_rate;
	private TextView tv_dealine, tv_repayment_method_text, tv_dealine_tv;
	private TextView tv_repayment_method;
	private EditText m_number_et;

//	private static final int TAG_INCOME_CACULATOR = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_income_caculator);

		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);

		int width = metric.widthPixels; // 宽度（PX）
		int height = metric.heightPixels; // 高度（PX）

		float density = metric.density; // 密度（0.75 / 1.0 / 1.5）
		int densityDpi = metric.densityDpi; // 密度DPI（120 / 160 / 240）
		System.out.println(width);
		System.out.println(height);
		System.out.println(density);
		System.out.println(densityDpi);

		initTitle();
		initView();
		initData();
//		doCaculorRequest();
	}

	private void initTitle() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText("收益计算器");
		iv_right_falg = (ImageView) findViewById(R.id.title_right);
		iv_right_falg.setVisibility(View.GONE);
	}

	private void initView() {
		p_income_tv = (TextView) findViewById(R.id.p_income_tv);
		rl_year_rate = (RelativeLayout) findViewById(R.id.rl_year_rate);
		rl_dealine = (RelativeLayout) findViewById(R.id.rl_dealine);
		rl_repayment_method = (RelativeLayout) findViewById(R.id.rl_repayment_method);
		tv_year_rate = (TextView) findViewById(R.id.tv_year_rate);
		tv_dealine = (TextView) findViewById(R.id.tv_dealine);
		tv_repayment_method = (TextView) findViewById(R.id.tv_repayment_method);
		tv_repayment_method_text = (TextView) findViewById(R.id.tv_repayment_method_text);
		m_number_et = (EditText) findViewById(R.id.m_number_et);
		tv_dealine_tv = (TextView) findViewById(R.id.tv_dealine_tv);

	}

	private void initData() {
		rl_year_rate.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// // pushActivity(MyFavoritesActivity.class);
				// AlertDialog.Builder builder = new AlertDialog.Builder(
				// IncomeCaculatorActivity.this);
				// // builder.setIcon(R.drawable.ic_launcher);
				// builder.setTitle("年化率");
				// // 指定下拉列表的显示数据
				// final String[] cities = { "1%", "2%", "3%", "4%", "5%", "6%",
				// "7%", "8%", "9%", "10%", "11%", "12%", "13%", "14%",
				// "15%", "16%", "17%", "18%", "19%", "20%" };
				// // 设置一个下拉的列表选择项
				// builder.setItems(cities, new
				// DialogInterface.OnClickListener() {
				// @Override
				// public void onClick(DialogInterface dialog, int which) {
				// Toast.makeText(IncomeCaculatorActivity.this,
				// "年化率为：" + cities[which], Toast.LENGTH_SHORT)
				// .show();
				// tv_year_rate.setText(cities[which]);
				// }
				// });
				// builder.show();
				ArrayList<String> ds = new ArrayList<String>();
				ds.add("1%");
				ds.add("2%");
				ds.add("3%");
				ds.add("4%");
				ds.add("5%");
				ds.add("6%");
				ds.add("7%");
				ds.add("8%");
				ds.add("9%");
				ds.add("10%");
				ds.add("11%");
				ds.add("12%");
				ds.add("13%");
				ds.add("14%");
				ds.add("15%");
				ds.add("16%");
				ds.add("17%");
				ds.add("18%");
				ds.add("19%");
				ds.add("20%");
				ds.add("21%");
				ds.add("22%");
				ds.add("23%");
				ds.add("24%");
				// 传入上下文，数据，选择按钮监听器,标题
				setPopwindowsOut(IncomeCaculatorActivity.this, ds, new MyNianhuaOnComfirmListener(), "选择年化率");

			}
		});
		rl_dealine.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				ArrayList<String> ds = new ArrayList<String>();
				ds.add("1月");
				ds.add("2月");
				ds.add("3月");
				ds.add("4月");
				ds.add("5月");
				ds.add("6月");
				ds.add("7月");
				ds.add("8月");
				ds.add("9月");
				ds.add("10月");
				ds.add("11月");
				ds.add("12月");
				ds.add("13月");
				ds.add("14月");
				ds.add("15月");
				ds.add("16月");
				ds.add("17月");
				ds.add("18月");
				ds.add("19月");
				ds.add("20月");
				ds.add("21月");
				ds.add("22月");
				ds.add("23月");
				ds.add("24月");
				ds.add("25月");
				ds.add("26月");
				ds.add("27月");
				ds.add("28月");
				ds.add("29月");
				ds.add("30月");
				ds.add("31月");
				ds.add("32月");
				ds.add("33月");
				ds.add("34月");
				ds.add("35月");
				ds.add("36月");
				// 传入上下文，数据，选择按钮监听器,标题
				setPopwindowsOut(IncomeCaculatorActivity.this, ds, new MyQixianOnComfirmListener(), "选择期限");

			}
		});
		rl_repayment_method.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				ArrayList<String> ds = new ArrayList<String>();
				ds.add("到期还本付息");
				ds.add("按月付息，到期还本");
				ds.add("按月分期还款");

				// 传入上下文，数据，选择按钮监听器,标题
				setPopwindowsOut(IncomeCaculatorActivity.this, ds, new MyHuankuanComfirmListener(), "选择还款方式");

			}
		});
		m_number_et.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {// 在输入数据时监听

				if (s.toString().contains(".")) {
					if (s.length() - 1 - s.toString().indexOf(".") > 2) {
						s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
						m_number_et.setText(s);
						m_number_et.setSelection(s.length());
					}
				}
				if (s.toString().trim().substring(0).equals(".")) {
					s = "0" + s;
					m_number_et.setText(s);
					m_number_et.setSelection(2);
				}

				if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
					if (!s.toString().substring(1, 2).equals(".")) {
						m_number_et.setText(s.subSequence(0, 1));
						m_number_et.setSelection(1);
						return;
					}
				}
				
				/*
				 * 除按月分期还款外 预期收益=投资金额*年化率/ 12 *期限
		     		按月分期还款时按银行标准等额本息还款方式计算预期收益。逾期收益包含利息管理费。
		     		每月还款额=[贷款本金×月利率×（1+月利率）^还款总期数]÷[（1+月利率）^还款总期数-1]
		     	*/
				if (!TextUtils.isEmpty(m_number_et.getText().toString().trim())) {
					try{
						double number_et = Double.parseDouble(m_number_et.getText().toString().trim());
					
						String str_year_rate = tv_year_rate.getText().toString().trim();
						int year_rate = Integer.parseInt(str_year_rate.substring(0, str_year_rate.indexOf("%")));
						
						String str_tv_dealine = tv_dealine.getText().toString().trim();
						int dealine = Integer.parseInt(str_tv_dealine.substring(0, str_tv_dealine.indexOf("月")));
					
						if(!TextUtils.isEmpty(tv_repayment_method_text.getText().toString().trim())){
							if(tv_repayment_method_text.getText().toString().trim().equals("1")){
								p_income_tv.setText("￥" + new DecimalFormat("#0.00").format(number_et*year_rate/12*dealine/100));
							}else if(tv_repayment_method_text.getText().toString().trim().equals("2")){
								p_income_tv.setText("￥" + new DecimalFormat("#0.00").format(number_et*year_rate/12*dealine/100));
							}else if(tv_repayment_method_text.getText().toString().trim().equals("3")){
								p_income_tv.setText("￥" + new DecimalFormat("#0.00").format(number_et*year_rate/12*dealine/100));
							}else{
								p_income_tv.setText("￥0.00");
							}
						}else{
							p_income_tv.setText("￥0.00");
						}
					}catch(Exception e){
						CommonUtils.toastMsgShort(mContext, "年化率、期限、还款方式皆不能为空！！！");
						return;
					}
				} else {
					p_income_tv.setText("￥0.00");
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, // 输入数据之前的监听
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {// 输入数据之后监听
				// CommonUtils.toastMsgShort(mContext, "计算中...");

			}

		});

	}

	private ASelectWheelPopupWindow sharePopupWindowLine;

	private class MyNianhuaOnComfirmListener implements OnComfirmListener {

		@Override
		public void onArticleSelected(String ms) {
			tv_year_rate.setText(ms);
		}

	}

	// sharePopupWindowLine = new ASelectWheelPopupWindow(
	// IncomeCaculatorActivity.this,ds,new MyNianhuaOnComfirmListener());
	private void setPopwindowsOut(Context cx, ArrayList<String> ds, OnComfirmListener mlistener, String title) {
		// 传入上下文，数据，选择按钮监听器
		sharePopupWindowLine = new ASelectWheelPopupWindow(cx, ds, mlistener, title);
		sharePopupWindowLine.showWindow();
		sharePopupWindowLine.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
			}
		});
		sharePopupWindowLine.showAtLocation(findViewById(R.id.title_content_tv), Gravity.TOP, 0, 0);

	}

	private class MyQixianOnComfirmListener implements OnComfirmListener {

		@Override
		public void onArticleSelected(String ms) {

			tv_dealine.setText(ms);
			tv_dealine_tv.setText(ms.substring(0, ms.length() - 1));
		}

	}

	private class MyHuankuanComfirmListener implements OnComfirmListener {

		@Override
		public void onArticleSelected(String ms) {
			tv_repayment_method.setText(ms);
			if (ms.equals("到期还本付息")) {
				tv_repayment_method_text.setText("1");
			} else if (ms.equals("按月付息，到期还本")) {
				tv_repayment_method_text.setText("2");
			} else if (ms.equals("按月分期还款")) {
				tv_repayment_method_text.setText("3");
			}
		}

	}

}
