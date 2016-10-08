package com.beyondsoft.ep2p.activity.home;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.beyondsoft.ep2p.ActivityManager;
import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.core.MainHolder;
import com.beyondsoft.ep2p.activity.home.adapter.RegisterProductDetailsListAdapter;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.response.BorrowDetailListResponse;
import com.beyondsoft.ep2p.model.response.BorrowDetailListResponse.NewStandardInformation;
import com.beyondsoft.ep2p.model.response.ExperienceBorrowUniqueInfoResponse;
import com.beyondsoft.ep2p.model.response.NewStandarUniqueInfoResponse;
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
 * 注册页面的产品详情类
 * @ClassName:ProductDetailsActivity 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: szit
 * @date: 2015年12月16日
 */
public class ProductDetailsActivity extends BaseActivity implements OnClickListener
{

    //Title
    private Context mContext;
    private TextView tv_title;
    private ImageView iv_right_falg;
    private ScrollView scrollView1;

    private TextView tv_moderepayment, tv_mode_interest, tv_mode_guarantee,
            tv_xm_description, tv_xm_participation;
    private ListView listview_record = null;
    private RegisterProductDetailsListAdapter mRegisterProductDetailsListAdapter;
    private List<NewStandardInformation> mList = new ArrayList<NewStandardInformation>();
    
    private Button bn_calculator,bn_immediately_tz;
   
    //圆形图表相关控件
    private RelativeLayout rl_table_data;
    private TextView tv_tb_top_info,tv_tb_top_code;
    private TextView tv_tb_zj_nhl,tv_tb_zj_yue,tv_sy_money;
    private ImageView iv_image_kjx;
    private RoundProgressBar rpv_rate;
    private String bl_falg = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ActivityManager.getInstance().pushActivity(ProductDetailsActivity.this);
        mContext = ProductDetailsActivity.this;
        initTitle();
        initView();
        initData();
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
        scrollView1 = (ScrollView) findViewById(R.id.scrollView1);
        
        //圆形图表相关控件
        rpv_rate = (RoundProgressBar) findViewById(R.id.rpv_rate);
        rpv_rate.setTextIsDisplayable(false);
        rpv_rate.setRoundWidth(6f);
        rpv_rate.setStartAnger(270);
        
        rl_table_data = (RelativeLayout) findViewById(R.id.rl_table_data);
        rl_table_data.setVisibility(View.GONE);
        tv_tb_top_info = (TextView) findViewById(R.id.tv_tb_top_info);
        tv_tb_top_code = (TextView) findViewById(R.id.tv_tb_top_code);
        tv_tb_zj_nhl = (TextView) findViewById(R.id.tv_tb_zj_nhl);//年化率
        tv_tb_zj_yue = (TextView) findViewById(R.id.tv_tb_zj_yue);//月份
        tv_sy_money = (TextView) findViewById(R.id.tv_sy_money);//剩余金额
        iv_image_kjx = (ImageView) findViewById(R.id.iv_image_kjx);//图标显示  //暂时没字段控制
        
        tv_moderepayment = (TextView) findViewById(R.id.tv_moderepayment);
        tv_mode_interest = (TextView) findViewById(R.id.tv_mode_interest);
        tv_mode_guarantee = (TextView) findViewById(R.id.tv_mode_guarantee);
        tv_xm_description = (TextView) findViewById(R.id.tv_xm_description);
        tv_xm_participation = (TextView) findViewById(R.id.tv_xm_participation);

        listview_record = (ListView) findViewById(R.id.listview_record);
        
