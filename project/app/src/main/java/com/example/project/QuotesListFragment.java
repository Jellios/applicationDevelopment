package com.example.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QuotesListFragment extends Fragment {

    private OnQuoteSelectedListener callback;
    private recyclerAdapter adapter;

    public interface OnQuoteSelectedListener {
        void onQuoteSelected(Quote quote);
    }

    public void setOnQuoteSelectedListener(OnQuoteSelectedListener callback) {
        this.callback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quotes_list, container, false);

        // Initialize RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_quotes);

        // Initialize the adapter
        adapter = new recyclerAdapter(new ArrayList<>());

        // Set the adapter for the RecyclerView
        recyclerView.setAdapter(adapter);

        // Set the layout manager for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        return view;
    }

    public void setQuoteList(ArrayList<Quote> quoteList) {
        // Update the data in the adapter
        adapter.setQuoteList(quoteList);

        // Notify the adapter that the data has changed
        adapter.notifyDataSetChanged();
    }
    public void refreshQuotesList() {
        // Fetch the updated list of quotes from the database
        ArrayList<Quote> updatedQuotes = ((MainActivity2)getActivity()).fetchQuotesFromDatabase();

        // Update the quote list in the adapter
        setQuoteList(updatedQuotes);
    }
}