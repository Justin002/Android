package com.beyondsoft.ep2p.widget;

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
import android.widget.Button;
import android.widget.TextView;

import com.beyondsoft.ep2p.R;

public class RechargeMoneyDialog extends Dialog{
	private Button button1;
	private TextView app_dialog_descri_01;
	private TextView app_dialog_descri_02;
	private TextView app_dialog_title;
	private Context ctx;
	private ButtonOnClickListener listener;
	
	public interface ButtonOnClickListener{
		void onButton1Click(View v);
		void onButton2Click(View v);
	}
	
	public RechargeMoneyDialog(Context context) {
		super(context);
		this.ctx = context;
	}
	
	public RechargeMoneyDialog(Context context, int theme) {
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
		setContentView(R.layout.dialog_onebutton_layout2);
		app_dialog_title=(TextView) findViewById(R.id.app_dialog_title);
		app_dialog_descri_01=(TextView) findViewById(R.id.app_dialog_descri_01);
		app_dialog_descri_02=(TextView) findViewById(R.id.app_dialog_descri_02);
		button1 = (Button)findViewById(R.id.app_dialog_button1);
		button1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(listener != null){
					listener.onButton1Click(button1);
					dismiss();
				}
			}
		});
		
		setCanceledOnTouchOutside(false);
		Window dialogWindow = this.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.CENTER);
		dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
		DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
		lp.width = (int) (0.8 * dm.widthPixels);
		dialogWindow.setAttributes(lp);
	}
	
	public void setButtonClickListener(ButtonOnClickListener listener){
		this.listener=listener;
	}
	
	public void setTitle(String title){
		if(app_dialog_title!=null)
			app_dialog_title.setText(title);
	}
	
	public void setDescri(String descri01,String descri02){
		if(app_dialog_descri_01!=null)
			app_dialog_descri_01.setText(descri01);
		if(app_dialog_descri_02!=null){
			app_dialog_descri_02.setText(descri02);
			}else{
				app_dialog_descri_02.setVisibility(View.GONE);
			}
		
	}
	
	public void hideTitle(){
		if(app_dialog_title!=null){
			app_dialog_title.setVisibility(View.GONE);
		}
	}
	
	public void hideDescri(){
		if(app_dialog_descri_01!=null)
			app_dialog_descri_01.setVisibility(View.GONE);
		if(app_dialog_descri_02!=null)
			app_dialog_descri_02.setVisibility(View.GONE);
	}
	
	public void setButtonText(String button1Text){
		if(button1!=null){
			button1.setText(button1Text);
		}
	}
}
