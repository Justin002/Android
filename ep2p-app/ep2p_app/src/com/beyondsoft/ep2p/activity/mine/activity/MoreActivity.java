package com.beyondsoft.ep2p.activity.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.LoginActivity;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.service.MoreService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.FileSizeUtil;
import com.beyondsoft.ep2p.view.gesture.LockUtil;
import com.beyondsoft.ep2p.widget.CustomDialog;
import com.beyondsoft.ep2p.widget.CustomDialog.ButtonOnClickListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

/**
 * @author Ivan.Lu
 * @description "我的"-更多
 */
public class MoreActivity extends BaseFragmentActivity implements OnClickListener{
	
	private static final int LOGINOUT=1;
	private TextView use_space_tv;
	private MoreService moreService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_more);
		moreService=new MoreService();
		init();
		initListener();
	}
	
	Handler mHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			use_space_tv.setText(String.format(getResources().getString(R.string.use_space),msg.obj.toString()));
		}
	};
	
	private void init(){
		setTitle(getResources().getString(R.string.mine_more));
		use_space_tv=(TextView) findViewById(R.id.use_space_tv);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				double size=FileSizeUtil.getFileOrFilesSize(StorageUtils.getOwnCacheDirectory(MoreActivity.this,"ep2p").getAbsolutePath(), FileSizeUtil.SIZETYPE_MB);
				mHandler.obtainMessage(0, size).sendToTarget();
			}
		}).start();
	}
	
	private void initListener(){
		((RelativeLayout)findViewById(R.id.mine_clear_layout)).setOnClickListener(this);
		((RelativeLayout)findViewById(R.id.mine_feedback_layout)).setOnClickListener(this);
		((RelativeLayout)findViewById(R.id.mine_about_layout)).setOnClickListener(this);
		((RelativeLayout)findViewById(R.id.mine_loginout_layout)).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.mine_clear_layout:
			CustomDialog clearDialog=new CustomDialog(this);
			clearDialog.setButtonClickListener(new ButtonOnClickListener() {
				
				@Override
				public void onButton2Click(View v) {
					// TODO Auto-generated method stub				
				}
				
				@Override
				public void onButton1Click(View v) {
					// TODO Auto-generated method stub
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							FileSizeUtil.deleteDirectory(StorageUtils.getOwnCacheDirectory(MoreActivity.this,"ep2p").getAbsolutePath());
							double size=FileSizeUtil.getFileOrFilesSize(StorageUtils.getOwnCacheDirectory(MoreActivity.this,"ep2p").getAbsolutePath(), FileSizeUtil.SIZETYPE_MB);
							mHandler.obtainMessage(0, size).sendToTarget();
						}
					}).start();
					
				}
			});
			clearDialog.show();
			clearDialog.setTitle(getResources().getString(R.string.mine_clear_label));
			clearDialog.setDescri(String.format(getResources().getString(R.string.mine_clear_label_desc),use_space_tv.getText().toString()));
			clearDialog.setButtonText(getResources().getString(R.string.ok), getResources().getString(R.string.cancel));
			break;
		case R.id.mine_feedback_layout:
			startActivity(new Intent(this,FeedbackActivity.class));
			break;
			
		case R.id.mine_about_layout:
			startActivity(new Intent(this,AboutActivity.class));
			break;
		case R.id.mine_loginout_layout:
			CustomDialog loginoutDialog=new CustomDialog(this);
			loginoutDialog.setButtonClickListener(new ButtonOnClickListener() {
				
				@Override
				public void onButton1Click(View v) {
					// TODO Auto-generated method stub
					connection(moreService.loginOut(LOGINOUT, null, MoreActivity.this));
				}
				
				@Override
				public void onButton2Click(View v) {
					// TODO Auto-generated method stub					
				}
			});
			loginoutDialog.show();
			loginoutDialog.hideDescri();
			loginoutDialog.setTitle(getResources().getString(R.string.mine_loginout_label));
			loginoutDialog.setButtonText(getResources().getString(R.string.ok), getResources().getString(R.string.cancel));
			break;
		}
	}

	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		switch (tag) {
		case LOGINOUT:
			LockUtil.clearPwd(this);
			LockUtil.setPwdStatus(this,false);
			//CommonUtils.deleteConfigInfo(this,Constants.EP2P_LOGIN_NAME);
			CommonUtils.deleteConfigInfo(this, Constants.EP2P_TOKEN);
			CommonUtils.deleteConfigInfo(this, Constants.PLAYLISTENTIME);
			CommonUtils.deleteConfigInfo(this, Constants.PLAYCURRENTLISTENTIME);
			CommonUtils.deleteConfigInfo(this, Constants.PLAYCURRENTLISTENNUM);
			CommonUtils.deleteConfigInfo(this, Constants.PLAYDIALOG);		
			CommonUtils.toastMsgShort(this,getString(R.string.mine_setting_loginout_success));
			
			pushActivity(new Intent(this,LoginActivity.class).putExtra("openType","login_out"));
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
		CommonUtils.toastMsgShort(mContext, error);
	}
}
