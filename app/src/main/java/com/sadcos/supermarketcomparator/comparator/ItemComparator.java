package com.sadcos.supermarketcomparator.comparator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.sadcos.supermarketcomparator.R;
import com.sadcos.supermarketcomparator.adapters.comparatorAdapters.CategoryItemRecyclerAdapter;


public class ItemComparator extends AppCompatActivity {
    CategoryItemRecyclerAdapter categoryItemRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_comparator);

        ImageView goBack = findViewById(R.id.goback);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.itemComparatorActivity,new ItemComparatorFragment()).commit();
    }
}