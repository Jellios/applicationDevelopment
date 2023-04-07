package com.example.project;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

import java.io.Console;
import java.util.ArrayList;

public class AddQuoteActivity extends AppCompatActivity {

    private EditText editText_quote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quote);
        this.editText_quote = findViewById(R.id.editText_quote_addQuote);
    }
    public void onConfirm(View view)
    {

        String newQuote = editText_quote.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("newQuote", newQuote);
        setResult(MainActivity.RESULT_ADD_QUOTE, intent);
        finish();

    }
    public void onBack(View view)
    {
        finish();
    }

}