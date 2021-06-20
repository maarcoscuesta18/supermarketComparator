package com.sadcos.supermarketcomparator;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sadcos.supermarketcomparator.adapters.comparatorAdapters.CategoryItemRecyclerAdapter;
import com.sadcos.supermarketcomparator.adapters.comparatorAdapters.ItemComparatorRecyclerAdapter;
import com.sadcos.supermarketcomparator.adapters.comparatorAdapters.MainRecyclerAdapter;
import com.sadcos.supermarketcomparator.adapters.supermercadosAdapters.AdapterAlcampo;
import com.sadcos.supermarketcomparator.apis.ApiClient;
import com.sadcos.supermarketcomparator.apis.ApiInterfaceAlcampo;
import com.sadcos.supermarketcomparator.products.*;
import com.sadcos.supermarketcomparator.searchers.searchAlcampoProducts;
import com.sadcos.supermarketcomparator.searchers.searchCarrefourProducts;
import com.sadcos.supermarketcomparator.searchers.searchDiaProducts;
import com.sadcos.supermarketcomparator.searchers.searchMercadonaProducts;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sadcos.supermarketcomparator.login.gestionActivity.comparatorProducts;

public class HomeFragment extends Fragment {
    RecyclerView mainCategoryRecycler,itemMainComparatorRecyclerAdapter;
    MainRecyclerAdapter mainRecyclerAdapter;
    ItemComparatorRecyclerAdapter itemComparatorRecyclerAdapter;

    private List<stringPriceProducts> alcampoProducts = new ArrayList<>();
    private CategoryItemRecyclerAdapter adapterAlcampo;
    private ApiInterfaceAlcampo apiInterfaceAlcampo;

    public static List<CategoryItem> categoryItemListMercadona = new ArrayList<>();
    public static List<CategoryItem> categoryItemListDia = new ArrayList<>();
    public static List<CategoryItem> categoryItemListCarrefour = new ArrayList<>();
    public static List<CategoryItem> categoryItemListAlcampo = new ArrayList<>();
    public List<CategoryItem> categoryItemListAllSupermarkets = new ArrayList<>();
    public List<AllCategory> allCategoryList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        generateData();
        saveCart(view);
        //comparator
        itemMainComparatorRecyclerAdapter = view.findViewById(R.id.item_ComparatorRecycler);
        RecyclerView.LayoutManager layoutManagerComparator = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false);
        itemMainComparatorRecyclerAdapter.setLayoutManager(layoutManagerComparator);
        itemComparatorRecyclerAdapter = new ItemComparatorRecyclerAdapter(getActivity(), comparatorProducts);
        itemMainComparatorRecyclerAdapter.setAdapter(itemComparatorRecyclerAdapter);


        //recommended
        mainCategoryRecycler = view.findViewById(R.id.main_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mainCategoryRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(getActivity(), allCategoryList,false);
        mainCategoryRecycler.setAdapter(mainRecyclerAdapter);
        return view;
    }

    private void generateData(){
        if(categoryItemListMercadona.isEmpty()){
            for(mercadonaProducts product : searchMercadonaProducts.mercadonaProducts){
                categoryItemListMercadona.add(new CategoryItem(product.getProduct_name(),product.getLink(),product.getPrice(),"Price Per kg/l/unit: No Data","Mercadona"));
            }
        }
        if(categoryItemListDia.isEmpty()){
            for(stringPriceProducts product : searchDiaProducts.diaProducts){
                categoryItemListDia.add(new CategoryItem(product.getProduct_name(),product.getLink(),Double.parseDouble(product.getPrice()),"Price Per kg/l/unit: "+product.getPrice_per_kg(),"Dia"));
            }
        }
        if(categoryItemListCarrefour.isEmpty()){
            for(stringPriceProducts product : searchCarrefourProducts.carrefourProducts){
                categoryItemListCarrefour.add(new CategoryItem(product.getProduct_name(),product.getLink(),Double.parseDouble(product.getPrice()),"Price Per kg/l/unit: "+ product.getPrice_per_kg(),"Carrefour"));
            }
        }
        if(categoryItemListAlcampo.isEmpty()){
            for(stringPriceProducts product : searchAlcampoProducts.alcampoProducts){
                categoryItemListAlcampo.add(new CategoryItem(product.getProduct_name(),product.getLink(),Double.parseDouble(product.getPrice()),"Price Per kg/l/unit: "+ product.getPrice_per_kg(),"Alcampo"));
            }
        }
        categoryItemListAllSupermarkets.addAll(categoryItemListMercadona);
        categoryItemListAllSupermarkets.addAll(categoryItemListDia);
        categoryItemListAllSupermarkets.addAll(categoryItemListCarrefour);
        categoryItemListAllSupermarkets.addAll(categoryItemListAlcampo);
        allCategoryList.add(new AllCategory("Productos que te gustarán",categoryItemListAllSupermarkets));
        allCategoryList.add(new AllCategory("Recomendaciones Mercadona",categoryItemListMercadona));
        allCategoryList.add(new AllCategory("Recomendaciones Dia",categoryItemListDia));
        allCategoryList.add(new AllCategory("Recomendaciones Carrefour",categoryItemListCarrefour));
        allCategoryList.add(new AllCategory("Recomendaciones Alcampo",categoryItemListAlcampo));
    }
    public void saveCart(View v){
        SharedPreferences cartPreferences=v.getContext().getSharedPreferences("cartPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = cartPreferences.edit();
        Gson gson = new Gson();
        String jsonMercadona = gson.toJson(categoryItemListMercadona);
        String jsonDia = gson.toJson(categoryItemListDia);
        String jsonCarrefour = gson.toJson(categoryItemListCarrefour);
        String jsonAlcampo = gson.toJson(categoryItemListAlcampo);
        String jsonSupermarkets = gson.toJson(categoryItemListAllSupermarkets);
        editor.putString("cartMercadonaRecommended", jsonMercadona);
        editor.putString("cartAlcampoRecommended", jsonAlcampo);
        editor.putString("cartDiaRecommended", jsonDia);
        editor.putString("cartCarrefourRecommended", jsonCarrefour);
        editor.putString("cartSupermarketsRecommended", jsonSupermarkets);
        editor.apply();
    }
}