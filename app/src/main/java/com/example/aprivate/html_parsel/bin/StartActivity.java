package com.example.aprivate.html_parsel.bin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.services.SearchService;

public class StartActivity extends AppCompatActivity {
    protected Button mBtnStartSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mBtnStartSearch = (Button) findViewById(R.id.button_start_search);
        mBtnStartSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), SearchService.class);
                startService(i);
            }
        });
    }

}
