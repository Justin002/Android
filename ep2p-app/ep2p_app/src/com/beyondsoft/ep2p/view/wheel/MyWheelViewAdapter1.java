package com.beyondsoft.ep2p.view.wheel;

import java.util.ArrayList;

import com.beyondsoft.ep2p.R;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class MyWheelViewAdapter1 implements WheelViewAdapter {

	private ArrayList<String> items;
	private Activity activity;

	public MyWheelViewAdapter1(Activity activity, ArrayList<String> wheelItems) {
		this.activity = activity;
		this.items = wheelItems;
	}


	@Override
	public int getItemsCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public View getItem(int index, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {
			convertView = activity.getLayoutInflater().inflate(
					R.layout.discover_w_wheelview_item, null);
		}
		
	
		((TextView) convertView).setText(items.get(index));
		return convertView;
	}

	@Override
	public View getEmptyItem(View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

}
