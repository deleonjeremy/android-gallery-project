package com.example.androidproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidproject.R;
import com.example.androidproject.Utils.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    EditText e1, e2;
    Button btnToRegister, btnLogin;
    DatabaseHelper db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DatabaseHelper(this);
        btnToRegister = findViewById(R.id.btnToRegister);
        btnLogin = findViewById(R.id.btnLogin);
        e1 = findViewById(R.id.etEmail);
        e2 = findViewById(R.id.etPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = e1.getText().toString();
                String password = e2.getText().toString();
                Boolean loginValid = db.checkLogin(email, password);

                if(loginValid){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("userEmail",email);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Email/Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}