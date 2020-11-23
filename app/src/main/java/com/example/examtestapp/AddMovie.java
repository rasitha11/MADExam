package com.example.examtestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddMovie extends AppCompatActivity {

    EditText movieName, movieYear;
    Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        movieName = findViewById(R.id.etMovieNameAP);
        movieYear = findViewById(R.id.etMovieYearAP);
        btnAdd = findViewById(R.id.btnAddAp);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler dbHandler = new DBHandler(getApplicationContext());
                try {
                    if (dbHandler.addMovie(movieName.getText().toString(), Integer.parseInt(movieYear.getText().toString())))
                        Toast.makeText(AddMovie.this, "Added Movie: " + movieName.getText().toString(), Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(AddMovie.this, "Failed to add Movie!", Toast.LENGTH_SHORT).show();
                }catch(NumberFormatException e){
                    Toast.makeText(AddMovie.this, "Movie year must be an integer!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}