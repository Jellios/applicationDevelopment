package com.lssoftworks.u0068830.example401_internet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lssoftworks.u0068830.example401_internet.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button mSearch;
    TextView mSearchResult;
    EditText mSearchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearch = findViewById(R.id.btn_search);
        mSearchText = findViewById(R.id.et_search);
        mSearchResult = findViewById(R.id.tv_searchresults);

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchString = mSearchText.getText().toString();

                URL githubUrl = NetworkUtils.buildUrl(searchString);
                Toast.makeText(MainActivity.this, githubUrl.toString(), Toast.LENGTH_LONG).show();

                try {
                    String result = NetworkUtils.getResponseFromHttpUrl(githubUrl);
                    mSearchResult.setText(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
