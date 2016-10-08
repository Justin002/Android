package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.mine.adapter.TradinglistAdapter;
import com.beyondsoft.ep2p.model.TradingInfoBean;
import com.beyondsoft.ep2p.model.TradingListBean;
import com.beyondsoft.ep2p.model.response.TradingListResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

/**
 * @author hanxiaohui
 * @description 交易记录
 */
public class TradingListActivity extends BaseActivity implements OnClickListener {

	private TextView tv_title;
	private TextView wifi_load_again;
	private LinearLayout ll_wifi_off;
	private LinearLayout ll_null_data;
	private PullToRefreshListView mTradingListView;
	public List<TradingInfoBean> listDataResource;
	TradinglistAdapter mAdapter;

	private static final int TAG_TRADING_LIST = 500;

	BaseService baseService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trading_list);
		baseService = new BaseService();
		initTitle();
		initView();
		initData();
		setListener();
	}

	/**
	 * 执行获取交易记录列表请求
	 */
	private void doGetTradingListRequest() {
		RefreshDialog.startProgressDialog(mContext, "");
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("token", "token");
		connection(baseService.getStringRequest(TAG_TRADING_LIST, URL.URL_TRADING_LIST, params, mContext));

	}

	private void initTitle() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText("交易记录");
	}

	private void initView() {
		wifi_load_again = (TextView) findViewById(R.id.wifi_load_again);
		wifi_load_again.setOnClickListener(this);
		ll_wifi_off = (LinearLayout) findViewById(R.id.ll_wifi_off);
		ll_null_data = (LinearLayout) findViewById(R.id.ll_null_data);
		mTradingListView = (PullToRefreshListView) findViewById(R.id.trading_list_rl);
		mTradingListView.setDividerDrawable(new ColorDrawable(Color.parseColor("#bbbbbb")));
	}

	private void initData() {
		doGetTradingListRequest();
	}

	private void setListener() {
		// 设置一个侦听器被调用时应该刷新列表。
		mTradingListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// 更新LastUpdatedLabel[时间]
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				// 刷新列表
				doGetTradingListRequest();
			}
		});

		// 添加一个end-of-list侦听器
		mTradingListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				CommonUtils.toastMsgShort(mContext, "数据已全部加载完!");
			}
		});
	}

	@Override
	public void onResponse(int tag, Object values) {
		super.onResponse(tag, values);
		switch (tag) {
		case TAG_TRADING_LIST:
			RefreshDialog.stopProgressDialog();
			TradingListResponse tradingListResponse = gson.fromJson(values.toString(), TradingListResponse.class);
			TradingListBean tradingListBean = tradingListResponse.getTradingListBean();
			ArrayList<TradingInfoBean> tradingInfoBean = tradingListBean.getTradingInfoBeanList();
			if (tradingInfoBean != null) {
				ll_wifi_off.setVisibility(View.GONE);
				ll_null_data.setVisibility(View.GONE);
				mTradingListView.setVisibility(View.VISIBLE);
				listDataResource = tradingInfoBean;
				mAdapter = new TradinglistAdapter(this, listDataResource);
				mTradingListView.setAdapter(mAdapter);
				mTradingListView.onRefreshComplete();
			} else {
				ll_wifi_off.setVisibility(View.GONE);
				ll_null_data.setVisibility(View.VISIBLE);
				mTradingListView.setVisibility(View.GONE);
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		super.onErrorResponse(tag, error);
		RefreshDialog.stopProgressDialog(); // #TODO 取消Dialog[Error]
		switch (tag) {
		case TAG_TRADING_LIST:
			// Log.d(TAG, error);
			CommonUtils.toastMsgLong(mContext, error);
			ll_wifi_off.setVisibility(View.VISIBLE);
			ll_null_data.setVisibility(View.GONE);
			mTradingListView.setVisibility(View.GONE);
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.wifi_load_again:
			doGetTradingListRequest();
			break;

		default:
			break;
		}

	}
}
