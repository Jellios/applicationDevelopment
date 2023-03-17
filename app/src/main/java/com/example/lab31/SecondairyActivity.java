package com.example.lab31;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondairyActivity extends AppCompatActivity {

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondairy);
        this.tv = findViewById(R.id.tv_second);
        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)== true)
        {
            String text = intent.getStringExtra(Intent.EXTRA_TEXT);
            tv.setText(text);
        }
    }
}