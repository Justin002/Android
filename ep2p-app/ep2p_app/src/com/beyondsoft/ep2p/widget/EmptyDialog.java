package com.beyondsoft.ep2p.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.beyondsoft.ep2p.R;

public class EmptyDialog extends Dialog{	
	private TextView app_dialog_title;
	private Context ctx;
	
	
	public EmptyDialog(Context context) {
		super(context);
		this.ctx = context;
	}
	
	public EmptyDialog(Context context, int theme) {
		super(context,theme);
		this.ctx = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		init();
	}
	
	private void init(){
		setContentView(R.layout.dialog_empty_layout);
		app_dialog_title=(TextView) findViewById(R.id.app_dialog_title);
		setCanceledOnTouchOutside(true);
		Window dialogWindow = this.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.CENTER);
		dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
		DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
		lp.width = (int) (0.8 * dm.widthPixels);
		dialogWindow.setAttributes(lp);
	}
	
	public void setTitle(String title){
		if(app_dialog_title!=null)
			app_dialog_title.setText(title);
	}
}
