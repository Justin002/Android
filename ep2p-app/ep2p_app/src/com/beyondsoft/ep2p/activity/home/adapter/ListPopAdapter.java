package com.beyondsoft.ep2p.activity.home.adapter;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beyondsoft.ep2p.R;


public class ListPopAdapter extends BaseAdapter
{
    private Context mContext;
    private List<String> message;
    private LayoutInflater mInflater;

    public ListPopAdapter(Context ct, List<String> mList)
    {
        super();
        this.mContext = ct;
        this.mInflater = (LayoutInflater) ct
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.message = mList;
    }

    @Override
    public int getCount()
    {
        return message.size();
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

    @Override
    public View getView(int arg0, View contentView, ViewGroup arg2)
    {
        ViewHolder mViewHolder;
        
        if (contentView == null)
        {
            mViewHolder = new ViewHolder();
            contentView = mInflater
                    .inflate(R.layout.layout_poptype_item, null);
            mViewHolder.ll_elinerlayout =  (LinearLayout) contentView.findViewById(R.id.ll_elinerlayout);
            mViewHolder.tv_pop_items = (TextView) contentView.findViewById(R.id.tv_pop_items);

            contentView.setTag(mViewHolder);
        }
        else
        {
            mViewHolder = (ViewHolder) contentView.getTag();
        }
        mViewHolder.ll_elinerlayout.getBackground().setAlpha(200);//0~255透明度值
        mViewHolder.tv_pop_items.setText(getItem(arg0)+"");
        
        return contentView;
    }

    class ViewHolder
    {
        LinearLayout ll_elinerlayout;
        TextView tv_pop_items;
    }

}
