package com.beyondsoft.ep2p.activity.discover;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
//import com.beyondsoft.ep2p.activity.LoginActivity;
import com.beyondsoft.ep2p.activity.discover.adapter.NewerNoviceListAdapter;
import com.beyondsoft.ep2p.common.Constants;
//import com.beyondsoft.ep2p.model.response.NewVoiceGuideContentResponse;
//import com.beyondsoft.ep2p.model.response.NewVoiceGuideContentResponse.Result.GuideContentListItem;
import com.beyondsoft.ep2p.model.response.NewVoiceGuideResponse;
import com.beyondsoft.ep2p.model.response.NewVoiceGuideResponse.Result.GuideListItem;
//import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.Config;
import com.beyondsoft.ep2p.utils.Logs;
//import com.beyondsoft.ep2p.utils.Logs;
//import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author xiaoliang
 * @description 新手指引
 */
public class NewerNoviceActivity extends BaseActivity implements OnItemClickListener {

	private TextView tv_title;
	private ImageView iv_right_falg;
	private static final String webTag = "api_col_noviceGuidePage_t_1";
	private static final int TAG_NEW_VOICE_LIST = 99;
//	private static final int TAG_CONTENT_BY_KEY = 199;
	private PullToRefreshListView lv_content_newnovice;
	private NewerNoviceListAdapter  newerNoviceListAdapter;
	private List<GuideListItem> mList = new ArrayList<GuideListItem>();
	private LinearLayout ll_wifi_off;
	private LinearLayout ll_null_data;
	private int pageCount=0;
	private int isClearAdapter = 0 ;//  0：清理  ,1：不清理
	private int pageIndex = 0; // 分页条数
	private int pageSize = 10;// 当前页显示条数

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newer_novice);
		initView();
		initListener();
		initData();

	}

	private void initView() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText("新手指引");
		iv_right_falg = (ImageView) findViewById(R.id.title_right);
		iv_right_falg.setVisibility(View.GONE);
		lv_content_newnovice = (PullToRefreshListView) findViewById(R.id.lv_content_newnovice);
//		lv_content_newnovice.setMode(Mode.BOTH);
		ll_wifi_off=(LinearLayout) findViewById(R.id.ll_wifi_off);
		ll_null_data=(LinearLayout) findViewById(R.id.ll_null_data);
		newerNoviceListAdapter=new NewerNoviceListAdapter(mList,this);
		lv_content_newnovice.setAdapter(newerNoviceListAdapter);
	}

	private void initData() {
		getNewVoiceList();
	}

	private void initListener() {
		lv_content_newnovice.setOnItemClickListener(this);
//		lv_content_newnovice.setOnRefreshListener(new OnRefreshListener2<ListView>() {
//
//			@Override
//			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//				pageIndex=0;
//				String label = DateUtils.formatDateTime(mContext, System.currentTimeMillis(),
//						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
//				// 更新LastUpdatedLabel[时间]
//				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
//				getNewVoiceList();
//			}
//
//			@Override
//			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//				getNewVoiceList();
//			}
//		});

		 // 设置一个侦听器被调用时应该刷新列表。
		lv_content_newnovice.setOnRefreshListener(new OnRefreshListener<ListView>()
        {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView)
            {
                String label = DateUtils.formatDateTime(getApplicationContext(),
                    System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
                        | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // 更新LastUpdatedLabel[时间]
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                pageIndex = 0; 
                pageSize = 10;
                isClearAdapter = 0;
                getNewVoiceList();
            }
        });

        // 添加一个end-of-list侦听器
		lv_content_newnovice.setOnLastItemVisibleListener(new OnLastItemVisibleListener()
        {

            @Override
            public void onLastItemVisible()
            {
                Logs.d("接口总数大小："+pageCount+"\n当前页面大小："+newerNoviceListAdapter.getCount()); 
                if (pageCount > newerNoviceListAdapter.getCount())
                { //有下一页数据
                    addList();
                }
                else
                {
                    CommonUtils.toastMsgShort(mContext, "数据已全部加载完!");
                }
            }
        });
		
	}


	private String isHome = "2"; // 必填0: 首页显示 非0：不在首页显示
	private GuideListItem guideListItem;

	private void getNewVoiceList() {
		RefreshDialog.startProgressDialog(mContext, "");
		SecurityCenterService securityCenterService = new SecurityCenterService();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		params.put("webTag", webTag);
		params.put("pageIndex", pageIndex);
		params.put("pageSize", pageSize);
		params.put("isHome", isHome);
		connection(securityCenterService.newNoticeGuide(TAG_NEW_VOICE_LIST, params, this));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Logs.d("list_id=:" + position);
		 if(position >= 1){
			 position = position - 1;
	        }
		 guideListItem = mList.get(position);
//		getContentByKey(guideListItem.getPid());
		if (guideListItem != null)
			{
				Intent intent = new Intent();
				intent.setClass(mContext, WebViewActivity.class);
				intent.putExtra("title", guideListItem.getTitleName());
				intent.putExtra("bbs_url", Config.getDomainUrl()+guideListItem.getFileUrl());
				intent.putExtra("bbs_url_result", Config.getDomainUrl()+guideListItem.getFileUrl());
				startActivity(intent);
			}
	}

