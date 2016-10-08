package com.beyondsoft.ep2p.activity.home.fragment;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.text.Html.ImageGetter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseFragment;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.core.MainHolder;
import com.beyondsoft.ep2p.model.response.TrabserProductInfoResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.StringRequest2;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.google.gson.reflect.TypeToken;


/**
 * 
 * 项目描述
 * @ClassName:ProjectInformationFragment 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2015年12月18日
 *
 */
public class ProjectInformation2ejhFragment extends BaseFragment
{
    private View mParent;
    private TextView tv_po_content;
    private ProgressBar pb_barview;
    private String  content_url = "";

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        mParent = inflater.inflate(R.layout.fragment_project_2ejh_information, null);
//        tv_po_title = (TextView) mParent.findViewById(R.id.tv_po_title);
        tv_po_content = (TextView) mParent.findViewById(R.id.tv_po_content);
        
        pb_barview = (ProgressBar) mParent.findViewById(R.id.pb_barview);
        pb_barview.setVisibility(View.GONE);
        return mParent;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private String mPid = "";
    private void initData()
    {
        mPid = MainHolder.Instance(mContext).getProjectInformation2ejh_pid();
        getTrabsferProductData();
    }

    //获取e计划 项目描述
    private void getTrabsferProductData()
    {
//        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("pid", mPid); //标的id 
        connection(registerService.getStringRequest(2, URL.API_EPLAN_PRODUCT, params,
            this,mContext));
    }
    
    @Override
    public void onResponse(int tag, Object values)
    {
        super.onResponse(tag, values);
        RefreshDialog.stopProgressDialog();
        TrabserProductInfoResponse mTrabserProductInfoResponse = (TrabserProductInfoResponse) StringRequest2
                .Json2Object(values.toString(),
                    new TypeToken<TrabserProductInfoResponse>()
                    {
                    });
        //        tv_po_content.setText(StringUtils.isTestNull(mTrabserProductInfoResponse.result.projectDescription));
        if (!TextUtils.isEmpty(mTrabserProductInfoResponse.result.projectDescription))
        {
//            tv_po_content.setText(Html.fromHtml(
//                mTrabserProductInfoResponse.result.projectDescription).toString());
            
            content_url = mTrabserProductInfoResponse.result.projectDescription;
            
            pb_barview.setVisibility(View.VISIBLE);
            t.start();
        }else{
            tv_po_content.setText("暂无数据!");
        }
        
    }
    @Override
    public void onErrorResponse(int tag, String error)
    {
        super.onErrorResponse(tag, error);
        RefreshDialog.stopProgressDialog();
        CommonUtils.toastMsgShort(mContext, error);
    }
    
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0x101) {
                pb_barview.setVisibility(View.GONE);
                tv_po_content.setText((CharSequence) msg.obj);
               }
        };
    };
    
    // 因为从网上下载图片是耗时操作 所以要开启新线程
    Thread t = new Thread(new Runnable()
    {
        Message msg = Message.obtain();

        @Override
        public void run()
        {
            // TODO Auto-generated method stub
            /**
             * 要实现图片的显示需要使用Html.fromHtml的一个重构方法：public static Spanned
             * fromHtml (String source, Html.ImageGetterimageGetter,
             * Html.TagHandler
             * tagHandler)其中Html.ImageGetter是一个接口，我们要实现此接口，在它的getDrawable
             * (String source)方法中返回图片的Drawable对象才可以。
             */
            ImageGetter imageGetter = new ImageGetter()
            {
                @Override
                public Drawable getDrawable(String source)
                {
                    // TODO Auto-generated method stub
                    java.net.URL url;
                    Drawable drawable = null;
                    try
                    {
                        url = new java.net.URL(source);
                        drawable = Drawable.createFromStream(url.openStream(), null);
                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight());
                    }
                    catch (MalformedURLException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    catch (IOException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    return drawable;
                }
            };
            CharSequence test = Html.fromHtml(content_url, imageGetter, null);
            msg.what = 0x101;
            msg.obj = test;
            mHandler.sendMessage(msg);
        }
    });

}
