package com.beyondsoft.ep2p.activity.mine.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.ProjectWaitInfoBean;
import com.beyondsoft.ep2p.model.WaitReceiveInfoBean;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 项目待收进行中适配器
 * 
 * @author hardy.zhou
 *
 */
public class WaittingRecieveDoingAdapter extends BaseAdapter {

	private Context mContext;
	private Handler mHandler;
	private LayoutInflater inflater;
	private ArrayList<WaitReceiveInfoBean> list;
	

	
	public WaittingRecieveDoingAdapter(Context mContext, ArrayList<WaitReceiveInfoBean> list) {
		super();
		this.mContext = mContext;
		this.list = list;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return list == null ? 0 : list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		WaitReceiveInfoBean bean = list.get(position);
		if (null == convertView) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_waitting_recieve_doing_item, null);
			viewHolder.tv_borrowCode = (TextView) convertView.findViewById(R.id.tv_borrowCode);
			viewHolder.tv_expireTime = (TextView) convertView.findViewById(R.id.tv_expireTime);
			viewHolder.tv_interestMoney = (TextView) convertView.findViewById(R.id.tv_interestMoney);
			viewHolder.tv_interestMoney_float = (TextView) convertView.findViewById(R.id.tv_interestMoney_float);
			viewHolder.tv_period_time = (TextView) convertView.findViewById(R.id.tv_period_time);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if(bean!=null)
		{
			viewHolder.tv_borrowCode.setText(bean.getBorrowCode());
			viewHolder.tv_expireTime.setText(bean.getExpireTime().split(" ")[0].trim());
			viewHolder.tv_period_time.setText(bean.getPlanIndex()+"/"+bean.getBorDeadline());
			DecimalFormat df = new DecimalFormat("########0.00");
			double money = bean.getInterestMoney();
			String money_str = df.format(money);
			String result[] = money_str.split("\\.");
			viewHolder.tv_interestMoney.setText(result[0]);
			viewHolder.tv_interestMoney_float.setText(" ."+result[1]+"（元）");
		}
		
		//convertView.setOnClickListener(new OnClickEvent());
		return convertView;
	}

	private class ViewHolder {
		TextView tv_borrowCode;
		TextView tv_expireTime;
		TextView tv_interestMoney;
		TextView tv_interestMoney_float;
		TextView tv_period_time;
		
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
