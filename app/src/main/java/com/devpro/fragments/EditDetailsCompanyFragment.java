package com.devpro.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.devpro.models.Company;
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

    public EditDetailsCompanyFragment() {
        instance = this;
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
        add_image = requireActivity().findViewById(R.id.company_add_location_add_image);
        add_service = requireActivity().findViewById(R.id.company_add_service2);
        add_service_2 = requireActivity().findViewById(R.id.company_add_service3);
        add_service_4 = requireActivity().findViewById(R.id.add_service_4);
        remove_service = requireActivity().findViewById(R.id.remove_service);


        username = ((CompanyAdminHomePageActivity) requireActivity()).returnUsername();
        companyName = ((CompanyAdminHomePageActivity) requireActivity()).returnCompanyName();
        setListenersButtons();

        /*FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("company")
                .child(companyName).child("locationList").child(username).child("services").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                Company company = task.getResult().getValue();
                assert user != null;
                ArrayList<String> array = user.getServices();
                String[] array2={"first","second item" ,"third item"};
                System.out.println(array + " d-asldasldasldpasdp,asdo,as0-o3049r39858432623467327847923978467236784662378462398");
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
        });*/

        System.out.println(companyName + " " + username);
        if (companyName != null && username != null) {
        }
        //String[] array={"first","second item" ,"third item"};


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("AICI");
        Bundle bundle = new Bundle();
        bundle = this.getArguments();
        if (bundle != null) {
            companyName = bundle.getString("key-company");
            System.out.println(companyName + " dasddadsadasdadsa4444444");
            username = ((CompanyAdminHomePageActivity) requireActivity()).returnUsername();

            System.out.println(username + " dasdad");
            FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies")
                    .child(companyName).child("locationList").child(username).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child("services").exists()) {
                        System.out.println("gaga");
                        //String[]array = new String[];
                        ArrayList<String> array = new ArrayList<>();
                        for (DataSnapshot service_name : snapshot.child("services").getChildren()) {
                            array.add(service_name.getValue().toString());
                            // System.out.println("dasda " +  service_name.getValue());
                            /*for (DataSnapshot ds : snapshot.getChildren()) {
                                System.out.println("dasda " + ds.getKey());

                            }*/
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                R.layout.list_item, array);
                        add_service.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

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
                //System.out.println("AMDOSDMOSDOSMDOI0000000000------------------------------------------------------------ " + add_service.getText().toString());
                currentSelectedService = add_service.getText().toString();
            }
        });

        remove_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                a.add(ds.getValue().toString());

                            }
                            a.add(add_service_4.getText().toString());
                            FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies")
                                    .child(companyName).child("locationList").child(username).child("services").setValue(a);
                        }
                    }
                });

                        /*.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<String> a = new ArrayList<>();
                        for (DataSnapshot ds : snapshot.getChildren()){
                           a.add(ds.getValue().toString());
                        }

                        a.add(add_service_4.getText().toString());
                        FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies")
                                .child(companyName).child("locationList").child(username).child("services").setValue(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/
                // a.add(add_service_4.getText().toString());
                //    FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies")
                //          .child(companyName).child("locationList").child(username).child("services").setValue(new ArrayList<>().add("nana"));
                //     FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies")
                //             .child(companyName).child("locationList").child(username).child("services").child(add_service_4.getText().toString()).setValue(0);
                //   System.out.println(add_service_4.getText().toString() + " d asdaosmdoasodmasmdoimasoidmoiasmdoimasoidmoiasd");
                //System.out.println(");
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