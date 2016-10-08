package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.mine.fragment.CardUnuseFragment;
import com.beyondsoft.ep2p.activity.mine.fragment.CardUseFragment;
import com.beyondsoft.ep2p.adapter.SimpleFragmentPagerAdapter;
import com.beyondsoft.ep2p.view.SpecialViewPager;

/**
 * 卡券页面
 * 
 * @author hardy.zhou
 *
 */
public class CardPackageActivity extends BaseFragmentActivity implements OnClickListener {

	private TextView title_content_tv;
	private FragmentActivity mContext;

	private SpecialViewPager svp_content;
	private SimpleFragmentPagerAdapter fragmentPagerAdapter;

	private ArrayList<Fragment> mFragmentList = new ArrayList<Fragment>();
	private CardUseFragment cardUseFragment = new CardUseFragment();
	private CardUnuseFragment cardUnuseFragment = new CardUnuseFragment();

	private TextView tv_use, tv_unuse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_package);
		mContext = this;
		initView();
		setListener();
		setTabStyle(0);
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		title_content_tv = (TextView) findViewById(R.id.title_content_tv);
		title_content_tv.setText(R.string.mine_card);
		svp_content = (SpecialViewPager) findViewById(R.id.svp_content_card);
		tv_use = (TextView) findViewById(R.id.tv_use);
		tv_unuse = (TextView) findViewById(R.id.tv_unuse);
		tv_use.setOnClickListener(this);
		tv_unuse.setOnClickListener(this);
		fragmentPagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
		svp_content.setAdapter(fragmentPagerAdapter);
		mFragmentList.add(cardUseFragment);
		mFragmentList.add(cardUnuseFragment);
		fragmentPagerAdapter.setData(mFragmentList);
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
				setTabStyle(index);
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
		tv_use.setSelected(false);
		tv_unuse.setSelected(false);
		switch (index) {
		case 0: {
			tv_use.setSelected(true);
		}
			break;
		case 1: {
			tv_unuse.setSelected(true);
		}
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_use: {
			svp_content.setCurrentItem(0, true);
		}
			break;
		case R.id.tv_unuse: {
			svp_content.setCurrentItem(1, true);
		}
			break;
		default:
			break;
		}

	}

}
