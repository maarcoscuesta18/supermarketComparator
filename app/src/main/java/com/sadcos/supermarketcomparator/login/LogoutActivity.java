package com.sadcos.supermarketcomparator.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.sadcos.supermarketcomparator.MainActivity;

public class LogoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("¿Quieres cerrar la Sesion? Perderas los datos de tu carrito");

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences preferences = getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
                SharedPreferences cartPreferences = getSharedPreferences("cartPreferences", Context.MODE_PRIVATE);
                cartPreferences.edit().clear().apply();
                preferences.edit().clear().apply();

                Intent intent = new Intent(LogoutActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(LogoutActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}