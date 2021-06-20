package com.sadcos.supermarketcomparator.searchers;



import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sadcos.supermarketcomparator.ItemDetail;
import com.sadcos.supermarketcomparator.adapters.supermercadosAdapters.AdapterDia;
import com.sadcos.supermarketcomparator.apis.ApiClient;
import com.sadcos.supermarketcomparator.apis.ApiInterfaceDia;
import com.sadcos.supermarketcomparator.R;
import com.sadcos.supermarketcomparator.products.stringPriceProducts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sadcos.supermarketcomparator.MainActivity.setupBadge;

public class searchDiaProducts extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public static List<stringPriceProducts> diaProducts = new ArrayList<>();
    private AdapterDia adapterDia;
    private ApiInterfaceDia apiInterfaceDia;
    ProgressBar progressBar;
    Spinner filter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.activity_search_products, container, false);
        progressBar = vista.findViewById(R.id.prograss);
        recyclerView = vista.findViewById(R.id.recyclerView);
        filter = vista.findViewById(R.id.filter);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        setupBadge();
        fetchContact("dia_products", "");
        return vista;
    }

    public void fetchContact(String type, String key){

        apiInterfaceDia = ApiClient.getApiClient().create(ApiInterfaceDia.class);

        Call<List<stringPriceProducts>> call = apiInterfaceDia.getProduct(type, key);
        call.enqueue(new Callback<List<stringPriceProducts>>() {
            @Override
            public void onResponse(Call<List<stringPriceProducts>> call, Response<List<stringPriceProducts>> response) {
                progressBar.setVisibility(View.GONE);
                diaProducts = response.body();
                adapterDia = new AdapterDia(diaProducts, getContext(),new AdapterDia.OnItemClickListener() {
                    @Override
                    public void onItemClick(stringPriceProducts item) {
                        moveToDescription(item);
                    }
                });
                recyclerView.setAdapter(adapterDia);
                adapterDia.notifyDataSetChanged();
                filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (position){
                            case 0:
                                Collections.shuffle(diaProducts);
                                adapterDia.notifyDataSetChanged();
                                break;
                            case 1:
                                Collections.sort(diaProducts, com.sadcos.supermarketcomparator.products.stringPriceProducts.ProductPriceDownCommparator);
                                adapterDia.notifyDataSetChanged();
                                break;
                            case 2:
                                Collections.sort(diaProducts, com.sadcos.supermarketcomparator.products.stringPriceProducts.ProductPriceUpCommparator);
                                adapterDia.notifyDataSetChanged();
                                break;
                            case 3:
                                Collections.sort(diaProducts, com.sadcos.supermarketcomparator.products.stringPriceProducts.ProductNameZACommparator);
                                adapterDia.notifyDataSetChanged();
                                break;
                            case 4:
                                Collections.sort(diaProducts, com.sadcos.supermarketcomparator.products.stringPriceProducts.ProductNameAZCommparator);
                                adapterDia.notifyDataSetChanged();
                                break;
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<stringPriceProducts>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        inflater.inflate(R.menu.searcher_menu, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchContact("dia_products", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchContact("dia_products", newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);
    }
    public void moveToDescription(stringPriceProducts item){
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getContext(), ItemDetail.class);
        bundle.putString("itemName", item.getCartproduct_name());
        bundle.putString("itemPrice", item.getCartprice().toString());
        bundle.putString("itemLink", item.getCartlink());
        bundle.putString("itemPricePerKg", item.getCartpriceperkg());
        bundle.putString("supermarketType","dia");
        intent.putExtras(bundle);
        startActivity(intent);
    }
}