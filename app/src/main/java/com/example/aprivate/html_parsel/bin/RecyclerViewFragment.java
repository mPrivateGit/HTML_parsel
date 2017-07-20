package com.example.aprivate.html_parsel.bin;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aprivate.html_parsel.SearchProduct;
import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.adapters.ProductAdapter;
import com.example.aprivate.html_parsel.data.BaseHelperUserProduct;
import com.example.aprivate.html_parsel.data.BaseShema;
import com.example.aprivate.html_parsel.log.LogApp;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewFragment extends Fragment {
    private RecyclerView mRecyclerView;
    protected SQLiteDatabase mSQL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new ProductAdapter
                (getProducts(getContext()), getActivity()));

        return v;
    }

    public void notifyRecycler() {
        mRecyclerView.swapAdapter(new ProductAdapter
                (getProducts(getContext()), getActivity()), true);
    }

    private List<SearchProduct> getProducts(Context context){
        List<SearchProduct> mList = new ArrayList<>();
        BaseHelperUserProduct baseHelperUserProduct =
                new BaseHelperUserProduct(context);

        //чтение
        mSQL = baseHelperUserProduct.getReadableDatabase();

        String projection [] = {
                BaseShema.ColsUserProducts.UUID,
                BaseShema.ColsUserProducts.PRODUCT_NAME,
                BaseShema.ColsUserProducts.PRODUCT_LOW_PRICE,
                BaseShema.ColsUserProducts.PRODUCT_HIGH_PRICE,
                BaseShema.ColsUserProducts.PRODUCT_CATEGORY,
                BaseShema.ColsUserProducts.PRODUCT_UNDER_CATEGORY,
                BaseShema.ColsUserProducts.DATE_USERS_ADDED,
                BaseShema.ColsUserProducts.WEB_SITE,
                BaseShema.ColsUserProducts.DATE_ADDED_ON_SITE,
                BaseShema.ColsUserProducts.BOOLEAN_SEARCH
        };
        Cursor cursor = mSQL.query(BaseShema.UserProductTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        try {
            int targetUUID = cursor
                    .getColumnIndex(BaseShema.ColsUserProducts.UUID);
            int targetName = cursor
                    .getColumnIndex(BaseShema.ColsUserProducts.PRODUCT_NAME);
            int targetLowPrice = cursor
                    .getColumnIndex(BaseShema.ColsUserProducts.PRODUCT_LOW_PRICE);
            int targetHighPrice = cursor
                    .getColumnIndex(BaseShema.ColsUserProducts.PRODUCT_HIGH_PRICE);
            int targetCategory = cursor
                    .getColumnIndex(BaseShema.ColsUserProducts.PRODUCT_CATEGORY);
            int targetUnderCategory = cursor
                    .getColumnIndex(BaseShema.ColsUserProducts.PRODUCT_UNDER_CATEGORY);
            int targetDateUsersAdded = cursor
                    .getColumnIndex(BaseShema.ColsUserProducts.DATE_USERS_ADDED);
            int targetWebSite = cursor
                    .getColumnIndex(BaseShema.ColsUserProducts.WEB_SITE);
            int targetAddedOnSite = cursor
                    .getColumnIndex(BaseShema.ColsUserProducts.DATE_ADDED_ON_SITE);
            int targetBoolean = cursor
                    .getColumnIndex(BaseShema.ColsUserProducts.BOOLEAN_SEARCH);

            while (cursor.moveToNext()) {
                String uuid = cursor.getString(targetUUID);
                String name = cursor.getString(targetName);
                String lowPrice = cursor.getString(targetLowPrice);
                String highPrice = cursor.getString(targetHighPrice);
                String category = cursor.getString(targetCategory);
                String underCategory = cursor.getString(targetUnderCategory);
                String dateUsersAdded = cursor.getString(targetDateUsersAdded);
                String webSite = cursor.getString(targetWebSite);
                String addedOnSite = cursor.getString(targetAddedOnSite);
                String bool = cursor.getString(targetBoolean);

                SearchProduct searchProduct = new SearchProduct();
                searchProduct.setProductId(uuid);
                searchProduct.setProductName(name);
                searchProduct.setPrice(lowPrice);
                searchProduct.setHighPrice(highPrice);
                searchProduct.setCategory(category);
                searchProduct.setUnderCategory(underCategory);
                searchProduct.setDateUserAdded(dateUsersAdded);
                searchProduct.setSearchSite(webSite);
                searchProduct.setDateAddedOnSite(addedOnSite);
                searchProduct.convertToBoolean(bool);

                mList.add(searchProduct);

                LogApp.Log(">>>>>>>>>-----: ", uuid);
                LogApp.Log(">>>>>>>>>-----: ", name);
                LogApp.Log(">>>>>>>>>-----: ", bool);
                LogApp.Log(">>>>>>>>>-----: ", dateUsersAdded);

                Log.d("ITEM------->: ", cursor.getCount()+ "");
            }
        } finally {
            cursor.close();
        }

        return mList;
    }
}
