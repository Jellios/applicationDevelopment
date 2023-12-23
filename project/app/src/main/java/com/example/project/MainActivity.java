package com.example.project;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final int RESULT_ADD_QUOTE = 1;
    public TextView tv_randomQuote;

    private ArrayList<Quote> quotes_list;

    private Button btn_ViewQuotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        this.quotes_list = new ArrayList<>();
        this.tv_randomQuote = findViewById(R.id.tv_main_quote);
        System.out.println("test");
        this.setRandomQuote();


    }

    public void triggerRandomQuote(View view) {
        this.setRandomQuote();
    }

    public void setRandomQuote() {

        QuoteReaderDbHelper dbHelper = new QuoteReaderDbHelper(getApplicationContext());


        Quote randomQuote = dbHelper.getRandomQuote();


        if (randomQuote != null) {

            tv_randomQuote.setText(randomQuote.getQuoteText() + "\n - " + randomQuote.getQuoteAuthor());
        } else {

            tv_randomQuote.setText("No quotes available.");
        }


        dbHelper.close();
    }


    public void onAddQuote(View view) {
        Intent intent = new Intent(this, AddQuoteActivity.class);
        startActivity(intent);
    }
    public void onTestActivity(View view)
    {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
    public void onShowAllQuotes(View view) {

        Intent intent = new Intent(MainActivity.this, ViewAllQuotesActivity.class);
        startActivity(intent);
    }


}