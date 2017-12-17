package com.example.brett.landlord_tenant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TenantRentActivity extends AppCompatActivity {
    private ListView mBillsListView;
    private String mTenantUserName;
    private ArrayList<Bill> mBillsList;
    private BillListAdapter mBillListAdapter;
    private TextView mBillStatusText;
    private int mUnpaidCount;

    private static final String LANDLORD_TENANT_DEBUG_TAG = "LandlordTenantApp";
    private static final String KEY_TENANT_USERNAME = "tenantusername";
    private static final DatabaseReference REF = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_rent);
        getSupportActionBar().setTitle("Rent");

        mBillsList = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            mTenantUserName = extras.getString(KEY_TENANT_USERNAME);
            Log.d(LANDLORD_TENANT_DEBUG_TAG, "TenantRentActivity: onCreate called for tenant: " + mTenantUserName);
        }

        mBillsListView = (ListView) findViewById(R.id.listview_tenant_bill_history);
        mBillListAdapter = new BillListAdapter(mBillsList, getApplicationContext());
        mBillListAdapter.setmIsLandlord(false);
        mBillsListView.setAdapter(mBillListAdapter);

        mBillStatusText = (TextView) findViewById(R.id.text_bill_status);
        mBillStatusText.setText("");

        getBillsList();
    }

    private void getBillsList() {
        REF
                .child("users")
                .child("tenants")
                .child(mTenantUserName)
                .child("mBills")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mBillsList.clear();
                        mUnpaidCount = 0;

                        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Bill bill = snapshot.getValue(Bill.class);
                            mBillsList.add(bill);
                            if (!bill.ismIsPaid()) mUnpaidCount ++;
                        }
                        mBillListAdapter.updateList(mBillsList);

                        String billStatus = "";
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
