package com.sadcos.supermarketcomparator.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

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

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position2) {
        holder.categoryTitle.setText(allCategoryList.get(position2).getCategoryTitle());
        setCatItemRecycler(holder.itemRecycler, allCategoryList.get(position2).getCategoryItemList());
        holder.filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Collections.shuffle(allCategoryList.get(position2).getCategoryItemList());
                        notifyItemChanged(position2);
                        break;
                    case 1:
                        Collections.sort(allCategoryList.get(position2).getCategoryItemList(),CategoryItem.ProductPriceDownCommparator);
                        notifyItemChanged(position2);
                        break;
                    case 2:
                        Collections.sort(allCategoryList.get(position2).getCategoryItemList(),CategoryItem.ProductPriceUpCommparator);
                        notifyItemChanged(position2);
                        break;
                    case 3:
                        Collections.sort(allCategoryList.get(position2).getCategoryItemList(),CategoryItem.ProductNameZACommparator);
                        notifyItemChanged(position2);
                        break;
                    case 4:
                        Collections.sort(allCategoryList.get(position2).getCategoryItemList(),CategoryItem.ProductNameAZCommparator);
                        notifyItemChanged(position2);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        Spinner filter;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.itemTitle);
            itemRecycler = itemView.findViewById(R.id.item_recycler);
            filter = itemView.findViewById(R.id.filter);
        }
    }

    private void setCatItemRecycler(RecyclerView recyclerView, List<CategoryItem> categoryItemList){
        CategoryItemRecyclerAdapter itemRecyclerAdapter = new CategoryItemRecyclerAdapter(context, categoryItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerView.setHorizontalScrollBarEnabled(true);
        recyclerView.setAdapter(itemRecyclerAdapter);
    }
}
