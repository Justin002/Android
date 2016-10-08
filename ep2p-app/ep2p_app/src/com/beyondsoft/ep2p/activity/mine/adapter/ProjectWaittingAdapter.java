package com.beyondsoft.ep2p.activity.mine.adapter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.ProjectWaitInfoBean;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 项目等待中适配器
 * 
 * @author hardy.zhou
 *
 */
public class ProjectWaittingAdapter extends BaseAdapter {

	private Context mContext;
	private Handler mHandler;
	private LayoutInflater inflater;
	private ArrayList<ProjectWaitInfoBean> list;

	public ProjectWaittingAdapter(Context context, Handler mHandler, 
			ArrayList<ProjectWaitInfoBean> list) {
		super();
		this.mContext = context;
		this.mHandler = mHandler;
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
		if (null == convertView) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_project_waitting_item, null);
			viewHolder.tv_borrowcode = (TextView) convertView.findViewById(R.id.tv_borrowcode);
			viewHolder.tv_investment_amount = (TextView) convertView.findViewById(R.id.tv_investment_amount);
			viewHolder.tv_bordeadline = (TextView) convertView.findViewById(R.id.tv_bordeadline);
			viewHolder.tv_forgotNper = (TextView) convertView.findViewById(R.id.tv_forgotNper);
			viewHolder.tv_investment_amount_float = (TextView) convertView.findViewById(R.id.tv_investment_amount_float);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		ProjectWaitInfoBean bean = list.get(position);
		if(bean!=null){
			viewHolder.tv_borrowcode.setText(bean.getBorrowCode());
			viewHolder.tv_forgotNper.setText(bean.getForgotNper()+"/"+bean.getBorDeadline());
			DecimalFormat df = new DecimalFormat("########0.00");
			BigDecimal money = bean.getInvestmentAmount();
			String money_str = df.format(money);
			String result[] = money_str.split("\\.");
			viewHolder.tv_investment_amount.setText(result[0]);
			viewHolder.tv_investment_amount_float.setText(" ."+result[1]+"（元）");
			if(bean.getBorrowCode().substring(0, 1).equals("X")||bean.getBorrowCode().substring(0, 1).equals("T")){
				viewHolder.tv_bordeadline.setText(bean.getBorDeadline()+"天");
			}else{
				viewHolder.tv_bordeadline.setText(bean.getBorDeadline()+"个月");
			}
			
		}
		
		//convertView.setOnClickListener(new OnClickEvent());
		return convertView;
	}

	private class ViewHolder {

		TextView tv_borrowcode;
		TextView tv_investment_amount;
		TextView tv_bordeadline;
		TextView tv_forgotNper;
		TextView tv_investment_amount_float;
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
