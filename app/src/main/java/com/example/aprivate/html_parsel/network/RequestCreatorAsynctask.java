package com.example.aprivate.html_parsel.network;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.example.aprivate.html_parsel.SearchProduct;
import com.example.aprivate.html_parsel.data.BaseHelperFoundProducts;
import com.example.aprivate.html_parsel.data.BaseHelperUserProduct;
import com.example.aprivate.html_parsel.log.LogApp;
import com.example.aprivate.html_parsel.services.SearchLogic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;


public class RequestCreatorAsynctask extends AsyncTask<Boolean, Object, Boolean> {
    private static final String BASE_URL_SEARCH_AMAZON =
            "https://www.amazon.com/s/ref=nb_sb_noss_2?url=search-alias%3Daps&field-keywords=";
    private static String FINALLY_URL = "";
    private Context mContext;
    private SearchProduct mSearchingProduct;
    private int mDocLogOut = 0;
    private String mLowPrice;
    private String mHighPrice;
    private String mSearchProduct;  //убрать пустые места между словами и заменить на +

    //Если указан диапазон цены
//    public RequestCreatorAsynctask(Context context, String searchProduct, String string) {
//        mContext = context;
//        mSearchProduct = searchProduct;
//        FINALLY_URL = BASE_URL_SEARCH_AMAZON + mSearchProduct;
//        LogApp.Log(RequestCreatorAsynctask.class.getCanonicalName(), FINALLY_URL);
//    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Thread t = currentThread();
        t.setName("ASYNCTASK THREAD - ");
        LogApp.Log("Поток Асинтаска", t.getName() + t.toString());
    }

    //Если диапазон цены не указан
    public RequestCreatorAsynctask(Context context, String searchProductId) {
        mContext = context;
        BaseHelperUserProduct baseHelperUserProduct = new
                BaseHelperUserProduct(context);
        mSearchingProduct = baseHelperUserProduct.getProductById(searchProductId);

        LogApp.Log("***********", "***********************************");
        LogApp.Log("searchProductId: ", searchProductId);
        LogApp.Log("--------------", "\n" + mSearchingProduct.getProductName() + "\n");

        FINALLY_URL = BASE_URL_SEARCH_AMAZON
                + mSearchingProduct.getProductName();
                //+ mSearchingProduct.getLowPrice()
                //+ mSearchingProduct.getHighPrice();

        // iUrlCreator();

        LogApp.Log(RequestCreatorAsynctask.class.getCanonicalName(), FINALLY_URL);
    }

    @Override
    protected Boolean doInBackground(Boolean... params) {
        try {
            jsonsCreator();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //
        }
        return true;
    }

    private int pagesCount() throws IOException {
        Document doc = Jsoup.connect(FINALLY_URL)
                .data("query", "Java")
                .userAgent("zalupa")
                .get();
        String target = doc.select("#pagn .pagnDisabled").text();
        LogApp.Log(">>>pagesCount>>>", "Всего страниц = " + target );

        if (mDocLogOut == 0) {
        LogApp.Log("***************", "***********************");
        LogApp.Log("HTML: ", doc.body().toString());
        LogApp.Log("***************", "***********************");
            mDocLogOut+=1;
        }

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
                .userAgent("new_zalupa")
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
                price = formatPrice(price);
                product.setPrice(price);
                LogApp.Log("ЗАПИСАНА ЦЕНА: ", price);
            } else {
                String miniPrice = elements.get(i).select(".a-size-base").text();
                price = formatPrice(miniPrice);
                product.setPrice(price);
            }
            // достаю URL
            String url = elements.get(i).select(".a-text-normal").text();
            //String href = url.absUrl("href");
            product.setUrl(url);


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
                        .userAgent("new_zalupa")
                        .get();
                Elements nextElements = doc.select("div.s-item-container");
                for (int w = 0; w < nextElements.size(); w++) {
                    FoundProduct product = new FoundProduct();
                    //Достаю имя (титул продукта)
                    String name = nextElements.get(w).select("[title]").text();
                    product.setProductName(name);
                    //Достаю цену
                    String price = nextElements.get(w).select(".sx-price").text();
                    LogApp.Log("------->", price);
                    if (!TextUtils.isEmpty(price)) {
                        price = formatPrice(price);
                        product.setPrice(price);
                        LogApp.Log("ЗАПИСАНА ЦЕНА: ", price);
                    } else {
                        String miniPrice = nextElements.get(w).select(".a-size-base").text();
                        price = formatPrice(miniPrice);
                        product.setPrice(price);
                    }
                    // достаю URL
                    String url = nextElements.get(w).select(".a-text-normal").text();
                    //String href = url.absUrl("href");
                    product.setUrl(url);

                    baseHelperFoundProducts.createProduct(product);
                }
            }
        }
        LogApp.Log("...Парсинг завершен....", ".");
        //onPostExecute("out");
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
            LogApp.Log(RequestCreatorAsynctask.class.getCanonicalName(), "crush = " + "stopThread()");
        }
    }

    @Override
    protected void onPostExecute(Boolean s) {
        LogApp.Log("onPostExecute", "RUN");
        SearchLogic logic = new SearchLogic();
        //logic.searchResultForUser(mContext, mSearchingProduct);

    }

    private String formatPrice(String priceSite){
        String result = "";

        for (int i = 0; i <priceSite.length() ; i++) {
            String target = String.valueOf(priceSite.charAt(i));
            if (target.equals(".")) break;
            for (int j = 0; j < 9; j++) {
                if (target.equals(String.valueOf(j))) {
                    result = result + priceSite.charAt(i);
                }
            }
        }
        if (result.isEmpty()){
            return "this is null :(";
        }
        return result;
    }

    private void iUrlCreator(){
        if (!TextUtils.isEmpty(mSearchingProduct.getCategory())){
            FINALLY_URL = FINALLY_URL + mSearchingProduct.getCategory();
            if (!TextUtils.isEmpty(mSearchingProduct.getUnderCategory())){
                FINALLY_URL = FINALLY_URL + mSearchingProduct.getUnderCategory();
            }
        }
    }
}
