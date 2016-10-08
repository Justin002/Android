package com.beyondsoft.ep2p.activity.mine.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import com.beyondsoft.ep2p.BaseFragment;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.mine.activity.ProjectDetailActivity;
import com.beyondsoft.ep2p.activity.mine.adapter.ProjectWaittingAdapter;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.ProjectWaitInfoBean;
import com.beyondsoft.ep2p.model.ProjectWaitInfoListBean;
import com.beyondsoft.ep2p.model.response.ProjectWaitListResponse;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 项目待收中页面
 * 
 * @author hardy.zhou
 *
 */
public class ProjectWaittingFragment extends BaseFragment implements OnClickListener{

	private View mParent;
    private BaseService baseService;
	private PullToRefreshListView lv_content;
	private LinearLayout ll_wifi_off;
	private LinearLayout ll_null_data;
	private TextView wifi_load_again;
	private ProjectWaittingAdapter projectWaittingAdapter;
    private static final int FLAG_PROJECT_WAIT_LIST = 100;
    private ArrayList<ProjectWaitInfoBean> projectWaitList = new ArrayList<ProjectWaitInfoBean>();
    private ArrayList<ProjectWaitInfoBean> projectTransferWaitList = new ArrayList<ProjectWaitInfoBean>();
    private String pid;
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
		mParent = inflater.inflate(R.layout.fragment_project_waitting, null);
		return mParent;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		baseService = new BaseService();
		initView();
		doGetProjectWaitListRequest();
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
	//请求已投-待收列表数据
	public void doGetProjectWaitListRequest(){
		//RefreshDialog.startProgressDialog(mContext, "",true);
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		params.put("pagesize", 10);
		params.put("page", 1);
		connection(baseService.getStringRequest(FLAG_PROJECT_WAIT_LIST, URL.URL_PROJECT_WAIT_LIST, params, this, mContext));
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		disconnect(FLAG_PROJECT_WAIT_LIST);
		RefreshDialog.stopProgressDialog();
	}
	
	//获取可转让列表并展示
	public void showTransferList(){
		//ArrayList<ProjectWaitInfoBean> projectTransferWaitList = new ArrayList<ProjectWaitInfoBean>();
		projectTransferWaitList.clear();
		if(projectWaitList!=null)
		{
			for(int i = 0;i<projectWaitList.size();i++){
				if(projectWaitList.get(i).getTransfStatus()==0){
					projectTransferWaitList.add(projectWaitList.get(i));
				}
			}
			if(projectTransferWaitList.size()>0){
				projectWaittingAdapter = new ProjectWaittingAdapter(mContext, mHandler,projectTransferWaitList);
				lv_content.setAdapter(projectWaittingAdapter);
				lv_content.setOnItemClickListener(new MyItemClickListener1());
			}else{
				 ll_wifi_off.setVisibility(View.GONE);
				 ll_null_data.setVisibility(View.VISIBLE);
				 lv_content.setVisibility(View.GONE);
			}	
		}
		
		
	}
	//数据加载完成后填充数据，绑定点击监听
	private void updateView(){
		projectWaittingAdapter = new ProjectWaittingAdapter(mContext, mHandler,projectWaitList);
		lv_content.setAdapter(projectWaittingAdapter);
		lv_content.setOnItemClickListener(new MyItemClickListener());
	}
	
	//单击监听内部类
	class MyItemClickListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			if(projectWaitList.get(arg2-1).getPid()!=null)
			{
				Intent intent = new Intent();
				intent.putExtra("pid", projectWaitList.get(arg2-1).getPid());
				intent.setClass(mContext, ProjectDetailActivity.class);
				startActivity(intent);
			}
		}
		
	}
	
	//单击监听内部类
		class MyItemClickListener1 implements OnItemClickListener{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(projectTransferWaitList.get(arg2-1).getPid()!=null)
				{
					Intent intent = new Intent();
					intent.putExtra("pid", projectTransferWaitList.get(arg2-1).getPid());
					intent.setClass(mContext, ProjectDetailActivity.class);
					startActivity(intent);
				}
			}
			
		}
	
	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		super.onResponse(tag, values);
		switch(tag)
		{
		case FLAG_PROJECT_WAIT_LIST:
			RefreshDialog.stopProgressDialog();
			ProjectWaitListResponse projectWaitListResponse = gson.fromJson(values.toString(), ProjectWaitListResponse.class);
		    if(projectWaitListResponse!=null)
		    {
		    	ProjectWaitInfoListBean projectWaitInfoListBean= projectWaitListResponse.getProjectWaitInfoListBean();
		    	if(projectWaitInfoListBean!=null)
		    	{
		    		projectWaitList = projectWaitInfoListBean.getProjectWaitInfoList();
		    		if(projectWaitInfoListBean.getTatol()>0){
		    			ll_wifi_off.setVisibility(View.GONE);
						ll_null_data.setVisibility(View.GONE);
						lv_content.setVisibility(View.VISIBLE);
		    			updateView();
		    		}else{
		    			 ll_wifi_off.setVisibility(View.GONE);
						 ll_null_data.setVisibility(View.VISIBLE);
						 lv_content.setVisibility(View.GONE);
		    		}
		    		
		    	}
		    }
		}
	}
	
	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
		RefreshDialog.stopProgressDialog();
		CommonUtils.toastMsgLong(mContext, error);
		switch(tag){
		case FLAG_PROJECT_WAIT_LIST:
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
			doGetProjectWaitListRequest();
			break;
			default:
				break;
		}
	}

}
