package com.devpro.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.devpro.R;
import com.devpro.fragments.AcceptedCompanyFragment;
import com.devpro.fragments.EditDetailsCompanyFragment;
import com.devpro.fragments.RequestsCompanyFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CompanyAdminHomePageActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_admin_home_page);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        Fragment mapFragment = getSupportFragmentManager()
                .findFragmentById(R.id.company_component);
        assert mapFragment != null;

        bottomNavigationView = findViewById(R.id.bottom_navigation_company);

        Fragment requestsFragment = new RequestsCompanyFragment();
        Fragment acceptFragment = new AcceptedCompanyFragment();
        Fragment editFragment = new EditDetailsCompanyFragment();


        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.acceptedRequests_company:
                    //mapFragment.requireView().setBackgroundColor(Color.GREEN);
                    //changeActiviy(EditAccountActivity.class, userId);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.company_component, acceptFragment)
                            .commit();

                    return true;
                case R.id.requests_company:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.company_component, requestsFragment)
                            .commit();

                    //changeActiviy(SubscriptionActivity.class, userId);
                    return true;
                case R.id.edit_details_comapany_nav:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.company_component, editFragment)
                            .commit();

                    return true;
            }
            return false;
        });
        //editare informatii
        //cereri
        //cereri acceptare
    }
}
