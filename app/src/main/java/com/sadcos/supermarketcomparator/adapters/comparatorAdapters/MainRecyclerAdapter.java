package com.sadcos.supermarketcomparator.adapters.comparatorAdapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sadcos.supermarketcomparator.R;
import com.sadcos.supermarketcomparator.adapters.supermercadosAdapters.AdapterAlcampo;
import com.sadcos.supermarketcomparator.apis.ApiClient;
import com.sadcos.supermarketcomparator.apis.ApiInterfaceAlcampo;
import com.sadcos.supermarketcomparator.products.AllCategory;
import com.sadcos.supermarketcomparator.products.CategoryItem;
import com.sadcos.supermarketcomparator.products.stringPriceProducts;

import java.util.ArrayList;
import java.util.Collections;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {

    private Context context;
    private List<AllCategory> allCategoryList;
    private List<AllCategory> allCategoryListOriginal;
    private boolean goCompare;
    public MainRecyclerAdapter(Context context, List<AllCategory> allCategoryList,boolean goCompare) {
        this.context = context;
        this.allCategoryList = allCategoryList;
        this.goCompare = goCompare;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(goCompare){
            allCategoryListOriginal = allCategoryList;
            return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.main_comparator_recycler_row_item, parent, false));
        }else {
            return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recycler_row_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position2) {
        holder.categoryTitle.setText(allCategoryList.get(position2).getCategoryTitle());
        setCatItemRecycler(holder.itemRecycler, allCategoryList.get(position2).getCategoryItemList(),goCompare);
        holder.filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Collections.shuffle(allCategoryList.get(position2).getCategoryItemList());
                        notifyItemChanged(position2);
                        break;
                    case 1:
                        allCategoryList.get(position2).getCategoryItemList().sort(CategoryItem.ProductPriceDownCommparator);
                        notifyItemChanged(position2);
                        break;
                    case 2:
                        allCategoryList.get(position2).getCategoryItemList().sort(CategoryItem.ProductPriceUpCommparator);
                        notifyItemChanged(position2);
                        break;
                    case 3:
                        allCategoryList.get(position2).getCategoryItemList().sort(CategoryItem.ProductNameZACommparator);
                        notifyItemChanged(position2);
                        break;
                    case 4:
                        allCategoryList.get(position2).getCategoryItemList().sort(CategoryItem.ProductNameAZCommparator);
                        notifyItemChanged(position2);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(goCompare){
            holder.resetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    allCategoryList.get(position2).setCategoryItemList(allCategoryListOriginal.get(position2).getCategoryItemList());
                    notifyItemChanged(position2);
                }
            });
            holder.searcher.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    notifyItemChanged(position2);
                    holder.searcher.setQuery("",false);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if(newText==null){
                        allCategoryList.get(position2).setCategoryItemList(allCategoryListOriginal.get(position2).getCategoryItemList());
                    }else{
                        List<CategoryItem> matches = allCategoryList.get(position2).getCategoryItemList().stream().filter(it -> it.getProduct_name().toLowerCase().contains(newText.toLowerCase())).collect(Collectors.toList());
                        if(!matches.isEmpty()){
                            allCategoryList.get(position2).setCategoryItemList(matches);
                        }
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return allCategoryList.size();
    }


    public static final class MainViewHolder extends RecyclerView.ViewHolder{

        TextView categoryTitle;
        RecyclerView itemRecycler;
        Spinner filter;
        SearchView searcher;
        Button resetButton;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.itemTitle);
            itemRecycler = itemView.findViewById(R.id.item_recycler);
            filter = itemView.findViewById(R.id.filter);
            searcher = itemView.findViewById(R.id.searchProduct);
            resetButton = itemView.findViewById(R.id.resetButton);
        }
    }

    private void setCatItemRecycler(RecyclerView recyclerView, List<CategoryItem> categoryItemList,boolean goCompare){
        CategoryItemRecyclerAdapter itemRecyclerAdapter = new CategoryItemRecyclerAdapter(context, categoryItemList,goCompare);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerView.setHorizontalScrollBarEnabled(true);
        recyclerView.setAdapter(itemRecyclerAdapter);
    }

}
