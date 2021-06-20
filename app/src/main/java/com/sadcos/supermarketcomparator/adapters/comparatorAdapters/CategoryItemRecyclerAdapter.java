package com.sadcos.supermarketcomparator.adapters.comparatorAdapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.sadcos.supermarketcomparator.ItemDetail;
import com.sadcos.supermarketcomparator.R;
import com.sadcos.supermarketcomparator.login.gestionActivity;
import com.sadcos.supermarketcomparator.products.CategoryItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryItemRecyclerAdapter extends RecyclerView.Adapter<CategoryItemRecyclerAdapter.CategoryItemViewHolder>{

    private Context context;
    private List<CategoryItem> categoryItemList;
    private boolean goCompare;
    public CategoryItemRecyclerAdapter(Context context, List<CategoryItem> categoryItemList,Boolean goCompare) {
        this.context = context;
        this.categoryItemList = categoryItemList;
        this.goCompare = goCompare;
    }

    @NonNull
    @Override
    public CategoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(goCompare){
            return new CategoryItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_to_compare, parent, false));
        } else {
            return new CategoryItemViewHolder(LayoutInflater.from(context).inflate(R.layout.suggesteditem, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemViewHolder holder, int position) {
        holder.product_name.setText(categoryItemList.get(position).getProduct_name());
        holder.price.setText("Precio: "+String.valueOf(categoryItemList.get(position).getPrice()+" €"));
        holder.price_per_kg.setText(categoryItemList.get(position).getPrice_per_kg());
        if(goCompare) {
            holder.link.setText(categoryItemList.get(position).getLink());
            holder.addtocompare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gestionActivity.comparatorProducts.set((int) (Math.random() * 2),new CategoryItem(categoryItemList.get(position).getProduct_name(),categoryItemList.get(position).getLink(),categoryItemList.get(position).getPrice(),categoryItemList.get(position).getPrice_per_kg(),categoryItemList.get(position).getSupermarket()));
                    System.out.println(gestionActivity.comparatorProducts.get(0).toString());
                    System.out.println(gestionActivity.comparatorProducts.get(1).toString());
                }
            });
        }else{
            switch (categoryItemList.get(position).getSupermarket()) {
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
        }
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
                    case "Alcampo":
                        bundle.putString("supermarketType","alcampo");
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

        TextView product_name,price,link,price_per_kg;
        ImageView imglink;
        Button addtocompare;

        public CategoryItemViewHolder(@NonNull View itemView) {
            super(itemView);

            product_name = itemView.findViewById(R.id.product_name);
            link = itemView.findViewById(R.id.link);
            price = itemView.findViewById(R.id.price);
            price_per_kg = itemView.findViewById(R.id.priceperkg);
            imglink = itemView.findViewById(R.id.circleView);
            addtocompare = itemView.findViewById(R.id.compare);
        }
    }

}
