package com.beyondsoft.ep2p.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beyondsoft.ep2p.R;

/**
 * @author Ivan.Lu
 * @description 积分任务适配器
 */
public class CreditTaskAdapter extends BaseAdapter {

	private List<?> mInviteList;
	private LayoutInflater layoutInflater;
	
	public CreditTaskAdapter(Context context){
		layoutInflater=LayoutInflater.from(context);
	}
	
	public void addData(List<?> list){
		mInviteList=list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
//		return mInviteList==null?0:mInviteList.size();
		return 100;
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
		ViewHolder viewHolder;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView=layoutInflater.inflate(R.layout.credit_task_item,null);
			viewHolder.name_tv=(TextView) convertView.findViewById(R.id.invite_name_tv);
			viewHolder.time_tv=(TextView) convertView.findViewById(R.id.invite_time_tv);
			viewHolder.reward_tv=(TextView) convertView.findViewById(R.id.invite_reward_tv);
		}
		return convertView;
	}
	
	class ViewHolder{
		TextView name_tv;
		TextView time_tv;
		TextView reward_tv;
	}
}
