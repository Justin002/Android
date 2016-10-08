package com.beyondsoft.ep2p.activity.mine.activity;

import com.beyondsoft.ep2p.R;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author hanxiaohui
 * @description 自主投标添加功能的第三个页面
 */

public class AutoTenderComplicProjectActivity extends BaseAutoTenderComplicActivity implements OnClickListener {

	private TextView tv_title;
	private TextView tv_title_right;
	private Button btn_e_plan;
	private Button btn_e_house;
	private Button btn_e_mortgage;

	private ImageView iv_e_plan;
	private ImageView iv_e_house;
	private ImageView iv_e_mortgage;

	private int btn_e_plan_status = 1;
	private int btn_e_house_status = 1;
	private int btn_e_mortgage_status = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_tender_complic_03);

		initView();
		initData();
		initListener();
	}

	private void initView() {

		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText("编辑");
		tv_title_right = (TextView) findViewById(R.id.tv_title_right);
		tv_title_right.setText("保存");
		iv_e_plan = (ImageView) findViewById(R.id.iv_auto_tender_complic_duihao_031);
		iv_e_house = (ImageView) findViewById(R.id.iv_auto_tender_complic_duihao_032);
		iv_e_mortgage = (ImageView) findViewById(R.id.iv_auto_tender_complic_duihao_033);
		btn_e_plan = (Button) findViewById(R.id.btn_auto_tender_complic_031);
		btn_e_house = (Button) findViewById(R.id.btn_auto_tender_complic_032);
		btn_e_mortgage = (Button) findViewById(R.id.btn_auto_tender_complic_033);
	}

	private void initData() {
		autoTenderInfoBean = AutoTenderActivity.checkedItemBean;
		String borrowType = autoTenderInfoBean.getBorrowType();
		if(borrowType.contains("1")){
			btn_e_plan.setBackgroundResource(R.drawable.btn_auto_tender_pressed);
			btn_e_plan.setTextColor(Color.WHITE);
			iv_e_plan.setImageResource(R.drawable.btn_left_duihao_icon_on);
			btn_e_plan_status = 0;
		}
		if(borrowType.contains("2")){
			btn_e_house.setBackgroundResource(R.drawable.btn_auto_tender_pressed);
			btn_e_house.setTextColor(Color.WHITE);
			iv_e_house.setImageResource(R.drawable.btn_left_duihao_icon_on);
			btn_e_house_status = 0;
		}
		if(borrowType.contains("3")){
			btn_e_mortgage.setBackgroundResource(R.drawable.btn_auto_tender_pressed);
			btn_e_mortgage.setTextColor(Color.WHITE);
			iv_e_mortgage.setImageResource(R.drawable.btn_left_duihao_icon_on);
			btn_e_mortgage_status = 0;
		}
		
	}
	
	private void initListener() {

		tv_title_right.setOnClickListener(this);
		btn_e_plan.setOnClickListener(this);
		btn_e_house.setOnClickListener(this);
		btn_e_mortgage.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_title_right:
			complicAutoTender();
			break;
		case R.id.btn_auto_tender_complic_031: {
			switch (btn_e_plan_status) {
			case 0:
				btn_e_plan.setBackgroundResource(R.drawable.btn_auto_tender_normal);
				btn_e_plan.setTextColor(0xff666666);
				iv_e_plan.setImageResource(R.drawable.btn_left_duihao_icon_off);
				btn_e_plan_status = 1;
				break;
			case 1:
				btn_e_plan.setBackgroundResource(R.drawable.btn_auto_tender_pressed);
				btn_e_plan.setTextColor(Color.WHITE);
				iv_e_plan.setImageResource(R.drawable.btn_left_duihao_icon_on);
				btn_e_plan_status = 0;
				break;
			default:
				break;
			}
		}
			break;
		case R.id.btn_auto_tender_complic_032: {
			switch (btn_e_house_status) {
			case 0:
				btn_e_house.setBackgroundResource(R.drawable.btn_auto_tender_normal);
				btn_e_house.setTextColor(0xff666666);
				iv_e_house.setImageResource(R.drawable.btn_left_duihao_icon_off);
				btn_e_house_status = 1;
				break;
			case 1:
				btn_e_house.setBackgroundResource(R.drawable.btn_auto_tender_pressed);
				btn_e_house.setTextColor(Color.WHITE);
				iv_e_house.setImageResource(R.drawable.btn_left_duihao_icon_on);
				btn_e_house_status = 0;
				break;
			default:
				break;
			}
		}
			break;
		case R.id.btn_auto_tender_complic_033: {
			switch (btn_e_mortgage_status) {
			case 0:
				btn_e_mortgage.setBackgroundResource(R.drawable.btn_auto_tender_normal);
				btn_e_mortgage.setTextColor(0xff666666);
				iv_e_mortgage.setImageResource(R.drawable.btn_left_duihao_icon_off);
				btn_e_mortgage_status = 1;
				break;
			case 1:
				btn_e_mortgage.setBackgroundResource(R.drawable.btn_auto_tender_pressed);
				btn_e_mortgage.setTextColor(Color.WHITE);
				iv_e_mortgage.setImageResource(R.drawable.btn_left_duihao_icon_on);
				btn_e_mortgage_status = 0;
				break;
			default:
				break;
			}
		}
			break;

		default:
			break;
		}

	}

	@Override
	protected boolean performPre() {
		AutoTenderActivity.checkedItemBean.setBorrowType(getItemBorrowType());
		startActivity(new Intent(this, AutoTenderComplicParameterActivity.class));
		return false;
	}

	@Override
	protected boolean performNext() {
		return true;
	}

	/*
	 * 获取编辑后的所选投资项目
	 */
	private String getItemBorrowType(){
		String borrowType = "";
		if (btn_e_plan_status == 0) {
			borrowType += "1";
		}
		if (btn_e_house_status == 0) {
			if (!borrowType.equals("")) {
				borrowType += ",2";
			} else {
				borrowType += "2";
			}
		}
		if (btn_e_mortgage_status == 0) {
			if (!borrowType.equals("")) {
				borrowType += ",3";
			} else {
				borrowType += "3";
			}
		}
		return borrowType;
	}

	@Override
	protected void complicAutoTender() {
		AutoTenderActivity.checkedItemBean.setBorrowType(getItemBorrowType());
		Intent intent2 = new Intent(AutoTenderComplicProjectActivity.this, AutoTenderActivity.class);
		doUpdateAutoTenderRequest(intent2);
	}
}
