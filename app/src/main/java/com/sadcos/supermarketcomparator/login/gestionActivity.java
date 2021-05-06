package com.sadcos.supermarketcomparator.login;

import androidx.appcompat.app.AppCompatActivity;

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
import com.sadcos.supermarketcomparator.MainActivity;
import com.sadcos.supermarketcomparator.R;
import com.sadcos.supermarketcomparator.adapters.AdapterMercadona;
import com.sadcos.supermarketcomparator.products.mercadonaProducts;

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
                    Intent intent = new Intent(gestionActivity.this, MainActivity.class);
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
        String json = cartPreferences.getString("cartMercadona",null);
        Type type = new TypeToken<ArrayList<mercadonaProducts>>() {}.getType();
        AdapterMercadona.mercadonaCartProducts=gson.fromJson(json,type);

        if(AdapterMercadona.mercadonaCartProducts==null){
            AdapterMercadona.mercadonaCartProducts=new ArrayList<mercadonaProducts>();
        }
    }
}