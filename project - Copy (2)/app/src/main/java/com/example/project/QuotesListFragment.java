package com.example.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class QuotesListFragment extends Fragment {

    private OnQuoteSelectedListener callback;

    public interface OnQuoteSelectedListener {
        void onQuoteSelected(Quote quote);
    }

    public void setOnQuoteSelectedListener(OnQuoteSelectedListener callback) {
        this.callback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quotes_list, container, false);

        ListView listView = view.findViewById(R.id.list_view_quotes);
        ArrayList<Quote> quotes = getQuotes(); // Implement this method to get the list of quotes from your database

        // Set up the adapter and list view
        // Assume QuoteAdapter is a custom adapter for displaying Quote objects
        QuoteAdapter adapter = new QuoteAdapter(getActivity(), quotes);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (callback != null) {
                    Quote quote = (Quote) parent.getItemAtPosition(position);
                    callback.onQuoteSelected(quote);
                }
            }
        });

        return view;
    }
}