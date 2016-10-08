package com.beyondsoft.ep2p.activity.home.adapter;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.home.ImagePagerActivity;
import com.beyondsoft.ep2p.model.response.FkRiskcontrolResponse.RiskcontrolItem;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.Config;
import com.beyondsoft.ep2p.utils.ImageLoadOptions;
import com.beyondsoft.ep2p.utils.Logs;
import com.nostra13.universalimageloader.core.ImageLoader;


public class FkImgAdapter extends BaseAdapter
{
    private Context ct;
    private List<RiskcontrolItem> data;
    public static final String EXTRA_IMAGE_INDEX = "image_index"; 
    public static final String EXTRA_IMAGE_URLS = "image_urls";
    private ArrayList<String> urls = new ArrayList<String>();
    protected ImageLoader imageLoader=ImageLoader.getInstance();
    private String mUrl = Config.getDomainUrl()+"/";
    
    public FkImgAdapter(Context ct, List<RiskcontrolItem> checkedList)
    {
        this.ct = ct;
        this.data = checkedList;
    }

    @Override
    public int getCount()
    {
        return data.size() > 0 ? data.size() : 0;
    }

    @Override
    public Object getItem(int position)
    {
        return data.get(position%data.size());
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {

        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(ct).inflate(
                R.layout.layout_item_kf_station, null);
            viewHolder = new ViewHolder();

            viewHolder.iv_material = (ImageView) convertView.findViewById(R.id.pinglun_img);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final RiskcontrolItem mRiskcontrolItem = (RiskcontrolItem) getItem(position%data.size());
        
        imageLoader.displayImage(mUrl+mRiskcontrolItem.fileUrl, viewHolder.iv_material,ImageLoadOptions.getOptions()); //网络获取图片
        viewHolder.iv_material.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                //点击放大图片
//                CommonUtils.toastMsgShort(ct, "" + position);
                Logs.d("点击放大图片url:"+mRiskcontrolItem.fileUrl+"完整路径:"+ mUrl+mRiskcontrolItem.fileUrl);
                urls.add(mUrl+mRiskcontrolItem.fileUrl);
//                urls.add("http://img.my.csdn.net/uploads/201410/19/1413698865_3560.jpg");
                imageBrower(position, urls);
            }
        });

        return convertView;
    }


    /**
     * 打开图片查看器
     * 
     * @param position
     * @param urls2
     */
    public void imageBrower(int position, ArrayList<String> urls2) {
        Intent intent = new Intent(ct, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(FkImgAdapter.EXTRA_IMAGE_URLS, urls2);
        intent.putExtra(FkImgAdapter.EXTRA_IMAGE_INDEX, position);
        ct.startActivity(intent);
    }
    
    static class ViewHolder
    {
        String id;
        ImageView iv_material;
    }
}