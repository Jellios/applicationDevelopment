package com.example.project;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class AddQuoteFragment extends Fragment {

    private EditText quoteText;
    private EditText quoteAuthor;
    private EditText quoteDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_quote, container, false);

        // Initialize the EditText fields
        quoteText = view.findViewById(R.id.editText_quote);
        quoteAuthor = view.findViewById(R.id.editText_author);
        quoteDate = view.findViewById(R.id.editText_date);

        // Initialize the add and cancel buttons
        Button addButton = view.findViewById(R.id.button_add);
        Button cancelButton = view.findViewById(R.id.button_cancel);

        // Set the click listeners for the buttons
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuote(v);
                removeFragment();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
                removeFragment();
            }
        });

        return view;
    }

    public void addQuote(View view) {
        String newQuote = quoteText.getText().toString();
        String author = quoteAuthor.getText().toString();
        String date = quoteDate.getText().toString();

        if (newQuote.isEmpty() != true && author.isEmpty() != true && date.isEmpty() != true)
        {
            ContentValues values = new ContentValues();
            values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT, newQuote);
            values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON, author);
            values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_DATE, date);

            ContentResolver contentResolver = getContext().getContentResolver();

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

        QuotesListFragment quotesListFragment = (QuotesListFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_quotes_list);
        if (quotesListFragment != null) {
            quotesListFragment.refreshQuotesList();
        }
    }

    public void cancel() {
        // TODO: Implement the logic to cancel the operation
    }

    private void removeFragment() {
        getParentFragmentManager().beginTransaction().remove(this).commit();
    }
}