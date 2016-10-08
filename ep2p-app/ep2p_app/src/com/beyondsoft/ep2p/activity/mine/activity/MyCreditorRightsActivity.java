package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.ArrayList;

import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.mine.fragment.CreditorRightsHaveFragment;
import com.beyondsoft.ep2p.activity.mine.fragment.CreditorRightsTransfDoingFragment;
import com.beyondsoft.ep2p.activity.mine.fragment.CreditorRightsTransfDoneFragment;
import com.beyondsoft.ep2p.adapter.SimpleFragmentPagerAdapter;
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
 * 我的债权页面
 * 
 * @author hardy.zhou
 *
 */
public class MyCreditorRightsActivity extends BaseFragmentActivity implements OnClickListener {

	public static final String TAB_SELECT = "tab_select";
	public static final int TAB_HAVE = 0;
	public static final int TAB_DOING = 1;
	public static final int TAB_DONE = 2;

	private int mCurrentTab = TAB_HAVE;

	private TextView title_content_tv;

	private SpecialViewPager svp_content;
	private SimpleFragmentPagerAdapter fragmentPagerAdapter;

	private ArrayList<Fragment> mFragmentList = new ArrayList<Fragment>();
	private CreditorRightsHaveFragment mCreditorRightsHaveFragment = new CreditorRightsHaveFragment();
	private CreditorRightsTransfDoingFragment mCreditorRightsTransfDoingFragment = new CreditorRightsTransfDoingFragment();
	private CreditorRightsTransfDoneFragment mCreditorRightsTransfDoneFragment = new CreditorRightsTransfDoneFragment();

	private TextView tv_cerditor_rights_have, tv_transf_doing, tv_transf_done;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_creditor_rights);
		//initData();
		initView();
		setListener();
		svp_content.setCurrentItem(0, true);
		setTabStyle(0);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		Intent intent = getIntent();
		mCurrentTab = intent.getIntExtra(TAB_SELECT, TAB_HAVE);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		mCurrentTab = intent.getIntExtra(TAB_SELECT, TAB_HAVE);
		svp_content.setCurrentItem(mCurrentTab, true);
		setTabStyle(mCurrentTab);
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		title_content_tv = (TextView) findViewById(R.id.title_content_tv);
		title_content_tv.setText(R.string.mine_my_bond);
		svp_content = (SpecialViewPager) findViewById(R.id.svp_content);
		tv_cerditor_rights_have = (TextView) findViewById(R.id.tv_cerditor_rights_have);
		tv_transf_doing = (TextView) findViewById(R.id.tv_transf_doing);
		tv_transf_done = (TextView) findViewById(R.id.tv_transf_done);
		tv_cerditor_rights_have.setOnClickListener(this);
		tv_transf_doing.setOnClickListener(this);
		tv_transf_done.setOnClickListener(this);
		fragmentPagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
		mFragmentList.clear();
		mFragmentList.add(mCreditorRightsHaveFragment);
		mFragmentList.add(mCreditorRightsTransfDoingFragment);
		mFragmentList.add(mCreditorRightsTransfDoneFragment);
		fragmentPagerAdapter.setData(mFragmentList);
		svp_content.setAdapter(fragmentPagerAdapter);
		svp_content.setCanScroll(false);
		svp_content.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return !svp_content.isCanScroll();
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
				mCurrentTab = index;
				setTabStyle(mCurrentTab);
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
		tv_cerditor_rights_have.setSelected(false);
		tv_transf_doing.setSelected(false);
		tv_transf_done.setSelected(false);
		switch (index) {
		case TAB_HAVE: {
			tv_cerditor_rights_have.setSelected(true);
		}
			break;
		case TAB_DOING: {
			tv_transf_doing.setSelected(true);
		}
			break;
		case TAB_DONE: {
			tv_transf_done.setSelected(true);
		}
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_cerditor_rights_have: {
			//mCurrentTab = TAB_HAVE;
			svp_content.setCurrentItem(0, true);
		}
			break;
		case R.id.tv_transf_doing: {
			//mCurrentTab = TAB_DOING;
			svp_content.setCurrentItem(1, true);
		}
			break;
		case R.id.tv_transf_done: {
			//mCurrentTab = TAB_DONE;
			svp_content.setCurrentItem(2, true);
		}
			break;
		default:
			break;
		}

	}

}
