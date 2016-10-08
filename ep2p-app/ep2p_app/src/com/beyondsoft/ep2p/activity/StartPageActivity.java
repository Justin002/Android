package com.beyondsoft.ep2p.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.mine.activity.GesturePasswordActivity;
import com.beyondsoft.ep2p.utils.Config;
import com.beyondsoft.ep2p.view.gesture.LockUtil;

/**
 * 
 * 启动页
 * @ClassName:StartPageActivity 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2016年1月6日
 *
 */
public class StartPageActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Config.mContext=this;
        setContentView(R.layout.activity_startpage);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
            	if(LockUtil.getPwdStatus(StartPageActivity.this)){
            		Intent intent=new Intent(StartPageActivity.this, GesturePasswordActivity.class).putExtra("launcher",0);
    				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    				startActivity(intent);
    				finish();
            	}else{
            		Intent intent = new Intent (StartPageActivity.this,MainActivity.class);           
            		startActivity(intent);          
            		finish();
            	}
            }
        }, 1000);
    }
}
