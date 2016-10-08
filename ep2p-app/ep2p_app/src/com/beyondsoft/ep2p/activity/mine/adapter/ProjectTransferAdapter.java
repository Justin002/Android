package com.beyondsoft.ep2p.activity.mine.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.ProjectTransferInfoBean;

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
 * 项目转让适配器
 * @author Jhuang
 * */
public class ProjectTransferAdapter extends BaseAdapter{

	private Context mContext;
	private Handler mHandler;
	private LayoutInflater inflater;
	private ArrayList<ProjectTransferInfoBean> list;
	
	public ProjectTransferAdapter(Context mContext, ArrayList<ProjectTransferInfoBean> list) {
		super();
		this.mContext = mContext;
		this.list = list;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		ProjectTransferInfoBean bean = list.get(position);
		if (null == convertView) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_project_transfer_item, null);
			convertView.setTag(viewHolder);
			viewHolder.tv_borrowCode = (TextView) convertView.findViewById(R.id.tv_borrowCode);
			viewHolder.tv_projectValue = (TextView) convertView.findViewById(R.id.tv_projectValue);
			viewHolder.tv_projectValue_float = (TextView) convertView.findViewById(R.id.tv_projectValue_float);
			viewHolder.tv_successAmount = (TextView) convertView.findViewById(R.id.tv_successAmount);
			viewHolder.tv_successAmount_float = (TextView) convertView.findViewById(R.id.tv_successAmount_float);
			viewHolder.tv_timeRemaining = (TextView) convertView.findViewById(R.id.tv_timeRemaining);
			viewHolder.tv_investmentTime = (TextView) convertView.findViewById(R.id.tv_investmentTime);
			viewHolder.tv_transfer_states = (TextView) convertView.findViewById(R.id.tv_transfer_states);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		//convertView.setOnClickListener(new OnClickEvent());
		viewHolder.tv_borrowCode.setText(bean.getTranderCode());
		if(bean.getBorrowCode().substring(0, 1).equals("X")||bean.getBorrowCode().substring(0, 1).equals("T")){
			viewHolder.tv_timeRemaining.setText(bean.getTimeRemaining()+"天");
		}else{
			viewHolder.tv_timeRemaining.setText(bean.getTimeRemaining()+"个月");
		}
		
		viewHolder.tv_investmentTime.setText(bean.getInvestmentTime().split(":")[0].replace("-", ".")+":"+bean.getInvestmentTime().split(":")[1]);
		DecimalFormat df = new DecimalFormat("########0.00");
		double money = bean.getProjectValue();
		String money_str = df.format(money);
		String result[] = money_str.split("\\.");
		viewHolder.tv_projectValue.setText(result[0]);
		viewHolder.tv_projectValue_float.setText(" ."+result[1]+"（元）");
		double amount = bean.getSuccessAmount();
		String amount_str = df.format(amount);
		String result1[] = money_str.split("\\.");
		viewHolder.tv_successAmount.setText(result1[0]);
		viewHolder.tv_successAmount_float.setText(" ."+result1[1]+"（元）");
		viewHolder.tv_transfer_states.setText(bean.getTrandStatus()==1 ? "转让中" : "已转让");
		return convertView;
	}
	
	private class ViewHolder {
		TextView tv_borrowCode;
		TextView tv_projectValue;
		TextView tv_projectValue_float;
		TextView tv_successAmount;
		TextView tv_successAmount_float;
		TextView tv_timeRemaining;
		TextView tv_investmentTime;
		TextView tv_transfer_states;
	}
	
	/**
	 * 自定义点击事件监听器
	 * 
	 * @author Jhuang
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
