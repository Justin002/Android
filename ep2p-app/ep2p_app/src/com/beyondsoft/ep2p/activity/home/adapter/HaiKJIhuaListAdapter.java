package com.beyondsoft.ep2p.activity.home.adapter;


import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.response.TransferInfoResponse.ReceiptPlanList;
import com.beyondsoft.ep2p.utils.StringUtils;


public class HaiKJIhuaListAdapter extends BaseAdapter
{
    private Context mContext;
    private List<ReceiptPlanList> message;
    private LayoutInflater mInflater;

    public HaiKJIhuaListAdapter(Context ct, List<ReceiptPlanList> mList)
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

    @Override
    public View getView(final int position, View contentView, ViewGroup arg2)
    {
        ViewHolder mViewHolder;
        if (contentView == null)
        {
            mViewHolder = new ViewHolder();
            contentView = mInflater.inflate(R.layout.layout_haikjihualistadapter, null);
            mViewHolder.tv_text_hktime_left = (TextView) contentView
                    .findViewById(R.id.tv_hktime_left);
            mViewHolder.tv_text_hktime = (TextView) contentView
                    .findViewById(R.id.tv_hktime);
            mViewHolder.tv_text_lixi = (TextView) contentView.findViewById(R.id.tv_lixi);
            mViewHolder.tv_text_benjin = (TextView) contentView
                    .findViewById(R.id.tv_benjin);
            contentView.setTag(mViewHolder);
        }
        else
        {
            mViewHolder = (ViewHolder) contentView.getTag();
        }
        ReceiptPlanList mReceiptPlanList = (ReceiptPlanList) getItem(position);
        mViewHolder.tv_text_hktime_left.setText(position+1 + "/" + getCount());
        mViewHolder.tv_text_hktime.setText(isTestNull(mReceiptPlanList.rDate));

        if (!TextUtils.isEmpty(mReceiptPlanList.capital + "")
            && mReceiptPlanList.capital > 0
            && !TextUtils.isEmpty(mReceiptPlanList.netInterest + "")
            && mReceiptPlanList.netInterest > 0)
        {
            double total = mReceiptPlanList.capital + mReceiptPlanList.netInterest;
            mViewHolder.tv_text_benjin.setText("￥" + StringUtils.formatMoney(total));
            mViewHolder.tv_text_lixi.setText("本息");
        }
        else if (!TextUtils.isEmpty(mReceiptPlanList.capital + "")
            && mReceiptPlanList.capital > 0)
        {//本金为不为空
            mViewHolder.tv_text_benjin.setText("￥"
                + StringUtils.formatMoney(mReceiptPlanList.capital));
            mViewHolder.tv_text_lixi.setText("本金");
        }
        else if (!TextUtils.isEmpty(mReceiptPlanList.netInterest + "")
            && mReceiptPlanList.netInterest > 0)
        {//利息不为为空
            mViewHolder.tv_text_benjin.setText("￥"
                + StringUtils.formatMoney(mReceiptPlanList.netInterest));
            mViewHolder.tv_text_lixi.setText("利息");
        }
        else // //本金和利息都没有
        {
            double total = mReceiptPlanList.capital + mReceiptPlanList.netInterest;
            mViewHolder.tv_text_lixi.setText("利息");
            mViewHolder.tv_text_benjin.setText("￥" + StringUtils.formatMoney(total));
        }

        return contentView;
    }

    class ViewHolder
    {
        TextView tv_text_hktime_left;
        TextView tv_text_hktime;
        TextView tv_text_lixi;
        TextView tv_text_benjin;
    }

    public String isTestNull(String text)
    {
        return TextUtils.isEmpty(text) ? "" : text;
    }

}
