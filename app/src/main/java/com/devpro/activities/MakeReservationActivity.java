package com.devpro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.devpro.R;
import com.devpro.models.Request;
import com.devpro.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MakeReservationActivity extends AppCompatActivity {

    String userId, ownerKey, companyName, userPhone;
    private DatabaseReference mDatabase;
    CalendarView calendar;
    NumberPicker picker_start, picker_end;
    Button send_res_button;
    long currentTime;
    private String[] timeSlotsStart;
    private String[] timeSlotsEnd;
    private ArrayList<String> endDynamic;
    int start_time, end_time;
    String date;
    Calendar calendar_date;
    SimpleDateFormat dateFormat;

    void setListenersButtons() {
        send_res_button.setOnClickListener(view -> sendRequest());

    }

    private void sendRequest() {

        if(end_time<=start_time)
        {
            Toast.makeText(getApplicationContext(), "End time is before start time", Toast.LENGTH_SHORT).show();
        }
        else
        {
            mDatabase = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");
            mDatabase.child(ownerKey).child("companyName").get().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    companyName = task.getResult().getValue(String.class);
                    mDatabase = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").
                            getReference("companies").child(companyName).child("locationList").child(ownerKey);

                    mDatabase.child("requests").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                //
                            } else {
                                ArrayList<Request> a = new ArrayList<>();
                                for (DataSnapshot ds : task.getResult().getChildren()) {
                                    a.add(ds.getValue(Request.class));
                                }
                                a.add(new Request(userId, userPhone, date, timeSlotsStart[start_time], timeSlotsEnd[end_time], "no-service"));
                                FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").
                                        getReference("companies").child(companyName).child("locationList").child(ownerKey).child("requests").setValue(a);
                            }
                        }
                    });
                    mDatabase = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").
                            getReference("companies").child(companyName).child("locationList").child(ownerKey);

                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reservation);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        mDatabase = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");

        userId = getIntent().getStringExtra("key-user");
        ownerKey = getIntent().getStringExtra("key-owner");
        System.out.println(userId + " " + ownerKey);

        mDatabase.child(userId).child("phone").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                userPhone = task.getResult().getValue(String.class);
            }
        });

        calendar = (CalendarView) findViewById(R.id.make_res_calendar);
        picker_start = findViewById(R.id.make_res_start_pick);
        picker_end = findViewById(R.id.make_res_end_pick);
        send_res_button = findViewById(R.id.make_res_request);
        endDynamic = new ArrayList<>();

        date = "";
        calendar_date = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        date = dateFormat.format(calendar_date.getTime());

        calendar.setFirstDayOfWeek(2);
        currentTime = System.currentTimeMillis();
        calendar.setMinDate(currentTime);
        calendar.setBackgroundColor(0xFFF4F0AB);

        timeSlotsStart = new String[] {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00",
                "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00"};
        timeSlotsEnd = new String[] {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00",
                "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00"};
        picker_start.setMinValue(0);
        picker_start.setMaxValue(21);
        picker_start.setEnabled(true);
        picker_end.setMinValue(0);
        picker_end.setMaxValue(22);
        picker_end.setEnabled(true);

        picker_start.setDisplayedValues(timeSlotsStart);
        picker_end.setDisplayedValues(timeSlotsEnd);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String msg = "Selected date Day: " + i2 + " Month : " + (i1 + 1) + " Year " + i;
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                if(i1 < 10)
                    date = +i2 +"-0"+(i1 + 1) + "-" + i;
                else
                    date = +i2 +"-"+(i1 + 1) + "-" + i;
            }
        });

        picker_start.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                start_time = picker_start.getValue();
            }
        });

        picker_end.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                end_time = picker_end.getValue();
            }
        });

        setListenersButtons();
    }

    private void changeActiviy(Class activityClass, String usernameKey) {
        Intent myIntent = new Intent(this, activityClass);
        myIntent.putExtra("key-user", usernameKey);
        startActivity(myIntent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                changeActiviy(UserHomePage.class, userId);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}