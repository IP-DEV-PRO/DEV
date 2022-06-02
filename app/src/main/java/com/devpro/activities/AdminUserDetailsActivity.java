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
import android.widget.Toast;

import com.devpro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminUserDetailsActivity extends AppCompatActivity {

    TextView fullname_text, email_text, phone_text, sub_text, date_text, banned_text;
    Button banButton, unbanButton;
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
        banned_text = findViewById(R.id.admin_user_details_banned);
        banButton = findViewById(R.id.admin_user_details_ban);
        unbanButton = findViewById(R.id.admin_user_details_unban);

        userId = getIntent().getStringExtra("key-user");

        mDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Integer role = Integer.parseInt(snapshot.child("role").getValue().toString());

                if(role == 0) {
                    String firstName = snapshot.child("firstName").getValue().toString();
                    String lastName = snapshot.child("lastName").getValue().toString();
                    String email = snapshot.child("e_mail").getValue().toString();
                    String phone = snapshot.child("phone").getValue().toString();
                    boolean active = Boolean.parseBoolean(snapshot.child("sub_active").getValue().toString());
                    fullname_text.setText("Full name: " + firstName + " " + lastName);
                    email_text.setText("E-mail: " + email);
                    phone_text.setText("Phone: " + phone);
                    if (active)
                        sub_text.setText("User has active subscription");
                    else
                        sub_text.setText("No subscription");
                    String reg_date = snapshot.child("reg_date").getValue().toString();
                    date_text.setText("Registration date: " + reg_date);
                    boolean banned = Boolean.parseBoolean(snapshot.child("blocked").getValue().toString());
                    if(banned)
                        banned_text.setText("Account Banned");
                    else
                        banned_text.setText("Active account");
                }
                else {
                    String email = snapshot.child("e_mail").getValue().toString();
                    email_text.setText("E-mail: " + email);
                    String companyName = snapshot.child("companyName").getValue().toString();
                    phone_text.setText("Company name: " + companyName);
                    String reg_date = snapshot.child("reg_date").getValue().toString();
                    sub_text.setText("Registration date: " + reg_date);
                    boolean banned = Boolean.parseBoolean(snapshot.child("blocked").getValue().toString());
                    if(banned)
                        date_text.setText("Account Banned");
                    else
                        date_text.setText("Active account");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        banButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child(userId).child("blocked").setValue(true);
                Toast.makeText(getApplicationContext(), "Account " + userId + " has been banned", Toast.LENGTH_LONG).show();
                changeActiviy(AdminPageActivity.class, "admin");
            }
        });
        unbanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child(userId).child("blocked").setValue(false);
                Toast.makeText(getApplicationContext(), "Account " + userId + " has been unbanned", Toast.LENGTH_LONG).show();
                changeActiviy(AdminPageActivity.class, "admin");
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