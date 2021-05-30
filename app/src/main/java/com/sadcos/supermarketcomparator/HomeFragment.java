package com.sadcos.supermarketcomparator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sadcos.supermarketcomparator.adapters.AdapterCarrefour;
import com.sadcos.supermarketcomparator.adapters.AdapterDia;
import com.sadcos.supermarketcomparator.adapters.AdapterMercadona;
import com.sadcos.supermarketcomparator.adapters.MainRecyclerAdapter;
import com.sadcos.supermarketcomparator.products.AllCategory;
import com.sadcos.supermarketcomparator.products.CategoryItem;
import com.sadcos.supermarketcomparator.products.carrefourProducts;
import com.sadcos.supermarketcomparator.products.diaProducts;
import com.sadcos.supermarketcomparator.products.mercadonaProducts;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView mainCategoryRecycler;
    MainRecyclerAdapter mainRecyclerAdapter;

    List<CategoryItem> categoryItemListMercadona = new ArrayList<>();
    List<CategoryItem> categoryItemListDia = new ArrayList<>();
    List<CategoryItem> categoryItemListCarrefour = new ArrayList<>();
    List<AllCategory> allCategoryList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerViewRecommendedProducts = (RecyclerView) view.findViewById(R.id.recyclerView);

        generateData();

        mainCategoryRecycler = view.findViewById(R.id.main_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mainCategoryRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(getActivity(), allCategoryList);
        mainCategoryRecycler.setAdapter(mainRecyclerAdapter);

        return view;
    }
    private void generateData(){
        for(mercadonaProducts product : AdapterMercadona.mercadonaCartProducts){
            categoryItemListMercadona.add(new CategoryItem(product.getCartproduct_name(), product.getCartlink(),product.getCartprice(),"Price Per kg/l/unit: No Data","Mercadona"));
        }
        for(diaProducts product : AdapterDia.diaCartProducts){
            categoryItemListDia.add(new CategoryItem(product.getCartproduct_name(), product.getCartlink(),product.getCartprice(),product.getCartpriceperkg(),"Dia"));
        }
        for(carrefourProducts product : AdapterCarrefour.carrefourCartProducts){
            categoryItemListCarrefour.add(new CategoryItem(product.getCartproduct_name(), product.getCartlink(),product.getCartprice(),product.getCartpriceperkg(),"Carrefour"));
        }
        allCategoryList.add(new AllCategory("Recomendaciones Mercadona",categoryItemListMercadona));
        allCategoryList.add(new AllCategory("Recomendaciones Dia",categoryItemListDia));
        allCategoryList.add(new AllCategory("Recomendaciones Carrefour",categoryItemListCarrefour));
    }

}