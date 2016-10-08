package com.beyondsoft.ep2p.activity.home;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.EServicerAgreementActivity;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.response.ExperienceBorrowUniqueInfoResponse;
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
 * 体验标详情
 * @ClassName:ExperienceActivity 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2015年12月16日
 *
 */
public class ExperienceDetailsActivity extends BaseActivity implements OnClickListener
{
    private Context mContext;
    private TextView tv_title;
    private ImageView iv_right_falg;

    private TextView tv_exp_info, tv_reg_xy;
    private CheckBox checkBox1;
    private boolean isChecked_xy = true;
    private Button bn_lj_join;
    
  //圆形图表相关控件
    private RelativeLayout  rl_table_data;
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
        setContentView(R.layout.activity_experience_details);

        mContext = ExperienceDetailsActivity.this;
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
        //图表
        rl_table_data = (RelativeLayout) findViewById(R.id.rl_table_data);
        rl_table_data.setVisibility(View.GONE);
        tv_tb_top_info = (TextView) findViewById(R.id.tv_tb_top_info);
        tv_tb_top_code = (TextView) findViewById(R.id.tv_tb_top_code);
        tv_tb_zj_nhl = (TextView) findViewById(R.id.tv_tb_zj_nhl);//年化率
        tv_tb_zj_yue = (TextView) findViewById(R.id.tv_tb_zj_yue);//月份
        tv_sy_money = (TextView) findViewById(R.id.tv_sy_money);//剩余金额
        iv_image_kjx = (ImageView) findViewById(R.id.iv_image_kjx);//图标显示
        rpv_rate = (RoundProgressBar) findViewById(R.id.rpv_rate);
        rpv_rate.setTextIsDisplayable(false);
        rpv_rate.setRoundWidth(6f);
        rpv_rate.setStartAnger(270);
        
        tv_exp_info = (TextView) findViewById(R.id.tv_exp_info);
        tv_reg_xy = (TextView) findViewById(R.id.tv_reg_xy);
        tv_reg_xy.setOnClickListener(this);
        checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        //绑定监听器
        checkBox1.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1)
            {
                // TODO Auto-generated method stub
                isChecked_xy = arg1;
//                CommonUtils.toastMsgShort(mContext, arg1 ? "选中了" : "取消了选中");
            }
        });

        bn_lj_join = (Button) findViewById(R.id.bn_lj_join);
        bn_lj_join.setOnClickListener(this);
    }

    private String mborrowId = "";
    private void initData()
    {
        if(getIntent() != null)
        {
            mborrowId = getIntent().getStringExtra("borrowId");
            Logs.d("获取的:borrowId="+mborrowId);
        }
        
        getExperienceborrowUniqueData();
    }
    
    //获取体验标详情数据接口
    private void getExperienceborrowUniqueData()
    {
        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("borrowId", mborrowId);
        connection(registerService.getStringRequest(1, URL.API_EXPERIENCEBORROW_UNIQUE_INFO, params,this));
    }
    
    private ExperienceBorrowUniqueInfoResponse mExperienceBorrowUniqueInfoResponse =null;
    @Override
    public void onResponse(int tag, Object values)
    {
        super.onResponse(tag, values);
        RefreshDialog.stopProgressDialog();
        mExperienceBorrowUniqueInfoResponse = (ExperienceBorrowUniqueInfoResponse) StringRequest2
                .Json2Object(values.toString(),
                    new TypeToken<ExperienceBorrowUniqueInfoResponse>()
                    {
                    });
        rl_table_data.setVisibility(View.VISIBLE);
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
                if(mFinalProgress > 0 && mFinalProgress < 1){
                    rpv_rate.setProgress(Math.round(mFinalProgress+1));
                }else{
                    rpv_rate.setProgress(Math.round(mFinalProgress));
                }
            }

            tv_tb_top_info
                    .setText(StringUtils
                            .isTestNull(mExperienceBorrowUniqueInfoResponse.result.borrow.borrowName));
            tv_tb_top_code
                    .setText("编号"+StringUtils
                            .isTestNull(mExperienceBorrowUniqueInfoResponse.result.borrow.borrowCode));
            if (StringUtils.isTestNull(
                mExperienceBorrowUniqueInfoResponse.result.borrow.borrowRate+"")
                    .equals(""))
            {
                tv_tb_zj_nhl.setText("0.0%");
            }
            else
            {
                float mFProgress = Float
                        .parseFloat(mExperienceBorrowUniqueInfoResponse.result.borrow.borrowRate+"") * 100;
                BigDecimal bd = new BigDecimal(mFProgress);
                mFProgress = bd.setScale(1, BigDecimal.ROUND_HALF_UP)
                        .floatValue();//取1位小数[取值条件：四舍五入]
//                tv_tb_zj_nhl.setText("" + mFProgress + "%");
                if(mFProgress % 1 == 0){// 是这个整数，小数点后面是0
                    bl_falg = "#,##0";
                }else{//不是整数，小数点后面不是0
                    bl_falg = "#,##0.00";
                }
                tv_tb_zj_nhl.setText(new DecimalFormat(bl_falg).format(mFProgress) + "%");//保留后面两位，不足用0代替
            }

            tv_tb_zj_yue
                    .setText(StringUtils
                            .isTestNull(mExperienceBorrowUniqueInfoResponse.result.borrow.borDeadline)
                        + "月");
            
            tv_sy_money.setText("剩余：￥" + StringUtils.formatMoney(mExperienceBorrowUniqueInfoResponse.result.borrow.surplusMoney));

            tv_exp_info
                    .setText(StringUtils
                            .isTestNull(mExperienceBorrowUniqueInfoResponse.result.borrow.borrowDesc));
        }
    }
    @Override
    public void onErrorResponse(int tag, String error)
    {
        super.onErrorResponse(tag, error);
        RefreshDialog.stopProgressDialog();
        CommonUtils.toastMsgShort(mContext, error);
    }

    @Override
    public void onClick(View arg0)
    {
        Intent intent =null;
        switch (arg0.getId())
        {
            case R.id.bn_lj_join :
                if(!isChecked_xy){
                    CommonUtils.toastMsgShort(mContext, "请阅读协议后，并且同意!");
                    return;
                }
                
                intent = new Intent(mContext, ExperienceActivity.class);
                if (mExperienceBorrowUniqueInfoResponse != null)
                {
                    intent.putExtra("pid", mExperienceBorrowUniqueInfoResponse.result.borrow.pid);
                }
                pushActivity(intent);//ExperienceDetailsActivity
                break;
            case R.id.tv_reg_xy :
                CommonUtils.toastMsgShort(mContext, "生财富注册协议");
                intent = new Intent(mContext, EServicerAgreementActivity.class);
                intent.putExtra(Constants.AGREEMENT_TYPE, 1);
                pushActivity(intent);
                break;

            default :
                break;
        }
    }

}
