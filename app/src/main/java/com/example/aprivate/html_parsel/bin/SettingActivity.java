package com.example.aprivate.html_parsel.bin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.services.SearchService;

public class SettingActivity extends AppCompatActivity
        implements View.OnClickListener{
    protected Button mBtnCancel;
    protected Button mBtnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seting);

        mBtnCancel = (Button) findViewById(R.id.btn_cancel_action);
        mBtnCancel.setOnClickListener(this);
        mBtnOk = (Button) findViewById(R.id.btn_save_action);
        mBtnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel_action:
                Intent cancel = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(cancel);
                break;
            case R.id.btn_save_action:
                //Todo сохрани данные
                Intent ok = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(ok);
                break;
        }
    }
}
