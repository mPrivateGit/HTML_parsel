package com.example.aprivate.html_parsel.holders;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.SearchProduct;
import com.example.aprivate.html_parsel.bin.SettingActivity;
import com.example.aprivate.html_parsel.data.BaseHelperUserProduct;
import com.example.aprivate.html_parsel.dialogs.EditDialogDeleteUserProduct;
import com.example.aprivate.html_parsel.dialogs.EditDialogStartOrStopSearch;
import com.example.aprivate.html_parsel.log.LogApp;

public class ProductHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
    private static final String EDIT_DIALOG_TAG = "ProductHolder";
    private static final String PRODUCT_USER_ID = "selected_product_id";
    private SearchProduct mSearchProduct;
    private TextView mTxtSearchProductName;
    private TextView mTxtDateUsersAdded;
    private TextView mTxtChosenWebSite;
    private ImageView mImgSetting;
    private ImageView mImgDelete;
    private ImageView mImgStartSearch;
    private ImageView mImgStopSearch;
    private Activity mAct;
    private int mNeedSearch;

    public ProductHolder(View itemView, Activity activity) {
        super(itemView);

        //контекст
        setContext(activity);

        //текстовые поля
        viewTextView();

        //изображения
        viewImageView();



    }

    public void bind(String searchProductId) {
        BaseHelperUserProduct baseHelperUserProduct = new BaseHelperUserProduct(mAct);
        SearchProduct searchProduct = baseHelperUserProduct
                .getProductById(searchProductId);

        Log.d(". . . . . . . . ", searchProduct.toString());

        //Собствено сам объект
        mSearchProduct = searchProduct;
        //Имя
        String name = searchProduct.getProductName();
        if (name.length() > 11) {
            char[] str = name.toCharArray();
            name = "";
            for (int i = 0; i < 9; i++) {
                name = name + String.valueOf(str[i]);
            }
            name = name + "...";
        }
        mTxtSearchProductName.setText(name);
        //Дата добавления пользователем
        String combined = "create: " + searchProduct.getDateUserAdded();
        mTxtDateUsersAdded.setText(combined);
        //Выбранный сайт для поиска
        String site = "search in: " + searchProduct.getSearchSite();
        mTxtChosenWebSite.setText(site);
        //
        mNeedSearch = searchProduct.getNeedSearch();
        LogApp.Log("<<<<<<<< - >>>>>>>>", String.valueOf(mNeedSearch));

        if (String.valueOf(mNeedSearch).equals("1")) {
            LogApp.Log("viewImageView ()", "In holder" + "\n" + "ЁЁЁЁ~~~~ штучка для " +
                    "смены картинки стоп или старт" + "\n");
            mImgStopSearch.setImageResource(R.drawable.ic_launcher_stop);
        } else if (String.valueOf(mNeedSearch).equals("0")) {
            mImgStartSearch.setImageResource(R.drawable.ic_launcher_start);
        }
    }

    private void setContext(Activity act){
        mAct = act;
    }

    private void viewTextView(){
        mTxtSearchProductName = (TextView) itemView.findViewById(R.id.search_product);
        mTxtSearchProductName.setOnClickListener(this);
        mTxtDateUsersAdded = (TextView) itemView.findViewById(R.id.txt_date_user_added);
        mTxtChosenWebSite = (TextView) itemView.findViewById(R.id.txt_chosen_web_site);
    }

    private void viewImageView() {
        mImgSetting = (ImageView) itemView.findViewById(R.id.img_search_product_setting);
        mImgSetting.setOnClickListener(this);
        mImgDelete = (ImageView) itemView.findViewById(R.id.img_search_product_delete);
        mImgDelete.setOnClickListener(this);

        mImgStartSearch = (ImageView) itemView.findViewById(R.id.img_start_search);
        mImgStartSearch.setOnClickListener(this);

        mImgStopSearch = (ImageView) itemView.findViewById(R.id.img_stop_search);
        mImgStopSearch.setOnClickListener(this);

        //LogApp.Log("ProductHolder: ", "viewImageView (): " + mSearchProduct.getNeedSearch());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_product:
//                Intent i = new Intent(v.getContext(), StartActivity.class);
//                mAct.startActivity(i);
                //TODO подумать нужно сдесь это вообще или нет
                break;
            case R.id.img_search_product_setting:
                Intent u = new Intent(v.getContext(), SettingActivity.class);
                u.putExtra(PRODUCT_USER_ID, mSearchProduct.getProductId());
                mAct.startActivity(u);
                break;
            case R.id.img_start_search:
                EditDialogStartOrStopSearch dialog_start_search = new EditDialogStartOrStopSearch();
                Bundle args_start_search = new Bundle();
                args_start_search.putSerializable(PRODUCT_USER_ID, mSearchProduct.getProductId());
                dialog_start_search.setArguments(args_start_search);
                dialog_start_search.show(mAct.getFragmentManager(), EDIT_DIALOG_TAG);
                //TODO start service
                break;
            case R.id.img_stop_search:
                EditDialogStartOrStopSearch dialog_stop_search = new EditDialogStartOrStopSearch();
                Bundle args_stop_search = new Bundle();
                args_stop_search.putSerializable(PRODUCT_USER_ID, mSearchProduct.getProductId());
                dialog_stop_search.setArguments(args_stop_search);
                dialog_stop_search.show(mAct.getFragmentManager(), EDIT_DIALOG_TAG);
//                //TODO stop service
                break;
            case R.id.img_search_product_delete:
                //delete from Db
                EditDialogDeleteUserProduct dialog_delete = new EditDialogDeleteUserProduct();
                Bundle args_delete = new Bundle();
                args_delete.putSerializable(PRODUCT_USER_ID, mSearchProduct.getProductId());
                dialog_delete.setArguments(args_delete);
                dialog_delete.show(mAct.getFragmentManager(), EDIT_DIALOG_TAG);
                break;
        }
    }
}
