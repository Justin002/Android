package com.beyondsoft.ep2p.activity.mine.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import com.beyondsoft.ep2p.BaseFragment;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.mine.activity.CreditorRightsDetailActivity;
import com.beyondsoft.ep2p.activity.mine.adapter.CreditorRightsHaveAdapter;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.CreditorRightsInfoBean;
import com.beyondsoft.ep2p.model.CreditorRightsListBean;
import com.beyondsoft.ep2p.model.response.CreditorRightsListResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.URL;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 债券持有页面
 * 
 * @author hardy.zhou
 *
 */
public class CreditorRightsHaveFragment extends BaseFragment {

	private View mParent;

	private ListView lv_content;
	private CreditorRightsHaveAdapter creditorRightsHaveAdapter;

	private final String TYPE = "1";

	private int mPage = 1;
	private int mPageSize = 10;

	private BaseService baseService;

	private ArrayList<CreditorRightsInfoBean> mCreditorRightsInfoBeanList;

	private static final int TAG_CREDITOR_RIGHTS_LIST = 100;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: {
				Intent intent = new Intent();
				intent.setClass(mContext, CreditorRightsDetailActivity.class);
//				intent.putExtra(CreditorRightsDetailActivity.FLAG_STATE, CreditorRightsDetailActivity.STATE_HAVE);
				startActivity(intent);
			}
				break;

			default:
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mParent = inflater.inflate(R.layout.fragment_creditor_right_have, null);
		return mParent;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		baseService = new BaseService();
		initView();
		//doGetCredotorRightsListRequest();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		lv_content = (ListView) mParent.findViewById(R.id.lv_content);
		creditorRightsHaveAdapter = new CreditorRightsHaveAdapter(mContext, mHandler);
		lv_content.setAdapter(creditorRightsHaveAdapter);
	}

	/**
	 * 执行获取债权列表请求
	 */
	private void doGetCredotorRightsListRequest() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		params.put("transferType", TYPE);
		params.put("pageNum", "" + mPage);
		params.put("pageSize", "" + mPageSize);
		connection(
				baseService.getStringRequest(TAG_CREDITOR_RIGHTS_LIST, URL.URL_CREDITOR_RIGHTS_LIST, params, this, mContext));
	}

	@Override
	public void onResponse(int tag, Object values) {
		super.onResponse(tag, values);
		switch (tag) {
		case TAG_CREDITOR_RIGHTS_LIST: {
			CreditorRightsListResponse creditorRightsListResponse = gson.fromJson(values.toString(),
					CreditorRightsListResponse.class);
			if (null != creditorRightsListResponse) {
				CreditorRightsListBean creditorRightsListBean = creditorRightsListResponse.getCreditorRightsListBean();
				if (null != creditorRightsListBean) {
					ArrayList<CreditorRightsInfoBean> tempCreditorRightsInfoBeanList = creditorRightsListBean
							.getCreditorRightsInfoBeanList();
					if (null != tempCreditorRightsInfoBeanList) {
						mCreditorRightsInfoBeanList = tempCreditorRightsInfoBeanList;
					}
					creditorRightsHaveAdapter.setData(mCreditorRightsInfoBeanList);
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
		super.onErrorResponse(tag, error);
		switch (tag) {
		case TAG_CREDITOR_RIGHTS_LIST: {
          Toast.makeText(mContext, "1返回参数错误", 3000).show();
		}
			break;
		default:
			break;
		}
	}

}
