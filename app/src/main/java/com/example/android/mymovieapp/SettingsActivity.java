package com.example.android.mymovieapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

/**
 * Created by Noopur on 8/24/2016.
 */
public class SettingsActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        Button button =(Button) findViewById(R.id.s_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectColor();
            }
        });

    }

    public void selectColor()
    {
        View menuItemView = findViewById(R.id.s_button);
        PopupMenu popup = new PopupMenu(SettingsActivity.this, menuItemView);
        MenuInflater inflate = popup.getMenuInflater();
        inflate.inflate(R.menu.choose_color, popup.getMenu());
        popup.show();
    }
}


