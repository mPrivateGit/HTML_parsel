package com.example.aprivate.html_parsel.bin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.dialogs.EditDialogExit;
import com.example.aprivate.html_parsel.fragments.HelpFragment;
import com.example.aprivate.html_parsel.fragments.SupportFragment;
import com.example.aprivate.html_parsel.sounds.BeatBox;
import com.example.aprivate.html_parsel.sounds.Sound;
//todo logic for after choose language
public class StartActivity extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = ">>>StartActivity: ";
    private static final String CHOSEN_FRAGMENT = "key_fragment";
    private static long back_pressed;
    protected TextView mTxtStartSearch;
    protected TextView mTxtHelp;
    protected TextView mTxtSupport;
    protected TextView mTxtExit;
    protected BeatBox mBeatBox;
    protected Spinner mSpn_Language;
    private Sound mSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //tODO Sound play // need?
        mBeatBox = new BeatBox(getApplicationContext());
        mBeatBox.play(mBeatBox.getSounds().get(0));

         /*Text(ы)*/
        viewTextView();

        /*Спинеры*/
        viewSpinners();
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

    private void viewSpinners(){
        mSpn_Language = (Spinner) findViewById(R.id.spn_language);
        mSpn_Language.setOnItemSelectedListener(this);
        ArrayAdapter<?> spn_language_adapter =
                ArrayAdapter.createFromResource(this, R.array.arr_languages,
                        android.R.layout.simple_spinner_dropdown_item);
        mSpn_Language.setAdapter(spn_language_adapter);
        spn_language_adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_start_search:
                Intent i = new Intent(StartActivity.this, MainActivity.class);
                startActivity(i);
                break;
            case R.id.txt_help:
                Intent help = new Intent(StartActivity.this, Menu_Fragment_Activity.class);
                help.putExtra(CHOSEN_FRAGMENT, 2);
                startActivity(help);
                break;
            case R.id.txt_support:
                Intent support = new Intent(StartActivity.this, Menu_Fragment_Activity.class);
                support.putExtra(CHOSEN_FRAGMENT, 1);
                startActivity(support);
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

    @Override
    public void onItemSelected(AdapterView<?> parent,
                               View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
//248