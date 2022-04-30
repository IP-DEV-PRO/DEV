package com.devpro.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.devpro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import models.User;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "CustomAuthActivity";

    EditText username, password, name;
    Button registerButton, registerCompanyButton;
    private DatabaseReference mDatabase;

    void setListenersButtons() {
        //registerButton.setOnClickListener(view -> register(username.getText().toString(),password.getText().toString()));
        registerButton.setOnClickListener(view -> register(username.getText().toString(),password.getText().toString(),
                name.getText().toString()));
        registerCompanyButton.setOnClickListener(view -> changeActiviy(RegisterCompanyActivity.class));
        //registerCompanyButton.setOnClickListener(view -> changeActiviy(RegisterCompanyActivityWithMap.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        actionBar.setBackgroundDrawable(new ColorDrawable(0xffBC3672));

        mAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.registerPage_username);
        password = findViewById(R.id.registerPage_password);
        name = findViewById(R.id.Name);
        registerCompanyButton = findViewById(R.id.registerPage_registerCompanyButton);
        registerButton = findViewById(R.id.registerPage_registerButton);

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

    private void register(String email, String password, String name) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        mDatabase = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");

                        String userId = mDatabase.push().getKey();
                        assert userId != null;

                        User registeredUser = new User(name, password, "no-last-name", "no-first-name", "no-telephone", email);
                        mDatabase.child(userId).setValue(registeredUser);

                        Log.d(TAG, "createUserWithEmail:success");
                        updateUI(userId);
                        Toast.makeText(getApplicationContext(),
                                "Register successful!!",
                                Toast.LENGTH_LONG)
                                .show();
                        // finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();

                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        finish();
                        //updateUI(null);
                    }
                });
    }

    private void changeActiviy(Class activityClass) {
        Intent myIntent = new Intent(this, activityClass);
        startActivity(myIntent);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    private void updateUI(String key) {
        Intent myIntent = new Intent(this, RegisterUserTwo.class);
        myIntent.putExtra("key-user", key);
        startActivity(myIntent);
    }
}
