package com.example.aprivate.html_parsel.bin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.sounds.BeatBox;
import com.example.aprivate.html_parsel.sounds.Sound;

public class StartActivity extends AppCompatActivity implements View.OnClickListener{
    protected TextView mTxtStartSearch;
    private Sound mSound;
    protected BeatBox mBeatBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //tODO Sound play
        mBeatBox = new BeatBox(getApplicationContext());
        mBeatBox.play(mBeatBox.getSounds().get(0));

        viewTextView();

    }

    private void viewTextView (){
        mTxtStartSearch = (TextView) findViewById(R.id.txt_start_search);
        mTxtStartSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_start_search:
                Intent i = new Intent(StartActivity.this, MainActivity.class);
                startActivity(i);
                break;
        }
    }
}
