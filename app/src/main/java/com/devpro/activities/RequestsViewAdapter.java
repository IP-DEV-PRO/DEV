package com.devpro.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devpro.R;
import com.devpro.models.Request;

import java.util.List;

class RequestsDataAdapter extends RecyclerView.Adapter<RequestsDataAdapter.RequestsViewHolder> {
    private List<Request> requests;

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
        holder.date.setText(request.getDate());
        holder.phone_email.setText(request.getDate());
        holder.username.setText(request.getUsername());
        holder.service.setText(request.getService());
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public class RequestsViewHolder extends RecyclerView.ViewHolder {
        TextView date, phone_email, username, service;

        public RequestsViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date_cardview);
            phone_email = itemView.findViewById(R.id.phone_email_cardview);
            username = itemView.findViewById(R.id.username_cardview);
            service = itemView.findViewById(R.id.service_cardview);
        }
    }

    public RequestsDataAdapter(List<Request> requests) {
        this.requests = requests;
    }

}
