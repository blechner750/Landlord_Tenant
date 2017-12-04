package com.example.brett.landlord_tenant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;

public class TenantMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_main);
        Intent intent = getIntent();
    }

    public void maintenance(View view){
        Intent intent = new Intent(this, maintenanceActivity.class);
        startActivity(intent);
    }

    public void messages(View view){
        Intent intent = new Intent(this, messagesActivity.class);
        startActivity(intent);
    }

    public void rent(View view){
        Intent intent = new Intent(this, rentActivity.class);
        startActivity(intent);
    }

    public void contacts(View view){
        Intent intent = new Intent(this, contactActivity.class);
        startActivity(intent);
    }

    public void utilities(View view){
        Intent intent = new Intent(this, utilitiesActivity.class);
        startActivity(intent);
    }

    public void lease(View view){
        Intent intent = new Intent(this, leaseActivity.class);
        startActivity(intent);
    }
}
