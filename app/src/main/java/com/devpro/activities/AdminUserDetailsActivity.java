package com.devpro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.devpro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminUserDetailsActivity extends AppCompatActivity {

    TextView fullname_text, email_text, phone_text, sub_text, date_text;
    Button banButton;
    private DatabaseReference mDatabase;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_details);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        mDatabase = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");
        fullname_text = findViewById(R.id.admin_user_details_fullname);
        email_text = findViewById(R.id.admin_user_details_email);
        phone_text = findViewById(R.id.admin_user_details_phone);
        sub_text = findViewById(R.id.admin_user_details_subscription);
        date_text = findViewById(R.id.admin_user_details_date);
        banButton = findViewById(R.id.admin_user_details_ban);
        userId = getIntent().getStringExtra("key-user");

        mDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String firstName = snapshot.child("firstName").getValue().toString();
                String lastName = snapshot.child("lastName").getValue().toString();
                String email = snapshot.child("e_mail").getValue().toString();
                String phone = snapshot.child("phone").getValue().toString();
                String reg_date = snapshot.child("reg_date").getValue().toString();
                boolean active = Boolean.parseBoolean(snapshot.child("activesub").getValue().toString());
                fullname_text.setText("Full name: " + firstName + " " + lastName);
                email_text.setText("E-mail: " + email);
                phone_text.setText("Phone: " + phone);
                if(active)
                    sub_text.setText("User has active subscription");
                else
                    sub_text.setText("No subscription");
                date_text.setText("Registration date: " + reg_date);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        banButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child(userId).child("blocked").setValue(true);
            }
        });
    }

    private void changeActiviy(Class activityClass, String userId) {
        Intent myIntent = new Intent(this, activityClass);
        myIntent.putExtra("key-user",userId);
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