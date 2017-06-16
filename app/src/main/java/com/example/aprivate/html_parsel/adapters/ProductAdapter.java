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
    private Activity mAct;

    public ProductAdapter(List<Product> products, Activity activity){
        mProducts = new ArrayList<>();
        mProducts.addAll(products);
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
        Boolean bool = mProducts.get(position).getNeedSearch();
        holder.bind(product, bool);
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

}
