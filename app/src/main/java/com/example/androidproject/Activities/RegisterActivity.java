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

public class RegisterActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText e1, e2, e3;
    Button btnRegister, btnBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);

        e1=(EditText)findViewById(R.id.etEmailreg);
        e2=(EditText)findViewById(R.id.etPasswordreg);
        e3=(EditText)findViewById(R.id.etConfirmPassword);

        btnRegister = findViewById(R.id.btnRegisterreg);
        btnBack = findViewById(R.id.btnBackToLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = e1.getText().toString();
                String s2 = e2.getText().toString();
                String s3 = e3.getText().toString();
                if(s1.equals("") || s2.equals("") || s3.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                } else {
                    if(s2.equals(s3)) {
                        Boolean checkEmail = db.checkEmail(s1);
                        if(checkEmail) {
                            Boolean insert = db.insert(s1, s2);
                            if(insert){
                                Toast.makeText(getApplicationContext(),"Successfuly Registered", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
