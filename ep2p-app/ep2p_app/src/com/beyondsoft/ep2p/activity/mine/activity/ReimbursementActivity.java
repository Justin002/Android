package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.ArrayList;

import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.mine.fragment.ReimbursementDoneFragment;
import com.beyondsoft.ep2p.activity.mine.fragment.ReimbursementWaitFragment;
import com.beyondsoft.ep2p.adapter.SimpleFragmentPagerAdapter;
import com.beyondsoft.ep2p.utils.CommonUtils;
import com.beyondsoft.ep2p.view.SpecialViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;

/**
 * 还款页面
 * 
 * @author hardy.zhou
 *
 */
public class ReimbursementActivity extends BaseFragmentActivity implements OnClickListener {

	public static final String TAB_SELECT = "tab_select";
	public static final int TAB_WAIT = 0;
	public static final int TAB_DONE = 1;

	private int mCurrentTab = TAB_WAIT;

	private TextView tv_title;
   
	private SpecialViewPager svp_content;
	private SimpleFragmentPagerAdapter fragmentPagerAdapter;

	private ArrayList<Fragment> mFragmentList = new ArrayList<Fragment>();
	private ReimbursementWaitFragment mReimbursementWaitFragment = new ReimbursementWaitFragment();
	private ReimbursementDoneFragment mReimbursementDoneFragment = new ReimbursementDoneFragment();

	private TextView tv_reimbursement_wait, tv_reimbursement_done, title_right;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reimbursement);
		initData();
		initView();
		setListener();
		svp_content.setCurrentItem(mCurrentTab, true);
		setTabStyle(mCurrentTab);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		Intent intent = getIntent();
		mCurrentTab = intent.getIntExtra(TAB_SELECT, TAB_WAIT);
		
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		mCurrentTab = intent.getIntExtra(TAB_SELECT, TAB_WAIT);
		svp_content.setCurrentItem(mCurrentTab, true);
		setTabStyle(mCurrentTab);
	}
	


	/**
	 * 初始化组件
	 */
	private void initView() {
		tv_title = (TextView) findViewById(R.id.title_content_tv);
		tv_title.setText(R.string.mine_refund);
		svp_content = (SpecialViewPager) findViewById(R.id.svp_content);
		tv_reimbursement_wait = (TextView) findViewById(R.id.tv_reimbursement_wait);
		tv_reimbursement_done = (TextView) findViewById(R.id.tv_reimbursement_done);
		title_right = (TextView)findViewById(R.id.title_right);
		title_right.setText("提前还清所有");
		
		tv_reimbursement_wait.setOnClickListener(this);
		tv_reimbursement_done.setOnClickListener(this);
		title_right.setOnClickListener(this);
		
		fragmentPagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
		svp_content.setAdapter(fragmentPagerAdapter);
		mFragmentList.add(mReimbursementWaitFragment);
		mFragmentList.add(mReimbursementDoneFragment);
		fragmentPagerAdapter.setData(mFragmentList);
		svp_content.setCanScroll(false);
		svp_content.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return !svp_content.isCanScroll();
			}
		});
		
		
	}
	
	public void showRightTitle(){
		title_right.setVisibility(View.VISIBLE);
	}

	public void hideRightTitle(){
		title_right.setVisibility(View.GONE);
	}
	/**
	 * 设置监听器
	 */
	@SuppressWarnings("deprecation")
	private void setListener() {
		svp_content.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int index) {
				mCurrentTab = index;
				setTabStyle(mCurrentTab);
				if(index==0){
					mReimbursementWaitFragment.doGetReimbersementWaitList();
				}else{
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
		tv_reimbursement_wait.setSelected(false);
		tv_reimbursement_done.setSelected(false);
		switch (index) {
		case 0: {
			tv_reimbursement_wait.setSelected(true);
			tv_reimbursement_wait.setTextColor(this.getResources().getColor(R.color.black));
			tv_reimbursement_done.setTextColor(this.getResources().getColor(R.color.gray));
		}
			break;
		case 1: {
			tv_reimbursement_done.setSelected(true);
			tv_reimbursement_wait.setTextColor(this.getResources().getColor(R.color.gray));
			tv_reimbursement_done.setTextColor(this.getResources().getColor(R.color.black));
		}
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_reimbursement_wait: {
			mCurrentTab = TAB_WAIT;
			svp_content.setCurrentItem(mCurrentTab, true);
		}
			break;
		case R.id.tv_reimbursement_done: {
			mCurrentTab = TAB_DONE;
			svp_content.setCurrentItem(mCurrentTab, true);
		}
			break;
		case R.id.title_right:{
				startActivity(new Intent(mContext,ReimbursementForAdvance.class));
		}
		default:
			break;
		}
	}

}
