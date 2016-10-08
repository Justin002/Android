package com.beyondsoft.ep2p.activity.home.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.beyondsoft.ep2p.BaseFragment;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.core.MainHolder;
import com.beyondsoft.ep2p.activity.home.adapter.HkScheduleAdapter;
import com.beyondsoft.ep2p.model.response.HkScheduleResponse;
import com.beyondsoft.ep2p.model.response.HkScheduleResponse.HkScheduleItem;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.Logs;
import com.beyondsoft.ep2p.utils.StringRequest2;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * 还款计划
 * @ClassName:ProjectInformationFragment 
 * @Description: 这里用一句话描述这个类的作用 
 * @author: ssy
 * @date: 2015年12月18日
 *
 */
public class HkScheduleFragment extends BaseFragment {

	private Activity mContext;
	private View mParent;

	private ListView lv_listview_hk_schedule;
	private HkScheduleAdapter<HkScheduleItem> mHkScheduleAdapter;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: {
//				Intent intent = new Intent();
//				intent.setClass(mContext, ProjectDetailActivity.class);
//				startActivity(intent);
			}
				break;

			default:
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mParent = inflater.inflate(R.layout.fragment_hk_schedule, null);
		return mParent;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mContext = getActivity();
		initView();
		initData();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
	    lv_listview_hk_schedule = (ListView) mParent.findViewById(R.id.lv_listview_hk_schedule);
	    
	}
	private void initData() {
        mPid = MainHolder.Instance(mContext).getProjectInformation_Pid();
        mCustomId = MainHolder.Instance(mContext).getCustomId();
        mTzjlPid  = MainHolder.Instance(mContext).getTzjlPid();
        Logs.d("还款计划记录pid="+mTzjlPid);
        getNewStandardData();
	}
	
    private  String mPid = "",mCustomId = "",mTzjlPid= "";
    private String pageNum = "1",pageSize = "10";
	//获取还款计划信息
    private void getNewStandardData()
    {
        RefreshDialog.startProgressDialog(mContext, "");
        BaseService registerService = new BaseService();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("borrowId", mTzjlPid); //标的id 
        connection(registerService.getStringRequest(1, URL.API_HOME_REPAYMENTBYBORROWID, params,
            this,mContext));
    }
    
    private List<HkScheduleItem> mList  =new ArrayList<HkScheduleItem>();
    @Override
    public void onResponse(int tag, Object values)
    {
        super.onResponse(tag, values);
        Logs.d("还款计划：===="+values.toString());
        RefreshDialog.stopProgressDialog();
        HkScheduleResponse mHkScheduleResponse = (HkScheduleResponse) StringRequest2
                .Json2Object(values.toString(),
                    new TypeToken<HkScheduleResponse>()
                    {
                    }); 
        if(mHkScheduleResponse!=null){
            mList =  mHkScheduleResponse.result.result;
            mHkScheduleAdapter = new HkScheduleAdapter<HkScheduleItem>(mContext, mList,mHandler);
            lv_listview_hk_schedule.setAdapter(mHkScheduleAdapter);
            
        }

    }
    @Override
    public void onErrorResponse(int tag, String error)
    {
        super.onErrorResponse(tag, error);
        RefreshDialog.stopProgressDialog();
    }
	
	
}
