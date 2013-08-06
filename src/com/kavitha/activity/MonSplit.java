package com.kavitha.activity;

import android.app.Activity;
import android.os.Bundle;
import com.kavitha.R;
import com.kavitha.database.AmountDataSource;

public class MonSplit extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        AmountDataSource amountDAO = new AmountDataSource(this);
        amountDAO.insertTransaction("Kavitha", "ABD", 1000);
        amountDAO.listAllTransactions();
    }

}
