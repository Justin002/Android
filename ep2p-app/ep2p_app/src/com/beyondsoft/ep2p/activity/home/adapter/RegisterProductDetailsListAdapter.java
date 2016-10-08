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
import com.beyondsoft.ep2p.model.response.BorrowDetailListResponse.NewStandardInformation;
import com.beyondsoft.ep2p.utils.StringUtils;


/**
 * 
 * 注册页面的产品详情Adapter
 * @ClassName:RegisterProductDetailsListAdapter 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2015年12月16日
 *
 */
public class RegisterProductDetailsListAdapter extends BaseAdapter
{
    private Context mContext;
    private List<NewStandardInformation> message;
    private LayoutInflater mInflater;

    public RegisterProductDetailsListAdapter(Context ct,
            List<NewStandardInformation> mList)
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
    public View getView(int arg0, View contentView, ViewGroup arg2)
    {
        ViewHolder mViewHolder;
        if (contentView == null)
        {
            mViewHolder = new ViewHolder();
            contentView = mInflater.inflate(R.layout.layout_register_pdetails_item, null);

            mViewHolder.tv_usname = (TextView) contentView.findViewById(R.id.tv_usname);
            mViewHolder.tv_usdate = (TextView) contentView.findViewById(R.id.tv_usdate);
            mViewHolder.tv_usmony = (TextView) contentView.findViewById(R.id.tv_usmony);

            contentView.setTag(mViewHolder);
        }
        else
        {
            mViewHolder = (ViewHolder) contentView.getTag();
        }

        NewStandardInformation mNewStandardInformation = (NewStandardInformation) getItem(arg0);

        mViewHolder.tv_usname.setText(isTestNull(mNewStandardInformation.privateName));
        mViewHolder.tv_usdate.setText(isTestNull(mNewStandardInformation.investmentTime));
        mViewHolder.tv_usmony.setText("￥"
            + StringUtils.formatMoney(mNewStandardInformation.investmentAmount));
//        +isTestNull(mNewStandardInformation.investmentAmount + "")

        return contentView;
    }

    class ViewHolder
    {
        TextView tv_usname, tv_usdate, tv_usmony;
    }

    private String isTestNull(String text)
    {
        return TextUtils.isEmpty(text) ? "" : text;
    }
}
