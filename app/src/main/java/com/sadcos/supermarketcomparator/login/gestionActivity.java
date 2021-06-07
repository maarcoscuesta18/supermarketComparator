package com.sadcos.supermarketcomparator.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sadcos.supermarketcomparator.HomeFragment;
import com.sadcos.supermarketcomparator.MainActivity;
import com.sadcos.supermarketcomparator.R;
import com.sadcos.supermarketcomparator.adapters.AdapterCarrefour;
import com.sadcos.supermarketcomparator.adapters.AdapterDia;
import com.sadcos.supermarketcomparator.adapters.AdapterMercadona;
import com.sadcos.supermarketcomparator.products.AllCategory;
import com.sadcos.supermarketcomparator.products.CategoryItem;
import com.sadcos.supermarketcomparator.products.carrefourProducts;
import com.sadcos.supermarketcomparator.products.diaProducts;
import com.sadcos.supermarketcomparator.products.mercadonaProducts;
import com.sadcos.supermarketcomparator.sliderintro.IntroActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class gestionActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TextView usernameTemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
                boolean session=preferences.getBoolean("session",false);
                if(session){
                    getCart();
                    Intent intent = new Intent(gestionActivity.this, IntroActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent=new Intent(gestionActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },2000);
    }
    public void getCart(){
        SharedPreferences cartPreferences=getSharedPreferences("cartPreferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonCarrefour = cartPreferences.getString("cartCarrefour",null);
        String jsonDia = cartPreferences.getString("cartDia",null);
        String jsonMercadona = cartPreferences.getString("cartMercadona",null);
        String jsonCarrefourRecommended = cartPreferences.getString("cartCarrefourRecommended",null);
        String jsonDiaRecommended = cartPreferences.getString("cartDiaRecommended",null);
        String jsonMercadonaRecommended = cartPreferences.getString("cartMercadonaRecommended",null);
        String jsonSupermarketsRecommended = cartPreferences.getString("cartSupermarketsRecommended",null);
        Boolean nightmode = cartPreferences.getBoolean("nightmode",false);

        if(nightmode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        Type typeCarrefour = new TypeToken<ArrayList<carrefourProducts>>() {}.getType();
        Type typeDia = new TypeToken<ArrayList<diaProducts>>() {}.getType();
        Type typeMercadona = new TypeToken<ArrayList<mercadonaProducts>>() {}.getType();
        Type typeCarrefourRecommended = new TypeToken<ArrayList<CategoryItem>>() {}.getType();
        Type typeDiaRecommended = new TypeToken<ArrayList<CategoryItem>>() {}.getType();
        Type typeMercadonaRecommended = new TypeToken<ArrayList<CategoryItem>>() {}.getType();
        Type typeSupermarketsRecommended = new TypeToken<ArrayList<CategoryItem>>() {}.getType();

        AdapterCarrefour.carrefourCartProducts=gson.fromJson(jsonCarrefour,typeCarrefour);
        AdapterDia.diaCartProducts=gson.fromJson(jsonDia,typeDia);
        AdapterMercadona.mercadonaCartProducts=gson.fromJson(jsonMercadona,typeMercadona);

        HomeFragment.categoryItemListCarrefour =gson.fromJson(jsonCarrefourRecommended,typeCarrefourRecommended);
        HomeFragment.categoryItemListDia=gson.fromJson(jsonDiaRecommended,typeDiaRecommended);
        HomeFragment.categoryItemListMercadona=gson.fromJson(jsonMercadonaRecommended,typeMercadonaRecommended);

        if(AdapterMercadona.mercadonaCartProducts==null){
            AdapterMercadona.mercadonaCartProducts=new ArrayList<mercadonaProducts>();
        }
        if(AdapterDia.diaCartProducts==null){
            AdapterDia.diaCartProducts=new ArrayList<diaProducts>();
        }
        if(AdapterCarrefour.carrefourCartProducts==null){
            AdapterCarrefour.carrefourCartProducts=new ArrayList<carrefourProducts>();
        }

        if(HomeFragment.categoryItemListCarrefour==null){
            HomeFragment.categoryItemListCarrefour=new ArrayList<CategoryItem>();
        }
        if(HomeFragment.categoryItemListDia==null){
            HomeFragment.categoryItemListDia=new ArrayList<CategoryItem>();
        }
        if(HomeFragment.categoryItemListMercadona==null){
            HomeFragment.categoryItemListMercadona=new ArrayList<CategoryItem>();
        }
    }
}