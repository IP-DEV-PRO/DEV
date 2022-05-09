package com.devpro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.devpro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import models.User;

public class AdminPageActivity extends AppCompatActivity {

    ListView usersLV;
    private DatabaseReference mDatabase;
    List<String> userArray;
    ArrayAdapter<String> userArrayAdapter;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Admin Page");


        mDatabase = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");
        usersLV = findViewById(R.id.admin_page_list);
        userArray = new ArrayList<String>();
        user =  new User();
        userArrayAdapter = new ArrayAdapter( this, android.R.layout.simple_list_item_1,userArray);
        usersLV.setAdapter(userArrayAdapter);

        usersLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),userArray.get(i)+"",Toast.LENGTH_LONG).show();
            }
        });

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren())
                {
                    user = ds.getValue(User.class);
                    userArray.add("Username " + user.getUsername());
                }
                usersLV.setAdapter(userArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                changeActiviy(MainActivity.class, "admin");
                return true;
            case R.id.admin_menu_accounts:
                Toast.makeText(getApplicationContext(),"Accounts",Toast.LENGTH_LONG).show();
                return true;
            case R.id.admin_menu_companies:
                Toast.makeText(getApplicationContext(),"Companies",Toast.LENGTH_LONG).show();
                return true;
            case R.id.admin_menu_requests:
                Toast.makeText(getApplicationContext(),"Requests",Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeActiviy(Class activityClass, String userId) {
        Intent myIntent = new Intent(this, activityClass);
        myIntent.putExtra("key-user",userId);
        startActivity(myIntent);
    }

}