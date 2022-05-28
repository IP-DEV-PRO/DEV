package com.devpro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devpro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RegisterCompanyTwo extends AppCompatActivity {

    EditText name_text, cui_text, first_text, last_text, phone_text;
    Button nextButton;
    private String userKey;
    private DatabaseReference mDatabase;

    void setListenersButtons() {
        nextButton.setOnClickListener(view -> next(userKey, name_text.getText().toString(),
                cui_text.getText().toString(), first_text.getText().toString(),
                last_text.getText().toString(),phone_text.getText().toString()));
    }

    private void next(String userKey, String name, String cui, String first, String last, String phone) {


        mDatabase.child(userKey).child("companyName").setValue(name);
        mDatabase.child(userKey).child("firstName").setValue(first);
        mDatabase.child(userKey).child("lastName").setValue(last);
        mDatabase.child(userKey).child("phone").setValue(phone);
        mDatabase.child(userKey).child("comp_reg_no").setValue(cui);
        mDatabase.child(userKey).child("accepted").setValue(0);

        Intent myIntent = new Intent(this, CompanyAdressActivity.class);
        myIntent.putExtra("key-user", userKey);
        myIntent.putExtra("name", name);
        myIntent.putExtra("cui", cui);
        myIntent.putExtra("first", first);
        myIntent.putExtra("last", last);
        myIntent.putExtra("phone", phone);
        startActivity(myIntent);

    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_company_two);
        Intent intent = getIntent();
        userKey = intent.getStringExtra("key-user");
        mDatabase = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");

        name_text = findViewById(R.id.register_company_two_name);
        cui_text = findViewById(R.id.register_company_two_cui);
        first_text = findViewById(R.id.register_company_two_firstname);
        last_text = findViewById(R.id.register_company_two_lastname);
        phone_text = findViewById(R.id.register_company_two_phone);
        nextButton = findViewById(R.id.register_company_two_next);

        setListenersButtons();
    }

    private void changeActiviy(Class activityClass, String userId) {
        Intent myIntent = new Intent(this, activityClass);
        myIntent.putExtra("key-user",userId);
        startActivity(myIntent);
    }
}