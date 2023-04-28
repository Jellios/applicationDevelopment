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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quote);
        this.editText_quote = findViewById(R.id.editText_quote_addQuote);
        QuoteReaderDbHelper mDbHelper = new QuoteReaderDbHelper(this.getApplicationContext());
        //System.out.println(mDbHelper.getDatabaseName());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
    }
    public void onConfirm(View view)
    {

        String newQuote = editText_quote.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("newQuote", newQuote);
        setResult(MainActivity.RESULT_ADD_QUOTE, intent);
        QuoteReaderDbHelper mDbHelper = new QuoteReaderDbHelper(this.getApplicationContext());
        //System.out.println(mDbHelper.getDatabaseName());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT, newQuote);
        values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON, "jelle");

        long newRowId = db.insert(QuoteReaderContract.QuoteEntry.TABLE_NAME, null, values);

        finish();

    }
    public void onBack(View view)
    {
        finish();
    }



}