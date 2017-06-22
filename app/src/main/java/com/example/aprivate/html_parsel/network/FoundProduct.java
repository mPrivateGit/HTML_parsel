package com.example.aprivate.html_parsel.network;


import java.net.URI;
import java.net.URL;
import java.util.UUID;

public class FoundProduct {
    private String mName;
    private String mPrice;
    private URL mProductUrl;
    private String mProductId;

    private FoundProduct(){
        mProductId = UUID.randomUUID().toString();
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public URL getProductUrl() {
        return mProductUrl;
    }

    public void setProductUrl(URL mProductUrl) {
        this.mProductUrl = mProductUrl;
    }

    public String getPoductId() {
        return mProductId;
    }

    public void setPoductId(String mProductId) {
        this.mProductId = mProductId;
    }
}
