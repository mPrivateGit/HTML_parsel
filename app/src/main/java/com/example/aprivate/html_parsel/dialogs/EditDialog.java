package com.example.aprivate.html_parsel.dialogs;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.aprivate.html_parsel.Product;
import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.adapters.ProductAdapter;
import com.example.aprivate.html_parsel.data.BaseHelper;
import com.example.aprivate.html_parsel.interfaces.EditDialogInterface;

public class EditDialog extends DialogFragment implements DialogInterface.OnClickListener{

    private final static String TAG = "EditDialog";
    private EditText edtProduct;
    private EditText mSetPice;
    private Button mButtonSearch;
    private EditDialogInterface editDialogInterface;


    @Nullable
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
//    AlertDialog.Builder abd = new AlertDialog.Builder(getActivity());
//                abd.setTitle("hi");

       // View v = inflater.inflate(R.layout.dialog_activity, null);
//
//        edtProduct =(EditText)v.findViewById(R.id.search_product);
//        mSetPice =(EditText)v.findViewById(R.id.search_product_pice);

        edtProduct = new EditText(getActivity());
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setTitle("Title!").setPositiveButton(R.string.yes, this)
                .setView(edtProduct)
                .setNegativeButton(R.string.no, this)
                .setMessage(R.string.message_text);
        return adb.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Log.d(TAG, "OnClick: " + which);
        switch (which) {
            case DialogInterface.BUTTON_NEGATIVE:
                break;
            case DialogInterface.BUTTON_POSITIVE:
                if (TextUtils.isEmpty(edtProduct.getText())) return;
                Product product = new Product();
                product.setProductName(edtProduct.getText().toString());
                BaseHelper baseHelper = new BaseHelper(getActivity());
                baseHelper.createProduct(product);
                editDialogInterface.onChanged();
        }
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
}

