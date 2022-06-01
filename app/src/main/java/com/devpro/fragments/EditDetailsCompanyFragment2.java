package com.devpro.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.devpro.R;
import com.devpro.activities.CompanyAdminHomePageActivity;
import com.devpro.activities.RegisterCompanyActivityWithMap;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Key;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EditDetailsCompanyFragment2 extends Fragment {
    TextInputEditText add_phone_textInput2, add_email_Input2, add_description_text2, add_password_input2;
    Button saveDetails;
    String companyName, username;

    public EditDetailsCompanyFragment2() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.company_homepage_editdetails_2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        add_phone_textInput2 = requireActivity().findViewById(R.id.add_phone_textInput2);
        add_email_Input2 = requireActivity().findViewById(R.id.add_email_Input2);
        add_description_text2 = requireActivity().findViewById(R.id.add_description_text2);
        add_password_input2 = requireActivity().findViewById(R.id.add_password_input2);
        saveDetails = requireActivity().findViewById(R.id.saveDetails);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            companyName = bundle.getString("key-company");
            System.out.println(companyName + " dasddadsadasdadsa4444444");
            username = ((CompanyAdminHomePageActivity) requireActivity()).returnUsername();
        }

        setListenersButtons();
    }


    private String encrypt(String password) throws Exception {
        Key key = new SecretKeySpec("1Hbfh667adfDEJ78".getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedByteValue = cipher.doFinal(password.getBytes("utf-8"));
        return Base64.encodeToString(encryptedByteValue, Base64.DEFAULT);
    }

    private void setListenersButtons() {
        saveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Objects.requireNonNull(add_description_text2.getText()).toString().compareTo("") != 0) {
                    if (add_description_text2.getText().toString().length() > 160) {
                        Toast.makeText(getActivity(), "Description is too long", Toast.LENGTH_LONG).show();
                    } else {
                        FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies")
                                .child(companyName).child("locationList").child(username).child("description").setValue(add_description_text2.getText().toString());
                        add_description_text2.setText("");
                    }
                }
                //System.out.println("DESCRIPTION: " + add_description_text2.getText());
                if (Objects.requireNonNull(add_phone_textInput2.getText()).toString().compareTo("") != 0) {
                    FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies")
                            .child(companyName).child("locationList").child(username).child("phone").setValue(add_phone_textInput2.getText().toString());
                    add_phone_textInput2.setText("");
                }
                if (Objects.requireNonNull(add_email_Input2.getText()).toString().compareTo("") != 0) {
                    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
                    Pattern pat = Pattern.compile(emailRegex);
                    if (!pat.matcher(add_email_Input2.getText().toString()).matches()) {
                        Toast.makeText(getActivity(), "Invalid e-mail address", Toast.LENGTH_LONG).show();
                    } else {
                        FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies")
                                .child(companyName).child("locationList").child(username).child("email").setValue(add_email_Input2.getText().toString());
                        add_email_Input2.setText("");
                    }
                }
                if (Objects.requireNonNull(add_password_input2.getText()).toString().compareTo("") != 0) {
                    String encrypted = "";
                    try {
                        encrypted = encrypt(add_password_input2.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users")
                            .child(username).child("password").setValue(encrypted);
                    add_password_input2.setText("");
                }
            }
        });
    }

}
