package com.example.aprivate.html_parsel.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.aprivate.html_parsel.SearchProduct;
import com.example.aprivate.html_parsel.log.LogApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        // Бд и действия с ней (чтение)
        mSQL = baseHelperUserProduct.getReadableDatabase();
        LogApp.Log("BD---------", "===== " + id);

        /* TODO: Внимание! Это костыль, желтельно доработать*/
        // Лист который заполню после чтения
        List<SearchProduct> mList = new ArrayList<>();

        // Объект
        SearchProduct target = new SearchProduct();

        //чтение
        mSQL = baseHelperUserProduct.getReadableDatabase();

        String projection [] = {
                BaseShema.ColsUserProducts.UUID,
                BaseShema.ColsUserProducts.PRODUCT_NAME,
                BaseShema.ColsUserProducts.PRODUCT_LOW_PRICE,
                BaseShema.ColsUserProducts.PRODUCT_HIGH_PRICE,
                BaseShema.ColsUserProducts.PRODUCT_CATEGORY,
                BaseShema.ColsUserProducts.PRODUCT_UNDER_CATEGORY,
                BaseShema.ColsUserProducts.DATE_USERS_ADDED,
                BaseShema.ColsUserProducts.WEB_SITE,
                BaseShema.ColsUserProducts.DATE_ADDED_ON_SITE,
                BaseShema.ColsUserProducts.BOOLEAN_SEARCH
        };
        Cursor cursor = mSQL.query(BaseShema.UserProductTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        try {
            int targetUUID = cursor
                    .getColumnIndex(BaseShema.ColsUserProducts.UUID);
            int targetName = cursor
                    .getColumnIndex(BaseShema.ColsUserProducts.PRODUCT_NAME);
            int targetLowPrice = cursor
                    .getColumnIndex(BaseShema.ColsUserProducts.PRODUCT_LOW_PRICE);
            int targetHighPrice = cursor
                    .getColumnIndex(BaseShema.ColsUserProducts.PRODUCT_HIGH_PRICE);
            int targetCategory = cursor
                    .getColumnIndex(BaseShema.ColsUserProducts.PRODUCT_CATEGORY);
            int targetUnderCategory = cursor
                    .getColumnIndex(BaseShema.ColsUserProducts.PRODUCT_UNDER_CATEGORY);
            int targetDateUsersAdded = cursor
                    .getColumnIndex(BaseShema.ColsUserProducts.DATE_USERS_ADDED);
            int targetWebSite = cursor
                    .getColumnIndex(BaseShema.ColsUserProducts.WEB_SITE);
            int targetAddedOnSite = cursor
                    .getColumnIndex(BaseShema.ColsUserProducts.DATE_ADDED_ON_SITE);
            int targetBoolean = cursor
                    .getColumnIndex(BaseShema.ColsUserProducts.BOOLEAN_SEARCH);

            while (cursor.moveToNext()) {
                String uuid = cursor.getString(targetUUID);
                String name = cursor.getString(targetName);
                String lowPrice = cursor.getString(targetLowPrice);
                String highPrice = cursor.getString(targetHighPrice);
                String category = cursor.getString(targetCategory);
                String underCategory = cursor.getString(targetUnderCategory);
                String dateUsersAdded = cursor.getString(targetDateUsersAdded);
                String webSite = cursor.getString(targetWebSite);
                String addedOnSite = cursor.getString(targetAddedOnSite);
                String bool = cursor.getString(targetBoolean);

                SearchProduct searchProduct = new SearchProduct();
                searchProduct.setProductId(uuid);
                searchProduct.setProductName(name);
                searchProduct.setPrice(lowPrice);
                searchProduct.setHighPrice(highPrice);
                searchProduct.setCategory(category);
                searchProduct.setUnderCategory(underCategory);
                searchProduct.setDateUserAdded(dateUsersAdded);
                searchProduct.setSearchSite(webSite);
                searchProduct.setDateAddedOnSite(addedOnSite);
                searchProduct.convertToBoolean(bool);

                mList.add(searchProduct);

//                LogApp.Log(">>>>>>>>>-----: ", uuid);
//                LogApp.Log(">>>>>>>>>-----: ", name);
//                LogApp.Log(">>>>>>>>>-----: ", bool);
//                LogApp.Log(">>>>>>>>>-----: ", dateUsersAdded);
//
//                Log.d("ITEM------->: ", cursor.getCount()+ "");
            }
        } finally {
            cursor.close();
        }

        for (int i = 0; i < mList.size() ; i++) {
            if (Objects.equals(mList.get(i).getProductId(), id)){
                target = mList.get(i);
                LogApp.Log("Look!>>>>>>",
                        "public SearchProduct getProductById(String id) has worked");
                break;
            } else {
                target.setProductId("Is Empty");
                target.setProductName("null product");
            }
        }
        return target;
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
