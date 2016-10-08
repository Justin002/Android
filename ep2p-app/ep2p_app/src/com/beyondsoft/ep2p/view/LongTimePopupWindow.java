package com.beyondsoft.ep2p.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.utils.CommonUtils;

public class LongTimePopupWindow extends PopupWindow implements
		OnClickListener {

	private Context context;
	TextView iknow_tv,tv_dianzhan_attention,show_time_tv;
	private int listenTime=0;
	private int score=0;

	/*
	 * private ShareParams shareParams; private PlatformActionListener
	 * platformActionListener;
	 */
	public LongTimePopupWindow(Context cx,int listenTime,int score) {
		this.context = cx;
		this.listenTime=listenTime;
		this.score=score;
	}

	public void showWindow() {
		View view = LayoutInflater.from(context).inflate(R.layout.show_pop_window_long_time, null);
		// 设置SelectPicPopupWindow的View
		this.setContentView(view);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击窗口外边窗口消失
		this.setOutsideTouchable(true);

		// 设置此参数获得焦点，否则无法点击
		this.setFocusable(true);
		iknow_tv = (TextView) view.findViewById(R.id.iknow_tv);
		show_time_tv=(TextView) view.findViewById(R.id.show_time_tv);
		show_time_tv.setText(context.getResources().getString(R.string.long_time_hint,listenTime,score));
		tv_dianzhan_attention=(TextView) view.findViewById(R.id.tv_dianzhan_attention);
		iknow_tv.setOnClickListener(this);
		tv_dianzhan_attention.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.iknow_tv) {
			LongTimePopupWindow.this.dismiss();
		}
		else if(v.getId() == R.id.tv_dianzhan_attention){
			LongTimePopupWindow.this.dismiss();
			CommonUtils.addConfigInfo(context, Constants.PLAYDIALOG, true);
		}

	}
	
	public void updateData(int listenTime,int score) {
		this.listenTime=listenTime;
		this.score=score;
		show_time_tv.setText(context.getResources().getString(R.string.long_time_hint,listenTime,score));
	}

}
