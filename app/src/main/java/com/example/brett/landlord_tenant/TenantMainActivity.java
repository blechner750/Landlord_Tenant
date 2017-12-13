package com.example.brett.landlord_tenant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class TenantMainActivity extends AppCompatActivity {

    String name = "";
    String identifier = "";
    String username = "";
    String landlordName ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_main);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras!= null){
            name = extras.getString("name");
            identifier = extras.getString("identifier");
            username = extras.getString("username");
        }
        TextView welcome_name = (TextView) findViewById(R.id.welcome_name);
        welcome_name.setText(name);

        getLandlord();

        Intent x = new Intent(getApplicationContext(), NotificationService.class);
        x.putExtra("name", name);
        x.putExtra("identifier", identifier);
        x.putExtra("landlord",landlordName);
        startService(x);
    }

    private void getLandlord() {
        DatabaseReference landlordRef = FirebaseDatabase.getInstance().getReference().child("users").child("tenants");
        landlordRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Landlord landlord = dataSnapshot.getValue(Landlord.class);
                if(landlord != null) {
                    landlordName = landlord.getmUsername();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
