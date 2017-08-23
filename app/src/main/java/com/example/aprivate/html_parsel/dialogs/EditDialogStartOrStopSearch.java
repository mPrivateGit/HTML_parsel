package com.example.aprivate.html_parsel.dialogs;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.SearchProduct;
import com.example.aprivate.html_parsel.data.BaseHelperUserProduct;
import com.example.aprivate.html_parsel.data.WorkerDataBaseSearchProduct;
import com.example.aprivate.html_parsel.interfaces.EditDialogInterface;
import com.example.aprivate.html_parsel.services.SearchService;

public class EditDialogStartOrStopSearch extends DialogFragment
        implements View.OnClickListener{
    private static final String PRODUCT_USER_ID = "selected_product_id";
    protected WorkerDataBaseSearchProduct worker;
    protected BaseHelperUserProduct baseHelperUserProduct;
    protected Button mBtnOk;
    protected Button mBtnCancel;
    protected TextView mTxtTitle;
    protected String mSearchProductId;
    private EditDialogInterface editDialogInterface;
    private int mNeedSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_activity, container, false);

        mSearchProductId = getArguments().getString(PRODUCT_USER_ID);
        editDialogInterface = (EditDialogInterface) getActivity();
        worker = new WorkerDataBaseSearchProduct(
                getActivity(), mSearchProductId);
        mNeedSearch = worker.readObjectFromDb().getNeedSearch();

        mTxtTitle = (TextView) v.findViewById(R.id.txt_dialog_title);
        mBtnOk = (Button) v.findViewById(R.id.btn_dialog_save_action);
        mBtnOk.setOnClickListener(this);
        mBtnCancel = (Button) v.findViewById(R.id.btn_dialog_cancel_action);
        mBtnCancel.setOnClickListener(this);

        if (mNeedSearch == 0) {
            mTxtTitle.setText(R.string.str_are_you_sure_start);
            mBtnOk.setText(R.string.str_start_search);
            mBtnCancel.setText(R.string.btn_cancel);
        } else {
            mTxtTitle.setText(R.string.str_are_you_sure_stop);
            mBtnOk.setText(R.string.stop);
            mBtnCancel.setText(R.string.btn_cancel);
        }

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
                editDialogInterface.onChanged();
                dismiss();
                break;
            case R.id.btn_dialog_save_action:
                if (mNeedSearch == 1){
                    mNeedSearch = 0;
                    baseHelperUserProduct = new BaseHelperUserProduct(getActivity());
                    SearchProduct searchProduct = baseHelperUserProduct
                            .getProductById(mSearchProductId);
                    searchProduct.setNeedSearch(mNeedSearch);
                    baseHelperUserProduct.createProduct(searchProduct);
                    //todo Stop Service
                } else if (mNeedSearch == 0){
                    mNeedSearch = 1;
                    baseHelperUserProduct = new BaseHelperUserProduct(getActivity());
                    SearchProduct searchProduct = baseHelperUserProduct
                            .getProductById(mSearchProductId);
                    searchProduct.setNeedSearch(mNeedSearch);
                    baseHelperUserProduct.createProduct(searchProduct);

                    getActivity().startService(new Intent(getActivity(), SearchService.class));

                    //todo Start Service
                }
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
