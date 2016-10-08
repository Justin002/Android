package com.beyondsoft.ep2p.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;


public class TimerTextView extends TextView implements Runnable
{

    public TimerTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    private long mday, mhour, mmin, msecond;//天，小时，分钟，秒
    private boolean run = false; //是否启动了

    public void setTimes(long[] times)
    {
//        mday = times[0];   //需求改变  不需要天
        mhour = times[0];
        mmin = times[1];
        msecond = times[2];

    }

    /**
     * 倒计时计算
     */
    private void ComputeTime()
    {
        msecond--;
        if (msecond < 0)
        {
            mmin--;
            msecond = 59;
            if (mmin < 0)
            {
                mmin = 59;
                mhour--;
                if (mhour < 0)
                {
                    // 倒计时结束，一天有24个小时
                    mhour = 23;
                    mday--;

                }
            }
        }
    }

    public boolean isRun()
    {
        return run;
    }

    public void beginRun()
    {
        this.run = true;
        run();
    }

    public void stopRun()
    {
        this.run = false;
    }

    @Override
    public void run()
    {
        //标示已经启动
        if (run)
        {
            ComputeTime();

//            String strTime = mday + "天:" + mhour + "小时:" + mmin + "分钟:" + msecond + "秒";
            String strTime = mhour + "时 : " + mmin + "分 : " + msecond + "秒";
            this.setText(strTime);

            postDelayed(this, 1000);
        }
        else
        {
            removeCallbacks(this);
        }
    }

}