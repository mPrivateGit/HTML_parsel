package com.example.aprivate.html_parsel.services;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.aprivate.html_parsel.network.RequestCreatorAsynctask;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

/** Сервис создается в главном потоке */
public class SearchService extends Service {
    private static final String PRODUCT_USER_ID = "selected_product_id";
    protected SQLiteDatabase mSQL;
    protected SearchProduct mSearchingProduct;
    protected RequestCreatorAsynctask request;
    protected String iProductId;

    RequestCreator requestCreator;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogApp.Log("SearchService", "Старт работы сервиса...");

        Thread service = Thread.currentThread();
        service.setName("MAIN IN SERVICE - ");
        LogApp.Log("Главный поток сервиса: ", service.getName() + service.toString());

        if(intent.hasExtra(PRODUCT_USER_ID)) {
            iProductId = intent.getStringExtra(PRODUCT_USER_ID);
            Log.d("РАБОТАЕТ СЕРВИС: ", "Айди объекта который пришел в сервис = "
                    + iProductId);
            BaseHelperUserProduct baseHelperUserProduct = new
                    BaseHelperUserProduct(getApplicationContext());
            mSearchingProduct = baseHelperUserProduct
                    .getProductById(iProductId);
        } else Log.d("EPIC ", "FAIL");

        requestCreator = new RequestCreator(this, mSearchingProduct.getProductId());
        requestCreator.start();
        if(requestCreator.isAlive())	//
        {
            try{
                requestCreator.join();	//Подождать побочный поток
            }catch(InterruptedException e){
                //
            }
        }

        SearchLogic s = new SearchLogic();
        s.searchResultForUser(this, mSearchingProduct);

        LogApp.Log("-------", "ok....?");
        LogApp.Log("WARNING!", "if you see this java has win");


        return super.onStartCommand(intent, flags, startId);
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

    public synchronized void getProducts(
            List<FoundProduct> mArr, SearchProduct searchProduct,
            Context context){
        BaseHelperFoundProducts base = new BaseHelperFoundProducts(context);
        mSQL = base.getWritableDatabase();


        LogApp.Log("SearchLogic (): ", String.valueOf(searchProduct.getLowPrice()));
        LogApp.Log("SearchLogic (): ", String.valueOf(searchProduct.getHighPrice()));
        LogApp.Log("SearchLogic (): ", String.valueOf(mArr.get(0).getPrice()) + " alooo");

        for (int i = 0; i <mArr.size() ; i++) {
            if (Integer.parseInt(mArr.get(i).getPrice())>searchProduct.getLowPrice()
                    & Integer.parseInt(mArr.get(i).getPrice()) < searchProduct.getHighPrice()){
                String finallyResultUrl = mArr.get(i).getUrl();
                String finallyResultPrice = mArr.get(i).getPrice();
                Log.d("---------", "----------------------------------------------------------");
                LogApp.Log("searchResultForUser",
                        "\n" +
                                finallyResultPrice +
                                "\n" +
                                finallyResultUrl);
                Log.d("---------", "----------------------------------------------------------");
            } else {
                base.deleteProduct(mArr.get(i));
                base.close();
            }
        }

//        base.deleteTable(mSQL);
//        base.onCreate(mSQL);
    }


    public void searchResultForUser(Context context, SearchProduct searchProduct){
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
        getProducts(mList, searchProduct, context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogApp.Log("SearchService: ", "SearchService is stopped");
    }
}
