package com.beyondsoft.ep2p.activity.mine.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.beyondsoft.ep2p.BaseActivity;
import com.beyondsoft.ep2p.R;
import com.beyondsoft.ep2p.model.RegionBean;
import com.beyondsoft.ep2p.utils.MyLocationUtils;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 选择省页面
 * @author Jason.Hwang
 *
 */

public class SelectLoactionActivity extends BaseActivity{

   private MyLocationUtils myLocationUtils;
   private String cityName;
   private ListView provice_lv;
   private TextView tv_mylocation,title_content_tv,tv_choose_privince,tv_mylocation_label;
   private SQLiteDatabase sqliteDB;;
   private ArrayList<RegionBean> mProvinces;
   private ArrayList<String> mCities;
   private int parent_code;
   @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_location);
		initView();
		addHeaderView();
		initLocationCity();
		initData();
		  tv_mylocation.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.putExtra("cityName",cityName );
					setResult(100, intent);
					SelectLoactionActivity.this.finish();
				}
			});
	}
   
  private void initData(){
	  getProvinceData();
	  provice_lv.setAdapter(new ProvinceAdpter(mProvinces, mContext));
	  provice_lv.setOnItemClickListener(new MyItemClickListener());
  }
   
  private void initView(){
	  provice_lv = (ListView) findViewById(R.id.lv_locations);
	
	  title_content_tv = (TextView) findViewById(R.id.title_content_tv);
	  title_content_tv.setText("选择地区");
  } 
  
  private void  ResultDel(){
	  tv_mylocation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("cityName",cityName );
				setResult(100, intent);
				SelectLoactionActivity.this.finish();
			}
		});
	  
  }
  
   private void addHeaderView(){
	   View mHeaderView = LayoutInflater.from(mContext).inflate(R.layout.activity_select_city_header, null);
	   tv_mylocation = (TextView) mHeaderView.findViewById(R.id.tv_mylocation);
	   tv_choose_privince = (TextView) mHeaderView.findViewById(R.id.tv_choose_privince);
	   tv_mylocation_label = (TextView) mHeaderView.findViewById(R.id.tv_mylocation_label);
	   tv_choose_privince.setOnClickListener(null);
	   tv_mylocation_label.setOnClickListener(null);
	   provice_lv.addHeaderView(mHeaderView);
	   
   }
   
   @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==100){
			setResult(100,data);
			finish();
		}
	}
   
   class MyItemClickListener implements OnItemClickListener{

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		parent_code = Integer.parseInt(mProvinces.get(arg2-1).getRegion_code());
		getCitiesData(parent_code);
		Intent intent = new Intent();
		intent.putStringArrayListExtra("cityName", mCities);
		intent.setClass(SelectLoactionActivity.this, SelecteCityActivity.class);
		startActivityForResult(intent, 100);
		
	}
	   
   }
   
 //省区适配器
      class ProvinceAdpter extends BaseAdapter{
	   
		private ArrayList<RegionBean> list;
		private Context context;
		
		public ProvinceAdpter(ArrayList<RegionBean> list, Context context) {
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
			/*// TODO Auto-generated method stub
			View view  = LayoutInflater.from(context).inflate(R.layout.item_region, null);
			TextView tv_item_region = (TextView) view.findViewById(R.id.tv_item_region);
			tv_item_region.setText(list.get(position).getRegion_name());
			return view;*/
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
			
			viewHolder.tv_item_region.setText(list.get(position).getRegion_name());
			
			return convertView;
		}
		
		class ViewHolder{
			TextView tv_item_region;
		}

	}
   
   public static final boolean isOPen(final Context context) {
       LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
       // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
       boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
       // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
       boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
       if (gps || network) {
           return true;
       }

       return false;
   }
   
   private void initLocationCity() {
    
	   /*if(isOPen(mContext)){*/
           // 定位打开下进行定位
           myLocationUtils = new MyLocationUtils(mContext, new BDLocationListener() {
               @Override
               public void onReceiveLocation(BDLocation location) {
                   // TODO Auto-generated method stub
                   if (null != location) {
                    	   cityName  = location.getCity();
                    	   Toast.makeText(mContext, "city"+cityName, 3000).show();
                    	   tv_mylocation.setText(cityName);
                           if (cityName != null) {
                               myLocationUtils.stopLocation();
                           }
                       
                   }else {
                	   Toast.makeText(mContext, "定位失败", 3000).show();
                	   tv_mylocation.setText("定位失败");
                   }

               }

           });
           myLocationUtils.startLoaction();
      /* }else{
    	   Toast.makeText(mContext, "网络未打开", 3000).show();
    	   tv_mylocation.setText("网络未打开");
       }*/
   }
   
   //从数据库中获取所有省区信息
   public  ArrayList<RegionBean> getProvinceData()
	{
		mProvinces= new ArrayList<RegionBean>();
		if (!new File("/data/data/" + this.getPackageName()  
       + "/database.sqlite").exists()) {  
   try {  
       FileOutputStream out = new FileOutputStream("data/data/"  
               + this.getPackageName() + "/database.sqlite");  
       InputStream in = getAssets().open("ep2p.db");  

       byte[] buffer = new byte[1024];  
       int readBytes = 0;  

       while ((readBytes = in.read(buffer)) != -1)  
           out.write(buffer, 0, readBytes);  

       in.close();  
       out.close();  
   } catch (IOException e) {  
   }  
}  

sqliteDB = SQLiteDatabase.openOrCreateDatabase(  
       "/data/data/" + this.getPackageName() + "/database.sqlite",  
       null);  
System.out.println("sqliteDB");  
 
try{  
	Cursor result=sqliteDB.rawQuery("SELECT * from t_mt_base_region where region_grade=1",null);  
   result.moveToFirst();  
   int cnt=0;  
   while(!result.isAfterLast()){   
       RegionBean province = new RegionBean();
       province.setRegion_code(result.getString(result.getColumnIndex("region_code")));
       province.setRegion_name(result.getString(result.getColumnIndex("region_name")));
       //System.out.println(result.getString(result.getColumnIndex("region_name")));
       //System.out.println(result.getString(result.getColumnIndex("region_code")));
       System.out.println( province.getRegion_name()+ province.getRegion_code());
       mProvinces.add(province);
       result.moveToNext();  
   }          
   result.close();   
   
}catch(Exception e){  
   e.printStackTrace();  
} 
		return mProvinces;
	}
   
   
   //从数据库获取城市信息
   public  ArrayList<String> getCitiesData(int parent_code)
	{
	     mCities= new ArrayList<String>();
		if (!new File("/data/data/" + this.getPackageName()  
       + "/database.sqlite").exists()) {  
   try {  
       FileOutputStream out = new FileOutputStream("data/data/"  
               + this.getPackageName() + "/database.sqlite");  
       InputStream in = getAssets().open("ep2p.db");  

       byte[] buffer = new byte[1024];  
       int readBytes = 0;  

       while ((readBytes = in.read(buffer)) != -1)  
           out.write(buffer, 0, readBytes);  

       in.close();  
       out.close();  
   } catch (IOException e) {  
   }  
}  

sqliteDB = SQLiteDatabase.openOrCreateDatabase(  
       "/data/data/" + this.getPackageName() + "/database.sqlite",  
       null);  
System.out.println("sqliteDB");  
 
try{  
	Cursor result=sqliteDB.rawQuery("SELECT region_name from t_mt_base_region where parents_region_code="+parent_code+" and region_grade=2",null);  
   result.moveToFirst();  
   
   while(!result.isAfterLast()){   
       String citynName = result.getString(result.getColumnIndex("region_name"));
       //System.out.println(result.getString(result.getColumnIndex("region_name")));
       //System.out.println(result.getString(result.getColumnIndex("region_code")));
       mCities.add(citynName);
       System.out.println( citynName);
       result.moveToNext();  
   }          
   result.close();   
   
}catch(Exception e){  
   e.printStackTrace();  
} 
		return mCities;
	}
   
   }

