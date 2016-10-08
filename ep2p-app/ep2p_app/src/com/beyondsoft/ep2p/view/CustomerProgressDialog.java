package com.beyondsoft.ep2p.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyondsoft.ep2p.R;

/**
 * ssy
 * 可作为全局数据加载等待显示
 */
public class CustomerProgressDialog extends Dialog {

	private static CustomerProgressDialog customerProgressDialog = null;
	private Animation rotateOpenAnimation;

	public CustomerProgressDialog(Context context) {
		super(context);
	}

	public CustomerProgressDialog(FragmentActivity fragment) {
		super(fragment);
	}

	public CustomerProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	public CustomerProgressDialog(FragmentActivity fragment, int theme) {
		super(fragment, theme);
	}

	public static CustomerProgressDialog createDialog(Context context) {
		customerProgressDialog = new CustomerProgressDialog(context, R.style.CustomerProgressDialog);
		customerProgressDialog.setContentView(R.layout.layout_load);
		customerProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

		return customerProgressDialog;
	}

	/**
	 * This hook is called whenever the window focus changes. 
	 * [这个钩子叫做每当窗口焦点的变化。]
	 */
	public void onWindowFocusChanged(boolean hasFocus) {

		if (customerProgressDialog == null) {
			return;
		}
		ImageView imageView = (ImageView) customerProgressDialog.findViewById(R.id.loadingImageView);
		imageView.setAnimation(setAnimation());
		imageView.startAnimation(rotateOpenAnimation);
	}

	private Animation setAnimation() {
		rotateOpenAnimation = new RotateAnimation(0.0f, +360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);// 设置旋转动画
		rotateOpenAnimation.setDuration(1000);// 持续1秒
		rotateOpenAnimation.setRepeatCount(Animation.INFINITE);// 无限次

		// 一直旋转，需要以下两行代码
		LinearInterpolator lir = new LinearInterpolator();
		rotateOpenAnimation.setInterpolator(lir);

		return rotateOpenAnimation;
	}

	public CustomerProgressDialog setTitile(String strTitle) {
		return customerProgressDialog;
	}

	public CustomerProgressDialog setMessage(CharSequence strMessage) {
		TextView tvMsg = (TextView) customerProgressDialog.findViewById(R.id.loadingMsgTextView);

		if (tvMsg != null && !TextUtils.isEmpty(strMessage)) {
			tvMsg.setVisibility(View.VISIBLE);
			tvMsg.setText(strMessage);
		}

		return customerProgressDialog;
	}
}
