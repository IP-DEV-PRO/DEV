package com.devpro.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.devpro.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeAdressLocationActivity extends AppCompatActivity {

    EditText adr1_text, adr2_text, city_text, postal_text, country_text;
    Button saveLocation;
    String userId, companyId;
    private DatabaseReference mDatabase;

    void setListenersButtons() {
        saveLocation.setOnClickListener(view -> setLocation(adr1_text.getText().toString(),
                adr2_text.getText().toString(),
                city_text.getText().toString(),
                postal_text.getText().toString(),
                country_text.getText().toString()));
    }

    private void setLocation(String adr1, String adr2, String city, String postal, String country) {
        mDatabase.child(companyId).child("locationList").child(userId).child("line1").setValue(adr1);
        mDatabase.child(companyId).child("locationList").child(userId).child("line2").setValue(adr2);
        mDatabase.child(companyId).child("locationList").child(userId).child("city").setValue(city);
        mDatabase.child(companyId).child("locationList").child(userId).child("postal_code").setValue(postal);
        mDatabase.child(companyId).child("locationList").child(userId).child("country").setValue(country);
        Toast.makeText(getApplicationContext(),
                "Location updated",
                Toast.LENGTH_LONG)
                .show();

        changeActiviy(CompanyAdminHomePageActivity.class, userId, companyId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_company_adress);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Change Adress");
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFF3F51B5));

        mDatabase = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies");

        Intent intent = getIntent();
        userId = intent.getStringExtra("key-user");
        companyId = intent.getStringExtra("key-company");

        adr1_text = findViewById(R.id.save_addr_line1);
        adr2_text = findViewById(R.id.save_addr_line2);
        city_text = findViewById(R.id.sava_addr_city);
        postal_text = findViewById(R.id.save_addr_postal);
        country_text = findViewById(R.id.save_addr_country);
        saveLocation = findViewById(R.id.save_adress_button);

        setListenersButtons();
    }

    private void changeActiviy(Class activityClass, String usernameKey, String companyKey) {
        Intent myIntent = new Intent(this, activityClass);
        myIntent.putExtra("key-user", usernameKey);
        myIntent.putExtra("key-company", companyKey);
        startActivity(myIntent);
    }
}
