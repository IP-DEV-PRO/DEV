package com.devpro.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.devpro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button loginButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    void setListenersButtons() {
        loginButton.setOnClickListener(view -> login(username.getText().toString(),password.getText().toString()));
    }

    private void changeActiviy(Class activityClass, String userId) {
        Intent myIntent = new Intent(this, activityClass);
        myIntent.putExtra("key-user", userId);
        startActivity(myIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        actionBar.setBackgroundDrawable(new ColorDrawable(0xff2190F3));

        mAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginPage_loginButton);
        setListenersButtons();

        String encrypted="";
            try {
                encrypted = encrypt("devadmin");
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(encrypted);

    }

    private void login(String username, String password) {

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(),
//                                    "Login successful!!",
//                                    Toast.LENGTH_LONG)
//                                    .show();
//                            // if sign-in is successful
//                            // intent to home activity
//                            mDatabase = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");
//                            String userID = mDatabase.push().getKey();
//                            System.out.println(userID);
//                            changeActiviy(UserHomePage.class, userID);
//                        } else {
//                            Toast.makeText(getApplicationContext(),
//                                    "Login failed!!",
//                                    Toast.LENGTH_LONG)
//                                    .show();
//                            finish();
//                        }
//                    }
//                });
        mDatabase = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");
        mDatabase.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                boolean blocked = false;
                if(!username.equals("admin"))
                    blocked = Boolean.parseBoolean(Objects.requireNonNull(snapshot.child("blocked").getValue()).toString());
                if(blocked)
                {
                    Toast.makeText(getApplicationContext(),
                            "Account banned. FUCK YOUl!!",
                            Toast.LENGTH_LONG)
                            .show();
                    changeActiviy(MainActivity.class, username);
                }
                else {
                    String encrypted = "";
                    if (snapshot.exists()) {
                        try {
                            encrypted = encrypt(password);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String user_paswsord = snapshot.child("password").getValue().toString();
                        if (encrypted.equals(user_paswsord)) {
                            Toast.makeText(getApplicationContext(),
                                    "Login successful!!",
                                    Toast.LENGTH_LONG)
                                    .show();
                            if (username.equals("admin"))
                                changeActiviy(AdminPageActivity.class, username);
                            else
                                changeActiviy(UserHomePage.class, username);
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Incorrect username/password!!",
                                    Toast.LENGTH_LONG)
                                    .show();
                            changeActiviy(MainActivity.class, username);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No account with that username", Toast.LENGTH_LONG).show();
                        changeActiviy(MainActivity.class, username);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent myIntent = new Intent(this, MainActivity.class);
                startActivity(myIntent);
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
