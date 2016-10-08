package com.beyondsoft.ep2p.activity.mine.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import com.beyondsoft.ep2p.BaseFragment;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.mine.activity.ProjectDetailActivity;
import com.beyondsoft.ep2p.activity.mine.activity.ProjectFinishedDetailActivity;
import com.beyondsoft.ep2p.activity.mine.adapter.ProjectFinishAdapter;
import com.beyondsoft.ep2p.activity.mine.fragment.ProjectTransferFragment.MyItenClickListener;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.ProjectFinishInfoBean;
import com.beyondsoft.ep2p.model.ProjectFinishListBean;
import com.beyondsoft.ep2p.model.response.ProjectFinishListResponse;
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
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 项目已完结页面
 * 
 * @author hardy.zhou
 *
 */
public class ProjectFinishFragment extends BaseFragment implements OnClickListener{

	private View mParent;
	private PullToRefreshListView lv_content;
	private LinearLayout ll_wifi_off;
	private LinearLayout ll_null_data;
    private TextView wifi_load_again;
    private BaseService baseService;
    private ArrayList<ProjectFinishInfoBean> projectFinishList = new ArrayList<ProjectFinishInfoBean>();
	private ProjectFinishAdapter projectFinishAdapter;
    private static final int FLAG_PROJECT_FINISHED_LIST = 100;
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
		mParent = inflater.inflate(R.layout.fragment_project_finish, null);
		return mParent;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		baseService = new BaseService();
		initView();
		doGetProjectFinishedList();
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
	
	private void doGetProjectFinishedList(){
	//	RefreshDialog.startProgressDialog(mContext, "",true);
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		params.put("pagesize", "10");
		params.put("page", "1");
		connection(baseService.getStringRequest(FLAG_PROJECT_FINISHED_LIST, URL.URL_PROJECT_FINISHED_LIST, params, this, mContext));
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		disconnect(FLAG_PROJECT_FINISHED_LIST);
		RefreshDialog.stopProgressDialog();
	}
	
	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		super.onResponse(tag, values);
		RefreshDialog.stopProgressDialog();
		switch(tag){
		case FLAG_PROJECT_FINISHED_LIST:
			ProjectFinishListResponse response =  gson.fromJson(values.toString(), ProjectFinishListResponse.class);
			if(response!=null){
				ProjectFinishListBean bean = response.getProjectFinishListBean();
				if(bean!=null){
					projectFinishList = bean.getProjectFinishList();
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
		case FLAG_PROJECT_FINISHED_LIST:
			ll_wifi_off.setVisibility(View.VISIBLE);
			ll_null_data.setVisibility(View.GONE);
			lv_content.setVisibility(View.GONE);
			break;
			default:
				break;
		}
	}
	

	private void updateView() {
		// TODO Auto-generated method stub
		projectFinishAdapter = new ProjectFinishAdapter(mContext, projectFinishList);
		lv_content.setAdapter(projectFinishAdapter);
		lv_content.setOnItemClickListener(new MyItenClickListener());
	}

	class MyItenClickListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			if(projectFinishList.get(arg2-1).getPid()!=null)
			{
				Intent intent = new Intent();
				intent.putExtra("pid", projectFinishList.get(arg2-1).getPid());
				intent.putExtra("transferId", projectFinishList.get(arg2-1).getTransferId());
				intent.setClass(mContext, ProjectFinishedDetailActivity.class);
				startActivity(intent);
			}
		}
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.wifi_load_again:
			doGetProjectFinishedList();
			break;
			
			default:
				break;
		}
	}
	
	

}
