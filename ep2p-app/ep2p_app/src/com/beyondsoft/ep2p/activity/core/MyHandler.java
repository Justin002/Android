package com.beyondsoft.ep2p.activity.core;


import android.os.Handler;
import android.os.Message;

import com.beyondsoft.ep2p.common.Constants;


public class MyHandler extends Handler
{
    @Override
    public void handleMessage(Message msg)
    {
        if (msg.what == Constants.CHANGED)
        {
            if (Listener != null)
            {
                Listener.OnHandlerClick();
            }
        }
        super.handleMessage(msg);

    }

    //申明接口对象  
    private OnMyHandlerClickListener Listener;

    //设置监听器 也就是实例化接口
    public void setOnClickListener(OnMyHandlerClickListener listener)
    {
        this.Listener = listener;
    }

    //创建接口  
    public static interface OnMyHandlerClickListener
    {
        public void OnHandlerClick();
    }

}
