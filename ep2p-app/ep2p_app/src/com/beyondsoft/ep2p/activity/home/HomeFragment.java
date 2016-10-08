package com.beyondsoft.ep2p.activity.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseFragment;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.LoginActivity;
import com.beyondsoft.ep2p.activity.SimpleWebActivity;
import com.beyondsoft.ep2p.activity.core.MainHolder;
import com.beyondsoft.ep2p.activity.core.MyHandler;
import com.beyondsoft.ep2p.activity.home.adapter.HomeRecommendListAdapter;
import com.beyondsoft.ep2p.activity.home.adapter.LoopPagerAdapter;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.response.HomeBannerResponse;
import com.beyondsoft.ep2p.model.response.HomeBannerResponse.BannerItem;
import com.beyondsoft.ep2p.model.response.HomeRecommendProjectsResponse;
import com.beyondsoft.ep2p.model.response.HomeRecommendProjectsResponse.HomeRecommendProjectsItem;
import com.beyondsoft.ep2p.model.response.IsNewsMessageResponse;
import com.beyondsoft.ep2p.model.response.SysNoticeResponse;
import com.beyondsoft.ep2p.model.response.SysNoticeResponse.SysNoticeItem;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.DisplayUtil;
import com.beyondsoft.ep2p.utils.Logs;
import com.beyondsoft.ep2p.utils.StringRequest2;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.beyondsoft.ep2p.view.ScrollTextViewLayout;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * 首页
 * @ClassName:HomeFragment2 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2015年12月21日
 *
 */
