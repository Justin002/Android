package com.beyondsoft.ep2p.activity.home;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.SlidingDrawer.OnDrawerScrollListener;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.LoginActivity;
import com.beyondsoft.ep2p.activity.core.MainHolder;
import com.beyondsoft.ep2p.activity.home.fragment.FkInformationFragment;
import com.beyondsoft.ep2p.activity.home.fragment.HkScheduleFragment;
import com.beyondsoft.ep2p.activity.home.fragment.ProjectInformation2ejhFragment;
import com.beyondsoft.ep2p.activity.home.fragment.ProjectInformationFragment;
import com.beyondsoft.ep2p.activity.home.fragment.TzRecordFragment;
import com.beyondsoft.ep2p.adapter.SimpleFragmentPagerAdapter;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.response.CFPTypeDetailsResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.Logs;
import com.beyondsoft.ep2p.utils.StringRequest2;
import com.beyondsoft.ep2p.utils.StringUtils;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.beyondsoft.ep2p.view.RoundProgressBar;
import com.google.gson.reflect.TypeToken;


/**
 * 
 * 首页的产品详情类
 * @ClassName:ProductDetailsActivity 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2015年12月16日
 *
 */
@SuppressWarnings("deprecation")
public class HomeProductDetailsActivity extends BaseFragmentActivity
    implements
        OnClickListener, OnGestureListener,OnTouchListener
{

    private static final String TAG = "HomeProductDetailsActivity";
    //Title
    private Context mContext;
    private TextView tv_title;
    private ImageView iv_right_falg;
    private int UI_falg = 0,index_data = 0;
    private String mPid = "";
    
    private RoundProgressBar rpv_rate;
    private TextView tv_moderepayment, tv_mode_interest, tv_mode_guarantee;
    private TextView myImage_heads,myImage;
    private Button bn_calculator, bn_immediately_tz;
    
    //圆形图标相关控件
    private RelativeLayout rl_table_data;
    private TextView tv_tb_top_info,tv_tb_top_code;
    private TextView tv_tb_zj_nhl,tv_tb_zj_yue,tv_sy_money;
    private ImageView iv_image_kjx;
    private TextView tv_image_kjx2;
    private String js_nhl = "",js_yue = "";
    private String bl_falg = "";
    
    //SlidingDrawer[抽屉相关的]
    private SlidingDrawer sd;
//    private SpecialViewPager svp_content;
    private ViewPager svp_content;
    private SimpleFragmentPagerAdapter fragmentPagerAdapter;
    private ArrayList<Fragment> mFragmentList = new ArrayList<Fragment>();
    //项目信息Fragment
    private ProjectInformationFragment mProjectInformationFragment = new ProjectInformationFragment();
    //项目描述Fragment
    private ProjectInformation2ejhFragment mProjectInformation2ejhFragment =new ProjectInformation2ejhFragment();
    //风控信息Fragment
    private FkInformationFragment mFkInformationFragment = new FkInformationFragment();
    //还款计划Fragment
    private HkScheduleFragment mHkScheduleFragment = new HkScheduleFragment();
    //投资记录Fragment
    private TzRecordFragment mTzRecordFragment = new TzRecordFragment();
    private TextView tv_project_information, tv_fk_information, tv_hk_schedule,
            tv_tz_record;
    
    private GestureDetector gd;
    private float mFristTimeY;
    private TranslateAnimation animation;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_product_details);
        mContext = HomeProductDetailsActivity.this;
        initTitle();
        initView();
        initData();
        initListener();
        
        gd = new GestureDetector(mContext,this);
    }

    private void initTitle()
    {
        tv_title = (TextView) findViewById(R.id.title_content_tv);
        tv_title.setText("产品详情");
        iv_right_falg = (ImageView) findViewById(R.id.title_right);
        iv_right_falg.setVisibility(View.GONE);
    }

    private void initView()
    {
        rpv_rate = (RoundProgressBar) findViewById(R.id.rpv_rate);
        rpv_rate.setTextIsDisplayable(false);
        rpv_rate.setRoundWidth(6f);
        rpv_rate.setStartAnger(270);
        
        myImage_heads= (TextView) findViewById(R.id.myImage_heads);
        myImage = (TextView) findViewById(R.id.myImage);
        
        sd = (SlidingDrawer) findViewById(R.id.drawer1);
        tv_moderepayment = (TextView) findViewById(R.id.tv_moderepayment);//还款方式
        tv_mode_interest = (TextView) findViewById(R.id.tv_mode_interest);//计息方式
        tv_mode_guarantee = (TextView) findViewById(R.id.tv_mode_guarantee);//保障方式
        
        //图表
        rl_table_data =  (RelativeLayout) findViewById(R.id.rl_table_data);
        rl_table_data.setVisibility(View.GONE);
        tv_tb_top_info = (TextView) findViewById(R.id.tv_tb_top_info);
        tv_tb_top_code = (TextView) findViewById(R.id.tv_tb_top_code);
        tv_tb_zj_nhl = (TextView) findViewById(R.id.tv_tb_zj_nhl);//年化率
        tv_tb_zj_yue = (TextView) findViewById(R.id.tv_tb_zj_yue);//月份
        tv_sy_money = (TextView) findViewById(R.id.tv_sy_money);//剩余金额
        iv_image_kjx = (ImageView) findViewById(R.id.iv_image_kjx);//图标显示
        tv_image_kjx2 = (TextView) findViewById(R.id.tv_image_kjx2);//图标 奖励 率

        //SlidingDrawer
//        svp_content = (SpecialViewPager) findViewById(R.id.svp_content);
        svp_content = (ViewPager) findViewById(R.id.svp_content);
//        svp_content.setCanScroll(true);
        tv_project_information = (TextView) findViewById(R.id.tv_project_information);
        tv_project_information.setOnClickListener(this);
        tv_project_information.setOnTouchListener(this);
        tv_fk_information = (TextView) findViewById(R.id.tv_fk_information);
        tv_fk_information.setOnClickListener(this);
        tv_hk_schedule = (TextView) findViewById(R.id.tv_hk_schedule);
        tv_hk_schedule.setOnClickListener(this);
        tv_tz_record = (TextView) findViewById(R.id.tv_tz_record);
        tv_tz_record.setOnClickListener(this);

        bn_calculator = (Button) findViewById(R.id.bn_calculator);
        bn_calculator.setOnClickListener(this);
        bn_immediately_tz = (Button) findViewById(R.id.bn_immediately_tz);
        bn_immediately_tz.setOnClickListener(this);
    }

    private String mToken = "";
    private void initData()
    {
        mToken = CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN);
        if(getIntent() != null){
            UI_falg = getIntent().getIntExtra(Constants.EP2P, 0);
            mPid =  getIntent().getStringExtra(Constants.EJH_PID);
            index_data = getIntent().getIntExtra("index_data", 0); 
        }
        
        fragmentPagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        svp_content.setAdapter(fragmentPagerAdapter);
        
        svp_content.setOnTouchListener(new OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
//                return !svp_content.isCanScroll();
                return false;
            }
        });
        
        if (!TextUtils.isEmpty(mPid))
        {
            Logs.d("e_info_pid:" + mPid);
            getEplanListInfoData(mPid);
        }
    }
    
    //获取E计划 散标投资详情接口
    private void getEplanListInfoData(String borrowId)
    {
        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("pid", borrowId); //标的id
        connection(registerService.getStringRequest(1, URL.EPLAN_LIST_INFO, params,this));
    }
    
    private  CFPTypeDetailsResponse mCFPTypeDetailsResponse = null;
    @Override
    public void onResponse(int tag, Object values)
    {
        super.onResponse(tag, values);
        RefreshDialog.stopProgressDialog();
        switch (tag)
        {
            case 1 :
                mCFPTypeDetailsResponse = (CFPTypeDetailsResponse) StringRequest2
                        .Json2Object(values.toString(),
                            new TypeToken<CFPTypeDetailsResponse>()
                            {
                            });

                rl_table_data.setVisibility(View.VISIBLE);
                if(mCFPTypeDetailsResponse!=null)
                {
                    //控制按钮是否可以点击状态   "borStatus":"2","   //1：  倒计时
                    if(mCFPTypeDetailsResponse.result.borStatus.equals("2")){
                        bn_immediately_tz.setEnabled(true);
                        MainHolder.Instance(mContext).setEarnings(true);//可点击
                    }else{
                        if(mCFPTypeDetailsResponse.result.borStatus.equals("1")){
                            bn_immediately_tz.setText("立即投资");
                        }else if(mCFPTypeDetailsResponse.result.borStatus.equals("5")){
                            bn_immediately_tz.setText("已满额");
                        }
                        else{
                            bn_immediately_tz.setText("已结束");
                        }
                        MainHolder.Instance(mContext).setEarnings(false);//不可点击
                        bn_immediately_tz.setBackgroundColor(mContext.getResources().getColor(R.color.e_button_bg_off));
                        bn_immediately_tz.setEnabled(false);
                    }
                    
                    if(UI_falg != 1){
                        if(!TextUtils.isEmpty(mCFPTypeDetailsResponse.result.isJiaxiTicket) && mCFPTypeDetailsResponse.result.isJiaxiTicket.equals("1")){ //可加息
//                            iv_image_kjx.setVisibility(View.VISIBLE);//图标加息标志
                            tv_image_kjx2.setVisibility(View.VISIBLE);//图标奖励
                            
                            float mFinvestRewardScale = Float.parseFloat(mCFPTypeDetailsResponse.result.investRewardScale + "") * 100;//目标进度
                            BigDecimal bd = new BigDecimal(mFinvestRewardScale);
                            mFinvestRewardScale = bd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();//取2位小数[取值条件：四舍五入]
//                            tv_image_kjx2.setText("奖励"+StringUtils.isTestNull(mFinvestRewardScale + "")+"%");
                            tv_image_kjx2.setText("奖励"+new DecimalFormat("#,##0.00").format(mFinvestRewardScale) + "%");//保留后面两位，不足用0代替
                        }else{
//                            iv_image_kjx.setVisibility(View.GONE);//图标加息标志
                            tv_image_kjx2.setVisibility(View.GONE);//图标奖励
                        }
                    }else{
                        iv_image_kjx.setVisibility(View.GONE);//图标加息标志
                    }
                    
                    if (TextUtils.isEmpty(mCFPTypeDetailsResponse.result.borrowProgress+""))
                    {
                        rpv_rate.setProgress(0);
                    }
                    else
                    {
                        float mFinalProgress = Float
                                .parseFloat(mCFPTypeDetailsResponse.result.borrowProgress+"") * 100;//目标进度
                        if(mFinalProgress > 0 && mFinalProgress < 1){
                            rpv_rate.setProgress(Math.round(mFinalProgress+1));
                        }else{
                            rpv_rate.setProgress(Math.round(mFinalProgress));
                        }
                    }

                    tv_tb_top_info.setText(StringUtils
                            .isTestNull(mCFPTypeDetailsResponse.result.borrowName));//borrowName==borrowTypeName
                    tv_tb_top_code.setText("编号"+StringUtils
                            .isTestNull(mCFPTypeDetailsResponse.result.borrowCode));
                    if (StringUtils.isTestNull(mCFPTypeDetailsResponse.result.borrowRate)
                            .equals(""))
                    {
                        tv_tb_zj_nhl.setText("0.0%");
                        js_nhl = "0.0%";
                    }
                    else
                    {
                        double mFProgress = Float
                                .parseFloat(mCFPTypeDetailsResponse.result.borrowRate) * 100;
                        BigDecimal bd = new BigDecimal(mFProgress);
                        mFProgress = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//取1位小数[取值条件：四舍五入]
                        if(mFProgress % 1 == 0){// 是这个整数，小数点后面是0
                            bl_falg = "#,##0";
                        }else{//不是整数，小数点后面不是0
                            bl_falg = "#,##0.00";
                        }
                        tv_tb_zj_nhl.setText(new DecimalFormat(bl_falg).format(mFProgress) + "%");//保留后面两位，不足用0代替
                        js_nhl = "" + new DecimalFormat(bl_falg).format(mFProgress) + "%";
                    }

                    tv_tb_zj_yue.setText(StringUtils
                            .isTestNull(mCFPTypeDetailsResponse.result.borDeadline) + "月");
                    js_yue = ""+StringUtils
                            .isTestNull(mCFPTypeDetailsResponse.result.borDeadline);
                    //
                    MainHolder.Instance(mContext).setMborDeadline(StringUtils.isTestNull(mCFPTypeDetailsResponse.result.borDeadline));
                    
                    tv_sy_money.setText("剩余：￥"
                        + StringUtils.isTestNull(mCFPTypeDetailsResponse.result.surplusMoney+""));

                    tv_moderepayment.setText(StringUtils
                            .isTestNull(mCFPTypeDetailsResponse.result.repaymentTypeName));
                    tv_mode_interest.setText(StringUtils
                            .isTestNull(mCFPTypeDetailsResponse.result.accrualTypeName));
                    if(!TextUtils.isEmpty(mCFPTypeDetailsResponse.result.securityType)){
                        tv_mode_guarantee.setText(StringUtils
                            .isTestNull(mCFPTypeDetailsResponse.result.securityType));//保障方式 
                    }else{
                        tv_mode_guarantee.setText("100%本息保障");
                    }
                    
                    MainHolder.Instance(mContext).setCustomId(mCFPTypeDetailsResponse.result.customId);
                    MainHolder.Instance(mContext).setTzjlPid(mCFPTypeDetailsResponse.result.pid);
                    
                    if(UI_falg == 1 || index_data == 5){//从e计划页面跳转过来的,5是或者首页跳转过来的
//                        tv_project_information.setText("项目描述");
                        tv_fk_information.setVisibility(View.GONE);
                        tv_hk_schedule.setVisibility(View.GONE);
                        setTab2eStyle(0);
                        mFragmentList.add(mProjectInformation2ejhFragment);
                        mFragmentList.add(mTzRecordFragment);
                    }else{
                        setTabStyle(0);
                        mFragmentList.add(mProjectInformationFragment);
                        mFragmentList.add(mFkInformationFragment);
                        mFragmentList.add(mHkScheduleFragment);
                        mFragmentList.add(mTzRecordFragment);
                    }
                    fragmentPagerAdapter.setData(mFragmentList);
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

    @SuppressWarnings("deprecation")
    private void initListener()
    {
        /* 设定SlidingDrawer被打开的事件处理 */
        sd.setOnDrawerOpenListener(new OnDrawerOpenListener()
        {

            @Override
            public void onDrawerOpened()
            {
//                iv.setImageResource(R.drawable.close);  
//                System.out.println("close");  //展开
//                myImage_heads.setVisibility(View.VISIBLE);
                myImage.setVisibility(View.GONE);
                isOpenWinds =22; 
            }
        });
        /* 设定SlidingDrawer被关闭的事件处理 */
        sd.setOnDrawerCloseListener(new OnDrawerCloseListener()
        {

            public void onDrawerClosed()
            {
//                iv.setImageResource(R.drawable.open);  
//                System.out.println("open"); //关闭
                myImage.setVisibility(View.VISIBLE);
//                myImage_heads.setVisibility(View.GONE);
                isOpenWinds =11; 
            }
        });
        sd.setOnDrawerScrollListener(new OnDrawerScrollListener()
        {

            @Override
            public void onScrollStarted()
            {
            }
            @Override
            public void onScrollEnded()
            {
            }
        });

        svp_content.setOnPageChangeListener(new OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int index)
            {
                if(UI_falg == 1||index_data == 5){
                    setTab2eStyle(index);
                }else{
                    setTabStyle(index);  
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {
            }

            @Override
            public void onPageScrollStateChanged(int arg0)
            {
            }
        });

    }

    private void setButtomLine(TextView tv)
    {
        Drawable right = getResources().getDrawable(
            R.drawable.e_img_tz_buttom_line_03);
        right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());
        tv.setCompoundDrawables(null, null, null, right);
    }
    private void setButtomDefaultLine(TextView tv)
    {
        Drawable right = getResources().getDrawable(
            R.drawable.e_img_tz_buttom_line_03);
        right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());
        tv.setCompoundDrawables(null, null, null, null);
    }
    /**
     * 设置tab样式
     * 
     * @param index
     */
    private void setTab2eStyle(int index)
    {
        tv_project_information.setSelected(false);
        tv_tz_record.setSelected(false);
        switch (index)
        {
            case 0 :
                tv_project_information.setSelected(true);
                setButtomLine(tv_project_information);
                setButtomDefaultLine(tv_tz_record);
                break;
            case 1 :
                tv_tz_record.setSelected(true);
                setButtomLine(tv_tz_record);
                setButtomDefaultLine(tv_project_information);
                break;
            default :
                break;
        }
    }
    /**
     * 设置tab样式
     * 
     * @param index
     */
    private void setTabStyle(int index)
    {
        tv_project_information.setSelected(false);
        tv_fk_information.setSelected(false);
        tv_hk_schedule.setSelected(false);
        tv_tz_record.setSelected(false);
        switch (index)
        {
            case 0 :
                tv_project_information.setSelected(true);
                setButtomLine(tv_project_information);
                setButtomDefaultLine(tv_fk_information);
                setButtomDefaultLine(tv_hk_schedule);
                setButtomDefaultLine(tv_tz_record);
                break;
            case 1 :
                tv_fk_information.setSelected(true);
                setButtomLine(tv_fk_information);
                setButtomDefaultLine(tv_project_information);
                setButtomDefaultLine(tv_hk_schedule);
                setButtomDefaultLine(tv_tz_record);
                break;
            case 2 :
                tv_hk_schedule.setSelected(true);
                setButtomLine(tv_hk_schedule);
                setButtomDefaultLine(tv_project_information);
                setButtomDefaultLine(tv_fk_information);
                setButtomDefaultLine(tv_tz_record);
                break;
            case 3 :
                tv_tz_record.setSelected(true);
                setButtomLine(tv_tz_record);
                setButtomDefaultLine(tv_project_information);
                setButtomDefaultLine(tv_fk_information);
                setButtomDefaultLine(tv_hk_schedule);
                break;
            default :
                break;
        }
    }

    @Override
    public void onClick(View arg0)
    { 
        Intent intent = null ;
        switch (arg0.getId())
        {
            case R.id.bn_calculator ://"计算器"
                intent = new Intent(mContext, EarningsCalculatorActivity.class);
                if(UI_falg == 1){
                    intent.putExtra(Constants.EP2P, 1);
                }else if(UI_falg == 2)
                {
                    intent.putExtra(Constants.EP2P, 2);
                }
                intent.putExtra("js_nhl", js_nhl);
                intent.putExtra("js_yue", js_yue);
                intent.putExtra("mDateFlag", 90);
                intent.putExtra("js_money", StringUtils.isTestNull(mCFPTypeDetailsResponse.result.borrowMoney+""));
                startActivity(intent);
                break;
            case R.id.bn_immediately_tz ://立即投资
                //是否登录
                if(TextUtils.isEmpty(mToken)){
                    //跳转登录
                    pushActivityForResult(new Intent(mContext, LoginActivity.class), 30);
                    return;
                }
                
                intent = new Intent(mContext, ImmediatelyInvestActivity.class);
                if(mCFPTypeDetailsResponse!=null)
                {
                    intent.putExtra("pid", mCFPTypeDetailsResponse.result.pid);
                    intent.putExtra("borrowName", mCFPTypeDetailsResponse.result.borrowName);
                }
                if(UI_falg == 1){
                    intent.putExtra(Constants.EP2P, 1);
                }else if(UI_falg == 2)
                {
                    intent.putExtra(Constants.EP2P, 2);
                }
                startActivity(intent);
                break;
            case R.id.tv_project_information :
                svp_content.setCurrentItem(0, true);
                break;
            case R.id.tv_fk_information :
                svp_content.setCurrentItem(1, true);
                break;
            case R.id.tv_hk_schedule :
                svp_content.setCurrentItem(2, true);
                break;
            case R.id.tv_tz_record :
                if(UI_falg == 1 || index_data == 5){
                    svp_content.setCurrentItem(1, true);
                }else{
                    svp_content.setCurrentItem(3, true);
                }
               
                break;
            default :
                break;
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        Log.i(TAG, "----dispatchTouchEvent---");
        if (isOpenWinds == 22)
        {
            if (gd != null)
            {
            	switch (ev.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mFristTimeY=ev.getY();
					break;
				case MotionEvent.ACTION_UP:
					if(ev.getY()-mFristTimeY>150){//滑动的高度处理是否收回
						  if(sd!=null&&sd.isShown()){
						        animation = new TranslateAnimation(0, 0, +y, 0);
		                        animation.setDuration(500);
		                        sd.startAnimation(animation);
		                        
				                sd.close();
				                isOpenWinds =11; 
				            }
					}else{
						 return super.dispatchTouchEvent(ev);
					}
				}
            }
        }

        return super.dispatchTouchEvent(ev);
    }
 
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (gd != null) {  
            Log.i(TAG, "----onTouchEvent---gb!=null");
            if (gd.onTouchEvent(event))  
                return true;  
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent arg0)
    {
        Logs.d(TAG, "onDown");
        return true;
    }
    private float y = 0;
    private int isOpenWinds =11; 
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float arg2, float arg3)
    {
        Logs.d(TAG, "onFling");
        y = e2.getY() - e1.getY();
        
        animation = new TranslateAnimation(0, 0, -y, 0);
        animation.setDuration(500);
        sd.startAnimation(animation);
        
        if(y>0){//向上滑动
            if(sd!=null&&sd.isShown()){
                sd.close();
                isOpenWinds =11; 
            }
        }else{ //向下滑动
            if(sd!=null){
                isOpenWinds = 22;
                sd.open();
            }
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent arg0)
    {
        Logs.d(TAG, "onLongPress");
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float arg2, float arg3)
    {
        Logs.d(TAG, "onScroll");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent arg0)
    {
        Logs.d(TAG, "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent arg0)
    {
        Logs.d(TAG, "onSingleTapUp");
        return false;
    }

    @Override
    public boolean onTouch(View arg0, MotionEvent arg1)
    {
        // TODO Auto-generated method stub
        Logs.d(TAG, "onTouch");
        return false;
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
