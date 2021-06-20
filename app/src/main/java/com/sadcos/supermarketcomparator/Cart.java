package com.sadcos.supermarketcomparator;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;

import com.sadcos.supermarketcomparator.adapters.supermercadosAdapters.AdapterAlcampo;
import com.sadcos.supermarketcomparator.adapters.supermercadosAdapters.AdapterCarrefour;
import com.sadcos.supermarketcomparator.adapters.supermercadosAdapters.AdapterDia;
import com.sadcos.supermarketcomparator.adapters.supermercadosAdapters.AdapterMercadona;
import com.sadcos.supermarketcomparator.ui.main.SectionsPagerAdapter;
import com.sadcos.supermarketcomparator.ui.main.fragmentsCart.alcampoFragmentCart;
import com.sadcos.supermarketcomparator.ui.main.fragmentsCart.carrefourFragmentCart;
import com.sadcos.supermarketcomparator.ui.main.fragmentsCart.diaFragmentCart;
import com.sadcos.supermarketcomparator.ui.main.fragmentsCart.mercadonaFragmentCart;

import static com.sadcos.supermarketcomparator.MainActivity.mCartItemCount;
import static com.sadcos.supermarketcomparator.MainActivity.setupBadge;
import static com.sadcos.supermarketcomparator.MainActivity.textCartItemCount;

public class Cart extends AppCompatActivity implements mercadonaFragmentCart.OnFragmentInteractionListener,
        diaFragmentCart.OnFragmentInteractionListener, carrefourFragmentCart.OnFragmentInteractionListener,
        alcampoFragmentCart.OnFragmentInteractionListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        setupBadge();
        ImageView goBack = findViewById(R.id.goback);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}