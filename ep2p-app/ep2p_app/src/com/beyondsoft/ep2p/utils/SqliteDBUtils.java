package com.beyondsoft.ep2p.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.beyondsoft.ep2p.model.RegionBean;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SqliteDBUtils extends Activity{

	public SQLiteDatabase sqliteDB;
	
	public  ArrayList<RegionBean> getProvinceData()
	{
		ArrayList<RegionBean> mProvinces= new ArrayList<RegionBean>();
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
	Cursor result=sqliteDB.rawQuery("SELECT region_name from t_mt_base_region where region_grade=1",null);  
    result.moveToFirst();  
    int cnt=0;  
    while(!result.isAfterLast()){   
        RegionBean province = new RegionBean();
        province.setRegion_code(result.getString(result.getColumnIndex("region_code")));
        province.setRegion_name(result.getString(result.getColumnIndex("region_name")));
        mProvinces.add(province);
        result.moveToNext();  
    }          
    result.close();   
    
}catch(Exception e){  
    e.printStackTrace();  
} 
		return mProvinces;
	}
}
