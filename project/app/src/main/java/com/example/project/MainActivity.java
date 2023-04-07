package com.example.project;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final int RESULT_ADD_QUOTE = 1;
    public TextView tv_randomQuote;

    private ArrayList<Quote> quotes_list;

    private Button btn_ViewQuotes;

    private String[] quotes = {
            "Be the change you wish to see in the world.",
            "The only way to do great work is to love what you do.",
            "In three words I can sum up everything I've learned about life: it goes on.",
            "Believe you can and you're halfway there.",
            "Don't let yesterday take up too much of today."
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.quotes_list = new ArrayList<>();
        this.tv_randomQuote = findViewById(R.id.tv_main_quote);
        int rndm = new Random().nextInt(this.quotes.length);
        this.tv_randomQuote.setText(this.quotes[rndm]);
        this.initialPopulation();


    }
    public void setRandomQuote(View view)
    {
        int limit = this.quotes.length;
        int rand = (int)(Math.random()*limit);
        this.tv_randomQuote.setText(this.quotes[rand]);

    }
    public void onAddQuote(View view)
    {
        Intent intent = new Intent(this, AddQuoteActivity.class);
        addQuoteLauncher.launch(intent);
    }
    public void onShowAllQuotes(View view)
    {
        ArrayList<String> stringQuotes = new ArrayList<>();
        System.out.println("size of list: " + this.quotes_list.size() + "\n");
        Intent intent = new Intent(MainActivity.this, ViewAllQuotesActivity.class);
        for (Quote test: quotes_list) {
        stringQuotes.add(test.getQuoteText());
        }
        intent.putExtra("list",stringQuotes);
        startActivity(intent);
    }

    private void initialPopulation()
    {
        quotes_list.add(new Quote("If you can see yourself, you'll never be truly blind"));
        quotes_list.add(new Quote("Be the change you wish to see in the world."));
        quotes_list.add(new Quote( "The only way to do great work is to love what you do."));
        quotes_list.add(new Quote("In three words I can sum up everything I've learned about life: it goes on."));
        quotes_list.add(new Quote("Believe you can and you're halfway there."));
        quotes_list.add(new Quote( "Don't let yesterday take up too much of today."));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_ADD_QUOTE && resultCode == RESULT_OK) {
            String newQuote = data.getStringExtra("newQuote");
            quotes_list.add(new Quote(newQuote));
        }
    }
    private ActivityResultLauncher<Intent> addQuoteLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        String newQuote = data.getStringExtra("newQuote");
                        quotes_list.add(new Quote(newQuote));
                    }
                }
            });

}