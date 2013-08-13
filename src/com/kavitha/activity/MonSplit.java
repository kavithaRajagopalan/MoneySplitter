package com.kavitha.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kavitha.R;
import com.kavitha.database.AmountDataSource;
import com.kavitha.model.Credit;

import java.util.List;

public class MonSplit extends Activity {
    private AmountDataSource amountDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        amountDAO = new AmountDataSource(this);
    }

    public void submitTransaction(View view) {
        String fromName = ((EditText) findViewById(R.id.fromName)).getText().toString();
        String toName = ((EditText) findViewById(R.id.toName)).getText().toString();
        long amount = Long.parseLong(((EditText) findViewById(R.id.amount)).getText().toString());
        amountDAO.insertTransaction(fromName, toName, amount);
        Toast.makeText(this, "Sucessfully inserted", Toast.LENGTH_SHORT).show();
    }

    public void deleteAllTransactions(View view) {
        amountDAO.deleteAllTransactions();
        LinearLayout showTransactions = (LinearLayout) findViewById(R.id.transactionsView);
        if (showTransactions != null) {
            showTransactions.removeAllViews();
        }
    }

    public void viewAllTransactions(View view) {
        List<Credit> allTransactions = amountDAO.listAllTransactions();
        populateTransactions(allTransactions);
    }

    public void viewTransactionForUser(View view) {
        String fromName = ((EditText) findViewById(R.id.fromName)).getText().toString();
        List<Credit> creditsForUser = amountDAO.listForUser(fromName);
       populateTransactions(creditsForUser);
    }

    public void amountBetween(View view) {
        String fromName = ((EditText) findViewById(R.id.fromName)).getText().toString();
        String toName = ((EditText) findViewById(R.id.toName)).getText().toString();
        String message = "";
        TextView textView = new TextView(this);

        long balanceAmount = amountDAO.amountBetween(fromName, toName);
        LinearLayout showTransactions = (LinearLayout) findViewById(R.id.transactionsView);
        showTransactions.removeAllViews();
        message = (balanceAmount<0) ? fromName+ " needs to pay "+ balanceAmount* -1 + " to " + toName : toName+ " manifneeds to pay "+ balanceAmount + " to " + fromName;
        textView.setText(message);
        showTransactions.addView(textView);
    }

    private void populateTransactions(List<Credit> allTransactions) {
        LinearLayout showTransactions = (LinearLayout) findViewById(R.id.transactionsView);
        showTransactions.removeAllViews();

        for (Credit transaction : allTransactions) {
            TextView textView = new TextView(this);
            textView.setText(transaction.getdebitedFrom() + "  " + transaction.getcreditedTo() + "  " + transaction.getAmount());
            showTransactions.addView(textView);
        }
    }
}
