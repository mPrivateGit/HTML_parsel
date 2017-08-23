package com.example.aprivate.html_parsel.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.example.aprivate.html_parsel.static_values.StaticImport;
import com.example.aprivate.html_parsel.SearchProduct;
import com.example.aprivate.html_parsel.log.LogApp;

import java.util.ArrayList;

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

        if (!TextUtils.isEmpty(searchProduct.getProductId())) {
            deleteProductById(searchProduct.getProductId());
            LogApp.Log("createProduct: ",  "Дубль! Совападение айди," +
                    "старый объект удален");
        }
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

        LogApp.Log("createProduct()", "\n" +
                    "\n" + "______________________" +
                    searchProduct.getNeedSearch() +
                    "______________________" + "\n" +
                    "\n");

        mSQL.insert(BaseShema.UserProductTable.TABLE_NAME, null, contentValues);
        LogApp.Log("createProduct()", "new product has been create!");
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

        LogApp.Log(">getProductById: ", "айди объекта который пришел = " + id);

        ArrayList<SearchProduct> mProductList = new ArrayList<>();
        SearchProduct result = new SearchProduct();

        String query = "SELECT " + BaseShema.ColsUserProducts.UUID + ", "
                + BaseShema.ColsUserProducts.PRODUCT_NAME + ", "
                + BaseShema.ColsUserProducts.PRODUCT_LOW_PRICE + ", "
                + BaseShema.ColsUserProducts.PRODUCT_HIGH_PRICE + ", "
                + BaseShema.ColsUserProducts.PRODUCT_CATEGORY + ", "
                + BaseShema.ColsUserProducts.PRODUCT_UNDER_CATEGORY + ", "
                + BaseShema.ColsUserProducts.DATE_USERS_ADDED + ", "
                + BaseShema.ColsUserProducts.WEB_SITE + ", "
                + BaseShema.ColsUserProducts.DATE_ADDED_ON_SITE + ", "
                + BaseShema.ColsUserProducts.BOOLEAN_SEARCH
                + " FROM " + BaseShema.UserProductTable.TABLE_NAME;
        Cursor cursor = mSQL.rawQuery(query, null);
        LogApp.Log(">getProductById: ", "Найдено объектов в Базе: " +
                String.valueOf(cursor.getCount()));

        if (cursor.getCount()>0) {
            if (cursor.moveToFirst()) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    String idFromCursor = cursor.getString(cursor
                            .getColumnIndexOrThrow(BaseShema.ColsUserProducts.UUID));
                    if (idFromCursor.equals(id)) {
                        result.setProductId(id);
                        result.setProductName(cursor.getString(cursor
                                .getColumnIndexOrThrow(BaseShema.ColsUserProducts.PRODUCT_NAME)));
                        result.setLowPrice(cursor.getInt(cursor
                                .getColumnIndexOrThrow(BaseShema.ColsUserProducts.PRODUCT_LOW_PRICE)));
                        result.setHighPrice(cursor.getInt(cursor
                                .getColumnIndexOrThrow(BaseShema.ColsUserProducts.PRODUCT_HIGH_PRICE)));
                        result.setCategory(cursor.getString(cursor
                                .getColumnIndexOrThrow(BaseShema.ColsUserProducts.PRODUCT_CATEGORY)));
                        result.setUnderCategory(cursor.getString(cursor
                                .getColumnIndexOrThrow(BaseShema.ColsUserProducts.PRODUCT_UNDER_CATEGORY)));
                        result.setDateUserCreate(cursor.getString(cursor
                                .getColumnIndexOrThrow(BaseShema.ColsUserProducts.DATE_USERS_ADDED)));
                        result.setSearchSite(cursor.getString(cursor
                                .getColumnIndexOrThrow(BaseShema.ColsUserProducts.WEB_SITE)));
                        result.setDateAddedOnSite(cursor.getString(cursor
                                .getColumnIndexOrThrow(BaseShema.ColsUserProducts.DATE_ADDED_ON_SITE)));
                        result.setNeedSearch(cursor.getInt(cursor
                                .getColumnIndexOrThrow(BaseShema.ColsUserProducts.BOOLEAN_SEARCH)));
                        break;
                    } else cursor.moveToNext();
                }
            }
        }
        cursor.close();

        if (TextUtils.isEmpty(result.getProductName())){
            LogApp.Log(">getProductById: ", ">>>TextUtils.isEmpty: " +
                    "error! Product is Empty");
            result.setProductId(StaticImport.NULL_OBJECT);
        }

        return result;
        //TODO не доработан: нужно заменить эквуалс на что-то под 16 АПИ
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

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
