package com.devpro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devpro.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class PaymentActivity extends AppCompatActivity {

    String userId;
    EditText number_text, name_text, exp_text, cvv_text;
    Button pay_button;
    private DatabaseReference mDatabase;

    void setListenersButtons() {
        pay_button.setOnClickListener(view -> pay(number_text.getText().toString(),
                name_text.getText().toString(),exp_text.getText().toString(),cvv_text.getText().toString()));
    }

    private void pay(String number, String name, String exp, String cvv) {

        if(number.isEmpty() || name.isEmpty() || exp.isEmpty() || cvv.isEmpty())
            Toast.makeText(getApplicationContext(),"Please complete all fields",Toast.LENGTH_LONG).show();
        else if(number.length() != 16 || !number.matches("[0-9]+"))
            Toast.makeText(getApplicationContext(),"Invalid card number",Toast.LENGTH_LONG).show();
        else if(cvv.length()!=3 || !cvv.matches("[0-9]+"))
            Toast.makeText(getApplicationContext(),"Invalid CVV",Toast.LENGTH_LONG).show();
        else if(exp.charAt(2)!='/')
            Toast.makeText(getApplicationContext(),"Date format must be mm/yy",Toast.LENGTH_LONG).show();
        else {
            String[] words = exp.split("/");
            if(words[0].length() != 2 || words[1].length() != 2)
                Toast.makeText(getApplicationContext(),"Format must be mm/yy",Toast.LENGTH_LONG).show();
            else if (!words[0].matches("[0-9]+") || !words[1].matches("[0-9]+"))
                Toast.makeText(getApplicationContext(),"Invalid expiration date",Toast.LENGTH_LONG).show();
            else
            {
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR) - 2000;
                int month_input = Integer.parseInt(words[0]);
                int year_input = Integer.parseInt(words[1]);
                System.out.println(year + " " + year_input);
                System.out.println(month + " " + month_input);
                if(year_input < year)
                    Toast.makeText(getApplicationContext(),"Expired card",Toast.LENGTH_LONG).show();
                else if (year_input == year)
                {
                    if(month_input < month)
                    {
                        Toast.makeText(getApplicationContext(),"Expired card",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Your payment was successful!",Toast.LENGTH_LONG).show();
                        mDatabase.child(userId).child("activesub").setValue(true);
                        year += 2000;
                        mDatabase.child(userId).child("start").setValue(day+"-"+month+"-"+year);
                        month++;
                        mDatabase.child(userId).child("expiration").setValue(day+"-"+month+"-"+year);
                        changeActiviy(SubscriptionActivity.class,userId);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Your payment was successful!",Toast.LENGTH_LONG).show();
                    mDatabase.child(userId).child("activesub").setValue(true);
                    year += 2000;
                    mDatabase.child(userId).child("start").setValue(day+"-"+month+"-"+year);
                    month++;
                    mDatabase.child(userId).child("expiration").setValue(day+"-"+month+"-"+year);
                    changeActiviy(SubscriptionActivity.class,userId);
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        actionBar.setBackgroundDrawable(new ColorDrawable(0xbe4d25));

        mDatabase = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");
        userId = getIntent().getStringExtra("key-user");

        number_text = findViewById(R.id.payment_number);
        name_text = findViewById(R.id.payment_holder);
        exp_text = findViewById(R.id.payment_exp);
        cvv_text = findViewById(R.id.payment_cvv);
        pay_button = findViewById(R.id.payment_pay);


        setListenersButtons();
    }


    private void changeActiviy(Class activityClass, String userId) {
        Intent myIntent = new Intent(this, activityClass);
        myIntent.putExtra("key-user",userId);
        startActivity(myIntent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                //changeActiviy(SubscriptionActivity.class, userId);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}