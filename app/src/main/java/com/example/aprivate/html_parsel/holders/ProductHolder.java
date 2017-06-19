package com.example.aprivate.html_parsel.holders;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.bin.StartActivity;
import com.example.aprivate.html_parsel.log.LogApp;
import com.example.aprivate.html_parsel.network.RequestCreator;

public class ProductHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private TextView mSearchText;
    private CheckBox mCheckBoxSearch;
    private Activity mAct;
    private Boolean mBoolean;

    public ProductHolder(View itemView, Activity activity) {
        super(itemView);
        mAct = activity;
        mSearchText = (TextView) itemView.findViewById(R.id.search_product);
        mSearchText.setOnClickListener(this);
        mCheckBoxSearch = (CheckBox) itemView.findViewById(R.id.checkbox_search_product);
        mCheckBoxSearch.setOnCheckedChangeListener(this);
    }

    public void bind(String editText, Boolean bool){
        mSearchText.setText(editText);
        mCheckBoxSearch.setChecked(bool);
    }

    @Override
    public void onClick(View v) {
        RequestCreator r = new RequestCreator(null);
        r.execute();
        Intent i = new Intent(v.getContext(), StartActivity.class);
        mAct.startActivity(i);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (mCheckBoxSearch.isChecked()){
            LogApp.Log(ProductHolder.class.getCanonicalName(), "mCheckBoxSearch" + mCheckBoxSearch);
            mBoolean = true;
        } else {
            LogApp.Log(ProductHolder.class.getCanonicalName(), "Oops!!!");
            mBoolean = false;
        }
    }
}
