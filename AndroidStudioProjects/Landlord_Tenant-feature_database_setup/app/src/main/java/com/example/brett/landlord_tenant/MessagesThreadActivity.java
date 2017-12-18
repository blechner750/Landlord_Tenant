package com.example.brett.landlord_tenant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MessagesThreadActivity extends AppCompatActivity {

    Button sendButton;

    SharedPreferences mPrefs;
    String message;
    String name ="";
    String recipient = "";
    String identifier = "";

    ListView messagesList;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference myRef = db.getReference();

    EditText message_prompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_thread);

        sendButton = findViewById(R.id.maint_button_2);
        messagesList = findViewById(R.id.messages_list_2);

        Intent intent = new Intent();
        Bundle extras = intent.getExtras();
        name = extras.getString("name");
        recipient = extras.getString("recipient");
        identifier = extras.getString("identifier");

        if (identifier.equals("tenant")) {
            final FirebaseListAdapter<Tenant> mAdapterTents;
            mAdapterTents = new FirebaseListAdapter<Tenant>(this, Tenant.class, android.R.layout.simple_list_item_1, myRef.child("users").child("tenants")) {
                @Override
                protected void populateView(View v, Tenant model, int position) {
                    String name = model.getmFirstName() + model.getmLastName();
                    ((TextView)v.findViewById(android.R.id.text1)).setText(name);
                }
            };

            messagesList.setAdapter(mAdapterTents);
            messagesList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
            mAdapterTents.notifyDataSetChanged();
        }
        else {
            final FirebaseListAdapter<Landlord> mAdapterLands;
            mAdapterLands = new FirebaseListAdapter<Landlord>(this, Landlord.class, android.R.layout.simple_list_item_1, myRef.child("users").child("landlords")) {
                @Override
                protected void populateView(View v, Landlord model, int position) {
                    String name = model.getmFirstName() + model.getmLastName();
                    ((TextView)v.findViewById(android.R.id.text1)).setText(name);
                }
            };

            messagesList.setAdapter(mAdapterLands);
            messagesList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
            mAdapterLands.notifyDataSetChanged();
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (identifier.equals("landlord")) {
                    DatabaseReference landlordRef = FirebaseDatabase.getInstance().getReference().child("users").child("landlords");
                    landlordRef.child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Landlord landlord = dataSnapshot.getValue(Landlord.class);
                            Log.d("LandlordTenantApp", "landlord retrieved from setLandlord: " + landlord.getmFirstName() + landlord.getmLastName());
                            if (landlord != null) {
                                HashMap<String, Messages> convos = landlord.getmConvos();
                                Messages messages = landlord.getMessages(recipient);

                                if (!message_prompt.getText().equals(null)) {
                                    messages.setmMessage(message_prompt.getText().toString());
                                    convos.put(recipient, messages);
                                    landlord.setmConvos(convos);
                                } else
                                    Toast.makeText(MessagesThreadActivity.this, "Empty message", Toast.LENGTH_SHORT).show();

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
                                Messages messages;
                                messages = tenant.getMessages(recipient);

                                if (!message_prompt.getText().equals(null)) {
                                    messages.setmMessage(message_prompt.getText().toString());
                                    convos.put(recipient, messages);
                                    tenant.setmConvos(convos);
                                } else
                                    Toast.makeText(MessagesThreadActivity.this, "Empty message", Toast.LENGTH_SHORT).show();

                                tenant.updateDatabase();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                Toast.makeText(MessagesThreadActivity.this, "Message Sent", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
