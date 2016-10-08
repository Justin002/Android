package com.beyondsoft.ep2p.activity.mine.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import com.beyondsoft.ep2p.BaseFragment;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.mine.activity.CreditorRightsDetailActivity;
import com.beyondsoft.ep2p.activity.mine.activity.ProjectDetailActivity;
import com.beyondsoft.ep2p.activity.mine.adapter.ProjectTransferAdapter;
import com.beyondsoft.ep2p.model.ProjectTransferInfoBean;
import com.beyondsoft.ep2p.model.ProjectTransferListBean;
import com.beyondsoft.ep2p.model.response.ProjectTransferLlistResponse;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ProjectTransferFragment extends BaseFragment implements OnClickListener{

	private View mParent;
	private PullToRefreshListView lv_content;
	private LinearLayout ll_wifi_off;
	private LinearLayout ll_null_data;
    private TextView wifi_load_again;
    private ProjectTransferAdapter projectTransferAdapter;
    private BaseService baseService;
    private ArrayList<ProjectTransferInfoBean> projectTransferList = new ArrayList<ProjectTransferInfoBean>();
    private static final int FLAG_PROJECT_TRANSFER_LIST = 100;
    private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: {
				/*Intent intent = new Intent();
				intent.setClass(mContext, ProjectDetailActivity.class);
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
		mParent = inflater.inflate(R.layout.fragment_project_transfer, null);
		return mParent;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		baseService = new BaseService();
		initView();
		doGetProjectTransferlist();
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
	
	private void doGetProjectTransferlist(){
	//	RefreshDialog.startProgressDialog(mContext, "",true);
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("pagesize", "10");
		params.put("page", "1");
		connection(baseService.getStringRequest(FLAG_PROJECT_TRANSFER_LIST, URL.URL_PROJECT_TRANSFER_LIST, params, this, mContext));
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		disconnect(FLAG_PROJECT_TRANSFER_LIST);
		RefreshDialog.stopProgressDialog();
	}
	
	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		super.onResponse(tag, values);
		RefreshDialog.stopProgressDialog();
		switch(tag){
		case FLAG_PROJECT_TRANSFER_LIST:
			ProjectTransferLlistResponse response = gson.fromJson(values.toString(), ProjectTransferLlistResponse.class);
			if(response!=null){
				ProjectTransferListBean bean = response.getProjectTransferListBean();
				if(bean!=null){
					projectTransferList = bean.getProjectTransferList();
					if(bean.getTatol()>0)
					{
						ll_wifi_off.setVisibility(View.GONE);
						ll_null_data.setVisibility(View.GONE);
						lv_content.setVisibility(View.VISIBLE);
						updateView();
					}else
					{
						 ll_wifi_off.setVisibility(View.GONE);
						 ll_null_data.setVisibility(View.VISIBLE);
						 lv_content.setVisibility(View.GONE);
					}
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
		RefreshDialog.stopProgressDialog();
		CommonUtils.toastMsgLong(mContext, error);
		switch(tag){
		case FLAG_PROJECT_TRANSFER_LIST:
			ll_wifi_off.setVisibility(View.VISIBLE);
			ll_null_data.setVisibility(View.GONE);
			lv_content.setVisibility(View.GONE);
			break;
			default:
				break;
		}
	}
	
	
	
	class MyItenClickListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			if(projectTransferList.get(arg2-1).getPid()!=null)
			{
				Intent intent = new Intent();
				intent.putExtra("transferId", projectTransferList.get(arg2-1).getPid());
				intent.putExtra("borrowId", projectTransferList.get(arg2-1).getBorrowId());
				intent.setClass(mContext, CreditorRightsDetailActivity.class);
				startActivity(intent);
			}
		}
		
	}

	private void updateView() {
		// TODO Auto-generated method stub
		projectTransferAdapter = new ProjectTransferAdapter(mContext,projectTransferList);
		lv_content.setAdapter(projectTransferAdapter);
		lv_content.setOnItemClickListener(new MyItenClickListener());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.wifi_load_again:
			doGetProjectTransferlist();
			break;
			default:
				break;
		}
	}
	
}
