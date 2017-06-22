package com.example.aprivate.html_parsel.network;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.example.aprivate.html_parsel.log.LogApp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import static java.lang.Thread.sleep;


public class RequestCreator extends AsyncTask<String, Void, Void> {
    private static final String BASE_URL =
            "https://www.amazon.com/s/ref=nb_sb_noss_2?url=search-alias%3Daps&field-keywords=";
    private int mPagesCount;
    private String mSearchProduct;  //убрать пустые места между словами и заменить на +

    public RequestCreator(String searchProduct){
        mSearchProduct = searchProduct;
        mSearchProduct = "macbook+pro";
        LogApp.Log(RequestCreator.class.getCanonicalName(), BASE_URL+mSearchProduct);
    }

    @Override
    protected Void doInBackground(String... params) {

        try {
            jsonsCreator();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    private int sum() throws IOException {
        Document doc = Jsoup.connect(BASE_URL + mSearchProduct).get();
        String target = doc.select("#pagn .pagnDisabled").text();
        return Integer.valueOf(target);
    }

    private void jsonsCreator() throws IOException, InterruptedException {
        Document doc = Jsoup.connect(BASE_URL + mSearchProduct).get();
        Elements answers = doc.select("#resultsCol .a-row a");
        for (Element answerer : answers) {
            System.out.println("Answerer: " + answerer.text());
        }
        if (sum()>0){
            for (int i = 2; i < sum() ; i++) {
                int count = sum()-i;
                LogApp.Log("..... Происходит парсинг страниц... ", "Осталось страниц: " + count);
                sleep(20000);
                String page = "&page=" + i;
                doc = Jsoup.connect(BASE_URL + mSearchProduct + page).get();
                Elements answersNext = doc.select("#resultsCol .a-row a");
                for (Element answerer : answersNext) {
                    System.out.println("Answerer: " + answerer.text());
                }
            }
            LogApp.Log("...Парсинг завершен....", ".");
        }
    }
}
