package com.devpro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.devpro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EditAccountActivity extends AppCompatActivity {

    EditText email_edit, telephone_edit, password_edit;
    Button saveChanges;
    String userId;
    RadioGroup radioGroup;
    private DatabaseReference mDatabase;


    void setListenersButtons() {
        saveChanges.setOnClickListener(view -> {
            try {
                saveChanges(email_edit.getText().toString(), telephone_edit.getText().toString(),
                        password_edit.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.edit_account_yes:
                        password_edit.setVisibility(View.VISIBLE);
                        break;
                    case R.id.edit_account_no:
                        password_edit.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");


        mDatabase = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");
        email_edit = findViewById(R.id.edit_account_email);
        telephone_edit = findViewById(R.id.edit_account_telephone);
        password_edit = findViewById(R.id.edit_account_password);
        saveChanges = findViewById(R.id.edit_account_save);
        radioGroup = findViewById(R.id.edit_account_radioGroup);
        userId = getIntent().getStringExtra("key-user");
        password_edit.setVisibility(View.INVISIBLE);

        mDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    email_edit.setText(snapshot.child("e_mail").getValue().toString());
                    telephone_edit.setText(snapshot.child("phone").getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        setListenersButtons();
    }

    void saveChanges(String email, String phone, String password) throws Exception {
        mDatabase.child(userId).child("e_mail").setValue(email);
        mDatabase.child(userId).child("phone").setValue(phone);
        if(!password.equals("")) {
            String encrypted = "";
            encrypted = encrypt(password);
            mDatabase.child(userId).child("password").setValue(encrypted);
        }
        changeActiviy(UserHomePage.class, userId);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                changeActiviy(UserHomePage.class, userId);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}