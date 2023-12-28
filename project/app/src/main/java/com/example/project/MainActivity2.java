package com.example.project;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements QuotesListFragment.OnQuoteSelectedListener {

    private QuoteDetailFragment quoteDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();

                if (id == R.id.nav_add_quote) {
                    Intent intent = new Intent(MainActivity2.this, AddQuoteActivity.class);
                    startActivity(intent);
                }
                else if (id == R.id.nav_view_all)
                {
                    Intent intent = new Intent(MainActivity2.this, ViewAllQuotesActivity.class);
                    startActivity(intent);
                }
                else
                {
                   // Intent intent = new Intent(MainActivity2.this, MainActivity2.class);
                    //startActivity(intent);
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        // Create a new QuoteDetailFragment
        this.quoteDetailFragment = new QuoteDetailFragment();

        // Set this activity as the listener for the QuotesListFragment
        QuotesListFragment quotesListFragment = (QuotesListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_quotes_list);
        quotesListFragment.setOnQuoteSelectedListener(this);

        // Fetch the quotes from the database
        ArrayList<Quote> quotes = fetchQuotesFromDatabase();

        // Pass the quotes to the QuotesListFragment
        quotesListFragment.setQuoteList(quotes);

        // Make the FrameLayout visible
        FrameLayout fragmentContainer = findViewById(R.id.fragment_container);
        fragmentContainer.setVisibility(View.VISIBLE);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new AddQuoteFragment
                AddQuoteFragment addQuoteFragment = new AddQuoteFragment();

                // Replace the existing QuoteDetailFragment (if any) with the new one
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, addQuoteFragment)
                        .commit();
            }
        });
    }

    @Override
    public void onQuoteSelected(Quote quote) {
        // Create a new QuoteDetailFragment
        QuoteDetailFragment quoteDetailFragment = new QuoteDetailFragment();
        quoteDetailFragment.setQuote(quote);

        // Replace the fragment in fragment_container
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, quoteDetailFragment)
                .addToBackStack(null)
                .commit();
    }

    protected ArrayList<Quote> fetchQuotesFromDatabase() {
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