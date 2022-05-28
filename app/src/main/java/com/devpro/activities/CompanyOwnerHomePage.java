package com.devpro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.devpro.R;

public class CompanyOwnerHomePage extends AppCompatActivity {

    Button edit_button, reg_admin_button,  view_button;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_owner_home_page);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        edit_button = findViewById(R.id.company_owner_homepage_edit);
        reg_admin_button = findViewById(R.id.company_owner_homepage_register);
        view_button = findViewById(R.id.company_owner_homepage_view);

        userId = getIntent().getStringExtra("key-user");

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActiviy(EditCompanyDetailsActivity.class, userId);
            }
        });
        reg_admin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActiviy(AddCompanyAdminActivity.class, userId);
            }
        });
    }

    private void changeActiviy(Class activityClass, String userId) {
        Intent myIntent = new Intent(this, activityClass);
        myIntent.putExtra("key-user", userId);
        startActivity(myIntent);
    }

}