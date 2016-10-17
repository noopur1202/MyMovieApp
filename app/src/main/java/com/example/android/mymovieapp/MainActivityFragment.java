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
    String[] moviePoster;
    List<String> posters;
    GridView gv;

    public MainActivityFragment() {
    }

/*    public static Fragment newInstance(Context context)
    {
      MainActivityFragment a = new MainActivityFragment();
      return a;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        posters = new ArrayList<String>();
        movieAdapter = new ImageAdapter(getActivity(),posters);
        gv = (GridView) rootView.findViewById(R.id.grid_fragment);
        gv.setAdapter(movieAdapter);
        movieAdapter.setNotifyOnChange(true);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.action_refresh:
                UpdateScreen();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void UpdateScreen(){
        FetchDetails movieFetch=new FetchDetails();
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sort_order=pref.getString("sort_order","Most_popular");
        movieFetch.execute(sort_order);
    }

    @Override
    public void onStart() {
        super.onStart();
        UpdateScreen();
    }

    public class FetchDetails extends AsyncTask<String, Void, List<String>> {

        private final String LOG_TAG=FetchDetails.class.getSimpleName();

        @Override
        protected List<String> doInBackground(String... params) {

            if (params.length==0)
            {
                return null;
            }
            HttpURLConnection urlConnection=null;
            BufferedReader reader=null;
            String json_string=null;

            String sort_order="popularity.desc";
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
                        .appendQueryParameter(SORT_BY,sort_order).build();

                URL url=new URL(buidUri.toString());
                Log.v(LOG_TAG,"build uri"+url);

                urlConnection=(HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream=urlConnection.getInputStream();
                StringBuffer buffer=new StringBuffer();

                if (inputStream==null)
                {
                    return null;
                }

                reader=new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line=reader.readLine())!=null)
                {
                    buffer.append(line+"/n");
                }

                if (buffer.length()==0)
                {
                    return null;
                }

                json_string=buffer.toString();
                Log.v(LOG_TAG,"json_string"+json_string);
            }
            catch (IOException e)
            {
                Log.e(LOG_TAG,"error",e);
                return null;
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

        private List<String> getdataFromJson(String json_string)throws JSONException
        {
            JSONObject jsonObject=new JSONObject(json_string);
            JSONArray jsonArray=jsonObject.getJSONArray("results");
       //     moviePoster=new String[jsonArray.length()];

            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject movie_list=jsonArray.getJSONObject(i);
                //moviePoster[i]=("http://image.tmdb.org/t/p/w185"+ movie_list.getString("poster_path"));
                posters.add("http://image.tmdb.org/t/p/w185"+ movie_list.getString("poster_path"));
            }
//            for (String s:moviePoster)
//            {
//                Log.v(LOG_TAG, "poster_path  " +s);
//            }
            return posters;
        }

        @Override
        protected void onPostExecute(List<String> result)
        {
            for (String r  : result) {
                Log.v(LOG_TAG,"result " + r);

            }
            movieAdapter.add(result);
  //          gv.setAdapter(movieAdapter);
           // movieAdapter.setImageUrl(result);

    /*        if (result!=null)
            {
                Log.v(LOG_TAG,"result " + result[0]);

            }
      */  }

    }

/*
    public static String[] images = {
            "http://i.imgur.com/rFLNqWI.jpg",
            "http://i.imgur.com/C9pBVt7.jpg",
            "http://i.imgur.com/rT5vXE1.jpg",
            "http://i.imgur.com/aIy5R2k.jpg",
            "http://i.imgur.com/MoJs9pT.jpg",
            "http://i.imgur.com/S963yEM.jpg",
            "http://i.imgur.com/rLR2cyc.jpg",
            "http://i.imgur.com/SEPdUIx.jpg",
            "http://i.imgur.com/aC9OjaM.jpg",
            "http://i.imgur.com/76Jfv9b.jpg",
            "http://i.imgur.com/fUX7EIB.jpg",
            "http://i.imgur.com/syELajx.jpg",
            "http://i.imgur.com/COzBnru.jpg",
            "http://i.imgur.com/Z3QjilA.jpg",
    };*/
}