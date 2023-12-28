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

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_quotes);

        adapter = new recyclerAdapter(new ArrayList<>());

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        return view;
    }

    public void setQuoteList(ArrayList<Quote> quoteList) {
        adapter.setQuoteList(quoteList);

        adapter.notifyDataSetChanged();
    }
    public void refreshQuotesList() {
        ArrayList<Quote> updatedQuotes = ((MainActivity2)getActivity()).fetchQuotesFromDatabase();

        setQuoteList(updatedQuotes);
    }
}