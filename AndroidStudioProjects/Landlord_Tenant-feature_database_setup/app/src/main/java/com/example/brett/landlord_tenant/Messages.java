package com.example.brett.landlord_tenant;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * Created by sanjaybhoir2002 on 12/17/17.
 */

public class Messages {
    private String mName;
    private HashMap<String, String> mMessages;

    private static final String LANDLORD_TENANT_DEBUG_TAG = "LandlordTenantApp";

    Messages(String name) {
        mName = name;
        mMessages = new HashMap<>();
        mMessages.put("--", "--" );
    }

    Messages() {
        mName = "";
        mMessages = new HashMap<>();
        mMessages.put("--", "--");
    }

    public String getmName() { return mName;}

    public void setmName(String name) { this.mName = name;}

    public void setmMessage(String message) { mMessages.put("message", message);}

    public HashMap<String, String> getmMessages() { return mMessages;}

    public void setmMessages(HashMap<String, String> messages) { this.mMessages = messages;}

    public void updateDatabase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child("landlords").child("conversations").child(mName).setValue(this);
    }
}
