package com.devpro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.devpro.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    Button registerButton;
    Button loginButton;



    void setListenersButtons() {
        registerButton.setOnClickListener(view -> {
            changeActiviy(RegisterActivity.class);
            //changeActiviy(RegisterCompanyActivityWithMap.class);
        });

        loginButton.setOnClickListener(view -> {
            changeActiviy(LoginActivity.class);
            //setContentView(R.layout.login_page);
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


        Objects.requireNonNull(this.getSupportActionBar()).hide();

        setListenersButtons();
    }


}