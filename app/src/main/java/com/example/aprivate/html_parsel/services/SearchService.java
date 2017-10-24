package com.example.aprivate.html_parsel.services;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.aprivate.html_parsel.SearchProduct;
import com.example.aprivate.html_parsel.data.BaseHelperFoundProducts;
import com.example.aprivate.html_parsel.data.BaseHelperUserProduct;
import com.example.aprivate.html_parsel.data.BaseShema;
import com.example.aprivate.html_parsel.log.LogApp;
import com.example.aprivate.html_parsel.network.FoundProduct;
import com.example.aprivate.html_parsel.network.RequestCreator;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/** Сервис создается в главном потоке */
public class SearchService extends Service {
    private static final String PRODUCT_USER_ID = "selected_product_id";
    protected SQLiteDatabase mSQL;
    protected SearchProduct mSearchingProduct;
    protected RequestCreator request;
    protected String iProductId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogApp.Log("SearchService", "Старт работы сервиса...");
        if(intent.hasExtra(PRODUCT_USER_ID)) {
            iProductId = intent.getStringExtra(PRODUCT_USER_ID);
            Log.d("РАБОТАЕТ СЕРВИС: ", "Айди объекта который пришел в сервис = "
                    + iProductId);
            BaseHelperUserProduct baseHelperUserProduct = new
                    BaseHelperUserProduct(getApplicationContext());
            mSearchingProduct = baseHelperUserProduct
                    .getProductById(iProductId);
        } else Log.d("EPIC ", "FAIL");



        //if (TextUtils.isEmpty(mLowPrice)) {

        request = new RequestCreator(getApplicationContext(),
                mSearchingProduct.getProductId());
        request.execute();


        //viewToLogResults(getProducts(getApplicationContext())); //TODO Застопить пока не выполнен парсинг
//        if (request.getStatus() == RequestCreator.Status.FINISHED){


        return super.onStartCommand(intent, flags, startId);
    }

    public List<FoundProduct> getProducts(Context context){
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

    public void viewToLogResults(List<FoundProduct> mArr){
        for (int i = 0; i <mArr.size() ; i++) {
            Log.d("---------", "----------------------------------------------------------");
            System.out.println(mArr.get(i).getPrice()
                    + "\n"
                    + mArr.get(i).getProduct()
                    + "\n"
                    + mArr.get(i).getUrl());
            Log.d("---------", "----------------------------------------------------------");
        }
        System.out.println("найдено объектов: " + mArr.size());
    }

    public void searchResultForUser(List<FoundProduct> mArr){
        for (int i = 0; i <mArr.size() ; i++) {
            if (Integer.parseInt(mArr.get(i).getPrice())>mSearchingProduct.getLowPrice()
                    & Integer.parseInt(mArr.get(i).getPrice()) < mSearchingProduct.getHighPrice()){
                String finallyResult = mArr.get(i).getUrl();
                Log.d("---------", "----------------------------------------------------------");
                LogApp.Log("searchResultForUser", "************" +
                        "\n" +
                        "***********" +
                        finallyResult +
                        "\n" +
                        "***********");
                BaseHelperFoundProducts base = new BaseHelperFoundProducts(getApplicationContext());
                base.deleteTable(mSQL);
                Log.d("---------", "----------------------------------------------------------");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogApp.Log("SearchService: ", "SearchService is stopped");
    }
}
