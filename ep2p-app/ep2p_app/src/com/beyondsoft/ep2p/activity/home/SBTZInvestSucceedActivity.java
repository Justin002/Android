package com.beyondsoft.ep2p.activity.home;


import java.math.BigDecimal;
import java.text.DecimalFormat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyondsoft.ep2p.ActivityManager;
import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.core.MainHolder;
import com.beyondsoft.ep2p.activity.mine.activity.InvestmentedProjectActivity;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.TzSucceedInfoBean;
import com.beyondsoft.ep2p.utils.Logs;
import com.beyondsoft.ep2p.utils.StringUtils;


/**
 * 
 * 投资成功【散标投资成功页面】
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2015年12月16日
 *
 */
public class SBTZInvestSucceedActivity extends BaseActivity implements OnClickListener
{

    //Title
    private Context mContext;
    private TextView tv_title;
    private TextView tv_title_right;
    private ImageView title_left_btn;

    private TextView tv_title_isResult_finish;
    private TextView tv_tzxm_moder_amount;//投资项目
    private TextView tv_user_code, tv_moder_amount, tv_moder_amount_jl,
            tv_moder_anticipated_income, tv_moder_deadline, tv_moder_rate;
    private Button bn_check_project;
    private int isShowUI = 0;
    private String bl_falg = "";
    private RelativeLayout rl_moder_amount_jl, rl_moder_anticipated_income;//投资奖励layout，预期收益layout

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sbtz_invest_succeed);
        mContext = SBTZInvestSucceedActivity.this;
        ActivityManager.getInstance().pushActivity(SBTZInvestSucceedActivity.this);
        initTitle();
        initView();
        initData();
    }

    private void initTitle()
    {
        title_left_btn = (ImageView) findViewById(R.id.title_left_btn);
        title_left_btn.setVisibility(View.GONE);//#TODO  测试要求不显示
        tv_title = (TextView) findViewById(R.id.title_content_tv);
        tv_title.setText("投资成功");

        tv_title_isResult_finish = (TextView) findViewById(R.id.tv_title_isResult_finish);
        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
        tv_title_right.setText("确定");
        tv_title_right.setOnClickListener(this);
    }

    private void initView()
    {
        tv_user_code = (TextView) findViewById(R.id.tv_user_code);
        tv_tzxm_moder_amount = (TextView) findViewById(R.id.tv_tzxm_moder_amount);
        tv_moder_amount = (TextView) findViewById(R.id.tv_moder_amount);
        tv_moder_amount_jl = (TextView) findViewById(R.id.tv_moder_amount_jl);
        tv_moder_anticipated_income = (TextView) findViewById(R.id.tv_moder_anticipated_income);
        tv_moder_deadline = (TextView) findViewById(R.id.tv_moder_deadline);
        tv_moder_rate = (TextView) findViewById(R.id.tv_moder_rate);

        rl_moder_amount_jl = (RelativeLayout) findViewById(R.id.rl_moder_amount_jl);
        rl_moder_anticipated_income = (RelativeLayout) findViewById(R.id.rl_moder_anticipated_income);

        bn_check_project = (Button) findViewById(R.id.bn_check_project);
        bn_check_project.setOnClickListener(this);
    }

    private TzSucceedInfoBean mTzSucceedInfoBean = null; //e计划和散标投资成功的数据Data
    private boolean isResult_finish = false; //是否投资成功

    private void initData()
    {
        if (getIntent() != null)
        {
            isShowUI = getIntent().getIntExtra(Constants.EP2P, 0);

            isResult_finish = getIntent().getBooleanExtra("result_finish", false);
            Logs.d("isShowUI=：" + isShowUI);
        }
        if (isResult_finish)
        {
            tv_title_isResult_finish.setText("恭喜您，投资成功!");
        }
        else
        {
            tv_title_isResult_finish.setText("很遗憾,手慢了,没投资成功!");
        }
        mTzSucceedInfoBean = MainHolder.Instance(mContext).getmTzSucceedInfoBean();

        if (mTzSucceedInfoBean != null)
        {

            tv_user_code.setText(mTzSucceedInfoBean.getTzCode());
            tv_tzxm_moder_amount.setText(mTzSucceedInfoBean.getTzXMName());

            tv_moder_amount.setText("￥"
                + StringUtils.formatMoney(Double.parseDouble(mTzSucceedInfoBean
                        .getTzMoney())));
            if (!TextUtils.isEmpty(mTzSucceedInfoBean.getTzjl()))
            {
                tv_moder_amount_jl.setText("￥"
                    + StringUtils.formatMoney(Double.parseDouble(mTzSucceedInfoBean
                            .getTzjl())));
            }
            else
            {
                tv_moder_amount_jl.setText("");
            }
            if (!TextUtils.isEmpty(mTzSucceedInfoBean.getTzyqsy()))
            {
                tv_moder_anticipated_income.setText("￥"
                    + StringUtils.formatMoney(Double.parseDouble(mTzSucceedInfoBean
                            .getTzyqsy())));
            }
            else
            {
                tv_moder_anticipated_income.setText("");
            }
            if (isShowUI == 4)
            {
                tv_moder_deadline.setText(mTzSucceedInfoBean.getTzqx() + "日");
            }
            else
            {
                tv_moder_deadline.setText(mTzSucceedInfoBean.getTzqx() + "月");
            }

            Double mNhllProgress = Double.parseDouble(mTzSucceedInfoBean.getTznhl()) * 100;//目标进度
            BigDecimal bd = new BigDecimal(mNhllProgress);
            mNhllProgress = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//取2位小数[取值条件：四舍五入]
            if (mNhllProgress % 1 == 0)
            {// 是这个整数，小数点后面是0
                bl_falg = "#,##0";
            }
            else
            {//不是整数，小数点后面不是0
                bl_falg = "#,##0.00";
            }
            tv_moder_rate.setText(new DecimalFormat(bl_falg).format(mNhllProgress) + "%");//保留后面两位，不足用0代替
        }
    }

    @Override
    public void onClick(View arg0)
    {
        switch (arg0.getId())
        {
            case R.id.tv_title_right :
                if (isShowUI == 1 || isShowUI == 2 || isShowUI == 3) //返回e计划列表
                {
                    pushActivity(CFPTypeActivity.class);
                }
                else //if (isShowUI == 4)
                {//返回新手专享
                    pushActivity(RegisterActivity.class);
                }
                ActivityManager.getInstance().popAllActivity();
                break;
            case R.id.bn_check_project :
                //                CommonUtils.toastMsgShort(mContext, "查看已投");
                Intent intent = new Intent(mContext, InvestmentedProjectActivity.class);
                intent.putExtra(InvestmentedProjectActivity.TAB_SELECT, InvestmentedProjectActivity.TAB_BIDING);
                pushActivity(intent);
                ActivityManager.getInstance().popAllActivity();
                break;

            default :
                break;
        }
    }
}
