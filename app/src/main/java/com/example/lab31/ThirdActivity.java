package com.example.lab31;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ThirdActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
    }
    public void backMain(View view)
    {
        RadioGroup rdgroup = findViewById(R.id.rdbtn_group);
        RadioButton rdbtn =  findViewById(rdgroup.getCheckedRadioButtonId());
        String val = rdbtn.getText().toString();
        Intent intent = new Intent(ThirdActivity.this, MainActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, val);
        startActivity(intent);
    }
}