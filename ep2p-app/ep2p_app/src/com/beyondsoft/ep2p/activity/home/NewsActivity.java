package com.beyondsoft.ep2p.activity.home;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.home.adapter.NewsListAdapter;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.response.NewsCenterListResponse;
import com.beyondsoft.ep2p.model.response.NewsCenterListResponse.NewsCenterList;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.Logs;
import com.beyondsoft.ep2p.utils.StringRequest2;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;


/**
 * 
 * 消息中心
 * @ClassName:NoticeActivity 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2015年12月15日
 *
 */
public class NewsActivity extends BaseActivity
    implements
        OnItemClickListener,
        OnClickListener
{
    private Context mContext;
    private TextView tv_title, tv_title_right;
    private ImageView iv_right_falg;

    private RelativeLayout rl_layout_null;//空数据的UI
    private LinearLayout ll_wifi_off_ui;//无wifi的UI
    private TextView wifi_load_again;
//    private PullToRefreshListView mList_notice;
    private NewsListAdapter<NewsCenterList> mNoticeListAdapter;
    private List<NewsCenterList> mList = new ArrayList<NewsCenterList>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_zx);
        mContext = NewsActivity.this;
        initTitle();
        initView();
        initData();
        setListener();
    }

    private void initTitle()
    {
        tv_title = (TextView) findViewById(R.id.title_content_tv);
        tv_title.setText("消息中心");

        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
        tv_title_right.setOnClickListener(this);
        tv_title_right.setText("全部已读");
        iv_right_falg = (ImageView) findViewById(R.id.title_right);
        iv_right_falg.setVisibility(View.GONE);
    }

    private void initView()
    {
        rl_layout_null = (RelativeLayout) findViewById(R.id.rl_layout_null);

        ll_wifi_off_ui = (LinearLayout) findViewById(R.id.ll_wifi_off_ui);
        ll_wifi_off_ui.setVisibility(View.GONE);
        wifi_load_again = (TextView) findViewById(R.id.wifi_load_again);
        wifi_load_again.setOnClickListener(this);
        mList_notice = (PullToRefreshListView) findViewById(R.id.listview_other_notice);
        mList_notice.setOnItemClickListener(this);
    }

    private String mToken = "";
    private String messageId = "";
    private int pageCount = 0;

    private void initData()
    {
        mToken = CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN);
    }

    private void setListener()
    {
        // 设置一个侦听器被调用时应该刷新列表。
        mList_notice.setOnRefreshListener(new OnRefreshListener<ListView>()
        {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView)
            {
                String label = DateUtils.formatDateTime(getApplicationContext(),
                    System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
                        | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // 更新LastUpdatedLabel[时间]
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                temp_pageNum = 1;
                pageNum = "1";
                isNewsClearAdapter = 0;
                //刷新列表
                getNewsCenterListData();

            }
        });

        // 添加一个end-of-list侦听器
        mList_notice.setOnLastItemVisibleListener(new OnLastItemVisibleListener()
        {

            @Override
            public void onLastItemVisible()
            {
                Logs.d("接口总数大小："+pageCount+"\n当前页面大小："+mNoticeListAdapter.getCount()); 
                if (pageCount > mNoticeListAdapter.getCount())
                { //有下一页数据
                    addZRList();
                }
                else
                {
                    CommonUtils.toastMsgShort(mContext, "数据已全部加载完!");
                }
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        temp_pageNum = 1;
        pageNum = "1";
        isNewsClearAdapter = 0;
        getNewsCenterListData();
    }

    private String pageNum = "1", pageSize = "10";

    //获取消息中心列表
    private void getNewsCenterListData()
    {
        if (mNoticeListAdapter != null && isNewsClearAdapter == 0)
        {
            mNoticeListAdapter.ClearListData();
        }
        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("token", mToken);
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);
        connection(registerService.getStringRequest(1, URL.API_NEWS_MESSAGE_LIST, params,
            this));
    }

    //标记消息为已读接口【单条】
    private void getSystemNoticeContentRead()
    {
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("token", mToken);
        params.put("messageId", messageId);
        connection(registerService.getStringRequest(2, URL.API_NEWS_MESSAGE_SIGNREAD,
            params, this));
    }

    //标记消息为已读接口【标记全部未读消息为已读】
    private void getSystemNoticeContentALLRead()
    {
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("token", mToken);
        connection(registerService.getStringRequest(3, URL.API_NEWS_MESSAGE_ALLSIGNREAD,
            params, this));
    }

    @Override
    public void onResponse(int tag, Object values)
    {
        super.onResponse(tag, values);
        RefreshDialog.stopProgressDialog();
        switch (tag)
        {
            case 1 :
                NewsCenterListResponse mNewsCenterListResponse = (NewsCenterListResponse) StringRequest2
                        .Json2Object(values.toString(),
                            new TypeToken<NewsCenterListResponse>()
                            {
                            });
                ll_wifi_off_ui.setVisibility(View.GONE);
                if (mNewsCenterListResponse != null)
                {
                    if (isNewsClearAdapter == 1)
                    { //加载更多
                        mList.addAll( mNewsCenterListResponse.result.data);
                        mNoticeListAdapter.notifyDataSetChanged();
                        mList_notice.onRefreshComplete();
                        return;
                    }

                    if (mNewsCenterListResponse.getMessage().equals("没查到消息中心的数据"))
                    {
                        mList_notice.setVisibility(View.GONE);
                        rl_layout_null.setVisibility(View.VISIBLE);
                        return;
                    }
                    mList = mNewsCenterListResponse.result.data;
                    pageCount = mNewsCenterListResponse.result.pageCount;
                    mList_notice.setVisibility(View.VISIBLE);
                    if (mList.size() > 0 || !mList.isEmpty())
                    {
                        mNoticeListAdapter = new NewsListAdapter<NewsCenterList>(mContext,mList);
                        mList_notice.setAdapter(mNoticeListAdapter);
                    }else{
                        mList_notice.setVisibility(View.GONE);
                        rl_layout_null.setVisibility(View.VISIBLE);
                    }
                    mList_notice.onRefreshComplete();
                }
                break;
            case 2 :
                if (mNoticeListAdapter != null)
                {
                    mNoticeListAdapter.notifyDataSetChanged();
                }
                break;
            case 3 :
                getNewsCenterListData();
                if (mNoticeListAdapter != null)
                {
                    mNoticeListAdapter.notifyDataSetChanged();
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
        ll_wifi_off_ui.setVisibility(View.VISIBLE);
        //        tv_list_lines.setVisibility(View.GONE);
        mList_notice.setVisibility(View.GONE);
        rl_layout_null.setVisibility(View.GONE);
        CommonUtils.toastMsgShort(mContext, error);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
    {
        Logs.d("list_id=:" + arg2);
        if (arg2 >= 1)
        {
            arg2 = arg2 - 1;
        }
        messageId = mList.get(arg2).pid;
        getSystemNoticeContentRead();
        Intent intent = new Intent(mContext, NoticeContextActivity.class);
        intent.putExtra(Constants.NEWS_NOTICE_CHECK, 10);
        intent.putExtra("NewsCenterList_bean", mList.get(arg2 % mList.size()));
        pushActivity(intent);
    }

    @Override
    public void onClick(View arg0)
    {
        switch (arg0.getId())
        {
            case R.id.tv_title_right ://全部已经读
                
                if(mList.size()>0){
                    getSystemNoticeContentALLRead();
                }else{
                    CommonUtils.toastMsgShort(mContext, "暂时无数据!");
                }
                break;
            case R.id.wifi_load_again :
                getNewsCenterListData();
                break;

            default :
                break;
        }
    }

    private int temp_pageNum = 1;
    private int isNewsClearAdapter = 0;//  0：清理  ,1：不清理

    private void addZRList()
    {
        if (mList.size() > 0 && !mList.isEmpty())
        {
            isNewsClearAdapter = 1;
            temp_pageNum += 1;
            pageNum = String.valueOf(temp_pageNum);
            //刷新列表
            getNewsCenterListData();
        }
    }
    
}
