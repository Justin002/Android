package com.beyondsoft.ep2p.activity.mine.adapter;

import java.util.ArrayList;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.CreditorRightsInfoBean;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 债券转让完成适配器
 * 
 * @author hardy.zhou
 *
 */
public class CreditorRightsTransfDoneAdapter extends BaseAdapter {

	private Context mContext;
	private Handler mHandler;
	private LayoutInflater inflater;

	private ArrayList<CreditorRightsInfoBean> mCreditorRightsInfoBeanList;

	public CreditorRightsTransfDoneAdapter(Context context, Handler handler) {
		this.mContext = context;
		this.mHandler = handler;
		inflater = LayoutInflater.from(mContext);
	}

	/**
	 * 设置数据集
	 * 
	 * @param creditorRightsInfoBeanList
	 */
	public void setData(ArrayList<CreditorRightsInfoBean> creditorRightsInfoBeanList) {
		mCreditorRightsInfoBeanList = creditorRightsInfoBeanList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return null == mCreditorRightsInfoBeanList ? 0 : mCreditorRightsInfoBeanList.size();
	}

	@Override
	public Object getItem(int position) {
		return mCreditorRightsInfoBeanList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		CreditorRightsInfoBean creditorRightsInfoBean = mCreditorRightsInfoBeanList.get(position);
		if (null == convertView) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_creditor_rights_transf_done_item, null);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		convertView.setOnClickListener(new OnClickEvent());
		return convertView;
	}

	private class ViewHolder {

	}

	/**
	 * 自定义点击事件监听器
	 * 
	 * @author hardy.zhou
	 *
	 */
	private class OnClickEvent implements OnClickListener {

		@Override
		public void onClick(View v) {
			Message msg = mHandler.obtainMessage();
			msg.what = 0;
			mHandler.sendMessage(msg);
		}

	}

}
