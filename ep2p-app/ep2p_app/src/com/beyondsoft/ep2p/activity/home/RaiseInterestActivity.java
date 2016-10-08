package com.beyondsoft.ep2p.activity.home;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.home.adapter.RaiseInterestList2Adapter2;
import com.beyondsoft.ep2p.activity.home.adapter.RaiseInterestListAdapter;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.response.RaiseInterestListResponse;
import com.beyondsoft.ep2p.model.response.RaiseInterestListResponse.RaiseInterestListItem;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.Logs;
import com.beyondsoft.ep2p.utils.StringRequest2;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.google.gson.reflect.TypeToken;


/**
 * 
 * 加息券
 * @ClassName:RaiseInterestActivity 
 * @Description: 这里用一句话描述这个类的作用 
 * @date: 2016年3月1日
 *
 */
public class RaiseInterestActivity extends BaseActivity implements OnClickListener
{
    private Context mContext;
    private TextView tv_title;
    private ImageView iv_right_falg;

    private ListView listview_experience;
//    private RaiseInterestListAdapter mExperienceListAdapter;
    private RaiseInterestList2Adapter2  mExperienceListAdapter;
    private List<RaiseInterestListItem> mList = new ArrayList<RaiseInterestListItem>();

    private Button bn_sy_ok;
    private LinearLayout ll_null_data_ui;//空数据的UI
    private LinearLayout ll_wifi_off_ui;//无wifi的UI
    private TextView wifi_load_again;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2experience3);

        mContext = RaiseInterestActivity.this;
        initTitle();
        initView();
        initData();
    }

    private void initTitle()
    {
        tv_title = (TextView) findViewById(R.id.title_content_tv);
        tv_title.setText("使用加息券");
        iv_right_falg = (ImageView) findViewById(R.id.title_right);
        iv_right_falg.setVisibility(View.GONE);
    }

    private void initView()
    {
        ll_null_data_ui = (LinearLayout) findViewById(R.id.ll_null_data_ui);
        ll_null_data_ui.setVisibility(View.GONE);
        ll_wifi_off_ui = (LinearLayout) findViewById(R.id.ll_wifi_off_ui);
        ll_wifi_off_ui.setVisibility(View.GONE);
        wifi_load_again = (TextView) findViewById(R.id.wifi_load_again);
        wifi_load_again.setOnClickListener(this);

        listview_experience = (ListView) findViewById(R.id.listview_experience);
        listview_experience.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        bn_sy_ok = (Button) findViewById(R.id.bn_sy_ok);
        bn_sy_ok.setOnClickListener(this);
    }

    private String mpid = "";
    private String mToken = "";

    private void initData()
    {
        if (getIntent() != null)
        {
            mpid = getIntent().getStringExtra("pid");
            Logs.d("pid=" + mpid);
        }
        mToken = CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN);
        if (!TextUtils.isEmpty(mToken))
        {
            getUseExperienceData();
        }
    }

    private int pageIndex = 1, pageSize = 10;

    //获取加息券数据接口
    private void getUseExperienceData()
    {
        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("token", mToken);
//        params.put("pageIndex", pageIndex);  //不是必填
//        params.put("pageSize", pageSize);
        connection(registerService.getStringRequest(1, URL.API_SELECTUSERINTERST_TICKET,
            params, this));
    }

    private String mepces = "";
    private double mjx_bl = 0.0;
    @Override
    public void onResponse(int tag, Object values)
    {
        super.onResponse(tag, values);
        RefreshDialog.stopProgressDialog();
        switch (tag)
        {
            case 1 :
                RaiseInterestListResponse mExperienceListResponse = (RaiseInterestListResponse) StringRequest2
                        .Json2Object(values.toString(),
                            new TypeToken<RaiseInterestListResponse>()
                            {
                            });

                if (mExperienceListResponse != null)
                {
                    mList = mExperienceListResponse.result.list;

                    if (mList.size() < 0 || mList.isEmpty())//"暂无数据!"
                    {
                        listview_experience.setVisibility(View.GONE);
                        ll_null_data_ui.setVisibility(View.VISIBLE);
                        ll_wifi_off_ui.setVisibility(View.GONE);
                        return;
                    }
                    ll_null_data_ui.setVisibility(View.GONE);
                    ll_wifi_off_ui.setVisibility(View.GONE);
                    listview_experience.setVisibility(View.VISIBLE);
//                    mExperienceListAdapter = new RaiseInterestListAdapter(mContext, mList);
                    mExperienceListAdapter = new RaiseInterestList2Adapter2(mContext, mList);
                    listview_experience.setAdapter(mExperienceListAdapter);
                    //按钮的点击事件[checkBox]
                    mExperienceListAdapter.setOnCheckBoxCheckedLsitener(new RaiseInterestList2Adapter2.OnCheckBoxCheckedLsitener() {
                        
                        @Override
                        public void getChecked(int position) {
                            mepces = mList.get(position).pid;
                            mjx_bl = mList.get(position).cardQuota;
                            if (!TextUtils.isEmpty(mepces))
                            {
                                bn_sy_ok.setEnabled(true);
                                Logs.d("选中的使用加息券String[]=:" + mepces);
                            }else {
                                bn_sy_ok.setEnabled(false);
                                mepces = "";
                            }
                        }
                    });
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

        switch (tag)
        {
            case 1 :
                ll_wifi_off_ui.setVisibility(View.VISIBLE);
                listview_experience.setVisibility(View.GONE);
                ll_null_data_ui.setVisibility(View.GONE);
                CommonUtils.toastMsgShort(mContext, error);
                break;
            case 2 :
                break;
            case 3 :
                CommonUtils.toastMsgShort(mContext, error);
                break;
            default :
                break;
        }
    }

    @Override
    public void onClick(View arg0)
    {
        switch (arg0.getId())
        {
            case R.id.bn_sy_ok :
                if (!TextUtils.isEmpty(mepces))
                {
                    //回到上一个页面
                    Intent miner = new Intent(mContext,ImmediatelyInvestActivity.class);
                    miner.putExtra("jxq_id", mepces);
                    miner.putExtra("jxq_bl", mjx_bl);
                    setResult(Activity.RESULT_OK, miner);
                    finish();
                }
                else
                {
                    CommonUtils.toastMsgShort(mContext, "请选择一个加息券!");
                }
                break;
            case R.id.wifi_load_again :
                getUseExperienceData();
                break;
            default :
                break;
        }
    }

}
