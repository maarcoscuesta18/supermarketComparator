package com.sadcos.supermarketcomparator.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sadcos.supermarketcomparator.R;
import com.sadcos.supermarketcomparator.products.AllCategory;
import com.sadcos.supermarketcomparator.products.CategoryItem;
import com.sadcos.supermarketcomparator.searchers.searchCarrefourProducts;
import com.sadcos.supermarketcomparator.searchers.searchDiaProducts;
import com.sadcos.supermarketcomparator.searchers.searchMercadonaProducts;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {

    private Context context;
    private List<AllCategory> allCategoryList;

    public MainRecyclerAdapter(Context context, List<AllCategory> allCategoryList) {
        this.context = context;
        this.allCategoryList = allCategoryList;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recycler_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.categoryTitle.setText(allCategoryList.get(position).getCategoryTitle());
        setCatItemRecycler(holder.itemRecycler, allCategoryList.get(position).getCategoryItemList());
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allCategoryList.get(position).getCategoryTitle().equals("Recomendaciones Mercadona")){
                }
                if (allCategoryList.get(position).getCategoryTitle().equals("Recomendaciones Dia")){
                }
                if (allCategoryList.get(position).getCategoryTitle().equals("Recomendaciones Carrefour")){
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return allCategoryList.size();
    }

    public static final class MainViewHolder extends RecyclerView.ViewHolder{

        TextView categoryTitle;
        RecyclerView itemRecycler;
        Button more;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.itemTitle);
            itemRecycler = itemView.findViewById(R.id.item_recycler);
            more = itemView.findViewById(R.id.btnMore);
        }
    }

    private void setCatItemRecycler(RecyclerView recyclerView, List<CategoryItem> categoryItemList){
        CategoryItemRecyclerAdapter itemRecyclerAdapter = new CategoryItemRecyclerAdapter(context, categoryItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(itemRecyclerAdapter);
    }
}
