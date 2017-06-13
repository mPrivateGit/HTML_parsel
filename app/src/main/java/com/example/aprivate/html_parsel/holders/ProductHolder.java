package com.example.aprivate.html_parsel.holders;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.dialogs.EditDialog;

public class ProductHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
    private TextView mSearchText;
    private Boolean mBooleanSearch;
    private Activity mAct;

    public ProductHolder(View itemView, Activity activity) {
        super(itemView);
        mAct =activity;
        mSearchText = (TextView) itemView.findViewById(R.id.search_product);
        mSearchText.setOnClickListener(this);

        // mBooleanSearch = (Boolean) itemView.findViewById(R.id.checkbox_search_product);
    }

    public void bind(String editText, Boolean bool){
        mSearchText.setText(editText);
    }

    @Override
    public void onClick(View v) {
        Log.d("ssss>>>>>", "ssssssssssssss");

        EditDialog editDialog = new EditDialog();
        mAct.getFragmentManager()
                .beginTransaction()
                .show(editDialog)
                .commit();
    }
}
