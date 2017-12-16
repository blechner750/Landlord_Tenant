package com.example.brett.landlord_tenant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class contactActivity extends AppCompatActivity {

    String name = "";
    String identifier = "";
    String username = "";
    String landlordName ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras!= null){
            name = extras.getString("name");
            identifier = extras.getString("identifier");
            username = extras.getString("username");
        }

        if(identifier.equals("landlord")){
            setContentView(R.layout.activity_contact_landlord);
        }
        else if (identifier.equals("tenant")){
            setContentView(R.layout.activity_contact);
        }
        else{
            setContentView(R.layout.activity_contact);
        }

    }
}
