package com.example.aprivate.html_parsel.dialogs;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
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
import com.example.aprivate.html_parsel.data.WorkerDataBaseSearchProduct;
import com.example.aprivate.html_parsel.interfaces.EditDialogInterface;

public class EditDialogStartSearch extends DialogFragment
        implements View.OnClickListener{
    private static final String PRODUCT_USER_ID = "selected_product_id";
    protected Button mBtnOk;
    protected Button mBtnCancel;
    protected TextView mTxtTitle;
    protected String mSearchProductId;
    private EditDialogInterface editDialogInterface;
    private SearchProduct mSearchProduct;
    private int mNeedSearch;
    private WorkerDataBaseSearchProduct worker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_activity, container, false);
        mBtnOk = (Button) v.findViewById(R.id.btn_dialog_save_action);
        mBtnOk.setOnClickListener(this);
        mBtnOk.setText(R.string.str_start_search);
        mBtnCancel = (Button) v.findViewById(R.id.btn_dialog_cancel_action);
        mBtnCancel.setOnClickListener(this);
        mBtnCancel.setText(R.string.btn_cancel);
        mTxtTitle = (TextView) v.findViewById(R.id.txt_dialog_title);
        mTxtTitle.setText(R.string.str_are_you_sure);
        mSearchProductId = getArguments().getString(PRODUCT_USER_ID);
        editDialogInterface = (EditDialogInterface) getActivity();

        //Поиск объекта
        mSearchProductId = getArguments().toString();

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
        worker = new WorkerDataBaseSearchProduct(
                getActivity(), mSearchProductId);
        mNeedSearch = worker.readObjectFromDb().getNeedSearch();

        switch (v.getId()){
            case R.id.btn_dialog_cancel_action:
                dismiss();
                break;
            case R.id.btn_dialog_save_action:
                if (mNeedSearch == 1){
                    mNeedSearch = 0;
                    mSearchProduct.setNeedSearch(mNeedSearch);
                    mSearchProduct = worker.readObjectFromDb();
                    worker = new WorkerDataBaseSearchProduct(getActivity(),
                            mSearchProduct.getProductId(), mSearchProduct.getProductName(),
                            mSearchProduct.getLowPrice(), mSearchProduct.getHighPrice(),
                            mSearchProduct.getCategory(), mSearchProduct.getUnderCategory(),
                            mSearchProduct.getSearchSite(), mSearchProduct.getDateAddedOnSite(),
                            mSearchProduct.getDateUserAdded(), mSearchProduct.getNeedSearch());
                    worker.writeObjectInDb();
                } else {
                    mNeedSearch = 1;
                    mSearchProduct = worker.readObjectFromDb();
                    mSearchProduct.setNeedSearch(mNeedSearch);
                    mSearchProduct = worker.readObjectFromDb();
                    worker = new WorkerDataBaseSearchProduct(getActivity(),
                            mSearchProduct.getProductId(), mSearchProduct.getProductName(),
                            mSearchProduct.getLowPrice(), mSearchProduct.getHighPrice(),
                            mSearchProduct.getCategory(), mSearchProduct.getUnderCategory(),
                            mSearchProduct.getSearchSite(), mSearchProduct.getDateAddedOnSite(),
                            mSearchProduct.getDateUserAdded(), mSearchProduct.getNeedSearch());
                    worker.writeObjectInDb();
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
