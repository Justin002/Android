package com.beyondsoft.ep2p.activity.discover;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.LoginActivity;
import com.beyondsoft.ep2p.activity.discover.DiscoverFragment.SerializableMap;
import com.beyondsoft.ep2p.activity.discover.adapter.GetNoticeAdapter;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.response.ReceiveInterestResponse;
import com.beyondsoft.ep2p.model.response.SelectReceiveInterestDetailResponse;
import com.beyondsoft.ep2p.model.response.ShowInterestResponse.Result.InterestListItem;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.StringRequest2;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.AddRateRollSucessPopupWindow;
import com.beyondsoft.ep2p.view.DayTimerTextView;
import com.google.gson.reflect.TypeToken;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author xiaoliang
 * @description 领取加息卷
 */
public class GetAddRateRollActivity extends BaseActivity {

	private TextView tv_title;
	private ImageView iv_right_falg;

	private static final int TAG_RECEIVE_INTEREST = 11;

	private TextView jiaxijuan_tv;
	private TextView date_tv;
	private Button m_makesure_bt;
	private TextView redbag_check;
	private TextView tv_residue_statistical;
	private TextView tv_residue_statistical_tb;

	private ListView lv_get_notice;
	private DayTimerTextView dttv_residue_times;

	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_addrateroll);
		intent = getIntent();
		initTitle();
		initView();
		initData();
	}

	private void initTitle() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText(R.string.e_coupon);
		iv_right_falg = (ImageView) findViewById(R.id.title_right);
		iv_right_falg.setVisibility(View.GONE);
	}

	private void initView() {

		jiaxijuan_tv = (TextView) findViewById(R.id.jiaxijuan_tv);
		double investvalue = intent.getDoubleExtra("investvalue",0.00)*100;
		jiaxijuan_tv.setText( (int)investvalue + "%");
		date_tv = (TextView) findViewById(R.id.date_tv);
		date_tv.setText("有效期:" + intent.getStringExtra("validity") + "天");
		m_makesure_bt = (Button) findViewById(R.id.m_makesure_bt);
		tv_residue_statistical = (TextView) findViewById(R.id.tv_residue_statistical);
		dttv_residue_times = (DayTimerTextView) findViewById(R.id.dttv_residue_times);
		tv_residue_statistical_tb = (TextView) findViewById(R.id.tv_residue_statistical_tb);
		redbag_check = (TextView) findViewById(R.id.redbag_check);
		lv_get_notice = (ListView) findViewById(R.id.lv_get_notice);

	}

