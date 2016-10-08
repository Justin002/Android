package com.beyondsoft.ep2p.activity.mine.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.ProjectFinishInfoBean;

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
 * 项目已结束适配器
 * 
 * @author hardy.zhou
 *
 */
public class ProjectFinishAdapter extends BaseAdapter {

	private Context mContext;
	private Handler mHandler;
	private LayoutInflater inflater;
    private ArrayList<ProjectFinishInfoBean> list;
	
    
    
	public ProjectFinishAdapter(Context mContext, ArrayList<ProjectFinishInfoBean> list) {
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
		ProjectFinishInfoBean bean = list.get(position);
		if (null == convertView) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_project_finish_item, null);
			viewHolder.tv_borrowCode = (TextView) convertView.findViewById(R.id.tv_borrowCode);
			viewHolder.tv_investmentAmount = (TextView) convertView.findViewById(R.id.tv_investmentAmount);
			viewHolder.tv_investmentAmount_float = (TextView) convertView.findViewById(R.id.tv_investmentAmount_float);
			viewHolder.tv_borDeadline = (TextView) convertView.findViewById(R.id.tv_borDeadline);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_borrowCode.setText(bean.getBorrowCode());
		DecimalFormat df = new DecimalFormat("########0.00");
		double money = bean.getInvestmentAmount();
		String money_str = df.format(money);
		String result[] = money_str.split("\\.");
		viewHolder.tv_investmentAmount.setText(result[0]);
		viewHolder.tv_investmentAmount_float.setText(" ."+result[1]+"元");
		if(bean.getBorrowCode().substring(0, 1).equals("X")||bean.getBorrowCode().substring(0, 1).equals("T")){
			viewHolder.tv_borDeadline.setText(bean.getBorDeadline()+"天");
		}else{
			viewHolder.tv_borDeadline.setText(bean.getBorDeadline()+"个月");
		}
		
		return convertView;
	}

	private class ViewHolder {

		TextView tv_borrowCode;
		TextView tv_investmentAmount_float;
		TextView tv_investmentAmount;
		TextView tv_borDeadline;
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
