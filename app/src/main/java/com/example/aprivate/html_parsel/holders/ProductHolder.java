package com.example.aprivate.html_parsel.holders;


import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.bin.SettingActivity;
import com.example.aprivate.html_parsel.bin.StartActivity;

public class ProductHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
    private TextView mTxtSearchProductName;
    private TextView mTxtDateUsersAdded;
    private TextView mTxtChosenWebSite;
    private ImageView mImgSetting;
    private ImageView mImgDelete;
    private ImageView mImgStartSearch;
    private ImageView mImgStopSearch;
    private Activity mAct;

    public ProductHolder(View itemView, Activity activity) {
        super(itemView);

        //контекст
        setContext(activity);

        //текстовые поля
        viewTextView();

        //изображения
        viewImageView();
    }

    public void bind(String productName, String dateUserAdded, String webSite){
        //Имя
        mTxtSearchProductName.setText(productName);
        //Дата добавления пользователем
        String combined = R.string.str_user_added + dateUserAdded;
        mTxtDateUsersAdded.setText(combined);
        //Выбранный сайт для поиска
        mTxtChosenWebSite.setText(webSite);
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
        mImgStopSearch.setVisibility(View.GONE);
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
                mAct.startActivity(u);
                break;
            case R.id.img_start_search:
                mImgStartSearch.setVisibility(View.GONE);
                mImgStopSearch.setVisibility(View.VISIBLE);
                //TODO start service
                break;
            case R.id.img_stop_search:
                mImgStopSearch.setVisibility(View.GONE);
                mImgStartSearch.setVisibility(View.VISIBLE);
                //TODO stop service
                break;
            case R.id.img_search_product_delete:
                //delete from Db

                break;
        }
    }
}