//	private AddRateRollSucessPopupWindow sharePopupWindowLine;

	@SuppressWarnings("unchecked")
	private void initData() {
		switch (Integer.parseInt(intent.getStringExtra("code"))) {
		case 801: {// 未领取(成功)
			tv_residue_statistical.setText("剩余" + intent.getStringExtra("noalreadyreceivenumber") + "/"
					+ intent.getStringExtra("investnumber"));
			m_makesure_bt.setText("立即领取");
			m_makesure_bt.setEnabled(true);
		}
			break;
		case 802: {// 已领取（成功）
			tv_residue_statistical.setText("剩余" + intent.getStringExtra("noalreadyreceivenumber") + "/" + intent.getStringExtra("investnumber"));
			m_makesure_bt.setText("已领取");
			m_makesure_bt.setBackgroundResource(R.drawable.ling_wan);
			m_makesure_bt.setEnabled(false);
		}
			break;
		case 803: {// 抢光了（成功）
			tv_residue_statistical.setText("剩余0/"+intent.getStringExtra("investnumber"));
			tv_residue_statistical.setTextColor(this.getResources().getColor(R.color.redbag_check_gray));
			redbag_check.setTextColor(this.getResources().getColor(R.color.e_main_bg_title));
			m_makesure_bt.setText("抢光了");
			m_makesure_bt.setBackgroundResource(R.drawable.ling_wan);
			m_makesure_bt.setEnabled(false);
		}
			break;
		case 804: {// 已结束（成功）
			tv_residue_statistical.setVisibility(View.GONE);
			tv_residue_statistical_tb.setVisibility(View.INVISIBLE);
			m_makesure_bt.setText("已结束");
			m_makesure_bt.setBackgroundResource(R.drawable.ling_wan);
			m_makesure_bt.setEnabled(false);
		}
			break;
		case 805: {// 未开始（成功）
			tv_residue_statistical.setVisibility(View.GONE);
			dttv_residue_times.setVisibility(View.VISIBLE);
			String startDate = intent.getStringExtra("startdate");
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
			String date = sDateFormat.format(curDate);
			long stamp = 0;
			try {
				Date d1 = sDateFormat.parse(date);
				Date d2 = sDateFormat.parse(startDate);
				stamp = d2.getTime() - d1.getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long days = stamp / 100 / (24 * 60 * 60);
			long hours = stamp / 100 % (24 * 60 * 60) / (60 * 60);
			long mins = stamp / 100 % (24 * 60 * 60) % (60 * 60) / 60;
			long seconds = stamp / 100 % (24 * 60 * 60) % (60 * 60) % 60;
			long[] times = new long[] { days, hours, mins, seconds };
			dttv_residue_times.setTimes(times);
			redbag_check.setVisibility(View.GONE);
			tv_residue_statistical_tb.setVisibility(View.INVISIBLE);
			m_makesure_bt.setText("立即领取");
			m_makesure_bt.setBackgroundResource(R.drawable.ling_wan);
			m_makesure_bt.setEnabled(false);
		}
			break;

		default:
			break;
		}
		
		
		lv_get_notice.setAdapter(new GetNoticeAdapter(mContext, intent.getStringArrayListExtra("listParConRel"),2));
		m_makesure_bt.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN) == null
						|| "".equals(CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN))) {
					pushActivity(LoginActivity.class);
					finish();
				} else {
					receiveInterest();
				}

			}
		});

		redbag_check.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent in = new Intent(mContext, GetRollDetailActivity.class);
				in.putExtra("pid", intent.getStringExtra("pid"));
				pushActivity(in);
			}
		});
	}

	/**
	 * 领取加息劵接口
	 */
	private void receiveInterest() {
		if (CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN) == null
				|| "".equals(CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN))) {
			pushActivity(LoginActivity.class);
		} else {
			BaseService bService = new BaseService();
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
			params.put("investAwardId", intent.getStringExtra("pid"));
			connection(
					bService.getStringRequest(TAG_RECEIVE_INTEREST, URL.API_RECEIVE_INTEREST, params, this, mContext));
		}
	}

	@Override
	public void onResponse(int tag, Object values) {
		super.onResponse(tag, values);
		switch (tag) {
		case TAG_RECEIVE_INTEREST: {
//			 CommonUtils.toastMsgLong(getApplicationContext(), "领取加息劵成功！");
			ReceiveInterestResponse receiveInterestResponse = (ReceiveInterestResponse) StringRequest2
					.Json2Object(values.toString(),new TypeToken<ReceiveInterestResponse>(){});
				switch (Integer.parseInt(receiveInterestResponse.getCode())) {
				case 200:{//成功
					View view = LayoutInflater.from(mContext).inflate(R.layout.feedback_submit_success, null);
					TextView textView = (TextView) view.findViewById(R.id.info_tv);
					textView.setText(getResources().getString(R.string.dis_pick_success));
					Toast toast = new Toast(mContext);
					toast.setDuration(Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, dip2px(-75));
					toast.setView(view);
					toast.show();
					tv_residue_statistical.setText("剩余" + receiveInterestResponse.getResult().getNoAlreadyReceiveNumber() + "/" + receiveInterestResponse.getResult().getInvestNumber());
//					tv_residue_statistical.setText("剩余0/10");
					m_makesure_bt.setText("已领取");
					m_makesure_bt.setBackgroundResource(R.drawable.ling_wan);
					m_makesure_bt.setClickable(false);
				}
					break;
				case 802:{//已领取
					tv_residue_statistical.setText("剩余" + intent.getStringExtra("noalreadyreceivenumber") + "/" + intent.getStringExtra("investnumber"));
					m_makesure_bt.setText("已领取");
					m_makesure_bt.setBackgroundResource(R.drawable.ling_wan);
					m_makesure_bt.setEnabled(false);
				}
					break;
				case 803:{//抢光了
					tv_residue_statistical.setText("剩余0/"+intent.getStringExtra("investnumber"));
					tv_residue_statistical.setTextColor(this.getResources().getColor(R.color.redbag_check_gray));
					redbag_check.setTextColor(this.getResources().getColor(R.color.e_main_bg_title));
					m_makesure_bt.setText("抢光了");
					m_makesure_bt.setBackgroundResource(R.drawable.ling_wan);
					m_makesure_bt.setEnabled(false);
				}
					break;
				case 804:{//已结束
					tv_residue_statistical.setVisibility(View.GONE);
					tv_residue_statistical_tb.setVisibility(View.INVISIBLE);
					m_makesure_bt.setText("已结束");
					m_makesure_bt.setBackgroundResource(R.drawable.ling_wan);
					m_makesure_bt.setEnabled(false);
				}
					break;
				case 805:{//未开始
					tv_residue_statistical.setVisibility(View.GONE);
					dttv_residue_times.setVisibility(View.VISIBLE);
					String startDate = intent.getStringExtra("startdate");
					SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
					String date = sDateFormat.format(curDate);
					long stamp = 0;
					try {
						Date d1 = sDateFormat.parse(date);
						Date d2 = sDateFormat.parse(startDate);
						stamp = d2.getTime() - d1.getTime();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					long days = stamp / 100 / (24 * 60 * 60);
					long hours = stamp / 100 % (24 * 60 * 60) / (60 * 60);
					long mins = stamp / 100 % (24 * 60 * 60) % (60 * 60) / 60;
					long seconds = stamp / 100 % (24 * 60 * 60) % (60 * 60) % 60;
					long[] times = new long[] { days, hours, mins, seconds };
					dttv_residue_times.setTimes(times);
					redbag_check.setVisibility(View.GONE);
					tv_residue_statistical_tb.setVisibility(View.INVISIBLE);
					m_makesure_bt.setText("立即领取");
					m_makesure_bt.setBackgroundResource(R.drawable.ling_wan);
					m_makesure_bt.setEnabled(false);
				}
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
	public void onErrorResponse(int tag, String error) {
		super.onErrorResponse(tag, error);
		switch (tag) {
		case TAG_RECEIVE_INTEREST:
			CommonUtils.toastMsgLong(getApplicationContext(), "领取加息劵失败..." + error);
			break;
		
		default:
			break;
		}
	}

}
