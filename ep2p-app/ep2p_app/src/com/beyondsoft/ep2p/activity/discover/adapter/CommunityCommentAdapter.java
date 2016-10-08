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
public class CommunityCommentAdapter extends BaseAdapter {

	private Context mContext;
	private Handler mHandler;
	private LayoutInflater inflater;
	private int type;
	private String[] mTitles;

	public CommunityCommentAdapter(Context context, Handler handler,int type) {
		this.mContext = context;
		this.mHandler = handler;
		this.type = type;
		inflater = LayoutInflater.from(mContext);
		if(type==1){
			mTitles = new String[]{"理财产品之我见解","债权购买之精打细算"};
			
		}
	}

	@Override
	public int getCount() {
		return 3;
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
			convertView = inflater.inflate(R.layout.item_community_comment, null);
			convertView.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
//		if(type==1){
//			if(position%2==0){
//				viewHolder.title_tv.setText(mTitles[0]);
//			}else{
//				viewHolder.title_tv.setText(mTitles[1]);
//			}
//			
//		}
		
		
		
		
		return convertView;
	}

	private class ViewHolder {

		private TextView title_tv;

	}

}
