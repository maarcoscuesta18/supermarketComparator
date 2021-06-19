package com.sadcos.supermarketcomparator.adapters.comparatorAdapters;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.sadcos.supermarketcomparator.comparator.ItemComparator;

import com.sadcos.supermarketcomparator.R;
import com.sadcos.supermarketcomparator.login.gestionActivity;
import com.sadcos.supermarketcomparator.products.CategoryItem;


import java.util.List;

public class ItemComparatorRecyclerAdapter extends RecyclerView.Adapter<ItemComparatorRecyclerAdapter.CategoryItemViewHolder> {

    private Context context;
    private List<CategoryItem> categoryItemList;


    public ItemComparatorRecyclerAdapter(Context context, List<CategoryItem> categoryItemList) {
        this.context = context;
        this.categoryItemList = categoryItemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryItemViewHolder(LayoutInflater.from(context).inflate(R.layout.itemcomparator, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemViewHolder holder, int position) {

        holder.product_name.setText(gestionActivity.comparatorProducts.get(position).getProduct_name());
        holder.price.setText("Precio: "+String.valueOf(gestionActivity.comparatorProducts.get(position).getPrice()+" €"));
        holder.price_per_kg.setText(gestionActivity.comparatorProducts.get(position).getPrice_per_kg());
        holder.supermarket.setText("Supermercado: "+gestionActivity.comparatorProducts.get(position).getSupermarket());
        switch (categoryItemList.get(position).getSupermarket()){
            case "Mercadona":
                holder.imglink.setImageResource(R.drawable.mercadona);
                break;
            case "Dia":
                holder.imglink.setImageResource(R.drawable.dia);
                break;
            case "Carrefour":
                holder.imglink.setImageResource(R.drawable.carrefour);
                break;
            case "Alcampo":
                holder.imglink.setImageResource(R.drawable.alcampo);
                break;
        }
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemComparator.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }

    public static final class CategoryItemViewHolder extends RecyclerView.ViewHolder{

        TextView product_name,price,price_per_kg,supermarket;
        Button update;
        ImageView imglink;
        public CategoryItemViewHolder(@NonNull View itemView) {
            super(itemView);

            update = itemView.findViewById(R.id.update);
            product_name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.price);
            price_per_kg = itemView.findViewById(R.id.priceperkg);
            supermarket = itemView.findViewById(R.id.supermarket);
            imglink = itemView.findViewById(R.id.circleView);
        }
    }

    public void update(){
        CategoryItem product1 = new CategoryItem(gestionActivity.comparatorProducts.get(0).getProduct_name(), gestionActivity.comparatorProducts.get(0).getLink(), gestionActivity.comparatorProducts.get(0).getPrice(), gestionActivity.comparatorProducts.get(0).getPrice_per_kg(), gestionActivity.comparatorProducts.get(0).getSupermarket());
        CategoryItem product2 = new CategoryItem(gestionActivity.comparatorProducts.get(1).getProduct_name(), gestionActivity.comparatorProducts.get(1).getLink(), gestionActivity.comparatorProducts.get(1).getPrice(), gestionActivity.comparatorProducts.get(1).getPrice_per_kg(), gestionActivity.comparatorProducts.get(1).getSupermarket());
        gestionActivity.comparatorProducts.set(0,product1);
        gestionActivity.comparatorProducts.set(1,product2);
    }
}
