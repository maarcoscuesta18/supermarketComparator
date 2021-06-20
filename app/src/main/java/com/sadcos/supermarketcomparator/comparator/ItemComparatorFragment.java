package com.sadcos.supermarketcomparator.comparator;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sadcos.supermarketcomparator.HomeFragment;
import com.sadcos.supermarketcomparator.R;
import com.sadcos.supermarketcomparator.adapters.comparatorAdapters.CategoryItemRecyclerAdapter;
import com.sadcos.supermarketcomparator.adapters.comparatorAdapters.MainRecyclerAdapter;
import com.sadcos.supermarketcomparator.products.AllCategory;
import java.util.ArrayList;
import java.util.List;

public class ItemComparatorFragment extends Fragment{
    RecyclerView mainCategoryRecycler;
    MainRecyclerAdapter mainRecyclerAdapter;
    CategoryItemRecyclerAdapter categoryItemRecyclerAdapter;
    public List<AllCategory> allCategoryList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_comparator_adapter, container, false);

        allCategoryList.add(new AllCategory("Productos Mercadona", HomeFragment.categoryItemListMercadona));
        allCategoryList.add(new AllCategory("Productos Dia",HomeFragment.categoryItemListDia));
        allCategoryList.add(new AllCategory("Productos Carrefour",HomeFragment.categoryItemListCarrefour));
        allCategoryList.add(new AllCategory("Productos Alcampo",HomeFragment.categoryItemListAlcampo));

        mainCategoryRecycler = view.findViewById(R.id.mainComparator_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mainCategoryRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(getActivity(), allCategoryList,true);
        mainCategoryRecycler.setAdapter(mainRecyclerAdapter);

        return view;
    }
}