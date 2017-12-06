package com.example.brett.landlord_tenant;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private String name;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText username = (EditText) findViewById(R.id.username_form);
        EditText password = (EditText) findViewById(R.id.password_form);


        Button login = (Button) findViewById(R.id.login_button);

        sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("pass", password.getText().toString());
        pass = sharedPreferences.getString("name", username.getText().toString());

        username.setText(name);
        password.setText(pass);
        /*
        if(name != null){
            username.setText(name);
        }
        if(pass != null){
            password.setText(pass);
        }
        */



        /*
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // function to retrieve credentials from database
                // function to use credentials to go to appropriate activity
            }
        });
        */




    }

    protected void onPause(){
        super.onPause();
        EditText username = (EditText) findViewById(R.id.username_form);
        EditText password = (EditText) findViewById(R.id.password_form);

        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString("name", username.getText().toString());
        ed.putString("pass", password.getText().toString());
        ed.commit();
    }

    //defined in xml onClick for the Create Account button
    public void createAccount(View view){
        Intent intent = new Intent(this, createAccount.class);
        startActivity(intent);
    }

    // TODO: Remove this function after the login feature is implemented
    public void logintest(View view){
        Intent intent = new Intent(this, TenantMainActivity.class);
        startActivity(intent);
    }
}
