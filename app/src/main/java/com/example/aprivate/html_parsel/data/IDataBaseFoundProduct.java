package com.example.aprivate.html_parsel.data;

import com.example.aprivate.html_parsel.SearchProduct;
import com.example.aprivate.html_parsel.network.FoundProduct;


public interface IDataBaseFoundProduct {
    void createProduct(FoundProduct searchProduct);
    FoundProduct getProductByPosition(int position);
    void deleteProduct(FoundProduct searchProduct);
    void updateProduct(FoundProduct searchProduct);
    int size();

}
