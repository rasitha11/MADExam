package com.example.examtestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity {

    EditText userName, password;
    Button login, register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        login = findViewById(R.id.btnLoginMn);
        register = findViewById(R.id.btnRegisterMn);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler dbHandler = new DBHandler(getApplicationContext());
                long newID = dbHandler.registerUser(userName.getText().toString(), password.getText().toString());
                if(newID>0) {
                    Toast.makeText(Main.this, "New User Added: " + newID, Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(Main.this, "Failed To Add User!", Toast.LENGTH_SHORT).show();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler dbHandler = new DBHandler(getApplicationContext());
                ArrayList userType = (ArrayList) dbHandler.loginUser(userName.getText().toString(), password.getText().toString());
                    if (userType.contains("admin")){
                        Intent intent = new Intent(Main.this, AddMovie.class);
                        startActivity(intent);
                    }
                    else if (userType.contains("user")){
                        Intent intent = new Intent(Main.this, MovieList.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(Main.this, "Invalid User Info!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}