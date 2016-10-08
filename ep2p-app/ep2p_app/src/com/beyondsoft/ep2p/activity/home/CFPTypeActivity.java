package com.beyondsoft.ep2p.activity.home;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.LoginActivity;
import com.beyondsoft.ep2p.activity.SimpleWebActivity;
import com.beyondsoft.ep2p.activity.core.MainHolder;
import com.beyondsoft.ep2p.activity.home.adapter.CFPTypeZRListAdapter;
import com.beyondsoft.ep2p.activity.home.adapter.ListPopAdapter;
import com.beyondsoft.ep2p.activity.home.adapter.RecommendListAdapter;
import com.beyondsoft.ep2p.activity.home.adapter.RecommendList2Adapter2;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.response.CFPTypeResponse;
import com.beyondsoft.ep2p.model.response.CFPTypeResponse.CFPTypeItem;
import com.beyondsoft.ep2p.model.response.TransferResponse;
import com.beyondsoft.ep2p.model.response.TransferResponse.TransferItem;
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
 * home 理财类型
 * @ClassName:CFPTypeActivity 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2015年12月15日
 *
 */
public class CFPTypeActivity extends BaseActivity implements OnClickListener,OnItemClickListener
{
    private Context mContext;
    //title
    private TextView tv_title;
    private ImageView iv_right_falg;
    private RelativeLayout title_layout;
    private int top_falg = 0;
    //top
    private TextView tv_sort_zh, tv_sort_nhl, tv_sort_qx;
    //button
    private  PullToRefreshListView mListView_cfp;
//    private RecommendListAdapter<CFPTypeItem> mCFPTypeListAdapter;
    private RecommendList2Adapter2 <CFPTypeItem> mCFPTypeListAdapter;
    private CFPTypeZRListAdapter<TransferItem> mCFPTypeZRListAdapter;
    private List<TransferItem> mData_Transferlist = new ArrayList<TransferItem>();
    
    private int isClearAdapter = 0 ;//  0：清理  ,1：不清理
    private int isZRClearAdapter = 0 ;//  0：清理  ,1：不清理
    private String sortType = "1";//【默认是1,1:综合排序，2：年化率，3：借款期限】
    private String mSortModel ="1";//1代表降序，2代表升序
    private boolean isChenckSortModel = true;//切换 排序 类型
    private String borrowType = "3";
    private int pageIndex = 0;
    private int pageSize = 10; //每页固定显示10条数
    private int  pageCount = 0; //总条数
    private List<CFPTypeItem>  mList_cfp = new ArrayList<CFPTypeItem>();
    
