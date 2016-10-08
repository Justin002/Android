package com.beyondsoft.ep2p.activity.mine.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import com.beyondsoft.ep2p.BaseFragment;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.mine.adapter.ProjectBiddingAdapter;
import com.beyondsoft.ep2p.adapter.CardPackageUnuseAdapter;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.CardUnusedInfoBean;
import com.beyondsoft.ep2p.model.CardUnusedListBean;
import com.beyondsoft.ep2p.model.response.CardUnusedListRresponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 卡券已失效
 * 
 * @author hardy.zhou
 *
 */
public class CardUnuseFragment extends BaseFragment implements OnClickListener{

	private Activity mContext;
	private View mParent;
    private BaseService baseService;
	private PullToRefreshListView lv_content;
	private LinearLayout ll_wifi_off;
	private LinearLayout ll_null_data;
	private static final int TAG_CARD_UNUSED_LIST =100;
	private CardPackageUnuseAdapter cardPackageUnuseAdapter;
	private TextView wifi_load_again;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mParent = inflater.inflate(R.layout.fragment_card_unuse, null);
		return mParent;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mContext = getActivity();
		baseService = new BaseService();
		initView();
		doGetCardUnusedList();
		setListener();
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
						doGetCardUnusedList();
					}
				});

				// 添加一个end-of-list侦听器
				lv_content.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						CommonUtils.toastMsgShort(mContext, "数据已全部加载完!");
					}
				});
		
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		lv_content = (PullToRefreshListView) mParent.findViewById(R.id.lv_content_card_unuse);
		ll_wifi_off = (LinearLayout)mParent.findViewById(R.id.ll_wifi_off);
		ll_null_data = (LinearLayout)mParent.findViewById(R.id.ll_null_data);
		cardPackageUnuseAdapter = new CardPackageUnuseAdapter(mContext);
		lv_content.setAdapter(cardPackageUnuseAdapter);
		wifi_load_again = (TextView) mParent.findViewById(R.id.wifi_load_again);
		wifi_load_again.setOnClickListener(this);
	}

	private void doGetCardUnusedList(){
		RefreshDialog.startProgressDialog(mContext, "",true);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("token",  CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		connection(baseService.getStringRequest(TAG_CARD_UNUSED_LIST, URL.URL_CARD_USED_LIST, params, this, mContext));
	}
	

	
	@Override
	public void onResponse(int tag, Object values) 
	{
		// TODO Auto-generated method stub
		super.onResponse(tag, values);
		RefreshDialog.stopProgressDialog();
		switch(tag)
		{
		case TAG_CARD_UNUSED_LIST:
		   CardUnusedListRresponse cardUnusedListResponse = gson.fromJson(values.toString(),CardUnusedListRresponse.class);
		   if(cardUnusedListResponse!=null)
		   {
		      CardUnusedListBean cardUnusedListBean = cardUnusedListResponse.getCardUnusedListBean();
		      if(cardUnusedListBean!=null)
		      {
		    	  ArrayList<CardUnusedInfoBean> cardList =  cardUnusedListBean.getCardUnusedListBean();
		    	  if(cardList.size()<=0)
					 {
						 ll_wifi_off.setVisibility(View.GONE);
						 ll_null_data.setVisibility(View.VISIBLE);
						 lv_content.setVisibility(View.GONE);
					 }else
					 {
						 ll_wifi_off.setVisibility(View.GONE);
						 ll_null_data.setVisibility(View.GONE);
						 lv_content.setVisibility(View.VISIBLE);
						 cardPackageUnuseAdapter.setUnuseData(cardList);
						 lv_content.onRefreshComplete();
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
		switch(tag)
		{
		case TAG_CARD_UNUSED_LIST:
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
			doGetCardUnusedList();
			break;
			default:
				break;
		}
	}
	
	
}
