package com.beyondsoft.ep2p.activity.discover.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.response.RatioStationListResponse.Result.RadioListItem;
import com.beyondsoft.ep2p.utils.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.weibo.sdk.constant.WBConstants.Msg;


public class PopRadioAdapter extends BaseAdapter {
	private Context mContext;
	private List<RadioListItem> mList;
	private ImageLoader imageLoader=ImageLoader.getInstance();

	public PopRadioAdapter(Context context) {
		mContext=context;
	}
	
	public void addData(List<RadioListItem> list){
		mList=list;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mList==null?0:mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RadioListItem popRadionItem=(RadioListItem) getItem(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_pg_station, null);
			viewHolder.cover_bg_iv=(ImageView) convertView.findViewById(R.id.cover_bg_iv);
			viewHolder.cover_tv=(TextView) convertView.findViewById(R.id.cover_tv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		imageLoader.displayImage(popRadionItem.getProgramType(), viewHolder.cover_bg_iv,ImageLoadOptions.getPopRadioOptions());
		viewHolder.cover_tv.setText(popRadionItem.getProgramTitle());
		return convertView;
	}

	class ViewHolder {
		ImageView cover_bg_iv;
		TextView cover_tv;
	}
	
	public RadioListItem getRadioItem(int position){
		return mList.get(position);
	}
}