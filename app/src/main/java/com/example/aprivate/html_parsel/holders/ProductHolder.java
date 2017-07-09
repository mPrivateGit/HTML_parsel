package com.example.aprivate.html_parsel.holders;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.bin.SettingActivity;
import com.example.aprivate.html_parsel.bin.StartActivity;
import com.example.aprivate.html_parsel.log.LogApp;
import com.example.aprivate.html_parsel.network.RequestCreator;
import com.example.aprivate.html_parsel.services.SearchService;

public class ProductHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
    private TextView mSearchText;
    private Activity mAct;
    private ImageView mSettingImage;

    public ProductHolder(View itemView, Activity activity) {
        super(itemView);
        mAct = activity;
        mSearchText = (TextView) itemView.findViewById(R.id.search_product);
        mSearchText.setOnClickListener(this);
        mSettingImage = (ImageView) itemView.findViewById(R.id.img_search_product);
        mSettingImage.setOnClickListener(this);
    }

    public void bind(String editText){
        mSearchText.setText(editText);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_product:
                Intent i = new Intent(v.getContext(), StartActivity.class);
                mAct.startActivity(i);
                break;
            case R.id.img_search_product:
                Intent u = new Intent(v.getContext(), SettingActivity.class);
                mAct.startActivity(u);
        }
    }
}
