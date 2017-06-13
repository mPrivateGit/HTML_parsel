package com.example.aprivate.html_parsel.adapters;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aprivate.html_parsel.Product;
import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.data.BaseHelper;
import com.example.aprivate.html_parsel.data.BaseShema;
import com.example.aprivate.html_parsel.holders.ProductHolder;

import java.util.ArrayList;
import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {
    private List<Product> mProducts;
    private SQLiteDatabase mSQL;
    private Activity mAct;

    public ProductAdapter(List<Product> products, Activity activity){
        mProducts = new ArrayList<>();
        mProducts.addAll(products);
//        for (int i=0;i<30;i++) {
//            Product p = new Product();
//            p.setProductName("test" + i);
//            mProducts.add(p);
//        }
        //testingDb(activity.getApplicationContext());
        mAct = activity;
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        return new ProductHolder(view, mAct);
    }

    @Override
    public void onBindViewHolder(ProductHolder holder, int position) {
        String product = mProducts.get(position).getProductName();
        holder.bind(product, null);
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }


//    void testingDb(Context context){
//        //запись
//        BaseHelper baseHelper = new BaseHelper(context);
//        Product product = new Product();
//        product.setProductName("testing");
//        product.setNeedSearch("true");
//        baseHelper.createProduct(product);
//
//        //чтение
//        mSQL = baseHelper.getReadableDatabase();
//
//        String projection [] = {
//                BaseShema.Cols.UUID,
//                BaseShema.Cols.PRODUCT_NAME,
//                BaseShema.Cols.BOOLEAN_SEARCH,
//                };
//        Cursor cursor = mSQL.query(BaseShema.ProductTable.TABLE_NAME,
//                projection,
//                null,
//                null,
//                null,
//                null,
//                null);
//
//        try {
//            int targetUUID = cursor.getColumnIndex(BaseShema.Cols.UUID);
//            int targetName = cursor.getColumnIndex(BaseShema.Cols.PRODUCT_NAME);
//            int targetBoolean = cursor.getColumnIndex(BaseShema.Cols.BOOLEAN_SEARCH);
//
//            while (cursor.moveToNext()) {
//                String uuid = cursor.getString(targetUUID);
//                String name = cursor.getString(targetName);
//                String bool = cursor.getString(targetBoolean);
//
//                Log.d(">>>>>>>>>-----: ", uuid);
//                Log.d(">>>>>>>>>-----: ", name);
//                Log.d(">>>>>>>>>-----: ", bool + "");
//
//                Log.d("ITEM------->: ", cursor.getCount()+ "");
//            }
//        } finally {
//            cursor.close();
//        }
//
//    }
}
