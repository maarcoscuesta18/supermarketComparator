package com.sadcos.supermarketcomparator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class LogoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
        Intent intent = new Intent(LogoutActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}