package com.example.aprivate.html_parsel.bin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.aprivate.html_parsel.Product;
import com.example.aprivate.html_parsel.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Product> mProducts;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test();

        startRecyclerView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, StartActivity.class);
                startActivity(i);
            }
        });
    }

    void startRecyclerView(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        RecyclerViewFragment recyclerViewFragment = new RecyclerViewFragment();
        ft.add(R.id.recycler_view_container, recyclerViewFragment);
        ft.commit();
    }

    private List<Product> test(){
        mProducts = new ArrayList<>();
        for (int i=0;i<30;i++){
            Product p = new Product();
            p.setProductName("test" + i);
            mProducts.add(p);
        }

        return mProducts;
    }

}
