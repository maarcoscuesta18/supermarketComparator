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

import com.sadcos.supermarketcomparator.adapters.AdapterDia;
import com.sadcos.supermarketcomparator.apis.ApiClient;
import com.sadcos.supermarketcomparator.apis.ApiInterfaceDia;
import com.sadcos.supermarketcomparator.R;
import com.sadcos.supermarketcomparator.products.diaProducts;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class searchDiaProducts extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<com.sadcos.supermarketcomparator.products.diaProducts> diaProducts;
    private AdapterDia adapterDia;
    private ApiInterfaceDia apiInterfaceDia;
    ProgressBar progressBar;
    TextView search;
    String[] item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dia_products);

        progressBar = findViewById(R.id.prograss);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        fetchContact("dia_products", "");

    }

    public void fetchContact(String type, String key){

        apiInterfaceDia = ApiClient.getApiClient().create(ApiInterfaceDia.class);

        Call<List<diaProducts>> call = apiInterfaceDia.getProduct(type, key);
        call.enqueue(new Callback<List<diaProducts>>() {
            @Override
            public void onResponse(Call<List<diaProducts>> call, Response<List<diaProducts>> response) {
                progressBar.setVisibility(View.GONE);
                diaProducts = response.body();
                adapterDia = new AdapterDia(diaProducts, searchDiaProducts.this);
                recyclerView.setAdapter(adapterDia);
                adapterDia.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<diaProducts>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(searchDiaProducts.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dia_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
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
        return true;
    }
}