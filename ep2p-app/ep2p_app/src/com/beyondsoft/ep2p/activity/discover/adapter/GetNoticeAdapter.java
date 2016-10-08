package com.beyondsoft.ep2p.activity.discover.adapter;

import java.util.List;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.response.ShowInterestResponse.Result.InterestListItem;
import com.beyondsoft.ep2p.model.response.ShowRedResponse.Result.RedListItem;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GetNoticeAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater inflater;
	private List<?> list;
	private int type;
	private String condName;
	
	
	public GetNoticeAdapter(Context context,List<?> list,int type) {
		this.mContext = context;
		this.list = list;
		this.type = type;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		if (null == convertView) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_get_notice_item, null);
			viewHolder.tv_get_notice_item = (TextView) convertView.findViewById(R.id.tv_get_notice_item);
			
			convertView.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if(type == 1){
			RedListItem redListItem = (RedListItem)list.get(position);
			condName = redListItem.getCondName();
			viewHolder.tv_get_notice_item.setTextColor(mContext.getResources().getColor(R.color.orange_discover));
		}else if(type == 2){
			condName = (String) list.get(position);
			viewHolder.tv_get_notice_item.setTextColor(Color.WHITE);
			
		}
		viewHolder.tv_get_notice_item.setText((position+1)+"."+condName);
		
		return convertView;
	}
	
	private class ViewHolder {

		private TextView tv_get_notice_item;
		
	}

}
