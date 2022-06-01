package com.devpro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devpro.R;
import com.devpro.activities.CompanyAdminHomePageActivity;
import com.devpro.models.Request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RequestsCompanyFragment extends Fragment {
    CardView cardItem;
    String companyName, username;

    public RequestsCompanyFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.company_location_requests_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //cardItem = requireActivity().findViewById(R.id.cardView);
        setListenersButtons();
        setRequestsDataAdapter();
        ((CompanyAdminHomePageActivity)requireActivity()).setupRecyclerView();
    }

    private void setListenersButtons() {

    }

    private void setRequestsDataAdapter() {
        List<Request> requests = new ArrayList<>();
        Bundle bundle = this.getArguments();
        companyName = bundle.getString("key-company");
        username = ((CompanyAdminHomePageActivity) requireActivity()).returnUsername();

        /*FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies")
                .child(companyName).child("locationList").child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("requests").exists()) {
                    for (DataSnapshot request : snapshot.child("requests").getChildren()) {
                        requests.add(request.getValue(Request.class));
                    }

                    ((CompanyAdminHomePageActivity) requireActivity()).setRequestsAdapter(requests);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        // mAdapter = new PlayersDataAdapter(players);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
