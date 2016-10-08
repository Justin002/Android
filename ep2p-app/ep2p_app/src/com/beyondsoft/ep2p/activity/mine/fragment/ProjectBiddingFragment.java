package com.beyondsoft.ep2p.activity.mine.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import com.beyondsoft.ep2p.BaseFragment;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.core.MainHolder;
import com.beyondsoft.ep2p.activity.home.ExperienceDetailsActivity;
import com.beyondsoft.ep2p.activity.home.HomeProductDetailsActivity;
import com.beyondsoft.ep2p.activity.home.ProductDetailsActivity;
import com.beyondsoft.ep2p.activity.mine.activity.ProjectDetailActivity;
import com.beyondsoft.ep2p.activity.mine.adapter.ProjectBiddingAdapter;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.ProjectBiddingInfoBean;
import com.beyondsoft.ep2p.model.ProjectBiddingListBean;
import com.beyondsoft.ep2p.model.response.ProjectBiddingListResponse;
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
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 项目招标中页面
 * 
 * @author hardy.zhou
 *
 */
public class ProjectBiddingFragment extends BaseFragment implements OnClickListener{

	private View mParent;
    private BaseService baseService;
	private PullToRefreshListView lv_content;
	private LinearLayout ll_wifi_off;
	private LinearLayout ll_null_data;
	private ArrayList<ProjectBiddingInfoBean> mList = new ArrayList<ProjectBiddingInfoBean>();
	private ProjectBiddingAdapter projectBiddingAdapter;
	private static final int FLAG_PROJECT_BIDDING_LIST = 100;
	private TextView wifi_load_again;
	
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
		mParent = inflater.inflate(R.layout.fragment_project_bidding, null);
		return mParent;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		baseService = new BaseService();
		initView();
		doGetProjectBiddingListRequest();
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
	
	//请求数据后填充至适配器
	private void initData() {
		// TODO Auto-generated method stub
		projectBiddingAdapter = new ProjectBiddingAdapter(mContext, mHandler,mList);
		lv_content.setAdapter(projectBiddingAdapter);
		lv_content.setOnItemClickListener(new MyItenClickListener());
	}
	
	class MyItenClickListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			if(mList.get(arg2-1).getPid()!=null)
			{
				Intent intent = new Intent();
				if("D".equals(mList.get(arg2-1).getBorrowCode().substring(0, 1))){
					//显示四个页面
					intent.putExtra(Constants.EP2P, 2);
					MainHolder.Instance(mContext).setProjectInformation2ejh_pid(mList.get(arg2-1).getPid());
					MainHolder.Instance(mContext).setProjectInformation_Pid(mList.get(arg2-1).getPid());
					intent.putExtra(Constants.EJH_PID, mList.get(arg2-1).getPid());
					intent.putExtra("index_data", arg2+4);
					intent.setClass(mContext, HomeProductDetailsActivity.class);
					startActivity(intent);
				}else if("C".equals(mList.get(arg2-1).getBorrowCode().substring(0, 1)))
				{
					//显示两个页面
					intent.putExtra(Constants.EP2P, 1);
					MainHolder.Instance(mContext).setProjectInformation2ejh_pid(mList.get(arg2-1).getPid());
					intent.putExtra(Constants.EJH_PID, mList.get(arg2-1).getPid());
					intent.putExtra("index_data", arg2+4);
					intent.setClass(mContext, HomeProductDetailsActivity.class);
					startActivity(intent);
				}else if("X".equals(mList.get(arg2-1).getBorrowCode().substring(0, 1))){
					//新手标
					intent.putExtra("borrowId", mList.get(arg2-1).getPid());
					intent.setClass(mContext, ProductDetailsActivity.class);
					startActivity(intent);
					
				}else if("T".equals(mList.get(arg2-1).getBorrowCode().substring(0, 1))){
					//体验标
					intent.putExtra("borrowId", mList.get(arg2-1).getPid());
					intent.setClass(mContext, ExperienceDetailsActivity.class);
					startActivity(intent);
				}
			}
		}
	}
	
	//请求招标中列表数据
	private void doGetProjectBiddingListRequest(){
	//	RefreshDialog.startProgressDialog(mContext, "",true);
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		params.put("pagesize", "10");
		params.put("page", "1");
		connection(baseService.getStringRequest(FLAG_PROJECT_BIDDING_LIST, URL.URL_PROJECT_BIDDING_LIST, params, this, mContext));
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		disconnect(FLAG_PROJECT_BIDDING_LIST);
		RefreshDialog.stopProgressDialog();
	}
	
	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		super.onResponse(tag, values);
		RefreshDialog.stopProgressDialog();
		switch(tag)
		{
		case FLAG_PROJECT_BIDDING_LIST:
		  {
			 ProjectBiddingListResponse projectBiddingListResponse = gson.fromJson(values.toString(), ProjectBiddingListResponse.class);
		     if(projectBiddingListResponse!=null){
		    	ProjectBiddingListBean listBean =  projectBiddingListResponse.getProjectBiddinglistBean();
		    	if(listBean!=null){
		    		ArrayList<ProjectBiddingInfoBean> list = listBean.getProjectBiddingInfoList();
		    		if(listBean.getTatol()>0){
		    			ll_wifi_off.setVisibility(View.GONE);
						ll_null_data.setVisibility(View.GONE);
						lv_content.setVisibility(View.VISIBLE);
		    			mList = list;
		    			initData();
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
		RefreshDialog.stopProgressDialog();
		switch(tag){
		case FLAG_PROJECT_BIDDING_LIST:
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
			doGetProjectBiddingListRequest();
			break;
			default:
				break;
		}
	}
	

}
