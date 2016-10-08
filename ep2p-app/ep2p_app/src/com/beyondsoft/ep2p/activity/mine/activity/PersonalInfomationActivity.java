package com.beyondsoft.ep2p.activity.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.core.MainHolder;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.model.response.UpdateHeadResponse;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.BitmapUtils;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.ImageLoadOptions;
import com.beyondsoft.ep2p.view.HeadSelectPopupWindow;
import com.beyondsoft.ep2p.view.HeadSelectPopupWindow.HeadSelectPopupWindowOnClickListener;

/**
 * @author Ivan.Lu
 * @description "我的"-个人基本资料
 */
public class PersonalInfomationActivity extends BaseFragmentActivity implements OnClickListener{
	private static final int UPLOADHEADIMG=1;
	private TextView tv_my_region;
	private TextView tv_my_vip;
	private ImageView iv_my_icon;
	private HeadSelectPopupWindow window;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_info);
		init();
		initListener();
		initData();
	}
	
	private void init(){
		setTitle(getResources().getString(R.string.personal_label));
		setRightTitle(getResources().getString(R.string.mine_finish));
		tv_my_region=(TextView) findViewById(R.id.tv_my_region);
		iv_my_icon=(ImageView) findViewById(R.id.iv_my_icon);
		tv_my_vip=(TextView) findViewById(R.id.tv_my_vip);
	}
	
	private void initListener(){
		((RelativeLayout)findViewById(R.id.username_rl)).setOnClickListener(this);
		((RelativeLayout)findViewById(R.id.vip_level_rl)).setOnClickListener(this);
		((RelativeLayout)findViewById(R.id.icon_rl)).setOnClickListener(this);
		((TextView)findViewById(R.id.title_right)).setOnClickListener(this);
	}

	private void initData() {
		tv_my_region.setText(UserPersonalInfo.getCustomerName3());
		tv_my_vip.setText(UserPersonalInfo.getVipLevelName());
		imageLoader.displayImage(UserPersonalInfo.getImageUrl(), iv_my_icon,ImageLoadOptions.getCircleOptions());
	}
	
	@Override
	protected void TakePhotoCallBack(String photoAbsolutePath) {
		// TODO Auto-generated method stub
		iv_my_icon.setImageBitmap(BitmapUtils.getRoundBitmap(BitmapUtils.getBitmap(photoAbsolutePath, iv_my_icon.getWidth())));
		iv_my_icon.setTag(photoAbsolutePath);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.username_rl:
			pushActivityForResult(new Intent(this, ModifyUserNameActivity.class), 1);
			break;
		case R.id.vip_level_rl:
			startActivityForResult(new Intent(this, VipLevelActivity.class),2);
			break;
		case R.id.icon_rl://修改头像
			window=new HeadSelectPopupWindow(mContext);
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
					getImageFromPhoto(iv_my_icon.getWidth(), iv_my_icon.getHeight());
					
				}

				@Override
				public void onButtonClick2(View v) {
					// TODO Auto-generated method stub
					getImageFromCamera(iv_my_icon.getWidth(), iv_my_icon.getHeight());
					
				}
			});

			window.showAtLocation(findViewById(R.id.icon_rl),Gravity.BOTTOM,0,0);
			backgroundAlpha(0.5f);
			break;
		case R.id.title_right:
			if(iv_my_icon.getTag()!=null){
				SecurityCenterService securityCenterService=new SecurityCenterService();
				connection(securityCenterService.upLoadHeadImg(UPLOADHEADIMG, iv_my_icon.getTag().toString(), this));
			}else{
				setResult(Activity.RESULT_OK);
				finish();
			}
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==1){
			if(resultCode==Activity.RESULT_OK){
				tv_my_region.setText(UserPersonalInfo.getCustomerName());
			}
		}else if(requestCode==2){
			if(resultCode==Activity.RESULT_OK){
				tv_my_vip.setText(UserPersonalInfo.getVipLevelName());
			}
		}
	}

	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		UpdateHeadResponse updateHeadResponse=gson.fromJson(values.toString(), UpdateHeadResponse.class);
		UserPersonalInfo.setImageUrl(updateHeadResponse.getResult().getCustomer().getImageUrl());
		CommonUtils.toastMsgShort(this,getResources().getString(R.string.success_headimg_hint));
		MainHolder.Instance(mContext).setHeadSetting(true);
		setResult(Activity.RESULT_OK);
		finish();
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		CommonUtils.toastMsgShort(this,getResources().getString(R.string.error_headimg_hint));
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		setResult(Activity.RESULT_OK);
		super.onBackPressed();
	}
}
