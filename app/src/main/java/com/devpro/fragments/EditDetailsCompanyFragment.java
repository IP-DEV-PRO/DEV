package com.devpro.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.devpro.R;
import com.devpro.activities.AddCompanyProfilePicture;
import com.devpro.activities.CompanyAdminHomePageActivity;
import com.devpro.activities.RegisterCompanyActivityWithMap;
import com.devpro.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class EditDetailsCompanyFragment extends Fragment {
    @SuppressLint("StaticFieldLeak")
    public static EditDetailsCompanyFragment instance = null;
    Button add_location, add_image;
    Button remove_service;
    AutoCompleteTextView add_service;
    TextInputLayout add_service_2;
    TextInputEditText add_service_4;
    String username;
    String companyName;
    String currentSelectedService = null;
    Bundle bundle;
    Button nextButton_editDetails;

    public EditDetailsCompanyFragment() {
        instance = this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.company_homepage_editdetails, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        add_location = requireActivity().findViewById(R.id.company_add_location_button);
        add_image = requireActivity().findViewById(R.id.company_add_location_add_image);
        add_service = requireActivity().findViewById(R.id.company_add_service2);
        add_service_2 = requireActivity().findViewById(R.id.company_add_service3);
        add_service_4 = requireActivity().findViewById(R.id.add_service_4);
        remove_service = requireActivity().findViewById(R.id.remove_service);
        nextButton_editDetails = requireActivity().findViewById(R.id.nextButton_editDetails);


        username = ((CompanyAdminHomePageActivity) requireActivity()).returnUsername();
        companyName = ((CompanyAdminHomePageActivity) requireActivity()).returnCompanyName();
        setListenersButtons();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = this.getArguments();
        if (bundle != null) {
            companyName = bundle.getString("key-company");
            username = ((CompanyAdminHomePageActivity) requireActivity()).returnUsername();

            FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies")
                    .child(companyName).child("locationList").child(username).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if (snapshot.child("services").exists()) {
                        ArrayList<String> array = new ArrayList<>();
                        for (DataSnapshot service_name : snapshot.child("services").getChildren()) {
                            array.add(Objects.requireNonNull(service_name.getValue()).toString());
                        }

                        if (array.size() > 0) {
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                                    R.layout.list_item, array);
                            add_service.setAdapter(adapter);
                        } else {
                            array.add("");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.list_item, array);
                            add_service.setAdapter(adapter);
                        }
                    }
                }
            });
        }
    }


    private void setListenersButtons() {
        add_location.setOnClickListener(view -> {
            changeActiviy(RegisterCompanyActivityWithMap.class);
        });

        add_service.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {
                currentSelectedService = add_service.getText().toString();
            }
        });

        nextButton_editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment editDetailsCompanyFragment2 = new EditDetailsCompanyFragment2();
                editDetailsCompanyFragment2.setArguments(bundle);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.company_component, editDetailsCompanyFragment2)
                        .commit();
            }
        });

        remove_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_service.setText("");

                FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies")
                        .child(companyName).child("locationList").child(username).child("services").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (currentSelectedService != null) {
                            ArrayList<String> a = new ArrayList<>();
                            for (DataSnapshot ds : task.getResult().getChildren()) {
                                if (ds.getValue().toString().compareTo(currentSelectedService) == 0) {
                                    //     FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies")
                                    //             .child(companyName).child("locationList").child(username).child("services").child(Objects.requireNonNull(ds.getKey())).removeValue();
                                } else {
                                    a.add(ds.getValue().toString());
                                }
                            }
                            currentSelectedService = null;
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.list_item, a);
                            add_service.setAdapter(adapter);
                            FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies")
                                    .child(companyName).child("locationList").child(username).child("services").setValue(a);
                        }
                    }
                });

            }
        });

        add_image.setOnClickListener(view -> changeActiviy(AddCompanyProfilePicture.class));

        add_service_2.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies")
                        .child(companyName).child("locationList").child(username).child("services").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            //
                        } else {
                            ArrayList<String> a = new ArrayList<>();
                            for (DataSnapshot ds : task.getResult().getChildren()) {
                                a.add(Objects.requireNonNull(ds.getValue()).toString());

                            }
                            a.add(Objects.requireNonNull(add_service_4.getText()).toString());
                            add_service_4.setText("");
                            FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies")
                                    .child(companyName).child("locationList").child(username).child("services").setValue(a);
                        }
                    }
                });
            }
        });
    }

    private void changeActiviy(Class activityClass) {
        Intent myIntent = new Intent(getActivity(), activityClass);
        FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users")
                .child(username).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                User user = task.getResult().getValue(User.class);
                assert user != null;
                myIntent.putExtra("key-user", username);
                myIntent.putExtra("key-company", user.getCompanyName());
                startActivity(myIntent);
            }
        });
    }
}