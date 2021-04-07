package com.sadcos.supermarketcomparator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by haerul on 17/03/18.
 */

public class AdapterMercadona extends RecyclerView.Adapter<AdapterMercadona.MyViewHolder> {

    private List<mercadonaProducts> product;
    private Context context;

    public AdapterMercadona(List<mercadonaProducts> products, Context context) {
        this.product = products;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemmercadona, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.product_name.setText(product.get(position).getProduct_name());
        holder.link.setText(product.get(position).getLink());
        holder.price.setText("Price: "+product.get(position).getPrice()+" €");
    }
    @Override
    public int getItemCount() {
        return product.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView product_name,link,price;
        public MyViewHolder(View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            link = itemView.findViewById(R.id.link);
            price = itemView.findViewById(R.id.price);
        }
    }
}
