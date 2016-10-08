package com.beyondsoft.ep2p.activity.discover.adapter;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.view.RoundProgressBar;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 红包详情
 * 
 * @author xiaoliang
 *
 */
public class CommunityFirstListAdapter extends BaseAdapter {

	private Context mContext;
	private Handler mHandler;
	private LayoutInflater inflater;
	private int type;
	private String[] mTitles;

	public CommunityFirstListAdapter(Context context, Handler handler,int type) {
		this.mContext = context;
		this.mHandler = handler;
		this.type = type;
		inflater = LayoutInflater.from(mContext);
		if(type==1){
			mTitles = new String[]{"投资交流","新手学院","综合讨论区"};
			
		}
	}

	@Override
	public int getCount() {
		return mTitles.length;
	}

	@Override
	public Object getItem(int position) {
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
			convertView = inflater.inflate(R.layout.item_community_firstlist, null);
			viewHolder.title_tv = (TextView) convertView.findViewById(R.id.title_tv);
			convertView.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if(type==1){
			viewHolder.title_tv.setText(mTitles[position]);
		}
		
		
		
		
		return convertView;
	}

	private class ViewHolder {

		private TextView title_tv;

	}

}
