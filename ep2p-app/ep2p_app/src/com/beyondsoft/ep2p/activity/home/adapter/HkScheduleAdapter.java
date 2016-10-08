package com.beyondsoft.ep2p.activity.home.adapter;

import java.util.List;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.core.MainHolder;
import com.beyondsoft.ep2p.model.response.HkScheduleResponse.HkScheduleItem;
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

public class HkScheduleAdapter<T> extends BaseAdapter {

	private Context mContext;
	private Handler mHandler;
	private LayoutInflater inflater;
	private List<T> mList ;
	
	public HkScheduleAdapter(Context context,List<T> List, Handler handler) {
		this.mContext = context;
		this.mHandler = handler;
		this.mList = List;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mList.size()>0?mList.size():0;
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
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
			convertView = inflater.inflate(R.layout.layout_hk_schedule_item, null);
			viewHolder.tv_hkrchedule_date = (TextView) convertView.findViewById(R.id.tv_hkrchedule_date);//期限
			viewHolder.tv_hkrchedule_lx = (TextView) convertView.findViewById(R.id.tv_hkrchedule_lx);//利息
			viewHolder.tv_hkrchedule_sl = (TextView) convertView.findViewById(R.id.tv_hkrchedule_sl);//金额
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		HkScheduleItem mhkItem  = (HkScheduleItem) getItem(position);
		
		viewHolder.tv_hkrchedule_date.setText(mhkItem.planindex+"/"+MainHolder.Instance(mContext).getMborDeadline());
		
		
        if (!TextUtils.isEmpty(mhkItem.capital + "")&&mhkItem.capital>0
            && !TextUtils.isEmpty(mhkItem.interest + "")&&mhkItem.interest>0)//若包含本金和利息则“本息表示”。
        {

            double total = mhkItem.capital + mhkItem.interest;
            viewHolder.tv_hkrchedule_sl.setText("￥" + StringUtils.formatMoney(total));
            viewHolder.tv_hkrchedule_lx.setText("本息");
        }
        else if (!TextUtils.isEmpty(mhkItem.capital + "")&&mhkItem.capital>0)//若仅包含本金则“本金”表示；
        {//本金为不为空
            viewHolder.tv_hkrchedule_sl.setText("￥"
                + StringUtils.formatMoney(mhkItem.capital));
            viewHolder.tv_hkrchedule_lx.setText("本金");
        }
        else if (!TextUtils.isEmpty(mhkItem.interest + "")&&mhkItem.interest>0)//若还款仅有利息则“利息”表示；
        {//利息不为为空
            viewHolder.tv_hkrchedule_sl.setText("￥"
                + StringUtils.formatMoney(mhkItem.interest));
            viewHolder.tv_hkrchedule_lx.setText("利息");
        }
        else  //本金和利息都没有
        {
            double total = mhkItem.capital + mhkItem.interest;
            viewHolder.tv_hkrchedule_sl.setText("￥" + StringUtils.formatMoney(total));
            viewHolder.tv_hkrchedule_lx.setText("利息");
        }
        
		convertView.setOnClickListener(new OnClickEvent());
		return convertView;
	}

	private class ViewHolder {
	    TextView tv_hkrchedule_date,tv_hkrchedule_lx,tv_hkrchedule_sl;
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
