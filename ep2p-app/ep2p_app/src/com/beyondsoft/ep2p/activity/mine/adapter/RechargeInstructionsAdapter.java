package com.beyondsoft.ep2p.activity.mine.adapter;

import java.util.List;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.discover.adapter.BasicAdapter;
import com.beyondsoft.ep2p.activity.mine.activity.RechargeInstructionsActivity.RechargeInstructions;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 充值-说明 适配器
 * 
 * @author hanxiaohui
 *
 */

public class RechargeInstructionsAdapter extends BasicAdapter {

	public RechargeInstructionsAdapter(Context context, List<?> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		HolderView holder = null;
		if (null == v) {
			v = mInflater.inflate(R.layout.item_recharge_instructions_item, null);
			holder = new HolderView();
			holder.mBankImage = (ImageView) v.findViewById(R.id.iv_recharge_instructions);
			holder.mBankName = (TextView) v.findViewById(R.id.tv_recharge_instructions_bankname);
			holder.mSingle = (TextView) v.findViewById(R.id.tv_recharge_instructions_single);
			holder.mOneDay = (TextView) v.findViewById(R.id.tv_recharge_instructions_oneday);
			
			v.setTag(holder);
		} else {
			holder = (HolderView) v.getTag();
		}
		RechargeInstructions mFeedbackList = (RechargeInstructions)list.get(position);
		holder.mBankImage.setImageResource(mFeedbackList.imageid);
		holder.mBankName.setText(mFeedbackList.bankname);
		holder.mSingle.setText(mFeedbackList.single);
		holder.mOneDay.setText(mFeedbackList.oneday);
        
		return v;
	}

	public class HolderView {
		private ImageView mBankImage;
		private TextView mBankName;
		private TextView mSingle;
		private TextView mOneDay;
	}

}