package com.sadcos.supermarketcomparator.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sadcos.supermarketcomparator.MainActivity;
import com.sadcos.supermarketcomparator.R;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText editUser,editPassword;
    CheckBox checkBox;
    ImageButton signin;
    Button signup;
    ProgressDialog pdDialog;
    private static String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        editUser = (EditText)findViewById(R.id.username);
        editPassword = (EditText)findViewById(R.id.password);

        checkBox = (CheckBox)findViewById(R.id.checkbox);
        signin =(ImageButton)findViewById(R.id.signin);
        signup = (Button) findViewById(R.id.signup);
        getLogin();
        pdDialog= new ProgressDialog(LoginActivity.this);
        pdDialog.setTitle("Logging in please wait...");
        pdDialog.setCancelable(false);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editUser.getText().toString();
                password = editPassword.getText().toString();
                if(!username.isEmpty() && !password.isEmpty()) {
                    login("https://supermarketcomparator.000webhostapp.com/login.php");
                }else{
                    Toast.makeText(LoginActivity.this,"Empty blanks not allowed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void login(String URL){
        pdDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    if(checkBox.isChecked()){
                        saveLogin();
                    }
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    pdDialog.dismiss();
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,"Username or password incorrect",Toast.LENGTH_SHORT).show();
                    pdDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this,"Error to connect into the database",Toast.LENGTH_SHORT).show();
                pdDialog.dismiss();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> parameters=new HashMap<String,String>();
                parameters.put("username",username);
                parameters.put("password",password);
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void saveLogin(){
        SharedPreferences preferences=getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username",username);
        editor.putString("password",password);
        editor.putBoolean("session",true);
        editor.apply();
    }
    private void getLogin(){
        SharedPreferences preferences=getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
        editUser.setText(preferences.getString("username",username));
        editPassword.setText(preferences.getString("password",password));
    }
}