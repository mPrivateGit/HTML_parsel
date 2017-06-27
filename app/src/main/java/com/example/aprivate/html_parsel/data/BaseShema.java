package com.example.aprivate.html_parsel.data;


public class BaseShema {
    /** Таблица №1 (данные введенные пользователем) */
    public static final class UserProductTable {
        public static final String TABLE_NAME = "products";
    }
    public static final class ColsUserProducts {
        public static final String UUID = "uuid";
        public static final String PRODUCT_NAME = "name";
        public static final String PRODUCT_PRICE = "price";
        public static final String BOOLEAN_SEARCH = "boolean";
    }

    /** Таблица №2 (полученные данные из Амазона) */
    public static final class FoundsProductTable{
        public static final String TABLE_NAME = "founds_product";
    }
    public static final class ColsFoundsProduct{
        public static final String UUID = "uuid";
        public static final String PRODUCT_NAME = "name";
        public static final String PRODUCT_PRICE = "price";
        public static final String URL = "url";
    }
}
