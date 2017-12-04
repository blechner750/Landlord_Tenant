package com.example.brett.landlord_tenant;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = (Button) findViewById(R.id.login_button);

        /*
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // function to retrieve credentials from database
                // function to use credentials to go to appropriate activity
            }
        });
        */

        // need to use something to store the form inputs during screen flips
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
