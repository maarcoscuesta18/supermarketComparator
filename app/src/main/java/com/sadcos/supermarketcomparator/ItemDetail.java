package com.sadcos.supermarketcomparator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sadcos.supermarketcomparator.adapters.supermercadosAdapters.AdapterAlcampo;
import com.sadcos.supermarketcomparator.adapters.supermercadosAdapters.AdapterCarrefour;
import com.sadcos.supermarketcomparator.adapters.supermercadosAdapters.AdapterDia;
import com.sadcos.supermarketcomparator.adapters.supermercadosAdapters.AdapterMercadona;
import com.sadcos.supermarketcomparator.products.stringPriceProducts;
import com.sadcos.supermarketcomparator.products.mercadonaProducts;

import static com.sadcos.supermarketcomparator.MainActivity.mCartItemCount;
import static com.sadcos.supermarketcomparator.MainActivity.setupBadge;
import static com.sadcos.supermarketcomparator.MainActivity.textCartItemCount;

public class ItemDetail extends AppCompatActivity {
    TextView product_name,price,price_per_kg,qty;
    Button addtocart;
    ImageView addqty,lessqty;
    String link;
    ImageView imglink;
    boolean isInCart;
    int[] count = {1};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdetail);
        product_name = findViewById(R.id.product_name);
        price = findViewById(R.id.price);
        price_per_kg = findViewById(R.id.priceperkg);
        addtocart = findViewById(R.id.addtocart);
        addqty = findViewById(R.id.qtyplus);
        lessqty = findViewById(R.id.qtyless);
        qty = findViewById(R.id.qty);
        imglink = findViewById(R.id.imglink);
        setupBadge();

        Bundle bundle = getIntent().getExtras();
        String supermarketType = bundle.getString("supermarketType");
        switch (supermarketType){
            case "mercadona":
                imglink.setImageResource(R.drawable.mercadona);
                break;
            case "dia":
                imglink.setImageResource(R.drawable.dia);
                break;
            case "carrefour":
                imglink.setImageResource(R.drawable.carrefour);
                break;
            case "alcampo":
                imglink.setImageResource(R.drawable.alcampo);
                break;
        }
        product_name.setText(bundle.getString("itemName"));
        price.setText(bundle.getString("itemPrice")+" €");
        price_per_kg.setText(bundle.getString("itemPricePerKg"));
        link = bundle.getString("itemLink");

        ImageView goBack = findViewById(R.id.goback);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imglink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(supermarketType.equals("mercadona")){
                    intent=new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                }else{
                    intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://"+link));
                }
                startActivity(intent);
            }
        });

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (supermarketType){
                    case "carrefour":
                        addcarrefour(v,count);
                        break;
                    case "mercadona":
                        addmercadona(v,count);
                        break;
                    case "dia":
                        addDia(v,count);
                        break;
                    case "alcampo":
                        addAlcampo(v,count);
                        break;
                }
            }
        });
        addqty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count[0]++;
                qty.setText(String.valueOf(count[0]));
            }
        });
        lessqty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count[0] == 1){
                    count[0] = 1;
                    qty.setText(String.valueOf(count[0]));
                } else{
                    count[0]--;
                    qty.setText(String.valueOf(count[0]));
                }
            }
        });
    }
    public void addcarrefour(View v,int[] count){
        stringPriceProducts newproduct = new stringPriceProducts(product_name.getText().toString(),link,price.getText().toString().replace("€","").trim(),price_per_kg.getText().toString(),String.valueOf(count[0]),String.valueOf(Double.parseDouble(price.getText().toString().replace("€","").trim())*count[0]));
        for(int i=0;i<AdapterCarrefour.carrefourCartProducts.size();i++){
            if(newproduct.getCartproduct_name().equals(AdapterCarrefour.carrefourCartProducts.get(i).getCartproduct_name())){
                isInCart=true;
            }
        }
        if(AdapterCarrefour.carrefourCartProducts.isEmpty()){
            AdapterCarrefour.carrefourCartProducts.add(newproduct);
            saveCartCarrefour(v);
        }else{
            if(isInCart){
                for (stringPriceProducts product : AdapterCarrefour.carrefourCartProducts){
                    if(product.getCartproduct_name().equals(newproduct.getCartproduct_name())){
                        product.setQty(String.valueOf(Integer.parseInt(product.getQty())+Integer.parseInt(newproduct.getQty())));
                        product.setTotalprice(Double.parseDouble(String.valueOf(product.getTotalprice()+newproduct.getTotalprice())));
                        saveCartCarrefour(v);
                    }
                }
            }else{
                AdapterCarrefour.carrefourCartProducts.add(newproduct);
                saveCartCarrefour(v);
            }
        }
        count[0]=1;
        qty.setText("1");
        Toast.makeText(v.getContext(),"Producto añadido correctamente",Toast.LENGTH_SHORT).show();
    }
    public void addAlcampo(View v,int[] count){
        stringPriceProducts newproduct = new stringPriceProducts(product_name.getText().toString(),link,price.getText().toString().replace("€","").trim(),price_per_kg.getText().toString(),String.valueOf(count[0]),String.valueOf(Double.parseDouble(price.getText().toString().replace("€","").trim())*count[0]));
        for(int i=0;i<AdapterAlcampo.alcampoCartProducts.size();i++){
            if(newproduct.getCartproduct_name().equals(AdapterAlcampo.alcampoCartProducts.get(i).getCartproduct_name())){
                isInCart=true;
            }
        }
        if(AdapterAlcampo.alcampoCartProducts.isEmpty()){
            AdapterAlcampo.alcampoCartProducts.add(newproduct);
            saveCartAlcampo(v);
        }else{
            if(isInCart){
                for (stringPriceProducts product : AdapterAlcampo.alcampoCartProducts){
                    if(product.getCartproduct_name().equals(newproduct.getCartproduct_name())){
                        product.setQty(String.valueOf(Integer.parseInt(product.getQty())+Integer.parseInt(newproduct.getQty())));
                        product.setTotalprice(Double.parseDouble(String.valueOf(product.getTotalprice()+newproduct.getTotalprice())));
                        saveCartAlcampo(v);
                    }
                }
            }else{
                AdapterAlcampo.alcampoCartProducts.add(newproduct);
                saveCartAlcampo(v);
            }
        }
        count[0]=1;
        qty.setText("1");
        Toast.makeText(v.getContext(),"Producto añadido correctamente",Toast.LENGTH_SHORT).show();
    }
    public void addDia(View v,int[] count){
        stringPriceProducts newproduct = new stringPriceProducts(product_name.getText().toString(),link,price.getText().toString().replace("€","").trim(),price_per_kg.getText().toString(),String.valueOf(count[0]),String.valueOf(Double.parseDouble(price.getText().toString().replace("€","").trim())*count[0]));
        for(int i=0;i<AdapterDia.diaCartProducts.size();i++){
            if(newproduct.getCartproduct_name().equals(AdapterDia.diaCartProducts.get(i).getCartproduct_name())){
                isInCart=true;
            }
        }
        if(AdapterDia.diaCartProducts.isEmpty()){
            AdapterDia.diaCartProducts.add(newproduct);
            saveCartDia(v);
        }else{
            if(isInCart){
                for (stringPriceProducts product : AdapterDia.diaCartProducts){
                    if(product.getCartproduct_name().equals(newproduct.getCartproduct_name())){
                        product.setQty(String.valueOf(Integer.parseInt(product.getQty())+Integer.parseInt(newproduct.getQty())));
                        product.setTotalprice(Double.parseDouble(String.valueOf(product.getTotalprice()+newproduct.getTotalprice())));
                        saveCartDia(v);
                    }
                }
            }else{
                AdapterDia.diaCartProducts.add(newproduct);
                saveCartDia(v);
            }
        }
        count[0]=1;
        qty.setText("1");
        Toast.makeText(v.getContext(),"Producto añadido correctamente",Toast.LENGTH_SHORT).show();
    }
    public void addmercadona(View v,int[] count){
        mercadonaProducts newproduct = new mercadonaProducts(product_name.getText().toString(),link,price.getText().toString().replace("€","").trim(),String.valueOf(count[0]),String.valueOf(Double.parseDouble(price.getText().toString().replace("€","").trim())*count[0]));
        for(int i=0;i<AdapterMercadona.mercadonaCartProducts.size();i++){
            if(newproduct.getCartproduct_name().equals(AdapterMercadona.mercadonaCartProducts.get(i).getCartproduct_name())){
                isInCart=true;
            }
        }
        if(AdapterMercadona.mercadonaCartProducts.isEmpty()){
            AdapterMercadona.mercadonaCartProducts.add(newproduct);
            saveCartMercadona(v);
        }else{
            if(isInCart){
                for (mercadonaProducts product : AdapterMercadona.mercadonaCartProducts){
                    if(product.getCartproduct_name().equals(newproduct.getCartproduct_name())){
                        product.setQty(String.valueOf(Integer.parseInt(product.getQty())+Integer.parseInt(newproduct.getQty())));
                        product.setTotalprice(Double.parseDouble(String.valueOf(product.getTotalprice()+newproduct.getTotalprice())));
                        saveCartMercadona(v);
                    }
                }
            }else{
                AdapterMercadona.mercadonaCartProducts.add(newproduct);
                saveCartMercadona(v);
            }
        }
        count[0]=1;
        qty.setText("1");
        Toast.makeText(v.getContext(),"Producto añadido correctamente",Toast.LENGTH_SHORT).show();
    }

    public void saveCartAlcampo(View v){
        SharedPreferences cartPreferences=v.getContext().getSharedPreferences("cartPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = cartPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(AdapterAlcampo.alcampoCartProducts);
        editor.putString("cartAlcampo", json);
        editor.apply();
    }
    public void saveCartCarrefour(View v){
        SharedPreferences cartPreferences=v.getContext().getSharedPreferences("cartPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = cartPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(AdapterCarrefour.carrefourCartProducts);
        editor.putString("cartCarrefour", json);
        editor.apply();
    }
    public void saveCartDia(View v){
        SharedPreferences cartPreferences=v.getContext().getSharedPreferences("cartPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = cartPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(AdapterDia.diaCartProducts);
        editor.putString("cartDia", json);
        editor.apply();
    }
    public void saveCartMercadona(View v){
        SharedPreferences cartPreferences=v.getContext().getSharedPreferences("cartPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = cartPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(AdapterMercadona.mercadonaCartProducts);
        editor.putString("cartMercadona", json);
        editor.apply();
    }

}