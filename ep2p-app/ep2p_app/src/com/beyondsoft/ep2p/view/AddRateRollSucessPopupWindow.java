package com.beyondsoft.ep2p.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.beyondsoft.ep2p.R;

public class AddRateRollSucessPopupWindow extends PopupWindow implements OnClickListener {

	private Context context;
	/*private ShareParams shareParams;
	private PlatformActionListener platformActionListener;*/
	public AddRateRollSucessPopupWindow(Context cx) {
		this.context = cx;
	}

	public void showWindow() {
		View view = LayoutInflater.from(context).inflate(
				R.layout.show_pop_window_rollsucess, null);
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
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
