package com.beyondsoft.ep2p.activity.home;


import java.math.BigDecimal;
import java.text.DecimalFormat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.beyondsoft.ep2p.model.TzZRSucceedInfoBean;


/**
 * 
 * 投资成功 【转让  债权成功页面】
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2015年12月16日
 *
 */
public class ZRInvestSucceedActivity extends BaseActivity implements OnClickListener
{

    //Title
    private Context mContext;
    private TextView tv_title;
    private TextView tv_title_right;
    private ImageView title_left_btn;

    private TextView tv_title_isResult_finish;
    private TextView tv_user_code, tv_moder_amount, tv_moder_amount_jl,
            tv_moder_anticipated_income, tv_moder_deadline, tv_moder_rate;
    private Button bn_check_project;
    private int isShowUI = 0;
    private RelativeLayout rl_moder_amount_jl, rl_moder_anticipated_income;//投资奖励layout，预期收益layout
    private TzZRSucceedInfoBean mTzZRSucceedInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zr_invest_succeed);
        mContext = ZRInvestSucceedActivity.this;
        ActivityManager.getInstance().pushActivity(ZRInvestSucceedActivity.this);
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
        tv_user_code = (TextView) findViewById(R.id.tv_user_code);//编号
        tv_moder_amount = (TextView) findViewById(R.id.tv_moder_amount);//项目价值
        tv_moder_amount_jl = (TextView) findViewById(R.id.tv_moder_amount_jl);//购买价格
        tv_moder_anticipated_income = (TextView) findViewById(R.id.tv_moder_anticipated_income);//期限
        tv_moder_deadline = (TextView) findViewById(R.id.tv_moder_deadline);//起息日期
        tv_moder_rate = (TextView) findViewById(R.id.tv_moder_rate);//年化率

        rl_moder_amount_jl = (RelativeLayout) findViewById(R.id.rl_moder_amount_jl);
        rl_moder_anticipated_income = (RelativeLayout) findViewById(R.id.rl_moder_anticipated_income);

        bn_check_project = (Button) findViewById(R.id.bn_check_project);
        bn_check_project.setOnClickListener(this);
    }

    private void initData()
    {
        if (getIntent() != null)
        {
            isShowUI = getIntent().getIntExtra(Constants.EP2P, 0);
        }
        tv_title_isResult_finish.setText("恭喜您，投资成功!");
        mTzZRSucceedInfoBean = MainHolder.Instance(mContext).getmTzZRSucceedInfoBean();

        if (mTzZRSucceedInfoBean != null)
        {
            tv_user_code.setText(mTzZRSucceedInfoBean.getTzCode());
            tv_moder_amount.setText("￥" + mTzZRSucceedInfoBean.getTzXMMoney());
            tv_moder_amount_jl.setText("￥" + mTzZRSucceedInfoBean.getTzGMMoney());
            tv_moder_anticipated_income.setText(mTzZRSucceedInfoBean.getTzqx() + "月");
            tv_moder_deadline.setText("" + mTzZRSucceedInfoBean.getTzqxDate());
            
            tv_moder_rate.setText(mTzZRSucceedInfoBean.getTznhl() + "%");//保留后面两位，不足用0代替
        }
    }

    @Override
    public void onClick(View arg0)
    {
        switch (arg0.getId())
        {
            case R.id.tv_title_right :
                if(isShowUI == 3){//返回转让专区列表
                    pushActivity(CFPTypeActivity.class);
                }else{//返回新手专享
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
