package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class ViewAllQuotesActivity extends AppCompatActivity {

    private ArrayList<Quote> quotes_list;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_quotes);
        recyclerView = findViewById(R.id.AllQoutes_RCV);
        this.quotes_list = new ArrayList<>();
        ArrayList<String> test_list = (ArrayList<String >) getIntent().getSerializableExtra("list");
        System.out.println("test list size: " + test_list.size() + "\n");
        //setQuoteInfo();
        for (String x: test_list)
        {
            this.quotes_list.add(new Quote(x));
        }
        setAdapter();
    }

    public void endActivity(View view)
    {
        finish();
    }
    private void setAdapter() {
        recyclerAdapter adapter = new recyclerAdapter(quotes_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setQuoteInfo() {
        quotes_list.add(new Quote("If you can see yourself, you'll never be truly blind"));
        quotes_list.add(new Quote("Be the change you wish to see in the world."));
        quotes_list.add(new Quote( "The only way to do great work is to love what you do."));
        quotes_list.add(new Quote("In three words I can sum up everything I've learned about life: it goes on."));
        quotes_list.add(new Quote("Believe you can and you're halfway there."));
        quotes_list.add(new Quote( "Don't let yesterday take up too much of today."));
    }
}