package com.example.aprivate.html_parsel;




import java.util.UUID;


public class SearchProduct {
    private String mProductName;
    private String mProductId;
    private String mLowPrice;
    private String mHighPrice;
    private String mCategory;
    private String mUnderCategory;
    private String mDateUserAdded;
    private String mSearchSite;
    private String mDateAddedOnSite;
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

    public String getmLowPrice() {
        return mLowPrice;
    }

    public void setmLowPrice(String mLowPrice) {
        this.mLowPrice = mLowPrice;
    }

    public String getmHighPrice() {
        return mHighPrice;
    }

    public void setmHighPrice(String mHighPrice) {
        this.mHighPrice = mHighPrice;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getmUnderCategory() {
        return mUnderCategory;
    }

    public void setmUnderCategory(String mUnderCategory) {
        this.mUnderCategory = mUnderCategory;
    }

    public String getmDateUserAdded() {
        return mDateUserAdded;
    }

    public void setmDateUserAdded(String mDateUserAdded) {
        this.mDateUserAdded = mDateUserAdded;
    }

    public String getmSearchSite() {
        return mSearchSite;
    }

    public void setmSearchSite(String mSearchSite) {
        this.mSearchSite = mSearchSite;
    }

    public String getmDateAddedOnSite() {
        return mDateAddedOnSite;
    }

    public void setmDateAddedOnSite(String mDateAddedOnSite) {
        this.mDateAddedOnSite = mDateAddedOnSite;
    }
}
