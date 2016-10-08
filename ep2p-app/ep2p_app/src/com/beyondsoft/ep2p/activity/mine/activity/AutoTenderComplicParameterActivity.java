package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.ArrayList;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.view.ASelectWheelPopupWindow;
import com.beyondsoft.ep2p.view.ASelectWheelPopupWindow.OnComfirmListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * @author hanxiaohui
 * @description 自主投标添加功能的第二个页面
 */

public class AutoTenderComplicParameterActivity extends BaseAutoTenderComplicActivity implements OnClickListener {

	private TextView tv_title;
	private TextView tv_title_right;

	private RadioButton rb_month_01;
	private RadioButton rb_month_02;
	private RadioButton rb_precent_03;
	private RadioButton rb_precent_04;

	private TextView etMonthMin;
	private TextView etMonthMax;

	private TextView etPercentMin;
	private TextView etPercentMax;
	private LinearLayout ll_precent;
	private LinearLayout ll_month;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_tender_complic_02);

		initView();
		initData();
		initListener();
	}

	private void initView() {

		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText("编辑");
		tv_title_right = (TextView) findViewById(R.id.tv_title_right);
		tv_title_right.setText("保存");

		ll_month = (LinearLayout) findViewById(R.id.ll_auto_tender_complic_021);
		ll_precent = (LinearLayout) findViewById(R.id.ll_auto_tender_complic_022);

		rb_month_01 = (RadioButton) findViewById(R.id.rb_auto_tender_complic_021);
		rb_month_02 = (RadioButton) findViewById(R.id.rb_auto_tender_complic_022);
		rb_precent_03 = (RadioButton) findViewById(R.id.rb_auto_tender_complic_023);
		rb_precent_04 = (RadioButton) findViewById(R.id.rb_auto_tender_complic_024);

		etMonthMin = (TextView) findViewById(R.id.et_auto_tender_complic_month_01);
		etMonthMax = (TextView) findViewById(R.id.et_auto_tender_complic_month_02);
		etPercentMin = (TextView) findViewById(R.id.et_auto_tender_complic_percent_01);
		etPercentMax = (TextView) findViewById(R.id.et_auto_tender_complic_percent_02);

	}

	private void initData() {
		autoTenderInfoBean = AutoTenderActivity.checkedItemBean;
		if (!autoTenderInfoBean.getMinborrowmonth().equals("0") && !autoTenderInfoBean.getMaxborrowmonth().equals("0")) {
			rb_month_01.setChecked(false);
			rb_month_02.setChecked(true);
			etMonthMin.setText(autoTenderInfoBean.getMinborrowmonth() + "月");
			etMonthMax.setText(autoTenderInfoBean.getMaxborrowmonth() + "月");
		}
		if (!autoTenderInfoBean.getMinborrowrate().equals("0.0000") && !autoTenderInfoBean.getMaxborrowrate().equals("0.0000")) {
			rb_precent_03.setChecked(false);
			rb_precent_04.setChecked(true);
			String minborrowrate = autoTenderInfoBean.getMinborrowrate();
			String maxborrowrate = autoTenderInfoBean.getMaxborrowrate();
			etPercentMin.setText(minborrowrate.substring(0, minborrowrate.indexOf(".")) + "%");
			etPercentMax.setText(maxborrowrate.substring(0, maxborrowrate.indexOf(".")) + "%");
		}
	}

	private void initListener() {

		tv_title_right.setOnClickListener(this);
		ll_month.setOnClickListener(this);
		ll_precent.setOnClickListener(this);
		rb_month_01.setOnClickListener(this);
		rb_month_02.setOnClickListener(this);
		rb_month_02.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {

					etMonthMin.setPressed(true);
					etMonthMax.setPressed(true);
				} else {

					etMonthMin.setPressed(false);
					etMonthMax.setPressed(false);
				}

			}
		});
		rb_precent_03.setOnClickListener(this);
		rb_precent_04.setOnClickListener(this);
		etMonthMin.setOnClickListener(this);
		etMonthMax.setOnClickListener(this);
		etPercentMin.setOnClickListener(this);
		etPercentMax.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_title_right:
			complicAutoTender();
			break;
		case R.id.rb_auto_tender_complic_021:
			v.getParent().requestDisallowInterceptTouchEvent(true);
			rb_month_01.setChecked(true);
			rb_month_02.setChecked(false);
			etMonthMin.setPressed(false);
			etMonthMax.setPressed(false);
			break;
		case R.id.rb_auto_tender_complic_022:
			v.getParent().requestDisallowInterceptTouchEvent(true);
			rb_month_01.setChecked(false);
			rb_month_02.setChecked(true);
			etMonthMin.setPressed(true);
			etMonthMax.setPressed(true);
			break;
		case R.id.et_auto_tender_complic_month_01:
			v.getParent().requestDisallowInterceptTouchEvent(true);
			putMonthPopwindows(this, etMonthMin);
			rb_month_01.setChecked(false);
			rb_month_02.setChecked(true);
			etMonthMin.setEnabled(false);
			etMonthMax.setEnabled(false);
			break;
		case R.id.et_auto_tender_complic_month_02:
			v.getParent().requestDisallowInterceptTouchEvent(true);
			putMonthPopwindows(this, etMonthMax);
			rb_month_01.setChecked(false);
			rb_month_02.setChecked(true);
			etMonthMin.setEnabled(false);
			etMonthMax.setEnabled(false);
			break;
		case R.id.rb_auto_tender_complic_023:
			v.getParent().requestDisallowInterceptTouchEvent(true);
			rb_precent_03.setChecked(true);
			rb_precent_04.setChecked(false);
			etPercentMin.setPressed(false);
			etPercentMax.setPressed(false);
			break;
		case R.id.rb_auto_tender_complic_024:
			v.getParent().requestDisallowInterceptTouchEvent(true);
			rb_precent_03.setChecked(false);
			rb_precent_04.setChecked(true);
			etPercentMin.setPressed(true);
			etPercentMax.setPressed(true);
			break;
		case R.id.et_auto_tender_complic_percent_01:
			v.getParent().requestDisallowInterceptTouchEvent(true);
			putPercentPopwindows(this, etPercentMin);
			rb_precent_03.setChecked(false);
			rb_precent_04.setChecked(true);
			etPercentMin.setEnabled(false);
			etPercentMax.setEnabled(false);
			break;
		case R.id.et_auto_tender_complic_percent_02:
			v.getParent().requestDisallowInterceptTouchEvent(true);
			putPercentPopwindows(this, etPercentMax);
			rb_precent_03.setChecked(false);
			rb_precent_04.setChecked(true);
			etPercentMin.setEnabled(false);
			etPercentMax.setEnabled(false);
			break;

		default:
			break;
		}

	}

	private ASelectWheelPopupWindow sharePopupWindowLine;

	private void setPopwindowsOut(Context cx, ArrayList<String> ds, OnComfirmListener mlistener, String title,
			final int i) {
		// 传入上下文，数据，选择按钮监听器
		sharePopupWindowLine = new ASelectWheelPopupWindow(cx, ds, mlistener, title);
		sharePopupWindowLine.showWindow();
		sharePopupWindowLine.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				if (i == 0) {
					etMonthMin.setPressed(true);
					etMonthMax.setPressed(true);
					etMonthMin.setEnabled(true);
					etMonthMax.setEnabled(true);
				} else {
					etPercentMin.setPressed(true);
					etPercentMax.setPressed(true);
					etPercentMin.setEnabled(true);
					etPercentMax.setEnabled(true);
				}
			}
		});
		sharePopupWindowLine.showAtLocation(findViewById(R.id.title_content_tv), Gravity.TOP, 0, 0);

	}

	private void putMonthPopwindows(Context context, final TextView et) {
		// imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
		class MyProportionOnComfirmListener implements OnComfirmListener {

			@Override
			public void onArticleSelected(String ms) {
				et.setText(ms);
				CharSequence text = et.getText();
				if (text instanceof Spannable) {
					Spannable spanText = (Spannable) text;
					Selection.setSelection(spanText, text.length());
				}
				etMonthMin.setPressed(true);
				etMonthMax.setPressed(true);
				etMonthMin.setEnabled(true);
				etMonthMax.setEnabled(true);
			}

		}

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
		setPopwindowsOut(context, ds, new MyProportionOnComfirmListener(), "选择余额比例", 0);

	}

	private void putPercentPopwindows(Context context, final TextView et) {
		// imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
		class MyProportionOnComfirmListener implements OnComfirmListener {

			@Override
			public void onArticleSelected(String ms) {
				et.setText(ms);
				CharSequence text = et.getText();
				if (text instanceof Spannable) {
					Spannable spanText = (Spannable) text;
					Selection.setSelection(spanText, text.length());
				}
				etPercentMin.setPressed(true);
				etPercentMax.setPressed(true);
				etPercentMin.setEnabled(true);
				etPercentMax.setEnabled(true);
			}

		}

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
		setPopwindowsOut(context, ds, new MyProportionOnComfirmListener(), "选择余额比例", 1);

	}

	@Override
	protected boolean performPre() {
		Intent intent = new Intent(this, AutoTenderComplicMoneyActivity.class);
		return updateParameter(intent);
	}

	@Override
	protected boolean performNext() {
		Intent intent = new Intent(this, AutoTenderComplicProjectActivity.class);
		return updateParameter(intent);
	}

	private boolean updateParameter(Intent intent) {
		if (rb_month_01.isChecked()) {
			AutoTenderActivity.checkedItemBean.setMinborrowmonth("0");
			AutoTenderActivity.checkedItemBean.setMaxborrowmonth("0");
		}
		if (rb_month_02.isChecked()) {
			String strMonthMin = etMonthMin.getText().toString().trim();
			String strMonthMax = etMonthMax.getText().toString().trim();
			if (strMonthMin.equals("") || strMonthMax.equals("")) {
				CommonUtils.toastMsgShort(mContext, "请完善投月标范围！");
				return true;
			}
			String minborrowmonth = strMonthMin.substring(0, strMonthMin.indexOf("月"));
			String maxborrowmonth = strMonthMax.substring(0, strMonthMax.indexOf("月"));
			try {
				int minmonth = Integer.parseInt(minborrowmonth);
				int maxmonth = Integer.parseInt(maxborrowmonth);
				if (minmonth > maxmonth) {
					CommonUtils.toastMsgShort(mContext, "最大月份必须大于等于最小月份！");
					return true;
				}
			} catch (Exception e) {
				CommonUtils.toastMsgShort(mContext, "类型转换异常！");
				return true;
			}
			AutoTenderActivity.checkedItemBean.setMinborrowmonth(minborrowmonth);
			AutoTenderActivity.checkedItemBean.setMaxborrowmonth(maxborrowmonth);
		}
		if (rb_precent_03.isChecked()) {
			AutoTenderActivity.checkedItemBean.setMinborrowrate("0.0000");
			AutoTenderActivity.checkedItemBean.setMaxborrowrate("0.0000");
		}
		if (rb_precent_04.isChecked()) {
			String strPercentMin = etPercentMin.getText().toString().trim();
			String strPercentMax = etPercentMax.getText().toString().trim();
			if (strPercentMin.equals("") || strPercentMax.equals("")) {
				CommonUtils.toastMsgShort(mContext, "请完善收益率范围！");
				return true;
			}
			String minborrowrate = strPercentMin.substring(0, strPercentMin.indexOf("%"));
			String maxborrowrate = strPercentMax.substring(0, strPercentMax.indexOf("%"));
			try {
				int minwrate = Integer.parseInt(minborrowrate);
				int maxwrate = Integer.parseInt(maxborrowrate);
				if (minwrate > maxwrate) {
					CommonUtils.toastMsgShort(mContext, "最大收益率必须大于等于最小收益率！");
					return true;
				}
			} catch (Exception e) {
				CommonUtils.toastMsgShort(mContext, "类型转换异常！");
				return true;
			}
			AutoTenderActivity.checkedItemBean.setMinborrowrate(minborrowrate+".0000");
			AutoTenderActivity.checkedItemBean.setMaxborrowrate(maxborrowrate+".0000");
		}
		startActivity(intent);
		return false;
	}

	@Override
	protected void complicAutoTender() {
		if (rb_month_01.isChecked()) {
			AutoTenderActivity.checkedItemBean.setMinborrowmonth("0");
			AutoTenderActivity.checkedItemBean.setMaxborrowmonth("0");
		}
		if (rb_month_02.isChecked()) {
			String strMonthMin = etMonthMin.getText().toString().trim();
			String strMonthMax = etMonthMax.getText().toString().trim();
			String minborrowmonth = strMonthMin.substring(0, strMonthMin.indexOf("月"));
			String maxborrowmonth = strMonthMax.substring(0, strMonthMax.indexOf("月"));
			AutoTenderActivity.checkedItemBean.setMinborrowmonth(minborrowmonth);
			AutoTenderActivity.checkedItemBean.setMaxborrowmonth(maxborrowmonth);
		}
		if (rb_precent_03.isChecked()) {
			AutoTenderActivity.checkedItemBean.setMinborrowrate("0");
			AutoTenderActivity.checkedItemBean.setMaxborrowrate("0");
		}
		if (rb_precent_04.isChecked()) {
			String strPercentMin = etPercentMin.getText().toString().trim();
			String strPercentMax = etPercentMax.getText().toString().trim();
			String minborrowrate = strPercentMin.substring(0, strPercentMin.indexOf("%"));
			String maxborrowrate = strPercentMax.substring(0, strPercentMax.indexOf("%"));
			AutoTenderActivity.checkedItemBean.setMinborrowrate(minborrowrate);
			AutoTenderActivity.checkedItemBean.setMaxborrowrate(maxborrowrate);
		}
		Intent intent2 = new Intent(AutoTenderComplicParameterActivity.this, AutoTenderActivity.class);
		doUpdateAutoTenderRequest(intent2);
	}
}
