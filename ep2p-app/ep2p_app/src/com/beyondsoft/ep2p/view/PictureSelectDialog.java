package com.beyondsoft.ep2p.view;

import com.beyondsoft.ep2p.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * 图片选择对话框
 * 
 * @author hardy.zhou
 *
 */
public class PictureSelectDialog extends Dialog {

	private TextView tv_title;
	private TextView tv_take_photo;
	private TextView tv_select_picture;
	private Context context;
	private SelectDialogOnClickListener listener;

	public interface SelectDialogOnClickListener {

		void onTakePhotoClick(View v);

		void onSelectPictureClick(View v);
	}

	public PictureSelectDialog(Context con) {
		super(con);
		this.context = con;
	}

	public PictureSelectDialog(Context con, int theme) {
		super(con, theme);
		this.context = con;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		setContentView(R.layout.dialog_picture_select);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_take_photo = (TextView) findViewById(R.id.tv_take_photo);
		tv_select_picture = (TextView) findViewById(R.id.tv_select_picture);
		tv_take_photo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (listener != null) {
					listener.onTakePhotoClick(tv_take_photo);
					dismiss();
				}
			}
		});

		tv_select_picture.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (listener != null) {
					listener.onSelectPictureClick(tv_select_picture);
					dismiss();
				}
			}
		});
		setCanceledOnTouchOutside(false);
		Window dialogWindow = this.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.CENTER);
		dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		lp.width = (int) (0.8 * dm.widthPixels);
		dialogWindow.setAttributes(lp);
	}

	public void setButtonClickListener(SelectDialogOnClickListener listener) {
		this.listener = listener;
	}

	public void setTitle(String title) {
		if (tv_title != null)
			tv_title.setText(title);
	}

	public void setButtonText(String button1Text, String button2Text) {
		if (tv_take_photo != null && tv_select_picture != null) {
			tv_take_photo.setText(button1Text);
			tv_select_picture.setText(button2Text);
		}
	}
}
