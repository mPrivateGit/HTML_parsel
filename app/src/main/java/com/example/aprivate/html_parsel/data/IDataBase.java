package com.example.aprivate.html_parsel.data;


import com.example.aprivate.html_parsel.Product;

public interface IDataBase {

    void createProduct(Product product);
    Product getProductByPosition(int position);
    void deleteProduct(Product product);
    void updateProduct(Product product);
    int size();

}
