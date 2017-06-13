package com.example.aprivate.html_parsel;


import java.util.UUID;

public class Product {
    private String mProductName;
    private String mProductId;
    private String mNeedSearch; //Todo convert to Boolean

    public Product(){
        mProductId = UUID.randomUUID().toString();
    }

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String mProductName) {
        this.mProductName = mProductName;
    }

    public String getProductId() {
        return mProductId;
    }

    public String getNeedSearch() {
        return mNeedSearch;
    }

    public void setNeedSearch(String mNeedSearch) {
        this.mNeedSearch = mNeedSearch;
    }

    public void setProductId(String mProductId) {
        this.mProductId = mProductId;
    }
}
