package com.example.brett.landlord_tenant;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private String name;
    private String pass;
    String fullName;
    String identifier;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference myRef = db.getReference();
    List<Tenant> tenants = new ArrayList<Tenant>();
    List<Landlord> landlord = new ArrayList<Landlord>();

    private BroadcastReceiver broadcastReceiver;
    @Override
    protected void onResume(){
        super.onResume();

        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                }
            };
        }
        registerReceiver(broadcastReceiver, new IntentFilter("automatic_checkIn"));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent x = new Intent(getApplicationContext(), NotificationService.class);
        startService(x);

        final EditText username = (EditText) findViewById(R.id.username_form);
        final EditText password = (EditText) findViewById(R.id.password_form);


        final Button login = (Button) findViewById(R.id.login_button);

        sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("pass", password.getText().toString());
        pass = sharedPreferences.getString("name", username.getText().toString());

        username.setText(name);
        password.setText(pass);

        checkDatabaseReference();



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username != null && password != null){
                    if(findType(username.getText().toString(), password.getText().toString())){
                        login(v);
                    }
                }

            }
        });

    }

    protected void onPause(){
        super.onPause();
        EditText username = (EditText) findViewById(R.id.username_form);
        EditText password = (EditText) findViewById(R.id.password_form);

        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString("name", username.getText().toString());
        ed.putString("pass", password.getText().toString());
        ed.commit();
    }

    public void checkDatabaseReference(){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("users")){
                    if(dataSnapshot.child("users").hasChild("tenants")){
                        getTenantList();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("users")){
                    if(dataSnapshot.child("users").hasChild("landlords")){
                        getLandlordList();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getTenantList(){
        myRef.child("users").child("tenants").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    tenants.add(postSnapshot.getValue(Tenant.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getLandlordList(){
        myRef.child("users").child("landlords").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    landlord.add(postSnapshot.getValue(Landlord.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public boolean findType(String username, String password){
        boolean check = false;
        if(tenants == null && landlord == null){
            Toast.makeText(this,"No database records", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tenants != null){
            if(!tenants.isEmpty()){
                for(int i =0; i<tenants.size();i++){
                    if(username.equals(tenants.get(i).getmUserName())){
                        fullName = tenants.get(i).getmFirstName() + " " + tenants.get(i).getmLastName();
                        identifier = "tenant";
                        check = checkTenant(username, password);
                    }
                }
            }
        }

        if(landlord != null){
            if(!landlord.isEmpty()){
                for(int i =0; i<landlord.size();i++){
                    if(username.equals(landlord.get(i).getmUsername())){
                        fullName = landlord.get(i).getmFirstName() + " " + landlord.get(i).getmLastName();
                        identifier = "landlord";
                        check = checkLandlord(username, password);
                    }
                }
            }
        }
        return check;
    }

    public boolean checkTenant(String username, String password){
        for(int i =0; i<tenants.size();i++){
            if(username.equals(tenants.get(i).getmUserName()) ){
                if(password.equals(tenants.get(i).getmPassword())){
                    return true;
                }
                else{
                    Toast.makeText(this, "Password Incorrect", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        Toast.makeText(this, "No record found", Toast.LENGTH_SHORT).show();
        return false;
    }

    public boolean checkLandlord(String username, String password){
        for(int i =0; i<landlord.size();i++){
            if(username.equals(landlord.get(i).getmUsername()) ){
                if(password.equals(landlord.get(i).getmPassword()) ){
                    return true;
                }
                else{
                    Toast.makeText(this, "Password Incorrect", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        Toast.makeText(this, "No record found", Toast.LENGTH_SHORT).show();
        return false;
    }

    //defined in xml onClick for the Create Account button
    public void createAccount(View view){
        Intent intent = new Intent(this, createAccount.class);
        startActivity(intent);
    }

    public void login(View view){
        Intent intent = new Intent(this, TenantMainActivity.class);
        intent.putExtra("name", fullName);
        intent.putExtra("identifier", identifier);
        startActivity(intent);
    }
}
