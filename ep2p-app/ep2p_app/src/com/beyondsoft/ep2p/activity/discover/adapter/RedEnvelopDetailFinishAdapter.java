package com.beyondsoft.ep2p.activity.discover.adapter;

import com.beyondsoft.ep2p.R;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 红包领完了详情
 * 
 * @author xiaoliang
 *
 */
public class RedEnvelopDetailFinishAdapter extends BaseAdapter {

	private Context mContext;
	private Handler mHandler;
	private LayoutInflater inflater;
	private int type;

	public RedEnvelopDetailFinishAdapter(Context context,int type) {
		this.mContext = context;
		this.type = type;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return 20;
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
			convertView = inflater.inflate(R.layout.item_redenvelope_detail, null);
			viewHolder.e_number = (TextView) convertView.findViewById(R.id.e_number);
			viewHolder.pinglun_name = (TextView) convertView.findViewById(R.id.pinglun_name);
			
			convertView.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if(type==2){
			viewHolder.e_number.setText("加息1.3%");
			viewHolder.pinglun_name.setText("1868205****");
			
		}else if(type==1){
			viewHolder.e_number.setText("￥9.75");
			viewHolder.pinglun_name.setText("1868205****");
			
		}
		
		
		
		
		return convertView;
	}

	private class ViewHolder {

		private TextView e_number;
		private TextView pinglun_name;
	}

}
