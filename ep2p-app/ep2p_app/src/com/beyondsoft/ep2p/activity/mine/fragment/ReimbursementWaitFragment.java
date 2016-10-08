package com.beyondsoft.ep2p.activity.mine.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.beyondsoft.ep2p.BaseFragment;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.mine.activity.ReimbursementWaitDetailActivity;
import com.beyondsoft.ep2p.activity.mine.activity.ReimbursementActivity;
import com.beyondsoft.ep2p.activity.mine.activity.ReimbursementForProfit;
import com.beyondsoft.ep2p.activity.mine.activity.ReimbursementSuccessActivity;
import com.beyondsoft.ep2p.activity.mine.adapter.ReimbursementWaitAdapter;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.ReimbursementWaitInfoBean;
import com.beyondsoft.ep2p.model.ReimbursementWaitListBean;
import com.beyondsoft.ep2p.model.response.ReimbursementWaitResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.beyondsoft.ep2p.view.gesture.PreferencesUtils;
import com.beyondsoft.ep2p.widget.PayPasswrodDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 待还款页面
 * 
 * @author hardy.zhou
 *
 */
public class ReimbursementWaitFragment extends BaseFragment implements OnClickListener{

	private View mParent;
    private BaseService baseService;
    private PullToRefreshListView lv_content;
    private LinearLayout ll_wifi_off;
	private LinearLayout ll_null_data;
	private Button bt_reimbursementforprofit;
	private int currentPage=1;
	private boolean isDelaiedOccured = false;
	private ArrayList<ReimbursementWaitInfoBean> reimbursementWaitList = new ArrayList<ReimbursementWaitInfoBean>();
	private ReimbursementWaitAdapter reimbursementWaitAdapter;
    private static final int FLAG_REIMBURSEMENT_WAIT_LIST = 100;
    private TextView wifi_load_again;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: {
				/*Intent intent = new Intent();
				intent.setClass(mContext, ReimbursementWaitDetailActivity.class);
				startActivity(intent);*/
			}
				break;
			case 1: {
				final PayPasswrodDialog clearDialog1 = new PayPasswrodDialog(mContext);
				clearDialog1.show();
				clearDialog1.setButtonClickListener(new PayPasswrodDialog.ButtonOnClickListener() {
					@Override
					public void onButton1Click(View v) {
						clearDialog1.dismiss();
						String pay_pass = PreferencesUtils.getString(mContext, Constants.EP2P_INPUT_PAY);
						if (!TextUtils.isEmpty(pay_pass)) {
							Intent intent = new Intent();
							intent.setClass(mContext, ReimbursementSuccessActivity.class);
							startActivity(intent);
						} else {
							CommonUtils.toastMsgShort(mContext, "请输入正确的密码");
						}
					};
				});
				showSoftKeyBoard();

			}
				break;
			default:
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mParent = inflater.inflate(R.layout.fragment_reimbursement_wait1, null);
		return mParent;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		baseService = new BaseService();
		initView();
		doGetReimbersementWaitList();
		setListener();
	}
	
	
	
	//请求待还款列表数据
	public void doGetReimbersementWaitList(){
	//	RefreshDialog.startProgressDialog(mContext, "",true);
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		params.put("pageSize", 10);
		params.put("pageIndex", currentPage);
	    connection(baseService.getStringRequest(FLAG_REIMBURSEMENT_WAIT_LIST, URL.URL_REIMBURSEMENT_WAIT_LIST, params, this, mContext));
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		disconnect(FLAG_REIMBURSEMENT_WAIT_LIST);
		RefreshDialog.stopProgressDialog();
	}
	/**
	 * 初始化组件
	 */
	private void initView() {
		lv_content = (PullToRefreshListView) mParent.findViewById(R.id.lv_content_reimbursement_wait);
		bt_reimbursementforprofit = (Button) mParent.findViewById(R.id.bt_reimbursementforprofit);
		ll_wifi_off = (LinearLayout)mParent.findViewById(R.id.ll_wifi_off);
		ll_null_data = (LinearLayout)mParent.findViewById(R.id.ll_null_data);
		bt_reimbursementforprofit.setOnClickListener(this);
		reimbursementWaitAdapter = new ReimbursementWaitAdapter(mContext, mHandler);
		lv_content.setAdapter(reimbursementWaitAdapter);
		wifi_load_again = (TextView) mParent.findViewById(R.id.wifi_load_again);
		wifi_load_again.setOnClickListener(this);
	}
	
	//列表项点击监听内部类
	class MyItemClicklistenenr implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(mContext, ReimbursementWaitDetailActivity.class);
			intent.putExtra("pid", reimbursementWaitList.get(arg2-1).getPid());
			startActivity(intent);
		}
		
	}
	

	/**
	 * 显示软键盘
	 */
	private void showSoftKeyBoard() {
		{
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					InputMethodManager inputMethodManager = (InputMethodManager) mContext
							.getSystemService(mContext.INPUT_METHOD_SERVICE);
					inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
				}
			}, 300);
		}

	}
	//判断是否有逾期发生
	private boolean isDelaiedOccured(){
		for(int i = 0;i<reimbursementWaitList.size();i++){
			if(!reimbursementWaitList.get(i).getReceiptPalnStatus().equals("1")){
				isDelaiedOccured=true;
			}
		}
		
		return isDelaiedOccured;
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
				doGetReimbersementWaitList();
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
	
	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		super.onResponse(tag, values);
		RefreshDialog.stopProgressDialog();
		switch(tag)
		{
		case FLAG_REIMBURSEMENT_WAIT_LIST:
		ReimbursementWaitResponse response = gson.fromJson(values.toString(),ReimbursementWaitResponse.class);
		if(response!=null)
		{
			ReimbursementWaitListBean listBean = response.getReibuersementWaitListbean();
			if(listBean!=null){
				reimbursementWaitList = listBean.getReimbursementWaitList();
				if(reimbursementWaitList.size()>0){
					ll_wifi_off.setVisibility(View.GONE);
					ll_null_data.setVisibility(View.GONE);
					lv_content.setVisibility(View.VISIBLE);
					CommonUtils.addConfigInfo(mContext, "borrowId", reimbursementWaitList.get(0).getBorrowId());
					reimbursementWaitAdapter.setData(reimbursementWaitList);
					lv_content.setOnItemClickListener(new MyItemClicklistenenr());
					lv_content.onRefreshComplete();
					bt_reimbursementforprofit.setVisibility(View.VISIBLE);
					ReimbursementActivity myReimbursementActivity =  (ReimbursementActivity)getActivity();
					myReimbursementActivity.showRightTitle();
					if(isDelaiedOccured)
					{
						bt_reimbursementforprofit.setText("为逾期还款");
					}else
					{
						bt_reimbursementforprofit.setText("为当期还款");
					}
					
				}else{
					 ll_wifi_off.setVisibility(View.GONE);
					 ll_null_data.setVisibility(View.VISIBLE);
					 lv_content.setVisibility(View.GONE);
					 ReimbursementActivity myReimbursementActivity =  (ReimbursementActivity)getActivity();
					 myReimbursementActivity.hideRightTitle();
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
		case FLAG_REIMBURSEMENT_WAIT_LIST:
			CommonUtils.toastMsgLong(mContext, error);
			ll_wifi_off.setVisibility(View.VISIBLE);
			ll_null_data.setVisibility(View.GONE);
			lv_content.setVisibility(View.GONE);
			ReimbursementActivity myReimbursementActivity =  (ReimbursementActivity)getActivity();
			 myReimbursementActivity.hideRightTitle();
			break;

		default:
			break;
		}
	}
	

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.bt_reimbursementforprofit:
			if(reimbursementWaitList.size()==0){
				CommonUtils.toastMsgShort(mContext, "当前无待还款项！");
			}else{
				Intent intent = new Intent();
				intent.setClass(mContext, ReimbursementForProfit.class);
				intent.putExtra("borrowId", reimbursementWaitList.get(0).getBorrowId());
				startActivity(intent);
			}
			break;
		case R.id.wifi_load_again:
			doGetReimbersementWaitList();
			break;
			default: 
				break;
		}
		
		
	}

}
