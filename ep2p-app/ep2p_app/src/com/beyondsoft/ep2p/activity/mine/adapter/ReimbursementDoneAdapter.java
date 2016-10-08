package com.beyondsoft.ep2p.activity.mine.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.ReimbursementDoneInfoBean;

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
public class ReimbursementDoneAdapter extends BaseAdapter {

	private Context mContext;
	private Handler mHandler;
	private LayoutInflater inflater;
    private ArrayList<ReimbursementDoneInfoBean> reimbursementDoneList;
    
	public ReimbursementDoneAdapter(Context context, Handler handler) {
		this.mContext = context;
		this.mHandler = handler;
		inflater = LayoutInflater.from(mContext);
	}
	
	public void setData(ArrayList<ReimbursementDoneInfoBean> mList){
		reimbursementDoneList = mList;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return reimbursementDoneList==null?0:reimbursementDoneList.size();
	}

	@Override
	public Object getItem(int position) {
		return reimbursementDoneList==null?null:reimbursementDoneList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ReimbursementDoneInfoBean bean = reimbursementDoneList.get(position);
		ViewHolder viewHolder = null;
		if (null == convertView) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_reimbursement_done_item, null);
			viewHolder.tv_borrowCode = (TextView) convertView.findViewById(R.id.tv_borrowCode);
			viewHolder.tv_borrowMoney = (TextView) convertView.findViewById(R.id.tv_borrowMoney);
			viewHolder.tv_borrowMoney_float = (TextView) convertView.findViewById(R.id.tv_borrowMoney_float);
			viewHolder.tv_borrow_deadline = (TextView) convertView.findViewById(R.id.tv_borrow_deadline);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if(bean!=null){
			viewHolder.tv_borrowCode.setText(bean.getBorrowCode());
			String money_str = bean.getBorrowMoney();
			double money_d = Double.parseDouble(money_str);
			DecimalFormat df = new DecimalFormat("########0.00");
			String money = df.format(money_d);
			String result[] = money.split("\\.");
			viewHolder.tv_borrowMoney.setText(result[0]);
			viewHolder.tv_borrowMoney_float.setText(" ."+result[1]+"（元）");
			if(bean.getBorrowCode().substring(0, 1).equals("X")||bean.getBorrowCode().substring(0, 1).equals("X")){
				viewHolder.tv_borrow_deadline.setText(bean.getBorDeadline()+"天");
			}else{
				viewHolder.tv_borrow_deadline.setText(bean.getBorDeadline()+"个月");
			}
			
			//convertView.setOnClickListener(new OnClickEvent());
		}
		
		return convertView;
	}

	private class ViewHolder {

		TextView tv_borrowCode;
		TextView tv_borrowMoney;
		TextView tv_borrowMoney_float;
		TextView tv_borrow_deadline;
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
