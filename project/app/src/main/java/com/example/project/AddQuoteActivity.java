package com.example.project;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Console;
import java.util.ArrayList;

public class AddQuoteActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private EditText editText_quote;
    private EditText editText_author;
    private EditText editText_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quote);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.nav_add_quote) {
                    //   Intent intent = new Intent(AddQuoteActivity.this, AddQuoteActivity.class);
                    //startActivity(intent);
                }
                else if (id == R.id.nav_view_all)
                {
                    Intent intent = new Intent(AddQuoteActivity.this, ViewAllQuotesActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(AddQuoteActivity.this, MainActivity2.class);
                    startActivity(intent);
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        this.editText_quote = findViewById(R.id.editText_quote_addQuote);
        this.editText_author = findViewById(R.id.edit_text_quotePerson_addQuote);
        this.editText_date = findViewById(R.id.edit_text_quoteDate_addQuote);
        QuoteReaderDbHelper mDbHelper = new QuoteReaderDbHelper(this.getApplicationContext());

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
    }

    public void onConfirm(View view) {


            String newQuote = editText_quote.getText().toString();
            String author = editText_author.getText().toString();
            String date = editText_date.getText().toString();

            ContentValues values = new ContentValues();
            values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT, newQuote);
            values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON, author);
            values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_DATE, date);

            ContentResolver contentResolver = getContentResolver();


            Uri newQuoteUri = contentResolver.insert(QuoteReaderContract.QuoteEntry.CONTENT_URI_QUOTES, values);


            long newRowId;
            if (newQuoteUri != null) {
                newRowId = Long.parseLong(newQuoteUri.getLastPathSegment());
            } else {

                newRowId = -1;
            }



        finish();

    }

    public void onBack(View view) {
        finish();
    }

    private void emptyDB() {
        QuoteReaderDbHelper dbHelper = new QuoteReaderDbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(QuoteReaderContract.QuoteEntry.TABLE_NAME, null, null);
        dbHelper.close();
    }


}