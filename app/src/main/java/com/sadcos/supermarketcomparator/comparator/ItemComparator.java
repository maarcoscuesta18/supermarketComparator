package com.sadcos.supermarketcomparator.comparator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.sadcos.supermarketcomparator.R;


public class ItemComparator extends AppCompatActivity {

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
        getSupportFragmentManager().beginTransaction().replace(R.id.itemComparatorActivity,new ItemComparatorFragment()).commit();
    }
}