package com.example.examtestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MovieList extends AppCompatActivity {

    ListView movieList;
    ArrayList dataList;
    ArrayAdapter adapter;
    DBHandler db;
    public static final String EXTRA_MESSAGE = "com.example.examtestapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        movieList = findViewById(R.id.lvMoviesM);

        db = new DBHandler(getApplicationContext());
        dataList = (ArrayList) db.viewMovies();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,dataList);
        movieList.setAdapter(adapter);

        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MovieList.this, MovieOverview.class);
                intent.putExtra(EXTRA_MESSAGE, movieList.getItemAtPosition(i).toString());
                startActivity(intent);
            }
        });
    }
}