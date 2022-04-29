package com.devpro.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.devpro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

import models.Company;
import models.User;

public class RegisterCompanyActivity extends AppCompatActivity {
    EditText email, password, lastName, firstName;
    Button nextButton;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static final String TAG = "CustomAuthActivity";

    void setListenersButtons() {
        nextButton.setOnClickListener(view -> {
            saveInDatabase();
        });
    }

    private boolean saveInDatabase() {
        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();
        String lastNameString = lastName.getText().toString();
        String firstNameString = firstName.getText().toString();

        if (emailString.length() == 0 || passwordString.length() == 0 ||
                lastNameString.length() == 0 || firstNameString.length() == 0) {
            return false;
        }

        register(emailString, passwordString, lastNameString, firstNameString);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_company_page);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        actionBar.setBackgroundDrawable(new ColorDrawable(0xff0BCF5C));

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.register_comapny_page_email);
        password = findViewById(R.id.register_company_page_password);
        lastName = findViewById(R.id.register_company_page_last_name);
        firstName = findViewById(R.id.register_company_page_first_name);
        nextButton = findViewById(R.id.register_comapny_page_next_button);

        setListenersButtons();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeActiviy(Class activityClass) {
        Intent myIntent = new Intent(this, activityClass);
        startActivity(myIntent);
    }

    private void register(String email, String password, String lastName, String firstName) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        mDatabase = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies");

                        String companyId = mDatabase.push().getKey();
                        assert companyId != null;

                        Company registeredCompany = new Company(email, password, lastName, firstName);
                        mDatabase.child(companyId).setValue(registeredCompany);

                        Log.d(TAG, "createUserWithEmail:success");
                        updateUI_Ok();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(RegisterCompanyActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();

                        Log.w(TAG, "createUserWithEmail:failure", task.getException());

                        //updateUI(null);
                    }
                });
    }

    private void updateUI_Ok() {
        changeActiviy(RegisterCompanyActivityWithMap.class);
    }

}
