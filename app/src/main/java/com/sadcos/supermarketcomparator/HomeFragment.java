package com.sadcos.supermarketcomparator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sadcos.supermarketcomparator.adapters.*;
import com.sadcos.supermarketcomparator.apis.ApiClient;
import com.sadcos.supermarketcomparator.apis.ApiInterfaceMercadona;
import com.sadcos.supermarketcomparator.products.*;
import com.sadcos.supermarketcomparator.searchers.searchCarrefourProducts;
import com.sadcos.supermarketcomparator.searchers.searchDiaProducts;
import com.sadcos.supermarketcomparator.searchers.searchMercadonaProducts;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    RecyclerView mainCategoryRecycler;
    MainRecyclerAdapter mainRecyclerAdapter;


    List<CategoryItem> categoryItemListMercadona = new ArrayList<>();
    List<CategoryItem> categoryItemListDia = new ArrayList<>();
    List<CategoryItem> categoryItemListCarrefour = new ArrayList<>();
    List<CategoryItem> categoryItemListAllSupermarkets = new ArrayList<>();
    List<AllCategory> allCategoryList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        generateData();
        mainCategoryRecycler = view.findViewById(R.id.main_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mainCategoryRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(getActivity(), allCategoryList);
        mainCategoryRecycler.setAdapter(mainRecyclerAdapter);
        return view;
    }

    private void generateData(){
        for(mercadonaProducts product : searchMercadonaProducts.mercadonaProducts){
            categoryItemListMercadona.add(new CategoryItem(product.getProduct_name(),product.getLink(),product.getPrice(),"Price Per kg/l/unit: No Data","Mercadona"));
        }
        for(diaProducts product : searchDiaProducts.diaProducts){
            categoryItemListDia.add(new CategoryItem(product.getProduct_name(),product.getLink(),Double.parseDouble(product.getPrice()),"Price Per kg/l/unit: "+product.getPrice_per_kg(),"Dia"));
        }
        for(carrefourProducts product : searchCarrefourProducts.carrefourProducts){
            categoryItemListCarrefour.add(new CategoryItem(product.getProduct_name(),product.getLink(),Double.parseDouble(product.getPrice()),"Price Per kg/l/unit: "+ product.getPrice_per_kg(),"Carrefour"));
        }
        categoryItemListAllSupermarkets.addAll(categoryItemListMercadona);
        categoryItemListAllSupermarkets.addAll(categoryItemListDia);
        categoryItemListAllSupermarkets.addAll(categoryItemListCarrefour);
        allCategoryList.add(new AllCategory("Productos que te gustarán",categoryItemListAllSupermarkets));
        allCategoryList.add(new AllCategory("Recomendaciones Mercadona",categoryItemListMercadona));
        allCategoryList.add(new AllCategory("Recomendaciones Dia",categoryItemListDia));
        allCategoryList.add(new AllCategory("Recomendaciones Carrefour",categoryItemListCarrefour));
    }
}