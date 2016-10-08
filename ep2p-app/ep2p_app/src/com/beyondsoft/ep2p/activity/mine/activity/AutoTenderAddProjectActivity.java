package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.HashMap;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.RefreshDialog;

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

public class AutoTenderAddProjectActivity extends BaseActivity implements OnClickListener {

	private TextView tv_title;
	private Button btn_e_plan;
	private Button btn_e_house;
	private Button btn_e_mortgage;
	private Button btn_finish;

	private ImageView iv_e_plan;
	private ImageView iv_e_house;
	private ImageView iv_e_mortgage;

	private int btn_e_plan_status = 1;// 0：选中； 1：未选中
	private int btn_e_house_status = 1;// 0：选中； 1：未选中
	private int btn_e_mortgage_status = 1;// 0：选中； 1：未选中

	private static final int TAG_ADD_AUTO_TENDER = 300;

	BaseService baseService;
	SecurityCenterService securityCenterService;

	private String amount;
	private String balanceratio;
	private String minborrowmonth;
	private String maxborrowmonth;
	private String minborrowrate;
	private String maxborrowrate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_tender_add_03);
		baseService = new BaseService();
		securityCenterService = new SecurityCenterService();
		getIntentvalues();
		initView();
		initListener();
	}

	private void getIntentvalues() {
		Intent intent = getIntent();
		amount = intent.getStringExtra("amount");
		balanceratio = intent.getStringExtra("balanceratio");
		minborrowmonth = intent.getStringExtra("minborrowmonth");
		maxborrowmonth = intent.getStringExtra("maxborrowmonth");
		minborrowrate = intent.getStringExtra("minborrowrate");
		maxborrowrate = intent.getStringExtra("maxborrowrate");

	}

	private void initView() {

		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText("添加");
		iv_e_plan = (ImageView) findViewById(R.id.iv_auto_tender_duihao_031);
		iv_e_house = (ImageView) findViewById(R.id.iv_auto_tender_duihao_032);
		iv_e_mortgage = (ImageView) findViewById(R.id.iv_auto_tender_duihao_033);
		btn_e_plan = (Button) findViewById(R.id.btn_auto_tender_add_031);
		btn_e_house = (Button) findViewById(R.id.btn_auto_tender_add_032);
		btn_e_mortgage = (Button) findViewById(R.id.btn_auto_tender_add_033);
		btn_finish = (Button) findViewById(R.id.btn_auto_tender_add_next_finish);
		btn_finish.setEnabled(false);
	}

	private void initListener() {

		btn_e_plan.setOnClickListener(this);
		btn_e_house.setOnClickListener(this);
		btn_e_mortgage.setOnClickListener(this);
		btn_finish.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_auto_tender_add_031: {
			switch (btn_e_plan_status) {
			case 0:
				btn_e_plan.setBackgroundResource(R.drawable.btn_auto_tender_normal);
				btn_e_plan.setTextColor(0xff666666);
				iv_e_plan.setImageResource(R.drawable.btn_left_duihao_icon_off);
				btn_e_plan_status = 1;
				changFinishButtonStatus();
				break;
			case 1:
				btn_e_plan.setBackgroundResource(R.drawable.btn_auto_tender_pressed);
				btn_e_plan.setTextColor(Color.WHITE);
				iv_e_plan.setImageResource(R.drawable.btn_left_duihao_icon_on);
				btn_e_plan_status = 0;
				changFinishButtonStatus();
				break;
			default:
				break;
			}
		}
			break;
		case R.id.btn_auto_tender_add_032: {
			switch (btn_e_house_status) {
			case 0:
				btn_e_house.setBackgroundResource(R.drawable.btn_auto_tender_normal);
				btn_e_house.setTextColor(0xff666666);
				iv_e_house.setImageResource(R.drawable.btn_left_duihao_icon_off);
				btn_e_house_status = 1;
				changFinishButtonStatus();
				break;
			case 1:
				btn_e_house.setBackgroundResource(R.drawable.btn_auto_tender_pressed);
				btn_e_house.setTextColor(Color.WHITE);
				iv_e_house.setImageResource(R.drawable.btn_left_duihao_icon_on);
				btn_e_house_status = 0;
				changFinishButtonStatus();
				break;
			default:
				break;
			}
		}
			break;
		case R.id.btn_auto_tender_add_033: {
			switch (btn_e_mortgage_status) {
			case 0:
				btn_e_mortgage.setBackgroundResource(R.drawable.btn_auto_tender_normal);
				btn_e_mortgage.setTextColor(0xff666666);
				iv_e_mortgage.setImageResource(R.drawable.btn_left_duihao_icon_off);
				btn_e_mortgage_status = 1;
				changFinishButtonStatus();
				break;
			case 1:
				btn_e_mortgage.setBackgroundResource(R.drawable.btn_auto_tender_pressed);
				btn_e_mortgage.setTextColor(Color.WHITE);
				iv_e_mortgage.setImageResource(R.drawable.btn_left_duihao_icon_on);
				btn_e_mortgage_status = 0;
				changFinishButtonStatus();
				break;
			default:
				break;
			}
		}
			break;
		case R.id.btn_auto_tender_add_next_finish:
			if (btn_e_plan_status == 1 && btn_e_house_status == 1 && btn_e_mortgage_status == 1) {
				CommonUtils.toastMsgShort(mContext, "请选择所要投标的项目！");
			} else {
				doAddAutoTenderRequest();
			}
			break;

		default:
			break;
		}

	}

	private void changFinishButtonStatus() {
		if (btn_e_plan_status == 1 && btn_e_house_status == 1 && btn_e_mortgage_status == 1) {
			btn_finish.setEnabled(false);
		} else {
			btn_finish.setEnabled(true);
		}
	}

	private void doAddAutoTenderRequest() {
		RefreshDialog.startProgressDialog(mContext, "");
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("amount", amount);
		params.put("balanceratio", balanceratio);
		params.put("minborrowmonth", minborrowmonth);
		params.put("maxborrowmonth", maxborrowmonth);
		params.put("minborrowrate", minborrowrate);
		params.put("maxborrowrate", maxborrowrate);

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
		params.put("borrowType", borrowType);
		params.put("autostatus", 1);
		connection(baseService.getStringRequest(TAG_ADD_AUTO_TENDER, URL.URL_ADD_AUTO_TENDER, params, mContext));
	}

	@Override
	public void onResponse(int tag, Object values) {
		super.onResponse(tag, values);
		switch (tag) {
		case TAG_ADD_AUTO_TENDER: {
//			CommonUtils.toastMsgShort(mContext, "chenggong");
			RefreshDialog.stopProgressDialog();
			CommonUtils.toastMsgShortForStyle(this,"添加成功");
			startActivity(new Intent(mContext, AutoTenderActivity.class));
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
		case TAG_ADD_AUTO_TENDER: {
			CommonUtils.toastMsgShort(mContext, error);
		}

			break;
		default:
			break;
		}
	}
}
