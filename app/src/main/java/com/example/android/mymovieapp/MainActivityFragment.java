package com.example.android.mymovieapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivityFragment extends Fragment {

    ImageAdapter movieAdapter;
    List<String> posters,summary,title,rating,year,duration;
    GridView gv;

    public MainActivityFragment() {setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_fragment,menu);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        posters = new ArrayList<String>();
        title=new ArrayList<String>();
        year=new ArrayList<String>();
        rating=new ArrayList<String>();
        summary=new ArrayList<String>();
        movieAdapter = new ImageAdapter(getActivity(),posters);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        gv = (GridView) rootView.findViewById(R.id.fragment);
        gv.setAdapter(movieAdapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                int poster_position = (int) movieAdapter.getItemId(position);
                String detail_poster= posters.get(poster_position);
                String detail_movieTitle=title.get(poster_position);
                String detail_releaseYear=year.get(poster_position);
                String  detail_duration;
                String detail_rating=rating.get(poster_position);
                String detail_summary=summary.get(poster_position);

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("POSTER",detail_poster);
                intent.putExtra("TITLE",detail_movieTitle);
                intent.putExtra("YEAR",detail_releaseYear);
                intent.putExtra("RATING",detail_rating);
                intent.putExtra("SUMMARY",detail_summary);
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_refresh)
        {
            UpdateScreen();
            Log.v("Log for refresh button","Refresh button pressed");
        }
        return super.onOptionsItemSelected(item);
    }

    public void UpdateScreen()
    {
        String sort;
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sort_order=pref.getString(getString(R.string.pref_sort_key),getString(R.string.pref_sort_default));
        if (getString(R.string.pref_sort_popular).equals(sort_order))
        {
                sort="popularity.desc";
        } else
        {
                sort="vote_average.desc";
        }
        movieAdapter.ClearArray();
        title.clear();
        summary.clear();
        rating.clear();
        year.clear();
        FetchDetails movieFetch=new FetchDetails();
        movieFetch.execute(sort);
        Log.v("LOG for preference","Preference is  "+sort);
    }

    @Override
    public void onStart() {
        super.onStart();
        UpdateScreen();
    }

    public class FetchDetails extends AsyncTask<String, Void, List<String>> {

        private final String LOG_TAG=FetchDetails.class.getSimpleName();

        private List<String> getdataFromJson(String json_string)throws JSONException
        {
            JSONObject jsonObject=new JSONObject(json_string);
            JSONArray jsonArray=jsonObject.getJSONArray("results");

            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject movie_list=jsonArray.getJSONObject(i);
                posters.add("http://image.tmdb.org/t/p/w185" + movie_list.getString("poster_path"));
                title.add(movie_list.getString("title"));
                year.add(movie_list.getString("release_date"));
                rating.add(movie_list.getString("vote_average"));
                summary.add(movie_list.getString("overview"));
            }
            return posters;
        }

        @Override
        protected List<String> doInBackground(String... params) {

            if (params.length==0)
            {
                return null;
            }
            HttpURLConnection urlConnection=null;
            BufferedReader reader=null;
            String json_string=null;
            String language="en-US";

            try
            {
                final String BASE_URL="https://api.themoviedb.org/3/discover/movie?";
                final String API_KEY="api_key";
                final String LANGUAGE="language";
                final String SORT_BY="sort_by";

                Uri buidUri=Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(API_KEY,BuildConfig.MOVIE_DB_API_KEY)
                        .appendQueryParameter(LANGUAGE,language)
                        .appendQueryParameter(SORT_BY,params[0]).build();

                URL url=new URL(buidUri.toString());
                Log.v(LOG_TAG,"build uri"+url);

                urlConnection=(HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream=urlConnection.getInputStream();
                StringBuffer buffer=new StringBuffer();

                if (inputStream==null)
                {
                    json_string= null;
                }

                reader=new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line=reader.readLine())!=null)
                {
                    buffer.append(line+"/n");
                }

                if (buffer.length()==0)
                {
                    json_string= null;
                }

                json_string=buffer.toString();
                Log.v(LOG_TAG,"json_string"+json_string);
            }
            catch (IOException e)
            {
                Log.e(LOG_TAG,"error",e);
                json_string= null;
            }
            finally
            {
                if(urlConnection!=null)
                {
                    urlConnection.disconnect();
                }
                if (reader!=null)
                {
                    try {
                        reader.close();
                    }catch (final IOException e)
                    {
                        Log.e(LOG_TAG,"error closing stream",e);
                    }
                }
            }
            try {
                return getdataFromJson(json_string);
            }catch (JSONException e){
                Log.e(LOG_TAG,e.getMessage(),e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<String> result)
        {
            super.onPostExecute(result);
            movieAdapter.clear();
            movieAdapter.notifyDataSetChanged();
            for (String r:result)
            {
                movieAdapter.add(r);
            }
        }
    }
}