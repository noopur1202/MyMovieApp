package com.example.android.mymovieapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Noopur on 8/24/2016.
 */
public class SettingsActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        TextView sTextview=(TextView)findViewById(R.id.sTextview);
        sTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectColor();
            }
        });

    }

    public void selectColor()
    {
        View menuItemView = findViewById(R.id.sTextview);
        PopupMenu popup = new PopupMenu(SettingsActivity.this, menuItemView);
        MenuInflater inflate = popup.getMenuInflater();
        inflate.inflate(R.menu.choose_color, popup.getMenu());
        popup.show();
    }
}


