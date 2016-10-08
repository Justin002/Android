package com.beyondsoft.ep2p.activity.home;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.core.MainHolder;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.response.NewsCenterListResponse.NewsCenterList;
import com.beyondsoft.ep2p.model.response.SysNoticeContentIsOneReadResponse;
import com.beyondsoft.ep2p.model.response.SysNoticeContentResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.Logs;
import com.beyondsoft.ep2p.utils.StringRequest2;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.google.gson.reflect.TypeToken;


/**
 * 
 * 系统公告详情
 * @ClassName:NoticeContextActivity
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2015年12月15日
 *
 */
public class NoticeContextActivity extends BaseActivity
{
    private Context mContext;
    private TextView tv_title;
    private ImageView iv_right_falg;

    private int show_ui = 0;
    private String Pid = "";
    private TextView tv_stitle, tv_stime, tv_notice_context;
    private NewsCenterList mNewsCenterList;
    private ProgressBar pb_barview;
    private ImageView back_btn ;
    
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0x101) {
                pb_barview.setVisibility(View.GONE);
                tv_notice_context.setText((CharSequence) msg.obj);
               }
        };
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_notice_listview_item_context);
        mContext = NoticeContextActivity.this;
        initTitle();
        initView();
        initData();
    }

    private void initTitle()
    {
        tv_title = (TextView) findViewById(R.id.title_content_tv);

        iv_right_falg = (ImageView) findViewById(R.id.title_right);
        iv_right_falg.setVisibility(View.GONE);
        
        back_btn = (ImageView) findViewById(R.id.title_left_btn);
    }

    private void initView()
    {
        tv_stitle = (TextView) findViewById(R.id.tv_stitle);
        tv_stime = (TextView) findViewById(R.id.tv_stime);
        tv_notice_context = (TextView) findViewById(R.id.tv_notice_context);
        pb_barview = (ProgressBar) findViewById(R.id.pb_barview);
        pb_barview.setVisibility(View.GONE);
    }

    private void initData()
    {
        if (getIntent() != null)
        {
            show_ui = getIntent().getIntExtra(Constants.NEWS_NOTICE_CHECK, 0);
            Pid = getIntent().getStringExtra("PID");
            Logs.d("" + Pid);
        }
        if (show_ui == 10)
        {//消息中心
            tv_title.setText("消息详情");
            //#TODO 内容未提供
            mNewsCenterList = (NewsCenterList) getIntent().getExtras().get("NewsCenterList_bean");
            if (mNewsCenterList != null)
            {
                tv_stitle.setText(StringUtils.isTestNull(mNewsCenterList.msgTitle));
                tv_stime.setText(StringUtils.isTestNull(mNewsCenterList.createTime));
                tv_notice_context.setText(StringUtils.isTestNull(mNewsCenterList.sendContent));
            }
        }
        else
        {
            tv_title.setText("公告详情");
            getSystemNoticeContentData();
        }

        mToken = CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN);
        
        if (show_ui != 10)
        {
            if (back_btn != null)
            {
                back_btn.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        pushActivity(NoticeActivity.class);//系统公告
                        finish();
                    }
                });
            }
        }
    }

    //获取系统公告详细内容接口
    private void getSystemNoticeContentData()
    {
        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("pid", Pid);
        connection(registerService.getStringRequest(1, URL.API_SYSTEM_NOTICE_CONTENT,
            params, this));
    }

    private String mToken = "";
    private String columnContentId = "",messageId = "";

    //系统公告数据是否单条已读
    private void getSystemNoticeContentRead()
    {
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("token", mToken);
        params.put("columnContentId", columnContentId);
        connection(registerService.getStringRequest(2,
            URL.API_SYSTEM_NOTICE_CONTENT_ISREAD, params, this));
    }
    
    private  String content_url = "";
    private  Spanned sp ;
    @Override
    public void onResponse(int tag, Object values)
    {
        super.onResponse(tag, values);
        RefreshDialog.stopProgressDialog();
        switch (tag)
        {
            case 1 :
                SysNoticeContentResponse mSysNoticeContentResponse = (SysNoticeContentResponse) StringRequest2
                        .Json2Object(values.toString(),
                            new TypeToken<SysNoticeContentResponse>()
                            {
                            });

                tv_stitle.setText(StringUtils
                        .isTestNull(mSysNoticeContentResponse.result.result.titleName));
                tv_stime.setText(StringUtils
                        .isTestNull(mSysNoticeContentResponse.result.result.createTime));
//                tv_notice_context.setText(StringUtils
//                        .isTestNull(mSysNoticeContentResponse.result.result.content));
                if(!TextUtils.isEmpty(mSysNoticeContentResponse.result.result.content)){
                   content_url = mSysNoticeContentResponse.result.result.content;
                   
                   pb_barview.setVisibility(View.VISIBLE);
                   t.start();
                }else{
                    tv_notice_context.setText("暂无内容!");
                }

                if (!TextUtils.isEmpty(mToken))
                {
                    columnContentId = mSysNoticeContentResponse.result.result.pid;
                    getSystemNoticeContentRead();
                }
                break;
            case 2 :
                SysNoticeContentIsOneReadResponse mSysNoticeContentIsOneReadResponse = (SysNoticeContentIsOneReadResponse) StringRequest2
                        .Json2Object(values.toString(),
                            new TypeToken<SysNoticeContentIsOneReadResponse>()
                            {
                            });

                boolean isRead = mSysNoticeContentIsOneReadResponse.result.result;
                if (isRead)
                {
                    MainHolder.Instance(mContext).setSystemRead(isRead);
//                    CommonUtils.toastMsgShort(mContext, "已读!");
                }
                break;

            default :
                break;
        }

    }

    @Override
    public void onErrorResponse(int tag, String error)
    {
        super.onErrorResponse(tag, error);
        RefreshDialog.stopProgressDialog();
        CommonUtils.toastMsgShort(mContext, error);
    }

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
