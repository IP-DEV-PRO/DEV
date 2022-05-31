package com.devpro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.devpro.R;
import com.google.firebase.database.DatabaseReference;

public class MakeReservationActivity extends AppCompatActivity {

    String userId, ownerKey;
    private DatabaseReference mDatabase;
    CalendarView calendar;
    NumberPicker picker;
    long currentTime;
    private String[] timeSlots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reservation);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        userId = getIntent().getStringExtra("key-user");
        ownerKey = getIntent().getStringExtra("key-owner");
        System.out.println(userId + " " + ownerKey);
        calendar = (CalendarView) findViewById(R.id.make_res_calendar);
        picker = findViewById(R.id.make_res_hour);

        calendar.setFirstDayOfWeek(2);
        currentTime = System.currentTimeMillis();
        calendar.setMinDate(currentTime);
        calendar.setBackgroundColor(0xFFF4F0AB);
        timeSlots = new String[] {"08 - 10", "10 - 12", "12 - 14", "14 - 16","16 - 18", "18 - 20","20 - 22"};
        picker.setDisplayedValues(timeSlots);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String msg = "Selected date Day: " + i2 + " Month : " + (i1 + 1) + " Year " + i;
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });

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