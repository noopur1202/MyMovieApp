package com.example.android.mymovieapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Created by Noopur on 10/18/2016.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview=inflater.inflate(R.layout.details_fragment,container,false);

        TextView title_textView=(TextView)rootview.findViewById(R.id.details_title);
        title_textView.setText("MovieTitle");
        TextView year_textView=(TextView)rootview.findViewById(R.id.details_year);
        year_textView.setText("Year");
        TextView duration_textView=(TextView)rootview.findViewById(R.id.details_duration);
        duration_textView.setText("Duration");
        TextView rating_textView=(TextView)rootview.findViewById(R.id.details_rating);
        rating_textView.setText("Rating");
        TextView trailer_heading_textView=(TextView)rootview.findViewById(R.id.trailer_heading);
        trailer_heading_textView.setText("Trailers:");
        TextView movieSummary_textview=(TextView)rootview.findViewById(R.id.details_movieSummary);
        movieSummary_textview.setText("Every child comes into the world full of promise, and none more so than chappie: he is gifted, special, a prodigy.Chappie, he is a robot");

        ToggleButton fav_button=(ToggleButton)rootview.findViewById(R.id.details_favButton);

        ImageView poster_imageview=(ImageView)rootview.findViewById(R.id.details_poster);
        poster_imageview.setImageResource(R.drawable.d);

        return rootview;
    }
}
