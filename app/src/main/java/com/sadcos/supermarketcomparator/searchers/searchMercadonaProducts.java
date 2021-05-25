package com.sadcos.supermarketcomparator.searchers;



import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sadcos.supermarketcomparator.ItemDetail;
import com.sadcos.supermarketcomparator.adapters.AdapterCarrefour;
import com.sadcos.supermarketcomparator.adapters.AdapterMercadona;
import com.sadcos.supermarketcomparator.apis.ApiClient;
import com.sadcos.supermarketcomparator.apis.ApiInterfaceMercadona;
import com.sadcos.supermarketcomparator.R;
import com.sadcos.supermarketcomparator.products.carrefourProducts;
import com.sadcos.supermarketcomparator.products.diaProducts;
import com.sadcos.supermarketcomparator.products.mercadonaProducts;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class searchMercadonaProducts extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<com.sadcos.supermarketcomparator.products.mercadonaProducts> mercadonaProducts;
    private AdapterMercadona adapterMercadona;
    private ApiInterfaceMercadona apiInterfaceMercadona;
    ProgressBar progressBar;
    TextView search;
    String[] item;

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
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        fetchContact("mercadona_products", "");
        return vista;
    }
    public void fetchContact(String type, String key){

        apiInterfaceMercadona = ApiClient.getApiClient().create(ApiInterfaceMercadona.class);

        Call<List<mercadonaProducts>> call = apiInterfaceMercadona.getProduct(type, key);
        call.enqueue(new Callback<List<mercadonaProducts>>() {
            @Override
            public void onResponse(Call<List<mercadonaProducts>> call, Response<List<mercadonaProducts>> response) {
                progressBar.setVisibility(View.GONE);
                mercadonaProducts = response.body();
                adapterMercadona = new AdapterMercadona(mercadonaProducts, getContext(),new AdapterMercadona.OnItemClickListener() {
                    @Override
                    public void onItemClick(mercadonaProducts item) {
                        moveToDescription(item);
                    }
                });
                recyclerView.setAdapter(adapterMercadona);
                adapterMercadona.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<mercadonaProducts>> call, Throwable t) {
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
                fetchContact("mercadona_products", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchContact("mercadona_products", newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);
    }
    public void moveToDescription(mercadonaProducts item){
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getContext(), ItemDetail.class);
        bundle.putString("itemName", item.getCartproduct_name());
        bundle.putString("itemPrice", item.getCartprice().toString());
        bundle.putString("itemLink", item.getCartlink());
        bundle.putString("itemPricePerKg", "Price per kg/unit/lb: no data");
        bundle.putString("supermarketType","mercadona");
        intent.putExtras(bundle);
        startActivity(intent);
    }
}