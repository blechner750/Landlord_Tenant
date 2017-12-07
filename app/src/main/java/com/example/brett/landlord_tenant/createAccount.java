package com.example.brett.landlord_tenant;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class createAccount extends AppCompatActivity {

    CheckBox account_landlord;
    CheckBox account_tenant;
    Button createAccount;

    SharedPreferences mPrefs;
    String name;
    String email;
    String phone;
    String pass;
    String passConfirm;

    EditText username;
    EditText emailAddress;
    EditText phoneNumber;
    EditText password;
    EditText passwordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        username = (EditText) findViewById(R.id.account_username);
        emailAddress = (EditText) findViewById(R.id.account_email);
        phoneNumber = (EditText) findViewById(R.id.account_phone);
        password = (EditText) findViewById(R.id.account_password);
        passwordConfirm = (EditText) findViewById(R.id.account_confirm);

        mPrefs = getSharedPreferences("key", Context.MODE_PRIVATE);
        name = mPrefs.getString("name", username.getText().toString());
        email = mPrefs.getString("email", emailAddress.getText().toString());
        phone = mPrefs.getString("phone", phoneNumber.getText().toString());
        pass = mPrefs.getString("pass", password.getText().toString());
        passConfirm = mPrefs.getString("passConfirm", passwordConfirm.getText().toString());

        username.setText(name);
        emailAddress.setText(email);
        phoneNumber.setText(phone);
        password.setText(pass);
        passwordConfirm.setText(passConfirm);


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

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(createAccount.this);

                builder.setTitle("Your information is accurate?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // TODO: submit approval to database
                        Toast.makeText(createAccount.this, "Account created", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        // need method to store form and checkbox data when screen flips
    }

    protected void onPause(){
        super.onPause();
        SharedPreferences.Editor ed = mPrefs.edit();

        username = (EditText) findViewById(R.id.account_username);
        emailAddress = (EditText) findViewById(R.id.account_email);
        phoneNumber = (EditText) findViewById(R.id.account_phone);
        password = (EditText) findViewById(R.id.account_password);
        passwordConfirm = (EditText) findViewById(R.id.account_confirm);

        ed.putString("name", username.getText().toString());
        ed.putString("email", emailAddress.getText().toString());
        ed.putString("phone", phoneNumber.getText().toString());
        ed.putString("pass", password.getText().toString());
        ed.putString("passConfirm", passwordConfirm.getText().toString());

        ed.commit();
    }
}
