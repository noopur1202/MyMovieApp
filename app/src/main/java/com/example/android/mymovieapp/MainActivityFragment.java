package com.example.android.mymovieapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONException;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    public static Fragment newInstance(Context context)
    {
      MainActivityFragment a = new MainActivityFragment();
      return a;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        final ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_main, null);
        GridView gv1=(GridView)viewGroup.findViewById(R.id.grid_fragment);
        gv1.setAdapter(new ImageAdapter(getActivity(),images));

        gv1.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                startActivity(intent);
            }
        });
        return viewGroup;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.action_refresh:
     //           UpdateScreen();
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

    public class FetchDetails extends AsyncTask<String, Void, String[]>{

        private String getdataFromJson()throws JSONException{


            return null;
        }

        @Override
        protected String[] doInBackground(String... strings) {
            return new String[0];
        }
    }


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
    };
}