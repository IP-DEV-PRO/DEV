package com.devpro.activities;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devpro.R;
import com.devpro.models.Request;
import com.devpro.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

class RequestsDataAdapter extends RecyclerView.Adapter<RequestsDataAdapter.RequestsViewHolder> {
    private List<Request> requests;
    private DatabaseReference mDatabase;

    @NonNull
    @Override
    public RequestsDataAdapter.RequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.requests_list_owner_location, parent, false);
        return new RequestsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestsDataAdapter.RequestsViewHolder holder, int position) {

        Request request = requests.get(position);

        holder.username.setText("Username: " + request.getUsername());
        holder.name.setText(request.getFirstName() + " " + request.getLastName());
        holder.date.setText("Date: " + request.getDate());
        holder.service.setText(request.getService());
        holder.time_slot.setText("Time slot: " + request.getStartTime()+"-"+request.getEndTime());
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public class RequestsViewHolder extends RecyclerView.ViewHolder {
        TextView date, name, username, service, time_slot;

        public RequestsViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date_cardview);
            name = itemView.findViewById(R.id.name_cardview);
            username = itemView.findViewById(R.id.username_cardview);
            service = itemView.findViewById(R.id.service_cardview);
            time_slot = itemView.findViewById(R.id.slot_cardview);
        }
    }

    public RequestsDataAdapter(List<Request> requests) {
        this.requests = requests;
    }

}
