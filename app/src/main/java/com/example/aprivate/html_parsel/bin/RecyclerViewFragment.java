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
import com.example.aprivate.html_parsel.data.BaseUserProductHelperUserProduct;
import com.example.aprivate.html_parsel.data.BaseShema;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private SQLiteDatabase mSQL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new ProductAdapter(getProducts(getContext()), getActivity()));

        return v;
    }

    public void notifyRecycler() {
        mRecyclerView.swapAdapter(new ProductAdapter(getProducts(getContext()), getActivity()), true);
    }

    private List<SearchProduct> getProducts(Context context){
        List<SearchProduct> mList = new ArrayList<>();
        BaseUserProductHelperUserProduct baseHelperUserProduct = new BaseUserProductHelperUserProduct(context);

        //чтение
        mSQL = baseHelperUserProduct.getReadableDatabase();

        String projection [] = {
                BaseShema.ColsUserProducts.UUID,
                BaseShema.ColsUserProducts.PRODUCT_NAME,
                BaseShema.ColsUserProducts.PRODUCT_PRICE,
                BaseShema.ColsUserProducts.BOOLEAN_SEARCH,
        };
        Cursor cursor = mSQL.query(BaseShema.UserProductTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        try {
            int targetUUID = cursor.getColumnIndex(BaseShema.ColsUserProducts.UUID);
            int targetName = cursor.getColumnIndex(BaseShema.ColsUserProducts.PRODUCT_NAME);
            int targetPrice = cursor.getColumnIndex(BaseShema.ColsUserProducts.PRODUCT_PRICE);
            int targetBoolean = cursor.getColumnIndex(BaseShema.ColsUserProducts.BOOLEAN_SEARCH);

            while (cursor.moveToNext()) {
                String uuid = cursor.getString(targetUUID);
                String name = cursor.getString(targetName);
                String price = cursor.getString(targetPrice);
                String bool = cursor.getString(targetBoolean);

                SearchProduct searchProduct = new SearchProduct();
                searchProduct.setProductId(uuid);
                searchProduct.setProductName(name);
                searchProduct.setPrice(price);
                searchProduct.convertToBoolean(bool);

                mList.add(searchProduct);

                Log.d(">>>>>>>>>-----: ", uuid);
                Log.d(">>>>>>>>>-----: ", name);
                Log.d(">>>>>>>>>-----: ", bool);

                Log.d("ITEM------->: ", cursor.getCount()+ "");
            }
        } finally {
            cursor.close();
        }

        return mList;
    }
}
