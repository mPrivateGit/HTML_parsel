package com.example.aprivate.html_parsel.bin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.aprivate.html_parsel.SearchProduct;
import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.dialogs.EditDialog;
import com.example.aprivate.html_parsel.dialogs.EditDialogDeleteUserProduct;
import com.example.aprivate.html_parsel.dialogs.EditDialogStartSearch;
import com.example.aprivate.html_parsel.interfaces.EditDialogInterface;

import java.util.List;

public class MainActivity extends AppCompatActivity implements EditDialogInterface {
    private final static String TAG = "MainActivity";
    private List<SearchProduct> mSearchProducts;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startRecyclerView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(i);
                //TODO Пофиксить нажатия, сделать так, чтобы не важно сколько нажатий был 1 вызов
            }
        });
    }

    void startRecyclerView(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        RecyclerViewFragment recyclerViewFragment = new RecyclerViewFragment();
        ft.add(R.id.recycler_view_container, recyclerViewFragment,
                RecyclerViewFragment.class.getCanonicalName());
        ft.commit();
    }

    @Override
    public void onChanged() {
        RecyclerViewFragment recyclerViewFragment = (RecyclerViewFragment)
        getSupportFragmentManager().findFragmentByTag(RecyclerViewFragment.class.getCanonicalName());
        recyclerViewFragment.notifyRecycler();
        Log.d(TAG, "onChanged()");
    }
}
