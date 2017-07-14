package com.example.aprivate.html_parsel;




import com.example.aprivate.html_parsel.log.LogApp;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


public class SearchProduct {
    private String mProductId;
    private String mProductName;
    private String mLowPrice; //TODO convert to int !!!
    private String mHighPrice; //TODO convert to int !!!
    private String mCategory;
    private String mUnderCategory;
    private String mDateUserAdded; //TODO convert to long!!!
    private String mSearchSite;
    private String mDateAddedOnSite;
    private String mNeedSearch; //Todo convert to Boolean

    public SearchProduct(){
        mProductId = UUID.randomUUID().toString();
        Date date = new Date();
        mDateUserAdded = String.valueOf(date.getTime()); //TODO!!!!
        LogApp.Log("SearchProduct: ", mDateUserAdded);
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

    public String getLowPrice() {
        return mLowPrice;
    }

    public void setPrice(String mPrice) {
        this.mLowPrice = mPrice;
    }

    public void convertToBoolean(String str){
        if (str == "true"){
            setNeedSearch(true);
        } else setNeedSearch(false);
    }

    public void setLowPrice(String mLowPrice) {
        this.mLowPrice = mLowPrice;
    }

    public String getHighPrice() {
        return mHighPrice;
    }

    public void setHighPrice(String mHighPrice) {
        this.mHighPrice = mHighPrice;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getUnderCategory() {
        return mUnderCategory;
    }

    public void setUnderCategory(String mUnderCategory) {
        this.mUnderCategory = mUnderCategory;
    }

    public String getDateUserAdded() {
        return mDateUserAdded;
    }

    public void setDateUserAdded(String mDateUserAdded) {
        this.mDateUserAdded = mDateUserAdded;
    }

    public String getSearchSite() {
        return mSearchSite;
    }

    public void setSearchSite(String mSearchSite) {
        this.mSearchSite = mSearchSite;
    }

    public String getDateAddedOnSite() {
        return mDateAddedOnSite;
    }

    public void setDateAddedOnSite(String mDateAddedOnSite) {
        this.mDateAddedOnSite = mDateAddedOnSite;
    }

    @Override
    public String toString() {
        return "\n" + "SearchProduct{" +
                "mProductId='" + mProductId + "\n" +
                "mProductName='" + mProductName + "\n" +
                "mLowPrice='" + mLowPrice + "\n" +
                "mHighPrice='" + mHighPrice + "\n" +
                "mCategory='" + mCategory + "\n" +
                "mUnderCategory='" + mUnderCategory + "\n" +
                "mDateUserAdded='" + mDateUserAdded + "\n" +
                "mSearchSite='" + mSearchSite + "\n" +
                "mDateAddedOnSite='" + mDateAddedOnSite + "\n" +
                "mNeedSearch='" + mNeedSearch + "\n" +
                '}';
    }
}
