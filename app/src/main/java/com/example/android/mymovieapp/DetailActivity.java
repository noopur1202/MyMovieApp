package com.example.android.mymovieapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Noopur on 8/25/2016.
 */
public class DetailActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.details);

        TextView textView=(TextView)findViewById(R.id.details_textview);
        textView.setText("details");
    }
}
