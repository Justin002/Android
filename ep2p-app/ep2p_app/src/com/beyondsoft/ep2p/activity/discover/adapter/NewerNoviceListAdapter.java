package com.beyondsoft.ep2p.activity.discover.adapter;

import java.util.List;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.response.NewVoiceGuideResponse.Result.GuideListItem;
import com.beyondsoft.ep2p.model.response.SysNoticeResponse.SysNoticeItem;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 新手指引列表
 */
@SuppressLint("InflateParams")
public class NewerNoviceListAdapter  extends BaseAdapter {
	private Context mContext;
	private LayoutInflater inflater;
	 private List<GuideListItem> mList;

	public NewerNoviceListAdapter(List<GuideListItem> list,Context context) {
		this.mContext = context;
		this.mList=list;
		inflater = LayoutInflater.from(mContext);
	}
	public void addData(List<GuideListItem> list,boolean isAdd){
		if(isAdd){
			if(mList!=null)
				mList.addAll(list);
		}else{
			mList=list;
		}
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mList.size()>0?mList.size():0;
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
		ViewHolder viewHolder = null;
		GuideListItem guideListItem=(GuideListItem) getItem(position);
		if (null == convertView) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_newernovice_first, null);
			viewHolder.title_tv = (TextView) convertView.findViewById(R.id.title_tv_guide);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.title_tv .setText(guideListItem.getTitleName());
		return convertView;
	}
	private class ViewHolder {
		private TextView title_tv;
	}

}
