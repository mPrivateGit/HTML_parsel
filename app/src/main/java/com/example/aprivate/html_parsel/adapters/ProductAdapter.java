package com.example.aprivate.html_parsel.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aprivate.html_parsel.SearchProduct;
import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.holders.ProductHolder;

import java.util.ArrayList;
import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {
    private List<SearchProduct> mSearchProducts;
    private Activity mAct;

    public ProductAdapter(List<SearchProduct> searchProducts, Activity activity){
        mSearchProducts = new ArrayList<>();
        mSearchProducts.addAll(searchProducts);
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
        String product = mSearchProducts.get(position).getProductName();
        Boolean bool = mSearchProducts.get(position).getNeedSearch();
        holder.bind(product, bool);
    }

    @Override
    public int getItemCount() {
        return mSearchProducts.size();
    }

}
