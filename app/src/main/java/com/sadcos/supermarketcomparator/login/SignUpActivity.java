package com.sadcos.supermarketcomparator.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText editUser,editPassword,editFirstname,editLastname,editEmail;
    CheckBox checkBox;
    ImageButton signup;
    Button signin;

    String firstname,lastname,email,username,password;
    ProgressDialog pdDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        editFirstname=findViewById(R.id.firstname);
        editLastname=findViewById(R.id.lastname);
        editEmail = findViewById(R.id.email);
        editUser = findViewById(R.id.username);
        editPassword = findViewById(R.id.password);

        checkBox = findViewById(R.id.checkbox);
        signin =findViewById(R.id.signin);
        signup = findViewById(R.id.signup);

        pdDialog= new ProgressDialog(SignUpActivity.this);
        pdDialog.setTitle("Registering please wait...");
        pdDialog.setCancelable(false);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstname = editFirstname.getText().toString();
                lastname = editLastname.getText().toString();
                email = editEmail.getText().toString();
                username = editUser.getText().toString();
                password = editPassword.getText().toString();
                if(!firstname.isEmpty() && !lastname.isEmpty() && !email.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                    if(checkBox.isChecked()) {
                        register("http://192.168.1.60/supermarketcomparator/GET/register.php");
                    }else{
                        Toast.makeText(SignUpActivity.this,"You must Accept Our Terms And Conditions",Toast.LENGTH_SHORT).show();
                    }
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
    private void register(String URL) {
        pdDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("anyText",response);
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    String message = jsonObject.getString("message");
                    if(success.equals("1")){
                        Toast.makeText(getApplicationContext(),"Registration Success",Toast.LENGTH_LONG).show();
                        pdDialog.dismiss();
                        Intent login = new Intent(SignUpActivity.this,LoginActivity.class);
                        startActivity(login);
                        finish();
                    }
                    if(success.equals("0")){
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                        pdDialog.dismiss();
                    }
                    if(success.equals("3")){
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                        pdDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Registration Error !1"+e,Toast.LENGTH_LONG).show();
                    pdDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Registration Error !2"+error,Toast.LENGTH_LONG).show();
                pdDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("firstname",firstname);
                params.put("lastname",lastname);
                params.put("email",email);
                params.put("username",username);
                params.put("password",password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
