package com.sadcos.supermarketcomparator;



import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class searchMercadonaProducts extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<mercadonaProducts> mercadonaProducts;
    private Adapter adapter;
    private ApiInterface apiInterface;
    ProgressBar progressBar;
    TextView search;
    String[] item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_mercadona_products);

        progressBar = findViewById(R.id.prograss);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        fetchContact("mercadona_products", "");

    }

    public void fetchContact(String type, String key){

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<mercadonaProducts>> call = apiInterface.getProduct(type, key);
        call.enqueue(new Callback<List<mercadonaProducts>>() {
            @Override
            public void onResponse(Call<List<mercadonaProducts>> call, Response<List<mercadonaProducts>> response) {
                progressBar.setVisibility(View.GONE);
                mercadonaProducts = response.body();
                adapter = new Adapter(mercadonaProducts, searchMercadonaProducts.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<mercadonaProducts>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(searchMercadonaProducts.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mercadona_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
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
        return true;
    }
}