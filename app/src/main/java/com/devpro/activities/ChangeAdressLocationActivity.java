package com.devpro.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.devpro.R;

public class ChangeAdressLocationActivity extends AppCompatActivity {
    Button saveLocation;

    void setListenersButtons() {
        saveLocation.setOnClickListener(view -> finish());
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

        saveLocation = findViewById(R.id.save_adress_button);
        setListenersButtons();
    }
}
