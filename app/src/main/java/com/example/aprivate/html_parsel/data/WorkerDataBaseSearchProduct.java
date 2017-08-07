package com.example.aprivate.html_parsel.data;

import android.content.Context;
import android.text.TextUtils;

import com.example.aprivate.html_parsel.SearchProduct;
import com.example.aprivate.html_parsel.log.LogApp;


public class WorkerDataBaseSearchProduct {
    private Context mContext;
    private SearchProduct searchProduct;
    private BaseHelperUserProduct baseHelperUserProduct;
    private String mSearchProductId;

    public WorkerDataBaseSearchProduct(Context context, String productName,
                                       int lowPrice, int highPrice,
                                       String category, String underCategory,
                                       String siteSearch, String addedOnSite) {
        mContext = context;
        searchProduct = new SearchProduct();
        searchProduct.setProductName(productName);
        searchProduct.setLowPrice(String.valueOf(lowPrice)); //TODO !!!
        searchProduct.setHighPrice(String.valueOf(highPrice)); //TODO!!!
        searchProduct.setCategory(category);
        searchProduct.setUnderCategory(underCategory);
        searchProduct.setSearchSite(siteSearch);
        searchProduct.setDateAddedOnSite(addedOnSite);
        searchProduct.setNeedSearch(false);

        LogApp.Log(">>>>>>", searchProduct.toString());
    }

    public WorkerDataBaseSearchProduct(Context context, String productId){
        mContext = context;
        mSearchProductId = productId;
    }

    public void writeObjectInDb(){
        baseHelperUserProduct =
                        new BaseHelperUserProduct(mContext);
        baseHelperUserProduct.createProduct(searchProduct);
    }

    public SearchProduct readObjectFromDb() {
        baseHelperUserProduct = new BaseHelperUserProduct(mContext);
//        LogApp.Log("This! >>>>>", searchProduct.toString());
        if (TextUtils.isEmpty(baseHelperUserProduct
                .getProductById(mSearchProductId)
                .getProductName())) {
            LogApp.Log("WORKER_DB_READ>>>: ", "object = null");
            return new SearchProduct();
        } else {
            LogApp.Log("WORKER_DB_READ>>>: ", "object = try");
            return baseHelperUserProduct.getProductById(mSearchProductId);
        }
    }
}
