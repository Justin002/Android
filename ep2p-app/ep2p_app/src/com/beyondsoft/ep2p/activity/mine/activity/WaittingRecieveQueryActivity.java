package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.ArrayList;

import com.beyondsoft.ep2p.BaseFragmentActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.activity.mine.fragment.WaittingRecieveDoingFragment;
import com.beyondsoft.ep2p.activity.mine.fragment.WaittingRecieveFinishFragment;
import com.beyondsoft.ep2p.adapter.SimpleFragmentPagerAdapter;
import com.beyondsoft.ep2p.view.SpecialViewPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;

/**
 * 待收查询页面
 * 
 * @author hardy.zhou
 *
 */
public class WaittingRecieveQueryActivity extends BaseFragmentActivity implements OnClickListener {

	private TextView title_content_tv, title_right;

	private SpecialViewPager svp_content;
	private SimpleFragmentPagerAdapter fragmentPagerAdapter;

	private ArrayList<Fragment> mFragmentList = new ArrayList<Fragment>();
	private WaittingRecieveDoingFragment mWaittingRecieveDoingFragment = new WaittingRecieveDoingFragment();
	private WaittingRecieveFinishFragment mWaittingRecieveFinishFragment = new WaittingRecieveFinishFragment();

	private TextView tv_waitting, tv_finished;

	//private boolean isAll = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_waitting_recieve_query);
		initView();
		setListener();
		setTabStyle(0);
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		title_content_tv = (TextView) findViewById(R.id.title_content_tv);
		title_right = (TextView) findViewById(R.id.title_right);
		title_content_tv.setText(R.string.mine_standby_check);
		//title_right.setText(R.string.look_can_transf);
		//title_right.setVisibility(View.GONE);
		//title_right.setOnClickListener(this);

		svp_content = (SpecialViewPager) findViewById(R.id.svp_content);
		tv_waitting = (TextView) findViewById(R.id.tv_waitting);
		tv_finished = (TextView) findViewById(R.id.tv_finished);
		tv_waitting.setOnClickListener(this);
		tv_finished.setOnClickListener(this);
		fragmentPagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
		svp_content.setAdapter(fragmentPagerAdapter);
		mFragmentList.add(mWaittingRecieveDoingFragment);
		mFragmentList.add(mWaittingRecieveFinishFragment);
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
		tv_waitting.setSelected(false);
		tv_finished.setSelected(false);
		tv_waitting.setTextColor(getResources().getColor(R.color.gray));
		tv_finished.setTextColor(getResources().getColor(R.color.gray));
		
		switch (index) {
		case 0: {
			tv_waitting.setSelected(true);
			tv_waitting.setTextColor(getResources().getColor(R.color.black));
		}
			break;
		case 1: {
			tv_finished.setSelected(true);
			tv_finished.setTextColor(getResources().getColor(R.color.black));

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
		case R.id.tv_finished: {
			svp_content.setCurrentItem(1, true);
		}
			break;
		default:
			break;
		}
	}

}
