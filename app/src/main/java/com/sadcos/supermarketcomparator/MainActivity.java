package com.sadcos.supermarketcomparator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.sadcos.supermarketcomparator.adapters.supermercadosAdapters.AdapterAlcampo;
import com.sadcos.supermarketcomparator.adapters.supermercadosAdapters.AdapterCarrefour;
import com.sadcos.supermarketcomparator.adapters.supermercadosAdapters.AdapterDia;
import com.sadcos.supermarketcomparator.adapters.supermercadosAdapters.AdapterMercadona;
import com.sadcos.supermarketcomparator.settings.Settings;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;



public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    TextView textCartItemCount;
    public static int mCartItemCount =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        SharedPreferences preferences=getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
        View nav = navigationView.getHeaderView(0);
        TextView Username= (TextView) nav.findViewById(R.id.textViewUsername);
        Username.setText("Welcome "+preferences.getString("username","")+"!");

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_mercadona,R.id.nav_dia, R.id.nav_carrefour,R.id.nav_alcampo)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_cart);

        View actionView = menuItem.getActionView();
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_cart:
                intent = new Intent(MainActivity.this,Cart.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
    private void setupBadge() {
        try{
            mCartItemCount= AdapterMercadona.mercadonaCartProducts.size()+ AdapterCarrefour.carrefourCartProducts.size()+ AdapterDia.diaCartProducts.size()+ AdapterAlcampo.alcampoCartProducts.size();
            if (textCartItemCount != null) {
                if (mCartItemCount == 0) {
                    if (textCartItemCount.getVisibility() != View.GONE) {
                        textCartItemCount.setVisibility(View.GONE);
                    }
                } else {
                    textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                    if (textCartItemCount.getVisibility() != View.VISIBLE) {
                        textCartItemCount.setVisibility(View.VISIBLE);
                    }
                }
            }
        }catch(Exception e){
            mCartItemCount=0;
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Utiliza la navegacion de la aplicacion. Gracias", Toast.LENGTH_SHORT).show();
    }
}