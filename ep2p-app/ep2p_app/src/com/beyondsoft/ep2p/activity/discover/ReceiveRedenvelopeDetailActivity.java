package com.beyondsoft.ep2p.activity.discover;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.LoginActivity;
import com.beyondsoft.ep2p.activity.discover.adapter.RedEnvelopDetailAdapter;
import com.beyondsoft.ep2p.activity.mine.adapter.ProjectBiddingAdapter;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.response.SelectReceiveInterestDetailResponse;
import com.beyondsoft.ep2p.model.response.SelectReceiveInterestDetailResponse.Result.InterestDetailListItem;
import com.beyondsoft.ep2p.model.response.SelectReceiveRedDetailResponse;
import com.beyondsoft.ep2p.model.response.SelectReceiveRedDetailResponse.Result.RedDetailListItem;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.StringRequest2;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

/**
 * @author xiaoliang
 * @description 红包详情界面
 */
public class ReceiveRedenvelopeDetailActivity extends BaseActivity {
	private static final int TAG_SELECT_RED_DETAIL = 15;
	private RelativeLayout title_layout;
	private TextView tv_title;
	private ImageView iv_right_falg;

	private TextView tv_detail_all;
	private PullToRefreshListView lv_content;
	private RedEnvelopDetailAdapter redEnvelopDetailAdapter;
	// private Handler mHandler = new Handler() {
	// public void handleMessage(android.os.Message msg) {
	// switch (msg.what) {
	// case 0: {
	// }
	// break;
	//
	// default:
	// break;
	// }
	// };
	// };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_receive_red_detail);

		initTitle();
		initView();
		initData();
	}

	private void initTitle() {
		title_layout = (RelativeLayout) findViewById(R.id.title_layout);
		title_layout.setBackgroundColor(getResources().getColor(R.color.dis_r_shenghong));
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText("领取明细");
		iv_right_falg = (ImageView) findViewById(R.id.title_right);
		iv_right_falg.setVisibility(View.GONE);
	}

	private void initView() {
		tv_detail_all = (TextView) findViewById(R.id.tv_detail_all);
		lv_content = (PullToRefreshListView) findViewById(R.id.lv_content);
		// redEnvelopDetailAdapter = new RedEnvelopDetailAdapter(this,
		// mHandler,1);
		// lv_content.setAdapter(redEnvelopDetailAdapter);
		// 设置一个侦听器被调用时应该刷新列表。
		lv_content.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// 更新LastUpdatedLabel[时间]
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				// 刷新列表
				selectReceiveRedDetail();
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
		selectReceiveRedDetail();
	}

	/**
	 * 查询红包明细接口
	 */
	private void selectReceiveRedDetail() {
		if (CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN) == null
				|| "".equals(CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN))) {
			pushActivity(LoginActivity.class);
		} else {
			RefreshDialog.startProgressDialog(mContext, "");
			BaseService bService = new BaseService();
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
			params.put("redpacketId", getIntent().getStringExtra("pid"));
			connection(bService.getStringRequest(TAG_SELECT_RED_DETAIL, URL.API_SELECTR_RED_DETAIL, params, this,
					mContext));
		}
	}

	@Override
	public void onResponse(int tag, Object values) {
		super.onResponse(tag, values);
		switch (tag) {
		case TAG_SELECT_RED_DETAIL: {
			RefreshDialog.stopProgressDialog();
			// CommonUtils.toastMsgLong(getApplicationContext(), "查询红包明细成功！");
			SelectReceiveRedDetailResponse selectReceiveRedDetailResponse = (SelectReceiveRedDetailResponse) StringRequest2
					.Json2Object(values.toString(), new TypeToken<SelectReceiveRedDetailResponse>() {
					});
			String alreadyReceiveNumber = selectReceiveRedDetailResponse.getResult().getAlreadyReceiveNumber();
			String repNumber = selectReceiveRedDetailResponse.getResult().getRepNumber();
			String alreadyReceiveAmount = selectReceiveRedDetailResponse.getResult().getAlreadyReceiveAmount();
			String repAmountTotal = selectReceiveRedDetailResponse.getResult().getRepAmountTotal();
			DecimalFormat format = new DecimalFormat("0.00");
			tv_detail_all.setText("已领取" + alreadyReceiveNumber + "/" + repNumber + "，共"
					+ format.format(new BigDecimal(alreadyReceiveAmount)) + "/"
					+ format.format(new BigDecimal(repAmountTotal)) + "元");
			ArrayList<RedDetailListItem> alreadyReceiveList = selectReceiveRedDetailResponse.getResult()
					.getAlreadyReceiveList();
			redEnvelopDetailAdapter = new RedEnvelopDetailAdapter(mContext, alreadyReceiveList, 1);
			lv_content.setAdapter(redEnvelopDetailAdapter);
			lv_content.onRefreshComplete();
		}
			break;

		default:
			break;
		}
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		super.onErrorResponse(tag, error);
		switch (tag) {
		case TAG_SELECT_RED_DETAIL: {
			RefreshDialog.stopProgressDialog();
			CommonUtils.toastMsgLong(getApplicationContext(), "查询红包明细失败..." + error);
		}

		default:
			break;
		}
	}
}
