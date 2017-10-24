package com.example.aprivate.html_parsel.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.aprivate.html_parsel.network.FoundProduct;


public class BaseHelperFoundProducts extends SQLiteOpenHelper
        implements IDataBaseFoundProduct {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "foundsproducts.db";
    private SQLiteDatabase mSQL;
    private Context mContext;

    public BaseHelperFoundProducts(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String mSQL = "create table " + BaseShema.FoundsProductTable.TABLE_NAME + "(" +
                " _id integer primary key autoincrement, " +
                BaseShema.ColsFoundsProduct.UUID + ", " +
                BaseShema.ColsFoundsProduct.PRODUCT_NAME + ", " +
                BaseShema.ColsFoundsProduct.PRODUCT_PRICE + ", " +
                BaseShema.ColsFoundsProduct.URL +
                ")";
        db.execSQL(mSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //
    }

    @Override
    public void createProduct(FoundProduct foundProduct) {
        mSQL = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BaseShema.ColsFoundsProduct.UUID, foundProduct.getmId());
        contentValues.put(BaseShema.ColsFoundsProduct.PRODUCT_NAME, foundProduct.getProduct());
        contentValues.put(BaseShema.ColsFoundsProduct.PRODUCT_PRICE, foundProduct.getPrice());
        contentValues.put(BaseShema.ColsFoundsProduct.URL, foundProduct.getUrl());

        mSQL.insert(BaseShema.FoundsProductTable.TABLE_NAME, null, contentValues);
    }

    @Override
    public FoundProduct getProductByPosition(int position) {
        return null;
    }

    @Override
    public void deleteProduct(FoundProduct searchProduct) {
        BaseHelperFoundProducts baseHelperFoundProducts = new BaseHelperFoundProducts(mContext);
        mSQL = baseHelperFoundProducts.getWritableDatabase();
        String target = "";
        String command = target +"";

        mSQL.delete(BaseShema.UserProductTable.TABLE_NAME, command, null);
        //TODO ??? что-то тут не чисто, нужно проверить правильность удаления
    }

    @Override
    public void updateProduct(FoundProduct searchProduct) {
        BaseHelperFoundProducts baseHelperFoundProducts = new BaseHelperFoundProducts(mContext);
        mSQL = baseHelperFoundProducts.getWritableDatabase();
        String target = "";
        String command = target +"";

        mSQL.update(BaseShema.UserProductTable.TABLE_NAME, null, command, null);
        //TODO ??? что-то тут не чисто, нужно проверить правильность обновления
    }


    @Override
    public int size() {
        BaseHelperFoundProducts baseHelperFoundProducts = new BaseHelperFoundProducts(mContext);
        mSQL = baseHelperFoundProducts.getReadableDatabase();

        String projection [] = {
                BaseShema.ColsFoundsProduct.UUID,
                BaseShema.ColsFoundsProduct.PRODUCT_NAME,
                BaseShema.ColsFoundsProduct.URL
        };

        Cursor cursor = mSQL.query(BaseShema.FoundsProductTable.TABLE_NAME,
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
    public void deleteTable(SQLiteDatabase db){
        String delete = "DROP TABLE " + BaseShema.FoundsProductTable.TABLE_NAME;
        db.execSQL(delete);
    }

}
