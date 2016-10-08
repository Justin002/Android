package com.beyondsoft.ep2p.activity.discover;
import java.util.HashMap;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.discover.adapter.MusicListAdapter;
import com.beyondsoft.ep2p.model.response.RatioStationListResponse;
import com.beyondsoft.ep2p.model.response.RatioStationListResponse.Result.RadioListItem;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.Logs;
import com.beyondsoft.ep2p.utils.Player;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @author Ivan.Lu
 * @description 电台列表
 */
public class RatioStationListActivity extends BaseActivity implements OnClickListener,OnItemClickListener{

	private static final int RADIO_LIST = 0;
	private TextView tv_img_icon_zhan;
	private ImageView iv_right_falg;
	private MusicListAdapter musicListAdapter;
	private PullToRefreshListView mListView;
	private Player player;
	private int mPageNum=1;
	private int mPageSize=20;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ratio_station_listl);
		registerReceiver(broadcastReceiver,new IntentFilter("com.beyondsoft.ep2p.startplay"));
		player=Player.getInstance(this);
		init();
		initListener();
		initData();
		getRadioList();
	}
	
	private BroadcastReceiver broadcastReceiver=new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			onResume();
		}
	};

	private void init() {
		iv_right_falg = (ImageView) findViewById(R.id.title_right);
		iv_right_falg.setVisibility(View.GONE);
		mListView=(PullToRefreshListView) findViewById(R.id.lv_content);
		mListView.setMode(Mode.PULL_FROM_END);
	}
	
	private void initListener(){
		iv_right_falg.setOnClickListener(this);
		mListView.setOnItemClickListener(this);
		mListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				Logs.e("下");
				getRadioList();
			}
		});
		//tv_img_icon_zhan.setOnClickListener(this);
	}
	
	private void initData(){
		setTitle(getString(R.string.dis_e_station));
		//View view=LayoutInflater.from(this).inflate(R.layout.radio_list_top_view,null);
		musicListAdapter=new MusicListAdapter(this);
		//mListView.getRefreshableView().addHeaderView(view);
		mListView.setAdapter(musicListAdapter);		
	}
	
	private void getRadioList(){
		SecurityCenterService securityCenterService = new SecurityCenterService();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pageNum", mPageNum);
		params.put("pageSize", mPageSize);
		connection(securityCenterService.getRadioList(RADIO_LIST, params, this));
	}
	
	@Override
	public void onResponse(int tag, Object values) {
		//RefreshDialog.stopProgressDialog();
		mListView.onRefreshComplete();
		mPageNum++;
		((RelativeLayout)findViewById(R.id.title_layout)).setBackgroundColor(0x00000000);
		RatioStationListResponse ratioStationListResponse=gson.fromJson(values.toString(), RatioStationListResponse.class);
		musicListAdapter.addData(ratioStationListResponse.getResult().getList());
		if(ratioStationListResponse.getResult().getPageCount()==musicListAdapter.getCount()){
			mListView.setMode(Mode.DISABLED);
		}
	}

	@Override
	public void onErrorResponse(int tag, String error) {
		super.onErrorResponse(tag, error);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_right:
			pushActivityForResult(new Intent(this, RatioStationPlayActivity.class), 1);
			break;
//		case R.id.tv_img_icon_zhan:
//			if (!isDianZhan) {
//				AdianzhanPopupWindow sharePopupWindowLine = new AdianzhanPopupWindow(
//						mContext);
//				sharePopupWindowLine.showWindow();
//				sharePopupWindowLine
//						.setOnDismissListener(new OnDismissListener() {
//
//							@Override
//							public void onDismiss() {
//							}
//						});
//				sharePopupWindowLine.showAtLocation(
//						findViewById(R.id.title_content_tv), Gravity.TOP, 0, 0);
//				isDianZhan = true;
//			} else {
//				tv_img_icon_zhan.setClickable(false);
//			}
//
//			break;
		}
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(player.isPlaying()){
			iv_right_falg.setVisibility(View.VISIBLE);
			Animation animation=AnimationUtils.loadAnimation(this,R.anim.circle);
			animation.setInterpolator(new LinearInterpolator());
			iv_right_falg.startAnimation(animation);
		}else{
			iv_right_falg.setVisibility(View.GONE);
			iv_right_falg.clearAnimation();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		pushActivityForResult(new Intent(this,RatioStationPlayActivity.class).putExtra("radioInfo", musicListAdapter.getRadioItem(position-1)),1);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			if(resultCode==Activity.RESULT_OK){
				RadioListItem  radioListItem=(RadioListItem) data.getSerializableExtra("item");
				musicListAdapter.refresh(radioListItem);
			}
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(broadcastReceiver);
	}
}
