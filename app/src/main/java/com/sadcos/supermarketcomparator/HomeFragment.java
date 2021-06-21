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
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sadcos.supermarketcomparator.adapters.comparatorAdapters.CategoryItemRecyclerAdapter;
import com.sadcos.supermarketcomparator.adapters.comparatorAdapters.ItemComparatorRecyclerAdapter;
import com.sadcos.supermarketcomparator.adapters.comparatorAdapters.MainRecyclerAdapter;
import com.sadcos.supermarketcomparator.adapters.supermercadosAdapters.AdapterAlcampo;
import com.sadcos.supermarketcomparator.apis.ApiClient;
import com.sadcos.supermarketcomparator.apis.ApiInterfaceAlcampo;
import com.sadcos.supermarketcomparator.apis.ApiInterfaceCarrefour;
import com.sadcos.supermarketcomparator.apis.ApiInterfaceDia;
import com.sadcos.supermarketcomparator.apis.ApiInterfaceMercadona;
import com.sadcos.supermarketcomparator.products.*;
import com.sadcos.supermarketcomparator.searchers.searchAlcampoProducts;
import com.sadcos.supermarketcomparator.searchers.searchCarrefourProducts;
import com.sadcos.supermarketcomparator.searchers.searchDiaProducts;
import com.sadcos.supermarketcomparator.searchers.searchMercadonaProducts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.sadcos.supermarketcomparator.MainActivity.setupBadge;
import static com.sadcos.supermarketcomparator.login.gestionActivity.comparatorProducts;

public class HomeFragment extends Fragment {
    RecyclerView mainCategoryRecycler,itemMainComparatorRecyclerAdapter;
    MainRecyclerAdapter mainRecyclerAdapter;
    ItemComparatorRecyclerAdapter itemComparatorRecyclerAdapter;

    private List<stringPriceProducts> alcampoRecommendedProducts = new ArrayList<>();
    private List<mercadonaProducts> mercadonaRecommendedProducts = new ArrayList<>();
    private List<stringPriceProducts> diaRecommendedProducts = new ArrayList<>();
    private List<stringPriceProducts> carrefourRecommendedProducts = new ArrayList<>();
    private ApiInterfaceAlcampo apiInterfaceAlcampo;
    private ApiInterfaceMercadona apiInterfaceMercadona;
    private ApiInterfaceDia apiInterfaceDia;
    private ApiInterfaceCarrefour apiInterfaceCarrefour;
    Call<List<stringPriceProducts>> call;
    Call<List<mercadonaProducts>> callMercadona;

    public static List<CategoryItem> categoryItemListMercadona = new ArrayList<>();
    public static List<CategoryItem> categoryItemListDia = new ArrayList<>();
    public static List<CategoryItem> categoryItemListCarrefour = new ArrayList<>();
    public static List<CategoryItem> categoryItemListAlcampo = new ArrayList<>();
    public static List<CategoryItem> categoryItemListAllSupermarkets = new ArrayList<>();
    public List<AllCategory> allCategoryList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setupBadge();
        generateData();
        saveCart(view);

