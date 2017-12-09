package com.example.brett.landlord_tenant;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class maintenanceActivity extends AppCompatActivity {

    private SharedPreferences mPrefs;
    String title;
    String description;

    EditText maintenance_title;
    EditText maintenance_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);

        Button button = (Button) findViewById(R.id.maint_button);

        maintenance_title = (EditText) findViewById(R.id.maintenance_title);
        maintenance_description = (EditText) findViewById(R.id.maintenance_description);

        mPrefs = getSharedPreferences("key", Context.MODE_PRIVATE);
        title = mPrefs.getString("title", maintenance_title.getText().toString());
        description = mPrefs.getString("description", maintenance_description.getText().toString());

        maintenance_title.setText(title);
        maintenance_description.setText(description);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(maintenanceActivity.this);

                builder.setTitle("You are sure you want to submit?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // TODO: submit approval to database
                        Toast.makeText(maintenanceActivity.this, "Request submitted", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
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
