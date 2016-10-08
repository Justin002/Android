package com.beyondsoft.ep2p.activity.home.adapter;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.response.RaiseInterestListResponse.RaiseInterestListItem;
import com.beyondsoft.ep2p.utils.Logs;
import com.beyondsoft.ep2p.utils.StringUtils;


public class RaiseInterestListAdapter extends BaseAdapter
{
    private Context mContext;
    private List<RaiseInterestListItem> message;
    private LayoutInflater mInflater;
    // 用来控制CheckBox的选中状况 
    private static HashMap<Integer, Boolean> isSelected;
    private String bl_falg = "#,##0.00";
    private ViewHolder mViewHolder;
    
    @SuppressLint("UseSparseArrays")
    public RaiseInterestListAdapter(Context ct, List<RaiseInterestListItem> mList)
    {
        super();
        this.mContext = ct;
        this.mInflater = (LayoutInflater) ct
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.message = mList;

        isSelected = new HashMap<Integer, Boolean>();
        // 初始化数据 
        initDate();
    }

    // 初始化isSelected的数据 
    private void initDate()
    {
        for (int i = 0; i < message.size(); i++)
        {
            isSelected.put(i, false);
        }
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
    public View getView(int position, View contentView, ViewGroup arg2)
    {
        final int mPosition=position;
        
        if (contentView == null)
        {
            mViewHolder = new ViewHolder();
            contentView = mInflater.inflate(R.layout.layout_raise_interest_item, null);
            mViewHolder.tv_image_ui = (ImageView) contentView
                    .findViewById(R.id.tv_image_ui);
            mViewHolder.tv_experience = (TextView) contentView
                    .findViewById(R.id.tv_experience);
            mViewHolder.tv_only_experience = (TextView) contentView
                    .findViewById(R.id.tv_only_experience);
            mViewHolder.tv_experience_date = (TextView) contentView
                    .findViewById(R.id.tv_experience_date);
            mViewHolder.bn_experience_sy = (CheckBox) contentView
                    .findViewById(R.id.bn_experience_sy);

            contentView.setTag(mViewHolder);
        }
        else
        {
            mViewHolder = (ViewHolder) contentView.getTag();
            mViewHolder.bn_experience_sy.setChecked(false);
        }

        final RaiseInterestListItem ListItem = (RaiseInterestListItem) getItem(position);

        if (StringUtils.isTestNull(ListItem.cardQuota + "").equals(""))
        {
            mViewHolder.tv_experience.setText("+0.00%");
        }
        else
        {
            Double mNhllProgress = ListItem.cardQuota * 100;//目标进度
            BigDecimal bd = new BigDecimal(mNhllProgress);
            mNhllProgress = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//取2位小数[取值条件：四舍五入]
            mViewHolder.tv_experience.setText("+"
                + new DecimalFormat(bl_falg).format(mNhllProgress) + "%");//保留后面两位，不足用0代替
        }

        mViewHolder.tv_experience_date.setText("还有"
            + StringUtils.isTestNull(ListItem.cardValidity + "") + "天到期");

        mViewHolder.bn_experience_sy.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                RaiseInterestListAdapter.this.onCheckBoxCheckedLsitener.getChecked(mPosition,mViewHolder.bn_experience_sy.isChecked());//回调监听
                for (Integer p : isSelected.keySet()) {
                    isSelected.put(p, false);
                }
                
                Logs.d("选择加息券3333=OnClickListener"+mViewHolder.bn_experience_sy.isChecked());
                isSelected.put(mPosition,mViewHolder.bn_experience_sy.isChecked());
                RaiseInterestListAdapter.this.notifyDataSetChanged();
            }
        });
        
        Logs.d("选择加息券=OnClickListener"+isSelected.get(mPosition));
        mViewHolder.bn_experience_sy.setChecked(isSelected.get(mPosition));

        return contentView;
    }

    class ViewHolder
    {
        ImageView tv_image_ui;
        TextView tv_experience;
        TextView tv_only_experience;//体验金使用说明
        TextView tv_experience_date; //有效时间
        CheckBox bn_experience_sy; //使用
    }
    private OnCheckBoxCheckedLsitener onCheckBoxCheckedLsitener;    
    
    public interface OnCheckBoxCheckedLsitener{
        void getChecked(int position,boolean isCheckedL);
    }
    public void setOnCheckBoxCheckedLsitener(OnCheckBoxCheckedLsitener onCheckBoxCheckedLsitener){
        this.onCheckBoxCheckedLsitener=onCheckBoxCheckedLsitener;
    }
    
    @Override  
    public boolean areAllItemsEnabled() {  
        return false;  
    }  
      
    @Override  
    public boolean isEnabled(int position) {  
        return false;  
    } 

}
