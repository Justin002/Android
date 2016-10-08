package com.beyondsoft.ep2p.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.core.MainHolder;

/**
 *上下滚动的textView
 * @ClassName:AutoTextView 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2016年2月24日
 *
 */
public class ScrollTextViewLayout extends LinearLayout
{
    /**
     * 实现textview 上下滚动。
     * 只有一行Text不滚动，2行以上滚动，反复交替显示。
     * @author Mi.Fujia
     */
    private String[] mTextArray;
    private int mDuration = 500;
    private TextView mTextView;
    private int mTextHight;
    private int mIndex = 0;
    private Context mContext;

    public ScrollTextViewLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.scrollText);
        mDuration = a.getInteger(R.styleable.scrollText_animationDuration, 500);
    }

    public String[] getTextArray()
    {
        return this.mTextArray;
    }

    public void setTextArray(String[] textArr)
    {
        this.mTextArray = textArr;
        if (mTextArray.length < 2)
        {
            mTextView.setText(mTextArray[0]);
        }
        else
        {
            mHandler.postDelayed(show, mDuration);
        }
    }

    public int getDuration()
    {
        return mDuration;
    }

    public void setDuration(int duration)
    {
        this.mDuration = duration;
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        mTextView = (TextView) this.getChildAt(0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);
        mTextHight = mTextView.getHeight();
    }

    private Handler mHandler = new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.arg1)
            {
                case 1 :
                    animationOpen();
                    break;
                case 2 :
                    animationClose();
                    break;
            }

        }

    };

    protected void animationOpen()
    {
        post(show);
    }

    protected void animationClose()
    {
        post(dismiss);
    }

    Runnable show = new Runnable()
    {

        @Override
        public void run()
        {
            int fromYDelta = 0, toYDelta = 0;
            fromYDelta = mTextHight;
            TranslateAnimation animation = new TranslateAnimation(0, 0, fromYDelta,
                toYDelta);
            animation.setDuration(mDuration);
            animation.setAnimationListener(new AnimationListener()
            {

                @Override
                public void onAnimationEnd(Animation animation)
                {
                    mHandler.postDelayed(dismiss, 3000);
                }

                @Override
                public void onAnimationRepeat(Animation animation)
                {

                }

                @Override
                public void onAnimationStart(Animation animation)
                {
                    mIndex = mIndex % mTextArray.length;
                    mTextView.setText(mTextArray[mIndex]);
                    mTextView.setVisibility(View.VISIBLE);
                    mIndex += 1;
                    MainHolder.Instance(mContext).setSystemNotice_Index(mIndex);
                }

            });
            startAnimation(animation);
        }

    };
    Runnable dismiss = new Runnable()
    {

        @Override
        public void run()
        {
            int fromYDelta = 0, toYDelta = 0;
            toYDelta = -1 * mTextHight;
            fromYDelta = mTextHight;
            TranslateAnimation animation = new TranslateAnimation(0, 0, fromYDelta,
                toYDelta);
            animation.setDuration(3000);
            animation.setAnimationListener(new AnimationListener()
            {
                @Override
                public void onAnimationEnd(Animation animation)
                {
                }

                @Override
                public void onAnimationRepeat(Animation animation)
                {

                }

                @Override
                public void onAnimationStart(Animation animation)
                {
                    mHandler.postDelayed(show, mDuration);
                    mTextView.setVisibility(View.INVISIBLE);
                }

            });
            startAnimation(animation);
        }

    };
}
