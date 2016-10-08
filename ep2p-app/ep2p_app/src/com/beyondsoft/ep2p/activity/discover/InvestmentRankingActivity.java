package com.beyondsoft.ep2p.activity.discover;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.LoginActivity;
import com.beyondsoft.ep2p.activity.discover.adapter.InestmentRankingListAdapter;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.model.response.MyRankingListResponse;
import com.beyondsoft.ep2p.model.response.MyRankingListResponse.MyRankingListItem;
import com.beyondsoft.ep2p.model.response.MyRankingNumberResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.ImageLoadOptions;
import com.beyondsoft.ep2p.utils.StringRequest2;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author xiaoliang
 * @description 投资排行
 */
public class InvestmentRankingActivity extends BaseActivity {
	RelativeLayout first_item_rl;
	RelativeLayout more_rl;
	RelativeLayout ziji_rl;
	private ImageView iv_one;

	private TextView tv_title;
	private ImageView iv_right_falg;
	private TextView e_id;
	private TextView e_name;
	private TextView e_money;

	public static int mypid = 0;
	public List<MyRankingListItem> listData = new ArrayList<MyRankingListItem>();
	public List<MyRankingListItem> listData2 = new ArrayList<MyRankingListItem>();
	private PullToRefreshListView lv_content;
	private InestmentRankingListAdapter<MyRankingListItem> mAdapter;
	private MyRankingListResponse rankingListResponse;

	private static final int TAG_MY_RANK = 1;
	private static final int TAG_MY_RANK_NUM = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invest_ranking);

		initTitle();
		initView();
		initData();
		setLinstener();
	}

	private void setLinstener() {
		more_rl.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (listData.size() == 0) {
					return;
				} else {
					first_item_rl.setVisibility(View.GONE);
					more_rl.setVisibility(View.GONE);
					ziji_rl.setVisibility(View.GONE);

					listData = rankingListResponse.result.list;
					mAdapter = new InestmentRankingListAdapter(mContext, listData);
					lv_content.setAdapter(mAdapter);
					mAdapter.notifyDataSetChanged();
				}
			}
		});

	}

	private void initTitle() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText("投资排行");
		iv_right_falg = (ImageView) findViewById(R.id.title_right);
		iv_right_falg.setVisibility(View.GONE);
	}

	private void initView() {
		first_item_rl = (RelativeLayout) findViewById(R.id.first_item_rl);
		more_rl = (RelativeLayout) findViewById(R.id.more_rl);
		ziji_rl = (RelativeLayout) findViewById(R.id.ziji_rl);
		iv_one = (ImageView) findViewById(R.id.iv_one);
		e_id = (TextView) findViewById(R.id.e_id);
		e_name = (TextView) findViewById(R.id.e_name);
		e_money = (TextView) findViewById(R.id.e_money);
		lv_content = (PullToRefreshListView) findViewById(R.id.lv_content);

		lv_content.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			}

		});
		// 设置一个侦听器被调用时应该刷新列表。
		lv_content.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// 更新LastUpdatedLabel[时间]
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				// 刷新列表
				RefreshDialog.startProgressDialog(mContext, "");
				getMyRankList();
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

	private void initData() {
		if (CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN) == null
				|| "".equals(CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN))) {
			pushActivity(LoginActivity.class);
			finish();
		} else {
			RefreshDialog.startProgressDialog(mContext, "");
			getMyRankList();
			getMyRankNumber();

		}
		// RefreshDialog.startProgressDialog(mContext, "");
		// getMyRankList();
		// getMyRankNumber();
	}

	private void getMyRankList() {
		BaseService baseRanklist = new BaseService();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		connection(baseRanklist.getStringRequest(TAG_MY_RANK, URL.URL_MY_RANKING_LIST, params, this));

	}

	private void getMyRankNumber() {

		BaseService baseRankNum = new BaseService();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		connection(baseRankNum.getStringRequest(TAG_MY_RANK_NUM, URL.URL_MY_RANGING_NUMBER, params, this));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void onResponse(int tag, Object values) {
		super.onResponse(tag, values);
		switch (tag) {
		case TAG_MY_RANK: {
			RefreshDialog.stopProgressDialog();
			rankingListResponse = (MyRankingListResponse) StringRequest2.Json2Object(values.toString(),
					new TypeToken<MyRankingListResponse>() {
					});
			listData = rankingListResponse.result.list;
			e_money.setText("￥" + listData.get(mypid).investmentAmount);
			if (listData.size() >= 7) {
				listData2 = listData.subList(0, 7);
			} else {
				listData2 = listData;
			}
			mAdapter = new InestmentRankingListAdapter(this, listData2);
			lv_content.setAdapter(mAdapter);
			lv_content.onRefreshComplete();
		}
			break;

		case TAG_MY_RANK_NUM: {
			RefreshDialog.stopProgressDialog();
			MyRankingNumberResponse myRankingNumberResponse = gson.fromJson(values.toString(),
					MyRankingNumberResponse.class);
			mypid = myRankingNumberResponse.result.list;
			// CommonUtils.toastMsgShort(mContext, mypid+"");
			if (mypid <= 7) {
				ziji_rl.setVisibility(View.GONE);
			} else {
				e_id.setText(mypid + "");
				if (iv_one.getTag() == null || !UserPersonalInfo.getImageUrl().equals(iv_one.getTag().toString())) {
					imageLoader.displayImage(UserPersonalInfo.getImageUrl(), iv_one,
							ImageLoadOptions.getCircleOptions());
					iv_one.setTag(UserPersonalInfo.getImageUrl());
				}
			}
		}
			break;
		}
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		super.onErrorResponse(tag, error);
		RefreshDialog.stopProgressDialog();
		switch (tag) {
		case TAG_MY_RANK:
			CommonUtils.toastMsgShort(mContext, error);
			break;

		case TAG_MY_RANK_NUM:
			CommonUtils.toastMsgShort(mContext, "我的排行" + error);
			break;
		}
	}

}
