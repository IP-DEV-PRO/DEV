package com.devpro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devpro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditCompanyDetailsActivity extends AppCompatActivity {

    EditText first_text, last_text, phone_text;
    Button saveChanges_button;
    String userId;
    private DatabaseReference mDatabase;

    void setListenersButtons() {
        saveChanges_button.setOnClickListener(view -> saveChanges(first_text.getText().toString(),
                                                                    last_text.getText().toString(),
                                                                    phone_text.getText().toString()));
    }

    private void saveChanges(String firstName, String lastName, String phone) {
        if(firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty())
            Toast.makeText(getApplicationContext(),"Please complete all fields",Toast.LENGTH_LONG).show();
        else
        {
            mDatabase.child(userId).child("firstName").setValue(firstName);
            mDatabase.child(userId).child("lastName").setValue(lastName);
            mDatabase.child(userId).child("phone").setValue(phone);
            changeActiviy(CompanyOwnerHomePage.class, userId);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_company_details);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        mDatabase = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");

        userId = getIntent().getStringExtra("key-user");
        first_text = findViewById(R.id.edit_comp_details_first);
        last_text = findViewById(R.id.edit_comp_details_last);
        phone_text = findViewById(R.id.edit_comp_details_phone);
        saveChanges_button = findViewById(R.id.edit_comp_save);

        mDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                first_text.setText(snapshot.child("firstName").getValue().toString());
                last_text.setText(snapshot.child("lastName").getValue().toString());
                phone_text.setText(snapshot.child("phone").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        setListenersButtons();

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                changeActiviy(CompanyOwnerHomePage.class, userId);
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