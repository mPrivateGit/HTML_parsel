package com.example.aprivate.html_parsel.data;


import com.example.aprivate.html_parsel.SearchProduct;

public interface IDataBase {

    void createProduct(SearchProduct searchProduct);
    SearchProduct getProductByPosition(int position);
    void deleteProduct(SearchProduct searchProduct);
    void updateProduct(SearchProduct searchProduct);
    int size();

}