        //comparator
        itemMainComparatorRecyclerAdapter = view.findViewById(R.id.item_ComparatorRecycler);
        RecyclerView.LayoutManager layoutManagerComparator = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false);
        itemMainComparatorRecyclerAdapter.setLayoutManager(layoutManagerComparator);
        itemComparatorRecyclerAdapter = new ItemComparatorRecyclerAdapter(getActivity(), comparatorProducts);
        itemMainComparatorRecyclerAdapter.setAdapter(itemComparatorRecyclerAdapter);


        mainCategoryRecycler = view.findViewById(R.id.main_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mainCategoryRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(getActivity(), allCategoryList,false);
        mainCategoryRecycler.setAdapter(mainRecyclerAdapter);

        //recommended
        return view;
    }


    private void generateData(){
        if(categoryItemListMercadona.isEmpty()){
            fetchContact("mercadona_products","",diaRecommendedProducts,categoryItemListMercadona,"Mercadona");
            /*for(mercadonaProducts product : searchMercadonaProducts.mercadonaProducts){
                categoryItemListMercadona.add(new CategoryItem(product.getProduct_name(),product.getLink(),product.getPrice(),"Price Per kg/l/unit: No Data","Mercadona"));
            }*/
        }
        if(categoryItemListDia.isEmpty()){
            fetchContact("dia_products","",diaRecommendedProducts,categoryItemListDia,"Dia");
            /*for(stringPriceProducts product : searchDiaProducts.diaProducts){
                categoryItemListDia.add(new CategoryItem(product.getProduct_name(),product.getLink(),Double.parseDouble(product.getPrice()),"Price Per kg/l/unit: "+product.getPrice_per_kg(),"Dia"));
            }*/
        }
        if(categoryItemListCarrefour.isEmpty()){
            fetchContact("carrefour_products","",carrefourRecommendedProducts,categoryItemListCarrefour,"Carrefour");
            /*for(stringPriceProducts product : searchCarrefourProducts.carrefourProducts){
                categoryItemListCarrefour.add(new CategoryItem(product.getProduct_name(),product.getLink(),Double.parseDouble(product.getPrice()),"Price Per kg/l/unit: "+ product.getPrice_per_kg(),"Carrefour"));
            }*/
        }
        if(categoryItemListAlcampo.isEmpty()){
            fetchContact("alcampo_products","",alcampoRecommendedProducts,categoryItemListAlcampo,"Alcampo");
            /*for(stringPriceProducts product : searchAlcampoProducts.alcampoProducts){
                categoryItemListAlcampo.add(new CategoryItem(product.getProduct_name(),product.getLink(),Double.parseDouble(product.getPrice()),"Price Per kg/l/unit: "+ product.getPrice_per_kg(),"Alcampo"));
            }*/
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
    public void fetchContact(String type, String key,List<stringPriceProducts> productsList,List<CategoryItem> productsRecommendedList,String supermercado){
        apiInterfaceAlcampo = ApiClient.getApiClient().create(ApiInterfaceAlcampo.class);
        apiInterfaceMercadona = ApiClient.getApiClient().create(ApiInterfaceMercadona.class);
        apiInterfaceDia = ApiClient.getApiClient().create(ApiInterfaceDia.class);
        apiInterfaceCarrefour = ApiClient.getApiClient().create(ApiInterfaceCarrefour.class);
        switch (supermercado){
            case "Dia":
                call = apiInterfaceDia.getProduct(type, key);
                break;
            case "Carrefour":
                call = apiInterfaceCarrefour.getProduct(type, key);
                break;
            case "Alcampo":
                call = apiInterfaceAlcampo.getProduct(type, key);
                break;
        }
        if(supermercado.equals("Mercadona")){
            callMercadona = apiInterfaceMercadona.getProduct(type, key);
            callMercadona.enqueue(new Callback<List<mercadonaProducts>>() {
                @Override
                public void onResponse(Call<List<mercadonaProducts>> call, Response<List<mercadonaProducts>> response) {
                    mercadonaRecommendedProducts.clear();
                    mercadonaRecommendedProducts.addAll(response.body());
                    for(mercadonaProducts product : mercadonaRecommendedProducts){
                        productsRecommendedList.add(new CategoryItem(product.getProduct_name(),product.getLink(),product.getPrice(),"Price Per kg/l/unit: no data",supermercado));
                    }
                }

                @Override
                public void onFailure(Call<List<mercadonaProducts>> call, Throwable t) {
                    Toast.makeText(getContext(), "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
                }
            });
        } else{
            call.enqueue(new Callback<List<stringPriceProducts>>() {
                @Override
                public void onResponse(Call<List<stringPriceProducts>> call, Response<List<stringPriceProducts>> response) {
                    productsList.clear();
                    productsList.addAll(response.body());
                    for(stringPriceProducts product : productsList){
                        productsRecommendedList.add(new CategoryItem(product.getProduct_name(),product.getLink(),Double.parseDouble(product.getPrice()),"Price Per kg/l/unit: "+ product.getPrice_per_kg(),supermercado));
                    }
                }

                @Override
                public void onFailure(Call<List<stringPriceProducts>> call, Throwable t) {
                    Toast.makeText(getContext(), "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
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