package com.beyondsoft.ep2p.activity.mine.activity;

import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.widget.CustomDialog;
import com.beyondsoft.ep2p.widget.CustomDialog.ButtonOnClickListener;

/**
 * @author Ivan.Lu
 * @description "我的"-关于
 */
public class AboutActivity extends BaseFragmentActivity implements OnClickListener
{
	private TextView version_tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		init();
		initListener();
	}
	
	private void init(){
		setTitle(getResources().getString(R.string.mine_setting_about));
		version_tv=(TextView) findViewById(R.id.version_tv);
		try {
			version_tv.setText("v"+getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initListener(){
		((TextView)findViewById(R.id.service_tel_tv)).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.service_tel_tv:
			CustomDialog clearDialog=new CustomDialog(this);
			clearDialog.setButtonClickListener(new ButtonOnClickListener() {
				
				@Override
				public void onButton2Click(View v) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onButton1Click(View v) {
					// TODO Auto-generated method stub
					 Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+"4009998992"));  
		             startActivity(intent); 
				}
			});
			clearDialog.show();
			clearDialog.setTitle(getResources().getString(R.string.mine_about_service_tel));
			clearDialog.hideDescri();
			clearDialog.setButtonText(getResources().getString(R.string.call), getResources().getString(R.string.cancel));
			break;
		}
	}
}
