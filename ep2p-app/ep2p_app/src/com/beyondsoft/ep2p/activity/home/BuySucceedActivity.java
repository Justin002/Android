package com.beyondsoft.ep2p.activity.home;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.utils.CommonUtils;

/**
 * 
 * 购买成功
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2015年12月16日
 *
 */
public class BuySucceedActivity extends BaseActivity implements OnClickListener
{

    //Title
    private Context mContext;
    private TextView tv_title;
    private ImageView iv_right_falg;
    
    private TextView tv_user_code,tv_moder_amount,tv_moder_anticipated_income,tv_moder_deadline,tv_moder_rate;
    private Button bn_check_project;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_succeed);
        mContext = BuySucceedActivity.this;
        initTitle();
        initView();
        initData();
    }

    private void initTitle()
    {
        tv_title = (TextView) findViewById(R.id.title_content_tv);
        tv_title.setText("购买成功");
        iv_right_falg = (ImageView) findViewById(R.id.title_right);
        iv_right_falg.setVisibility(View.GONE);          
    }

    private void initView()
    {
        tv_user_code = (TextView) findViewById(R.id.tv_user_code);
        tv_moder_amount = (TextView) findViewById(R.id.tv_moder_amount);
        tv_moder_anticipated_income = (TextView) findViewById(R.id.tv_moder_anticipated_income);
        tv_moder_deadline = (TextView) findViewById(R.id.tv_moder_deadline);
        tv_moder_rate = (TextView) findViewById(R.id.tv_moder_rate);
        
        bn_check_project = (Button) findViewById(R.id.bn_check_project);
        bn_check_project.setOnClickListener(this);
    }

    private void initData()
    {
        
    }

    @Override
    public void onClick(View arg0)
    {
        CommonUtils.toastMsgShort(mContext, "查看已投");
    }
}
