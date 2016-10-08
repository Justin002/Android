package com.beyondsoft.ep2p.adapter;

import java.text.NumberFormat;
import java.util.ArrayList;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.CardUnusedInfoBean;
import com.beyondsoft.ep2p.model.CardUsedInfoBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author Ivan.Lu
 * @description 卡包可以用适配器
 */
public class CardPackageUseAdapter extends BaseAdapter{
	private LayoutInflater layoutInflater;
	private Context mContext;
	private ArrayList<CardUsedInfoBean> cardList;
	
	public CardPackageUseAdapter(Context context){
		this.mContext=context;
		layoutInflater=LayoutInflater.from(mContext);
	}
	
	public void setData(ArrayList<CardUsedInfoBean> list){
		cardList = list;
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cardList==null?0:cardList.size();
		
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return cardList==null?null:cardList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		CardUsedInfoBean cardInfo = cardList.get(position);
		ViewHolder viewHolder;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView=layoutInflater.inflate(R.layout.card_package_use_item,null);
			viewHolder.jiaxijuan_tv = (TextView) convertView.findViewById(R.id.jiaxijuan_tv);
			viewHolder.card_tv_detail = (TextView) convertView.findViewById(R.id.card_tv_detail);
			viewHolder.date_tv = (TextView)convertView.findViewById(R.id.date_tv);
			viewHolder.img_card = (TextView)convertView.findViewById(R.id.img_card);
			viewHolder.type_biaoji = (TextView) convertView.findViewById(R.id.type_biaoji);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		NumberFormat ft= NumberFormat.getPercentInstance();
		ft.setMaximumFractionDigits(2);
		
		viewHolder.card_tv_detail.setText(cardInfo.getCardDesc());
		viewHolder.date_tv.setText("还有"+(int)cardInfo.getCardValidity()+"天到期");
		int type=cardInfo.getCardType() ;
		if(type==1){
			viewHolder.jiaxijuan_tv.setText(""+ft.format(cardInfo.getCardQuota()));
			viewHolder.img_card.setText("加息券");
			viewHolder.type_biaoji.setText("+");
		}else if(type==2){
			viewHolder.img_card.setText("体验金");
			viewHolder.jiaxijuan_tv.setText(""+cardInfo.getCardQuota());
		}
		return convertView;
	}
	
	class ViewHolder{
		TextView jiaxijuan_tv;
		TextView card_tv_detail;
		TextView date_tv;
		TextView img_card;
		TextView type_biaoji;
	}
}
