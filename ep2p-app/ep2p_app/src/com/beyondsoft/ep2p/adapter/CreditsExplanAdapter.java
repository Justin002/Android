package com.beyondsoft.ep2p.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.response.CreditsExplainResponse.Result.Detail;

public class CreditsExplanAdapter extends BaseAdapter{

	private List<Detail> mInviteList;
	private LayoutInflater layoutInflater;
	
	public CreditsExplanAdapter(Context context){
		layoutInflater=LayoutInflater.from(context);
	}
	
	public void addData(List<Detail> list,boolean isAdd){
		if(isAdd){
			mInviteList.addAll(list);
		}else{
			mInviteList=list;
			this.notifyDataSetChanged();
		}
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Detail detail=(Detail) getItem(position);
		ViewHolder viewHolder;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView=layoutInflater.inflate(R.layout.credits_explan_item,null);
			viewHolder.tv_credits_count=(TextView) convertView.findViewById(R.id.tv_credits_count);
			viewHolder.credits_type_tv=(TextView) convertView.findViewById(R.id.credits_type_tv);
			viewHolder.credits_time_tv=(TextView) convertView.findViewById(R.id.credits_time_tv);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		if("2".equals(detail.getPointGetType())||"3".equals(detail.getPointGetType())||"4".equals(detail.getPointGetType())||"5".equals(detail.getPointGetType())){
			viewHolder.tv_credits_count.setTextColor(0xfff39c12);
			viewHolder.tv_credits_count.setText("-"+detail.getPointValue());
		}else{
			viewHolder.tv_credits_count.setTextColor(0xff29b92c);
			viewHolder.tv_credits_count.setText("+"+detail.getPointValue());
		}
		viewHolder.credits_type_tv.setText(detail.getPointType());
		viewHolder.credits_time_tv.setText(detail.getHappenTime());
		return convertView;
	}
	
	class ViewHolder{
		TextView tv_credits_count;
		TextView credits_type_tv;
		TextView credits_time_tv;
	}
}
