package com.example.android.mymovieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageAdapter extends ArrayAdapter {

    private String[] imageUrl;
    private Context ctx;
    private LayoutInflater inflater;

    public ImageAdapter(Context context, String[] imageUrl)
    {
        super(context, R.layout.image_grid, imageUrl);
        this.ctx = context;
        this.imageUrl = imageUrl;
        inflater = LayoutInflater.from(context);
    }

  /*  @Override
    public int getCount()
    {
      return imageUrl.length;
    }*/

    @Override
    public Object getItem(int position)
    {
      return null;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView iv;

        iv=(ImageView)convertView;
        if (iv == null)
        {
            iv=new ImageView(getContext());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            convertView = inflater.inflate(R.layout.image_grid, parent, false);
        }

        Picasso
                .with(ctx)
                .load(imageUrl[position])
                .into(iv);

        return convertView;
    }
}
