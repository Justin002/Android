package com.beyondsoft.ep2p.activity.discover.adapter;

import java.util.List;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.R.color;
import com.beyondsoft.ep2p.activity.discover.InvestmentRankingActivity;
import com.beyondsoft.ep2p.model.response.MyRankingListResponse.MyRankingListItem;
import com.beyondsoft.ep2p.utils.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InestmentRankingListAdapter<T> extends BaseAdapter {

	private Context mContext;
	private Handler mHandler;
	private LayoutInflater inflater;
	List<T> list;
	protected ImageLoader imageLoader=ImageLoader.getInstance();

	public InestmentRankingListAdapter(Context context, List<T> list) {
		this.mContext = context;

		inflater = LayoutInflater.from(mContext);
		this.list = list;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		HolderView holder = null;
		if (null == v) {
			v = inflater.inflate(R.layout.item_invest_r, null);
			holder = new HolderView();
			holder.tv_rank_id = (TextView) v.findViewById(R.id.tv_rank_id);
			holder.iv_rank_pic = (ImageView) v.findViewById(R.id.iv_rank_pic);
			holder.tv_rank_name = (TextView) v.findViewById(R.id.tv_rank_name);
			holder.tv_rank_money = (TextView) v.findViewById(R.id.tv_rank_money);
			v.setTag(holder);
		} else {
			holder = (HolderView) v.getTag();
		}
		MyRankingListItem myRankingListItem = (MyRankingListItem) list.get(position);
		holder.tv_rank_id.setText(myRankingListItem.pid + "");
		
		if(holder.iv_rank_pic.getTag()==null||!myRankingListItem.getImageUrl().equals(holder.iv_rank_pic.getTag().toString())){
			imageLoader.displayImage(myRankingListItem.getImageUrl(), holder.iv_rank_pic,ImageLoadOptions.getCircleOptions());
			holder.iv_rank_pic.setTag(myRankingListItem.getImageUrl());
		}
		
		if (InvestmentRankingActivity.mypid == myRankingListItem.pid) {
			holder.tv_rank_name.setText("我");
			holder.tv_rank_name.setTextColor(mContext.getResources().getColor(R.color.orange));
		} else {
			// 用户名处理
			String name = "";
			if (TextUtils.isEmpty(myRankingListItem.customerName)) {
				holder.tv_rank_name.setText(myRankingListItem.customerName);
			} else {
				int length = myRankingListItem.customerName.length();
				if (length <= 2) {
					name = myRankingListItem.customerName;
				} else if (length > 2 && length <= 6) {
					name = myRankingListItem.customerName.substring(0, 2);
					for (int i = 1; i <= (length - 2); i++) {
						name += "*";
					}
				} else {
					name = myRankingListItem.customerName.substring(0, 2) + "****";
				}
			}
			holder.tv_rank_name.setText(name);

			holder.tv_rank_name.setTextColor(mContext.getResources().getColor(R.color.text_color));
		}
		holder.tv_rank_money.setText("￥" + myRankingListItem.investmentAmount);

		return v;
	}

	public class HolderView {
		private TextView tv_rank_id;
		private ImageView iv_rank_pic;
		private TextView tv_rank_name;
		private TextView tv_rank_money;

	}

	public void ClearListData() {
		if (list != null) {
			list.clear();
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

}
