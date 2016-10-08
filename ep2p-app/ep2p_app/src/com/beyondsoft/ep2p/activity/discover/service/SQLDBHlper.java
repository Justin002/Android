package com.beyondsoft.ep2p.activity.discover.service;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * SQLDBHlper辅助类
 * 
 * @author 
 */
public class SQLDBHlper extends SQLiteOpenHelper {
	private static SQLDBHlper sqldbHlper;

	/**
	 * 获取SQLDBHlper
	 * 
	 * @param context
	 * @return
	 */
	public static SQLDBHlper getSQLDBHlper(Context context) {
		if (sqldbHlper == null) {
			sqldbHlper = new SQLDBHlper(context);
		}
		return sqldbHlper;
	}

	public SQLDBHlper(Context context) {
		super(context, "xiao_player.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
//			db.execSQL(SkinThemeDB.CREATE_TBL);
//			db.execSQL(SplashDB.CREATE_TBL);
//			db.execSQL(DownloadTaskDB.CREATE_TBL);
			db.execSQL(SongDB.CREATE_TBL);
		} catch (SQLException e) {
			Log.i("error", "create table failed");
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
//			db.execSQL("drop table if exists " + SkinThemeDB.TBL_NAME);
//			db.execSQL("drop table if exists " + SplashDB.TBL_NAME);
//			db.execSQL("drop table if exists " + DownloadTaskDB.TBL_NAME);
			db.execSQL("drop table if exists " + SongDB.TBL_NAME);
		} catch (SQLException e) {
			Log.i("error", "drop table failed");
		}
		onCreate(db);
	}

}
