package com.beyondsoft.ep2p.activity.mine.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import com.beyondsoft.ep2p.BaseFragment;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.mine.activity.WaittingRecieveDetailActivity;
import com.beyondsoft.ep2p.activity.mine.activity.WaittingRecieveDoneDetailActivity;
import com.beyondsoft.ep2p.activity.mine.adapter.WaittingRecieveFinishAdapter;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.WaitReceiveInfoBean;
import com.beyondsoft.ep2p.model.WaitReceiveListBean;
import com.beyondsoft.ep2p.model.response.WaitReceiveListResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 待收已完结页面
 * 
 * @author hardy.zhou
 *
 */
public class WaittingRecieveFinishFragment extends BaseFragment implements OnClickListener{

	private View mParent;
	private PullToRefreshListView lv_content;
	private LinearLayout ll_wifi_off;
	private LinearLayout ll_null_data;
	private TextView wifi_load_again;
	private WaittingRecieveFinishAdapter waittingRecieveFinishAdapter;
	private BaseService baseService;
    private static final int FLAG_PROJECT_FINISHED_LIST = 100;
    private ArrayList<WaitReceiveInfoBean> WaitReceiveList = new ArrayList<WaitReceiveInfoBean>();
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: {
				Intent intent = new Intent();
				intent.setClass(mContext, WaittingRecieveDoneDetailActivity.class);
				intent.putExtra(WaittingRecieveDoneDetailActivity.FLAG_STATE, WaittingRecieveDoneDetailActivity.STATE_DONE);
				startActivity(intent);
			}
				break;

			default:
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mParent = inflater.inflate(R.layout.fragment_waitting_recieve_finish, null);
		return mParent;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		baseService = new BaseService();
		initView();
		getWaitReceiveDoneList();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		lv_content = (PullToRefreshListView) mParent.findViewById(R.id.lv_content);
		ll_wifi_off = (LinearLayout)mParent.findViewById(R.id.ll_wifi_off);
		ll_null_data = (LinearLayout)mParent.findViewById(R.id.ll_null_data);
		wifi_load_again = (TextView) mParent.findViewById(R.id.wifi_load_again);
		wifi_load_again.setOnClickListener(this);
	}

	public void getWaitReceiveDoneList(){
		   //RefreshDialog.startProgressDialog(mContext, "",true);
		   HashMap<String,Object> params = new HashMap<String,Object>();
		   params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		   params.put("pagesize", 100);
		   params.put("page", 1);
		   params.put("selectType", "2");
		   connection(baseService.getStringRequest(FLAG_PROJECT_FINISHED_LIST, URL.URL_WAIT_RECEIVE_LIST, params, this, mContext));
	   }
	   
	private void initData(){
		waittingRecieveFinishAdapter = new WaittingRecieveFinishAdapter(mContext, WaitReceiveList);
		lv_content.setAdapter(waittingRecieveFinishAdapter);
		lv_content.setOnItemClickListener(new MyItemClickListenener());
	}
	
	class MyItemClickListenener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.putExtra("receiptPlanId",WaitReceiveList.get(arg2-1).getbPid());
			intent.setClass(mContext, WaittingRecieveDoneDetailActivity.class);
			startActivity(intent);
		}
		   
	   }
	
	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		super.onResponse(tag, values);
		switch(tag){
		case FLAG_PROJECT_FINISHED_LIST:
			WaitReceiveListResponse response = gson.fromJson(values.toString(), WaitReceiveListResponse.class);
			if(response!=null){
				WaitReceiveListBean listBean = response.getWaitReceiveListBean();
				WaitReceiveList = listBean.getWaitReceiveList();
				if(listBean.getTatol()>0){
					ll_wifi_off.setVisibility(View.GONE);
					ll_null_data.setVisibility(View.GONE);
					lv_content.setVisibility(View.VISIBLE);
	    			initData();
				}else
				{
					 ll_wifi_off.setVisibility(View.GONE);
					 ll_null_data.setVisibility(View.VISIBLE);
					 lv_content.setVisibility(View.GONE);
				}
			}
			break;
			default:
				break;
		}
	}
	
	
	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
		CommonUtils.toastMsgShort(mContext, error);
		switch(tag){
		case FLAG_PROJECT_FINISHED_LIST:
			ll_wifi_off.setVisibility(View.VISIBLE);
			ll_null_data.setVisibility(View.GONE);
			lv_content.setVisibility(View.GONE);
			break;
			default:
				break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.wifi_load_again:
			//getWaitReceiveList();
			break;
			default:
				break;
		}
	}

}
