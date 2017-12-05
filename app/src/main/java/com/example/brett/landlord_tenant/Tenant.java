package com.example.brett.landlord_tenant;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by kristenwong on 12/4/17.
 */

public class Tenant {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private String mFirstName;
    private String mLastName;
    private String mUserName;
    private String mLandlordUserName;

//    ** We might not need to use UUIDs **
    private UUID mUUID, mLandlordUUID;

    private static final String LANDLORD_TENANT_DEBUG_TAG = "landlord tenant app";

//    ** use this constructor when creating a new tenant to be added to the database, call addNewTenant() to ensure that tenant is added
    Tenant(String first, String last, String userName) {
        mDatabase = null;
        mFirstName = first;
        mLastName = last;
        mUserName = userName;
    }

//    ** use this constructor to get an already existing tenant from the database
    Tenant(String username) {
        mDatabase.child("users").child("tenants").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tenant tenant = dataSnapshot.getValue(Tenant.class);
                mFirstName = tenant.getmFirstName();
                mLastName = tenant.getmLastName();
                mUserName = tenant.getmUserName();
                mUUID = tenant.getmUUID();
                mLandlordUserName = tenant.getmLandlordUserName();
                mLandlordUUID = tenant.getmLandlordUUID();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(LANDLORD_TENANT_DEBUG_TAG, "Tenant retrieval error: ", databaseError.toException());
            }
        });
    }

    Tenant() {
        mDatabase = null;
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

    public UUID getmUUID() {
        return mUUID;
    }

    public void setmUUID(UUID mUUID) {
        this.mUUID = mUUID;
        updateDatabase();
    }

    public UUID getmLandlordUUID() {
        return mLandlordUUID;
    }

    public void setmLandlordUUID(UUID mLandlordUUID) {
        this.mLandlordUUID = mLandlordUUID;
        updateDatabase();
    }

    public String getmLandlordUserName() {
        return mLandlordUserName;
    }

    public void setmLandlordUserName(String mLandlordUserName) {
        Landlord landlord = new Landlord(mLandlordUserName);
        if (landlord.getmFirstName() != null && landlord.getmLastName() != null && landlord.getmUsername() != null){
            ArrayList<Tenant> tenants = landlord.getmTenants();
            tenants.add(this);
            landlord.setmTenants(tenants);

            this.mLandlordUserName = mLandlordUserName;
            updateDatabase();
        } else {
            Log.d(LANDLORD_TENANT_DEBUG_TAG, "Error setting landlord to tenant - landlord does not exist in database.");
        }


    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
        updateDatabase();
    }

    private void updateDatabase() {
        if (mDatabase == null) addNewTenant();
        else mDatabase.child("users").child("tenants").child(mUserName).setValue(this);
    }

    public void addNewTenant() {
        if (mDatabase == null) mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child("tenants").child(mUserName).setValue(this);
    }
}
