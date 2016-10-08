package com.beyondsoft.ep2p.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.beyondsoft.ep2p.R;

@SuppressLint("InflateParams")
public class HeadSelectPopupWindow extends PopupWindow  {

	private Context context;
	private TextView tv_photos,tv_pick_photo,tv_cancel;
	private HeadSelectPopupWindowOnClickListener listener;
	public HeadSelectPopupWindow(Context cx) {
		this.context = cx;
	}
	public interface HeadSelectPopupWindowOnClickListener {

		void onButtonClick1(View v);

		void onButtonClick2(View v);
	}

	public void showWindow() {
		View view = LayoutInflater.from(context).inflate(
				R.layout.popwindow_headportrait, null);
		tv_photos=(TextView) view.findViewById(R.id.tv_photos);
		tv_pick_photo=(TextView) view.findViewById(R.id.tv_pick_photo);
		tv_cancel=(TextView) view.findViewById(R.id.tv_cancel);
		tv_photos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (listener!=null) {
					listener.onButtonClick1(tv_photos);
					dismiss();
				}

			}
		});
		tv_pick_photo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (listener!=null) {
					listener.onButtonClick2(tv_pick_photo);
					dismiss();
				}
			}
		});
		tv_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (listener!=null) {
					dismiss();
				}
			}
		});
		// 设置SelectPicPopupWindow的View
		this.setContentView(view);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.AnimBottom);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
	}
	public void setButtonClickListener(HeadSelectPopupWindowOnClickListener listener) {
		this.listener = listener;
	}
	public void setButtonText(String button1Text, String button2Text) {
		if (tv_photos != null && tv_pick_photo != null) {
			tv_photos.setText(button1Text);
			tv_pick_photo.setText(button2Text);
		}
	}
}
