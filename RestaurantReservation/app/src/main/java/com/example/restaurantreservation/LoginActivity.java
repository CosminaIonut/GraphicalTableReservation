package com.example.restaurantreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
                System.out.println("Button Clicked");
                Intent mapActivity = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(mapActivity);
            }
        });

        email = (EditText) findViewById(R.id.emailTextLoginPage);
        password = (EditText) findViewById(R.id.passwordTextLoginPage);
        go = (ImageButton) findViewById(R.id.goButtonLoginPage);
        db = new DatabaseHelper(this);

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