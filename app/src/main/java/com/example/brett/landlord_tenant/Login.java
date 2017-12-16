package com.example.brett.landlord_tenant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {
    private Button mStartLandlordBillPay, mStartTenantBillPay;

    private static final String KEY_LANDLORD_USERNAME = "landlordUsername";
    private static final String KEY_TENANT_USERNAME = "tenantusername";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mStartLandlordBillPay = (Button) findViewById(R.id.button_start_landlord_billpay);
        mStartTenantBillPay = (Button) findViewById(R.id.button_start_tenant_billpay);

        mStartLandlordBillPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LandlordRentActivity.class);
                intent.putExtra(KEY_LANDLORD_USERNAME, "new test landlord");
                startActivity(intent);
            }
        });

        mStartTenantBillPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TenantRentActivity.class);
                intent.putExtra(KEY_TENANT_USERNAME, "TestTenant001");
                startActivity(intent);
            }
        });
    }
}
