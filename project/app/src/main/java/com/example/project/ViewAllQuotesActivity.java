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

import java.util.ArrayList;

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
                QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON
        };

// Define a sortOrder for the query
        String sortOrder =
                QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT + " DESC";

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
            this.quotes_list.add(new Quote(text));
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
        quotes_list.add(new Quote("If you can see yourself, you'll never be truly blind"));
        quotes_list.add(new Quote("Be the change you wish to see in the world."));
        quotes_list.add(new Quote( "The only way to do great work is to love what you do."));
        quotes_list.add(new Quote("In three words I can sum up everything I've learned about life: it goes on."));
        quotes_list.add(new Quote("Believe you can and you're halfway there."));
        quotes_list.add(new Quote( "Don't let yesterday take up too much of today."));
    }
}