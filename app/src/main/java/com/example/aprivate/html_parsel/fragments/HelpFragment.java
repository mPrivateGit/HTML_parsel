package com.example.aprivate.html_parsel.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.adapters.ProductAdapter;

public class HelpFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TextView mTxtHelp; //TODO in RecyclerView if it need

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_help, container, false);
        mTxtHelp = (TextView)v.findViewById(R.id.txt_fragment_help);
        mTxtHelp.setText(R.string.str_help_full_text);
        return v;
    }

    private void destroyFragment(){

    }

}
