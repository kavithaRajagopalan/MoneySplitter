package com.kavitha.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "credit";
	private static final String COLUMN_ID = "_id";
	public static final String COLUMN_FROM = "debitedFrom";
	public static final String COLUMN_TO = "creditedTo";
	public static final String COLUMN_AMOUNT = "amount";
	public static String[] all_columns = {COLUMN_ID, COLUMN_FROM, COLUMN_TO, COLUMN_AMOUNT};

	private String statement = "CREATE TABLE " + TABLE_NAME+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"+COLUMN_FROM+" TEXT NOT NULL,"+COLUMN_TO+" TEXT NOT NULL,"+COLUMN_AMOUNT+" INTEGER NOT NULL)";

	private SQLiteDatabase db;
	private int VERSION = 1;

	public DbHelper(Context context) {
		super(context, "credit", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(statement);	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists "+TABLE_NAME);
		onCreate(db);
	}
}