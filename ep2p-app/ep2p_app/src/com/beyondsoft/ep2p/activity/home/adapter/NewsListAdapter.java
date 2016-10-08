package com.beyondsoft.ep2p.activity.home.adapter;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.response.NewsCenterListResponse.NewsCenterList;
import com.beyondsoft.ep2p.utils.StringUtils;


public class NewsListAdapter<T> extends BaseAdapter
{
    private Context mContext;
    private List<T> message;
    private LayoutInflater mInflater;

    public NewsListAdapter(Context ct, List<T> mList)
    {
        super();
        this.mContext = ct;
        this.mInflater = (LayoutInflater) ct
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.message = mList;
    }

    public void ClearListData()
    {
        if (message.size() > 0)
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
    public View getView(int arg0, View contentView, ViewGroup arg2)
    {
        ViewHolder mViewHolder;
        if (contentView == null)
        {
            mViewHolder = new ViewHolder();
            contentView = mInflater.inflate(R.layout.layout_news_listview_item, null);
            mViewHolder.im_ty_left_ui = (ImageView) contentView
                    .findViewById(R.id.im_ty_left_ui);
            mViewHolder.tv_ty_title = (TextView) contentView
                    .findViewById(R.id.tv_ty_title);
            mViewHolder.tv_ty_time = (TextView) contentView.findViewById(R.id.tv_ty_time);
            mViewHolder.tv_notice_context = (TextView) contentView
                    .findViewById(R.id.tv_notice_context);

            contentView.setTag(mViewHolder);
        }
        else
        {
            mViewHolder = (ViewHolder) contentView.getTag();
            mViewHolder.im_ty_left_ui.setImageResource(R.drawable.page_xx_center_on_03);
        }

        NewsCenterList newsCenLitst = (NewsCenterList) getItem(arg0);

        mViewHolder.tv_ty_title.setText(""
            + StringUtils.isTestNull(newsCenLitst.msgTitle));
        mViewHolder.tv_notice_context.setText(""
            + StringUtils.isTestNull(newsCenLitst.sendContent));
        mViewHolder.tv_ty_time.setText(""
            + StringUtils.isTestNull(newsCenLitst.createTime));

        //灰的是已读    亮的是未读 【是否已读  0:未读  1：已读】
        if (StringUtils.isTestNull(newsCenLitst.isRead + "").equals("0"))
        {//未读
            mViewHolder.im_ty_left_ui.setImageResource(R.drawable.page_xx_center_on_03);
            mViewHolder.tv_ty_title.setTextColor(mContext.getResources().getColor(
                R.color.e_syshint_bg));
            mViewHolder.tv_notice_context.setTextColor(mContext.getResources().getColor(
                R.color.e_text_bg_black));
            mViewHolder.tv_ty_time.setTextColor(mContext.getResources().getColor(
                R.color.e_news_text_color));//cbcbcf e_news_text_color
        }
        else
        {//已读
            mViewHolder.im_ty_left_ui.setImageResource(R.drawable.page_xx_center_off_03);
            mViewHolder.tv_ty_title.setTextColor(mContext.getResources().getColor(
                R.color.e_button_bg_off));//bdc3c7 e_button_bg_off
            mViewHolder.tv_notice_context.setTextColor(mContext.getResources().getColor(
                R.color.e_news_text_color));//cbcbcf
            mViewHolder.tv_ty_time.setTextColor(mContext.getResources().getColor(
                R.color.e_text_huise_buttom));//999999 e_text_huise_buttom

        }
        return contentView;
    }

    class ViewHolder
    {
        ImageView im_ty_left_ui;
        TextView tv_ty_title;
        TextView tv_ty_time;
        TextView tv_notice_context;
    }

}
