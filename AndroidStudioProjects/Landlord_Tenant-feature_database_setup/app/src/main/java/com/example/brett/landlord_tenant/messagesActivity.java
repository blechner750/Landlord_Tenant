package com.example.brett.landlord_tenant;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class messagesActivity extends AppCompatActivity {


    SharedPreferences mPrefs;
    String message;
    String name ="";
    String identifier = "";
    String recipient = "";

   Messages messages;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference myRef = db.getReference();

    EditText message_prompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras!= null){
            name = extras.getString("name");
            identifier = extras.getString("identifier");
        }

        message_prompt = (EditText) findViewById(R.id.messages_prompt);

        mPrefs = getSharedPreferences("key", Context.MODE_PRIVATE);
        mPrefs.getString("message", message_prompt.getText().toString());

        Button button = (Button) findViewById(R.id.maint_button);

        final ListView list = (ListView) findViewById(R.id.messages_list);
        final ListView accountNamesList = findViewById(R.id.account_names);


        final FirebaseListAdapter<Tenant> mAdapter;
        mAdapter = new FirebaseListAdapter<Tenant>(this, Tenant.class, android.R.layout.simple_list_item_2, myRef.child("user").child("landlords")) {
            @Override
            protected void populateView(View v, Tenant model, int position) {
                String title = model.getmFirstName() + model.getmLastName();
                ((TextView)v.findViewById(android.R.id.text1)).setText(title);
                ((TextView)v.findViewById(android.R.id.text2)).setText("");
            }
        };
        list.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        if (identifier.equals("tenant")) {
            final FirebaseListAdapter<Tenant> mAdapterTents;
            mAdapterTents = new FirebaseListAdapter<Tenant>(this, Tenant.class, android.R.layout.simple_list_item_checked, myRef.child("users").child("tenants")) {
                @Override
                protected void populateView(View v, Tenant model, int position) {
                    String name = model.getmFirstName() + model.getmLastName();
                    ((CheckedTextView)v.findViewById(android.R.id.text1)).setText(name);
                }
            };

            accountNamesList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
            accountNamesList.setAdapter(mAdapterTents);
            mAdapterTents.notifyDataSetChanged();
        }
        else {
            final FirebaseListAdapter<Landlord> mAdapterLands;
            mAdapterLands = new FirebaseListAdapter<Landlord>(this, Landlord.class, android.R.layout.simple_list_item_checked, myRef) {
                @Override
                protected void populateView(View v, Landlord model, int position) {
                    String name = model.getmFirstName() + model.getmLastName();
                    ((CheckedTextView)v.findViewById(android.R.id.text1)).setText(name);
                }
            };

            accountNamesList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
            accountNamesList.setAdapter(mAdapterLands);
            mAdapterLands.notifyDataSetChanged();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(messagesActivity.this);

                builder.setTitle("Send the message?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    if (identifier.equals("landlord")) {
                    DatabaseReference landlordRef = FirebaseDatabase.getInstance().getReference().child("users").child("landlords");
                    landlordRef.child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Landlord landlord = dataSnapshot.getValue(Landlord.class);
                            Log.d("LandlordTenantApp", "landlord retrieved from setLandlord: " + landlord.getmFirstName() + landlord.getmLastName());
                            if (landlord != null) {
                                HashMap<String, Messages> convos = landlord.getmConvos();

                                int check = 0;
                                if (accountNamesList.getCheckedItemPosition() > 0) {
                                    check = accountNamesList.getCheckedItemPosition();
                                }

                                if (!message_prompt.getText().equals(null)) {
                                    messages = new Messages(accountNamesList.getItemAtPosition(check).toString());
                                    messages.setmMessage(message_prompt.getText().toString());
                                    convos.put(accountNamesList.getItemAtPosition(check).toString(), messages);
                                    landlord.setmConvos(convos);
                                } else
                                    Toast.makeText(messagesActivity.this, "Empty message", Toast.LENGTH_SHORT).show();

                                recipient = accountNamesList.getItemAtPosition(check).toString();
                                landlord.updateDatabase();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else
                {
                    DatabaseReference tenantRef = FirebaseDatabase.getInstance().getReference().child("users").child("tenants");
                    tenantRef.child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Tenant tenant = dataSnapshot.getValue(Tenant.class);
                            Log.d("LandlordTenantApp", "tenant retrieved from setTenant: " + tenant.getmFirstName() + tenant.getmLastName());
                            if (tenant != null) {
                                HashMap<String, Messages> convos = tenant.getmConvos();

                                int check = 0;
                                if (accountNamesList.getCheckedItemPosition() > 0) {
                                    check = accountNamesList.getCheckedItemPosition();
                                }

                                if (!message_prompt.getText().equals(null)) {
                                    messages = new Messages(accountNamesList.getItemAtPosition(check).toString());
                                    messages.setmMessage(message_prompt.getText().toString());
                                    convos.put(accountNamesList.getItemAtPosition(check).toString(), messages);
                                    tenant.setmConvos(convos);
                                } else
                                    Toast.makeText(messagesActivity.this, "Empty message", Toast.LENGTH_SHORT).show();

                                recipient = accountNamesList.getItemAtPosition(check).toString();
                                tenant.updateDatabase();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                Toast.makeText(messagesActivity.this, "Message Sent", Toast.LENGTH_SHORT).show();
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

        list.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(messagesActivity.this, MessagesThreadActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("recipient", recipient);
                intent.putExtra("identifier", identifier);

                startActivity(intent);
                return true;
            }
        });

    }

    protected void onPause(){
        super.onPause();

        SharedPreferences.Editor ed = mPrefs.edit();
        message_prompt = (EditText) findViewById(R.id.messages_prompt);
        ed.putString("message", message_prompt.getText().toString());
        ed.commit();
    }
}
