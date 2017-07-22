package com.example.aprivate.html_parsel.bin;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.fragments.HelpFragment;
import com.example.aprivate.html_parsel.fragments.SupportFragment;

public class Menu_Fragment_Activity extends AppCompatActivity {
    private static final String CHOSEN_FRAGMENT = "key_fragment";
    private int mChosenFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_fragment_);

        setChose();

        viewFragments(mChosenFragment);
    }

    private void setChose(){
        Intent intent = getIntent();
        if (intent.hasExtra(CHOSEN_FRAGMENT)){
            mChosenFragment = intent.getExtras().getInt(CHOSEN_FRAGMENT);
        } else {
            Toast error = Toast.makeText(this,
                    "Activity - Menu_Fragment_Activity {} onknow error", Toast.LENGTH_SHORT);
            error.show();
        }
    }

    private void viewFragments(int choose){
        if (choose == 1){
            SupportFragment supportFragment = new SupportFragment();
            FragmentManager fmSupportFragment = getSupportFragmentManager();
            fmSupportFragment.beginTransaction()
                    .add(R.id.fragment_container, supportFragment)
                    .commit();
        } else if (choose == 2){
            HelpFragment helpFragment = new HelpFragment();
            FragmentManager fmHelpFragment = getSupportFragmentManager();
            fmHelpFragment.beginTransaction()
                    .add(R.id.fragment_container, helpFragment)
                    .commit();
        }
    }
}
