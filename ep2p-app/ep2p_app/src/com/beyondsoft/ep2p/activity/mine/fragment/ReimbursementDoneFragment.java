package com.beyondsoft.ep2p.activity.mine.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import com.beyondsoft.ep2p.BaseFragment;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.mine.activity.ReimbursementDoneDetailActivity;
import com.beyondsoft.ep2p.activity.mine.activity.ReimbursementWaitDetailActivity;
import com.beyondsoft.ep2p.activity.mine.adapter.ReimbursementDoneAdapter;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.ReimbursementDoneInfoBean;
import com.beyondsoft.ep2p.model.ReimbursementDoneListBean;
import com.beyondsoft.ep2p.model.response.ReimbursementDoneReponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 已还款页面
 * 
 * @author hardy.zhou
 *
 */
public class ReimbursementDoneFragment extends BaseFragment implements OnClickListener{

	private View mParent;
	private BaseService baseService;
	private PullToRefreshListView lv_content;
	private LinearLayout ll_wifi_off;
	private LinearLayout ll_null_data;
	private ReimbursementDoneAdapter reimbursementDoneAdapter;
	private ArrayList<ReimbursementDoneInfoBean> list;
	private static final int FLAG_REIMBURSEMENT_DONE_LIST = 100;
	private TextView wifi_load_again;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: {
				/*Intent intent = new Intent();
				intent.setClass(mContext, ReimbursementDoneDetailActivity.class);
				startActivity(intent);*/
			}
				break;

			default:
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mParent = inflater.inflate(R.layout.fragment_reimbursement_done, null);
		return mParent;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		baseService = new BaseService();
		initView();
		doGetReimbursementDoneList();
		setListener();
	}
	
	private void doGetReimbursementDoneList(){
		//RefreshDialog.startProgressDialog(mContext, "",true);
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
	    connection(baseService.getStringRequest(FLAG_REIMBURSEMENT_DONE_LIST, URL.URL_REIMBURSEMENT_DONE_LIST, params, this, mContext));
	}

	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		disconnect(FLAG_REIMBURSEMENT_DONE_LIST);
		RefreshDialog.stopProgressDialog();
	}
	/**
	 * 初始化组件
	 */
	private void initView() {
		ll_wifi_off = (LinearLayout)mParent.findViewById(R.id.ll_wifi_off);
		ll_null_data = (LinearLayout)mParent.findViewById(R.id.ll_null_data);
		lv_content = (PullToRefreshListView) mParent.findViewById(R.id.lv_content_reimbursement_done);
		reimbursementDoneAdapter = new ReimbursementDoneAdapter(mContext, mHandler);
		lv_content.setAdapter(reimbursementDoneAdapter);
		wifi_load_again = (TextView) mParent.findViewById(R.id.wifi_load_again);
		wifi_load_again.setOnClickListener(this);
	}
	
	private void setListener() {
		// 设置一个侦听器被调用时应该刷新列表。
		lv_content.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(mContext, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// 更新LastUpdatedLabel[时间]
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				// 刷新列表
				doGetReimbursementDoneList();			}
		});

		// 添加一个end-of-list侦听器
		lv_content.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				CommonUtils.toastMsgShort(mContext, "数据已全部加载完!");
			}
		});
	}

	class MyItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(mContext, ReimbursementDoneDetailActivity.class);
			intent.putExtra("pid", list.get(arg2-1).getPid());
			startActivity(intent);
		}
		
	}
	
	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		super.onResponse(tag, values);
		RefreshDialog.stopProgressDialog();
		switch(tag){
		case FLAG_REIMBURSEMENT_DONE_LIST:{
			ReimbursementDoneReponse response = gson.fromJson(values.toString(), ReimbursementDoneReponse.class);
		    if(response!=null){
		    	ReimbursementDoneListBean listBean = response.getReimbursementDoneListBean();
		    	if(listBean!=null){
		    		list = listBean.getReimbursementDoneList();
		    	    if(list.size()>0){
		    	    	ll_wifi_off.setVisibility(View.GONE);
						ll_null_data.setVisibility(View.GONE);
						lv_content.setVisibility(View.VISIBLE);
		    	    	reimbursementDoneAdapter.setData(list);
		    	    	lv_content.setOnItemClickListener(new MyItemClickListener());
			    	    lv_content.onRefreshComplete();
		    	    }else{
		    	    	 ll_wifi_off.setVisibility(View.GONE);
						 ll_null_data.setVisibility(View.VISIBLE);
						 lv_content.setVisibility(View.GONE);
		    	    }
		    	    
		    	    
		    	}
		    }
		}
		}
	}
	
	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
		switch(tag){
		case FLAG_REIMBURSEMENT_DONE_LIST:
			CommonUtils.toastMsgLong(mContext, error);
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
			doGetReimbursementDoneList();
			break;
			default:
				break;
		}
	}

}
