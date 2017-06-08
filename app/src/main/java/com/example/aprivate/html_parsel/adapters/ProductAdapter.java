package com.example.aprivate.html_parsel.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aprivate.html_parsel.Product;
import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.holders.ProductHolder;

import java.util.ArrayList;
import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {
    private List<Product> mProducts;

    public ProductAdapter(List<Product> products){
        mProducts = new ArrayList<>();
        for (int i=0;i<30;i++) {
            Product p = new Product();
            p.setProductName("test" + i);
            mProducts.add(p);
        }

    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        return new ProductHolder(view, parent.getContext());
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

    private List<Product> test(){
        mProducts = new ArrayList<>();
        for (int i=0;i<30;i++){
            Product p = new Product();
            p.setProductName("test" + i);
            mProducts.add(p);
        }

        return mProducts;
    }
}
