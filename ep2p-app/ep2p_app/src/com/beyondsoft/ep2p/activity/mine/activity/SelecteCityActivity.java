package com.beyondsoft.ep2p.activity.mine.activity;

import java.util.ArrayList;

import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
public class SelecteCityActivity extends BaseActivity{
  
	private ListView city_lv;
	private TextView title_content_tv;
	private ArrayList<String> mCities;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_location);
		initView();
		initData();
	}
	
	
	
	private void initView(){
		  city_lv = (ListView) findViewById(R.id.lv_locations);
		  title_content_tv = (TextView) findViewById(R.id.title_content_tv);
		  title_content_tv.setText("选择地区");
	}
	
	private void initData(){
		mCities = getIntent().getStringArrayListExtra("cityName");
		city_lv.setAdapter(new CityAdpter(mCities, mContext));
		city_lv.setOnItemClickListener(new MyItemClickListener());
	}
	
	class MyItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.putExtra("cityName", mCities.get(arg2));
			setResult(100, intent);
			SelecteCityActivity.this.finish();
		}
		
	}
	
	
	
	class CityAdpter extends BaseAdapter{
		   
		private ArrayList<String> list;
		private Context context;
		
		public CityAdpter(ArrayList<String> list, Context context) {
			super();
			this.list = list;
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list == null ? 0 : list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			 return  list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		
			final ViewHolder viewHolder;
			if(convertView==null)
			{
				convertView = LayoutInflater.from(context).inflate(R.layout.item_region, null);
				viewHolder = new ViewHolder();
				convertView.setTag(viewHolder);
				viewHolder.tv_item_region = (TextView) convertView.findViewById(R.id.tv_item_region);
			}else
			{
				 viewHolder = (ViewHolder) convertView.getTag();
			}
			
			viewHolder.tv_item_region.setText(list.get(position));
			
			return convertView;
		}
		
		class ViewHolder{
			TextView tv_item_region;
		}

	}
}
