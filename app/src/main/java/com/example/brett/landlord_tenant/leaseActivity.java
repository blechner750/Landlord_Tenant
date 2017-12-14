package com.example.brett.landlord_tenant;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class leaseActivity extends AppCompatActivity {

    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lease);


        checkBox = (CheckBox) findViewById(R.id.lease_checkbox);
        Button button = (Button) findViewById(R.id.lease_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()){
                    Intent intent = new Intent(leaseActivity.this, SignatureActivity.class);
                    startActivity(intent);
                }//if
                else{
                    Toast.makeText(leaseActivity.this, "Check that you have read and understand the lease", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
