package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
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
        QuoteReaderDbHelper mDbHelper = new QuoteReaderDbHelper(this.getApplicationContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID, QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT,
                QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON};
    String selection = QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT + " = ?";
    String[] selectionArgs = { "*" };
    String sortOrder = QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT + " DESC";

    Cursor result = db.query(QuoteReaderContract.QuoteEntry.TABLE_NAME, projection, selection,
            selectionArgs, null, null, sortOrder);
   while (result.moveToNext())
   {
       this.quotes_list.add(new Quote(result.getString(result.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry._ID))));
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