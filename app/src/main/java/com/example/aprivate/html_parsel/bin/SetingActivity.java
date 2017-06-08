package com.example.aprivate.html_parsel.bin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.aprivate.html_parsel.R;

public class SetingActivity extends AppCompatActivity {
    private Button mStartButton;
    private Button mStopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seting);



        mStartButton = (Button) findViewById(R.id.button_start_search);


        mStopButton = (Button) findViewById(R.id.button_stop_search);
    }
}
