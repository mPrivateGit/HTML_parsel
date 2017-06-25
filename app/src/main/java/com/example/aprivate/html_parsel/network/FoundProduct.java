package com.example.aprivate.html_parsel.network;


import java.net.URI;
import java.net.URL;
import java.util.UUID;

public class FoundProduct {
    private String mProductName;
    private String mPrice;
    private String mUrl;
    private String mId;

    public FoundProduct(){
        mId = UUID.randomUUID().toString();
    }

    public String getProduct() {
        return mProductName;
    }

    public void setProductName(String mProduct) {
        this.mProductName = mProduct;
    }

    public String getmId() {
        return mId;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
