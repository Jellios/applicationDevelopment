package com.example.project;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class QuoteDetailFragment extends Fragment {

    public EditText quoteText;
    public  EditText quoteAuthor;
    public EditText quoteDate;
    public int quoteId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quote_detail, container, false);

        // Initialize the EditText fields
        quoteText = view.findViewById(R.id.editText_quote);
        quoteAuthor = view.findViewById(R.id.editText_author);
        quoteDate = view.findViewById(R.id.editText_date);

        // Initialize the save and delete buttons
        Button saveButton = view.findViewById(R.id.button_save);
        Button deleteButton = view.findViewById(R.id.button_delete);

        // Set the click listeners for the buttons
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveQuote(v);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteQuote();
            }
        });

        return view;
    }

    public void setQuote(Quote quote) {
        // Update the EditText fields with the details of the quote
        Log.d("QuoteDetailFragment", "setQuote() called");
        Log.d("QuoteDetailFragment", "quoteText: " + quote.getQuoteText().toString());
        Log.d("QuoteDetailFragment", "quoteAuthor: " + quote.getQuoteAuthor().toString());
        Log.d("QuoteDetailFragment", "quoteDate: " + quote.getQuoteDate().toString());

        quoteText.setText(quote.getQuoteText().toString());
        quoteAuthor.setText(quote.getQuoteAuthor().toString());
        quoteDate.setText(quote.getQuoteDate().toString());
        quoteId = quote.getId();
        Log.d("QuoteDetailFragment", "-----------------------------------------");
        Log.d("QuoteDetailFragment", "quoteId: " + quoteId);
        Log.d("QuoteDetailFragment", "quoteText: " + quoteText.getText().toString());
        Log.d("QuoteDetailFragment", "quoteAuthor: " + quoteAuthor.getText().toString());
        Log.d("QuoteDetailFragment", "quoteDate: " + quoteDate.getText().toString());

    }

    public void saveQuote(View view) {


        // Log the method call
        Log.d("QuoteDetailFragment", "saveQuote() called");

        // Create a new ContentValues object
        ContentValues values = new ContentValues();
        values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT, quoteText.getText().toString());
        values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON, quoteAuthor.getText().toString());
        values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_DATE, quoteDate.getText().toString());

        // Log the values
        Log.d("QuoteDetailFragment", "quoteId: " + quoteId);
        Log.d("QuoteDetailFragment", "quoteText: " + quoteText.getText().toString());
        Log.d("QuoteDetailFragment", "quoteAuthor: " + quoteAuthor.getText().toString());
        Log.d("QuoteDetailFragment", "quoteDate: " + quoteDate.getText().toString());

        // Create the URI for the quote
        Uri quoteUri = ContentUris.withAppendedId(QuoteReaderContract.QuoteEntry.CONTENT_URI_QUOTES, quoteId);

        // Update the quote in the database
        int rowsUpdated = getContext().getContentResolver().update(quoteUri, values, null, null);

        // Log the result
        Log.d("QuoteDetailFragment", "rowsUpdated: " + rowsUpdated);
    }

    private void deleteQuote() {
        // Create the URI for the quote
        Uri quoteUri = ContentUris.withAppendedId(QuoteReaderContract.QuoteEntry.CONTENT_URI_QUOTES, quoteId);

        // Delete the quote from the database
        getContext().getContentResolver().delete(quoteUri, null, null);
    }
}