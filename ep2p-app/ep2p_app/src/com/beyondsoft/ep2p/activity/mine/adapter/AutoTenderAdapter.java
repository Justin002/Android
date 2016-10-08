package com.beyondsoft.ep2p.activity.mine.adapter;

import java.util.List;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.discover.adapter.BasicAdapter;
import com.beyondsoft.ep2p.model.AutoTenderInfoBean;
import com.beyondsoft.ep2p.service.BaseService;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 自动投标适配器
 * 
 * @author hanxiaohui
 *
 */

public class AutoTenderAdapter extends BasicAdapter {
	
	private String TAG = "AutoTenderAdapter";

	public Context context;

	public HolderView holder;

	private int input_count = 3;
	
	BaseService baseService;

	public AutoTenderAdapter(Context context, List<?> list) {
		super(context, list);
		this.context = context;
		baseService = new BaseService();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		holder = null;
		if (null == convertView) {
			convertView = mInflater.inflate(R.layout.item_auto_tender_item, null);
			holder = new HolderView();
			holder.mTitleLeft = (TextView) convertView.findViewById(R.id.tv_mine_auto_tender_item_title_left);
			holder.mTitle = (TextView) convertView.findViewById(R.id.tv_mine_auto_tender_item_title);
			holder.mRatio = (TextView) convertView.findViewById(R.id.tv_auto_tender_item_ratio);
			holder.mYears = (TextView) convertView.findViewById(R.id.tv_auto_tender_item_years);
			holder.mRate = (TextView) convertView.findViewById(R.id.tv_auto_tender_item_rate);
			holder.mProjects = (TextView) convertView.findViewById(R.id.tv_auto_tender_item_projects);
			holder.mEdit = (ImageView) convertView.findViewById(R.id.iv_mine_auto_tender_item_title_right);
			holder.mOnOff = (ImageView) convertView.findViewById(R.id.mine_auto_tender_item_on_off);

			convertView.setTag(holder);
		} else {
			holder = (HolderView) convertView.getTag();
		}

		final AutoTenderInfoBean mAutoTender = (AutoTenderInfoBean) list.get(position);
		if(!mAutoTender.getAmount().equals("0.00")){
			holder.mTitle.setText("固定金额");
			String money = "";
			if(!mAutoTender.getAmount().contains(".")){
				money = mAutoTender.getAmount() + ".00";
			}else if(mAutoTender.getAmount().indexOf(".") == mAutoTender.getAmount().length()-1){
				money = mAutoTender.getAmount() + "00";
			}else if(mAutoTender.getAmount().indexOf(".") == mAutoTender.getAmount().length()-2){
				money = mAutoTender.getAmount() + "0";
			}else{
				money = mAutoTender.getAmount();
			}
			holder.mRatio.setText("固定 "+money+"元");
		}else{
			holder.mTitle.setText("固定比例");
			holder.mRatio.setText("余额 "+mAutoTender.getBalanceratio()+"%");
		}
		
		if(mAutoTender.getMaxborrowmonth().equals("0")){
			holder.mYears.setText("不限");
		}else{
			holder.mYears.setText(mAutoTender.getMinborrowmonth()+"月~"+mAutoTender.getMaxborrowmonth()+"月");
		}
		
		if(mAutoTender.getMaxborrowrate().equals("0.0000")){
			holder.mRate.setText("不限");
		}else{
			String minborrowrate = mAutoTender.getMinborrowrate();
			String maxborrowrate = mAutoTender.getMaxborrowrate();
			
			holder.mRate.setText(minborrowrate.substring(0, minborrowrate.indexOf("."))+"%~"+maxborrowrate.substring(0, maxborrowrate.indexOf("."))+"%");
		}
		String borrowType = mAutoTender.getBorrowType();
		String str = "";
		if(borrowType.contains("1")){
			str += "e计划 ";
		}
		if(borrowType.contains("2")){
			str += "e首房 ";
		}
		if(borrowType.contains("3")){
			str += "e抵押 ";
		}
		holder.mProjects.setText(str);
		
		switch (mAutoTender.getAutostatus()) {
		case 0:
			holder.mOnOff.setImageResource(R.drawable.off_icon);
			holder.mOnOff.setTag(R.drawable.off_icon);
			holder.mTitleLeft.setVisibility(View.GONE);
			break;
		case 1:
			holder.mOnOff.setImageResource(R.drawable.on_icon);
			holder.mOnOff.setTag(R.drawable.on_icon);
			
			holder.mTitleLeft.setText("当前排名："+ mAutoTender.getRanking());
			holder.mTitleLeft.setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}	

		holder.mEdit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (Listener != null) {
					Listener.OnClick(v, position);
				}
			}

		});
		holder.mOnOff.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Listener != null) {
					
					Listener.OnClick(v, position);
					Log.d(TAG, "1");
					
				}
			}

		});

		return convertView;
	}

	public class HolderView {
		private TextView mTitleLeft;
		private TextView mTitle;
		private TextView mRatio;
		private TextView mYears;
		private TextView mRate;
		private TextView mProjects;
		private ImageView mEdit;
		public ImageView mOnOff;
	}

	// 申明接口对象
	private OnClickListener Listener;

	// 设置监听器 也就是实例化接口
	public void setOnClickListener(OnClickListener listener) {
		this.Listener = listener;
	}

	// 创建接口
	public static interface OnClickListener {
		public void OnClick(View v, int flag);
	}

}
