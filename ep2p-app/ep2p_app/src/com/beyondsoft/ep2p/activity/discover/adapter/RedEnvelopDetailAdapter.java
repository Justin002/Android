package com.beyondsoft.ep2p.activity.discover.adapter;

import java.util.List;

import com.beyondsoft.ep2p.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.model.response.SelectReceiveInterestDetailResponse.Result.InterestDetailListItem;
import com.beyondsoft.ep2p.model.response.SelectReceiveRedDetailResponse.Result.RedDetailListItem;
import com.beyondsoft.ep2p.utils.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 红包详情
 * 
 * @author hanxiaohui
 *
 */
public class RedEnvelopDetailAdapter extends BaseAdapter {

	private Context mContext;
//	private Handler mHandler;
	private LayoutInflater inflater;
	private int type;
	private List<?> list;
	protected ImageLoader imageLoader=ImageLoader.getInstance();
//	public RedEnvelopDetailAdapter(Context context, Handler handler,int type) {
//		this.mContext = context;
//		this.mHandler = handler;
//		this.type = type;
//		inflater = LayoutInflater.from(mContext);
//	}
	public RedEnvelopDetailAdapter(Context context, List<?> list,int type) {
		this.mContext = context;
		inflater = LayoutInflater.from(mContext);
		this.list = list;
		this.type = type;
	}

	@Override
	public int getCount() {
		return list.size();
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
			convertView = inflater.inflate(R.layout.item_redenvelope_detail, null);
			viewHolder.pinglun_img = (ImageView) convertView.findViewById(R.id.pinglun_img);
			viewHolder.e_number = (TextView) convertView.findViewById(R.id.e_number);
			viewHolder.pinglun_name = (TextView) convertView.findViewById(R.id.pinglun_name);
			viewHolder.pinglun_time = (TextView) convertView.findViewById(R.id.pinglun_time);
			
			convertView.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if(type==2){
			InterestDetailListItem interestDetailListItem = (InterestDetailListItem) list.get(position);
			if(viewHolder.pinglun_img.getTag()==null||!interestDetailListItem.getImageUrl().equals(viewHolder.pinglun_img.getTag().toString())){
				imageLoader.displayImage(interestDetailListItem.getImageUrl(), viewHolder.pinglun_img,ImageLoadOptions.getCircleOptions());
				viewHolder.pinglun_img.setTag(interestDetailListItem.getImageUrl());
			}
			double investAwardValue = interestDetailListItem.getInvestAwardValue()*100;
			viewHolder.e_number.setText("加息"+(int)investAwardValue+"%");
			viewHolder.pinglun_name.setText(interestDetailListItem.getPhoneNo().substring(0, interestDetailListItem.getPhoneNo().length()-4)+"****");
			viewHolder.pinglun_time.setText(interestDetailListItem.getGetTime());
			
		}else if(type==1){
			RedDetailListItem redDetailListItem = (RedDetailListItem)list.get(position);
			
			if(viewHolder.pinglun_img.getTag()==null||!redDetailListItem.getImageUrl().equals(viewHolder.pinglun_img.getTag().toString())){
				imageLoader.displayImage(redDetailListItem.getImageUrl(), viewHolder.pinglun_img,ImageLoadOptions.getCircleOptions());
				viewHolder.pinglun_img.setTag(redDetailListItem.getImageUrl());
			}
			
			viewHolder.e_number.setText("￥"+redDetailListItem.getGetAmount());
			viewHolder.pinglun_name.setText(redDetailListItem.getPhoneNo().substring(0, redDetailListItem.getPhoneNo().length()-4)+"****");
			viewHolder.pinglun_time.setText(redDetailListItem.getGetTime());
		}
		
		
		
		
		return convertView;
	}

	private class ViewHolder {

		private ImageView pinglun_img;
		private TextView e_number;
		private TextView pinglun_name;
		private TextView pinglun_time;
	}

}
