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

public class RegisterUserTwo extends AppCompatActivity {

    EditText username, password;
    Button nextButton;
    private FirebaseAuth mAuth;

    void setListenersButtons() {

    }

    private void changeActiviy(Class activityClass) {
        Intent myIntent = new Intent(this, activityClass);
        startActivity(myIntent);
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

//        mAuth = FirebaseAuth.getInstance();
//        username = findViewById(R.id.);
//        password = findViewById(R.id.password);
//        loginButton = findViewById(R.id.loginPage_loginButton);
//        setListenersButtons();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}