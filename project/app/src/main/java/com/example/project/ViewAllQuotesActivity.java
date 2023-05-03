package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ViewAllQuotesActivity extends AppCompatActivity {

    private ArrayList<Quote> quotes_list;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_quotes);
        recyclerView = findViewById(R.id.AllQoutes_RCV);
        this.quotes_list = new ArrayList<>();

        // Get a reference to the database
        QuoteReaderDbHelper dbHelper = new QuoteReaderDbHelper(this.getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.


        String[] projection = {
                BaseColumns._ID,
                QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT,
                QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON,
                QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_DATE,
        };

// Define a sortOrder for the query
        String sortOrder =
                QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT + " ASC";

// Perform a query on the database
        Cursor cursor = db.query(
                QuoteReaderContract.QuoteEntry.TABLE_NAME,   // The table to query
                projection,                                 // The columns to return
                null,                                       // The columns for the WHERE clause
                null,                                       // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                sortOrder                                   // The sort order
        );

// Loop through the Cursor to get all the entries

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry._ID));
            String text = cursor.getString(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT));
            String person = cursor.getString(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_DATE));
            this.quotes_list.add(new Quote(text,person,date));

        }

// Close the Cursor and the database
        cursor.close();
        dbHelper.close();

// At this point, quotesList contains all the quotes from the quotes table

        setAdapter();
    }

    public void endActivity(View view)
    {
        finish();
    }

    private void setAdapter() {
        recyclerAdapter adapter = new recyclerAdapter(quotes_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setQuoteInfo() {
       System.out.println("test");
    }
}