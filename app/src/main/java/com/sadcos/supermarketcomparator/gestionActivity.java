package com.sadcos.supermarketcomparator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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
                    Intent intent = new Intent(gestionActivity.this,MainActivity.class);
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
}