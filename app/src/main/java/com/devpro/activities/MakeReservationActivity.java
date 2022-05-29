package com.devpro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.devpro.R;
import com.google.firebase.database.DatabaseReference;

public class MakeReservationActivity extends AppCompatActivity {

    String userId, ownerKey;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reservation);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        userId = getIntent().getStringExtra("key-user");
        ownerKey = getIntent().getStringExtra("key-owner");
        System.out.println(userId + " " + ownerKey);
    }

    private void changeActiviy(Class activityClass, String usernameKey) {
        Intent myIntent = new Intent(this, activityClass);
        myIntent.putExtra("key-user", usernameKey);
        startActivity(myIntent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                changeActiviy(UserHomePage.class, userId);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}