package com.example.examtestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MovieOverview extends AppCompatActivity {

    EditText newComment;
    TextView movieName, currentRating;
    SeekBar ratingBar;
    Button submitButton;
    ListView commentsList;
    ArrayList ratings;
    DBHandler db;
    ArrayList comments;
    ArrayAdapter adapter;
    int myRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_overview);

        movieName = findViewById(R.id.tvMovieNameMO);
        currentRating = findViewById(R.id.tvCurrentRatingMO);
        newComment = findViewById(R.id.etCommentMO);
        ratingBar = findViewById(R.id.skbRatingMO);
        submitButton = findViewById(R.id.btnSubmitMO);
        commentsList = findViewById(R.id.lvCommentsMO);

        db = new DBHandler(getApplicationContext());
        Intent intent = getIntent();
        String name = intent.getStringExtra(MovieList.EXTRA_MESSAGE);
        movieName.setText(name);

        comments = (ArrayList) db.viewComments(name);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,comments);
        commentsList.setAdapter(adapter);

        ratings = (ArrayList) db.viewRatings(name);
        int average = calculateAverage(ratings);


        //seek bar
        ratingBar.setProgress(average);

        ratingBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                myRating = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        currentRating.setText(Integer.toString(average));

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long insert = -99;
                insert = db.insertComments(movieName.getText().toString(), myRating, newComment.getText().toString());
                if(insert>0)
                    Toast.makeText(MovieOverview.this, "Comment Added", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MovieOverview.this, "Failed to add Comment!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ratingBar.setProgress(0);
    }

    private int calculateAverage(ArrayList <Integer> values) {
        Integer sum = 0;
        if(!values.isEmpty()) {
            for (Integer value : values) {
                sum += value;
            }
            return sum / values.size();
        }
        return sum;
    }

}