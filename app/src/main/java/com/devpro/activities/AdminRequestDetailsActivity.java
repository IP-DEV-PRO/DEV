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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class AdminRequestDetailsActivity extends AppCompatActivity {
    TextView company_name, owner_first_name, owner_last_name, phone, registration_number;
    Button acceptButton, denyButton;
    private DatabaseReference mDatabase;
    String requestId;

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
                company_name.setText("Company name: " + Objects.requireNonNull(snapshot.child("company_name").getValue()));
                owner_first_name.setText("Owner first name: " + Objects.requireNonNull(snapshot.child("owner_first_name").getValue()).toString());
                owner_last_name.setText("Owner last name: " + Objects.requireNonNull(snapshot.child("owner_last_name").getValue()).toString());
                phone.setText("Phone: " + Objects.requireNonNull(snapshot.child("phone").getValue()).toString());
                registration_number.setText("Registration number: " + Objects.requireNonNull(snapshot.child("registration_number").getValue()).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                // TODO ADD COMPANY IN COMPANY DB AND DELETE REQUEST

                //mDatabase.child(userId).child("blocked").setValue(true);
            }
        });

        denyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO

                finish();
                //mDatabase.child(userId).child("blocked").setValue(true);
            }
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
