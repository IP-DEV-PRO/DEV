package com.devpro.fragments;

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
import com.devpro.activities.CompanyAdminHomePageActivity;
import com.devpro.activities.RegisterCompanyActivityWithMap;
import com.devpro.models.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class EditDetailsCompanyFragment   extends Fragment {
    Button add_location;
    AutoCompleteTextView add_service;
    TextInputLayout add_service_2;
    TextInputEditText add_service_4;
    String username;
    String companyName;

    public EditDetailsCompanyFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.company_homepage_editdetails, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        add_location = requireActivity().findViewById(R.id.company_add_location_button);
        add_service = requireActivity().findViewById(R.id.company_add_service2);
        add_service_2 = requireActivity().findViewById(R.id.company_add_service3);
        add_service_4 = requireActivity().findViewById(R.id.add_service_4);


        username = ((CompanyAdminHomePageActivity) requireActivity()).returnUsername();
        companyName = ((CompanyAdminHomePageActivity) requireActivity()).returnCompanyName();
        setListenersButtons();

        FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users")
                .child(username).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                User user = task.getResult().getValue(User.class);
                assert user != null;
                String[] array = user.getServices();
                String[] array2={"first","second item" ,"third item"};
                if (array != null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            R.layout.list_item, array);
                    add_service.setAdapter(adapter);
                } else {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            R.layout.list_item, array2);
                    add_service.setAdapter(adapter);
                }
            }
        });
        //String[] array={"first","second item" ,"third item"};


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void setListenersButtons() {
        add_location.setOnClickListener(view -> {
            changeActiviy(RegisterCompanyActivityWithMap.class);
        });
        add_service.setOnClickListener(view -> {
            System.out.println("AMDOSDMOSDOSMDOI0000000000------------------------------------------------------------");
        });

        add_service_2.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(add_service_4.getText().toString() + " d asdaosmdoasodmasmdoimasoidmoiasmdoimasoidmoiasd");
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