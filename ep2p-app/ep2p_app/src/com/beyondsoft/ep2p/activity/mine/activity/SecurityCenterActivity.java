package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.home.AuthenticationActivity;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.view.HeadSelectPopupWindow;
import com.beyondsoft.ep2p.view.HeadSelectPopupWindow.HeadSelectPopupWindowOnClickListener;
import com.beyondsoft.ep2p.view.SeekArc;
import com.beyondsoft.ep2p.view.SeekArc.OnSeekArcChangeListener;
import com.beyondsoft.ep2p.view.gesture.LockUtil;

/**
 * @author Ivan.Lu
 * @description "我的"-安全中心
 */
public class SecurityCenterActivity extends BaseFragmentActivity implements OnClickListener{

	private SeekArc mSeekArc;
	private TextView mSeekArcProgress;
	private TextView mSeekArcProgress_txt;
	private TextView security_level_hint;
	private TextView pay_pwd_tv;
	private TextView phone_bound_tv;
	private TextView real_name_tv;
	private CheckBox switch_gesture_cb;
	private int isCertification = 10;//[未认证]
	private Timer mTimer;
	private int mProgress;
	private int mCompleteProgress=10;
	private int delay=300;
	
	private RelativeLayout real_name_layout ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_security_center);
		init();
		initListener();
		//initData();
	}
	
	private void init(){
		setTitle(getResources().getString(R.string.mine_security_center));
		pay_pwd_tv=(TextView) findViewById(R.id.pay_pwd_tv);
		phone_bound_tv=(TextView) findViewById(R.id.phone_bound_tv);
		real_name_tv=(TextView) findViewById(R.id.real_name_tv);
		mSeekArcProgress=(TextView) findViewById(R.id.seekArcProgress);
		mSeekArcProgress_txt=(TextView) findViewById(R.id.seekArcProgress_txt);
		security_level_hint=(TextView) findViewById(R.id.security_level_hint);
		switch_gesture_cb=(CheckBox) findViewById(R.id.switch_gesture_cb);
		mSeekArc=(SeekArc) findViewById(R.id.seekArc);
		switch_gesture_cb.setChecked(LockUtil.getPwdStatus(this));
	}
	
	private void initListener(){
	    real_name_layout = (RelativeLayout)findViewById(R.id.real_name_layout);
	    real_name_layout.setOnClickListener(this);
		((RelativeLayout)findViewById(R.id.phone_bound_layout)).setOnClickListener(this);
		((RelativeLayout)findViewById(R.id.login_pwd_layout)).setOnClickListener(this);
		((RelativeLayout)findViewById(R.id.pay_pwd_layout)).setOnClickListener(this);
		switch_gesture_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					startActivityForResult(new Intent(SecurityCenterActivity.this, GesturePasswordActivity.class),0);
				}else{
					LockUtil.clearPwd(SecurityCenterActivity.this);
					LockUtil.setPwdStatus(SecurityCenterActivity.this,false);
					mProgress=mCompleteProgress;
	    			calcScore();
	    			preProgress();
				}
			}
		});
		mSeekArc.setOnSeekArcChangeListener(new OnSeekArcChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekArc seekArc) {
			}

			@Override
			public void onStartTrackingTouch(SeekArc seekArc) {
			}

			@Override
			public void onProgressChanged(SeekArc seekArc, int progress,
										  boolean fromUser) {
				if(progress>45&&progress<86){
					mSeekArc.setProgressColor(Color.parseColor("#ccd5d753"));
					mSeekArc.setThumb(R.drawable.security_level_middle_icon);
					mSeekArcProgress.setTextColor(0xccd5d753);
					mSeekArcProgress_txt.setText(getResources().getString(R.string.security_level_middle));
					mSeekArcProgress_txt.setTextColor(0xccd5d753);
					security_level_hint.setText(getResources().getString(R.string.security_level_middle_hint));
				}else if(progress>85){
					mSeekArc.setProgressColor(Color.parseColor("#cc53d769"));
					mSeekArc.setThumb(R.drawable.security_level_high_icon);
					mSeekArcProgress.setTextColor(0xcc53d769);
					mSeekArcProgress_txt.setText(getResources().getString(R.string.security_level_high));
					mSeekArcProgress_txt.setTextColor(0xcc53d769);
					security_level_hint.setText(getResources().getString(R.string.security_level_high_hint));
				}else{
					mSeekArc.setProgressColor(Color.parseColor("#ccd78b53"));
					mSeekArc.setThumb(R.drawable.security_level_low_icon);
					mSeekArcProgress.setTextColor(0xccd78b53);
					mSeekArcProgress_txt.setText(getResources().getString(R.string.security_level_low));
					mSeekArcProgress_txt.setTextColor(0xccd78b53);
					security_level_hint.setText(getResources().getString(R.string.security_level_low_hint));
				}
				mSeekArcProgress.setText(String.valueOf(progress));
			}
		});
	}
	
	Handler mHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 0:
				mSeekArc.setProgress(mProgress);
				mProgress=mProgress+2;
				if(mProgress>mCompleteProgress){
					if(mTimer!=null){
						mTimer.cancel();
						mTimer=null;
					}
					mProgress=mCompleteProgress;
					mSeekArc.setProgress(mCompleteProgress);
					delay=300;
				}
				break;
			case 1:
				mSeekArc.setProgress(mProgress);
				mProgress=mProgress-2;
				if(mProgress<mCompleteProgress){
					if(mTimer!=null){
						mTimer.cancel();
						mTimer=null;
					}
					mProgress=mCompleteProgress;
					mSeekArc.setProgress(mCompleteProgress);
				}
				break;
			}
		}
	};
	
	private void initData(){
		calcScore();
		nextProgress();
		if("1".equals(UserPersonalInfo.getIsSetTradePwd())){
			pay_pwd_tv.setText(getResources().getString(R.string.pay_psd_setting2));
			pay_pwd_tv.setTextColor(getResources().getColor(R.color.e_main_bg_title));
		}else if ("2".equals(UserPersonalInfo.getIsSetTradePwd())) {
			pay_pwd_tv.setText(getResources().getString(R.string.pay_psd_setting3));
			pay_pwd_tv.setTextColor(getResources().getColor(R.color.text_grey_color));
		}
		if ("1".equals(UserPersonalInfo.getIsBingPhone())) {
			phone_bound_tv.setText(StringUtils.getShowPhoneNum(UserPersonalInfo.getPhoneNo()));
			phone_bound_tv.setTextColor(getResources().getColor(R.color.e_main_bg_title));
		} else if ("2".equals(UserPersonalInfo.getIsBingPhone())) {
			real_name_tv.setText(getResources().getString(R.string.real_name_setting));
		}
		if ("1".equals(UserPersonalInfo.getIsAttestation())) {
			
            if (!TextUtils.isEmpty(UserPersonalInfo.getSname()))
            {
                //替换
                StringBuffer str = new StringBuffer(UserPersonalInfo.getSname());
                str.replace(1, UserPersonalInfo.getSname().length(), ReplaceSymbol(str.length()));
                real_name_tv.setText(str.toString());
                real_name_tv.setTextColor(getResources().getColor(R.color.e_main_bg_title));
                
                //不可点击
                real_name_layout.setClickable(false);
            }
          
		} else if ("2".equals(UserPersonalInfo.getIsAttestation())) {
			real_name_tv.setText(getResources().getString(R.string.real_name_setting));
		}
	}
	
	/**
	 * 计算安全分数
	 */
	private void calcScore(){
		mCompleteProgress=10;
		if("1".equals(UserPersonalInfo.getIsAttestation())){
			mCompleteProgress=mCompleteProgress+25;
		}
		if("1".equals(UserPersonalInfo.getIsBingPhone())){
			mCompleteProgress=mCompleteProgress+20;
		}
		if("1".equals(UserPersonalInfo.getIsSetTradePwd())){
			mCompleteProgress=mCompleteProgress+20;
		}
		if(LockUtil.getPwdStatus(this)){
			mCompleteProgress=mCompleteProgress+20;
		}
	}
	
	/**
	 * 累加进度条
	 */
	private void nextProgress(){
		if(mTimer==null){
			mTimer=new Timer();
			mTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					mHandler.sendEmptyMessage(0);
				}
			},delay, 20);
		}
	}
	
	/**
	 * 累减进度条
	 */
	private void preProgress(){
		if(mTimer==null){
			mTimer=new Timer();
			mTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					mHandler.sendEmptyMessage(1);
				}
			},300, 20);
		}
	}

    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        switch (v.getId())
        {
            case R.id.real_name_layout :
                //			startActivity(new Intent(this,RealNameAuthActivity.class));

                //实名认证：
                //未认证：“未认证”，点按打开实名认证页面。
                //已认证：姓名表示（名子星号替换）。不能点按
                Intent intent = new Intent(SecurityCenterActivity.this, AuthenticationActivity.class);
                intent.putExtra(Constants.IS_Certification_CHECK, isCertification);
                pushActivity(intent);
                break;
            case R.id.phone_bound_layout :
            	if ("1".equals(UserPersonalInfo.getIsBingPhone())) {
            		startActivity(new Intent(this, PhoneBoundAuthActivity.class).putExtra("type", 0));
        		} else if ("2".equals(UserPersonalInfo.getIsBingPhone())) {
        			pushActivity(PhoneBoundActivity.class);
        		}
                break;
            case R.id.login_pwd_layout :
            	{HeadSelectPopupWindow window=new HeadSelectPopupWindow(mContext);
    			window.showWindow();
    			window.setOnDismissListener(new OnDismissListener() {
    				
    				@Override
    				public void onDismiss() {
    					backgroundAlpha(1.0f);
    				}
    			});
    			window.setButtonClickListener(new HeadSelectPopupWindowOnClickListener() {

    				@Override
    				public void onButtonClick1(View v) {
    					startActivity(new Intent(SecurityCenterActivity.this,ModifyLoginPwdActivity.class));
    				}

    				@Override
    				public void onButtonClick2(View v) {
    					// TODO Auto-generated method stub
    					 startActivity(new Intent(SecurityCenterActivity.this,PhoneBoundAuthActivity.class).putExtra("type", 1));  					
    				}
    			});
    			window.showAtLocation(findViewById(R.id.login_pwd_layout),Gravity.BOTTOM,0,0);
    			backgroundAlpha(0.5f);
    			window.setButtonText("修改登录密码", "重置登录密码");}
                break;

            case R.id.pay_pwd_layout :
            	if("1".equals(UserPersonalInfo.getIsSetTradePwd())){
            		HeadSelectPopupWindow window=new HeadSelectPopupWindow(mContext);
        			window.showWindow();
        			window.setOnDismissListener(new OnDismissListener() {
        				
        				@Override
        				public void onDismiss() {
        					backgroundAlpha(1.0f);
        				}
        			});
        			window.setButtonClickListener(new HeadSelectPopupWindowOnClickListener() {

        				@Override
        				public void onButtonClick1(View v) {
        					startActivity(new Intent(SecurityCenterActivity.this,ModifyPayPwdActivity.class).putExtra("ModifyPay_type", 1));
        				}

        				@Override
        				public void onButtonClick2(View v) {
        					// TODO Auto-generated method stub
        					pushActivity(new Intent(SecurityCenterActivity.this,ValidateLoginPasswordActivity.class).putExtra("setType",1));
        				}
        			});
        			window.showAtLocation(findViewById(R.id.pay_pwd_layout),Gravity.BOTTOM,0,0);
        			backgroundAlpha(0.5f);
        			window.setButtonText("修改交易密码", "重置交易密码");
            	}else{
            		pushActivity(new Intent(SecurityCenterActivity.this,ValidateLoginPasswordActivity.class).putExtra("setType", 0));
            	}
                break;
        }
    }
    
    
    
    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initData();
	}

	@Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	if(mTimer!=null){
    		mTimer.cancel();
    		mTimer=null;
    	}
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	if(requestCode==0){
    		if(resultCode!=Activity.RESULT_OK){
    			switch_gesture_cb.setChecked(false);
    		}else{
    			delay=500;
    			mProgress=mCompleteProgress;
    			calcScore();
    			nextProgress();
    		}
    	}
    }
    
    private String ReplaceSymbol(int size)
    {
        if(size == 1||size == 2){
            return "*";
        }else if(size == 3){
            return "**";
        }else if(size == 4){
            return "***";
        }else if(size == 5){
            return "****";
        }
        return "**";
    }
}