    private LinearLayout ll_null_data_ui;//空数据的UI
    private LinearLayout ll_wifi_off_ui;//无wifi的UI
    private TextView wifi_load_again;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cfp_type);
        mContext = CFPTypeActivity.this;
        initTitle();
        initView();
        initData();
        setListener();
    }

    private void initTitle()
    {
        title_layout = (RelativeLayout) findViewById(R.id.title_layout);
        tv_title = (TextView) findViewById(R.id.title_content_tv);
        tv_title.setText("e计划");
        Drawable right = getResources().getDrawable(
            R.drawable.e_img_jh_jt_buttom);
        right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());
        tv_title.setCompoundDrawables(null, null, right, null);
        tv_title.setOnClickListener(this);
        
        iv_right_falg = (ImageView) findViewById(R.id.title_right);
        iv_right_falg.setVisibility(View.VISIBLE);
        iv_right_falg.setImageResource(R.drawable.bn_e_jh_right_image);
        iv_right_falg.setOnClickListener(this);
    }

    private void initView()
    {
        tv_sort_zh = (TextView) findViewById(R.id.tv_sort_zh);
        tv_sort_zh.setOnClickListener(this);
        tv_sort_nhl = (TextView) findViewById(R.id.tv_sort_nhl);
        tv_sort_nhl.setOnClickListener(this);
        tv_sort_qx = (TextView) findViewById(R.id.tv_sort_qx);
        tv_sort_qx.setOnClickListener(this);
        
        ll_null_data_ui =  (LinearLayout) findViewById(R.id.ll_null_data_ui);
        ll_wifi_off_ui =  (LinearLayout) findViewById(R.id.ll_wifi_off_ui); 
        wifi_load_again =  (TextView) findViewById(R.id.wifi_load_again); 
        wifi_load_again.setOnClickListener(this);
        
        mListView_cfp = (PullToRefreshListView) findViewById(R.id.lv_listview_cfp_info);
        mListView_cfp.setOnItemClickListener(this);
    }

    private String mToken = "";
    private void initData()
    {
        mToken = CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN);
        top_falg = getIntent().getIntExtra(Constants.EP2P, 0);
        //文字颜色
        tv_sort_zh.setTextColor(getResources().getColor(R.color.e_text_bg_yellow));

        if (top_falg == 1)
        {
            tv_title.setText(getResources().getString(R.string.cfp_type_plan1));
        }
        else if (top_falg == 2)
        {
            tv_title.setText(getResources().getString(R.string.cfp_type_invest1));
            borrowType = "1";
        }
        else if (top_falg == 3)
        {
            tv_sort_nhl.setText("转让价格");//年化率
            tv_title.setText(getResources().getString(R.string.cfp_type_prefecture1));
            getTrabsferListData();
            return;
        }
        getEplanListData();
    }

    private void setListener()
    {
        // 设置一个侦听器被调用时应该刷新列表。
        mListView_cfp.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // 更新LastUpdatedLabel[时间]
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                if (top_falg == 3)
                {
                    temp_pageNum = 1;
                    pageNum = "1";
                    zrpageSize = "10";
                    isZRClearAdapter = 0;
                    getTrabsferListData();
                }else{
                    pageIndex = 0;
                    pageSize = 10; //每页固定显示条数
                    isClearAdapter = 0;
                  //刷新列表
                   getEplanListData();
                }
                
            }
        });

        // 添加一个end-of-list侦听器
        mListView_cfp.setOnLastItemVisibleListener(new OnLastItemVisibleListener()
        {

            @Override
            public void onLastItemVisible()
            {

                if (top_falg == 3)
                {
                    Logs.d("接口总数大小："+pageCount+"\n当前页面大小："+mCFPTypeZRListAdapter.getCount()); 
                    if (pageCount > mCFPTypeZRListAdapter.getCount())
                    { //有下一页数据
                        addZRList();
                    }
                    else
                    {
                        CommonUtils.toastMsgShort(mContext, "数据已全部加载完!");
                    }
                }
                else
                {  
                    Logs.d("接口总数大小："+pageCount+"\n当前页面大小："+mCFPTypeListAdapter.getCount());                    
                    if (pageCount  > mCFPTypeListAdapter.getCount())
                    { //有下一页数据
                        addList();
                    }
                    else
                    {
                        CommonUtils.toastMsgShort(mContext, "数据已全部加载完!");
                    }
                }
            }
        });
    }
    //获取E计划 散标投资列表接口
    private void getEplanListData()
    {
        if (mCFPTypeListAdapter != null && isClearAdapter == 0)
        {
            mCFPTypeListAdapter.ClearListData();
        }
        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("sortType", sortType); //排序类型 【默认是1,1:综合排序，2：年化率，3：借款期限】
        params.put("sortModel", mSortModel); //排序方式【1:降序，2：升序】
        params.put("pageIndex", pageIndex);//当前页
        params.put("pageSize", pageSize);//页面大小
        params.put("borrowType", borrowType);//标的类型必填（1：散标，3：e计划）
        connection(registerService.getStringRequest(1, URL.EPLAN_LIST, params,this));
    }
    
    private String pageNum = "1";
    private String zrpageSize = "10";
    private String type = "1"; 
    private String desc = "true";
    //获取转让专区
    private void getTrabsferListData()
    {
        if (mCFPTypeZRListAdapter != null && isZRClearAdapter == 0)
        {
            mCFPTypeZRListAdapter.ClearListData();
        }
        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("pageNum", pageNum); //页码    默认为1 
        params.put("pageSize ", zrpageSize); //条数     默认10 
        params.put("type", type);//请求类型 1:年化率 2:转让价格 
        params.put("desc", desc);//排序类型  true:降序  false:升序 
        connection(registerService.getStringRequest(2, URL.API_TRABSFER_LIST, params,this));
    }
    
    @Override
    public void onResponse(int tag, Object values)
    {
        super.onResponse(tag, values);
        switch (tag)
        {
            case 1 ://E计划 散标投资列表
                RefreshDialog.stopProgressDialog();
               
                CFPTypeResponse mCFPTypeResponse = (CFPTypeResponse) StringRequest2
                        .Json2Object(values.toString(), new TypeToken<CFPTypeResponse>()
                        {
                        });
                if(mCFPTypeResponse!=null)
                {
                    if(isClearAdapter ==1){ //加载更多
                        mList_cfp.addAll(mCFPTypeResponse.result.list);
                        mCFPTypeListAdapter.notifyDataSetChanged();
                        mListView_cfp.onRefreshComplete();
                        return;
                    }
                    mList_cfp = mCFPTypeResponse.result.list;
                    
                    if (mList_cfp.size() < 0 || mList_cfp.isEmpty()) //返回数据集合为空
                    {
                        mListView_cfp.setVisibility(View.GONE);
                        ll_null_data_ui.setVisibility(View.VISIBLE);
                        ll_wifi_off_ui.setVisibility(View.GONE);
                        return;
                    }
                    ll_null_data_ui.setVisibility(View.GONE);
                    ll_wifi_off_ui.setVisibility(View.GONE);
                    mListView_cfp.setVisibility(View.VISIBLE);
                    pageCount = mCFPTypeResponse.result.pageCount;
                    mCFPTypeListAdapter = new RecommendList2Adapter2<CFPTypeItem>(mContext, mList_cfp,top_falg,mCFPTypeResponse.result.systime);
                    mListView_cfp.setAdapter(mCFPTypeListAdapter);
                    mListView_cfp.onRefreshComplete();
                    
                    mCFPTypeListAdapter.setOnClickListener(new RecommendList2Adapter2.OnClickListener()
                    {

                        @Override
                        public void OnClick(int flag)
                        {
                            if(!TextUtils.isEmpty(mToken)){
                                Intent intent = new Intent(mContext, ImmediatelyInvestActivity.class);
                                intent.putExtra("pid", mList_cfp.get(flag).pid);
                                intent.putExtra("borrowName", mList_cfp.get(flag).borrowName);
                                intent.putExtra(Constants.EP2P, top_falg);
                                pushActivity(intent);
                            }else{
                                //跳转登录
                                pushActivityForResult(new Intent(mContext, LoginActivity.class), 30);
                                return;
                            }
                        }
                    });
                }
               
                break;
            case 2 : //转让专区
                RefreshDialog.stopProgressDialog();
                TransferResponse mTransferResponse = (TransferResponse) StringRequest2
                        .Json2Object(values.toString(), new TypeToken<TransferResponse>()
                        {
                        });
                if(mTransferResponse!=null)
                {
                    if(isZRClearAdapter ==1){  //加载更多
                        mData_Transferlist.addAll(mTransferResponse.result.list);
                        mCFPTypeZRListAdapter.notifyDataSetChanged();
                        mListView_cfp.onRefreshComplete();
                        return;
                    }
                    mData_Transferlist =  mTransferResponse.result.list; 
                    
                    if (mData_Transferlist.size() < 0 || mData_Transferlist.isEmpty()) //返回数据集合为空
                    {
                        mListView_cfp.setVisibility(View.GONE);
                        ll_null_data_ui.setVisibility(View.VISIBLE);
                        ll_wifi_off_ui.setVisibility(View.GONE);
                        return;
                    }
                    ll_null_data_ui.setVisibility(View.GONE);
                    ll_wifi_off_ui.setVisibility(View.GONE);
                    mListView_cfp.setVisibility(View.VISIBLE);
                    pageCount = mTransferResponse.result.pageCount;
                    mCFPTypeZRListAdapter = new CFPTypeZRListAdapter<TransferItem>(mContext, mData_Transferlist);
                    mListView_cfp.setAdapter(mCFPTypeZRListAdapter);
                    
                    mListView_cfp.onRefreshComplete();
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
        CommonUtils.toastMsgShort(mContext, error);
        RefreshDialog.stopProgressDialog();
        switch (tag)
        {
            case 1 :
                ll_wifi_off_ui.setVisibility(View.VISIBLE);
                mListView_cfp.setVisibility(View.GONE);
                ll_null_data_ui.setVisibility(View.GONE);
                break;
            case 2 :
                ll_wifi_off_ui.setVisibility(View.VISIBLE);
                mListView_cfp.setVisibility(View.GONE);
                ll_null_data_ui.setVisibility(View.GONE);
                break;
            default :
                break;
        }
       
    }

    @Override
    public void onClick(View arg0)
    {
        isClearAdapter = 0;
        isZRClearAdapter = 0;
        switch (arg0.getId())
        {
            case R.id.tv_sort_zh ://"综合排序"
                tv_sort_zh.setTextColor(getResources().getColor(R.color.e_text_bg_yellow));
                tv_sort_nhl.setTextColor(getResources().getColor(R.color.e_text_bg_black));
                tv_sort_qx.setTextColor(getResources().getColor(R.color.e_text_bg_black));
                Drawable right = getResources().getDrawable(
                    R.drawable.e_img_falg_03);
                right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());
                tv_sort_nhl.setCompoundDrawables(null, null, right, null);
                tv_sort_qx.setCompoundDrawables(null, null, right, null);
                
                if(top_falg == 3){
                    pageNum = "1";
                    zrpageSize = "10";
                    type = "1";
                    desc = "true";
                    getTrabsferListData();
                    return;
                }
                pageIndex = 0;
                pageSize = 10; 
                sortType = "1";
                mSortModel = "1";
                getEplanListData();
                break;
            case R.id.tv_sort_nhl ://年化率
                tv_sort_nhl.setTextColor(getResources().getColor(R.color.e_text_bg_yellow));
                tv_sort_zh.setTextColor(getResources().getColor(R.color.e_text_bg_black));
                tv_sort_qx.setTextColor(getResources().getColor(R.color.e_text_bg_black));
                
                Drawable rightn = getResources().getDrawable(
                    R.drawable.e_img_falg_03);
                rightn.setBounds(0, 0, rightn.getMinimumWidth(), rightn.getMinimumHeight());
//                tv_sort_nhl.setCompoundDrawables(null, null, right, null);
                tv_sort_qx.setCompoundDrawables(null, null, rightn, null);
                
                if(top_falg == 3){
                    pageNum = "1";
                    zrpageSize = "10";
                    type = "2";
                    isZRCheckSortModel(tv_sort_nhl);
                    getTrabsferListData();
                    return;
                }
                pageIndex = 0;
                pageSize = 10; 
                sortType = "2";
                isCheckSortModel(tv_sort_nhl);
                getEplanListData();
                break;
            case R.id.tv_sort_qx ://期限
                tv_sort_qx.setTextColor(getResources().getColor(R.color.e_text_bg_yellow));
                tv_sort_zh.setTextColor(getResources().getColor(R.color.e_text_bg_black));
                tv_sort_nhl.setTextColor(getResources().getColor(R.color.e_text_bg_black));
                
                Drawable rightq = getResources().getDrawable(
                    R.drawable.e_img_falg_03);
                rightq.setBounds(0, 0, rightq.getMinimumWidth(), rightq.getMinimumHeight());
                tv_sort_nhl.setCompoundDrawables(null, null, rightq, null);
//                tv_sort_qx.setCompoundDrawables(null, null, rightq, null);
                if(top_falg == 3){
                    pageNum = "1";
                    zrpageSize = "10";
                    type = "3";//TODO
                    isZRCheckSortModel(tv_sort_qx);
                    getTrabsferListData();
                    return;
                }
                pageIndex = 0;
                pageSize = 10; 
                sortType = "3";
                isCheckSortModel(tv_sort_qx);
                getEplanListData();
                break;
                
            case R.id.title_content_tv :
                if(null != mMsgTypePopupWindow &&  mMsgTypePopupWindow.isShowing())
                {
                    mMsgTypePopupWindow = null;
                    mMsgTypePopupWindow.dismiss();
                }else{
                    showMsgTypePop();
                    setPopupWindowDismissDrawable();
                }
                break;
            case R.id.title_right:
//                CommonUtils.toastMsgShort(mContext, "e计划介绍专题页");
                Intent intent = new Intent();
                intent.setClass(mContext, SimpleWebActivity.class);
                intent.putExtra(SimpleWebActivity.TITLE,"e计划介绍专题页");//getString(R.string.personal_vip_level)
                intent.putExtra(SimpleWebActivity.URL, "http://www.baidu.com");//"http://www.baidu.com"
                pushActivity(intent);
                break;
            case R.id.wifi_load_again :  //无wifi  重新加载数据
                if(top_falg == 3){
                    pageNum = "1";
                    zrpageSize = "10";
                    isZRClearAdapter = 0;
                    getTrabsferListData();
                    return;
                }
                
                pageIndex = 0;
                pageSize = 10; 
                isClearAdapter = 0;
                getEplanListData();
                break;
            default :
                break;
        }

    }
    private void isCheckSortModel(TextView textView){
        if (isChenckSortModel)
        {
            mSortModel = "2";
            isChenckSortModel = false;
            Drawable right = getResources().getDrawable(
                R.drawable.e_sort_sx_03);
            right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());
            textView.setCompoundDrawables(null, null, right, null);
        }else{
            mSortModel = "1";
            isChenckSortModel = true;
            Drawable right = getResources().getDrawable(
                R.drawable.e_sort_jx_03);
            right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());
            textView.setCompoundDrawables(null, null, right, null);
        }
    }
    private void isZRCheckSortModel(TextView textView){
        if (isChenckSortModel)
        {
            desc = "true"; 
            isChenckSortModel = false;
            Drawable right = getResources().getDrawable(
                R.drawable.e_sort_jx_03);
            right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());
            textView.setCompoundDrawables(null, null, right, null);
        }else{
            desc = "false"; 
            isChenckSortModel = true;
            Drawable right = getResources().getDrawable(
                R.drawable.e_sort_sx_03);
            right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());
            textView.setCompoundDrawables(null, null, right, null);
        }
    }
    
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
    { 
        Intent intent = null;
        if(arg2>=1){
            arg2 = arg2 - 1;
        }
        if(top_falg == 3){
            String transferId = mData_Transferlist.get(arg2%mData_Transferlist.size()).transferId;
            Logs.d("transferId==:"+arg2+"\n"+ transferId);
            intent = new Intent(mContext, CreditorRightsDetailsActivity.class);
            intent.putExtra(Constants.EP2P, 3);
            intent.putExtra("transferId",transferId);
            pushActivity(intent);
        }else if(top_falg == 1){
            String pid = mList_cfp.get(arg2%mList_cfp.size()).pid;
            Logs.d("e_PID:"+ pid);
            MainHolder.Instance(mContext).setProjectInformation2ejh_pid(pid);
            intent  = new Intent(mContext, HomeProductDetailsActivity.class);
            intent.putExtra(Constants.EP2P, 1);
            intent.putExtra(Constants.EJH_PID, pid);
            pushActivity(intent);
        }else{
            String pid = mList_cfp.get(arg2%mList_cfp.size()).pid;
            Logs.d("e_PID:"+pid);
            MainHolder.Instance(mContext).setProjectInformation2ejh_pid(pid);
            MainHolder.Instance(mContext).setProjectInformation_Pid(pid);
            intent  = new Intent(mContext, HomeProductDetailsActivity.class);
            intent.putExtra(Constants.EP2P, 2);
            intent.putExtra(Constants.EJH_PID,pid);
            pushActivity(intent);
        }
        
    }

    private View popView;
    private ListPopAdapter listPopAdapter;
    private PopupWindow mMsgTypePopupWindow;
    private List<String> mPopList = new ArrayList<String>();
    @SuppressWarnings("deprecation")
    private void showMsgTypePop()
    {
     // 自定义ListView的Adapter
        if (popView == null)
        {
            popView = CFPTypeActivity.this.getLayoutInflater().inflate(
                R.layout.layout_msgtype_pop, null);
        }
        ListView listView = (ListView) popView.findViewById(R.id.type_list);
        
        if(null == listPopAdapter){
            mPopList.add(getResources().getString(R.string.cfp_type_plan1));
            mPopList.add(getResources().getString(R.string.cfp_type_invest1));
            mPopList.add(getResources().getString(R.string.cfp_type_prefecture1));
            listPopAdapter = new ListPopAdapter(mContext, mPopList);
            listView.setAdapter(listPopAdapter);
        }else{
            listPopAdapter.notifyDataSetChanged();
        }
        
        /**
         * List Itme 点击事件
         */
        listView.setOnItemClickListener(new OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                isClearAdapter = 0;
                isZRClearAdapter = 0;
                if (null != mMsgTypePopupWindow && mMsgTypePopupWindow.isShowing())
                {
                    mMsgTypePopupWindow.dismiss();
                }
                //调用接口，通过type获取对应的数据值
                switch (arg2)
                {
                    case 0 :
                        tv_sort_nhl.setText("年化率");
                        tv_title.setText(getResources().getString(R.string.cfp_type_plan1));
                        top_falg = 1;
                        borrowType = "3";
                        pageIndex = 0;
                        isClearAdapter = 0;
                        getEplanListData();
                        break;
                    case 1 :
                        tv_sort_nhl.setText("年化率");
                        tv_title.setText(getResources().getString(R.string.cfp_type_invest1));
                        top_falg = 2;
                        borrowType ="1";
                        pageIndex = 0;
                        isClearAdapter = 0;
                        getEplanListData();
                        break;
                    case 2 :
                        tv_sort_nhl.setText("转让价格");
                        tv_title.setText(getResources().getString(R.string.cfp_type_prefecture1));
                        top_falg = 3;
                        if (mCFPTypeListAdapter != null)
                        {
                            mCFPTypeListAdapter.ClearListData();
                        }
                        temp_pageNum = 1;
                        pageNum = "1";
                        isZRClearAdapter = 0;
                        getTrabsferListData();
                        break;

                    default :
                        break;
                }
            }
            
        });
        
        mMsgTypePopupWindow = new PopupWindow(popView);
        // 设置弹框的宽度为布局文件的宽
        mMsgTypePopupWindow.setWidth(LayoutParams.MATCH_PARENT);//title_layout.getWidth()
        // 高度随着内容变化
        mMsgTypePopupWindow.setHeight(LayoutParams.WRAP_CONTENT);

        mMsgTypePopupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置点击窗口外边窗口消失
        mMsgTypePopupWindow.setOutsideTouchable(true);

        // 设置此参数获得焦点，否则无法点击
        mMsgTypePopupWindow.setFocusable(true);
        mMsgTypePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener()
        {
            @Override
            public void onDismiss()
            {
                Drawable right = getResources().getDrawable(
                    R.drawable.e_img_jh_jt_buttom);
                right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());
                tv_title.setCompoundDrawables(null, null, right, null);
            }
        });

        // 下拉框显示在文本框messageType的下方
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);  
        int xPos = -mMsgTypePopupWindow.getWidth() / 2  
                + title_layout.getWidth() / 2;  
        mMsgTypePopupWindow.showAsDropDown(title_layout, xPos, 0); 
    }
    
    private void setPopupWindowDismissDrawable()
    {
        if (null != mMsgTypePopupWindow && mMsgTypePopupWindow.isShowing())
        {
            Drawable right = getResources().getDrawable(
                R.drawable.e_img_jh_jt_top);
            right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());
            tv_title.setCompoundDrawables(null, null, right, null);
            
        }
    }
    
    private void addList()
    {
        if (mList_cfp.size() > 0 && !mList_cfp.isEmpty())
        {
            isClearAdapter = 1;
            pageIndex += 1;  //页码
            //刷新列表
            getEplanListData();
        }
    }
    private int temp_pageNum = 1;
    private void addZRList()
    {
        if (mData_Transferlist.size() > 0 && !mData_Transferlist.isEmpty())
        {
            isZRClearAdapter = 1;
            temp_pageNum += 1;
            pageNum =  String.valueOf(temp_pageNum);
            //刷新列表
            getTrabsferListData();
        }
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 30)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                Logs.d("登录后，初始化");
                mToken = CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN);
            }
        }
    }    
    
}
