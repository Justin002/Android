package com.beyondsoft.ep2p.activity.mine.adapter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.ReimbursementWaitInfoBean;

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
 * 待还款适配器
 * 
 * @author hardy.zhou
 *
 */
public class ReimbursementWaitAdapter extends BaseAdapter {

	private Context mContext;
	private Handler mHandler;
	private LayoutInflater inflater;
    private ArrayList<ReimbursementWaitInfoBean> reimbursementWaitList;
	public ReimbursementWaitAdapter(Context context, Handler handler) {
		this.mContext = context;
		this.mHandler = handler;
		inflater = LayoutInflater.from(mContext);
	}

	public void setData(ArrayList<ReimbursementWaitInfoBean> mList){
		reimbursementWaitList = mList;
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return reimbursementWaitList==null?0:reimbursementWaitList.size();
	}

	@Override
	public Object getItem(int position) {
		return reimbursementWaitList==null?null:reimbursementWaitList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ReimbursementWaitInfoBean bean = reimbursementWaitList.get(position);
		ViewHolder viewHolder = null;
		if (null == convertView) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_reimbursement_wait_item, null);
			convertView.setTag(viewHolder);
			//viewHolder.bt_reimbursement_now = (Button) convertView.findViewById(R.id.bt_reimbursement_now);
			//viewHolder.bt_reimbursement_now.setOnClickListener(new OnClickEvent(OnClickEvent.FLAG_REIMBURSEMENT));
		
			viewHolder.tv_borrowcode = (TextView) convertView.findViewById(R.id.tv_borrowcode);
			viewHolder.tv_expireTime = (TextView) convertView.findViewById(R.id.tv_expireTime);
			viewHolder.tv_bortotalamount = (TextView) convertView.findViewById(R.id.tv_bortotalamount);
			viewHolder.tv_qici = (TextView) convertView.findViewById(R.id.tv_qici);
			viewHolder.tv_bortotalamount_float = (TextView) convertView.findViewById(R.id.tv_bortotalamount_float);
		
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if(bean!=null){
			viewHolder.tv_borrowcode.setText(bean.getBorrowCode());
			String expireTimes[] = bean.getExpireTime().split(" ");
			viewHolder.tv_expireTime.setText(expireTimes[0].trim());
			viewHolder.tv_qici.setText(bean.getPlanindex()+"/"+bean.getBorDeadline());
			double money_d = bean.getBorTotalAmount();
			DecimalFormat df = new DecimalFormat("########0.00");
			String money = df.format(money_d);
			String result[] = money.split("\\.");
			viewHolder.tv_bortotalamount.setText(result[0]);
			viewHolder.tv_bortotalamount_float.setText(" ."+result[1]+"（元）");
			//convertView.setOnClickListener(new OnClickEvent(OnClickEvent.FLAG_DETAIL));
		}
		
		return convertView;
	}

	private class ViewHolder {

		//private Button bt_reimbursement_now;

		TextView tv_borrowcode;
		TextView tv_expireTime;
		TextView tv_bortotalamount;
		TextView tv_bortotalamount_float;
		TextView tv_qici;
	}

	/**
	 * 自定义点击事件监听器
	 * 
	 * @author hardy.zhou
	 *
	 */
	private class OnClickEvent implements OnClickListener {

		public static final int FLAG_DETAIL = 0;
		public static final int FLAG_REIMBURSEMENT = 2;
		private int flag = FLAG_DETAIL;

		public OnClickEvent(int flag) {
			this.flag = flag;
		}

		@Override
		public void onClick(View v) {

			switch (flag) {
			case FLAG_DETAIL: {
				Message msg = mHandler.obtainMessage();
				msg.what = 0;
				mHandler.sendMessage(msg);
			}
				break;
			case FLAG_REIMBURSEMENT: {
				Message msg = mHandler.obtainMessage();
				msg.what = 1;
				mHandler.sendMessage(msg);
			}
				break;
			default:
				break;
			}
		}

	}

}
