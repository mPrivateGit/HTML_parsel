package com.example.aprivate.html_parsel.data;


public class BaseShema {
    /** Таблица №1 (данные введенные пользователем) */
    public static final class UserProductTable {
        public static final String TABLE_NAME = "user_products";
    }
    public static final class ColsUserProducts {
        public static final String UUID = "uuid";
        public static final String PRODUCT_NAME = "name";
        public static final String PRODUCT_LOW_PRICE = "low_price";
        public static final String PRODUCT_HIGH_PRICE = "high_price";
        public static final String PRODUCT_CATEGORY = "category";
        public static final String PRODUCT_UNDER_CATEGORY = "under_category";
        public static final String DATE_USERS_ADDED = "user_added";
        public static final String WEB_SITE = "web_site";
        public static final String DATE_ADDED_ON_SITE = "on_site_added";
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
