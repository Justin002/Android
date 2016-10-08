package com.beyondsoft.ep2p.activity.mine;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseFragment;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.LoginActivity;
import com.beyondsoft.ep2p.activity.mine.activity.AutoTenderActivity;
import com.beyondsoft.ep2p.activity.mine.activity.InvestmentedProjectActivity;
/**
 * @author Ivan.Lu
 * @description "我的"模块
 */
import com.beyondsoft.ep2p.activity.mine.activity.InvitePrizeActivity;
import com.beyondsoft.ep2p.activity.mine.activity.MoreActivity;
import com.beyondsoft.ep2p.activity.mine.activity.ReimbursementActivity;
import com.beyondsoft.ep2p.activity.mine.activity.SecurityCenterActivity;
import com.beyondsoft.ep2p.activity.mine.activity.TradingListActivity;
import com.beyondsoft.ep2p.activity.mine.activity.VipLevelActivity;
import com.beyondsoft.ep2p.activity.mine.activity.WaittingRecieveQueryActivity;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.model.response.PersonalInfoResponse;
import com.beyondsoft.ep2p.service.SecurityCenterService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.IDataConnectListener;
import com.beyondsoft.ep2p.utils.ImageLoadOptions;

/**
 * @author Ivan.Lu
 * @description 我的
 */
