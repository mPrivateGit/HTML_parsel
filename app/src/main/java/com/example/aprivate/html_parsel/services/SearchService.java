package com.example.aprivate.html_parsel.services;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.aprivate.html_parsel.data.BaseHelperFoundProducts;
import com.example.aprivate.html_parsel.data.BaseShema;
import com.example.aprivate.html_parsel.network.FoundProduct;
import com.example.aprivate.html_parsel.network.RequestCreator;

import java.util.ArrayList;
import java.util.List;

public class SearchService extends Service {
    protected SQLiteDatabase mSQL;
    protected String mSearchingProduct = "mackbook+pro";
    protected int mLowPrice = 100;
    protected int mHighPrice = 200;
    //TODO Передавай от пользователя данные

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //if (TextUtils.isEmpty(mLowPrice)) {
            RequestCreator request = new RequestCreator(getApplicationContext(),
                    mSearchingProduct, mLowPrice, mHighPrice);
            request.execute();
       // }


        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        viewToLogResults(getProducts(getApplicationContext())); //TODO Застопить пока не выполнен парсинг
        return super.onStartCommand(intent, flags, startId);
    }

    private List<FoundProduct> getProducts(Context context){
        List<FoundProduct> mList = new ArrayList<>();
        BaseHelperFoundProducts baseHelperFoundProducts = new BaseHelperFoundProducts(context);

        //чтение
        mSQL = baseHelperFoundProducts.getReadableDatabase();

        String projection [] = {
                BaseShema.ColsFoundsProduct.UUID,
                BaseShema.ColsFoundsProduct.PRODUCT_NAME,
                BaseShema.ColsFoundsProduct.PRODUCT_PRICE,
                BaseShema.ColsFoundsProduct.URL,
        };
        Cursor cursor = mSQL.query(BaseShema.FoundsProductTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        try {
            int targetUUID = cursor.getColumnIndex(BaseShema.ColsFoundsProduct.UUID);
            int targetName = cursor.getColumnIndex(BaseShema.ColsFoundsProduct.PRODUCT_NAME);
            int targetPrice = cursor.getColumnIndex(BaseShema.ColsFoundsProduct.PRODUCT_PRICE);
            int targetUrl = cursor.getColumnIndex(BaseShema.ColsFoundsProduct.URL);

            while (cursor.moveToNext()) {
                String uuid = cursor.getString(targetUUID);
                String name = cursor.getString(targetName);
                String price = cursor.getString(targetPrice);
                String url = cursor.getString(targetUrl);

                FoundProduct foundProduct = new FoundProduct();
                foundProduct.setProductId(uuid);
                foundProduct.setProductName(name);
                foundProduct.setPrice(price);
                foundProduct.setUrl(url);

                mList.add(foundProduct);
            }
        } finally {
            cursor.close();
        }
        return mList;
    }

    private void viewToLogResults(List<FoundProduct> mArr){
        for (int i = 0; i <mArr.size() ; i++) {
            System.out.println(mArr.get(i).getPrice()
                    + " "
                    + mArr.get(i).getProduct()
                    + "\n"
                    + mArr.get(i).getUrl());
        }
        System.out.println(mArr.size());
    }

    private void searchResultForUser(){
        //TODO
    }
}
