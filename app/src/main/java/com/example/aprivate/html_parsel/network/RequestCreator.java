package com.example.aprivate.html_parsel.network;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.example.aprivate.html_parsel.data.BaseHelperFoundProducts;
import com.example.aprivate.html_parsel.log.LogApp;
import com.example.aprivate.html_parsel.services.SearchService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import static java.lang.Thread.sleep;


public class RequestCreator extends AsyncTask<Object, Object, String> {
    private static final String BASE_URL_SEARCH_AMAZON =
            "https://www.amazon.com/s/ref=nb_sb_noss_2?url=search-alias%3Daps&field-keywords=";
    private static String FINALLY_URL = "";
    private Context mContext;
    private String mLowPrice;
    private String mHighPrice;
    private String mSearchProduct;  //убрать пустые места между словами и заменить на +

    //Если указан диапазон цены
    public RequestCreator(Context context, String searchProduct) {
        mContext = context;
        mSearchProduct = searchProduct;
        FINALLY_URL = BASE_URL_SEARCH_AMAZON + mSearchProduct;
        LogApp.Log(RequestCreator.class.getCanonicalName(), FINALLY_URL);
    }
    //Если диапазон цены не указан
    public RequestCreator(Context context, String searchProduct, int lowPrice, int highPrice) {
        mContext = context;
        mLowPrice = "&low-price="+ lowPrice;
        mHighPrice = "&high-price=" + highPrice;
        mSearchProduct = searchProduct;
        FINALLY_URL = BASE_URL_SEARCH_AMAZON + mSearchProduct + mLowPrice + mHighPrice;
        LogApp.Log(RequestCreator.class.getCanonicalName(), FINALLY_URL);
    }

    @Override
    protected String doInBackground(Object... params) {
        try {
            jsonsCreator();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //
        }
        return mSearchProduct;
    }

    private int pagesCount() throws IOException {
        Document doc = Jsoup.connect(FINALLY_URL)
                .data("query", "Java")
                .userAgent("Mozilla")
                .get();
        String target = doc.select("#pagn .pagnDisabled").text();
        LogApp.Log(">>>pagesCount>>>", "Всего страниц = " + target );
        if (TextUtils.isEmpty(target)){
            target = "1";
        }
        return Integer.parseInt(target);
    }

    private synchronized void jsonsCreator() throws IOException, InterruptedException {
        BaseHelperFoundProducts baseHelperFoundProducts = new BaseHelperFoundProducts(mContext);
        int count = pagesCount();
        LogApp.Log("..... Происходит парсинг страниц... ", "Осталось страниц: " + count);

        Document doc = Jsoup.connect(FINALLY_URL)
                .userAgent("Mozilla")
                .timeout(5000)
                .get();
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
        }

        //stopThread();
        if (pagesCount() > 0) {
            for (int q = 2; q < pagesCount() + 1; q++) {
                count--;
                LogApp.Log("..... Происходит парсинг страниц... ", "Осталось страниц: " + count);
                stopThread();
                String page = "&page=" + q;
                doc = Jsoup.connect(FINALLY_URL + page)
                        .userAgent("Mozilla")
                        .get();
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
                }
            }
        }
        LogApp.Log("...Парсинг завершен....", ".");
    }

    private static synchronized void stopThread(){
        double a = 13000;
        double b = 17000;
        double i;
        i = a + (Math.random() * b);
        LogApp.Log("Пауза при парсинге...",
                "Поток засыавет на " + i/1000 + " секунд...");
        try {
            sleep((int) i);
        } catch (InterruptedException e) {
            e.printStackTrace();
            LogApp.Log(RequestCreator.class.getCanonicalName(), "crush = " + "stopThread()");
        }
    }

    @Override
    protected void onPostExecute(String s) {
        LogApp.Log("onPostExecute", "RUN");
        SearchService service = new SearchService();
        service.viewToLogResults(service.getProducts(mContext));
    }
}
