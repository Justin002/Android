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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.response.NewStandarUniqueResponse.Result;


public class NewStandarUniqueAdapter<T> extends BaseAdapter
{
    private Context mContext;
    private List<T> message;
    private LayoutInflater mInflater;
    private String bl_falg = "";

    public NewStandarUniqueAdapter(Context ct, List<T> mList)
    {
        super();
        this.mContext = ct;
        this.mInflater = (LayoutInflater) ct
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.message = mList;
    }
    public void ClearListData()
    {
        if (message != null)
        {
            message.clear();
        }
        notifyDataSetChanged();
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

    @SuppressWarnings("unchecked")
    @Override
    public View getView(final int position, View contentView, ViewGroup arg2)
    {
        ViewHolder mViewHolder;
        if (contentView == null)
        {
            mViewHolder = new ViewHolder();
            contentView = mInflater
                    .inflate(R.layout.layout_news_unique_listview_item, null);

            mViewHolder.tv_news_title = (TextView) contentView
                    .findViewById(R.id.tv_news_title);

            mViewHolder.tv_text_nhll = (TextView) contentView
                    .findViewById(R.id.tv_text_nhll);
            mViewHolder.tv_text_month = (TextView) contentView
                    .findViewById(R.id.tv_text_month);
            mViewHolder.tv_text_money = (TextView) contentView
                    .findViewById(R.id.tv_text_money);
            mViewHolder.ll_layout_money = (LinearLayout) contentView
                    .findViewById(R.id.ll_layout_money);
            mViewHolder.bn_recommend_join = (Button) contentView
                    .findViewById(R.id.bn_recommend_join);
            mViewHolder.pro = (ProgressBar) contentView.findViewById(R.id.progressBar2);

            mViewHolder.tv_re_hint1 = (TextView) contentView
                    .findViewById(R.id.tv_re_hint1);
//            mViewHolder.tv_re_hint2 = (TextView) contentView
//                    .findViewById(R.id.tv_re_hint2);
//            mViewHolder.tv_re_hint3 = (TextView) contentView
//                    .findViewById(R.id.tv_re_hint3);

            mViewHolder.iv_image_kjx = (ImageView) contentView
                    .findViewById(R.id.iv_image_kjx);
            contentView.setTag(mViewHolder);
        }
        else
        {
            mViewHolder = (ViewHolder) contentView.getTag();
            mViewHolder.pro.setProgress(0);
            mViewHolder.bn_recommend_join.setBackgroundResource(R.drawable.bn_shape);
            mViewHolder.bn_recommend_join.setClickable(true);
        }
        
        Result mResult = (Result) getItem(position);
        
        if (isTestNull(mResult.yearRate + "").equals(""))
        {
            mViewHolder.tv_text_nhll.setText("0.00%");
        }
        else
        {
            Double mNhllProgress = mResult.yearRate  * 100;//目标进度
            BigDecimal bd = new BigDecimal(mNhllProgress);
            mNhllProgress = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//取2位小数[取值条件：四舍五入]
            if(mNhllProgress % 1 == 0){// 是这个整数，小数点后面是0
                bl_falg = "#,##0";
            }else{//不是整数，小数点后面不是0
                bl_falg = "#,##0.00";
            }
            
            mViewHolder.tv_text_nhll.setText(new DecimalFormat(bl_falg).format(mNhllProgress) + "%");//保留后面两位，不足用0代替
        }
        mViewHolder.tv_text_month.setText(isTestNull(mResult.deadline) + "日");
        //        mViewHolder.tv_text_money.setText(isTestNull(mCFPTypeItem.borrowMoney));

        ClipDrawable d = new ClipDrawable(new ColorDrawable(mContext.getResources()
                .getColor(R.color.e_progressbar_bg)), Gravity.LEFT,
            ClipDrawable.HORIZONTAL);
        mViewHolder.pro.setProgressDrawable(d);

        if (isTestNull(mResult.borProgress+"").equals(""))
        {
            mViewHolder.pro.setProgress(0);
        }
        else
        {
            float mFinalProgress =Float.parseFloat((mResult.borProgress * 100)+"");
            BigDecimal bd = new BigDecimal(mFinalProgress);
            mFinalProgress = bd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();//取2位小数[取值条件：四舍五入]
            if(mFinalProgress > 0 && mFinalProgress < 1){
                mViewHolder.pro.setProgress(Math.round(mFinalProgress+1));//Integer.parseInt(mFinalProgress+"")
            }else{
                mViewHolder.pro.setProgress(Math.round(mFinalProgress));//Integer.parseInt(mFinalProgress+"")
            }
        }
        
        mViewHolder.tv_news_title.setVisibility(View.VISIBLE);
        if(mResult.isType.equals("9")){//注册页面的title[mFalg:9]
            mViewHolder.tv_news_title.setText("新手标");
            mViewHolder.tv_re_hint1.setText(isTestNull(mResult.des));
            
        }else if(mResult.isType.equals("10")){
            mViewHolder.tv_news_title.setText("体验标");
            mViewHolder.tv_re_hint1.setText(isTestNull(mResult.desc));
        }
        mViewHolder.iv_image_kjx.setVisibility(View.GONE);
        
        if (mResult.borStatus.equals("2") || mResult.borStatus.equals("1"))
        {
            
        }else{
            mViewHolder.bn_recommend_join.setBackgroundResource(R.drawable.bn_shape_clickableoff);
            mViewHolder.bn_recommend_join.setClickable(false);//不可以点击
        }
        
        
        if (mResult.borStatus.equals("2") || mResult.borStatus.equals("1"))
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
        TextView tv_news_title;

        TextView tv_text_nhll;//年化利率
        TextView tv_text_month;//期限
        LinearLayout ll_layout_money;//金额的显示布局
        TextView tv_text_money;//金额
        Button bn_recommend_join; //立即加入
        ProgressBar pro; //进度条
        TextView tv_re_hint1;//, tv_re_hint2, tv_re_hint3;//buttom 说明【借款名称，借款编号，计息方式名称】

        ImageView iv_image_kjx;//可加息=图标
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
