package com.sadcos.supermarketcomparator.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.sadcos.supermarketcomparator.R;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText editUser,editPassword;
    CheckBox checkBox;
    ImageButton signup;
    Button signin;

    private static String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        editUser = findViewById(R.id.username);
        editPassword = findViewById(R.id.password);

        checkBox = findViewById(R.id.checkbox);
        signin =findViewById(R.id.signin);
        signup = findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editUser.getText().toString();
                password = editPassword.getText().toString();
                if(!username.isEmpty() && !password.isEmpty()) {
                    register("http://192.168.1.60/supermarketcomparator/GET/register.php");
                }else{
                    Toast.makeText(SignUpActivity.this,"Empty blanks not allowed",Toast.LENGTH_SHORT).show();
                }
            }
        });
        signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
    private void register(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    if(checkBox.isChecked()){
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        Toast.makeText(SignUpActivity.this,"Register successfully",Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Toast.makeText(SignUpActivity.this,"Username or password incorrect",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignUpActivity.this,"Error to connect into the database",Toast.LENGTH_SHORT).show();
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
}
