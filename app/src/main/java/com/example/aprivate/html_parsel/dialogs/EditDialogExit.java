package com.example.aprivate.html_parsel.dialogs;


import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.interfaces.EditDialogInterface;

public class EditDialogExit extends DialogFragment
        implements View.OnClickListener{
    protected TextView mTxtExit;
    protected Button mBtnOk;
    protected Button mBtnCancel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_activity, container, false);
        mTxtExit = (TextView)v.findViewById(R.id.txt_dialog_title);
        mTxtExit.setText(R.string.str_exit_title);
        mBtnOk = (Button)v.findViewById(R.id.btn_dialog_save_action);
        mBtnOk.setOnClickListener(this);
        mBtnOk.setText(R.string.str_yes);
        mBtnCancel = (Button)v.findViewById(R.id.btn_dialog_cancel_action);
        mBtnCancel.setOnClickListener(this);
        mBtnCancel.setText(R.string.str_no);
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
            case R.id.btn_dialog_cancel_action:
                dismiss();
                break;
            case R.id.btn_dialog_save_action:
                getActivity().finish();
                break;
        }
    }
}
