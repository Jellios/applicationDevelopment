package com.example.project;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements QuotesListFragment.OnQuoteSelectedListener {

    private QuoteDetailFragment quoteDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Create a new QuoteDetailFragment

         this.quoteDetailFragment = (QuoteDetailFragment) getSupportFragmentManager().findFragmentById((R.id.fragment_quote_detail));
        // Set this activity as the listener for the QuotesListFragment
        QuotesListFragment quotesListFragment = (QuotesListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_quotes_list);
        quotesListFragment.setOnQuoteSelectedListener(this);

        // Fetch the quotes from the database
        ArrayList<Quote> quotes = fetchQuotesFromDatabase();

        // Pass the quotes to the QuotesListFragment
        quotesListFragment.setQuoteList(quotes);
        // Create a new QuoteDetailFragment


        // Set this activity as the listener for the QuotesListFragment
       // QuotesListFragment quotesListFragment1 = (QuotesListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_quotes_list);
        quotesListFragment.setOnQuoteSelectedListener(this);

        // Check if the activity is being recreated (e.g., after a configuration change)
        // If it's not, then it's being created for the first time and we need to add the default QuoteDetailFragment
        if (savedInstanceState == null) {
            // Create a new FragmentTransaction
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Add the QuoteDetailFragment
            transaction.add(R.id.fragment_quote_detail, quoteDetailFragment);

            // Commit the transaction
            transaction.commit();
        }
    }

    @Override
    public void onQuoteSelected(Quote quote) {

     /*   Log.d("MainActivity2", "onQuoteSelected() called");
        Log.d("MainActivity2", "quoteId: " + quote.getId());
        Log.d("MainActivity2", "quoteText: " + quote.getQuoteText());
        Log.d("MainActivity2", "quoteAuthor: " + quote.getQuoteAuthor());
        Log.d("MainActivity2", "quoteDate: " + quote.getQuoteDate());
        // Update the QuoteDetailFragment with the selected quote */
        quoteDetailFragment.setQuote(quote);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_quote_detail, quoteDetailFragment);


        // Replace the existing QuoteDetailFragment (if any) with the new one
        transaction.commit();
    }
    private ArrayList<Quote> fetchQuotesFromDatabase() {
        ArrayList<Quote> quotes = new ArrayList<>();

        // Use the content resolver to query the quotes
        ContentResolver contentResolver = getContentResolver();
        String[] projection = {
                QuoteReaderContract.QuoteEntry._ID,
                QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT,
                QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON,
                QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_DATE
        };

        try (Cursor cursor = contentResolver.query(
                QuoteReaderContract.QuoteEntry.CONTENT_URI_QUOTES,
                projection,
                null,
                null,
                null)) {

            // Process the cursor and add entries to the quotes list
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry._ID));
                    String quoteText = cursor.getString(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT));
                    String quotePerson = cursor.getString(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON));
                    String quoteDate = cursor.getString(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_DATE));

                    // Create a Quote object and add it to the list
                    Quote quote = new Quote(quoteText, quotePerson, quoteDate, id);
                    quotes.add(quote);
                }
            }
        } catch (Exception e) {
            // Handle exception
        }

        return quotes;
    }
}