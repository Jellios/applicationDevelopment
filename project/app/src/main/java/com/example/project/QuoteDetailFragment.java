package com.example.project;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class QuoteDetailFragment extends Fragment {

    private EditText quoteText;
    private EditText quoteAuthor;
    private EditText quoteDate;
    private Quote selectedQuote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quote_detail, container, false);

        // Initialize the EditText fields
        quoteText = view.findViewById(R.id.editText_quote);
        quoteAuthor = view.findViewById(R.id.editText_author);
        quoteDate = view.findViewById(R.id.editText_date);

        // Set the EditText fields if a quote has been selected
        if (selectedQuote != null) {
            quoteText.setText(selectedQuote.getQuoteText());
            quoteAuthor.setText(selectedQuote.getQuoteAuthor());
            quoteDate.setText(selectedQuote.getQuoteDate());
        }

        // Initialize the save and delete buttons
        Button saveButton = view.findViewById(R.id.button_save);
        Button deleteButton = view.findViewById(R.id.button_delete);

        // Set the click listeners for the buttons
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveQuote(v);
                removeFragment();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteQuote();
                removeFragment();
            }
        });

        return view;
    }

    public void setQuote(Quote quote) {
        // Store the selected quote
        this.selectedQuote = quote;

        // If the EditText fields have been initialized, set their text
        if (quoteText != null && quoteAuthor != null && quoteDate != null) {
            quoteText.setText(quote.getQuoteText());
            quoteAuthor.setText(quote.getQuoteAuthor());
            quoteDate.setText(quote.getQuoteDate());
        }
    }

    public void saveQuote(View view) {
        // Create a new ContentValues object
        ContentValues values = new ContentValues();
        values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT, quoteText.getText().toString());
        values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON, quoteAuthor.getText().toString());
        values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_DATE, quoteDate.getText().toString());

        // Create the URI for the quote
        Uri quoteUri = ContentUris.withAppendedId(QuoteReaderContract.QuoteEntry.CONTENT_URI_QUOTES, selectedQuote.getId());

        // Update the quote in the database
        int rowsUpdated = getContext().getContentResolver().update(quoteUri, values, null, null);

        if (rowsUpdated != 0) {
            QuotesListFragment quotesListFragment = (QuotesListFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_quotes_list);
            if (quotesListFragment != null) {
                quotesListFragment.refreshQuotesList();
            }
        }
    }

    private void deleteQuote() {
        // Create the URI for the quote
        Uri quoteUri = ContentUris.withAppendedId(QuoteReaderContract.QuoteEntry.CONTENT_URI_QUOTES, selectedQuote.getId());

        // Delete the quote from the database
        getContext().getContentResolver().delete(quoteUri, null, null);

        // After deleting the quote, refresh the QuotesListFragment
        QuotesListFragment quotesListFragment = (QuotesListFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_quotes_list);
        if (quotesListFragment != null) {
            quotesListFragment.refreshQuotesList();
        }
    }

    private void removeFragment() {
        getParentFragmentManager().beginTransaction().remove(this).commit();
    }
}