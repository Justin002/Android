package com.beyondsoft.ep2p.activity.mine.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import com.beyondsoft.ep2p.BaseFragment;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.adapter.CardPackageUseAdapter;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.CardUsedInfoBean;
import com.beyondsoft.ep2p.model.CardUsedListBean;
import com.beyondsoft.ep2p.model.response.CardUsedListResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
 * 卡券可使用
 * 
 * @author hardy.zhou
 *
 */
public class CardUseFragment extends BaseFragment implements OnClickListener{

	private View mParent;
	private PullToRefreshListView lv_content;
	private LinearLayout ll_wifi_off;
	private LinearLayout ll_null_data;
	private  CardPackageUseAdapter cardPackageUseAdapter;
    private BaseService baseService;
    private static final int TAG_CARD_USED_LIST = 100;
    private TextView wifi_load_again;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: {
			}
				break;

			default:
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mParent = inflater.inflate(R.layout.fragment_card_use, null);
		return mParent;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		baseService = new BaseService();
		initView();
		doGetCardUsedListRequest();
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
				doGetCardUsedListRequest();
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
		lv_content =  (PullToRefreshListView) mParent.findViewById(R.id.lv_content_card_use);
		lv_content.setDividerDrawable(new ColorDrawable(Color.parseColor("#bbbbbb")));
		ll_wifi_off = (LinearLayout)mParent.findViewById(R.id.ll_wifi_off);
		ll_null_data = (LinearLayout)mParent.findViewById(R.id.ll_null_data);
		cardPackageUseAdapter = new CardPackageUseAdapter(mContext);
		lv_content.setAdapter(cardPackageUseAdapter);
		wifi_load_again = (TextView) mParent.findViewById(R.id.wifi_load_again);
		wifi_load_again.setOnClickListener(this);
	}
	
	private void doGetCardUsedListRequest(){
		RefreshDialog.startProgressDialog(mContext, "",true);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("token",  CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
	    connection(baseService.getStringRequest(TAG_CARD_USED_LIST, URL.URL_CARD_UNUSED_LIST, params, this, mContext));
	}
	
	
	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		super.onResponse(tag, values);
		RefreshDialog.stopProgressDialog();
		switch(tag)
		{
		case TAG_CARD_USED_LIST:
			CardUsedListResponse cardUsedListResponse = gson.fromJson(values.toString(), CardUsedListResponse.class);
			if(cardUsedListResponse!=null)
			{
			  CardUsedListBean cardUsedListBean = cardUsedListResponse.getCardUsedListBean();
			  if(cardUsedListBean!=null)
			  {
				 ArrayList<CardUsedInfoBean> cardList = cardUsedListBean.getCardUsedListBean();
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
					 cardPackageUseAdapter.setData(cardList);
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
		case TAG_CARD_USED_LIST:
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
			doGetCardUsedListRequest();
			break;
			default:
				break;
		}
	}

}
