package com.sadcos.supermarketcomparator.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sadcos.supermarketcomparator.ItemDetail;
import com.sadcos.supermarketcomparator.R;
import com.sadcos.supermarketcomparator.products.CategoryItem;

import java.util.List;

public class ItemComparatorRecyclerAdapter extends RecyclerView.Adapter<ItemComparatorRecyclerAdapter.CategoryItemViewHolder> {

    private Context context;
    private List<CategoryItem> categoryItemList;

    public ItemComparatorRecyclerAdapter(Context context, List<CategoryItem> categoryItemList) {
        this.context = context;
        this.categoryItemList = categoryItemList;
    }

    @NonNull
    @Override
    public CategoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryItemViewHolder(LayoutInflater.from(context).inflate(R.layout.itemcomparator, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemViewHolder holder, int position) {
        holder.product_name.setText(categoryItemList.get(position).getProduct_name());
        holder.price.setText("Precio: "+String.valueOf(categoryItemList.get(position).getPrice()+" €"));
        holder.price_per_kg.setText(categoryItemList.get(position).getPrice_per_kg());
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                try{
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
                        case "Alcampo":
                            bundle.putString("supermarketType","alcampo");
                            break;
                    }
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }catch(Exception e){
                    bundle.putString("itemPricePerKg", "no data");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }

    public static final class CategoryItemViewHolder extends RecyclerView.ViewHolder{

        TextView product_name,price,price_per_kg;
        ImageView imglink;
        public CategoryItemViewHolder(@NonNull View itemView) {
            super(itemView);

            product_name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.price);
            price_per_kg = itemView.findViewById(R.id.priceperkg);
            imglink = itemView.findViewById(R.id.circleView);
        }
    }
}
