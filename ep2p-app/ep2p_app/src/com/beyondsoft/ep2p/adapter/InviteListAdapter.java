package com.beyondsoft.ep2p.adapter;

import java.util.List;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.response.InvitePrizeListResponse.Result.InviteList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author Ivan.Lu
 * @description "我的"-"我的邀请"适配器
 */
public class InviteListAdapter extends BaseAdapter {

	private List<InviteList> mInviteList;
	private LayoutInflater layoutInflater;
	public InviteListAdapter(Context context){
		layoutInflater=LayoutInflater.from(context);
	}
	
	public void addData(List<InviteList> list){
		mInviteList=list;
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
		InviteList inviteList=(InviteList) getItem(position);
		ViewHolder viewHolder;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView=layoutInflater.inflate(R.layout.mine_invite_item,null);
			viewHolder.name_tv=(TextView) convertView.findViewById(R.id.invite_name_tv);
			viewHolder.time_tv=(TextView) convertView.findViewById(R.id.invite_time_tv);
			viewHolder.reward_tv=(TextView) convertView.findViewById(R.id.invite_reward_tv);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		viewHolder.name_tv.setText(inviteList.getCustomerName());
		viewHolder.time_tv.setText(inviteList.getCreateTime());
		return convertView;
	}
	
	class ViewHolder{
		TextView name_tv;
		TextView time_tv;
		TextView reward_tv;
	}

}
