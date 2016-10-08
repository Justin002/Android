package com.beyondsoft.ep2p.activity.discover;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.beyondsoft.ep2p.BaseFragment;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.LoginActivity;
import com.beyondsoft.ep2p.activity.discover.adapter.GetNoticeAdapter;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.response.ShowRedResponse.Result.RedListItem;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.view.DayTimerTextView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author xiaoliang
 * @description 红包展示界面
 */
public class RedFirstFragment extends BaseFragment {
	public interface OnArticleSelectedListener {
		public void onArticleSelected();
	}
	private OnArticleSelectedListener mListener;
	private LinearLayout main_ll;
	private TextView redbag_check;
	private View view ;
	private String noAlreadyReceiveNumber;
	private String repNumber;
	private ArrayList<RedListItem> redLIst;
	private TextView tv_count;
	
	private TextView tv_residue_statistical_tb;
	private ListView lv_get_notice;
	private DayTimerTextView dttv_residue_times;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnArticleSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnArticleSelectedListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_receive_redenvelope_first,container, false);
		init(view);
		setListener();
		return view;
	}

	private void setListener() {
		redbag_check.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent in = new Intent(mContext, ReceiveRedenvelopeDetailActivity.class);
				in.putExtra("pid", getArguments().getString("pid"));
				pushActivity(in);
			}
		});
	}

	private void init(View view) {
		main_ll = (LinearLayout) view.findViewById(R.id.main_ll);
		tv_count=(TextView) view.findViewById(R.id.tv_count);
		dttv_residue_times = (DayTimerTextView) view.findViewById(R.id.dttv_residue_times);
		tv_residue_statistical_tb = (TextView) view.findViewById(R.id.tv_residue_statistical_tb);
		redbag_check = (TextView) view.findViewById(R.id.redbag_check);
		lv_get_notice = (ListView) view.findViewById(R.id.lv_get_notice);
		noAlreadyReceiveNumber = getActivity().getIntent().getStringExtra("noalreadyreceivenumber");
		repNumber = getActivity().getIntent().getStringExtra("repnumber");
		redLIst = (ArrayList<RedListItem>) getActivity().getIntent().getSerializableExtra("listparconrel");
		
		switch (Integer.parseInt(getActivity().getIntent().getStringExtra("code"))) {
		case 701:{//未领取(成功)
			main_ll.setBackgroundResource(R.drawable.discover_r_chai_bg_chb);
			tv_count.setText("剩余" + noAlreadyReceiveNumber + "/" + repNumber);
			main_ll.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					if( CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN)==null||"".equals( CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN))){
						pushActivity(LoginActivity.class);
						getActivity().finish();
					}else{
						mListener.onArticleSelected();
					}
				}
			});
		}
			break;
		case 702:{//已领取（成功）
			main_ll.setBackgroundResource(R.drawable.discover_r_chai_bg_ylq);
			tv_count.setText("剩余" + noAlreadyReceiveNumber + "/" + repNumber);
			main_ll.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					if( CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN)==null||"".equals( CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN))){
						pushActivity(LoginActivity.class);
						getActivity().finish();
					}else{
						mListener.onArticleSelected();
					}
				}
			});
			
		}
		break;
		case 703:{//抢光了（成功）
			main_ll.setBackgroundResource(R.drawable.discover_r_chai_bg_qgl);
			tv_count.setText("剩余 0/" + repNumber);
			tv_count.setTextColor(getResources().getColor(R.color.dis_r_zhongse));
			redbag_check.setTextColor(getResources().getColor(R.color.orange_discover));
		}
		break;
		case 704:{//已结束（成功）
			main_ll.setBackgroundResource(R.drawable.discover_r_chai_bg_yjs);
			tv_count.setText("剩余" + noAlreadyReceiveNumber + "/" + repNumber);
			tv_count.setTextColor(getResources().getColor(R.color.dis_r_zhongse));
			redbag_check.setTextColor(getResources().getColor(R.color.orange_discover));
			
		}
		break;
		case 805:{//未开始（成功）
			main_ll.setBackgroundResource(R.drawable.discover_r_chai_bg_wks);
			tv_count.setVisibility(View.GONE);
			dttv_residue_times.setVisibility(View.VISIBLE);
			if(!TextUtils.isEmpty(getActivity().getIntent().getStringExtra("startdate"))){
				
			String startDate = getActivity().getIntent().getStringExtra("startdate");
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
			}
			redbag_check.setVisibility(View.GONE);
			tv_residue_statistical_tb.setVisibility(View.INVISIBLE);
		}
		break;
		default:
			break;
		}
		lv_get_notice.setAdapter(new GetNoticeAdapter(mContext, redLIst,1));
		
	}
}
