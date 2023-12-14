package com.example.project;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

import java.io.Console;
import java.util.ArrayList;

public class AddQuoteActivity extends AppCompatActivity {

    private EditText editText_quote;
    private EditText editText_author;
    private EditText editText_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quote);
        getSupportActionBar().hide();

        this.editText_quote = findViewById(R.id.editText_quote_addQuote);
        this.editText_author = findViewById(R.id.edit_text_quotePerson_addQuote);
        this.editText_date = findViewById(R.id.edit_text_quoteDate_addQuote);
        QuoteReaderDbHelper mDbHelper = new QuoteReaderDbHelper(this.getApplicationContext());

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
    }

    public void onConfirm(View view) {

        for (int i = 0; i < 50; i++)
        {
            String newQuote = editText_quote.getText().toString();
            String author = editText_author.getText().toString();
            String date = editText_date.getText().toString();

            ContentValues values = new ContentValues();
            values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT, newQuote);
            values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON, author);
            values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_DATE, date);

            ContentResolver contentResolver = getContentResolver();

// Use the content resolver to insert the new quote
            Uri newQuoteUri = contentResolver.insert(QuoteReaderContract.QuoteEntry.CONTENT_URI_QUOTES, values);

// The new row ID can be obtained from the URI
            long newRowId;
            if (newQuoteUri != null) {
                newRowId = Long.parseLong(newQuoteUri.getLastPathSegment());
            } else {
                // Handle the error case if the insertion fails
                newRowId = -1;
            }
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