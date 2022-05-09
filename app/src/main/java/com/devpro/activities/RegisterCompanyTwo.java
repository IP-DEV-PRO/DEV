package com.devpro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devpro.R;


public class RegisterCompanyTwo extends AppCompatActivity {

    EditText name_text, cui_text, first_text, last_text, phone_text;
    Button nextButton;
    private String userKey;

    void setListenersButtons() {
        nextButton.setOnClickListener(view -> sendRequest(userKey, name_text.getText().toString(),
                cui_text.getText().toString(), first_text.getText().toString(),
                last_text.getText().toString(),phone_text.getText().toString()));
    }

    private void sendRequest(String userKey, String name, String cui, String first, String last, String phone) {
        Toast.makeText(getApplicationContext(),
                "Request sent to application admin",
                Toast.LENGTH_LONG)
                .show();
        Intent myIntent = new Intent(this, RegisterCompanyActivityWithMap.class);
        myIntent.putExtra("userKey", userKey);
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

        name_text = findViewById(R.id.register_company_two_name);
        cui_text = findViewById(R.id.register_company_two_cui);
        first_text = findViewById(R.id.register_company_two_firstname);
        last_text = findViewById(R.id.register_company_two_lastname);
        phone_text = findViewById(R.id.register_company_two_phone);
        nextButton = findViewById(R.id.register_company_two_next);

        setListenersButtons();
    }
}