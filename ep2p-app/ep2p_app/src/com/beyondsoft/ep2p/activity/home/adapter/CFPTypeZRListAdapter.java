package com.beyondsoft.ep2p.activity.home.adapter;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.response.TransferResponse.TransferItem;
import com.beyondsoft.ep2p.utils.StringUtils;

/**
 * 
 * 转让的Adapter
 * @ClassName:CFPTypeZRListAdapter 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2015年12月25日
 *
 */
public class CFPTypeZRListAdapter<T> extends BaseAdapter
{
    private Context mContext;
    private List<T> message;
    private LayoutInflater mInflater;
    private String bl_falg = "";

    public CFPTypeZRListAdapter(Context ct, List<T> mList)
    {
        super();
        this.mContext = ct;
        this.mInflater = (LayoutInflater) ct
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.message = mList;
    }
    public void ClearListData()
    {
        if(message.size()>0){
            message.clear();
        }
        notifyDataSetChanged();
    }
    @Override
    public int getCount()
    {
        return message.size()>0?message.size():0;
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

    //简单对convertView进行复用，复用的意思是回收已经用过的视图，需要显示的时候再次利用
    /**
     * 我们每次绘制视图的时候都会调用getView这个方法，它有三个参数分别是： 
position - 视图的位置，从0开始。 
convertView - 我们最外层的视图，也就是我们的动态加载进来的布局，每一项条目。 
parent - 我们视图的父容器。
     */
    @SuppressWarnings("unchecked")
    @Override
    public View getView(final int position, View contentView, ViewGroup arg2)
    {
        ViewHolder mViewHolder;
        if (contentView == null)
        {
            contentView = mInflater
                    .inflate(R.layout.layout_recommend_zr_listview_item, null);
            mViewHolder = new ViewHolder(contentView);
            contentView.setTag(mViewHolder);
        }
        else
        {
            mViewHolder = (ViewHolder) contentView.getTag();
            mViewHolder.ll_layout_zritem.setBackgroundResource(R.color.white);
            
            mViewHolder.tv_text_nhll.setTextColor(mContext.getResources().getColor(
                R.color.e_text_bg_yellow));
            mViewHolder.tv_text_sy_money.setTextColor(mContext.getResources().getColor(
                R.color.e_text_bg_yellow));
            mViewHolder.tv_text_day.setTextColor(mContext.getResources().getColor(
                R.color.e_text_bg_yellow));
            
            mViewHolder.iv_img_hint1.setImageResource(R.drawable.e_img_zr_iocn_03);
            mViewHolder.iv_img_hint23
            .setImageResource(R.drawable.e_img_zr_icon_right_off_03);
        }
        TransferItem transferItem = (TransferItem) getItem(position);//不管视图是否重用，都重新设置数据
   
        if(StringUtils.isTestNull(String.valueOf(transferItem.yearRate)).equals("")){
            mViewHolder.tv_text_nhll.setText("0.00%");
        }else{
            Float mFinalProgress =Float.parseFloat((transferItem.yearRate * 100)+"") ;//目标进度
            BigDecimal bd = new BigDecimal(mFinalProgress);
            mFinalProgress = bd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();//取2位小数[取值条件：四舍五入]
            if(mFinalProgress % 1 == 0){// 是这个整数，小数点后面是0
                bl_falg = "#,##0";
            }else{//不是整数，小数点后面不是0
                bl_falg = "#,##0.00";
            }
            mViewHolder.tv_text_nhll.setText(new DecimalFormat(bl_falg).format(mFinalProgress)+"%");//保留后面两位，不足用0代替
//            mViewHolder.tv_text_nhll.setText(""+mFinalProgress+"%");
        }
        mViewHolder.tv_text_sy_money.setText(StringUtils.isTestNull(transferItem.timeRemaining+"")+"个月");
        mViewHolder.tv_text_day.setText("￥"+StringUtils.formatMoney(transferItem.successAmount));
        mViewHolder.tv_text_money.setText("￥"+StringUtils.formatMoney(transferItem.projectValue));
        
//        mViewHolder.ll_layout_zritem.setTag(transferItem.transferStatus);
        if (transferItem.transferStatus.equals("2"))
        { //灰色显示
            mViewHolder.ll_layout_zritem
                .setBackgroundResource(R.color.layout_background_zr_item);//e_button_bg_off
            mViewHolder.tv_text_nhll.setTextColor(mContext.getResources().getColor(
                R.color.e_button_bg_off));
            mViewHolder.tv_text_sy_money.setTextColor(mContext.getResources().getColor(
                R.color.e_button_bg_off));
            mViewHolder.tv_text_day.setTextColor(mContext.getResources().getColor(
                R.color.e_button_bg_off));
            mViewHolder.iv_img_hint1.setImageResource(R.drawable.e_img_zr_iocnoff_03);
            mViewHolder.iv_img_hint23
                    .setImageResource(R.drawable.e_img_zr_icon_right_on_03);
        }
        
        return contentView;
    }

    class ViewHolder
    {
       LinearLayout ll_layout_zritem; //设置layout背景
       TextView tv_text_nhll;//年化利率
       TextView tv_text_sy_money;//期限
       TextView tv_text_day;//转让价格
       TextView tv_text_money;//项目价格
       ImageView iv_img_hint1,iv_img_hint23;//[项目价格的图标，是否已转让的图标]
       public ViewHolder(View contentView)
       {
           ll_layout_zritem = (LinearLayout) contentView.findViewById(R.id.ll_layout_zritem);
           tv_text_nhll= (TextView) contentView.findViewById(R.id.tv_text_nhl);
           tv_text_sy_money= (TextView) contentView.findViewById(R.id.tv_text_month);
           tv_text_day= (TextView) contentView.findViewById(R.id.tv_text_day);
           tv_text_money= (TextView) contentView.findViewById(R.id.tv_text_meony);
           iv_img_hint1 = (ImageView) contentView.findViewById(R.id.iv_img_hint1);
           iv_img_hint23 = (ImageView) contentView.findViewById(R.id.iv_img_hint23);
       }
    }

}
