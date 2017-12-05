package com.example.brett.landlord_tenant;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by kristenwong on 12/4/17.
 */

public class Landlord {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private String mFirstName, mLastName, mUsername;
    private ArrayList<Tenant> mTenants;

    private static final String LANDLORD_TENANT_DEBUG_TAG = "landlord tenant app";

//   ** use this constructor when creating a new landlord to be added to the database, call addNewLandlord() to ensure that landlord is added
    Landlord(String first, String last, String userName) {
        mDatabase = null;
        mFirstName = first;
        mLastName = last;
        mUsername = userName;
        mTenants = new ArrayList<>();
    }

//    ** use this constructor to get an already existing landlord from the database
    Landlord(String username) {
        Log.d(LANDLORD_TENANT_DEBUG_TAG, "Landlord: onDataChanged called: username = " + username);
        mDatabase.child("users").child("landlords").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Landlord landlord = dataSnapshot.getValue(Landlord.class);

                if (landlord != null) {
                    mFirstName = landlord.getmFirstName();
                    mLastName = landlord.getmLastName();
                    mUsername = landlord.getmUsername();
                    mTenants = landlord.getmTenants();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(LANDLORD_TENANT_DEBUG_TAG, "Landlord retrieval error: ", databaseError.toException());
            }
        });
    }

    Landlord() {

    }

    public String getmFirstName() {
        return mFirstName;
    }

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
        updateDatabase();
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
        updateDatabase();
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
        updateDatabase();
    }

    public ArrayList<Tenant> getmTenants() {
        return mTenants;
    }

    public void setmTenants(ArrayList<Tenant> mTenants) {
        this.mTenants = mTenants;
        updateDatabase();
    }

    private void updateDatabase() {
        if (mDatabase == null) addNewLandlord();
        else mDatabase.child("users").child("landlords").child(mUsername).setValue(this);
    }

    public void addNewLandlord() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child("landlords").child(mUsername).setValue(this);
    }
}
