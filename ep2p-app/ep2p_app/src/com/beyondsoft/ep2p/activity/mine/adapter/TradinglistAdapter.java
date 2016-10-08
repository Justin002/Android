package com.beyondsoft.ep2p.activity.mine.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.discover.adapter.BasicAdapter;
import com.beyondsoft.ep2p.model.TradingInfoBean;

/**
 * 交易记录适配器
 * 
 * @author hanxiaohui
 * 
 */


public class TradinglistAdapter extends BasicAdapter {

	private Context context;
	
	public TradinglistAdapter(Context context, List<?> list) {
		super(context, list);
		this.context = context;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		HolderView holder = null;
		if (null == v) {
			v = mInflater.inflate(R.layout.item_trading_list_item, null);
			holder = new HolderView();
			holder.mMoney = (TextView) v.findViewById(R.id.trading_list_item_money);
			holder.mType = (TextView) v.findViewById(R.id.trading_list_item_type);
			holder.mRestMoney = (TextView) v.findViewById(R.id.trading_list_item_restmoney);
			holder.mTime = (TextView) v.findViewById(R.id.trading_list_item_time);
			
			v.setTag(holder);
		} else {
			holder = (HolderView) v.getTag();
		}
		TradingInfoBean mFeedbackList = (TradingInfoBean)list.get(position);
		if(mFeedbackList.getFundValue() != 0){
			String money = mFeedbackList.getFundValue()+"";
			if(!money.contains(".")){
				money += ".00";
			}else if(money.indexOf(".") == money.length()-1){
				money += "00";
			}else if(money.indexOf(".") == money.length()-2){
				money += "0";
			}
			holder.mMoney.setText("+" + money);
			holder.mMoney.setTextColor(context.getResources().getColor(R.color.text_lvse));
		}
		if(mFeedbackList.getExpenditure() != 0){
			String money = mFeedbackList.getExpenditure()+"";
			if(!money.contains(".")){
				money += ".00";
			}else if(money.indexOf(".") == money.length()-1){
				money += "00";
			}else if(money.indexOf(".") == money.length()-2){
				money += "0";
			}
			holder.mMoney.setText(money);
			holder.mMoney.setTextColor(context.getResources().getColor(R.color.orange));
		}
		
		holder.mType.setText(mFeedbackList.getBusinessType());
		holder.mRestMoney.setText("余额：￥"+mFeedbackList.getAccountBalance());
//		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
//		String happenTime = mFeedbackList.getHappenTime();
//		long parseLong = Long.parseLong(happenTime);
//		holder.mTime.setText(sDateFormat.format(new Date(parseLong + 0)));
		holder.mTime.setText(mFeedbackList.getHappenTimes().replaceFirst("-", "年").replaceFirst("-", "月").replaceFirst(" ", "日 "));
//		holder.mTime.setText(mFeedbackList.getHappenTimes());
        
		return v;
	}

	public class HolderView {
		private TextView mMoney;
		private TextView mType;
		private TextView mRestMoney;
		private TextView mTime;
	}

}
