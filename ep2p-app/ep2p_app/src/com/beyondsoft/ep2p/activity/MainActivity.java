package com.beyondsoft.ep2p.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.beyondsoft.ep2p.ActivityManager;
import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.core.MainHolder;
import com.beyondsoft.ep2p.activity.core.MyHandler;
import com.beyondsoft.ep2p.activity.discover.DiscoverFragment;
import com.beyondsoft.ep2p.activity.home.HomeFragment;
import com.beyondsoft.ep2p.activity.mine.MineFragment;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.model.response.PersonalInfoResponse;
import com.beyondsoft.ep2p.model.response.RatioStationListResponse;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.Logs;
import com.beyondsoft.ep2p.widget.CustomDialog2;

/**
 * @author Ivan.lu
 * @description  主页
 */
public class MainActivity extends BaseFragmentActivity implements OnCheckedChangeListener {

	private FragmentManager mFragmentManager;
	private String mCurrentFragmentTag = null;
	private RadioGroup mMenuGroup;
	private SecurityCenterService securityCenterService;
	private long exitTime = 0;
	private int mCheckId=0;
	private MyHandler handler = new MyHandler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(savedInstanceState!=null){
			mCurrentFragmentTag=savedInstanceState.getString("fragmentTag");
			mCheckId=savedInstanceState.getInt("selectId", 0);
		}
		setContentView(R.layout.activity_main);
		securityCenterService=new SecurityCenterService();
		mFragmentManager = getSupportFragmentManager();
		init();
	}

    private void init(){
		mMenuGroup = (RadioGroup)findViewById(R.id.menu_panel_rg);
		mMenuGroup.setOnCheckedChangeListener(this);
		((RadioButton)mMenuGroup.getChildAt(mCheckId)).setChecked(true);
	}
    
    @Override
    public void onResume()
    {
        super.onResume();
        Logs.d("home_登陆入口注册ue = 刷新UI");
        if(MainHolder.Instance(mContext).isLoginRegister()){  //  ture  是从登陆入口去注册成功的
            //TODO 在"首页"显示红包
            mCheckId = 0;
            ((RadioButton)mMenuGroup.getChildAt(mCheckId)).setChecked(true);
            
            RegistrationSuccessful(); 
            Logs.d("isLoginRegister===:"+MainHolder.Instance(mContext).isLoginRegister());
        }
    }
    
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		String tag = null;
		Fragment fragment = null;
		FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
		if (mCurrentFragmentTag != null) {
			Fragment currentFragment = mFragmentManager.findFragmentByTag(mCurrentFragmentTag);
			if (mFragmentManager != null) {
				fragmentTransaction.hide(currentFragment);
			}
		}
		switch (checkedId) {
		case R.id.home_rb:
			mCheckId=0;
			tag = HomeFragment.class.getSimpleName();
            Fragment menu1 = mFragmentManager.findFragmentByTag(tag);
            if (menu1 != null) {
                fragment = menu1;
            } else {
                fragment = new HomeFragment();
            }
            if(!"".equals(CommonUtils.getStringByKey(this, Constants.EP2P_TOKEN))&&!UserPersonalInfo.isGetUserInfo()){
	            connection(securityCenterService.getPersonalInfo(3, this, this));           
	        }
            handler.sendEmptyMessage(Constants.CHANGED);
            MainHolder.Instance(mContext).setHandle(handler);
			break;

		case R.id.discover_rb:
			mCheckId=1;
			disconnect(1);
			tag = DiscoverFragment.class.getSimpleName();
			Fragment menu2 = mFragmentManager.findFragmentByTag(tag);
			if (menu2 != null) {
				fragment = menu2;
			} else {
				fragment = new DiscoverFragment();
			}
			connection(securityCenterService.getRadioPopList(2, this));
			break;

		case R.id.mine_rb:
			mCheckId=2;
			disconnect(2);
			tag = MineFragment.class.getSimpleName();
			Fragment menu3 = mFragmentManager.findFragmentByTag(tag);
			if (menu3 != null) {
				fragment = menu3;
			} else {
				fragment = new MineFragment();
			}
	        if(!"".equals(CommonUtils.getStringByKey(this, Constants.EP2P_TOKEN))){
	            connection(securityCenterService.getPersonalInfo(1, this, this));           
	        }
			break;
		}
		if (fragment != null && fragment.isAdded()) {
			fragmentTransaction.show(fragment);
		} else {
			fragmentTransaction.add(R.id.frament_main, fragment, tag);
		}
		fragmentTransaction.commit();
		mCurrentFragmentTag = tag;
	}
	
	
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            MainHolder.Instance(mContext).setLoginRegister(false);
            exitAppWithToast();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exitAppWithToast()
    {
        if ((System.currentTimeMillis() - exitTime) > 2000)
        {
            CommonUtils.toastMsgShort(mContext, "再按一次退出程序");
            exitTime = System.currentTimeMillis();
        }
        else
        {
            ActivityManager.getInstance().popAllActivity();
            finish();
            System.gc();
            System.exit(0);
        }
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 30)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                Logs.d("登录后，初始化");
                handler.sendEmptyMessage(Constants.CHANGED);
                MainHolder.Instance(mContext).setHandle(handler);

                return;
            }
        }else{
        	MineFragment fragment=(MineFragment) mFragmentManager.findFragmentByTag(mCurrentFragmentTag);
        	if(fragment!=null){
        		fragment.onActivityResult(requestCode, resultCode, data);
        	}       	
        }
    }

	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		switch (tag) {
		case 1:
		    MainHolder.Instance(mContext).setHeadSetting(false);
		    PersonalInfoResponse response = gson.fromJson(values.toString(),PersonalInfoResponse.class);
			CommonUtils.setUserInfo(response);
			MineFragment fragment=(MineFragment) mFragmentManager.findFragmentByTag(mCurrentFragmentTag);
	    	if(fragment!=null){
	    		fragment.loadData();
	    	}
			break;
		case 2:
			RatioStationListResponse popRadioResponse = gson.fromJson(values.toString(),RatioStationListResponse.class);
			DiscoverFragment discoverFragment=(DiscoverFragment) mFragmentManager.findFragmentByTag(mCurrentFragmentTag);
	    	if(discoverFragment!=null){
	    		discoverFragment.loadData(popRadioResponse);
	    	}
			break;
		case 3:
			PersonalInfoResponse response2 = gson.fromJson(values.toString(),PersonalInfoResponse.class);
			CommonUtils.setUserInfo(response2);
			break;
		}
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		if(intent!=null){
			if("login_out".equals(intent.getStringExtra("openType"))){
				MineFragment fragment=(MineFragment) mFragmentManager.findFragmentByTag(mCurrentFragmentTag);
		    	if(fragment!=null){
		    		fragment.loadData();
		    	}
			}
		}
	}
	
    //注册成功
    private void RegistrationSuccessful()
    {
//        MainHolder.Instance(mContext).setRegistered(true);
        CommonUtils.addConfigInfo(mContext, Constants.IS_REGISTER_CHECK, true);
        final CustomDialog2 clearDialog = new CustomDialog2(MainActivity.this,1);
        clearDialog.show();
        clearDialog.setTitle("注册成功");
//        clearDialog.setDescri("获得188元红包!");
        String Red_packInfo =  CommonUtils.getStringByKey(mContext, Constants.KEY_RED_PACT_USER);
        clearDialog.setDescri(Red_packInfo);
        Logs.d("注册成功的红包信息:"+Red_packInfo);
        clearDialog.setButtonClickListener(new CustomDialog2.ButtonOnClickListener()
        {

            @Override
            public void onLinearLayoutClick(View v)
            {
                MainHolder.Instance(mContext).setLoginRegister(false);
                clearDialog.dismiss();
            }

            @Override
            public void onButton2Click(View v)
            {
                MainHolder.Instance(mContext).setLoginRegister(false);
            }

            @Override
            public void onButton1Click(View v)
            {
                MainHolder.Instance(mContext).setLoginRegister(false);
            }
        });
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	// TODO Auto-generated method stub
    	super.onSaveInstanceState(outState);
    	outState.putInt("selectId", mCheckId);
    	outState.putString("fragmentTag", mCurrentFragmentTag);
    }
}
