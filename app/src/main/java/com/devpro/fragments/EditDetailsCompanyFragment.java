package com.devpro.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.devpro.R;
import com.devpro.activities.CompanyAdminHomePageActivity;
import com.devpro.activities.RegisterCompanyActivityWithMap;

import java.util.Objects;

public class EditDetailsCompanyFragment   extends Fragment {
    Button add_location, add_service;
    String username;
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
        add_service = requireActivity().findViewById(R.id.company_add_service);

        username = ((CompanyAdminHomePageActivity) requireActivity()).returnUsername();

        setListenersButtons();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setListenersButtons() {
        add_location.setOnClickListener(view -> {
            //System.out.println("AMDOSDMOSDOSMDOI0000000000------------------------------------------------------------");
            changeActiviy(RegisterCompanyActivityWithMap.class);
        });
    }

    private void changeActiviy(Class activityClass) {
        Intent myIntent = new Intent(getActivity(), activityClass);
        myIntent.putExtra("key-user", username);
        startActivity(myIntent);
    }
}