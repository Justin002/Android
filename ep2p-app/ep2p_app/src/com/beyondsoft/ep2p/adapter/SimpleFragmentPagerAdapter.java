package com.beyondsoft.ep2p.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 简单FragmentPagerAdapter适配器
 * 
 * @author hardy.zhou
 *
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> fragmentList;

	public SimpleFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	/**
	 * 设置数据集
	 * 
	 * @param fragmentList
	 */
	public void setData(ArrayList<Fragment> fragmentList) {
		this.fragmentList = fragmentList;
		this.notifyDataSetChanged();
	}

	@Override
	public Fragment getItem(int index) {
		return fragmentList.get(index);
	}

	@Override
	public int getCount() {
		return fragmentList == null ? 0 : fragmentList.size();
	}

}
