package com.beyondsoft.ep2p.adapter;

import java.util.ArrayList;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.CardUnusedInfoBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author Ivan.Lu
 * @description 卡包不可以使用适配器
 */
public class CardPackageUnuseAdapter extends BaseAdapter{
	private LayoutInflater layoutInflater;
	private Context context;
	private ArrayList<CardUnusedInfoBean> mInviteList;
	public CardPackageUnuseAdapter(Context context){
		this.context=context;
		layoutInflater=LayoutInflater.from(context);
	}
	
	public void setUnuseData(ArrayList<CardUnusedInfoBean> mList){
		this.mInviteList = mList;
		this.notifyDataSetChanged();
	}
	
	
	@Override
	public int getCount() {
		return mInviteList==null?0:mInviteList.size();
	}

	@Override
	public Object getItem(int position) {
		return mInviteList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CardUnusedInfoBean cardUnusedInfoBean=mInviteList.get(position);
		ViewHolder viewHolder;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView=layoutInflater.inflate(R.layout.card_package_unuse_item,null);
			viewHolder.jiaxijuan_tv_unuse=(TextView) convertView.findViewById(R.id.jiaxijuan_tv_unuse);
			viewHolder.img_card_unuse=(TextView) convertView.findViewById(R.id.img_card);
			viewHolder.card_tv_detail=(TextView) convertView.findViewById(R.id.card_tv_detail);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.jiaxijuan_tv_unuse.setText(""+cardUnusedInfoBean.getCardQuota());
		viewHolder.card_tv_detail.setText(cardUnusedInfoBean.getCardDesc());
		viewHolder.img_card_unuse.setText(cardUnusedInfoBean.getCardType()==1?"加息券":"体验金");
		return convertView;
	}
	
	class ViewHolder{
		TextView jiaxijuan_tv_unuse;
		TextView img_card_unuse;
		TextView card_tv_detail;
	}
}
