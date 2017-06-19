package com.example.aprivate.html_parsel;


import java.util.UUID;

public class SearchProduct {
    private String mProductName;
    private String mProductId;
    private String mPrice;
    private String mNeedSearch; //Todo convert to Boolean

    public SearchProduct(){
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

    public Boolean getNeedSearch() {
        if (mNeedSearch == "true")return true;
        else return false;
    }

    public void setNeedSearch(Boolean mNeedSearch) {
        if (mNeedSearch == true){
            this.mNeedSearch = "true";
        } else {
            this.mNeedSearch = "false";
        }
    }

    public void setProductId(String mProductId) {
        this.mProductId = mProductId;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public void convertToBoolean(String str){
        if (str == "true"){
            setNeedSearch(true);
        } else setNeedSearch(false);
    }
}
