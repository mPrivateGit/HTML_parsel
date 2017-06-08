package com.example.aprivate.html_parsel.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.aprivate.html_parsel.Product;

public class BaseHelper extends SQLiteOpenHelper
        implements IDataBase{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "products.db";

    private SQLiteDatabase mSQL;
    private Context mContext;

    public BaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String mSQL = "create table " + BaseShema.ProductTable.TABLE_NAME + "(" +
                " _id integer primary key autoincrement, " +
                BaseShema.Cols.UUID + ", " +
                BaseShema.Cols.PRODUCT_NAME + ", " +
                BaseShema.Cols.BOOLEAN_SEARCH +
                ")";
        db.execSQL(mSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // null
    }

    @Override
    public void createProduct(Product product) {
        mSQL = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BaseShema.Cols.UUID, product.getProductId().toString());
        contentValues.put(BaseShema.Cols.PRODUCT_NAME, product.getProductName());
        contentValues.put(BaseShema.Cols.BOOLEAN_SEARCH, product.getNeedSearch());

        mSQL.insert(BaseShema.ProductTable.TABLE_NAME, null, contentValues);
    }

    @Override
    public Product getProductByPosition(int position) {
        return null;
    }

    @Override
    public void deleteProduct(Product product) {
        BaseHelper baseHelper = new BaseHelper(mContext);
        mSQL = baseHelper.getWritableDatabase();
        String target = "";
        String command = target +"";

        mSQL.delete(BaseShema.ProductTable.TABLE_NAME, command, null);
    }

    @Override
    public void updateProduct(Product product) {
        BaseHelper baseHelper = new BaseHelper(mContext);
        mSQL = baseHelper.getWritableDatabase();
        String target = "";
        String command = target +"";

        mSQL.update(BaseShema.ProductTable.TABLE_NAME, null, command, null);
    }

    @Override
    public int size() {
        BaseHelper baseHelper = new BaseHelper(mContext);
        mSQL = baseHelper.getReadableDatabase();

        String projection [] = {
                BaseShema.Cols.UUID,
                BaseShema.Cols.PRODUCT_NAME,
                BaseShema.Cols.BOOLEAN_SEARCH
                    };

        Cursor cursor = mSQL.query(BaseShema.ProductTable.TABLE_NAME,
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