public class HomeFragment extends BaseFragment
    implements
        OnClickListener,
        OnItemClickListener
{
    //title
    private TextView tv_left_falg = null, tv_title = null;
    private ImageView title_left_btn, iv_right_falg = null;
    private TextView tv_image_right;

    //center
    private TextView tv_CFPtype1 = null, tv_CFPtype2 = null, tv_CFPtype3 = null;
    private int CFPtype1 = 1, CFPtype2 = 2, CFPtype3 = 3;//[1:e计划，2：散标，3：转让专区]

    private View view = null;
    private LinearLayout act_loop_view_dot_layout;
    private LoopViewPager mLoopViewPage = null;
    private LoopPagerAdapter mLoopPagerAdapter = null;
    private LinearLayout ll_notice_portal;
//    private AutoTextView tv_notice_portal;
    private ScrollTextViewLayout mutiScrollText;

    private ListView mListview_other_recommend = null;
    private HomeRecommendListAdapter<HomeRecommendProjectsItem> mRecommendListAdapter = null;
    private List<HomeRecommendProjectsItem> mList_Recommend= new ArrayList<HomeRecommendProjectsItem>();

    //bottonm
    private TextView tv_bottonm_invest, tv_bottonm_guarantee, tv_bottonm_safety,
            tv_bottonm_escort;
    private List<SysNoticeItem> mList = new ArrayList<SysNoticeItem>();
    private List<BannerItem> mList_banner = new ArrayList<BannerItem>();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.activity_home_content, container, false);
       
        Logs.d("DisplayUtil:===="+ DisplayUtil.px2dip(mContext,80)+"\n==px2sp:"+DisplayUtil.px2sp(mContext, 38));//80px=40dip
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        initView();
        inintData();
    }

    private View view_heads = null, view_footer;

    private void initView()
    {
        //Title
        title_left_btn = (ImageView) view.findViewById(R.id.title_left_btn);
        title_left_btn.setVisibility(View.GONE);

        tv_left_falg = (TextView) view.findViewById(R.id.tv_title_left_text);
        tv_left_falg.setText("新手专享");
        tv_left_falg.setOnClickListener(this);

        tv_image_right =  (TextView) view.findViewById(R.id.tv_image_right);
//        tv_image_right.setVisibility(View.VISIBLE);//有未读消息  显示图标
        tv_title = (TextView) view.findViewById(R.id.title_content_tv);
        tv_title.setText("首页");
        iv_right_falg = (ImageView) view.findViewById(R.id.title_right);
        iv_right_falg.setVisibility(View.VISIBLE);
        iv_right_falg.setImageResource(R.drawable.page_img_title_right_03);
        iv_right_falg.setOnClickListener(this);

        //加载头部的布局文件
        view_heads = getActivity().getLayoutInflater().inflate(
            R.layout.activity_home_heads, null);
        //加载底部的布局
        view_footer = getActivity().getLayoutInflater().inflate(
            R.layout.activity_home_footer, null);

        //center
        //系统公告
        ll_notice_portal= (LinearLayout) view_heads.findViewById(R.id.ll_notice_portal);
//        tv_notice_portal = (AutoTextView) view_heads.findViewById(R.id.tv_notice_portal);
        mutiScrollText =  (ScrollTextViewLayout) view_heads.findViewById(R.id.mutil_text_layout);
        ll_notice_portal.setOnClickListener(this);
        //理财类型[e计划，散标投资，转让专区]
        tv_CFPtype1 = (TextView) view_heads.findViewById(R.id.tv_etype_jihua);
        tv_CFPtype1.setOnClickListener(this);
        tv_CFPtype2 = (TextView) view_heads.findViewById(R.id.tv_etype_sanbiao);
        tv_CFPtype2.setOnClickListener(this);
        tv_CFPtype3 = (TextView) view_heads.findViewById(R.id.tv_etype_zhuanrang);
        tv_CFPtype3.setOnClickListener(this);

        act_loop_view_dot_layout = (LinearLayout) view_heads
                .findViewById(R.id.act_loop_view_dot_layout);
        mLoopViewPage = (LoopViewPager) view_heads.findViewById(R.id.act_login_banner);

        //主的ListView
        mListview_other_recommend = (ListView) view
                .findViewById(R.id.listview_other_recommend);
        mListview_other_recommend.setOnItemClickListener(this);

        //buttom 
        tv_bottonm_invest = (TextView) view_footer.findViewById(R.id.tv_bottonm_invest);
        tv_bottonm_invest.setOnClickListener(this);
        tv_bottonm_guarantee = (TextView) view_footer
                .findViewById(R.id.tv_bottonm_guarantee);
        tv_bottonm_guarantee.setOnClickListener(this);
        tv_bottonm_safety = (TextView) view_footer.findViewById(R.id.tv_bottonm_safety);
        tv_bottonm_safety.setOnClickListener(this);
        tv_bottonm_escort = (TextView) view_footer.findViewById(R.id.tv_bottonm_escort);
        tv_bottonm_escort.setOnClickListener(this);
    }

    private MyHandler myHandler =null;
    private void inintData()
    {
        myHandler = MainHolder.Instance(mContext).getHandle();
        setCFPUITextColor();
//        mLoopPagerAdapter = new LoopPagerAdapter(getActivity()); //#TODO  默认逻辑
//        mLoopViewPage.setAdapter(mLoopPagerAdapter);
//        mLoopViewPage.setDotParent(act_loop_view_dot_layout);

        mListview_other_recommend.addHeaderView(view_heads);
        mListview_other_recommend.addFooterView(view_footer);
        
        mRecommendListAdapter = new HomeRecommendListAdapter<HomeRecommendProjectsItem>(getActivity(),
                mList_Recommend);
            mListview_other_recommend.setAdapter(mRecommendListAdapter);
        
        mToken = CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN);
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        //刷新UI接口的全局MyHandler
        myHandler.setOnClickListener(new MyHandler.OnMyHandlerClickListener()
        {
            
            @Override
            public void OnHandlerClick()
            {
                mToken = CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN);
                Logs.d("home = 刷新UI" + mToken);
                
                //获取横幅广告接口数据
                getBannerData();
                
                if (mList_Recommend != null && mList_Recommend.size() <= 0)
                {
                    getisHomeRecommendProjectsData();
                }
               
                if(!TextUtils.isEmpty(mToken)){
                    getisNewMessageData();
                }else{
                    if (mList != null && mList.size() <= 0)
                    {
                        getSystemNoticeListData();
                    }
                }
            }
        });
    }

    @Override
    public void onResponse(int tag, Object values)
    {
        super.onResponse(tag, values);
        switch (tag)
        {
            case 4 : //系统公告
                SysNoticeResponse mSysNoticeResponse = (SysNoticeResponse) StringRequest2
                        .Json2Object(values.toString(),
                            new TypeToken<SysNoticeResponse>()
                            {
                            });
                mList = mSysNoticeResponse.result.result;
                if(isHome.equals("0") && mList.size() > 0){
                    ll_notice_portal.setVisibility(View.VISIBLE);
                    
                    //上下滚动的TextView
                    List<String> text_sx = new ArrayList<String>();
                    String [] str_textArr = null;
                    for (int i = 0; i < mList.size(); i++)
                    {
                        text_sx.add(""+mList.get(i).titleName);
                    }
                    
                    final int size =  text_sx.size();
                    str_textArr = (String[])text_sx.toArray(new String[size]);
                    mutiScrollText.setTextArray(str_textArr);
                }else{
                    ll_notice_portal.setVisibility(View.GONE);
                }
                
//                getisNewMessageData();
                break;
            case 5 : //是否有新的消息中心数据
                RefreshDialog.stopProgressDialog();
                IsNewsMessageResponse mIsNewsMessageResponse = (IsNewsMessageResponse) StringRequest2
                        .Json2Object(values.toString(),
                            new TypeToken<IsNewsMessageResponse>()
                            {
                            });
                if (mIsNewsMessageResponse != null)
                {
                    //flag =1 有未读消息  0没有未读消息
                    if (mIsNewsMessageResponse.result.flag != 1)
                    {
                        tv_image_right.setVisibility(View.GONE);
                    }
                    else
                    {
                        tv_image_right.setVisibility(View.VISIBLE);//有未读消息  显示图标
                    }
                }  
                
                if (mList != null && mList.size() <= 0)
                {
                    getSystemNoticeListData();
                }
                break;
            case 6: //推荐列表
                RefreshDialog.stopProgressDialog();
                HomeRecommendProjectsResponse mHomeRecommendProjectsResponse = (HomeRecommendProjectsResponse) StringRequest2
                        .Json2Object(values.toString(),
                            new TypeToken<HomeRecommendProjectsResponse>()
                            {
                            });

                if (mHomeRecommendProjectsResponse != null)
                {
                    mList_Recommend = mHomeRecommendProjectsResponse.result.list;

                    mRecommendListAdapter = new HomeRecommendListAdapter<HomeRecommendProjectsItem>(getActivity(),
                        mList_Recommend,mHomeRecommendProjectsResponse.result.systime);
                    mListview_other_recommend.setAdapter(mRecommendListAdapter);

                    mRecommendListAdapter
                            .setOnClickListener(new HomeRecommendListAdapter.OnClickListener()
                            {

                                @Override
                                public void OnClick(int flag)
                                {
                                    if (TextUtils.isEmpty(mToken))
                                    {
                                        //跳转登录
                                        pushActivityForResult(new Intent(mContext,
                                            LoginActivity.class), 30);
                                        return;
                                    }
                                    
                                    Logs.d("pid=:"+mList_Recommend.get(flag).pid+"home=ID:"+flag);
                                    Intent intent = new Intent(mContext, ImmediatelyInvestActivity.class);
                                    intent.putExtra("pid", mList_Recommend.get(flag).pid);
                                    intent.putExtra("borrowName", mList_Recommend.get(flag).borrowTypeName);
                                    if(flag == 0){
                                        intent.putExtra(Constants.EP2P, 1);
                                    }else{
                                        intent.putExtra(Constants.EP2P, 2);
                                    }
                                    pushActivity(intent);
                                }
                            });
                }
                break;
                
            case 7: //获取 广告、banner
                RefreshDialog.stopProgressDialog();
                HomeBannerResponse mHomeBannerResponse = (HomeBannerResponse) StringRequest2
                .Json2Object(values.toString(),
                    new TypeToken<HomeBannerResponse>()
                    {
                    });
                if (mHomeBannerResponse != null)
                {
                    if(mList_banner!=null&&mList_banner.size()>0){
                        mList_banner.clear();
                        mLoopPagerAdapter.notifyDataSetChanged();
                        Logs.d("start接口数据清空"+mList_banner.size());
                    }
                    
                    mList_banner = mHomeBannerResponse.result.relist;
                    if(mList_banner.size() > 0){
                        Logs.d("ends接口数据清空"+mList_banner.size());
                        mLoopPagerAdapter = new LoopPagerAdapter(getActivity(), mList_banner);
                        mLoopViewPage.setAdapter(mLoopPagerAdapter);
                        mLoopViewPage.setDotParent(act_loop_view_dot_layout);
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
    }
    
    private void setCFPUITextColor()
    {
        SpannableStringBuilder builder = new SpannableStringBuilder(tv_CFPtype1.getText()
                .toString());
        ForegroundColorSpan yellowSpan1 = new ForegroundColorSpan(getResources()
                .getColor(R.color.e_text_bg_black));
        ForegroundColorSpan yellowSpan2 = new ForegroundColorSpan(getResources()
                .getColor(R.color.e_text_bg_huise_black));
        builder.setSpan(yellowSpan1, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(yellowSpan2, 3, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_CFPtype1.setText(builder);

        SpannableStringBuilder builder1 = new SpannableStringBuilder(tv_CFPtype2
                .getText().toString());
        ForegroundColorSpan yellowSpan1_2 = new ForegroundColorSpan(getResources()
                .getColor(R.color.e_text_bg_black));
        ForegroundColorSpan yellowSpan2_2 = new ForegroundColorSpan(getResources()
                .getColor(R.color.e_text_bg_huise_black));
        builder1.setSpan(yellowSpan1_2, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder1.setSpan(yellowSpan2_2, 4, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_CFPtype2.setText(builder1);

        SpannableStringBuilder builder2 = new SpannableStringBuilder(tv_CFPtype3
                .getText().toString());
        ForegroundColorSpan yellowSpan1_3 = new ForegroundColorSpan(getResources()
                .getColor(R.color.e_text_bg_black));
        ForegroundColorSpan yellowSpan2_3 = new ForegroundColorSpan(getResources()
                .getColor(R.color.e_text_bg_huise_black));
        builder2.setSpan(yellowSpan1_3, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder2.setSpan(yellowSpan2_3, 4, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_CFPtype3.setText(builder2);
    }

    @Override
    public void onClick(View arg0)
    {
        Intent intent = null;
        switch (arg0.getId())
        {
            case R.id.tv_title_left_text :
                pushActivity(RegisterActivity.class); //新手专享
                break;
            case R.id.title_right :
                if(!TextUtils.isEmpty(mToken)){
                    pushActivity(NewsActivity.class); //消息中心
                }else{
                    //跳转登录
                    pushActivityForResult(new Intent(mContext, LoginActivity.class), 30);
                    return;
                }
                break;
            case R.id.ll_notice_portal :
//                 pushActivity(NoticeActivity.class);//系统公告
                 //#TODO 更改  直接跳转到详情中
                if(MainHolder.Instance(mContext).getSystemNotice_Index()>0){
                  int mIndex = MainHolder.Instance(mContext).getSystemNotice_Index() - 1;
                  String mPid = mList.get(mIndex).pid;
                  Logs.d("PID=:" + mPid+"系统公告详情索引:"+mIndex);
                  intent = new Intent(mContext, NoticeContextActivity.class);
                  intent.putExtra("PID", mPid);
                  pushActivity(intent);
                }
                break;
            case R.id.tv_etype_jihua :
                intent = new Intent(getActivity(), CFPTypeActivity.class);
                intent.putExtra(Constants.EP2P, CFPtype1);
                pushActivity(intent);
                break;
            case R.id.tv_etype_sanbiao :
                intent = new Intent(getActivity(), CFPTypeActivity.class);
                intent.putExtra(Constants.EP2P, CFPtype2);
                pushActivity(intent);
                break;
            case R.id.tv_etype_zhuanrang :
                intent = new Intent(getActivity(), CFPTypeActivity.class);
                intent.putExtra(Constants.EP2P, CFPtype3);
                pushActivity(intent);
                break;
            case R.id.tv_bottonm_invest :
                intent = new Intent();
                intent.setClass(mContext, SimpleWebActivity.class);
                intent.putExtra(SimpleWebActivity.TITLE,"3亿投资");//getString(R.string.personal_vip_level)
                intent.putExtra(SimpleWebActivity.URL, "http://www.baidu.com");
                pushActivity(intent);
                break;
            case R.id.tv_bottonm_guarantee :
                intent = new Intent();
                intent.setClass(mContext, SimpleWebActivity.class);
                intent.putExtra(SimpleWebActivity.TITLE,"多重保障");//getString(R.string.personal_vip_level)
                intent.putExtra(SimpleWebActivity.URL, "http://www.baidu.com");
                pushActivity(intent);
                break;
            case R.id.tv_bottonm_safety :
                intent = new Intent();
                intent.setClass(mContext, SimpleWebActivity.class);
                intent.putExtra(SimpleWebActivity.TITLE,"交易安全");//getString(R.string.personal_vip_level)
                intent.putExtra(SimpleWebActivity.URL, "http://www.baidu.com");
                pushActivity(intent);
                break;
            case R.id.tv_bottonm_escort :
                intent = new Intent();
                intent.setClass(mContext, SimpleWebActivity.class);
                intent.putExtra(SimpleWebActivity.TITLE,"光大护航");//getString(R.string.personal_vip_level)
                intent.putExtra(SimpleWebActivity.URL, "http://www.baidu.com");
                pushActivity(intent);
                break;
            default :
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
    {
        //是否有网络
        if(!CommonUtils.isNetworkAvailable(mContext)){
//            CommonUtils.toastMsgShortForStyle(mContext, "没有可用的网络！");
            CommonUtils.toastMsgShort(mContext,  "没有可用的网络！");
            return;
        }
        if(arg2>0){
            arg2 = arg2 -1;
        }
        
        String e_pid= mList_Recommend.get(arg2%mList_Recommend.size()).pid;
        Logs.d("首页:e_PID:"+ e_pid+"集合的索引："+arg2);
        MainHolder.Instance(mContext).setProjectInformation_Pid(e_pid);
        Intent intent  = new Intent(mContext, HomeProductDetailsActivity.class);
        if(arg2 == 0){
            MainHolder.Instance(mContext).setProjectInformation2ejh_pid(e_pid);
            intent.putExtra(Constants.EP2P, 1);
        }else{
            intent.putExtra(Constants.EP2P, 2);
        }
        intent.putExtra(Constants.EJH_PID, e_pid);
        intent.putExtra("index_data", arg2+5);
        pushActivity(intent);
    }
    private static final String System_webTag = "P2P_SYS_NOTICE",Banner_webTag = "api_adv_homePage_t_1";
    private int pageIndex = 0; //分页条数
    private int pageSize = 4;//当前页显示条数
    private String isHome = "0"; //必填0: 首页显示 ,非0：不在首页显示
    //获取系统公告列表接口
    private void getSystemNoticeListData()
    {
//        RefreshDialog.startProgressDialog(mContext, "",true);
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("webTag", System_webTag);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);  //如果isHome 值为0，则不需要传pageIndex pageSize 参数
        if(!TextUtils.isEmpty(mToken)){
            params.put("token", mToken);
        }
        params.put("isHome", isHome);
        connection(registerService.getStringRequest(4, URL.API_SYSTEM_NOTICE, params,this,mContext));
    }
    
    private String mToken = "";
    //是否有新消息中心数据
    private void getisNewMessageData()
    {
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("token", mToken);//必填
        connection(registerService.getStringRequest(5, URL.API_ISNEWMESSAGE, params,this,mContext));
    }    
    //获取推荐项目list数据
    private void getisHomeRecommendProjectsData()
    {
        RefreshDialog.startProgressDialog(mContext, "",true);
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
//        params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
        connection(registerService.getStringRequest(6, URL.API_HOME_RecommendProjects, params,this,mContext));
    }  
    
    //获取横幅广告接口数据
    private void getBannerData()
    {
//        RefreshDialog.startProgressDialog(mContext, "",true);
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("webTag", Banner_webTag);
        connection(registerService.getStringRequest(7, URL.API_HOMEPAGE_BANNER, params,this,mContext));
    }
    
}
