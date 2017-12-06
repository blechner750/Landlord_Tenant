package com.example.brett.landlord_tenant;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class maintenanceActivity extends AppCompatActivity {

    SharedPreferences mPrefs;
    String title;
    String description;

    EditText maintenance_title;
    EditText maintenance_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);

        maintenance_title = (EditText) findViewById(R.id.maintenance_title);
        maintenance_description = (EditText) findViewById(R.id.maintenance_description);

        mPrefs = getSharedPreferences("key", Context.MODE_PRIVATE);
        title = mPrefs.getString("title", maintenance_title.getText().toString());
        description = mPrefs.getString("description", maintenance_description.getText().toString());
    }

    protected void onPause(){
        super.onPause();

        maintenance_title = (EditText) findViewById(R.id.maintenance_title);
        maintenance_description = (EditText) findViewById(R.id.maintenance_description);

        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putString("title", maintenance_title.getText().toString());
        ed.putString("description", maintenance_description.getText().toString());
        ed.commit();

    }
}
