package com.beyondsoft.ep2p.widget;

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
import android.widget.ImageView;

/**
 * 债权转让说明对话框
 * 
 * @author Jason.Hwang
 *
 */
public class CreditRightTranferInstructionsDialog extends Dialog {

	private Context ctx;

	public CreditRightTranferInstructionsDialog(Context context) {
		super(context);
		this.ctx = context;
	}

	public CreditRightTranferInstructionsDialog(Context context, int theme) {
		super(context, theme);
		this.ctx = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		initView();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		setContentView(R.layout.dialog_creditor_tansfer_instructions);
		setCanceledOnTouchOutside(true);
		Window dialogWindow = this.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.CENTER);
		dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
		DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
		lp.width = (int) dm.widthPixels;
		dialogWindow.setAttributes(lp);
		ImageView iv_close = (ImageView) findViewById(R.id.iv_close);
		iv_close.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}
}
