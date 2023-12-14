package com.example.project;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Quote;
import com.example.project.QuoteReaderContract;
import com.example.project.QuotesListFragment;
import com.example.project.R;
import com.example.project.recyclerAdapter;
import java.util.ArrayList;

public class ViewAllQuotesActivity extends AppCompatActivity {

    private ArrayList<Quote> quotes_list;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_all_quotes);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.AllQoutes_RCV);
        this.quotes_list = new ArrayList<>();

        // Fetch the data from the database
        fetchQuotesFromDatabase();

        // Get the QuotesListFragment
        QuotesListFragment quotesListFragment = (QuotesListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_quotes_list);

        // Pass the data to the QuotesListFragment
        if (quotesListFragment != null) {
            quotesListFragment.setQuoteList(quotes_list);
        }

        setAdapter();
    }

    public void endActivity(View view) {
        finish();
    }

    private void setAdapter() {
        recyclerAdapter adapter = new recyclerAdapter (quotes_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    protected void fetchQuotesFromDatabase() {
        // Now you can use this URI to query all quotes using ContentResolver
        ContentResolver contentResolver = getContentResolver();

        String[] projection = {
                QuoteReaderContract.QuoteEntry._ID,
                QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT,
                QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON,
                QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_DATE
        };

        Cursor cursor = contentResolver.query(
                QuoteReaderContract.QuoteEntry.CONTENT_URI_QUOTES,
                projection,
                null,
                null,
                null
        );

        // Process the cursor and add entries to the quotes_list
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry._ID));
                    String quoteText = cursor.getString(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT));
                    String quotePerson = cursor.getString(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON));
                    String quoteDate = cursor.getString(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_DATE));

                    // Create a Quote object and add it to the list
                    Quote quote = new Quote(quoteText, quotePerson, quoteDate, id);
                    quotes_list.add(quote);
                }
            } finally {
                cursor.close();
            }
        }
    }
}