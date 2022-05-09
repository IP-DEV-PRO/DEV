package com.devpro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.devpro.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class UserHomePage extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private DatabaseReference mDatabase;
    static String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");


        userId = getIntent().getStringExtra("key-user");
        System.out.println(userId);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        mDatabase = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_navigation_edit:
                        changeActiviy(EditAccountActivity.class, userId);
                        return true;
                    case R.id.bottom_navigation_subscription:
                        changeActiviy(SubscriptionActivity.class, userId);
                        return true;
                    case R.id.bottom_navigation_requests:
                        return true;
                }
                return false;
            }
        });
    }

    private void changeActiviy(Class activityClass, String userId) {
        Intent myIntent = new Intent(this, activityClass);
        myIntent.putExtra("key-user",userId);
        startActivity(myIntent);
    }

}