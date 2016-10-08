package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.ArrayList;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.view.ASelectWheelPopupWindow;
import com.beyondsoft.ep2p.view.ASelectWheelPopupWindow.OnComfirmListener;
import com.beyondsoft.ep2p.widget.BindBankCardDialog;
import com.beyondsoft.ep2p.widget.BindBankCardDialog.ButtonOnClickListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * @author hanxiaohui
 * @description 自主投标添加功能的第二个页面
 */

public class AutoTenderAddParameterActivity extends BaseActivity implements OnClickListener {

	private TextView tv_title;
	private Button btn_next;

	private RadioButton rb_month_01;
	private RadioButton rb_month_02;
	private RadioButton rb_precent_03;
	private RadioButton rb_precent_04;

	private TextView etMonthMin = null;
	private TextView etMonthMax = null;

	private TextView etPercentMin = null;
	private TextView etPercentMax = null;
	
	private String amount;
	private String balanceratio;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_tender_add_02);
		Intent intent = getIntent();
		amount = intent.getStringExtra("amount");
		balanceratio = intent.getStringExtra("balanceratio");
		initView();
		initListener();
	}

	private void initView() {

		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText("添加");
		btn_next = (Button) findViewById(R.id.btn_auto_tender_add_next_02);

		rb_month_01 = (RadioButton) findViewById(R.id.rb_auto_tender_add_021);
		rb_month_02 = (RadioButton) findViewById(R.id.rb_auto_tender_add_022);
		rb_precent_03 = (RadioButton) findViewById(R.id.rb_auto_tender_add_023);
		rb_precent_04 = (RadioButton) findViewById(R.id.rb_auto_tender_add_024);
		
		etMonthMin = (TextView) findViewById(R.id.et_auto_tender_add_month_01);
		etMonthMax = (TextView) findViewById(R.id.et_auto_tender_add_month_02);
		etPercentMin = (TextView) findViewById(R.id.et_auto_tender_add_percent_01);
		etPercentMax = (TextView) findViewById(R.id.et_auto_tender_add_percent_02);

	}

	private void initListener() {

		btn_next.setOnClickListener(this);
		rb_month_01.setOnClickListener(this);
		rb_month_02.setOnClickListener(this);
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
		case R.id.rb_auto_tender_add_021:
			rb_month_01.setChecked(true);
			rb_month_02.setChecked(false);
			etMonthMin.setPressed(false);
			etMonthMax.setPressed(false);
			break;
		case R.id.rb_auto_tender_add_022:
			rb_month_01.setChecked(false);
			rb_month_02.setChecked(true);
			etMonthMin.setPressed(true);
			etMonthMax.setPressed(true);
			break;
		case R.id.et_auto_tender_add_month_01:
			rb_month_01.setChecked(false);
			rb_month_02.setChecked(true);
			etMonthMin.setEnabled(false);
			etMonthMax.setEnabled(false);
			putMonthPopwindows(this, etMonthMin);
			break;
		case R.id.et_auto_tender_add_month_02:
			rb_month_01.setChecked(false);
			rb_month_02.setChecked(true);
			etMonthMin.setEnabled(false);
			etMonthMax.setEnabled(false);
			putMonthPopwindows(this, etMonthMax);
			break;
		case R.id.rb_auto_tender_add_023:
			rb_precent_03.setChecked(true);
			rb_precent_04.setChecked(false);
			etPercentMin.setPressed(false);
			etPercentMax.setPressed(false);
			break;
		case R.id.rb_auto_tender_add_024:
			rb_precent_03.setChecked(false);
			rb_precent_04.setChecked(true);
			etPercentMin.setPressed(true);
			etPercentMax.setPressed(true);
			break;
		case R.id.et_auto_tender_add_percent_01:
			rb_precent_03.setChecked(false);
			rb_precent_04.setChecked(true);
			etPercentMin.setEnabled(false);
			etPercentMax.setEnabled(false);
			putPercentPopwindows(this, etPercentMin);
			break;
		case R.id.et_auto_tender_add_percent_02:
			rb_precent_03.setChecked(false);
			rb_precent_04.setChecked(true);
			etPercentMin.setEnabled(false);
			etPercentMax.setEnabled(false);
			putPercentPopwindows(this, etPercentMax);
			break;

		case R.id.btn_auto_tender_add_next_02:
			Intent intent = new Intent(this, AutoTenderAddProjectActivity.class);
			intent.putExtra("amount", amount);
			intent.putExtra("balanceratio", balanceratio.substring(0, balanceratio.indexOf("%")));
			if(rb_month_01.isChecked()){
				intent.putExtra("minborrowmonth", "0");
				intent.putExtra("maxborrowmonth", "0");
			}
			if(rb_month_02.isChecked()){
				String strMonthMin = etMonthMin.getText().toString().trim();
				String strMonthMax = etMonthMax.getText().toString().trim();
				if(strMonthMin.equals("") || strMonthMax.equals("")){
					CommonUtils.toastMsgShort(mContext, "请完善投月标范围！");
					break;
				}
				String minborrowmonth = strMonthMin.substring(0, strMonthMin.indexOf("月"));
				String maxborrowmonth = strMonthMax.substring(0, strMonthMax.indexOf("月"));
				try {
					int minmonth = Integer.parseInt(minborrowmonth);
					int maxmonth = Integer.parseInt(maxborrowmonth);
					if(minmonth > maxmonth){
						CommonUtils.toastMsgShort(mContext, "最大月份必须大于等于最小月份！");
						break;
					}
				} catch (Exception e) {
					CommonUtils.toastMsgShort(mContext, "类型转换异常！");
					break;
				}
				intent.putExtra("minborrowmonth", minborrowmonth);
				intent.putExtra("maxborrowmonth", maxborrowmonth);
			}
			if(rb_precent_03.isChecked()){
				intent.putExtra("minborrowrate", "0");
				intent.putExtra("maxborrowrate", "0");
			}
			if(rb_precent_04.isChecked()){
				String strPercentMin = etPercentMin.getText().toString().trim();
				String strPercentMax = etPercentMax.getText().toString().trim();
				if(strPercentMin.equals("") || strPercentMax.equals("")){
					CommonUtils.toastMsgShort(mContext, "请完善收益率范围！");
					break;
				}
				String minborrowrate = strPercentMin.substring(0, strPercentMin.indexOf("%"));
				String maxborrowrate = strPercentMax.substring(0, strPercentMax.indexOf("%"));
				try {
					int minwrate = Integer.parseInt(minborrowrate);
					int maxwrate = Integer.parseInt(maxborrowrate);
					if(minwrate > maxwrate){
						CommonUtils.toastMsgShort(mContext, "最大收益率必须大于等于最小收益率！");
						break;
					}
				} catch (Exception e) {
					CommonUtils.toastMsgShort(mContext, "类型转换异常！");
					break;
				}
				intent.putExtra("minborrowrate", minborrowrate);
				intent.putExtra("maxborrowrate", maxborrowrate);
			}
			startActivity(intent);
			break;

		default:
			break;
		}

	}

	private ASelectWheelPopupWindow sharePopupWindowLine;

	private void setPopwindowsOut(Context cx, ArrayList<String> ds, OnComfirmListener mlistener, String title, final int i) {
		// 传入上下文，数据，选择按钮监听器
		sharePopupWindowLine = new ASelectWheelPopupWindow(cx, ds, mlistener, title);
		sharePopupWindowLine.showWindow();
		sharePopupWindowLine.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				if(i == 0){
					etMonthMin.setPressed(true);
					etMonthMax.setPressed(true);
					etMonthMin.setEnabled(true);
					etMonthMax.setEnabled(true);
				}else{
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
		setPopwindowsOut(context, ds, new MyProportionOnComfirmListener(), null, 0);

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
		setPopwindowsOut(context, ds, new MyProportionOnComfirmListener(), null, 1);

	}

}
