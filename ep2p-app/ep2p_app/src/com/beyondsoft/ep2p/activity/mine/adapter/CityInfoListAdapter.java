package com.beyondsoft.ep2p.activity.mine.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.mine.bean.CityInfoBean;

/**
 * 城市列表适配器
 * 
 * @author hardy.zhou
 *
 */
public class CityInfoListAdapter extends BaseAdapter {

	private Context mContext;
	private List<CityInfoBean> mCityInfoBeanList;
	private List<Integer> letterPositionList;
	private Handler mHandler;

	public CityInfoListAdapter(Context mContext, Handler mHandler) {
		this.mContext = mContext;
		this.mHandler = mHandler;
		mCityInfoBeanList = new ArrayList<CityInfoBean>();
	}

	public CityInfoListAdapter(Context mContext, List<CityInfoBean> cityInfoBeanList) {
		this.mContext = mContext;
		this.mCityInfoBeanList = cityInfoBeanList;
	}

	/**
	 * 设置数据列表
	 * 
	 * @param cityInfoBeanList
	 */
	public void setData(List<CityInfoBean> cityInfoBeanList, List<Integer> letterPositionList) {
		this.mCityInfoBeanList = cityInfoBeanList;
		this.letterPositionList = letterPositionList;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return (mCityInfoBeanList == null ? 0 : mCityInfoBeanList.size());
	}

	@Override
	public Object getItem(int arg0) {
		return mCityInfoBeanList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			final LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.item_city_info_item, null);
			viewHolder = new ViewHolder();
			convertView.setTag(viewHolder);
			viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			viewHolder.ll_letter = (LinearLayout) convertView.findViewById(R.id.ll_letter);
			viewHolder.tv_letter = (TextView) convertView.findViewById(R.id.tv_letter);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final CityInfoBean contact = mCityInfoBeanList.get(position);
		if (null != contact) {
			viewHolder.tv_name.setText(contact.getName());
			viewHolder.tv_letter.setText(contact.getLetter());
		}
		if (letterPositionList.contains(position)) {
			viewHolder.ll_letter.setVisibility(View.VISIBLE);
		} else {
			viewHolder.ll_letter.setVisibility(View.GONE);
		}
		return convertView;
	}

	private class ViewHolder {

		private TextView tv_name;
		private LinearLayout ll_letter;
		private TextView tv_letter;
	}

	/**
	 * 自定义点击事件
	 * 
	 * @author hardy.zhou
	 *
	 */
	private class OnClickEvent implements OnClickListener {
		private Context mContext;
		private CityInfoBean cityInfoBean;

		public OnClickEvent(Context mContext, CityInfoBean contact) {
			this.mContext = mContext;
			this.cityInfoBean = contact;
		}

		@Override
		public void onClick(View arg0) {
			// TODO
		}
	}
}
