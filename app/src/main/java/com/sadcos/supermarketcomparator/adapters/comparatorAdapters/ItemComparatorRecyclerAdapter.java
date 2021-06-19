package com.sadcos.supermarketcomparator.adapters.comparatorAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.supermarket.setText("Supermercado: "+categoryItemList.get(position).getSupermarket());
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

            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }

    public static final class CategoryItemViewHolder extends RecyclerView.ViewHolder{

        TextView product_name,price,price_per_kg,supermarket;
        ImageView imglink;
        public CategoryItemViewHolder(@NonNull View itemView) {
            super(itemView);

            product_name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.price);
            price_per_kg = itemView.findViewById(R.id.priceperkg);
            supermarket = itemView.findViewById(R.id.supermarket);
            imglink = itemView.findViewById(R.id.circleView);
        }
    }
}
