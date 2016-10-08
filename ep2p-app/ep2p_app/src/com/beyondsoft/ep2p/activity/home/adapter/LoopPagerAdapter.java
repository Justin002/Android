package com.beyondsoft.ep2p.activity.home.adapter;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.SimpleWebActivity;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.model.response.HomeBannerResponse.BannerItem;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.Config;
import com.beyondsoft.ep2p.utils.ImageLoadOptions;
import com.beyondsoft.ep2p.utils.Logs;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;


public class LoopPagerAdapter extends PagerAdapter
{
    private static final int[] mList = {R.drawable.page_img_loop1_03,
        R.drawable.page_img_loop2_03};
//    private static final String[] mMsgList = {
//        "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=228701166,1717539300&fm=80",
//        "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=463918659,1922643138&fm=80",
//        "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=4066140439,656289359&fm=80"};
    private ArrayList<View> mView = new ArrayList<View>();
    private Context ctx;
    private DisplayImageOptions options;//设置图片参数 
    private List<BannerItem> mList_banner = null;
    private String mUrl = Config.getDomainUrl()+"/";
    
    protected ImageLoader imageLoader=ImageLoader.getInstance();
    public LoopPagerAdapter(Context context)
    {
        this.ctx = context;
        options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.page_img_loop1_03)
                .imageScaleType(ImageScaleType.EXACTLY).resetViewBeforeLoading(true)
                .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true).build();
    }
    
    public LoopPagerAdapter(Context context, List<BannerItem> List_banner)
    {
        this.ctx = context;
        options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.page_img_loop1_03)
                .imageScaleType(ImageScaleType.EXACTLY).resetViewBeforeLoading(true)
                .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true).build();
        
        mList_banner = List_banner;
    }

    @Override
    public int getCount()
    {
        if(mList_banner == null){
            return 2;
        }else{
            return mList_banner.size()>0?mList_banner.size():0;
        }
    }
    
    @Override
    public boolean isViewFromObject(View view, Object obj)
    {
        return view == obj;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position)
    {
        View view = LayoutInflater.from(ctx).inflate(R.layout.header_viewpager, null);
        BannerItem mBannerItem=  mList_banner.get(position);
        ImageView imageView = (ImageView) view.findViewById(R.id.header_imageview);
//        ImageLoader.getInstance().displayImage(mBannerItem.fileUrl, imageView, options); //网络获取图片  1
        imageLoader.displayImage(mUrl+mBannerItem.fileUrl, imageView,ImageLoadOptions.getHomeBannerPageOptions()); //网络获取图片  2
//        imageView.setImageResource(mList[position]);  //默认本地图片
//        Logs.d("广告url==:"+mUrl+mBannerItem.fileUrl);
        mView.add(view);
        container.addView(view);

        imageView.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
//                CommonUtils.toastMsgShort(ctx, "广告:" + position);
                Intent intent = new Intent();
                intent.setClass(ctx, SimpleWebActivity.class);
                intent.putExtra(SimpleWebActivity.TITLE,mList_banner.get(position).titleName);//getString(R.string.personal_vip_level)
                intent.putExtra(SimpleWebActivity.URL, mList_banner.get(position).url);//"http://www.baidu.com"
                ctx.startActivity(intent);
            }
        });
        return view;
    }

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
    
}
