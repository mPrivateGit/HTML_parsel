package com.example.aprivate.html_parsel.network;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.example.aprivate.html_parsel.data.BaseHelperFoundProducts;
import com.example.aprivate.html_parsel.data.BaseShema;
import com.example.aprivate.html_parsel.log.LogApp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Thread.sleep;


public class RequestCreator extends AsyncTask<String, Context, Void> {
    private static final String BASE_URL_SEARCH_AMAZON =
            "https://www.amazon.com/s/ref=nb_sb_noss_2?url=search-alias%3Daps&field-keywords=";
    private Context mContext;
    private String mSearchProduct;  //убрать пустые места между словами и заменить на +
    private ArrayList<FoundProduct> mArr = new ArrayList<>();
    private SQLiteDatabase mSQL;

    public RequestCreator(String searchProduct, Context context) {
        mContext = context;
        mSearchProduct = searchProduct;
        mSearchProduct = "macbook+pro";
        LogApp.Log(RequestCreator.class.getCanonicalName(), BASE_URL_SEARCH_AMAZON + mSearchProduct);
        //viewToLogResults(getProducts(mContext));
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            jsonsCreator();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            viewToLogResults();
        }
        return null;
    }

    private int sum() throws IOException {
        Document doc = Jsoup.connect(BASE_URL_SEARCH_AMAZON + mSearchProduct)
                .data("query", "Java")
                .userAgent("Mozilla")
                .timeout(3000)
                .get();
        String target = doc.select("#pagn .pagnDisabled").text();
        if (TextUtils.isEmpty(target)){
            target = "1";
        }
        return Integer.parseInt(target);
    }

    private void jsonsCreator() throws IOException, InterruptedException {
        BaseHelperFoundProducts baseHelperFoundProducts = new BaseHelperFoundProducts(mContext);
        int count = sum();
        LogApp.Log("..... Происходит парсинг страниц... ", "Осталось страниц: " + count);

        Document doc = Jsoup.connect(BASE_URL_SEARCH_AMAZON + mSearchProduct).get();
        Elements elements = doc.select("div.s-item-container");
        for (int i = 0; i < elements.size(); i++) {
            //достань имя, цену, ссылку
            FoundProduct product = new FoundProduct();
            //Достаю имя (титул продукта)
            String name = elements.get(i).select("[title]").text();
            product.setProductName(name);
            //Достаю цену
            String price = elements.get(i).select(".sx-price").text();
            if (!TextUtils.isEmpty(price)) {
                product.setPrice(price);
            } else {
                String miniPrice = elements.get(i).select(".a-size-base").text();
                product.setPrice(miniPrice);
            }
            // достаю URL
            Element url = elements.get(i).select(".a-text-normal").first();
            String href = url.absUrl("href");
            product.setUrl(href);

            baseHelperFoundProducts.createProduct(product);
            //mArr.add(product);
        }

        if (sum() > 0) {
            for (int q = 2; q < sum()+1; q++) {
                count--;
                LogApp.Log("..... Происходит парсинг страниц... ", "Осталось страниц: " + count);
                sleep(20000); //TODO random
                String page = "&page=" + q;
                doc = Jsoup.connect(BASE_URL_SEARCH_AMAZON + mSearchProduct + page).get(); //TODO set firefox
                Elements nextElements = doc.select("div.s-item-container");
                for (int w = 0; w < nextElements.size(); w++) {
                    FoundProduct product = new FoundProduct();
                    //Достаю имя (титул продукта)
                    String name = nextElements.get(w).select("[title]").text();
                    product.setProductName(name);
                    //Достаю цену
                    String price = nextElements.get(w).select(".sx-price").text();
                    if (!TextUtils.isEmpty(price)) {
                        product.setPrice(price);
                    } else {
                        String miniPrice = nextElements.get(w).select(".a-size-base").text();
                        product.setPrice(miniPrice);
                    }
                    // достаю URL
                    Element url = nextElements.get(w).select(".a-text-normal").first();
                    String href = url.absUrl("href");
                    product.setUrl(href);

                    baseHelperFoundProducts.createProduct(product);
                    //mArr.add(product);
                }
            }
        }
        LogApp.Log("...Парсинг завершен....", ".");

        //чтение
        mSQL = baseHelperFoundProducts.getReadableDatabase();

        String projection [] = {
                BaseShema.ColsFoundsProduct.UUID,
                BaseShema.ColsFoundsProduct.PRODUCT_NAME,
                BaseShema.ColsFoundsProduct.PRODUCT_PRICE,
                BaseShema.ColsFoundsProduct.URL,
        };
        Cursor cursor = mSQL.query(BaseShema.FoundsProductTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        try {
            int targetUUID = cursor.getColumnIndex(BaseShema.ColsFoundsProduct.UUID);
            int targetName = cursor.getColumnIndex(BaseShema.ColsFoundsProduct.PRODUCT_NAME);
            int targetPrice = cursor.getColumnIndex(BaseShema.ColsFoundsProduct.PRODUCT_PRICE);
            int targetUrl = cursor.getColumnIndex(BaseShema.ColsFoundsProduct.URL);

            while (cursor.moveToNext()) {
                String uuid = cursor.getString(targetUUID);
                String name = cursor.getString(targetName);
                String price = cursor.getString(targetPrice);
                String url = cursor.getString(targetUrl);

                FoundProduct foundProduct = new FoundProduct();
                foundProduct.setProductId(uuid);
                foundProduct.setProductName(name);
                foundProduct.setPrice(price);
                foundProduct.setUrl(url);


                Log.d(">>>>>>>>>-----: ", uuid +
                        "\n" +
                        price +
                        name +
                        "\n" +
                        url);

                Log.d("ITEM------->: ", cursor.getCount()+ "");
            }
        } finally {
            cursor.close();
        }
    }

    private void viewToLogResults(){
//        for (int i = 0; i <mArr.size() ; i++) {
//            System.out.println(mArr.get(i).getPrice()
//                    + " "
//                    + mArr.get(i).getProduct()
//                    + "\n"
//                    + mArr.get(i).getUrl());
//        }
//        System.out.println(mArr.size());
    }

    private List<FoundProduct> getProducts(Context context){
        List<FoundProduct> mList = new ArrayList<>();
        BaseHelperFoundProducts baseHelperFoundProducts = new BaseHelperFoundProducts(context);

        //чтение
        mSQL = baseHelperFoundProducts.getReadableDatabase();

        String projection [] = {
                BaseShema.ColsFoundsProduct.UUID,
                BaseShema.ColsFoundsProduct.PRODUCT_NAME,
                BaseShema.ColsFoundsProduct.PRODUCT_PRICE,
                BaseShema.ColsFoundsProduct.URL,
        };
        Cursor cursor = mSQL.query(BaseShema.FoundsProductTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        try {
            int targetUUID = cursor.getColumnIndex(BaseShema.ColsFoundsProduct.UUID);
            int targetName = cursor.getColumnIndex(BaseShema.ColsFoundsProduct.PRODUCT_NAME);
            int targetPrice = cursor.getColumnIndex(BaseShema.ColsFoundsProduct.PRODUCT_PRICE);
            int targetUrl = cursor.getColumnIndex(BaseShema.ColsFoundsProduct.URL);

            while (cursor.moveToNext()) {
                String uuid = cursor.getString(targetUUID);
                String name = cursor.getString(targetName);
                String price = cursor.getString(targetPrice);
                String url = cursor.getString(targetUrl);

                FoundProduct foundProduct = new FoundProduct();
                foundProduct.setProductId(uuid);
                foundProduct.setProductName(name);
                foundProduct.setPrice(price);
                foundProduct.setUrl(url);

                mList.add(foundProduct);

                Log.d(">>>>>>>>>-----: ", uuid);
                Log.d(">>>>>>>>>-----: ", name);
                Log.d(">>>>>>>>>-----: ", url);

                Log.d("ITEM------->: ", cursor.getCount()+ "");
            }
        } finally {
            cursor.close();
        }
        return mList;
    }

    private void viewToLogResults(List<FoundProduct> mArr){
        for (int i = 0; i <mArr.size() ; i++) {
            System.out.println(mArr.get(i).getPrice()
                    + " "
                    + mArr.get(i).getProduct()
                    + "\n"
                    + mArr.get(i).getUrl());
        }
        System.out.println(mArr.size());
    }
}
