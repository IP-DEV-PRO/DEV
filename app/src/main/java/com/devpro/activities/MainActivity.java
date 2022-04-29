package com.devpro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.devpro.R;
import com.devpro.fragments.FirstPageFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Button registerButton;
    Button loginButton;

    void setListenersButtons() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActiviy(RegisterActivity.class);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActiviy(LoginActivity.class);
                //setContentView(R.layout.login_page);
            }
        });
    }

    private void changeActiviy(Class activityClass) {
        Intent myIntent = new Intent(this, activityClass);
        startActivity(myIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);
        registerButton = findViewById(R.id.firstPage_registerButton);
        loginButton = findViewById(R.id.firstPage_loginButton);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(this.getSupportActionBar()).hide();

        setListenersButtons();
    }


}