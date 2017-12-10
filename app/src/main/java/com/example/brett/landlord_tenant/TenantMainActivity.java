package com.example.brett.landlord_tenant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TenantMainActivity extends AppCompatActivity {

    String name = "";
    String identifier = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_main);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras!= null){
            name = extras.getString("name");
            identifier = extras.getString("identifier");
        }
        TextView welcome_name = (TextView) findViewById(R.id.welcome_name);
        welcome_name.setText(name);
    }

    public void maintenance(View view){
        Intent intent = new Intent(this, maintenanceActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("identifier", identifier);
        startActivity(intent);
    }

    public void messages(View view){
        Intent intent = new Intent(this, messagesActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("identifier", identifier);
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