//	private void getContentByKey(String pid) {
//		BaseService baseService = new BaseService();
//		HashMap<String, Object> params = new HashMap<String, Object>();
//		params.put("pid", pid);
//		connection(baseService.getStringRequest(TAG_CONTENT_BY_KEY, URL.URL_GETCONTENTBYKEY, params, mContext));
//	}

	@Override
	public void onResponse(int tag, Object values) {
		super.onResponse(tag, values);
		switch (tag) {
		case TAG_NEW_VOICE_LIST:
			RefreshDialog.stopProgressDialog();
			NewVoiceGuideResponse newVoiceGuideResponse=gson.fromJson(values.toString(), NewVoiceGuideResponse.class);
//			int size=newVoiceGuideResponse.getResult().getResult().size();
			pageCount = newVoiceGuideResponse.getResult().getPageCount();
			boolean isAdd;
			if(pageIndex>0){
				isAdd=true;
			}else{
				isAdd=false;
				mList = newVoiceGuideResponse.getResult().getResult();
			}
			if(mList!=null){
				ll_wifi_off.setVisibility(View.GONE);
				ll_null_data.setVisibility(View.GONE);
				lv_content_newnovice.setVisibility(View.VISIBLE);
				newerNoviceListAdapter.addData(newVoiceGuideResponse.getResult().getResult(), isAdd);
				newerNoviceListAdapter.notifyDataSetChanged();
				lv_content_newnovice.onRefreshComplete();
//				pageIndex++;
			}else{
				ll_wifi_off.setVisibility(View.GONE);
				ll_null_data.setVisibility(View.VISIBLE);
				lv_content_newnovice.setVisibility(View.GONE);
			}
			break;
//		case TAG_CONTENT_BY_KEY: {
//			NewVoiceGuideContentResponse contentResponse=gson.fromJson(values.toString(), NewVoiceGuideContentResponse.class);
//			if (contentResponse != null)
//			{
//				GuideContentListItem guideContentListItem = contentResponse.getResult().getResult().get(0);
//				bbs_url_result=contentResponse.getResult().getBbsResult();
//				Intent intent = new Intent();
//				intent.setClass(mContext, WebViewActivity.class);
//				intent.putExtra("title", getString(R.string.e_community));
//				intent.putExtra("bbs_url", bbs_url);
//				intent.putExtra("bbs_url_result", bbs_url_result);
//				startActivity(intent);
//			}
//			CommonUtils.toastMsgLong(mContext, "成功！");
//		}
//		break;
		}
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
		switch (tag) {
		case TAG_NEW_VOICE_LIST:
			CommonUtils.toastMsgLong(mContext, "失败" + error);
			ll_wifi_off.setVisibility(View.VISIBLE);
			ll_null_data.setVisibility(View.GONE);
			lv_content_newnovice.setVisibility(View.GONE);
			RefreshDialog.stopProgressDialog();
			break;
//		case TAG_CONTENT_BY_KEY: {
//			CommonUtils.toastMsgLong(mContext, "失败" + error);
//		}
//		break;
		}
	}

	 private void addList()
	    {
	        if (mList.size() > 0 && !mList.isEmpty())
	        {
	            isClearAdapter = 1;
	            pageIndex += 1;
	            //刷新列表
	            getNewVoiceList();
	        }
	    }
}
