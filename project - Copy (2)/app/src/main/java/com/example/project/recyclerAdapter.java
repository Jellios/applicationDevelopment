package com.example.project;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {

    private final ArrayList<Quote> quoteList;

    public recyclerAdapter(ArrayList<Quote> quoteList)
    {
        this.quoteList = quoteList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView quote_tv;
        private final TextView quote_tv_person;
        private final TextView quote_tv_date;

        private final TextView quote_tv_id;




        public MyViewHolder(final View view)
        {
            super(view);
            quote_tv = view.findViewById(R.id.tv_quotes_list1);
            quote_tv_person = view.findViewById(R.id.quote_tv_person);
            quote_tv_date = view.findViewById(R.id.quote_tv_date);
            quote_tv_id = view.findViewById(R.id.tv_quote_id);

        }
    }

    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.qoutes_list,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {


        Quote quote = quoteList.get(position);
        holder.quote_tv.setText(quote.getQuoteText());
        holder.quote_tv_person.setText(quote.getQuoteAuthor());
        holder.quote_tv_date.setText(quote.getQuoteDate());
        holder.quote_tv_id.setText(String.valueOf(quote.getId()));


    }

    @Override
    public int getItemCount() {
        return quoteList.size();
    }
}
