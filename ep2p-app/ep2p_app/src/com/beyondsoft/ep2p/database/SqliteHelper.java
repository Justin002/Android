package com.beyondsoft.ep2p.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * @author Ivan.Lu
 * @description 数据库
 */
public class SqliteHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "ep2p.db";
	public static final int VERSION = 1;
	private static SqliteHelper sqliteHelper;

	private SqliteHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}

	public synchronized static SqliteHelper getInstance(Context context) {
		if (sqliteHelper == null) {
			sqliteHelper = new SqliteHelper(context);
		}
		return sqliteHelper;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table radio_info_table(radio_info text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}
