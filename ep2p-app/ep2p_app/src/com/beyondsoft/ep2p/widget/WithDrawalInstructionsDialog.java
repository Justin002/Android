package com.beyondsoft.ep2p.widget;

import java.text.NumberFormat;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.UserPersonalInfo;

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
import android.widget.TextView;

/**
 * 提现说明对话框
 * 
 * @author hardy.zhou
 *
 */
public class WithDrawalInstructionsDialog extends Dialog {

	private Context ctx;
    private TextView tv_withdrawal_instructions_1;
    
	public WithDrawalInstructionsDialog(Context context) {
		super(context);
		this.ctx = context;
	}

	public WithDrawalInstructionsDialog(Context context, int theme) {
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
		setContentView(R.layout.dialog_withdrawal_instructions);
		tv_withdrawal_instructions_1 = (TextView) findViewById(R.id.tv_withdrawal_instructions_1);
		NumberFormat ft = NumberFormat.getPercentInstance();
		tv_withdrawal_instructions_1.setText("1、未投标的充值资金提现需收取"+Double.parseDouble(UserPersonalInfo.getWithdrawalFee())*100+"%的手续费；");
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
