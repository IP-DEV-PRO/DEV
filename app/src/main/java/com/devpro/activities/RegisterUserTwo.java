package com.devpro.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.devpro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUserTwo extends AppCompatActivity {

    EditText lastName, firstName, telephone;
    Button nextButton;
    private String userKey;

    void setListenersButtons() {
        nextButton.setOnClickListener(view -> {
            saveInDatabase();
        });
    }

    private void changeActiviy(Class activityClass) {
        Intent myIntent = new Intent(this, activityClass);
        startActivity(myIntent);
    }

    private boolean saveInDatabase() {
        String telephoneString = telephone.getText().toString();
        String lastNameString = lastName.getText().toString();
        String firstNameString = firstName.getText().toString();

        if (telephoneString.length() == 0 || lastNameString.length() == 0 ||
                firstNameString.length() == 0) {
            return false;
        }

        updateUser(lastNameString, firstNameString, telephoneString, userKey);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user_two);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        actionBar.setBackgroundDrawable(new ColorDrawable(0xff0BCF5C));

        Intent intent = getIntent();
        userKey = intent.getStringExtra("key-user");

        lastName = findViewById(R.id.register_user_two_lastname);
        firstName = findViewById(R.id.register_user_two_first_name);
        telephone =findViewById(R.id.register_user_two_telephoneNumber);
        nextButton = findViewById(R.id.register_user_two_nextButton);

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

    private void updateUser(String lastName, String firstName, String telephone, String key) {
        FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users").child(key).child("firstName").setValue(firstName);
        FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users").child(key).child("lastName").setValue(lastName);
        FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users").child(key).child("phone").setValue(telephone);
    }
}