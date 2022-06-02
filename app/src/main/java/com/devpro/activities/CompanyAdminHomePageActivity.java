package com.devpro.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devpro.R;
import com.devpro.fragments.EditDetailsCompanyFragment;
import com.devpro.fragments.RequestsCompanyFragment;
import com.devpro.models.Request;
import com.devpro.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CompanyAdminHomePageActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    Button add_location, add_service;
    TextInputEditText add_description, change_phone, change_email, change_password;
    public RequestsDataAdapter requestsDataAdapter;
    public RequestsDataAdapter acceptedRequestsDataAdapter;

    String username;
    private DatabaseReference mDatabase;
    private String companyName;
    RecyclerView recyclerView, recyclerViewAccepted;
    LinearLayoutManager linearLayoutManager, linearLayoutManagerAccepted;

    Fragment requestsFragment;
    Fragment editFragment;
    boolean ok = false;
    boolean set1 = false;
    boolean set2 = false;

    void setListenersButtons() {
        //registerButton.setOnClickListener(view -> register(username.getText().toString(),password.getText().toString()));
        add_location.setOnClickListener(view -> {
            //    changeActiviy(RegisterCompanyActivityWithMap.class);
        });
    }

    public static Date StringToDate(String dob) throws ParseException {
        //Instantiating the SimpleDateFormat class
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        //Parsing the given String to Date object
        Date date = formatter.parse(dob);
        System.out.println("Date object value: " + date);
        return date;
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

        requestsFragment = new RequestsCompanyFragment();
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
                requestsFragment.setArguments(bundle);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.company_component, editFragment)
                        .commit();
            }
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.requests_company:
                    List<Request> requests = new ArrayList<Request>();
                    FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies")
                            .child(companyName).child("locationList").child(username).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (ok) {
                                if (snapshot.child("requests").exists()) {
                                    for (DataSnapshot request : snapshot.child("requests").getChildren()) {
                                        Request request1 = request.getValue(Request.class);
                                        assert request1 != null;
                                        if (!request1.isAccepted()) {
                                            requests.add(request.getValue(Request.class));
                                        }
                                    }

                                    setRequestsAdapter(requests);
                                    setupRecyclerView();

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
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

        ok = true;
    }


    public String returnUsername() {
        return username;
    }

    public String returnCompanyName() {
        return companyName;
    }

    public void setRequestsAdapter(List<Request> requests) {
        requestsDataAdapter = new RequestsDataAdapter(requests);
    }

    public void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView2);
            linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(requestsDataAdapter);

        SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onLeftClicked(int position) {
                FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies")
                        .child(companyName).child("locationList").child(username).child("requests").get().addOnCompleteListener(task -> {
                    ArrayList<Request> a = new ArrayList<>();
                    int i = 0;
                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        if (i != position) {
                            a.add(ds.getValue(Request.class));
                        }
                        i++;
                    }

                    FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies")
                            .child(companyName).child("locationList").child(username).child("requests").setValue(a);
                });

                requestsDataAdapter.requests.remove(position);
                linearLayoutManager.removeAllViews();
                List<Request> a = requestsDataAdapter.requests;
                requestsDataAdapter.requests.clear();
                requestsDataAdapter.notifyDataSetChanged();
                requestsDataAdapter.requests = a;
                requestsDataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onRightClicked(int position) {
                Request a = requestsDataAdapter.requests.get(position);
                a.setAccepted(true);

                FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies")
                        .child(companyName).child("locationList").child(username).child("requests").child(String.valueOf(position)).setValue(a);

                requestsDataAdapter.requests.remove(position);
                linearLayoutManager.removeAllViews();
                List<Request> b = requestsDataAdapter.requests;
                requestsDataAdapter.requests.clear();
                requestsDataAdapter.notifyDataSetChanged();
                requestsDataAdapter.requests = b;
                requestsDataAdapter.notifyDataSetChanged();
            }
        });
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
