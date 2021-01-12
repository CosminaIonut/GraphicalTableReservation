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

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    ImageButton go;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView textView = findViewById(R.id.signUpButtonLoginPage);
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mapActivity = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(mapActivity);
            }
        });

        email = (EditText) findViewById(R.id.emailTextLoginPage);
        password = (EditText) findViewById(R.id.passwordTextLoginPage);
        go = (ImageButton) findViewById(R.id.goButtonLoginPage);
        db = new DatabaseHelper(this);
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_ = email.getText().toString();
                String pass = password.getText().toString();
                if (email_.equals("") || pass.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkCredentials = db.checkCredentials(email_, pass);
                    if (checkCredentials) {
                        Toast.makeText(LoginActivity.this, "Sign in succesfully!", Toast.LENGTH_SHORT).show();
                        String name= db.getName(email_);
                        SharedPreferences.Editor edit= preferences.edit();
                        edit.putString("pref_USERNAME",email_);
                        edit.putString("pref_NAME",name);
                        edit.commit();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Wrong credentials!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}