package com.sadcos.supermarketcomparator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ItemDetail extends AppCompatActivity {
    TextView product_name,price,price_per_kg,qty;
    Button addtocart;
    String link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdetail);

        product_name = findViewById(R.id.product_name);
        price = findViewById(R.id.price);
        price_per_kg = findViewById(R.id.priceperkg);
        addtocart = findViewById(R.id.addtocart);

        product_name.setText(getIntent().getStringExtra("itemName"));
        price.setText(getIntent().getStringExtra("itemPrice"));
        price_per_kg.setText(getIntent().getStringExtra("itemPricePerKg"));
        link =getIntent().getStringExtra("itemLink");
    }
}