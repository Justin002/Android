package com.beyondsoft.ep2p.activity.home.adapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.response.ExperienceListResponse.ExperienceListItem;
import com.beyondsoft.ep2p.utils.StringUtils;


public class ExperienceListAdapter extends BaseAdapter
{
    private Context mContext;
    private List<ExperienceListItem> message;
    private LayoutInflater mInflater;
    // 用来控制CheckBox的选中状况 
    private static HashMap<Integer, Boolean> isSelected; 
    private List<String> strList = new ArrayList<String>();
    
    @SuppressLint("UseSparseArrays")
    public ExperienceListAdapter(Context ct, List<ExperienceListItem> mList)
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
            getIsSelected().put(i, false);
        }
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

    @Override
    public View getView(final int position, View contentView, ViewGroup arg2)
    {
        final ViewHolder mViewHolder;
        if (contentView == null)
        {
            mViewHolder = new ViewHolder();
            contentView = mInflater.inflate(R.layout.layout_experience_item, null);
            mViewHolder.tv_image_ui= (ImageView) contentView.findViewById(R.id.tv_image_ui);
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
            getIsSelected().put(position, false);
        }
       
        final ExperienceListItem ListItem  =(ExperienceListItem) getItem(position);
      
        mViewHolder.tv_experience.setText("￥"+StringUtils.isTestNull(ListItem.expAmount+"")+"元体验金");
        mViewHolder.tv_experience_date.setText("有效时间:"+StringUtils.isTestNull(ListItem.expireTime+""));
        
        // 监听checkBox并根据原来的状态来设置新的状态 
        mViewHolder.bn_experience_sy.setOnClickListener(new View.OnClickListener()
        {
            
            @Override
            public void onClick(View arg0)
            {
                if (Listener != null)
                {
                    if (isSelected.get(position)) { 
                        isSelected.put(position, false); 
                        setIsSelected(isSelected); 
                        if(strList.size() > 0){
                            strList.remove(ListItem.pid);
                        }
                        mViewHolder.bn_experience_sy.setBackgroundResource(R.drawable.e_img_zc_bnoff_03);
                    } else { 
                        isSelected.put(position, true); 
                        setIsSelected(isSelected); 
                        strList.add(ListItem.pid);
                        mViewHolder.bn_experience_sy.setBackgroundResource(R.drawable.e_img_zc_bnon_03);
                    } 
                    Listener.OnClick(position,strList);
                }
            }
        });
        // 根据isSelected来设置checkbox的选中状况 
        mViewHolder.bn_experience_sy.setChecked(getIsSelected().get(position)); 

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
        public void OnClick(int flag,List<String> strList);
    }
    
    public static HashMap<Integer, Boolean> getIsSelected() { 
        return isSelected; 
    } 
   
    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) { 
        ExperienceListAdapter.isSelected = isSelected; 
    } 

}
