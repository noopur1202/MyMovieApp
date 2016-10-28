package com.example.android.mymovieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

/**
 * Created by Noopur on 10/18/2016.
 */
public class DetailActivityFragment extends Fragment {

    String poster_detail,title_detail,rating_detail,summary_detail;
    String year_detail,duration_detail;


    public DetailActivityFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootview=inflater.inflate(R.layout.details_fragment,container,false);
        Intent intent=getActivity().getIntent();
        poster_detail = intent.getStringExtra("POSTER");
        title_detail=intent.getStringExtra("TITLE");
        year_detail=intent.getStringExtra("YEAR");
        rating_detail=intent.getStringExtra("RATING");
        summary_detail=intent.getStringExtra("SUMMARY");

        ImageView poster_imageview=(ImageView)rootview.findViewById(R.id.details_poster);

        Picasso.with(getContext())
                    .load(poster_detail)
                    .into(poster_imageview);


        TextView title_textView=(TextView)rootview.findViewById(R.id.details_title);
        title_textView.setText(title_detail);

        TextView year_textView=(TextView)rootview.findViewById(R.id.details_year);
        year_textView.setText(year_detail);

        TextView duration_textView=(TextView)rootview.findViewById(R.id.details_duration);
        duration_textView.setText("Duration");

        TextView rating_textView=(TextView)rootview.findViewById(R.id.details_rating);
        rating_textView.setText(rating_detail);

        TextView trailer_heading_textView=(TextView)rootview.findViewById(R.id.trailer_heading);
        trailer_heading_textView.setText("Trailers:");

        TextView movieSummary_textview=(TextView)rootview.findViewById(R.id.details_movieSummary);
        movieSummary_textview.setText(summary_detail);

        ToggleButton fav_button=(ToggleButton)rootview.findViewById(R.id.details_favButton);

        return rootview;
    }
}