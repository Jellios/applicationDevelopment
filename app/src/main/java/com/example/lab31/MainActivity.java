package com.example.lab31;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Console;

public class MainActivity extends AppCompatActivity {
    EditText et;
    TextView tv;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.et = findViewById(R.id.te_main);
        this.tv = findViewById(R.id.tv_main1);
        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)== true)
        {
            String text = intent.getStringExtra(Intent.EXTRA_TEXT);
            this.tv.setText(text);
            this.tv.setVisibility(View.VISIBLE);
        }
    }
    public void naarAndereDing(View view)
    {

        String test = et.getText().toString();
        findViewById(R.id.btn_main).setTooltipText(et.getText());
        Intent intent = new Intent(MainActivity.this, SecondairyActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT,test);
        startActivity(intent);

    }
    public void naarEenNogAnderDing(View view)
    {
        Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
        startActivity(intent);
    }

}