package com.example.brett.landlord_tenant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class Login extends AppCompatActivity {
    //        BUTTONS FOR DEBUG PURPOSES
    Button startLandlord, startTenant, startLogin, startCreateUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        BUTTONS FOR DEBUG PURPOSES
        startLandlord = (Button) findViewById(R.id.landlord_activity);
        startTenant = (Button) findViewById(R.id.tenant_activity);
        startLogin = (Button) findViewById(R.id.login_screen);
        startCreateUser = (Button) findViewById(R.id.create_user_activity);

        
    }
}
