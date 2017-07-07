package com.example.aprivate.html_parsel.bin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.services.SearchService;

public class SettingActivity extends AppCompatActivity {
    protected Button mSkipButton;
    protected Button mStopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seting);

        mSkipButton = (Button) findViewById(R.id.btn_skip);
        mSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingActivity.this, StartActivity.class);
                startActivity(i);
            }
        });
    }
}
