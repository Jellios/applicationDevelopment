package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewAllQuotesActivity extends AppCompatActivity {

    private ArrayList<Quote> quotes_list;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_quotes);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.AllQoutes_RCV);
        this.quotes_list = new ArrayList<>();

        Uri allQuotesUri = QuoteReaderContract.QuoteEntry.CONTENT_URI_QUOTES;

        // Now you can use this URI to query all quotes using ContentResolver
        ContentResolver contentResolver = getContentResolver();

        String[] projection = {
                QuoteReaderContract.QuoteEntry._ID,
                QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT,
                QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON,
                QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_DATE
        };

        Cursor cursor = contentResolver.query(
                allQuotesUri,
                projection,
                null,
                null,
                null
        );

        // Process the cursor and add entries to the quotes_list
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry._ID));
                    String quoteText = cursor.getString(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT));
                    String quotePerson = cursor.getString(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON));
                    String quoteDate = cursor.getString(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_DATE));

                    // Create a Quote object and add it to the list
                    Quote quote = new Quote(quoteText, quotePerson, quoteDate, id);
                    quotes_list.add(quote);
                }
            } finally {
                cursor.close();
            }
        }

        // Now quotes_list contains all the quotes from the cursor
        // You can use quotes_list for further processing or displaying in a RecyclerView
        setAdapter();
    }

    public void endActivity(View view) {
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
        System.out.println("test");
    }

    public void showPopup(View view) {
        EditText text;
        EditText author;
        EditText date;
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View quotePopupView = inflater.inflate(R.layout.pop_up, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(quotePopupView, width, height, focusable);

        text = quotePopupView.findViewById(R.id.popUpQuoteText);
        author = quotePopupView.findViewById(R.id.popup_Quote_author);
        date = quotePopupView.findViewById(R.id.popup_quote_date);

        TextView tv_id = view.findViewById(R.id.tv_quote_id);

        TextView quoteTextView = view.findViewById(R.id.tv_quotes_list1);
        String quoteText = quoteTextView.getText().toString();
        text.setText(quoteText);
        TextView quoteAuthor = view.findViewById(R.id.quote_tv_person);
        TextView quoteDate = view.findViewById(R.id.quote_tv_date);
        author.setText(quoteAuthor.getText().toString());
        date.setText(quoteDate.getText().toString());

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        quotePopupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

        Button btn_save_changes = quotePopupView.findViewById(R.id.btn_popup_saveChanges);
        btn_save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp = tv_id.getText().toString();
                int id = Integer.valueOf(tmp);

                ContentValues values = new ContentValues();
                values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT, text.getText().toString());
                values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON, author.getText().toString());
                values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_DATE, date.getText().toString());

                Uri quoteUri = ContentUris.withAppendedId(QuoteReaderContract.QuoteEntry.CONTENT_URI_QUOTES, id);
                getContentResolver().update(quoteUri, values, null, null);

                popupWindow.dismiss();
                recreate();
            }
        });

        Button btn_delete_quote = quotePopupView.findViewById(R.id.btn_popup_DeleteQuote);
        btn_delete_quote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = tv_id.getText().toString();
                int id = Integer.valueOf(tmp);

                Uri quoteUri = ContentUris.withAppendedId(QuoteReaderContract.QuoteEntry.CONTENT_URI_QUOTES, id);
                getContentResolver().delete(quoteUri, null, null);

                popupWindow.dismiss();
                recreate();
            }
        });
    }
}