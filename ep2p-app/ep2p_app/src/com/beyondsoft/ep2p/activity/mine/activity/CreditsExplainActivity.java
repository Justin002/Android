package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.HashMap;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.adapter.CreditsExplanAdapter;
import com.beyondsoft.ep2p.model.response.CreditsExplainResponse;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @author Ivan.Lu
 * @description 积分明细
 */
public class CreditsExplainActivity extends BaseFragmentActivity {
	private static final int POINTDETAILLIST=1;
	private CreditsExplanAdapter creditsExplanAdapter;
	private LinearLayout ll_wifi_off;
	private LinearLayout ll_null_data;
	private PullToRefreshListView credits_lv;
	private boolean isAdd;
	private int mPageIndex=1;
	private int mPageSize=20;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.credits_explain);
		init();
		initListener();
		loadData();
	}
	
	private void init(){
		ll_wifi_off = (LinearLayout)findViewById(R.id.ll_wifi_off);
		ll_null_data = (LinearLayout)findViewById(R.id.ll_null_data);
		setTitle(getResources().getString(R.string.credits_explain));
		creditsExplanAdapter=new CreditsExplanAdapter(this);
		credits_lv=(PullToRefreshListView) findViewById(R.id.credits_lv);
		credits_lv.setMode(Mode.BOTH);
//		ILoadingLayout iLoadingLayout=credits_lv.getLoadingLayoutProxy();
//		iLoadingLayout.setPullLabel("上拉加载");
//		iLoadingLayout.setReleaseLabel("放开以加载...");
		credits_lv.setAdapter(creditsExplanAdapter);
	}
	
	private void initListener(){
		credits_lv.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				credits_lv.setMode(Mode.BOTH);
				mPageIndex=1;
				loadData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				loadData();
			}
		});
	}
	
	private void loadData(){
		SecurityCenterService securityCenterService=new SecurityCenterService();
		HashMap<String,Object> hashMap=new HashMap<String, Object>();
		hashMap.put("pageIndex", mPageIndex);
		hashMap.put("pageSize", mPageSize);
		connection(securityCenterService.getUserPointDetailList(POINTDETAILLIST, hashMap, this));
	}

	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		credits_lv.onRefreshComplete();	
		CreditsExplainResponse creditsExplainResponse=gson.fromJson(values.toString(), CreditsExplainResponse.class);
		int size=creditsExplainResponse.getResult().getList().size();
		if(mPageIndex>1){
			isAdd=true;
		}else{
			isAdd=false;
		}		
		if(isAdd){
			if(size>0&&size==mPageSize){
				if(credits_lv.getVisibility()==View.GONE){
					credits_lv.setVisibility(View.VISIBLE);
				}
				ll_wifi_off.setVisibility(View.GONE);
				ll_null_data.setVisibility(View.GONE);
			}else{
				credits_lv.setMode(Mode.PULL_FROM_START);
			}
		}else{
			if(size<1){			
				ll_wifi_off.setVisibility(View.GONE);
				ll_null_data.setVisibility(View.VISIBLE);
				credits_lv.setVisibility(View.GONE);
			}
		}
		mPageIndex++;
		creditsExplanAdapter.addData(creditsExplainResponse.getResult().getList(),isAdd);
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		super.onErrorResponse(tag, error);
		if(!isAdd){
			ll_wifi_off.setVisibility(View.VISIBLE);
			ll_null_data.setVisibility(View.GONE);
			credits_lv.setVisibility(View.GONE);
		}
	}
}
