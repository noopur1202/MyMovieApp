package com.example.android.mymovieapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Noopur on 8/25/2016.
 */
public class DetailActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        ColorDrawable colorDrawable=new ColorDrawable(Color.parseColor("#000000"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
    }
}
