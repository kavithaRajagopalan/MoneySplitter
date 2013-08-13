package com.kavitha.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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
        database.insert(TABLE_NAME, null, values);
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
        Cursor cursor = database.query(TABLE_NAME,all_columns, COLUMN_FROM +"=? or "+COLUMN_TO+"=?", new String[]{userName, userName},null,null,COLUMN_FROM);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            transactions.add(cursorToComment(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return transactions;
    }

    public long amountBetween(String userOne, String userTwo) {
        long credit = 0;
        long debit = 0;
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME,all_columns, COLUMN_FROM +"=? and "+COLUMN_TO+"=?", new String[]{userOne, userTwo},null,null,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            credit+= cursor.getLong(3);
            cursor.moveToNext();
        }
        cursor.close();
        cursor = database.query(TABLE_NAME,all_columns, COLUMN_FROM +"=? and "+COLUMN_TO+"=?", new String[]{userTwo, userOne},null,null,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            debit+= cursor.getLong(3);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();

        return credit-debit;
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