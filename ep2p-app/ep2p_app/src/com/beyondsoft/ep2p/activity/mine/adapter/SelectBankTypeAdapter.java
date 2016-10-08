package com.beyondsoft.ep2p.activity.mine.adapter;

import com.beyondsoft.ep2p.R;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 选择银行分类适配器
 * 
 * @author hardy.zhou
 *
 */
public class SelectBankTypeAdapter extends BaseAdapter {

	private Context mContext;
	private Handler mHandler;
	private LayoutInflater inflater;

	public SelectBankTypeAdapter(Context context, Handler handler) {
		this.mContext = context;
		this.mHandler = handler;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return 5;
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
			convertView = inflater.inflate(R.layout.item_select_bank_type_item, null);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		convertView.setOnClickListener(new OnclickEvent());
		return convertView;
	}

	private class ViewHolder {

	}

	/**
	 * 自定义点击事件
	 * 
	 * @author hardy.zhou
	 *
	 */
	private class OnclickEvent implements OnClickListener {

		@Override
		public void onClick(View v) {
			Message msg = mHandler.obtainMessage();
			msg.what = 0;
			msg.obj = "中国银行";
			mHandler.sendMessage(msg);

		}
	}

}
