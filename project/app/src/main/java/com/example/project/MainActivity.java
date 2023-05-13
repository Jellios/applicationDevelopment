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

        this.quotes_list = new ArrayList<>();
        this.tv_randomQuote = findViewById(R.id.tv_main_quote);

       // this.setRandomQuote();
        this.initialPopulation();


    }
    public void triggerRandomQuote(View view)
    {
        this.setRandomQuote();
    }
    public void setRandomQuote()
    {
        // Create a new instance of your QuoteReaderDbHelper
        QuoteReaderDbHelper dbHelper = new QuoteReaderDbHelper(getApplicationContext());

        // Get a random quote from the database
        Quote randomQuote = dbHelper.getRandomQuote();

        // Check if the quote is not null
        if (randomQuote != null) {
            // Set the tv_randomQuote text to the text of the random quote
            tv_randomQuote.setText(randomQuote.getQuoteText() + "\n - " + randomQuote.getQuoteAuthor());
        } else {
            // If the quote is null (there are no quotes in the database), display a default message
            tv_randomQuote.setText("No quotes available.");
        }

        // Close the dbHelper
        dbHelper.close();
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
    public void bogusQuotes(View view) {
        QuoteReaderDbHelper dbHelper = new QuoteReaderDbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for (int i = 1; i <= 20; i++) {
          /*  String quoteText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. " +
                    "Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, " +
                    "Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, " +
                    "Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, " +
                    "Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, " +
                    "Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, " +
                    "Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, " +
                    "ultricies sed, dolor. Cras elementum ultrices diam. Maecenas ligula massa, " +
                    "varius a, semper congue, euismod non, mi. Proin porttitor, orci nec nonummy " +
                    "molestie, enim est eleifend mi, non fermentum diam nisl sit amet erat."; */

            String quoteText = "dit is quote nummer: " + i + ".";
            ContentValues values = new ContentValues();
            LocalDate localDate  = LocalDate.now();
            values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT, quoteText);
            values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON, "Author " + i);
            values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_DATE, localDate.toString());

            db.insert(QuoteReaderContract.QuoteEntry.TABLE_NAME, null, values);
        }

        dbHelper.close();
    }

    private void initialPopulation()
    {
        System.out.println("test");
    }

}