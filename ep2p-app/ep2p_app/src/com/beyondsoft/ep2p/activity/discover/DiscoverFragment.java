package com.beyondsoft.ep2p.activity.discover;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.beyondsoft.ep2p.BaseFragment;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.LoginActivity;
import com.beyondsoft.ep2p.activity.discover.adapter.PopRadioAdapter;
import com.beyondsoft.ep2p.activity.mine.activity.ModifyUserNameActivity;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.UserPersonalInfo;
import com.beyondsoft.ep2p.model.response.GetBbsUrlResponse;
import com.beyondsoft.ep2p.model.response.RatioStationListResponse;
import com.beyondsoft.ep2p.model.response.ShowInterestResponse;
import com.beyondsoft.ep2p.model.response.ShowInterestResponse.Result.InterestListItem;
import com.beyondsoft.ep2p.model.response.ShowRedResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.Logs;
import com.beyondsoft.ep2p.utils.Player;
import com.beyondsoft.ep2p.utils.StringRequest2;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.HorizontalListView;
import com.google.gson.reflect.TypeToken;

/**
 * @author xiecaijiao
 * @description 发现模块
 */
public class DiscoverFragment extends BaseFragment implements OnClickListener,OnItemClickListener{
	private LinearLayout e_investment_ranking_tv;
	private LinearLayout e_income_caculator_tv;
	private LinearLayout e_newer_novice_tv;
	private LinearLayout e_community_tv;
	private LinearLayout e_receive_redenvelope_tv;
	private LinearLayout e_coupon_tv;
	private Button radio_more_bt;
	private HorizontalListView checkedContack;
	private PopRadioAdapter checkedAdapter;
	private ImageView iv_right_falg;
	private String bbs_url;
	private String bbs_url_result;
	private Player player;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.activity_discover, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		player=Player.getInstance(getActivity());
		getActivity().registerReceiver(broadcastReceiver,new IntentFilter("com.beyondsoft.ep2p.startplay"));
		init(view);
		initListener();
	}
	
	private BroadcastReceiver broadcastReceiver=new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			onResume();
		}
	};

	private void init(View view) {
		((ImageView)view.findViewById(R.id.title_left_btn)).setVisibility(View.GONE);
		iv_right_falg = (ImageView) view.findViewById(R.id.title_right_image);
		e_investment_ranking_tv = (LinearLayout) view.findViewById(R.id.e_investment_ranking_tv);		
		e_income_caculator_tv = (LinearLayout) view.findViewById(R.id.e_income_caculator_tv);		
		e_newer_novice_tv = (LinearLayout) view.findViewById(R.id.e_newer_novice_tv);
		e_community_tv = (LinearLayout) view.findViewById(R.id.e_community_tv);
		e_receive_redenvelope_tv = (LinearLayout) view.findViewById(R.id.e_receive_redenvelope_tv);
		e_coupon_tv = (LinearLayout) view.findViewById(R.id.e_coupon_tv);
		checkedContack = (HorizontalListView) view.findViewById(R.id.imgList);
		checkedAdapter = new PopRadioAdapter(getActivity());
		checkedContack.setAdapter(checkedAdapter);
		radio_more_bt = (Button) view.findViewById(R.id.radio_more_bt);
	}

	private void initListener(){
		iv_right_falg.setOnClickListener(this);
		e_investment_ranking_tv.setOnClickListener(this);
		e_income_caculator_tv.setOnClickListener(this);
		e_newer_novice_tv.setOnClickListener(this);
		e_community_tv.setOnClickListener(this);
		e_receive_redenvelope_tv.setOnClickListener(this);
		e_coupon_tv.setOnClickListener(this);
		checkedContack.setOnItemClickListener(this);
		radio_more_bt.setOnClickListener(this);
	}

	private void inData() {
		if( CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN)==null||"".equals( CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN))){
			pushActivity(LoginActivity.class);
//			getBbsUrl();
		} else if("".equals(UserPersonalInfo.getCustomerName2())&&UserPersonalInfo.getCustomerName2()==null){
			CommonUtils.toastMsgLong(getActivity(), "您未设置用户名，请先设置用户名！");
			pushActivity(ModifyUserNameActivity.class);
//			getBbsUrl();
		}else{
			getBbsUrl();
		}
	}
	private void getBbsUrl()
	{
		BaseService registerService = new BaseService();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		connection(registerService.getStringRequest(8, URL.API_GETBBSURl, params,this,mContext));
	}

	public void loadData(RatioStationListResponse popRadioResponse){
		checkedAdapter.addData(popRadioResponse.getResult().getList());
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.e_investment_ranking_tv) {
			if(CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN)==null||"".equals( CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN))){
				pushActivity(LoginActivity.class);
			}else{
				pushActivity(InvestmentRankingActivity.class);
			}
		} else if (id == R.id.e_income_caculator_tv) {
			pushActivity(IncomeCaculatorActivity.class);
		} else if (id == R.id.e_newer_novice_tv) {
			pushActivity(NewerNoviceActivity.class);
		} else if (id == R.id.e_community_tv) {
			inData();
		} else if (id == R.id.e_receive_redenvelope_tv) {
			getShowRedCode();
			//有红包的情况
		//	pushActivity(ReceiveRedenvelopeActivity.class);
			//红包抢光的情况
		} else if (id == R.id.e_coupon_tv) {
			getInterest();//展示加息劵接口
		} else if (id == R.id.title_right_image) {
			pushActivity(RatioStationPlayActivity.class);
		} else if (id == R.id.radio_more_bt) {
			pushActivity(RatioStationListActivity.class);
		}

	}
	/**
	 * 判断是否有红包
	 */
	private void getShowRedCode() {
		if(CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN)==null||"".equals( CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN))){
			pushActivity(LoginActivity.class);
		}else{
			BaseService registerService = new BaseService();
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
			connection(registerService.getStringRequest(9, URL.API_SHOWRED, params,this,mContext));
		}
		
		
	}
	/**
	 * 判断是否有加息卷
	 */
	private void getInterest() {
		if(CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN)==null||"".equals( CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN))){
			pushActivity(LoginActivity.class);
		}else{
			BaseService bService = new BaseService();
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
			connection(bService.getStringRequest(10, URL.API_SHOW_INTEREST, params,this,mContext));
		}
		
	}


	@Override
	public void onResponse(int tag, Object values) {
		super.onResponse(tag, values);
		switch (tag) {
		case 8:
			GetBbsUrlResponse bbsUrlResponse = (GetBbsUrlResponse) StringRequest2
			.Json2Object(values.toString(),
					new TypeToken<GetBbsUrlResponse>()
			{
			});
			if (bbsUrlResponse != null)
			{
				bbs_url=bbsUrlResponse.getResult().getBbsUrl();
				bbs_url_result=bbsUrlResponse.getResult().getBbsResult();
				Intent intent = new Intent();
				intent.setClass(mContext, WebViewActivity.class);
				intent.putExtra("title", getString(R.string.e_community));
				intent.putExtra("bbs_url", bbs_url);
				intent.putExtra("bbs_url_result", bbs_url_result);
				startActivity(intent);
			}
			break;
		case 9:{
//			CommonUtils.toastMsgLong(getActivity(), "BBS获取成功！");

			ShowRedResponse redResponse = (ShowRedResponse) StringRequest2
							.Json2Object(values.toString(),new TypeToken<ShowRedResponse>(){});
							if (redResponse != null){
									Intent intent = new Intent();
									intent.putExtra("code", redResponse.getCode());
									intent.putExtra("startdate", redResponse.getResult().getStartDate());
									intent.putExtra("alreadyreceivenumber", redResponse.getResult().getAlreadyReceiveNumber());
									intent.putExtra("alreadyreceiveamount", redResponse.getResult().getAlreadyReceiveAmount());
									intent.putExtra("repamounttotal", redResponse.getResult().getRepAmountTotal());
									intent.putExtra("noalreadyreceivenumber", redResponse.getResult().getNoAlreadyReceiveNumber());
									intent.putExtra("repnumber", redResponse.getResult().getRepNumber());
									intent.putExtra("listparconrel", redResponse.getResult().getListParConRel());
									intent.putExtra("pid", redResponse.getResult().getPid());
									intent.putExtra("receiveamount", redResponse.getResult().getReceiveAmount());
									intent.putExtra("alreadyreceivelist", redResponse.getResult().getAlreadyReceiveList());
									intent.setClass(mContext, ReceiveRedenvelopeActivity.class);
									startActivity(intent);
							}
		}
			break;
		case 10:{
//			CommonUtils.toastMsgLong(getActivity(), "加息券信息获取成功！");

			ShowInterestResponse interestResponse = (ShowInterestResponse) StringRequest2
			.Json2Object(values.toString(),new TypeToken<ShowInterestResponse>(){});
			if (interestResponse != null){
					Intent intent = new Intent();
					intent.putExtra("code", interestResponse.getCode());
					intent.putExtra("investvalue", interestResponse.getResult().getInvestValue());
					intent.putExtra("validity", interestResponse.getResult().getValidity());
					intent.putExtra("noalreadyreceivenumber", interestResponse.getResult().getNoAlreadyReceiveNumber());
					intent.putExtra("investnumber", interestResponse.getResult().getInvestNumber());
					intent.putExtra("startdate", interestResponse.getResult().getStartDate());
					intent.putExtra("pid", interestResponse.getResult().getPid());
					ArrayList<InterestListItem> listParConRel = interestResponse.getResult().getListParConRel();
					ArrayList<String> arrayList = new ArrayList<String>();
					for(int i = 0;i < listParConRel.size();i++){
						arrayList.add(listParConRel.get(i).getCondName());
					}
					intent.putExtra("listParConRel", arrayList);
					intent.setClass(mContext, GetAddRateRollActivity.class);
					startActivity(intent);
			}
		}
			break;
		}
	}
	@Override
	public void onErrorResponse(int tag, String error) {
		super.onErrorResponse(tag, error);
		switch (tag) {
		case 8:
			CommonUtils.toastMsgLong(getActivity(), "论坛访问失败..."+error);
			break;
		case 9:
			CommonUtils.toastMsgLong(getActivity(), "BBS未获取到..."+error);
			break;
		case 10:
			CommonUtils.toastMsgLong(getActivity(), "加息券信息未获取到..."+error);
			break;

		default:
			break;
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(player.isPlaying()){
			iv_right_falg.setVisibility(View.VISIBLE);
			Animation animation=AnimationUtils.loadAnimation(getActivity(),R.anim.circle);
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
		pushActivity(new Intent(getActivity(),RatioStationPlayActivity.class).putExtra("radioInfo", checkedAdapter.getRadioItem(position)));
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		getActivity().unregisterReceiver(broadcastReceiver);
	}
	
	/**
	 * 序列化map供Bundle传递map使用
	 * Created  on 13-12-9.
	 */
	public class SerializableMap implements Serializable {
	 
	    private Map<String,Object> map;
	 
	    public Map<String, Object> getMap() {
	        return map;
	    }
	 
	    public void setMap(Map<String, Object> map) {
	        this.map = map;
	    }
	}
}

