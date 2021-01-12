package com.example.restaurantreservation.Authentification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantreservation.DatabaseHelper;
import com.example.restaurantreservation.HomePage.MainActivity;
import com.example.restaurantreservation.R;

public class SignUpActivity extends AppCompatActivity {

    EditText name, email, password;
    ImageButton go;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        TextView textView = findViewById(R.id.signInTextSignUpPage);
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginActivity);
            }
        });

        name = (EditText) findViewById(R.id.NameTextSignUpPage);
        email = (EditText) findViewById(R.id.emailTextSignUpPage);
        password = (EditText) findViewById(R.id.passwordTextSignUpPage);
        go = (ImageButton) findViewById(R.id.goButtonSignUpPage);
        db = new DatabaseHelper(this);

        // create Prefrence to store the email and user of the logged user
        // check if credentails are valid and the user does not already exist.
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_ = name.getText().toString();
                String email_ = email.getText().toString();
                String pass = password.getText().toString();

                if (email_.equals("") || pass.equals("") || name_.equals("")) {
                    Toast.makeText(SignUpActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkUser = db.checkEmail(email_);
                    if (!checkUser) {
                        Boolean insert = db.insertUser(name_, email_, pass);
                        if (insert) {
                            Toast.makeText(SignUpActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor edit= preferences.edit();
                            edit.putString("pref_USERNAME",email_);
                            edit.putString("pref_NAME", name_);
                            edit.commit();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUpActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, "User already exists! Sign in!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}