package com.example.aprivate.html_parsel.holders;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.aprivate.html_parsel.R;

public class ProductHolder extends RecyclerView.ViewHolder {

    private TextView mSearchText;
    private Boolean mBooleanSearch;

    public ProductHolder(View itemView, Context context) {
        super(itemView);
        mSearchText = (TextView) itemView.findViewById(R.id.edit_search_product);
       // mBooleanSearch = (Boolean) itemView.findViewById(R.id.checkbox_search_product);
    }

    public void bind(String editText, Boolean bool){
        mSearchText.setText(editText);
    }
}
