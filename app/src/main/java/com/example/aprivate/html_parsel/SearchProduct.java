package com.example.aprivate.html_parsel;




import com.example.aprivate.html_parsel.log.LogApp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


public class SearchProduct {
    private String mProductId;
    private String mProductName;
    private String mCategory;
    private String mUnderCategory;
    private String mDateUserAdded;
    private String mSearchSite;
    private String mDateAddedOnSite;
    private int mNeedSearch;
    private int mLowPrice;
    private int mHighPrice;

    public SearchProduct(){
        mProductId = UUID.randomUUID().toString();
        Date date = Calendar.getInstance().getTime();
        //
        // Display a date in day, month, year format
        //
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        mDateUserAdded = formatter.format(date);
        mNeedSearch = 0;
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

    public void setProductId(String mProductId) {
        this.mProductId = mProductId;
    }

    public int getLowPrice() {
        return mLowPrice;
    }

    public void setPrice(int mPrice) {
        this.mLowPrice = mPrice;
    }

    public void setLowPrice(int mLowPrice) {
        this.mLowPrice = mLowPrice;
    }

    public int getHighPrice() {
        return mHighPrice;
    }

    public void setHighPrice(int mHighPrice) {
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

    public void setDateUserCreate(String mDateUserAdded) {
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

    public int getNeedSearch() {
        return mNeedSearch;
    }

    public void setNeedSearch(int mNeedSearch) {
        this.mNeedSearch = mNeedSearch;
    }

    @Override
    public String toString() {
        return  "SearchProduct{" + "\n" +
                "mProductId=  " + mProductId + "\n" +
                "mProductName = " + mProductName + "\n" +
                "mLowPrice = " + mLowPrice + "\n" +
                "mHighPrice = " + mHighPrice + "\n" +
                "mCategory = " + mCategory + "\n" +
                "mUnderCategory = " + mUnderCategory + "\n" +
                "mDateUserAdded = " + mDateUserAdded + "\n" +
                "mSearchSite = " + mSearchSite + "\n" +
                "mDateAddedOnSite = " + mDateAddedOnSite + "\n" +
                "mNeedSearch = " + mNeedSearch + "\n" +
                '}';
    }
}
