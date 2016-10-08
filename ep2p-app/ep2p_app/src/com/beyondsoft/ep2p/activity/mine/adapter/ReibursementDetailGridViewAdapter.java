package com.beyondsoft.ep2p.activity.mine.adapter;

import java.util.ArrayList;

import com.beyondsoft.ep2p.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ReibursementDetailGridViewAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<String> list;
	
	
	public ReibursementDetailGridViewAdapter(Context context, ArrayList<String> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater layoutInflayter = LayoutInflater.from(context);
		View view = layoutInflayter.inflate(R.layout.item_reimbursement_amount_gridview, null);
		TextView textView = (TextView) view.findViewById(R.id.textView1);
		textView.setText(list.get(position));
		return view;
		
	}

}
