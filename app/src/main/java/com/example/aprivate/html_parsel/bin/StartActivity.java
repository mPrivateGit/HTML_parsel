package com.example.aprivate.html_parsel.bin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.dialogs.EditDialogExit;
import com.example.aprivate.html_parsel.fragments.HelpFragment;
import com.example.aprivate.html_parsel.fragments.SupportFragment;
import com.example.aprivate.html_parsel.sounds.BeatBox;
import com.example.aprivate.html_parsel.sounds.Sound;

public class StartActivity extends AppCompatActivity
        implements View.OnClickListener{
    private static final String TAG = ">>>StartActivity: ";
    private static long back_pressed;
    protected TextView mTxtStartSearch;
    protected TextView mTxtHelp;
    protected TextView mTxtSupport;
    protected TextView mTxtExit;
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
        mTxtHelp = (TextView) findViewById(R.id.txt_help);
        mTxtHelp.setOnClickListener(this);
        mTxtSupport = (TextView) findViewById(R.id.txt_support);
        mTxtSupport.setOnClickListener(this);
        mTxtExit = (TextView) findViewById(R.id.txt_exit);
        mTxtExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_start_search:
                Intent i = new Intent(StartActivity.this, MainActivity.class);
                startActivity(i);
                break;
            case R.id.txt_help:
                HelpFragment helpFragment = new HelpFragment();
                FragmentManager fmHelpFragment = getSupportFragmentManager();
                fmHelpFragment.beginTransaction()
                        .add(R.id.frag_container, helpFragment)
                        .commit();
                break;
            case R.id.txt_support:
                SupportFragment supportFragment = new SupportFragment();
                FragmentManager fmSupportFragment = getSupportFragmentManager();
                fmSupportFragment.beginTransaction()
                        .add(R.id.frag_container, supportFragment)
                        .commit();
                break;
            case R.id.txt_exit:
                EditDialogExit exit = new EditDialogExit();
                exit.show(getFragmentManager(), TAG);
        }
    }

    @Override
    public void onBackPressed() {
        openQuitDialog();
    }

    private void openQuitDialog() {
        if (back_pressed + 2000 > System.currentTimeMillis())
            super.onBackPressed();
        else
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }
}
