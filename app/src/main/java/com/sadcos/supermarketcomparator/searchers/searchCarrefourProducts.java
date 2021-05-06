package com.sadcos.supermarketcomparator.searchers;

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

import com.sadcos.supermarketcomparator.adapters.AdapterCarrefour;
import com.sadcos.supermarketcomparator.apis.ApiClient;
import com.sadcos.supermarketcomparator.apis.ApiInterfaceCarrefour;
import com.sadcos.supermarketcomparator.R;
import com.sadcos.supermarketcomparator.products.carrefourProducts;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class searchCarrefourProducts extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<com.sadcos.supermarketcomparator.products.carrefourProducts> carrefourProducts;
    private AdapterCarrefour adapterCarrefour;
    private ApiInterfaceCarrefour apiInterfaceCarrefour;
    ProgressBar progressBar;
    TextView search;
    String[] item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);
        this.setTitle("Supermercado Carrefour");
        progressBar = findViewById(R.id.prograss);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        fetchContact("carrefour_products", "");

    }

    public void fetchContact(String type, String key){

        apiInterfaceCarrefour = ApiClient.getApiClient().create(ApiInterfaceCarrefour.class);

        Call<List<carrefourProducts>> call = apiInterfaceCarrefour.getProduct(type, key);
        call.enqueue(new Callback<List<carrefourProducts>>() {
            @Override
            public void onResponse(Call<List<carrefourProducts>> call, Response<List<carrefourProducts>> response) {
                progressBar.setVisibility(View.GONE);
                carrefourProducts = response.body();
                adapterCarrefour = new AdapterCarrefour(carrefourProducts, searchCarrefourProducts.this);
                recyclerView.setAdapter(adapterCarrefour);
                adapterCarrefour.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<carrefourProducts>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(searchCarrefourProducts.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searcher_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchContact("carrefour_products", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchContact("carrefour_products", newText);
                return false;
            }
        });
        return true;
    }
}