package com.example.aprivate.html_parsel.dialogs;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.data.BaseUserProductHelperUserProduct;
import com.example.aprivate.html_parsel.interfaces.EditDialogInterface;
import com.example.aprivate.html_parsel.log.LogApp;

public class EditDialogDeleteUserProduct extends DialogFragment
        implements View.OnClickListener{
    private static final String PRODUCT_USER_ID = "selected_product_id";
    private Button mBtnOk;
    private Button mBtnCancel;
    private TextView mTxtTitle;
    private String mSearchProductId;
    private EditDialogInterface editDialogInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_activity, container, false);
        mBtnOk = (Button) v.findViewById(R.id.btn_save_action);
        mBtnOk.setOnClickListener(this);
        mBtnOk.setText(R.string.str_delete);
        mBtnCancel = (Button) v.findViewById(R.id.btn_cancel_action);
        mBtnCancel.setOnClickListener(this);
        mBtnCancel.setText(R.string.btn_cancel);
        mTxtTitle = (TextView) v.findViewById(R.id.txt_title);
        mTxtTitle.setText(R.string.str_are_you_sure);


        mSearchProductId = getArguments().getString(PRODUCT_USER_ID);
        return v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
        return dialog;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel_action:
                dismiss();
                break;
            case R.id.btn_save_action:
                BaseUserProductHelperUserProduct baseHelper =
                        new BaseUserProductHelperUserProduct(getActivity());
                baseHelper.deleteProductById(mSearchProductId);
                editDialogInterface.onChanged();
                dismiss();
                break;
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
