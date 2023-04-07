package com.example.project;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {

    private ArrayList<Quote> quoteList;

    public recyclerAdapter(ArrayList<Quote> quoteList)
    {
        this.quoteList = quoteList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView quote_tv;

        public MyViewHolder(final View view)
        {
            super(view);
            quote_tv = view.findViewById(R.id.tv_quotes_list1);
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
            String quoteText = quoteList.get(position).getQuoteText();
            holder.quote_tv.setText(quoteText);
    }

    @Override
    public int getItemCount() {
        return quoteList.size();
    }
}
