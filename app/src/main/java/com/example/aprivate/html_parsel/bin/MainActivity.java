package com.example.aprivate.html_parsel.bin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.aprivate.html_parsel.Product;
import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.dialogs.EditDialog;
import com.example.aprivate.html_parsel.interfaces.EditDialogInterface;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EditDialogInterface {
    private final static String TAG = "MainActivity";
    private List<Product> mProducts;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startRecyclerView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(MainActivity.this, StartActivity.class);
                //startActivity(i);
                EditDialog editDialog = new EditDialog();
                getFragmentManager().beginTransaction()
                        .add(editDialog, EditDialog.class.getCanonicalName())
                        .commitAllowingStateLoss();
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
