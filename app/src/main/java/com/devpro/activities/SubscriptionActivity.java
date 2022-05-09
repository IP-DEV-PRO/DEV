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

import java.util.Date;

public class SubscriptionActivity extends AppCompatActivity {

    TextView fullname_text, active_text, start_text, end_text;
    Button paybutton;
    private DatabaseReference mDatabase;
    String userId;


    void setListenersButtons() {
        paybutton.setOnClickListener(view -> changeActiviy(PaymentActivity.class, userId));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        fullname_text = findViewById(R.id.subscription_fullname);
        active_text = findViewById(R.id.subscription_active);
        start_text = findViewById(R.id.subscription_start);
        end_text = findViewById(R.id.subscription_expiration);
        paybutton = findViewById(R.id.subscription_pay);
        mDatabase = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");

        userId = getIntent().getStringExtra("key-user");
        mDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String first_name = snapshot.child("firstName").getValue().toString();
                String last_name = snapshot.child("lastName").getValue().toString();
                fullname_text.setText("Name: " + first_name + " " + last_name);
                boolean active = Boolean.parseBoolean(snapshot.child("activesub").getValue().toString());
                String start =  snapshot.child("start").getValue().toString();
                String end =  snapshot.child("expiration").getValue().toString();
                if(active) {
                        active_text.setText("Subscription is paid");
                        start_text.setText("Start date is " + start);
                        end_text.setText("Expiration date is " + end);
                        paybutton.setVisibility(View.INVISIBLE);
                }
                else
                {
                    active_text.setText("No subscription");
                    paybutton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        setListenersButtons();
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
                changeActiviy(UserHomePage.class, userId);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
