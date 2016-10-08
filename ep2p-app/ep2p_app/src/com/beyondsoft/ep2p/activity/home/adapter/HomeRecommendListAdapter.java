package com.beyondsoft.ep2p.activity.home.adapter;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.response.HomeRecommendProjectsResponse.HomeRecommendProjectsItem;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.utils.TimeUtil;
import com.beyondsoft.ep2p.view.CountDownButtonView;


public class HomeRecommendListAdapter<T> extends BaseAdapter
{
    private Context mContext;
    private List<T> message;
    private LayoutInflater mInflater;
    private String bl_falg ="",money_bl_falg = "";
    private long mSys_time = 0;

    public HomeRecommendListAdapter(Context ct, List<T> mList)
    {
        super();
        this.mContext = ct;
        this.mInflater = (LayoutInflater) ct
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.message = mList;
    }
    
    public HomeRecommendListAdapter(Context ct, List<T> mList,long stime)
    {
        super();
        this.mContext = ct;
        this.mInflater = (LayoutInflater) ct
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.message = mList;
        this.mSys_time = stime;
    }

    @Override
    public int getCount()
    {
        return message.size() > 0 ? message.size() : 0;
    }

    @Override
    public Object getItem(int arg0)
    {
        return message.get(arg0);
    }

    @Override
    public long getItemId(int arg0)
    {
        return arg0;
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    @Override
    public View getView(final int position, View contentView, ViewGroup arg2)
    {
        ViewHolder mViewHolder;
        if (contentView == null)
        {
            mViewHolder = new ViewHolder();
            contentView = mInflater.inflate(R.layout.layout_home_recommend_listview_item,
                null);

            mViewHolder.tv_text_nhll = (TextView) contentView
                    .findViewById(R.id.tv_text_nhll);
            mViewHolder.tv_text_month = (TextView) contentView
                    .findViewById(R.id.tv_text_month);
            mViewHolder.ll_layout_money =  (LinearLayout) contentView
                    .findViewById(R.id.ll_layout_money);
            mViewHolder.tv_text_money = (TextView) contentView
                    .findViewById(R.id.tv_text_money);
            mViewHolder.tv_text_nhll = (TextView) contentView
                    .findViewById(R.id.tv_text_nhll);
            mViewHolder.tv_text_nhll = (TextView) contentView
                    .findViewById(R.id.tv_text_nhll);
            mViewHolder.tv_text_nhll = (TextView) contentView
                    .findViewById(R.id.tv_text_nhll);
            mViewHolder.bn_recommend_join = (CountDownButtonView) contentView
                    .findViewById(R.id.bn_recommend_join);
            mViewHolder.pro = (ProgressBar) contentView.findViewById(R.id.progressBar2);

            mViewHolder.tv_re_hint1 = (TextView) contentView
                    .findViewById(R.id.tv_re_hint1);
            mViewHolder.tv_re_hint2 = (TextView) contentView
                    .findViewById(R.id.tv_re_hint2);
            mViewHolder.tv_re_hint3 = (TextView) contentView
                    .findViewById(R.id.tv_re_hint3);

            mViewHolder.iv_image_kjx = (TextView) contentView
                    .findViewById(R.id.iv_image_kjx);
            mViewHolder.tv_join_buttons = (TextView) contentView
                    .findViewById(R.id.tv_join_buttons);
            contentView.setTag(mViewHolder);
        }
        else
        {
            mViewHolder = (ViewHolder) contentView.getTag();
            mViewHolder.pro.setProgress(0);
            mViewHolder.tv_text_nhll.setText("0.0%");
            mViewHolder.bn_recommend_join.setBackgroundResource(R.drawable.bn_shape);
            mViewHolder.bn_recommend_join.setClickable(true);
        }

        HomeRecommendProjectsItem ProjectsItem = (HomeRecommendProjectsItem) getItem(position);
        
        if (isTestNull(ProjectsItem.borrowRate + "").equals(""))
        {
            mViewHolder.tv_text_nhll.setText("0.0%");
        }
        else
        {
            Double mNhllProgress = ProjectsItem.borrowRate  * 100;//目标进度
            BigDecimal bd = new BigDecimal(mNhllProgress);
            mNhllProgress = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//取2位小数[取值条件：四舍五入]
            if(mNhllProgress % 1 == 0){// 是这个整数，小数点后面是0
                bl_falg = "#,##0";
            }else{//不是整数，小数点后面不是0
                bl_falg = "#,##0.00";
            }
            mViewHolder.tv_text_nhll.setText(new DecimalFormat(bl_falg).format(mNhllProgress) + "%");//保留后面两位，不足用0代替
        }

        mViewHolder.tv_text_month
                .setText(isTestNull(ProjectsItem.borDeadline + "") + "月");

        ClipDrawable d = new ClipDrawable(new ColorDrawable(mContext.getResources()
                .getColor(R.color.e_progressbar_bg)), Gravity.LEFT,
            ClipDrawable.HORIZONTAL);
        mViewHolder.pro.setProgressDrawable(d);
        if (isTestNull(ProjectsItem.borrowProgress + "").equals("")
            || ProjectsItem.borrowProgress == 0)
        {
            mViewHolder.pro.setProgress(0);
        }
        else
        {
            float mFinalProgress = Float.parseFloat(ProjectsItem.borrowProgress + "") * 100;//目标进度
            BigDecimal bd = new BigDecimal(mFinalProgress);
            mFinalProgress = bd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();//取2位小数[取值条件：四舍五入]
            
            if(mFinalProgress > 0 && mFinalProgress < 1){
                mViewHolder.pro.setProgress(Math.round(mFinalProgress+1));
            }else{
                mViewHolder.pro.setProgress(Math.round(mFinalProgress));
            }
           
        }

        mViewHolder.tv_re_hint1.setText(isTestNull(ProjectsItem.borrowTypeName));
        mViewHolder.tv_re_hint2.setText(isTestNull(ProjectsItem.borrowCode));//#TODO e计划【不显示】
        mViewHolder.tv_re_hint3.setText(isTestNull(ProjectsItem.accrualTypeName));

        if (isTestNull(ProjectsItem.isJiaxiTicket + "").equals(""))
        {
            mViewHolder.iv_image_kjx.setVisibility(View.GONE);
//            奖励1.02%
        }
        else
        {
            if (ProjectsItem.isJiaxiTicket == 1)//可以使用
            {
                mViewHolder.iv_image_kjx.setVisibility(View.VISIBLE);
                float mFinvestRewardScale = Float.parseFloat(ProjectsItem.investRewardScale + "") * 100;//目标进度
                BigDecimal bd = new BigDecimal(mFinvestRewardScale);
                mFinvestRewardScale = bd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();//取2位小数[取值条件：四舍五入]
//                mViewHolder.iv_image_kjx.setText("奖励"+isTestNull(mFinvestRewardScale + "")+"%");
                mViewHolder.iv_image_kjx.setText("奖励"+new DecimalFormat("#,##0.00").format(mFinvestRewardScale) + "%");//保留后面两位，不足用0代替
            }
            else
            {
                mViewHolder.iv_image_kjx.setVisibility(View.GONE);
            }
        }
        if (ProjectsItem.borStatus.equals("2"))
        {
            if(position == 0){
                mViewHolder.bn_recommend_join.setText("立即加入");
            }else{
                mViewHolder.bn_recommend_join.setText("立即投资");
            }
        }else if (ProjectsItem.borStatus.equals("1"))//      第一个 当标的状态为1（待发布） 并且发布时间不为空 时  显示倒计时
        { //显示时间
          //已开始未结束的 用发布时间和招标期限 计算
//            long ss = mSys_time  - TimeUtil.stringToLong(ProjectsItem.publishTime, "yyyy-MM-dd HH:mm:ss");
            mViewHolder.tv_join_buttons.setVisibility(View.VISIBLE);
            mViewHolder.bn_recommend_join.setTimes(StringUtils.CountDown(ProjectsItem.publishTime, TimeUtil.longToString(mSys_time, "yyyy-MM-dd HH:mm:ss")));
//          mViewHolder.bn_recommend_join.setText();
          if (!mViewHolder.bn_recommend_join.isRun())
          {
              mViewHolder.bn_recommend_join.beginRun();//开始倒计时
          }
        }else{//招标中
            if (ProjectsItem.borStatus.equals("5"))
            {
                mViewHolder.bn_recommend_join.setText("已满额");
            }
            else
            {
                mViewHolder.bn_recommend_join.setText("已结束");
            }
            mViewHolder.bn_recommend_join.setBackgroundResource(R.drawable.bn_shape_clickableoff);
            mViewHolder.bn_recommend_join.setClickable(false);//不可以点击
        }
//        else if(ProjectsItem.borStatus.equals("5")){//还款中
//            mViewHolder.bn_recommend_join.setText("还款中");
//        }
        
        //后面两项显示金额
        if(position != 0){ //不是第一项  就显示金额
            mViewHolder.ll_layout_money.setVisibility(View.VISIBLE);
//            mViewHolder.tv_text_money.setText(isTestNull(""+Math.round(ProjectsItem.borrowMoney)+"万"));  //去掉小数点后的数值，，需求变更
            if(!TextUtils.isEmpty(ProjectsItem.borrowMoney+"")){
                double mMoney = ProjectsItem.borrowMoney/10000;
                if(mMoney % 1 == 0){// 是这个整数，小数点后面是0
                    money_bl_falg = "#,##0";
                }else{//不是整数，小数点后面不是0
                    money_bl_falg = "#,##0.00";
                }
                mViewHolder.tv_text_money.setText(new DecimalFormat(money_bl_falg).format(mMoney)+"万");//保留后面两位，不足用0代替
            }
        }
        if (ProjectsItem.borStatus.equals("2") || ProjectsItem.borStatus.equals("1"))
        {
            mViewHolder.bn_recommend_join.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View arg0)
                {
                    if (Listener != null)
                    {
                        Listener.OnClick(position);
                    }
                }
            });
        }
        return contentView;
    }

    class ViewHolder
    {
        TextView tv_text_nhll;//年化利率
        TextView tv_text_month;//期限
        TextView tv_text_money;//金额
//        Button bn_recommend_join; //立即加入
        CountDownButtonView bn_recommend_join; //立即加入
        ProgressBar pro; //进度条
        TextView tv_re_hint1, tv_re_hint2, tv_re_hint3;//buttom 说明

        TextView iv_image_kjx, tv_join_buttons;//可加息=图标
        LinearLayout ll_layout_money;
    }

    //申明接口对象  
    private OnClickListener Listener;

    //设置监听器 也就是实例化接口
    public void setOnClickListener(OnClickListener listener)
    {
        this.Listener = listener;
    }

    //创建接口  
    public static interface OnClickListener
    {
        public void OnClick(int flag);
    }

    public String isTestNull(String text)
    {
        return TextUtils.isEmpty(text) ? "" : text;
    }

}
