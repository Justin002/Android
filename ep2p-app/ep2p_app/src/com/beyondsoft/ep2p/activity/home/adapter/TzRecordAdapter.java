package com.beyondsoft.ep2p.activity.home.adapter;

import java.util.List;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.response.BorrowDetailListResponse.NewStandardInformation;
import com.beyondsoft.ep2p.utils.StringUtils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TzRecordAdapter<T> extends BaseAdapter {

	private Context mContext;
	private Handler mHandler;
	private LayoutInflater inflater;
    private List<T> message;

	public TzRecordAdapter(Context context,  List<T> mList,Handler handler) {
		this.mContext = context;
		this.mHandler = handler;
		this.message = mList;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return message.size()>0?message.size():0;
	}

	@Override
	public Object getItem(int position) {
		return message.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("unchecked")
    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (null == convertView) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.layout_tz_record_item, null);
			viewHolder.tv_tzrecord_uname = (TextView) convertView.findViewById(R.id.tv_tzrecord_uname);
			viewHolder.tv_tzrecord_date = (TextView) convertView.findViewById(R.id.tv_tzrecord_date);
			viewHolder.tv_tzrecord_sl = (TextView) convertView.findViewById(R.id.tv_tzrecord_sl);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		NewStandardInformation mNewStandardInformation = (NewStandardInformation) getItem(position);
		viewHolder.tv_tzrecord_uname.setText(isTestNull(mNewStandardInformation.privateName));
		viewHolder.tv_tzrecord_date.setText(isTestNull(mNewStandardInformation.investmentTime));
		viewHolder.tv_tzrecord_sl.setText("￥"+StringUtils.formatMoney(mNewStandardInformation.investmentAmount));
		convertView.setOnClickListener(new OnClickEvent());
		return convertView;
	}

	private class ViewHolder {
	    TextView tv_tzrecord_uname,tv_tzrecord_date,tv_tzrecord_sl;
	}

	/**
	 * 自定义点击事件监听器
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
	
    private String isTestNull(String text)
    {
        return TextUtils.isEmpty(text) ? "" : text;
    }

}
