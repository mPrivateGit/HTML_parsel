package com.example.aprivate.html_parsel.data;

import android.content.Context;

import com.example.aprivate.html_parsel.SearchProduct;
import com.example.aprivate.html_parsel.log.LogApp;


public class WorkerDataBaseSearchProduct {
    private Context mContext;
    private SearchProduct searchProduct;

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
        searchProduct.setDateUserAdded("data"); //TODO!!!
        searchProduct.setSearchSite(siteSearch);
        searchProduct.setDateAddedOnSite(addedOnSite);
        searchProduct.setNeedSearch(false);

        LogApp.Log(">>>>>>", searchProduct.toString());
    }

    public void writeObjectInDb(){
        BaseUserProductHelperUserProduct baseHelperUserProduct =
                        new BaseUserProductHelperUserProduct(mContext);
        baseHelperUserProduct.createProduct(searchProduct);
    }

    public void readObjectFromDb(){

    }
}
