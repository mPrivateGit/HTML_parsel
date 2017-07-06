package com.example.aprivate.html_parsel.data;

import android.database.sqlite.SQLiteDatabase;

import com.example.aprivate.html_parsel.SearchProduct;
import com.example.aprivate.html_parsel.network.FoundProduct;

import java.sql.SQLDataException;


public interface IDataBaseFoundProduct {
    void createProduct(FoundProduct searchProduct);
    FoundProduct getProductByPosition(int position);
    void deleteProduct(FoundProduct searchProduct);
    void updateProduct(FoundProduct searchProduct);
    int size();
    void deleteTable(SQLiteDatabase db);
}
