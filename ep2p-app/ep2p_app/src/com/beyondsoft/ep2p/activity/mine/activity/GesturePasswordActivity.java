package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyondsoft.ep2p.ActivityManager;
import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.LoginActivity;
import com.beyondsoft.ep2p.activity.MainActivity;
import com.beyondsoft.ep2p.activity.core.MainHolder;
import com.beyondsoft.ep2p.activity.home.RegisterActivity;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.Logs;
import com.beyondsoft.ep2p.view.gesture.CustomLockView;
import com.beyondsoft.ep2p.view.gesture.CustomLockView.OnCompleteListener;
import com.beyondsoft.ep2p.view.gesture.LockUtil;
import com.beyondsoft.ep2p.widget.ConFirmDialog;
import com.beyondsoft.ep2p.widget.ConFirmDialog.ButtonOnClickListener;

/**
 * @author Ivan.Lu
 * @description 手势密码
 */
public class GesturePasswordActivity extends BaseFragmentActivity {

	private TextView gesture_hint_tv;
	private ImageView iva, ivb, ivc, ivd, ive, ivf, ivg, ivh, ivi;
	private List<ImageView> list = new ArrayList<ImageView>();
	private int[] mIndexs = null;
	private String mGesturePwd;
	private int times = 0;
	private int isInputFinish = 0;
	/**是否从启动页跳转
	 * 0:是**/
	private int mLauncher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gesture_password);
		mGesturePwd=LockUtil.getPwd(this);
		if(getIntent()!=null){
		    isInputFinish = getIntent().getIntExtra(Constants.EP2P, 0);
	        mLauncher=getIntent().getIntExtra("launcher", -1);
		}
		initPoint();
		init();
		initListener();
		initData();
	}

	private void init() {
		setTitle(getResources().getString(R.string.security_center_new_gesture_pwd));
		((RelativeLayout)findViewById(R.id.title_layout)).setBackgroundColor(0xff3486bd);
		gesture_hint_tv=(TextView) findViewById(R.id.gesture_hint_tv);
		final CustomLockView cl = (CustomLockView) findViewById(R.id.cl);
		if("".equals(mGesturePwd)){
			cl.setOnCompleteListener(new OnCompleteListener() {

				@Override
				public void onError() {
					// TODO Auto-generated method stub
					gesture_hint_tv.setTextColor(0xfff1c40f);
					gesture_hint_tv.setText(getResources().getString(R.string.security_center_gesture_error_hint));
				}

				@Override
				public void onComplete(int[] indexs) {
					// TODO Auto-generated method stub
					mIndexs = indexs;
					// 显示次数
					if (times == 0) {
						for (int i = 0; i < indexs.length; i++) {
							list.get(indexs[i]).setImageDrawable(
									getResources().getDrawable(
											R.drawable.gesturecirle_white));
						}
						gesture_hint_tv.setText(getResources().getString(R.string.security_center_again_gesture_pwd));
						times++;
					} else if (times == 1) {
						CommonUtils.toastMsgShortForStyle(GesturePasswordActivity.this,getResources().getString(R.string.security_center_modify_set_success));
						// 将密码设置在本地
						LockUtil.setPwdToDisk(GesturePasswordActivity.this, mIndexs);
						LockUtil.setPwdStatus(GesturePasswordActivity.this, true);
	                    if (isInputFinish == 10)//#TODO 从新手专享页面过来的
	                    {
	                        ActivityManager.getInstance().pushActivity(GesturePasswordActivity.this);
	                        ActivityManager.getInstance().popAllActivity();
//	                        //保存注册成功的标记
//	                        CommonUtils.addConfigInfo(mContext, Constants.IS_REGISTER_CHECK, 10);
	                        //手势密码确认  ，注册完成
	                        Intent intent = new Intent(GesturePasswordActivity.this,
	                            RegisterActivity.class);
	                        intent.putExtra(Constants.EP2P, 10);
	                        startActivity(intent);
	                    }else if(isInputFinish == 55){//#TODO 从登录页面过来的
	                        Logs.d("从登录页面过来的========");
	                        MainHolder.Instance(mContext).setLoginRegister(true);
	                        //手势密码确认  ，注册完成
	                        Intent intent = new Intent(GesturePasswordActivity.this,
	                            MainActivity.class);
	                        startActivity(intent);
	                        ActivityManager.getInstance().pushActivity(GesturePasswordActivity.this);
                            ActivityManager.getInstance().popAllActivity();
	                    }else{
	                    	setResult(Activity.RESULT_OK);
							finish();
	                    }
					}
				}
			});
		}else{
			setTitle(getResources().getString(R.string.security_center_new_gesture_pwd_title));
			gesture_hint_tv.setText(getResources().getString(R.string.security_center_new_gesture_pwd_hint_login));
			((ImageView) findViewById(R.id.title_left_btn)).setVisibility(View.GONE);
			cl.setmIndexs(mIndexs);
			cl.setmIndexsMD5(mGesturePwd);
			cl.setErrorTimes(5);
			cl.setStatus(1);
			cl.setShow(false);
			cl.setOnCompleteListener(new CustomLockView.OnCompleteListener() {
				@Override
				public void onComplete(int[] indexs) {
					//Toast.makeText(GesturePasswordActivity.this, "正确",Toast.LENGTH_SHORT).show();
					if(mLauncher==0){     
	            		startActivity(new Intent (GesturePasswordActivity.this,MainActivity.class));
	            		finish();
					}else{
						finish();						
					}
					
					if (isInputFinish == 10)//#TODO 从新手专享页面过来的
                    {
                        ActivityManager.getInstance().pushActivity(GesturePasswordActivity.this);
                        ActivityManager.getInstance().popAllActivity();
//                        //保存注册成功的标记
//                        CommonUtils.addConfigInfo(mContext, Constants.IS_REGISTER_CHECK, 10);
                        //手势密码确认  ，注册完成
                        Intent intent = new Intent(GesturePasswordActivity.this,
                            RegisterActivity.class);
                        intent.putExtra(Constants.EP2P, 10);
                        startActivity(intent);
                    }else if(isInputFinish == 55){//#TODO 从登录页面过来的
                        Logs.d("从登录页面过来的========存在");
                        MainHolder.Instance(mContext).setLoginRegister(true);
                        //手势密码确认  ，注册完成
                        Intent intent = new Intent(GesturePasswordActivity.this,
                            MainActivity.class);
                        startActivity(intent);
                        ActivityManager.getInstance().pushActivity(GesturePasswordActivity.this);
                        ActivityManager.getInstance().popAllActivity();
                    }
				}

				@Override
				public void onError() {
					if (cl.getErrorTimes() > 0) {
						gesture_hint_tv.setTextColor(0xfff1c40f);
						gesture_hint_tv.setText("手势密码错误，还可以输入"+ cl.getErrorTimes() + "次");
					}else{
						//CommonUtils.toastMsgShort(GesturePasswordActivity.this,"您已连续5次输入错误手势，请重新登录!");
						LockUtil.clearPwd(GesturePasswordActivity.this);
						LockUtil.setPwdStatus(GesturePasswordActivity.this,false);
						CommonUtils.deleteConfigInfo(GesturePasswordActivity.this, Constants.EP2P_TOKEN);
						ConFirmDialog conFirmDialog=new ConFirmDialog(GesturePasswordActivity.this);
						conFirmDialog.setCancelable(false);
						conFirmDialog.setButtonClickListener(new ButtonOnClickListener() {						
							@Override
							public void onButton1Click(View v) {
								// TODO Auto-generated method stub
								pushActivity(new Intent(GesturePasswordActivity.this,LoginActivity.class).putExtra("openType", "login_out"));
								ActivityManager.getInstance().exitApp();
							}
						});
						conFirmDialog.show();
					}
				}
			});
		}
	}
	
	private void initListener(){
		
	}
	
	private void initData(){
		
	}

	/**
	 * 初始化控件
	 */
	private void initPoint() {
		// 初始化9个小圆
		iva = (ImageView) findViewById(R.id.iva);
		ivb = (ImageView) findViewById(R.id.ivb);
		ivc = (ImageView) findViewById(R.id.ivc);
		ivd = (ImageView) findViewById(R.id.ivd);
		ive = (ImageView) findViewById(R.id.ive);
		ivf = (ImageView) findViewById(R.id.ivf);
		ivg = (ImageView) findViewById(R.id.ivg);
		ivh = (ImageView) findViewById(R.id.ivh);
		ivi = (ImageView) findViewById(R.id.ivi);
		list.add(iva);
		list.add(ivb);
		list.add(ivc);
		list.add(ivd);
		list.add(ive);
		list.add(ivf);
		list.add(ivg);
		list.add(ivh);
		list.add(ivi);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(LockUtil.getPwdStatus(this)){
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
