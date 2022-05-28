package com.devpro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devpro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AddCompanyAdminActivity extends AppCompatActivity {


    String userId, companyName;
    EditText admin_username_text, pass_text, email_text, first_text, last_text, phone_text;
    Button addadmin_button;
    private DatabaseReference mDatabase;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company_admin);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        userId = getIntent().getStringExtra("key-user");

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        date = dateFormat.format(calendar.getTime());

        admin_username_text = findViewById(R.id.add_comp_adm_username);
        pass_text = findViewById(R.id.add_comp_adm_pwd);
        email_text = findViewById(R.id.add_comp_adm_email);
        first_text = findViewById(R.id.add_comp_adm_first);
        last_text = findViewById(R.id.add_comp_adm_last);
        phone_text = findViewById(R.id.add_comp_adm_phone);
        addadmin_button = findViewById(R.id.add_comp_adm_add_c);


        setListenersButtons();
    }

    void setListenersButtons() {
        addadmin_button.setOnClickListener(view -> addAdmin(admin_username_text.getText().toString(),
                pass_text.getText().toString(),
                email_text.getText().toString(),
                first_text.getText().toString(),
                last_text.getText().toString(),
                phone_text.getText().toString()));
    }

    private void addAdmin(String username, String password, String email, String first, String last, String phone) {

        mDatabase = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");
        mDatabase.child(userId).child("companyName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    companyName = task.getResult().getValue(String.class);
                    System.out.println(companyName);
                }
            }
        });
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
                        Toast.makeText(getApplicationContext(),"Password must be at least 6 characters long",Toast.LENGTH_LONG).show();
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
                            mDatabase.child(username).child("username").setValue(username);
                            mDatabase.child(username).child("password").setValue(encrypted);
                            mDatabase.child(username).child("firstName").setValue(first);
                            mDatabase.child(username).child("lastName").setValue(last);
                            mDatabase.child(username).child("phone").setValue(phone);
                            mDatabase.child(username).child("e_mail").setValue(email);
                            mDatabase.child(username).child("companyName").setValue(companyName);
                            mDatabase.child(username).child("role").setValue(1);
                            mDatabase.child(username).child("reg_date").setValue(date);
                            mDatabase.child(username).child("blocked").setValue(false);
                            Toast.makeText(getApplicationContext(),
                                    "Admin added!!",
                                    Toast.LENGTH_LONG)
                                    .show();
                            changeActiviy(CompanyOwnerHomePage.class, userId);
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
        myIntent.putExtra("key-user", userId);
        startActivity(myIntent);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                changeActiviy(CompanyOwnerHomePage.class, userId);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String encrypt(String password) throws Exception{
        Key key = new SecretKeySpec("1Hbfh667adfDEJ78".getBytes(),"AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte [] encryptedByteValue = cipher.doFinal(password.getBytes("utf-8"));
        String encryptedValue64 = Base64.encodeToString(encryptedByteValue, Base64.DEFAULT);
        return encryptedValue64;
    }

}