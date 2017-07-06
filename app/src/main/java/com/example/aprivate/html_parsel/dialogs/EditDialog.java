package com.example.aprivate.html_parsel.dialogs;


import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aprivate.html_parsel.SearchProduct;
import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.data.BaseUserProductHelperUserProduct;
import com.example.aprivate.html_parsel.interfaces.EditDialogInterface;

public class EditDialog extends DialogFragment implements View.OnClickListener{

    private final static String TAG = "EditDialog";
    private EditText edtProduct;
    private EditText mSetLowePrice;
    private EditText mSetHighPrice;
    private EditDialogInterface editDialogInterface;
    private Button mButtonSearch;
    private Button mButtonCancel;
    private TextView mTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_activity, container, false);
        mTitle = (TextView)v.findViewById(R.id.text_dialog_title);
        edtProduct = (EditText) v.findViewById(R.id.search_product);
        mSetLowePrice = (EditText)v.findViewById(R.id.search_product_pice);
        mButtonSearch = (Button)v.findViewById(R.id.btn_search);
        mButtonSearch.setOnClickListener(this);
        mButtonCancel = (Button)v.findViewById(R.id.btn_cancel);
        mButtonSearch.setOnClickListener(this);


        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        editDialogInterface = (EditDialogInterface) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        editDialogInterface = null;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "OnClick: " + v.getId());
        switch (v.getId()) {
            case R.id.btn_cancel:

                break;
            case R.id.btn_search:
                if (TextUtils.isEmpty(edtProduct.getText())) return;
                SearchProduct searchProduct = new SearchProduct();
                searchProduct.setProductName(edtProduct.getText().toString());
                searchProduct.setPrice(mSetLowePrice.getText().toString());
                searchProduct.setNeedSearch(false);
                BaseUserProductHelperUserProduct baseHelperUserProduct =
                        new BaseUserProductHelperUserProduct(getActivity());
                baseHelperUserProduct.createProduct(searchProduct);
                editDialogInterface.onChanged();
                break;
        }
    }


}