public class MineFragment extends BaseFragment implements OnClickListener {
	private ImageView user_head_iv;
	private TextView user_name_tv;
	private RelativeLayout title_top_layout;
	private FragmentManager mFragmentManager;
	private Fragment mFragment;;
	private TextView mine_level_tv;
	private ScrollView scroll_view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		IntentFilter intentFilter=new IntentFilter();
		intentFilter.addAction(Constants.UPDATEDATA);
		intentFilter.addAction(Constants.UPDATEUSERINFO);
		getActivity().registerReceiver(broadcastReceiver,intentFilter);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_mine, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		init();
		initListener();
	}
	
	BroadcastReceiver broadcastReceiver=new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(Constants.UPDATEDATA.equals(intent.getAction())){
				loadData();
			}else if(Constants.UPDATEUSERINFO.equals(intent.getAction())){
				connection(new SecurityCenterService().getPersonalInfo(1,(IDataConnectListener) getActivity(),getActivity()));
			}
		}
	};

	private void init() {
		mFragmentManager = getChildFragmentManager();
		title_top_layout=(RelativeLayout) getActivity().findViewById(R.id.title_top_layout);
		scroll_view=(ScrollView) getActivity().findViewById(R.id.scroll_view);
		mine_level_tv=(TextView) getActivity().findViewById(R.id.mine_level_tv);
		user_head_iv=(ImageView) getActivity().findViewById(R.id.user_head_iv);
		user_name_tv=(TextView) getActivity().findViewById(R.id.user_name_tv);
		if(!"".equals(CommonUtils.getStringByKey(getActivity(),Constants.EP2P_TOKEN))){
			title_top_layout.setVisibility(View.VISIBLE);
			mFragment=new MineLoginFragment();
		}else{
			title_top_layout.setVisibility(View.GONE);
			mFragment=new MineNoLoginFragment();
		}
		FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.mine_top_layout,mFragment);
		fragmentTransaction.commit();
	}

	private void initListener() {
		((RelativeLayout) getActivity().findViewById(R.id.mine_drop_project_layout)).setOnClickListener(this);
		((RelativeLayout) getActivity().findViewById(R.id.mine_standby_check_layout)).setOnClickListener(this);
		((RelativeLayout) getActivity().findViewById(R.id.mine_refund_layout)).setOnClickListener(this);
		((RelativeLayout) getActivity().findViewById(R.id.mine_trading_list_layout)).setOnClickListener(this);
		((RelativeLayout) getActivity().findViewById(R.id.mine_auto_tender_layout)).setOnClickListener(this);
		((RelativeLayout) getActivity().findViewById(R.id.mine_security_center_layout)).setOnClickListener(this);
		((RelativeLayout) getActivity().findViewById(R.id.mine_invite_prize_layout)).setOnClickListener(this);
		((RelativeLayout) getActivity().findViewById(R.id.mine_more_layout)).setOnClickListener(this);
		mine_level_tv.setOnClickListener(this);
	}
	
	/**
	 * 加载数据
	 */
	public void loadData() {
		scroll_view.scrollTo(0,0);
		if(!"".equals(CommonUtils.getStringByKey(getActivity(),Constants.EP2P_TOKEN))){
			user_name_tv.setText(UserPersonalInfo.getCustomerName());
			mine_level_tv.setText(UserPersonalInfo.getVipLevelName());
			if(user_head_iv.getTag()==null||!UserPersonalInfo.getImageUrl().equals(user_head_iv.getTag().toString())){
				imageLoader.displayImage(UserPersonalInfo.getImageUrl(), user_head_iv,ImageLoadOptions.getCircleOptions());
				user_head_iv.setTag(UserPersonalInfo.getImageUrl());
			}
			if("0".equals(UserPersonalInfo.getIsVip())){
				mine_level_tv.setBackgroundResource(R.drawable.vip_level_no_btn);
			}else if ("1".equals(UserPersonalInfo.getIsVip())) {
				mine_level_tv.setBackgroundResource(R.drawable.vip_level_btn);
			}
			mFragment=new MineLoginFragment();
			FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.mine_top_layout,mFragment);
			fragmentTransaction.commitAllowingStateLoss();
			title_top_layout.setVisibility(View.VISIBLE);
		}else{
			mFragment=new MineNoLoginFragment();
			FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.mine_top_layout,mFragment);
			fragmentTransaction.commitAllowingStateLoss();
			title_top_layout.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {			
		case R.id.mine_drop_project_layout:
			if(!"".equals(CommonUtils.getStringByKey(getActivity(),Constants.EP2P_TOKEN))){
				startActivity(new Intent(getActivity(), InvestmentedProjectActivity.class));			
			}else{
				//CommonUtils.toastMsgShort(getActivity(), getResources().getString(R.string.login_hint));
				pushActivityForResult(LoginActivity.class,MineNoLoginFragment.LOGIN_REQUESTCODE);
			}
			break;
		case R.id.mine_standby_check_layout:	
			if(!"".equals(CommonUtils.getStringByKey(getActivity(),Constants.EP2P_TOKEN))){
				startActivity(new Intent(getActivity(), WaittingRecieveQueryActivity.class));			
			}else{
				//CommonUtils.toastMsgShort(getActivity(), getResources().getString(R.string.login_hint));
				pushActivityForResult(LoginActivity.class,MineNoLoginFragment.LOGIN_REQUESTCODE);
			}
			break;
		case R.id.mine_refund_layout: {			
			if(!"".equals(CommonUtils.getStringByKey(getActivity(),Constants.EP2P_TOKEN))){
				Intent intent = new Intent();
				intent.setClass(getActivity(), ReimbursementActivity.class);
				intent.putExtra(ReimbursementActivity.TAB_SELECT, ReimbursementActivity.TAB_WAIT);
				startActivity(intent);		
			}else{
				//CommonUtils.toastMsgShort(getActivity(), getResources().getString(R.string.login_hint));
				pushActivityForResult(LoginActivity.class,MineNoLoginFragment.LOGIN_REQUESTCODE);
			}
		}
			break;
		case R.id.mine_trading_list_layout:			
			if(!"".equals(CommonUtils.getStringByKey(getActivity(),Constants.EP2P_TOKEN))){
				startActivity(new Intent(getActivity(), TradingListActivity.class));			
			}else{
				pushActivityForResult(LoginActivity.class,MineNoLoginFragment.LOGIN_REQUESTCODE);
			}
			break;
		case R.id.mine_auto_tender_layout:
			if(!"".equals(CommonUtils.getStringByKey(getActivity(),Constants.EP2P_TOKEN))){
				startActivity(new Intent(getActivity(), AutoTenderActivity.class));			
			}else{
				pushActivityForResult(LoginActivity.class,MineNoLoginFragment.LOGIN_REQUESTCODE);
			}
			break;
		case R.id.mine_security_center_layout:		
			if(!"".equals(CommonUtils.getStringByKey(getActivity(),Constants.EP2P_TOKEN))){
				startActivity(new Intent(getActivity(), SecurityCenterActivity.class));	
			}else{
				pushActivityForResult(LoginActivity.class,MineNoLoginFragment.LOGIN_REQUESTCODE);
			}
			break;
		case R.id.mine_invite_prize_layout:
			if(!"".equals(CommonUtils.getStringByKey(getActivity(),Constants.EP2P_TOKEN))){
				startActivity(new Intent(getActivity(), InvitePrizeActivity.class));			
			}else{
				pushActivityForResult(LoginActivity.class,MineNoLoginFragment.LOGIN_REQUESTCODE);
			}
			break;
		case R.id.mine_more_layout:
			if(!"".equals(CommonUtils.getStringByKey(getActivity(),Constants.EP2P_TOKEN))){
				pushActivity(MoreActivity.class);	
			}else{
				pushActivityForResult(LoginActivity.class,MineNoLoginFragment.LOGIN_REQUESTCODE);
			}
			break;
		case R.id.mine_level_tv:
			if(!"".equals(CommonUtils.getStringByKey(getActivity(),Constants.EP2P_TOKEN))){
				startActivity(new Intent(getActivity(), VipLevelActivity.class));				
			}else{
				pushActivityForResult(LoginActivity.class,MineNoLoginFragment.LOGIN_REQUESTCODE);
			}
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode==MineNoLoginFragment.LOGIN_REQUESTCODE){
			if(resultCode==Activity.RESULT_OK){
				loadData();
			}
		}
	}
	
	@Override
	public void onResponse(int tag, Object values) {
		// TODO Auto-generated method stub
		switch (tag) {
		case 1:
			PersonalInfoResponse response = gson.fromJson(values.toString(),PersonalInfoResponse.class);
			CommonUtils.setUserInfo(response);
			loadData();
			break;
		}
	}	

	@Override
	public void onErrorResponse(int tag, String error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(tag, error);
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		if(broadcastReceiver!=null){
			getActivity().unregisterReceiver(broadcastReceiver);
		}
	}
}
