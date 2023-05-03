package com.example.project;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final int RESULT_ADD_QUOTE = 1;
    public TextView tv_randomQuote;

    private ArrayList<Quote> quotes_list;

    private Button btn_ViewQuotes;

    private String[] quotes = {
            "Be the change you wish to see in the world.",
            "The only way to do great work is to love what you do.",
            "In three words I can sum up everything I've learned about life: it goes on.",
            "Believe you can and you're halfway there.",
            "Don't let yesterday take up too much of today."
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.quotes_list = new ArrayList<>();
        this.tv_randomQuote = findViewById(R.id.tv_main_quote);
        int rndm = new Random().nextInt(this.quotes.length);
        this.tv_randomQuote.setText(this.quotes[rndm]);
        this.initialPopulation();


    }
    public void setRandomQuote(View view)
    {
        int limit = this.quotes.length;
        int rand = (int)(Math.random()*limit);
        this.tv_randomQuote.setText(this.quotes[rand]);

    }
    public void onAddQuote(View view)
    {
        Intent intent = new Intent(this, AddQuoteActivity.class);
        startActivity(intent);
    }
    public void onShowAllQuotes(View view)
    {

        Intent intent = new Intent(MainActivity.this, ViewAllQuotesActivity.class);
        startActivity(intent);
    }
    public void emptyDB(View view)
    {
        QuoteReaderDbHelper dbHelper = new QuoteReaderDbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(QuoteReaderContract.QuoteEntry.TABLE_NAME, null, null);
        dbHelper.close();
    }

    private void initialPopulation()
    {
        System.out.println("test");
    }

}