package com.devpro.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.devpro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.devpro.models.User;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "CustomAuthActivity";
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    EditText username, password, email;
    Button registerButton, registerCompanyButton;
    private DatabaseReference mDatabase;

    void setListenersButtons() {
        //registerButton.setOnClickListener(view -> register(username.getText().toString(),password.getText().toString()));
        registerButton.setOnClickListener(view -> registerUser(username.getText().toString(),password.getText().toString(),
                email.getText().toString(),false));
        registerCompanyButton.setOnClickListener(view -> registerUser(username.getText().toString(),password.getText().toString(),
                email.getText().toString(),true));
        //registerCompanyButton.setOnClickListener(view -> changeActiviy(RegisterCompanyActivityWithMap.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        actionBar.setBackgroundDrawable(new ColorDrawable(0xffBC3672));

        mAuth = FirebaseAuth.getInstance();
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        date = dateFormat.format(calendar.getTime());
        username = findViewById(R.id.regsiterPage_username);
        password = findViewById(R.id.registerPage_password);
        email = findViewById(R.id.registerPage_email);
        registerCompanyButton = findViewById(R.id.registerPage_registerCompanyButton);
        registerButton = findViewById(R.id.registerPage_registerButton);

        setListenersButtons();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void registerUser(String username, String password, String email, boolean iscompany) {
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, task -> {
//                    if (task.isSuccessful()) {
//                        // Sign in success, update UI with the signed-in user's information
//                        FirebaseUser user = mAuth.getCurrentUser();
//                        mDatabase = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");
//
//                        String userId = mDatabase.push().getKey();
//                        assert userId != null;
//
//                        User registeredUser = new User(username, password, "no-last-name", "no-first-name", "no-telephone", email);
//                        mDatabase.child(username).setValue(registeredUser);
//
//                        Log.d(TAG, "createUserWithEmail:success");
//                        //System.out.println(userId);
//                        updateUI(username);
//                        Toast.makeText(getApplicationContext(),
//                                "Register successful!!",
//                                Toast.LENGTH_LONG)
//                                .show();
//                        // finish();
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
//                                Toast.LENGTH_SHORT).show();
//
//                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                        finish();
//                        //updateUI(null);
//                    }
//                });

        mDatabase = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");
        mDatabase.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(email.isEmpty() || password.isEmpty() || username.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Please complete all fields",Toast.LENGTH_LONG).show();
                }
                else {
                    if(snapshot.exists())
                        Toast.makeText(getApplicationContext(),"Username already taken",Toast.LENGTH_LONG).show();
                    else if(password.length() < 6)
                    {
                        Toast.makeText(getApplicationContext(),"Password must be at least 6 characters long",Toast.LENGTH_LONG).show();
                    }
                    else {
                        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                                "[a-zA-Z0-9_+&*-]+)*@" +
                                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                                "A-Z]{2,7}$";
                        Pattern pat = Pattern.compile(emailRegex);
                        if (!pat.matcher(email).matches()) {
                            Toast.makeText(getApplicationContext(),"Invalid e-mail address",Toast.LENGTH_LONG).show();
                        } else {
                            String encrypted = "";
                            try {
                                encrypted = encrypt(password);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            User registeredUser;

                            if(!iscompany)
                            {
                                registeredUser = new User(username, encrypted, "-", "-", "-",
                                        email, "-", "-", false, false);
                                mDatabase.child(username).setValue(registeredUser);
                                mDatabase.child(username).child("reg_date").setValue(date);
                                mDatabase.child(username).child("blocked").setValue(false);
                                Toast.makeText(getApplicationContext(),
                                        "Register successful!!",
                                        Toast.LENGTH_LONG)
                                        .show();
                                changeActiviy(RegisterUserTwo.class, username);
                            }
                            else{
                                registeredUser = new User(username, encrypted,"-","-","-", email,true);
                                mDatabase.child(username).setValue(registeredUser);
                                mDatabase.child(username).child("reg_date").setValue(date);
                                mDatabase.child(username).child("blocked").setValue(false);
                                changeActiviy(RegisterCompanyTwo.class, username);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void changeActiviy(Class activityClass, String userId) {
        Intent myIntent = new Intent(this, activityClass);
        myIntent.putExtra("key-user",userId);
        startActivity(myIntent);
    }

    private String encrypt(String password) throws Exception{
        Key key = new SecretKeySpec("1Hbfh667adfDEJ78".getBytes(),"AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte [] encryptedByteValue = cipher.doFinal(password.getBytes("utf-8"));
        String encryptedValue64 = Base64.encodeToString(encryptedByteValue, Base64.DEFAULT);
        return encryptedValue64;
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    private void updateUI(String key) {
        Intent myIntent = new Intent(this, RegisterUserTwo.class);
        myIntent.putExtra("key-user", key);
        startActivity(myIntent);
    }
}
