package com.example.brett.landlord_tenant;

import android.content.DialogInterface;
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(leaseActivity.this);

                    builder.setTitle("You are sure you want to submit?");

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            // TODO: submit approval to database
                            Toast.makeText(leaseActivity.this, "Signature submitted", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }//if
                else{
                    Toast.makeText(leaseActivity.this, "Check that you have read and understand the lease", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
