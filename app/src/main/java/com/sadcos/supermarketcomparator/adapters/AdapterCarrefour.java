package com.sadcos.supermarketcomparator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sadcos.supermarketcomparator.R;
import com.sadcos.supermarketcomparator.products.carrefourProducts;

import java.util.List;

/**
 * Created by haerul on 17/03/18.
 */

public class AdapterCarrefour extends RecyclerView.Adapter<AdapterCarrefour.MyViewHolder> {

    private List<carrefourProducts> product;
    private Context context;

    public AdapterCarrefour(List<carrefourProducts> products, Context context) {
        this.product = products;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.product_name.setText(product.get(position).getProduct_name());
        holder.link.setText(product.get(position).getLink());
        holder.price.setText("Price: "+product.get(position).getPrice()+" €");
        holder.price_per_kg.setText("Price per kg/l/unit: "+product.get(position).getPrice_per_kg());

    }
    @Override
    public int getItemCount() {
        return product.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView product_name,link,price,price_per_kg,qty;
        public MyViewHolder(View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            link = itemView.findViewById(R.id.link);
            price = itemView.findViewById(R.id.price);
            price_per_kg = itemView.findViewById(R.id.priceperkg);

        }
    }
}
