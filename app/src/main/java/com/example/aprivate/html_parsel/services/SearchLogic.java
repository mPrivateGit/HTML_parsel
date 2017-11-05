package com.example.aprivate.html_parsel.services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.aprivate.html_parsel.SearchProduct;
import com.example.aprivate.html_parsel.data.BaseHelperFoundProducts;
import com.example.aprivate.html_parsel.data.BaseShema;
import com.example.aprivate.html_parsel.log.LogApp;
import com.example.aprivate.html_parsel.network.FoundProduct;

import java.util.ArrayList;
import java.util.List;



public class SearchLogic {
    private SQLiteDatabase mSQL;
    //private boolean mSearchVictory;

    public synchronized void getProducts(
            List<FoundProduct> mArr, SearchProduct searchProduct,
            Context context){
        BaseHelperFoundProducts base = new BaseHelperFoundProducts(context);
        mSQL = base.getWritableDatabase();


        LogApp.Log("SearchLogic (): ", String.valueOf(searchProduct.getLowPrice()));
        LogApp.Log("SearchLogic (): ", String.valueOf(searchProduct.getHighPrice()));
        LogApp.Log("SearchLogic (): ", String.valueOf(mArr.get(0).getPrice()) + "alooo");

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
}
