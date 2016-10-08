package com.beyondsoft.ep2p.activity.discover;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.LoginActivity;
import com.beyondsoft.ep2p.activity.discover.RedFirstFragment.OnArticleSelectedListener;
import com.beyondsoft.ep2p.activity.home.HomeFragment;
import com.beyondsoft.ep2p.activity.mine.MineFragment;
import com.beyondsoft.ep2p.common.Constants;
import com.beyondsoft.ep2p.model.response.ReceiveRedResponse;
import com.beyondsoft.ep2p.model.response.ShowRedResponse;
import com.beyondsoft.ep2p.service.BaseService;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.utils.StringRequest2;
import com.beyondsoft.ep2p.utils.URL;
import com.beyondsoft.ep2p.view.RefreshDialog;
import com.google.gson.reflect.TypeToken;


/**
 * @author xiaoliang
 * @description 领取红包
 */
public class ReceiveRedenvelopeActivity extends BaseFragmentActivity implements OnArticleSelectedListener{
	private static final int TAG_RECEIVE_RED = 11;
	private FragmentManager mFragmentManager;
	private String mCurrentFragmentTag = null;
	private TextView tv_title;
	private ImageView iv_right_falg;
	private RelativeLayout title_layout;
//	private RelativeLayout redbag_rl;
//	private TextView redbag_check;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_receive_redenvelope);
		mFragmentManager = getSupportFragmentManager();
		initTitle();
		Bundle bundle = new Bundle();  
		bundle.putString("pid", getIntent().getStringExtra("pid"));
		initFragmet(0,bundle);
	}
	
	private void initTitle() {
		title_layout= (RelativeLayout) findViewById(R.id.title_layout);
		title_layout.setBackgroundColor(getResources().getColor(R.color.dis_r_shenghong));
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText("领取红包");
		iv_right_falg = (ImageView) findViewById(R.id.title_right);
		iv_right_falg.setVisibility(View.GONE);
	}
	public void initFragmet(int position,Bundle bundle) {
		String tag = null;
		Fragment fragment = null;
		FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
		if (mCurrentFragmentTag != null) {
			Fragment currentFragment = mFragmentManager.findFragmentByTag(mCurrentFragmentTag);
			if (mFragmentManager != null) {
				fragmentTransaction.hide(currentFragment);
			}
		}
		switch (position) {
		case 0:
			tag = HomeFragment.class.getSimpleName();
            Fragment menu1 = mFragmentManager.findFragmentByTag(tag);
            if (menu1 != null) {
                fragment = menu1;
            } else {
                fragment = new RedFirstFragment();
            }
			break;

		case 1:
			tag = DiscoverFragment.class.getSimpleName();
			Fragment menu2 = mFragmentManager.findFragmentByTag(tag);
			if (menu2 != null) {
				fragment = menu2;
			} else {
				fragment = new RedResultFragment();
			}
			break;
		}
		  
		fragment.setArguments(bundle);
		if (fragment != null && fragment.isAdded()) {
			fragmentTransaction.show(fragment);
		} else {
			fragmentTransaction.add(R.id.frament_main, fragment, tag);
		}
		fragmentTransaction.commit();
		mCurrentFragmentTag = tag;
	}

	@Override
	public void onArticleSelected() {
		//TODO 领红包
//		CommonUtils.toastMsgLong(mContext, "领红包");
		if (CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN) == null
				|| "".equals(CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN))) {
			pushActivity(LoginActivity.class);
		} else {
		receiveRed();
		}
	}

	private void receiveRed() {
		RefreshDialog.startProgressDialog(mContext, "");
		BaseService bService = new BaseService();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("token", CommonUtils.getStringByKey(mContext, Constants.EP2P_TOKEN));
		params.put("redpacketId", getIntent().getStringExtra("pid"));
		connection(bService.getStringRequest(TAG_RECEIVE_RED, URL.API_RECEIVE_RED, params, this, mContext));
		
	}
	
	@Override
	public void onResponse(int tag, Object values) {
		super.onResponse(tag, values);
		switch (tag) {
		case TAG_RECEIVE_RED:
			RefreshDialog.stopProgressDialog();
//			CommonUtils.toastMsgLong(mContext, "领红包成功！");
			ReceiveRedResponse receiveRedResponse = (ReceiveRedResponse) StringRequest2
					.Json2Object(values.toString(),new TypeToken<ReceiveRedResponse>(){});
					if (receiveRedResponse != null){
						Bundle bundle2 = new Bundle();
						bundle2.putString("code", receiveRedResponse.getCode());
						bundle2.putString("receiveamount", receiveRedResponse.getResult().getReceiveAmount());
						bundle2.putString("alreadyreceiveamount", receiveRedResponse.getResult().getAlreadyReceiveAmount());
						bundle2.putString("alreadyreceivenumber", receiveRedResponse.getResult().getAlreadyReceiveNumber());
						bundle2.putString("repamounttotal", receiveRedResponse.getResult().getRepAmountTotal());
						bundle2.putString("repnumber", receiveRedResponse.getResult().getRepNumber());
						bundle2.putSerializable("alreadyreceivelist", receiveRedResponse.getResult().getAlreadyReceiveList());
						initFragmet(1,bundle2);
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
		case TAG_RECEIVE_RED:
			RefreshDialog.stopProgressDialog();
			CommonUtils.toastMsgLong(mContext, "领红包失败..."+error);
			break;

		default:
			break;
		}
	}

}
