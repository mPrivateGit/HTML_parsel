package com.example.aprivate.html_parsel.network;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.example.aprivate.html_parsel.log.LogApp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Thread.sleep;


public class RequestCreator extends AsyncTask<String, Void, Void> {
    private static final String BASE_URL_SEARCH_AMAZON =
            "https://www.amazon.com/s/ref=nb_sb_noss_2?url=search-alias%3Daps&field-keywords=";
    private int mPagesCount;
    private String mSearchProduct;  //убрать пустые места между словами и заменить на +
    private ArrayList<FoundProduct> mArr = new ArrayList<>();

    public RequestCreator(String searchProduct) {
        mSearchProduct = searchProduct;
        mSearchProduct = "macbook+pro";
        LogApp.Log(RequestCreator.class.getCanonicalName(), BASE_URL_SEARCH_AMAZON + mSearchProduct);
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
        Document doc = Jsoup.connect(BASE_URL_SEARCH_AMAZON + mSearchProduct).get();
        String target = doc.select("#pagn .pagnDisabled").text();
        return Integer.valueOf(target);
    }

    private void jsonsCreator() throws IOException, InterruptedException {
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

            mArr.add(product);
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

                    mArr.add(product);
                }
            }
        }
        LogApp.Log("...Парсинг завершен....", ".");
    }

    private void viewToLogResults(){
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
