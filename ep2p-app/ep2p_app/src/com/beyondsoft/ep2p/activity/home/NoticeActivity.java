package com.beyondsoft.ep2p.activity.home;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.core.MainHolder;
import com.beyondsoft.ep2p.activity.home.adapter.NoticeListAdapter;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.response.SysNoticeContentIsAllReadResponse;
import com.beyondsoft.ep2p.model.response.SysNoticeResponse;
import com.beyondsoft.ep2p.model.response.SysNoticeResponse.SysNoticeItem;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.Logs;
import com.beyondsoft.ep2p.utils.StringRequest2;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;


/**
 * 
 * 系统公告
 * @ClassName:NoticeActivity 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2015年12月15日
 *
 */
public class NoticeActivity extends BaseActivity
    implements
        OnItemClickListener,
        OnClickListener
{
    private Context mContext;
    private TextView tv_title, tv_title_right;
    private ImageView iv_right_falg;

    private static final String webTag = "P2P_SYS_NOTICE";
    private PullToRefreshListView mList_notice;
    private NoticeListAdapter<SysNoticeItem> mNoticeListAdapter;
    private List<SysNoticeItem> mList = new ArrayList<SysNoticeItem>();
    private int pageCount = 0; //总条数
    private int isClearAdapter = 0;//  0：清理  ,1：不清理
    private LinearLayout ll_null_data_ui;//空数据的UI
    private LinearLayout ll_wifi_off_ui;//无wifi的UI
    private TextView wifi_load_again;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        mContext = NoticeActivity.this;
        initTitle();
        initView();
        initData();
        setListener();
    }

    private void initTitle()
    {
        tv_title = (TextView) findViewById(R.id.title_content_tv);
        tv_title.setText("系统公告");

        ll_null_data_ui = (LinearLayout) findViewById(R.id.ll_null_data_ui);
        ll_wifi_off_ui = (LinearLayout) findViewById(R.id.ll_wifi_off_ui);

        wifi_load_again = (TextView) findViewById(R.id.wifi_load_again);
        wifi_load_again.setOnClickListener(this);
        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
        tv_title_right.setOnClickListener(this);
        tv_title_right.setText("全部已读");
        iv_right_falg = (ImageView) findViewById(R.id.title_right);
        iv_right_falg.setVisibility(View.GONE);
    }

    private void initView()
    {
        mList_notice = (PullToRefreshListView) findViewById(R.id.listview_other_notice);
        mList_notice.setOnItemClickListener(this);
    }

    private void initData()
    {
        mToken = CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN);
        getSystemNoticeListData();
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        if(MainHolder.Instance(mContext).isSystemRead()){
            
            pageIndex = 0;
            isClearAdapter = 0;
            getSystemNoticeListData();
        }
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
                pageIndex = 0; 
                isClearAdapter = 0;
                getSystemNoticeListData();
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
                    addList();
                }
                else
                {
                    CommonUtils.toastMsgShort(mContext, "数据已全部加载完!");
                }
            }
        });
    }

    private int pageIndex = 0; //分页条数
    private int pageSize = 10;//当前页显示条数
    private String isHome = "1"; //必填0: 首页显示 非0：不在首页显示

    //获取系统公告列表接口
    private void getSystemNoticeListData()
    {
        if (mNoticeListAdapter != null && isClearAdapter == 0)
        {
            mNoticeListAdapter.ClearListData();
        }
        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("webTag", webTag);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
        if (!TextUtils.isEmpty(mToken))
        {
            params.put("token", mToken);
        }
        params.put("isHome", isHome);
        connection(registerService.getStringRequest(1, URL.API_SYSTEM_NOTICE, params,
            this));
    }

    private String mPid = "";

    @Override
    public void onResponse(int tag, Object values)
    {
        super.onResponse(tag, values);
        switch (tag)
        {
            case 1 :
                RefreshDialog.stopProgressDialog();
                SysNoticeResponse mSysNoticeResponse = (SysNoticeResponse) StringRequest2
                        .Json2Object(values.toString(),
                            new TypeToken<SysNoticeResponse>()
                            {
                            });
                if (mSysNoticeResponse != null)
                {
                    if(isClearAdapter ==1){ //加载更多
                        mList.addAll(mSysNoticeResponse.result.result);
                        mNoticeListAdapter.notifyDataSetChanged();
                        mList_notice.onRefreshComplete();
                        return;
                    }
                    mList = mSysNoticeResponse.result.result;
                    pageCount = mSysNoticeResponse.result.pageCount;
                    if (mList.size() < 0 || mList.isEmpty())
                    {
                        mList_notice.setVisibility(View.GONE);
                        ll_null_data_ui.setVisibility(View.VISIBLE);
                        return;
                    }
                    mList_notice.setVisibility(View.VISIBLE);
                    mNoticeListAdapter = new NoticeListAdapter<SysNoticeItem>(mContext,
                        mList);
                    mList_notice.setAdapter(mNoticeListAdapter);
                    mList_notice.onRefreshComplete();
                }
                break;
            case 2 : //全部已读
                RefreshDialog.stopProgressDialog();
                SysNoticeContentIsAllReadResponse mSysNoticeContentIsReadResponse = (SysNoticeContentIsAllReadResponse) StringRequest2
                        .Json2Object(values.toString(),
                            new TypeToken<SysNoticeContentIsAllReadResponse>()
                            {
                            });
                boolean isRead = mSysNoticeContentIsReadResponse.result.result;
                MainHolder.Instance(mContext).setSystemRead(isRead);
                Logs.d("是否已读=" + isRead);
                if(isRead){
                    getSystemNoticeListData();
                    
                    if(mNoticeListAdapter!=null){
                        mNoticeListAdapter.notifyDataSetChanged();
                    }
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
        ll_wifi_off_ui.setVisibility(View.VISIBLE);
        mList_notice.setVisibility(View.GONE);
        ll_null_data_ui.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
    { 
        Logs.d("list_id=:" + arg2);
        if(arg2 >= 1){
            arg2 = arg2 - 1;
        }
        mPid = mList.get(arg2).pid;
        Logs.d("PID=:" + mPid);
        Intent intent = new Intent(mContext, NoticeContextActivity.class);
        intent.putExtra("PID", mPid);
        pushActivity(intent);
    }

    @Override
    public void onClick(View arg0)
    {
        switch (arg0.getId())
        {
            case R.id.tv_title_right :
                //CommonUtils.toastMsgShort(mContext, "全部已经读");
                if (mList.size() > 0)
                {
                    if(!TextUtils.isEmpty(mToken)){
                        getSystemNoticeContentAllRead();
                    }else{
                        CommonUtils.toastMsgShort(mContext,
                            mContext.getResources().getString(R.string.login_hint));
                    }
                  
                }
                break;
            case R.id.wifi_load_again :
                getSystemNoticeListData();
                break;
            default :
                break;
        }
    }

    private String mToken = "";

    //系统公告数据是否全部已读
    private void getSystemNoticeContentAllRead()
    {
        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("token", mToken);
        connection(registerService.getStringRequest(2,
            URL.API_SYSTEM_NOTICE_CONTENT_ISALLREAD, params, this));
    }

    private void addList()
    {
        if (mList.size() > 0 && !mList.isEmpty())
        {
            isClearAdapter = 1;
            pageIndex += 1;
            //刷新列表
            getSystemNoticeListData();
        }
    }
}
