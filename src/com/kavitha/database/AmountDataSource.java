package com.kavitha.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.kavitha.model.Credit;

import java.util.ArrayList;
import java.util.List;

import static com.kavitha.database.DbHelper.*;

public class AmountDataSource {

    private DbHelper dbHelper;
    private SQLiteDatabase database;

    public AmountDataSource(Context context) {
        dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertTransaction(String from, String to, long amount) {
        ContentValues values = new ContentValues();
        database = dbHelper.getWritableDatabase();
        values.put(COLUMN_FROM, from);
        values.put(COLUMN_TO, to);
        values.put(COLUMN_AMOUNT, amount);
        long insert_id = database.insert(TABLE_NAME, null, values);
        Log.e("Insert", "Happened");
        database.close();
    }

    public List<Credit> listAllTransactions() {
        List<Credit> transactions = new ArrayList<Credit>();

        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, DbHelper.all_columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            transactions.add(cursorToComment(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return transactions;
    }

    public List<Credit> listForUser(String userName) {
       List<Credit> transactions = new ArrayList<Credit>();
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME,all_columns, COLUMN_FROM +"=?", new String[]{userName},null,null,COLUMN_FROM);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            transactions.add(cursorToComment(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return transactions;
    }

    public void deleteAllTransactions() {
        database = dbHelper.getWritableDatabase();
        database.delete(DbHelper.TABLE_NAME,null,null);
        database.close();
    }

    public Credit cursorToComment(Cursor cursor) {
        String from = cursor.getString(1);
        String to = cursor.getString(2);
        long amount = cursor.getLong(3);
        Credit credit = new Credit();
        credit.setdebitedFrom(from);
        credit.setcreditedTo(to);
        credit.setAmount(amount);
        return credit;
    }
}