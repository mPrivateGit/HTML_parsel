package com.example.aprivate.html_parsel.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.aprivate.html_parsel.SearchProduct;

public class BaseHelperUserProduct extends SQLiteOpenHelper
        implements IDataBaseUserProduct {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "products.db";
    private SQLiteDatabase mSQL;
    private Context mContext;

    public BaseHelperUserProduct(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String mSQL = "create table " + BaseShema.UserProductTable.TABLE_NAME + "(" +
                " _id integer primary key autoincrement, " +
                BaseShema.ColsUserProducts.UUID + ", " +
                BaseShema.ColsUserProducts.PRODUCT_NAME + ", " +
                BaseShema.ColsUserProducts.PRODUCT_LOW_PRICE + ", " +
                BaseShema.ColsUserProducts.PRODUCT_HIGH_PRICE + ", " +
                BaseShema.ColsUserProducts.PRODUCT_CATEGORY + ", " +
                BaseShema.ColsUserProducts.PRODUCT_UNDER_CATEGORY + ", " +
                BaseShema.ColsUserProducts.DATE_USERS_ADDED + ", " +
                BaseShema.ColsUserProducts.WEB_SITE + ", " +
                BaseShema.ColsUserProducts.DATE_ADDED_ON_SITE + ", " +
                BaseShema.ColsUserProducts.BOOLEAN_SEARCH +
                ")";
        db.execSQL(mSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // null
    }

    @Override
    public void createProduct(SearchProduct searchProduct) {
        mSQL = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BaseShema.ColsUserProducts.UUID,
                searchProduct.getProductId());
        contentValues.put(BaseShema.ColsUserProducts.PRODUCT_NAME,
                searchProduct.getProductName());
        contentValues.put(BaseShema.ColsUserProducts.PRODUCT_LOW_PRICE,
                searchProduct.getLowPrice());
        contentValues.put(BaseShema.ColsUserProducts.PRODUCT_HIGH_PRICE,
                searchProduct.getHighPrice());
        contentValues.put(BaseShema.ColsUserProducts.PRODUCT_CATEGORY,
                searchProduct.getCategory());
        contentValues.put(BaseShema.ColsUserProducts.PRODUCT_UNDER_CATEGORY,
                searchProduct.getUnderCategory());
        contentValues.put(BaseShema.ColsUserProducts.DATE_USERS_ADDED,
                searchProduct.getDateUserAdded());
        contentValues.put(BaseShema.ColsUserProducts.WEB_SITE,
                searchProduct.getSearchSite());
        contentValues.put(BaseShema.ColsUserProducts.DATE_ADDED_ON_SITE,
                searchProduct.getDateAddedOnSite());
        contentValues.put(BaseShema.ColsUserProducts.BOOLEAN_SEARCH,
                searchProduct.getNeedSearch());

        mSQL.insert(BaseShema.UserProductTable.TABLE_NAME, null, contentValues);
    }

    @Override
    public SearchProduct getProductByPosition(int position) {
        return null;
    }

    @Override
    public SearchProduct getProductById(String id) {
        BaseHelperUserProduct baseHelperUserProduct =
                new BaseHelperUserProduct(mContext);
        mSQL = baseHelperUserProduct.getReadableDatabase();
        return null;
        //TODO не доработан
    }

    @Override
    public void deleteProduct(SearchProduct searchProduct) {
        BaseHelperUserProduct baseHelperUserProduct =
                new BaseHelperUserProduct(mContext);
        mSQL = baseHelperUserProduct.getWritableDatabase();
        String target = BaseShema.ColsUserProducts.UUID +
                " =" +
                "'" +
                searchProduct.getProductId() +
                "'";

        mSQL.delete(BaseShema.UserProductTable.TABLE_NAME, target, null);
    }

    @Override
    public void deleteProductById(String id) {
        BaseHelperUserProduct baseHelperUserProduct =
                new BaseHelperUserProduct(mContext);
        mSQL = baseHelperUserProduct.getWritableDatabase();

        mSQL = baseHelperUserProduct.getWritableDatabase();
        String target = BaseShema.ColsUserProducts.UUID +
                " =" +
                "'" +
                id +
                "'";

        mSQL.delete(BaseShema.UserProductTable.TABLE_NAME, target, null);
    }

    @Override
    public void updateProduct(SearchProduct searchProduct) {
        BaseHelperUserProduct baseHelperUserProduct =
                new BaseHelperUserProduct(mContext);
        mSQL = baseHelperUserProduct.getWritableDatabase();
        String target = "";
        String command = target +"";

        mSQL.update(BaseShema.UserProductTable.TABLE_NAME, null, command, null);
        //TODO ??? что-то тут не чисто, нужно проверить правильность обновления
    }

    @Override
    public void updateProductById(String id) {
        BaseHelperUserProduct baseHelperUserProduct =
                new BaseHelperUserProduct(mContext);
        mSQL = baseHelperUserProduct.getWritableDatabase();

        String target = BaseShema.ColsUserProducts.UUID +
                " =" +
                "'" +
                id +
                "'";

        mSQL.update(BaseShema.UserProductTable.TABLE_NAME, null, target, null);
    }

    @Override
    public int size() {
        BaseHelperUserProduct baseHelperUserProduct =
                new BaseHelperUserProduct(mContext);
        mSQL = baseHelperUserProduct.getReadableDatabase();

        String projection [] = {BaseShema.ColsUserProducts.UUID};

        Cursor cursor = mSQL.query(BaseShema.UserProductTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        cursor.close();

        return cursor.getCount();
    }
}
