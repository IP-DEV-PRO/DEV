package com.devpro.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.devpro.R;
import com.devpro.fragments.AcceptedCompanyFragment;
import com.devpro.fragments.EditDetailsCompanyFragment;
import com.devpro.fragments.RequestsCompanyFragment;
import com.devpro.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CompanyAdminHomePageActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    Button add_location, add_service;
    TextInputEditText add_description, change_phone, change_email, change_password;

    String username;
    private DatabaseReference mDatabase;
    private String companyName;

    Fragment requestsFragment;
    Fragment acceptFragment;
    Fragment editFragment;

    void setListenersButtons() {
        //registerButton.setOnClickListener(view -> register(username.getText().toString(),password.getText().toString()));
        add_location.setOnClickListener(view -> {
            System.out.println("AMDOSDMOSDOSMDOI0000000000------------------------------------------------------------");
            //    changeActiviy(RegisterCompanyActivityWithMap.class);
        });

//        add_service.setOnClickListener(view -> registerUser(username.getText().toString(),password.getText().toString(),
//                email.getText().toString(),false));
        //registerCompanyButton.setOnClickListener(view -> changeActiviy(RegisterCompanyActivityWithMap.class));
    }

    private void changeActiviy(Class activityClass) {
        Intent myIntent = new Intent(this, activityClass);
        myIntent.putExtra("key-user", username);
        myIntent.putExtra("key-company", companyName);
        startActivity(myIntent);
    }

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

        Intent intent = getIntent();
        username = intent.getStringExtra("key-user");

        Bundle bundle = new Bundle();

        Fragment requestsFragment = new RequestsCompanyFragment();
        Fragment acceptFragment = new AcceptedCompanyFragment();
        Fragment editFragment = new EditDetailsCompanyFragment();

       // requestsFragment.setArguments(bundle);
       // acceptFragment.setArguments(bundle);

        mDatabase = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");
        mDatabase.child(username).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                User user = task.getResult().getValue(User.class);
                assert user != null;
                companyName = user.getCompanyName();
                bundle.putString("key-company", companyName);
                editFragment.setArguments(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.company_component, editFragment)
                        .commit();
            }
        });
        // add_location = editFragment.requireView().findViewById(R.id.company_add_location_button);
       /* add_service = findViewById(R.id.company_add_service);
        add_description = findViewById(R.id.add_description_text);
        change_phone = findViewById(R.id.add_phone_textInput);
        change_email = findViewById(R.id.add_email_Input);
        change_password = findViewById(R.id.add_password_input);
*/
        //setListenersButtons();

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
                    editFragment.setArguments(bundle);
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

    public String returnUsername() {
        return username;
    }

    public String returnCompanyName() {
        return companyName;
    }
}
