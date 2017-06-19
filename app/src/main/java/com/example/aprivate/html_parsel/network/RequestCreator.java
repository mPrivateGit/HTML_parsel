package com.example.aprivate.html_parsel.network;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.example.aprivate.html_parsel.log.LogApp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class RequestCreator extends AsyncTask<String, Void, Void> {
    private static final String BASE_URL =
            "https://www.amazon.com/s/ref=nb_sb_noss_2?url=search-alias%3Daps&field-keywords=";
    private String mSearchProduct;  //убрать пустые места между словами и заменить на +

    public RequestCreator(String searchProduct){
        mSearchProduct = searchProduct;
        mSearchProduct = "mac book pro";
        LogApp.Log(RequestCreator.class.getCanonicalName(), BASE_URL+mSearchProduct);
    }

    @Override
    protected Void doInBackground(String... params) {
        Document doc = null;
        try {
            doc = Jsoup.connect(BASE_URL + mSearchProduct).get();
            String target = doc.select("#resultsCol").text();
            LogApp.Log(RequestCreator.class.getCanonicalName(), target );
            Elements answerers = doc.select("#resultsCol .a-row a");
            for (Element answerer : answerers) {
                System.out.println("Answerer: " + answerer.text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }
}
