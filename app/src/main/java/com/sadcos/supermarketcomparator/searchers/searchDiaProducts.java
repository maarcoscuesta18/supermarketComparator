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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sadcos.supermarketcomparator.ItemDetail;
import com.sadcos.supermarketcomparator.adapters.AdapterCarrefour;
import com.sadcos.supermarketcomparator.adapters.AdapterDia;
import com.sadcos.supermarketcomparator.apis.ApiClient;
import com.sadcos.supermarketcomparator.apis.ApiInterfaceDia;
import com.sadcos.supermarketcomparator.R;
import com.sadcos.supermarketcomparator.products.carrefourProducts;
import com.sadcos.supermarketcomparator.products.diaProducts;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class searchDiaProducts extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<com.sadcos.supermarketcomparator.products.diaProducts> diaProducts;
    private AdapterDia adapterDia;
    private ApiInterfaceDia apiInterfaceDia;
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
        fetchContact("dia_products", "");
        return vista;
    }

    public void fetchContact(String type, String key){

        apiInterfaceDia = ApiClient.getApiClient().create(ApiInterfaceDia.class);

        Call<List<diaProducts>> call = apiInterfaceDia.getProduct(type, key);
        call.enqueue(new Callback<List<diaProducts>>() {
            @Override
            public void onResponse(Call<List<diaProducts>> call, Response<List<diaProducts>> response) {
                progressBar.setVisibility(View.GONE);
                diaProducts = response.body();
                adapterDia = new AdapterDia(diaProducts, getContext(),new AdapterDia.OnItemClickListener() {
                    @Override
                    public void onItemClick(diaProducts item) {
                        moveToDescription(item);
                    }
                });
                recyclerView.setAdapter(adapterDia);
                adapterDia.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<diaProducts>> call, Throwable t) {
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
    public void moveToDescription(diaProducts item){
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