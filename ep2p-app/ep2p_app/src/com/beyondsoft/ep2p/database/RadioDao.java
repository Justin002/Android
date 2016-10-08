package com.beyondsoft.ep2p.database;

import com.beyondsoft.ep2p.model.response.RatioStationListResponse.Result.RadioListItem;
import com.google.gson.Gson;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RadioDao {
	
	
	/**保存播放电台信息
	 * @param data
	 * @param context
	 */
	public void saveRadioInfo(String data, Context context) {
		SqliteHelper helper = SqliteHelper.getInstance(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put("radio_info",data);
			db.delete("radio_info_table", null, null);
			db.insert("radio_info_table", null, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(db!=null){
				db.close();
			}
		}
	}
	
	/** 获取Radio信息
	 * @param context
	 * @return
	 */
	public RadioListItem getRadioInfo(Context context){
		SqliteHelper helper = SqliteHelper.getInstance(context);
		SQLiteDatabase db = helper.getReadableDatabase();
		RadioListItem radioListItem=null;
		String sql = "select radio_info from radio_info_table";
		Cursor cursor = null;
		try {
			cursor = db.rawQuery(sql,null);
			if(cursor.moveToFirst()){
				Gson gson=new Gson();
				radioListItem=gson.fromJson(cursor.getString(0), RadioListItem.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
			if (db != null)
				db.close();
		}
		return radioListItem;
	}
}
