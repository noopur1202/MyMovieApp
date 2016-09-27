package com.example.android.mymovieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ImageAdapter extends ArrayAdapter {

    private String[] imageUrl;
    private String[] imageText;
    private Context ctx;
    private LayoutInflater inflater;

    ImageView imageView;

    public ImageAdapter(Context context,String[] imageUrl,String[] imageText)
    {
        super(context, R.layout.image_grid,imageUrl);
        this.ctx = context;
        this.imageUrl = imageUrl;
        this.imageText = imageText;
        inflater = LayoutInflater.from(context);
    }


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

    public void setImageUrl(String[] imageUrl){
        this.imageUrl=imageUrl;
        notifyDataSetChanged();
    }

    public void setImageText(String[] imageText){
        this.imageText=imageText;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;

        if (null == convertView) {
            convertView = inflater.inflate(R.layout.image_grid, parent, false);
            holder=new ViewHolder();
            holder.titleTextView=(TextView)convertView.findViewById(R.id.image_text);
            holder.imageView=(ImageView)convertView.findViewById(R.id.movieImage);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
            }
        Picasso
                .with(ctx)
                .load(imageUrl[position])
                .fit()
                .into(holder.imageView);

        holder.titleTextView.setText(imageText[position]);

        return convertView;
    }
    static class ViewHolder {
        TextView titleTextView;
        ImageView imageView;
    }
}