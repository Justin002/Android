package com.beyondsoft.ep2p.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.widget.CustomDialog.ButtonOnClickListener;

public class PayConfirmDialog extends Dialog{
	private Button button1;
	private Button button2;
	private TextView app_dialog_title;
	private TextView app_dialog_descri;
	private EditText et_verification_code;
	private TextView tv_daojishi;
	private Context ctx;
	private ButtonOnClickListener listener;
	private int i;
	private Timer timer;
	private TimerTask timerTask;
	
	public interface ButtonOnClickListener{
		void onButton1Click(View v);
		void onButton2Click(View v);
	}
	
	public PayConfirmDialog(Context context) {
		super(context);
		this.ctx = context;
//		startTime();
	}
	
	public PayConfirmDialog(Context context, int theme) {
		super(context,theme);
		this.ctx = context;
//		startTime();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		init();
		
	}
	
	private void init(){
		setContentView(R.layout.dialog_pay_confirm_layout);
		app_dialog_title=(TextView) findViewById(R.id.app_dialog_title);
		app_dialog_descri=(TextView) findViewById(R.id.app_dialog_descri);
		et_verification_code = (EditText) findViewById(R.id.et_verification_code);
		tv_daojishi = (TextView) findViewById(R.id.tv_daojishi);
		button1 = (Button)findViewById(R.id.app_dialog_button1);
		button2 = (Button)findViewById(R.id.app_dialog_button2);
		button1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(listener != null){
					listener.onButton1Click(button1);
					dismiss();
				}
			}
		});
		
		button2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(listener != null){
					listener.onButton2Click(button2);
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
	
	public void setDescri(String descri){
		if(app_dialog_descri!=null)
			app_dialog_descri.setText(descri);
	}
	
	public void hideTitle(){
		if(app_dialog_title!=null){
			app_dialog_title.setVisibility(View.GONE);
		}
	}
	
	public void hideDescri(){
		if(app_dialog_descri!=null)
			app_dialog_descri.setVisibility(View.GONE);
	}
	
	public void setButtonText(String button1Text,String button2Text){
		if(button1!=null&&button2!=null){
			button1.setText(button1Text);
			button2.setText(button2Text);			
		}
	}
	
	public void setVerificationCode(String string){
		et_verification_code.setText(string);
	}
}
