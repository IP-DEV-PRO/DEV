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

    Button edit_button, reg_admin_button,  view_button, change_adr_button;
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
        change_adr_button = findViewById(R.id.company_owner_homepage_changeaddr);

        userId = getIntent().getStringExtra("key-user");

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActiviy(EditCompanyDetailsActivity.class, userId, -1);
            }
        });
        reg_admin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActiviy(AddCompanyAdminActivity.class, userId, -1);
            }
        });

        change_adr_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActiviy(CompanyAdressActivity.class, userId, 1);
            }
        });
    }

    private void changeActiviy(Class activityClass, String userId, int back_flag) {
        Intent myIntent = new Intent(this, activityClass);
        myIntent.putExtra("key-user", userId);
        if(back_flag != -1)
            myIntent.putExtra("back_flag",1);
        startActivity(myIntent);
    }

}