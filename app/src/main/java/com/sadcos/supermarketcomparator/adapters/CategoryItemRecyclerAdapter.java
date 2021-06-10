package com.sadcos.supermarketcomparator.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sadcos.supermarketcomparator.ItemDetail;
import com.sadcos.supermarketcomparator.R;
import com.sadcos.supermarketcomparator.products.CategoryItem;
import com.sadcos.supermarketcomparator.products.mercadonaProducts;

import java.util.Collections;
import java.util.List;

public class CategoryItemRecyclerAdapter extends RecyclerView.Adapter<CategoryItemRecyclerAdapter.CategoryItemViewHolder> {

    private Context context;
    private List<CategoryItem> categoryItemList;

    public CategoryItemRecyclerAdapter(Context context, List<CategoryItem> categoryItemList) {
        this.context = context;
        this.categoryItemList = categoryItemList;
    }

    @NonNull
    @Override
    public CategoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryItemViewHolder(LayoutInflater.from(context).inflate(R.layout.suggesteditem, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemViewHolder holder, int position) {
        holder.product_name.setText(categoryItemList.get(position).getProduct_name());
        holder.price.setText("Precio: "+String.valueOf(categoryItemList.get(position).getPrice()+" €"));
        holder.price_per_kg.setText(categoryItemList.get(position).getPrice_per_kg());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(context, ItemDetail.class);
                bundle.putString("itemName", categoryItemList.get(position).getProduct_name());
                bundle.putString("itemPrice", String.valueOf(categoryItemList.get(position).getPrice()));
                bundle.putString("itemLink", categoryItemList.get(position).getLink());
                bundle.putString("itemPricePerKg", categoryItemList.get(position).getPrice_per_kg());
                switch (categoryItemList.get(position).getSupermarket()){
                    case "Mercadona":
                        bundle.putString("supermarketType","mercadona");
                        break;
                    case "Dia":
                        bundle.putString("supermarketType","dia");
                        break;
                    case "Carrefour":
                        bundle.putString("supermarketType","carrefour");
                        break;
                }
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }

    public static final class CategoryItemViewHolder extends RecyclerView.ViewHolder{

        TextView product_name,price,price_per_kg;
        public CategoryItemViewHolder(@NonNull View itemView) {
            super(itemView);

            product_name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.price);
            price_per_kg = itemView.findViewById(R.id.priceperkg);
        }
    }
    public void saveCart(View v){
        SharedPreferences cartPreferences=v.getContext().getSharedPreferences("cartPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = cartPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(AdapterMercadona.mercadonaCartProducts);
        editor.putString("cartMercadona", json);
        editor.apply();
    }
}
