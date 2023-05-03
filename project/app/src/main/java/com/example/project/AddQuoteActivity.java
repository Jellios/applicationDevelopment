package com.example.project;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
        //emptyDB();
        this.editText_quote = findViewById(R.id.editText_quote_addQuote);
        this.editText_author = findViewById(R.id.edit_text_quotePerson_addQuote);
        this.editText_date = findViewById(R.id.edit_text_quoteDate_addQuote);
        QuoteReaderDbHelper mDbHelper = new QuoteReaderDbHelper(this.getApplicationContext());
        //System.out.println(mDbHelper.getDatabaseName());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
    }
    public void onConfirm(View view)
    {

        String newQuote = editText_quote.getText().toString();
        QuoteReaderDbHelper mDbHelper = new QuoteReaderDbHelper(this.getApplicationContext());
        //System.out.println(mDbHelper.getDatabaseName());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT, newQuote);
        values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON,this.editText_author.getText().toString());
        values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_DATE, this.editText_date.getText().toString());
        long newRowId = db.insert(QuoteReaderContract.QuoteEntry.TABLE_NAME, null, values);

        finish();

    }
    public void onBack(View view)
    {
        finish();
    }
    private void emptyDB()
    {
        QuoteReaderDbHelper dbHelper = new QuoteReaderDbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(QuoteReaderContract.QuoteEntry.TABLE_NAME, null, null);
        dbHelper.close();
    }



}