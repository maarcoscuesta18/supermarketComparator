package com.sadcos.supermarketcomparator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;

import com.sadcos.supermarketcomparator.ui.main.SectionsPagerAdapter;
import com.sadcos.supermarketcomparator.ui.main.alcampoFragmentCart;
import com.sadcos.supermarketcomparator.ui.main.carrefourFragmentCart;
import com.sadcos.supermarketcomparator.ui.main.diaFragmentCart;
import com.sadcos.supermarketcomparator.ui.main.mercadonaFragmentCart;

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