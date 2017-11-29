package com.example.brett.landlord_tenant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class createAccount extends AppCompatActivity {

    CheckBox account_landlord;
    CheckBox account_tenant;
    Button createAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        account_landlord = (CheckBox) findViewById(R.id.landlord_checkBox);
        account_tenant = (CheckBox) findViewById(R.id.tenant_checkBox);
        createAccount = (Button) findViewById(R.id.account_create_button);

        account_landlord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(account_landlord.isChecked() || account_tenant.isChecked()){
                    account_tenant.setChecked(false);
                }
            }
        });

        account_tenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(account_landlord.isChecked() || account_tenant.isChecked()){
                    account_landlord.setChecked(false);
                }
            }
        });


        // need method to store form and checkbox data when screen flips
    }
}