        bn_calculator = (Button) findViewById(R.id.bn_calculator);
        bn_calculator.setOnClickListener(this);
        bn_immediately_tz = (Button) findViewById(R.id.bn_immediately_tz);
        bn_immediately_tz.setOnClickListener(this);
    }

    private String mborrowId = "";
    private void initData()
    {
        if(getIntent() != null)
        {
            mborrowId = getIntent().getStringExtra("borrowId");
            Logs.d("获取的:borrowId="+mborrowId);
        }
        
        getNewstandarUniqueInfoData();
    }
    
    public void setListViewHeightBasedOnChildren(ListView listView)
    {
        // 获取ListView对应的Adapter   
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
        {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++)
        {
            // listAdapter.getCount()返回数据项的数目   
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高   
            listItem.measure(0, 0);
            // 统计所有子项的总高度   
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
            + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度   
        // params.height最后得到整个ListView完整显示需要的高度   
        listView.setLayoutParams(params);
    }

    private String js_nhl="",js_yue="",js_borrowMoney ="";
    @Override
    public void onClick(View arg0)
    {
        Intent intent = null;
        switch (arg0.getId())
        {
            case R.id.bn_calculator ://"计算器"
                intent = new Intent(mContext, EarningsCalculatorActivity.class);
                intent.putExtra("js_nhl", js_nhl);
                intent.putExtra("js_yue", js_yue);
                intent.putExtra("mDateFlag", 50);
                intent.putExtra("js_money", js_borrowMoney);
                pushActivity(intent);
                break;
            case R.id.bn_immediately_tz ://立即投资
                intent = new Intent(mContext, ImmediatelyInvestActivity.class);
                intent.putExtra(Constants.EP2P, 4); 
                intent.putExtra("isDay", 3);
                intent.putExtra("pid", mPid); 
                pushActivity(intent);
                break;              

            default :
                break;
        }
        
    }
    
    //获取新手标详情数据接口
    private void getNewstandarUniqueInfoData()
    {
        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("borrowId", mborrowId);
        connection(registerService.getStringRequest(1, URL.API_NEWSTANDAR_UNIQUE_INFO, params,this));
    }

    //获取投资记录信息
    private void getNewStandardData()
    {
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("borrowId", mborrowId); //标的id 
//        params.put("pageNum ", pageNum);
//        params.put("pageSize ", pageSize);
        connection(registerService.getStringRequest(3, URL.API_HOME_BORROWDETAILLIST, params,
            this,mContext));
    }
    private String mPid = "";
    @Override
    public void onResponse(int tag, Object values)
    {
        super.onResponse(tag, values);
        
        switch (tag)
        {
            case 1 : //新手标详情
                NewStandarUniqueInfoResponse mNewStandarUniqueInfoResponse = (NewStandarUniqueInfoResponse) StringRequest2
                        .Json2Object(values.toString(),
                            new TypeToken<NewStandarUniqueInfoResponse>()
                            {
                            });

                rl_table_data.setVisibility(View.VISIBLE);
                if (mNewStandarUniqueInfoResponse != null)
                {
                    //控制按钮是否可以点击状态   "borStatus":"2","
                    if(mNewStandarUniqueInfoResponse.result.borrow.borStatus.equals("2")){
                        bn_immediately_tz.setEnabled(true);
                        MainHolder.Instance(mContext).setEarnings(true);//可点击
                    }else{
                        MainHolder.Instance(mContext).setEarnings(false);//不可点击
                        bn_immediately_tz.setText("已结束");
                        bn_immediately_tz.setBackgroundColor(mContext.getResources().getColor(R.color.e_button_bg_off));
                        bn_immediately_tz.setEnabled(false);
                    }
                    
                    mPid =  mNewStandarUniqueInfoResponse.result.borrow.pid;
                    if (TextUtils
                            .isEmpty(mNewStandarUniqueInfoResponse.result.borrow.borrowProgress+""))
                    {
                        rpv_rate.setProgress(0);
                    }
                    else
                    {
                        float mFinalProgress = Float
                                .parseFloat(mNewStandarUniqueInfoResponse.result.borrow.borrowProgress+"") * 100;//目标进度
                        if(mFinalProgress > 0 && mFinalProgress < 1){
                            rpv_rate.setProgress(Math.round(mFinalProgress+1));
                        }else{
                            rpv_rate.setProgress(Math.round(mFinalProgress));
                        }
                       
                    }

                    tv_tb_top_info
                            .setText(StringUtils
                                    .isTestNull(mNewStandarUniqueInfoResponse.result.borrow.borrowName));
                    tv_tb_top_code
                            .setText("编号"+StringUtils
                                    .isTestNull(mNewStandarUniqueInfoResponse.result.borrow.borrowCode));
                    if (StringUtils.isTestNull(
                        mNewStandarUniqueInfoResponse.result.borrow.borrowRate+"")
                            .equals(""))
                    {
                        tv_tb_zj_nhl.setText("0.0%");
                        js_nhl = "0.0%";
                    }
                    else
                    {
                        double mFProgress = Double.parseDouble(mNewStandarUniqueInfoResponse.result.borrow.borrowRate+"") * 100;
                        BigDecimal bd = new BigDecimal(mFProgress);
                        mFProgress = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//取1位小数[取值条件：四舍五入]
                        
                        if(mFProgress % 1 == 0){// 是这个整数，小数点后面是0
                            bl_falg = "#,##0";
                        }else{//不是整数，小数点后面不是0
                            bl_falg = "#,##0.00";
                        }
                        tv_tb_zj_nhl.setText(new DecimalFormat(bl_falg).format(mFProgress) + "%");//保留后面两位，不足用0代替
                        js_nhl = new DecimalFormat(bl_falg).format(mFProgress) + "%";
                    }

                    tv_tb_zj_yue
                            .setText(StringUtils
                                    .isTestNull(mNewStandarUniqueInfoResponse.result.borrow.borDeadline)
                                + "日");
                    js_yue = StringUtils
                            .isTestNull(mNewStandarUniqueInfoResponse.result.borrow.borDeadline);
                    
                    tv_sy_money.setText("剩余：￥"+ StringUtils.formatMoney(mNewStandarUniqueInfoResponse.result.borrow.surplusMoney));
                    
                    js_borrowMoney = StringUtils
                            .isTestNull(mNewStandarUniqueInfoResponse.result.borrow.borrowMoney+"");

                    tv_moderepayment
                            .setText(StringUtils
                                    .isTestNull(mNewStandarUniqueInfoResponse.result.borrow.repaymentTypeName));
                    tv_mode_interest
                            .setText(StringUtils
                                    .isTestNull(mNewStandarUniqueInfoResponse.result.borrow.accrualTypeName));
                    
                    tv_mode_guarantee.setText("100%本息保障");

                    tv_xm_description
                            .setText(StringUtils
                                    .isTestNull(mNewStandarUniqueInfoResponse.result.borrow.borrowDesc));
                    tv_xm_participation
                            .setText(StringUtils
                                    .isTestNull(mNewStandarUniqueInfoResponse.result.borrow.joinCondition));
                    
                    getNewStandardData();
                }
                break;
            case 2: //#TODO 体验标详情
                RefreshDialog.stopProgressDialog();
                ExperienceBorrowUniqueInfoResponse mExperienceBorrowUniqueInfoResponse = (ExperienceBorrowUniqueInfoResponse) StringRequest2
                        .Json2Object(values.toString(),
                            new TypeToken<ExperienceBorrowUniqueInfoResponse>()
                            {
                            });
                if (mExperienceBorrowUniqueInfoResponse != null)
                {
                    if (TextUtils
                            .isEmpty(mExperienceBorrowUniqueInfoResponse.result.borrow.borrowProgress+""))
                    {
                        rpv_rate.setProgress(0);
                    }
                    else
                    {
                        float mFinalProgress = Float
                                .parseFloat(mExperienceBorrowUniqueInfoResponse.result.borrow.borrowProgress+"") * 100;//目标进度
                        rpv_rate.setProgress(Math.round(mFinalProgress));
                    }

                    tv_tb_top_info
                            .setText(StringUtils
                                    .isTestNull(mExperienceBorrowUniqueInfoResponse.result.borrow.borrowTypeName));
                    tv_tb_top_code
                            .setText(StringUtils
                                    .isTestNull(mExperienceBorrowUniqueInfoResponse.result.borrow.borrowCode));
                    if (StringUtils.isTestNull(
                        mExperienceBorrowUniqueInfoResponse.result.borrow.borrowRate+"")
                            .equals(""))
                    {
                        tv_tb_zj_nhl.setText("0%");
                    }
                    else
                    {
                        float mFProgress = Float
                                .parseFloat(mExperienceBorrowUniqueInfoResponse.result.borrow.borrowRate+"") * 100;
                        BigDecimal bd = new BigDecimal(mFProgress);
                        mFProgress = bd.setScale(1, BigDecimal.ROUND_HALF_UP)
                                .floatValue();//取1位小数[取值条件：四舍五入]
                        tv_tb_zj_nhl.setText("" + mFProgress + "%");
                    }

                    tv_tb_zj_yue
                            .setText(StringUtils
                                    .isTestNull(mExperienceBorrowUniqueInfoResponse.result.borrow.borDeadline)
                                + "月");
                    tv_sy_money
                            .setText("剩余：￥"
                                + StringUtils
                                        .isTestNull(mExperienceBorrowUniqueInfoResponse.result.borrow.borrowMoney+""));

                    tv_moderepayment
                            .setText(StringUtils
                                    .isTestNull(mExperienceBorrowUniqueInfoResponse.result.borrow.repaymentTypeName));
                    tv_mode_interest
                            .setText(StringUtils
                                    .isTestNull(mExperienceBorrowUniqueInfoResponse.result.borrow.accrualTypeName));
                    tv_mode_guarantee
                            .setText(StringUtils
                                    .isTestNull(mExperienceBorrowUniqueInfoResponse.result.borrow.borrowTypeName));

                    tv_xm_description
                            .setText(StringUtils
                                    .isTestNull(mExperienceBorrowUniqueInfoResponse.result.borrow.borrowDesc));
                    tv_xm_participation
                            .setText(StringUtils
                                    .isTestNull(mExperienceBorrowUniqueInfoResponse.result.borrow.borrowDesc));
                }
                break;
                
            case 3: //投资记录===
                RefreshDialog.stopProgressDialog();
                Logs.d("新手专享：投资记录===："+values.toString());
                BorrowDetailListResponse mNewStandardInformationResponse = (BorrowDetailListResponse) StringRequest2
                        .Json2Object(values.toString(),
                            new TypeToken<BorrowDetailListResponse>()
                            {
                            }); 
                
                if (mNewStandardInformationResponse != null)
                {
                    scrollView1.post(new Runnable()
                    {
                        public void run()
                        {
                            //scrollView1.fullScroll(ScrollView.FOCUS_UP);   
                            scrollView1.scrollTo(0, 0);
                        }
                    });
                    
                    mList = mNewStandardInformationResponse.result.result;
                    mRegisterProductDetailsListAdapter = new RegisterProductDetailsListAdapter(
                        mContext, mList);
                    listview_record.setAdapter(mRegisterProductDetailsListAdapter);
                    setListViewHeightBasedOnChildren(listview_record);
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
}
