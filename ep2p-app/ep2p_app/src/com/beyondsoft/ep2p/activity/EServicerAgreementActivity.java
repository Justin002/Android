package com.beyondsoft.ep2p.activity;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.common.Constants;


/**
 * 
 * 服务协议
 * @ClassName:EServicerAgreementActivity 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2015年12月28日
 *
 */
public class EServicerAgreementActivity extends BaseActivity
{
    private Context mContext;
    private TextView tv_title;
    private ImageView iv_right_falg;

    //协议类型【1:用户注册，2：债权转让、债权购买，3：借款、投资】
    private int mAgreement_Type = 0; 
    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eservicer_agreement);
        mContext = EServicerAgreementActivity.this;
        initTitle();
        initView();
        initData();
    }

    private void initTitle()
    {
        tv_title = (TextView) findViewById(R.id.title_content_tv);
        iv_right_falg = (ImageView) findViewById(R.id.title_right);
        iv_right_falg.setVisibility(View.GONE);
    }

    private void initView()
    {
        webview = (WebView) findViewById(R.id.webview);
    }

    private void initData()
    {
        //获取对应传过来的协议类型值
        mAgreement_Type = getIntent().getExtras().getInt(Constants.AGREEMENT_TYPE, 0);

        if (mAgreement_Type == 1)
        {
            tv_title.setText("e生财富用户服务协议");
            webview.loadUrl("file:///android_asset/ep2p_service_register_agreement.htm");
        }
        else if (mAgreement_Type == 2)
        {
            tv_title.setText("债权转让协议");
            webview.loadUrl("file:///android_asset/ep2p_service_creditor_agreement.htm");
        }
        else if (mAgreement_Type == 3)
        {
            tv_title.setText("借款担保合同");
            webview.loadUrl("file:///android_asset/ep2p_service_guarantee_agreement.htm");
        }
    }

    @Override
    public void onStop()
    {
        webview = null;
        super.onStop();
    }

}
