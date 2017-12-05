package com.example.brett.landlord_tenant;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by kristenwong on 12/4/17.
 */

public class DBManager {
    DatabaseReference mDatabase;

    DBManager(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


}


