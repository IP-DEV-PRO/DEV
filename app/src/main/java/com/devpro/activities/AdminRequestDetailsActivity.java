package com.devpro.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.devpro.R;
import com.devpro.models.Company;
import com.devpro.models.CompanyType;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class AdminRequestDetailsActivity extends AppCompatActivity {
    TextView company_name, owner_first_name, owner_last_name, phone, registration_number;
    Button acceptButton, denyButton;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase_for_company;

    String requestId;
    String company_name_s, owner_first_name_s, owner_last_name_s, phone_s, registration_number_s, owner_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_request_page);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        mDatabase = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users")
                .child("admin").child("requests");
        mDatabase_for_company = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies");

        company_name = findViewById(R.id.admin_request_details_company_name);
        owner_first_name = findViewById(R.id.admin_request_details_owner_first_name);
        owner_last_name = findViewById(R.id.admin_request_details_owner_last_name);
        phone = findViewById(R.id.admin_request_details_phone);
        registration_number = findViewById(R.id.admin_request_details_registration_nr);
        acceptButton = findViewById(R.id.admin_request_details_accept_button);
        denyButton = findViewById(R.id.admin_request_details_deny_button);
        requestId = getIntent().getStringExtra("key-user");

        mDatabase.child(requestId).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                company_name_s = Objects.requireNonNull(snapshot.child("company_name").getValue()).toString();
                owner_first_name_s = Objects.requireNonNull(snapshot.child("owner_first_name").getValue()).toString();
                owner_last_name_s = Objects.requireNonNull(snapshot.child("owner_last_name").getValue()).toString();
                phone_s = Objects.requireNonNull(snapshot.child("phone").getValue()).toString();
                registration_number_s = Objects.requireNonNull(snapshot.child("registration_number").getValue()).toString();
                owner_username = Objects.requireNonNull(snapshot.child("owner_username").getValue()).toString();

                company_name.setText("Company name: " + company_name_s);
                owner_first_name.setText("Owner first name: " + owner_first_name_s);
                owner_last_name.setText("Owner last name: " + owner_last_name_s);
                phone.setText("Phone: " + phone_s);
                registration_number.setText("Registration number: " + registration_number_s);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Company company = new Company();
                company.setCompanyType(CompanyType.NONE);
                company.setFirstName(owner_first_name_s);
                company.setLastName(owner_last_name_s);
                company.setPhone(phone_s);
                company.setCui(registration_number_s);
                company.setLocationList(new ArrayList<>());

                mDatabase_for_company.child(company_name_s).setValue(company);
                FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").
                        getReference("users").child(owner_username).child("accepted").setValue(1);
                mDatabase.child(requestId).removeValue();
                finish();
            }
        });

        denyButton.setOnClickListener(view -> {
            FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").
                    getReference("users").child(owner_username).child("accepted").setValue(2);
            mDatabase.child(requestId).removeValue();
            finish();
        });
    }

    private void changeActiviy(Class activityClass, String userId) {
        Intent myIntent = new Intent(this, activityClass);
        myIntent.putExtra("key-user", userId);
        startActivity(myIntent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                changeActiviy(AdminPageActivity.class, "admin");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
