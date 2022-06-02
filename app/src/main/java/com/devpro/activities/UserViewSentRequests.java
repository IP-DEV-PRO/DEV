package com.devpro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.devpro.R;
import com.devpro.models.Request;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserViewSentRequests extends AppCompatActivity {


    ListView requestsLV;
    static String userId;
    static int ok;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_sent_requests);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        userId = getIntent().getStringExtra("key-user");
        mDatabase =  FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").
                getReference("users").child(userId).child("requests");

        requestsLV = findViewById(R.id.user_sent_requests);

        List<String> requestArray = new ArrayList<>();
        ArrayAdapter<String> requestArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, requestArray);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Request request = ds.getValue(Request.class);
                    if(request.isAccepted())
                        requestArray.add("Company: " + request.getCompanyName() + "\n" +
                            "Service: " + request.getService() + "\n"
                            +"Date: " + request.getDate() + "\n" +
                            "Interval: " + request.getStartTime() +"-" + request.getEndTime()+ "\n" +
                            "Request accepted");
                    else
                        requestArray.add("Company: " + request.getCompanyName() + "\n" +
                                "Service: " + request.getService() + "\n"
                                +"Date: " + request.getDate() + "\n" +
                                "Interval: " + request.getStartTime() +"-" + request.getEndTime()+ "\n" +
                                "Request not accepted");
                }
                requestsLV.setAdapter(requestArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                changeActiviy(UserHomePage.class, userId);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeActiviy(Class activityClass, String userId) {
        Intent myIntent = new Intent(this, activityClass);
        myIntent.putExtra("key-user", userId);
        startActivity(myIntent);
    }
}