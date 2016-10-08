package com.beyondsoft.ep2p.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.adapter.VipExchangeAdapter.ViewHolder;
import com.beyondsoft.ep2p.model.response.ExchangeResponse.Result.ExchangeDetail;
import com.beyondsoft.ep2p.utils.StringUtils;

public class PhoneBillExchangeAdapter extends BaseAdapter {
	private List<ExchangeDetail> mInviteList;
	private LayoutInflater layoutInflater;
	
	public PhoneBillExchangeAdapter(Context context){
		layoutInflater=LayoutInflater.from(context);
	}
	
	public void addData(List<ExchangeDetail> list){
		mInviteList=list;
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mInviteList==null?0:mInviteList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mInviteList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		if(position==0){
			return 0;
		}else{
			return 1;
		}
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		ExchangeDetail vipDetail=(ExchangeDetail) getItem(position);
		int type=getItemViewType(position);
		if(convertView==null){
			viewHolder=new ViewHolder();
			if(type==0){
				convertView=layoutInflater.inflate(R.layout.phone_exchange_item_top,null);
			}else{
				convertView=layoutInflater.inflate(R.layout.phone_exchange_item,null);
			}
			viewHolder.tv_vip_year=(TextView) convertView.findViewById(R.id.tv_vip_year);
			viewHolder.tv_vip_count=(TextView) convertView.findViewById(R.id.tv_vip_count);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		viewHolder.tv_vip_year.setText(vipDetail.getDictContName()+"元");
		viewHolder.tv_vip_count.setText(vipDetail.getDictContDesc());
		return convertView;
	}
	
	class ViewHolder{
		TextView tv_vip_year;
		TextView tv_vip_count;
	}
	
	public ExchangeDetail getExchangeDetail(int position){
		return mInviteList.get(position);
	}
}