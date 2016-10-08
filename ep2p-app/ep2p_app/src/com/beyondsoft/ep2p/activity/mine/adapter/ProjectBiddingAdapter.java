package com.beyondsoft.ep2p.activity.mine.adapter;

import java.text.NumberFormat;
import java.util.ArrayList;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.ProjectBiddingInfoBean;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.view.RoundProgressBar;

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
 * 项目竞拍中适配器
 * 
 * @author hardy.zhou
 *
 */
public class ProjectBiddingAdapter extends BaseAdapter {

	private Context mContext;
	private Handler mHandler;
	private LayoutInflater inflater;
	private ArrayList<ProjectBiddingInfoBean> list;

	
	
	public ProjectBiddingAdapter(Context mContext, Handler mHandler, ArrayList<ProjectBiddingInfoBean> list) {
		super();
		this.mContext = mContext;
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
		return list == null ? null : list.get(position);
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
			convertView = inflater.inflate(R.layout.item_project_bidding_item, null);
			convertView.setTag(viewHolder);
			viewHolder.roundProgressBar = (RoundProgressBar) convertView.findViewById(R.id.rpv_rate);
			viewHolder.tv_borrowCode = (TextView) convertView.findViewById(R.id.tv_borrowCode);
			viewHolder.tv_borrowRate = (TextView) convertView.findViewById(R.id.tv_borrowRate);
			viewHolder.tv_borDeadline = (TextView) convertView.findViewById(R.id.tv_borDeadline);
			viewHolder.tv_borrowMoney = (TextView) convertView.findViewById(R.id.tv_borrowMoney);
			viewHolder.tv_allinvsetMoney = (TextView) convertView.findViewById(R.id.tv_allinvsetMoney);
			
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		ProjectBiddingInfoBean bean = list.get(position);
		NumberFormat ft = NumberFormat.getPercentInstance();
		ft.setMaximumFractionDigits(2);
		if(bean!=null){
			
			viewHolder.tv_borrowCode.setText(bean.getBorrowCode());
			if(bean.getBorrowCode().substring(0, 1).equals("X")||bean.getBorrowCode().substring(0, 1).equals("T"))
			{
				viewHolder.tv_borDeadline.setText(bean.getBorDeadline()+"天");
			}
			else
			{
				viewHolder.tv_borDeadline.setText(bean.getBorDeadline()+"个月");
			}			
			
			viewHolder.tv_allinvsetMoney.setText("已投金额："+bean.getAllinvsetMoney()+"元");
			viewHolder.tv_borrowRate.setText(ft.format(bean.getBorrowRate()));
			//viewHolder.tv_borrowMoney.setText(""+ft.format((bean.getBorrowMoney()/10000f))+"万");
			if((int)(bean.getBorrowMoney()%10000)==0)
			{
				viewHolder.tv_borrowMoney.setText(""+(int)(bean.getBorrowMoney()/10000)+"万");
			}else
			{
			    if((int)(bean.getBorrowMoney()%1000)==0)
			    {
			    	viewHolder.tv_borrowMoney.setText(""+(bean.getBorrowMoney()/10000d)+"万");
			    }else
			    {
			    	viewHolder.tv_borrowMoney.setText(""+StringUtils.formatMoney((bean.getBorrowMoney()/10000f))+"万");
				}
			    }
				
			viewHolder.roundProgressBar.setStartAnger(270);
			
			int i =  (int) (bean.getAllinvsetMoney()*100/bean.getBorrowMoney());
			if(bean.getAllinvsetMoney()==0){
				viewHolder.roundProgressBar.setProgress(0);	
			}else{
				viewHolder.roundProgressBar.setProgress(i);
			}
			
			
		}
		
		
		
		//convertView.setOnClickListener(new OnClickEvent());
		return convertView;
	}

	private class ViewHolder {

		private RoundProgressBar roundProgressBar;
		private TextView tv_borrowCode;
		private TextView tv_borrowRate;
		private TextView tv_borDeadline;
		private TextView tv_borrowMoney;
		private TextView tv_allinvsetMoney;

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
