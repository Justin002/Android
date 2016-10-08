package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.mine.fragment.ProjectBiddingFragment;
import com.beyondsoft.ep2p.activity.mine.fragment.ProjectFinishFragment;
import com.beyondsoft.ep2p.activity.mine.fragment.ProjectTransferFragment;
import com.beyondsoft.ep2p.activity.mine.fragment.ProjectWaittingFragment;
import com.beyondsoft.ep2p.adapter.SimpleFragmentPagerAdapter;



/**
 * 已投项目页面
 * 
 * @author hardy.zhou
 *
 */
public class InvestmentedProjectActivity extends BaseFragmentActivity implements OnClickListener {

	private TextView title_content_tv;

	private ViewPager svp_content;
	private SimpleFragmentPagerAdapter fragmentPagerAdapter;
	private ArrayList<Fragment> mFragmentList = new ArrayList<Fragment>();
	private ProjectWaittingFragment mProjectWaittingFragment = new ProjectWaittingFragment();
	private ProjectBiddingFragment mProjectBiddingFragment = new ProjectBiddingFragment();
	private ProjectFinishFragment mProjectFinishFragment = new ProjectFinishFragment();
    private ProjectTransferFragment mProjectTransferFragment = new ProjectTransferFragment();
	private TextView tv_waitting, tv_bidding, tv_finished,tv_transfer,title_right;
	private boolean isAll = false;
	
	private int mCurrentTab = TAB_WAIT;
	
	public static final String TAB_SELECT = "vp_flag";
	
	public static final int TAB_WAIT = 0;
	public static final int TAB_BIDING = 1;
	public static final int TAB_TRANSFER = 2;
	public static final int TAB_DONE = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_investmented_project);
		initView();
		setListener();
		Intent intent = getIntent();
		mCurrentTab = intent.getIntExtra(TAB_SELECT, TAB_WAIT);
		svp_content.setCurrentItem(mCurrentTab, true);
		setTabStyle(mCurrentTab);
	}

/*	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Intent intent = getIntent();
		mCurrentTab = intent.getIntExtra(TAB_SELECT, TAB_WAIT);
		svp_content.setCurrentItem(mCurrentTab, true);
		setTabStyle(mCurrentTab);
	}*/
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		mCurrentTab = intent.getIntExtra(TAB_SELECT, TAB_WAIT);
		svp_content.setCurrentItem(mCurrentTab, true);
		setTabStyle(mCurrentTab);
	}
	
	/**
	 * 初始化组件
	 */
	private void initView() {
		title_content_tv = (TextView) findViewById(R.id.title_content_tv);
		title_content_tv.setText(R.string.mine_drop_project);
		svp_content = (ViewPager) findViewById(R.id.svp_content);
		tv_waitting = (TextView) findViewById(R.id.tv_waitting);
		tv_bidding = (TextView) findViewById(R.id.tv_bidding);
		tv_transfer = (TextView) findViewById(R.id.tv_transfer);
		tv_finished = (TextView) findViewById(R.id.tv_finished);
		title_right = (TextView)findViewById(R.id.title_right);
		title_right.setText(R.string.look_can_transf);
		title_right.setOnClickListener(this);
		tv_waitting.setOnClickListener(this);
		tv_bidding.setOnClickListener(this);
		tv_transfer.setOnClickListener(this);
		tv_finished.setOnClickListener(this);
		fragmentPagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
		svp_content.setAdapter(fragmentPagerAdapter);
		mFragmentList.add(mProjectWaittingFragment);
		mFragmentList.add(mProjectBiddingFragment);
		mFragmentList.add(mProjectTransferFragment);
		mFragmentList.add(mProjectFinishFragment);
		fragmentPagerAdapter.setData(mFragmentList);
		//svp_content.setCanScroll(false);
		svp_content.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}

	/**
	 * 设置监听器
	 */
	@SuppressWarnings("deprecation")
	private void setListener() {
		svp_content.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int index) {
				setTabStyle(index);
				if(index==0)
				{
					title_right.setVisibility(View.VISIBLE);
				}else
				{
					title_right.setVisibility(View.GONE);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	/**
	 * 设置tab样式
	 * 
	 * @param index
	 */
	private void setTabStyle(int index) {
		tv_waitting.setSelected(false);
		tv_bidding.setSelected(false);
		tv_finished.setSelected(false);
		tv_transfer.setSelected(false);
		
		tv_waitting.setTextColor(this.getResources().getColor(R.color.gray));
		tv_bidding.setTextColor(this.getResources().getColor(R.color.gray));
		tv_finished.setTextColor(this.getResources().getColor(R.color.gray));
		tv_transfer.setTextColor(this.getResources().getColor(R.color.gray));
		
		switch (index) {
		case 0: {
			tv_waitting.setSelected(true);
			tv_waitting.setTextColor(this.getResources().getColor(R.color.black));
		}
			break;
		case 1: {
			tv_bidding.setSelected(true);
			tv_bidding.setTextColor(this.getResources().getColor(R.color.black));

		}
			break;
		case 2:{
			tv_transfer.setSelected(true);
			tv_transfer.setTextColor(this.getResources().getColor(R.color.black));

		}
		   break;
		case 3: {
			tv_finished.setSelected(true);
			tv_finished.setTextColor(this.getResources().getColor(R.color.black));

		}
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_waitting: {
			
			svp_content.setCurrentItem(0, true);
		}
			break;
		case R.id.tv_bidding: {
			
			svp_content.setCurrentItem(1, true);
		}
			break;
		case R.id.tv_transfer:{
			svp_content.setCurrentItem(2, true);
		}
		    break;
		case R.id.tv_finished: {
			
			svp_content.setCurrentItem(3, true);
		}
			break;
		case R.id.title_right:{
			Intent intent = new Intent();
			if(isAll)
			{
				title_right.setText(R.string.look_can_transf);
				intent.putExtra("flag", R.string.look_can_transf);
				mProjectWaittingFragment.doGetProjectWaitListRequest();
			}else
			{
				title_right.setText(R.string.look_all);
				intent.putExtra("flag", R.string.look_all);
				mProjectWaittingFragment.showTransferList();
			}
			isAll = !isAll;
		}
		    break;
		default:
			break;
		}

	}

}
