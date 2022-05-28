package com.devpro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devpro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CompanyAdressActivity extends AppCompatActivity {

    EditText adr1_text, adr2_text, city_text, postal_text, country_text;
    Button register_button;
    String userId, companyName, firstName, lastName, phone, comp_reg_no;
    private DatabaseReference mDatabase;

    void setListenersButtons() {
        register_button.setOnClickListener(view -> sendRequest(adr1_text.getText().toString(), adr2_text.getText().toString(),
                                                                city_text.getText().toString(),postal_text.getText().toString(),
                                                                country_text.getText().toString()));
    }

    private void sendRequest(String adr1, String adr2, String city, String postal, String country) {

        mDatabase.child("admin").child("requests").child(String.valueOf(comp_reg_no)).child("owner_username").setValue(userId);
        mDatabase.child("admin").child("requests").child(String.valueOf(comp_reg_no)).child("company_name").setValue(companyName);
        mDatabase.child("admin").child("requests").child(String.valueOf(comp_reg_no)).child("registration_number").setValue(comp_reg_no);
        mDatabase.child("admin").child("requests").child(String.valueOf(comp_reg_no)).child("owner_first_name").setValue(firstName);
        mDatabase.child("admin").child("requests").child(String.valueOf(comp_reg_no)).child("owner_last_name").setValue(lastName);
        mDatabase.child("admin").child("requests").child(String.valueOf(comp_reg_no)).child("phone").setValue(phone);
        mDatabase.child("admin").child("requests").child(String.valueOf(comp_reg_no)).child("company_address").child("line1").setValue(adr1);
        mDatabase.child("admin").child("requests").child(String.valueOf(comp_reg_no)).child("company_address").child("line2").setValue(adr2);
        mDatabase.child("admin").child("requests").child(String.valueOf(comp_reg_no)).child("company_address").child("city").setValue(city);
        mDatabase.child("admin").child("requests").child(String.valueOf(comp_reg_no)).child("company_address").child("postal_code").setValue(postal);
        mDatabase.child("admin").child("requests").child(String.valueOf(comp_reg_no)).child("company_address").child("country").setValue(country);

        mDatabase.child(userId).child("company_address").child("line1").setValue(adr1);
        mDatabase.child(userId).child("company_address").child("line2").setValue(adr2);
        mDatabase.child(userId).child("company_address").child("city").setValue(city);
        mDatabase.child(userId).child("company_address").child("postal_code").setValue(postal);
        mDatabase.child(userId).child("company_address").child("country").setValue(country);
        Toast.makeText(getApplicationContext(),
                "Request sent to application admin",
                Toast.LENGTH_LONG)
                .show();

        changeActiviy(MainActivity.class,"");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_adress);
        Intent intent = getIntent();
        userId = intent.getStringExtra("key-user");
        companyName = intent.getStringExtra("name");
        firstName = intent.getStringExtra("first");
        lastName = intent.getStringExtra("last");
        phone = intent.getStringExtra("phone");
        comp_reg_no = intent.getStringExtra("cui");

        mDatabase = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");

        adr1_text = findViewById(R.id.comp_adress_adress_line1);
        adr2_text = findViewById(R.id.comp_adress_adress_line2);
        city_text = findViewById(R.id.comp_adress_adress_city);
        postal_text = findViewById(R.id.comp_adress_adress_postal);
        country_text = findViewById(R.id.comp_adress_adress_country);
        register_button = findViewById(R.id.comp_adress_adress_register);

        setListenersButtons();
    }

    private void changeActiviy(Class activityClass, String userId) {
        Intent myIntent = new Intent(this, activityClass);
        myIntent.putExtra("key-user",userId);
        startActivity(myIntent);
    }
}