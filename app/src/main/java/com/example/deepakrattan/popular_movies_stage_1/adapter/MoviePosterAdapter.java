package com.example.deepakrattan.popular_movies_stage_1.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.deepakrattan.popular_movies_stage_1.Model.Movie;
import com.example.deepakrattan.popular_movies_stage_1.utilities.NetworkUtilities;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class MoviePosterAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Movie> moviePostersList;
    private String[] moviePosterPaths;

    public MoviePosterAdapter(Context context, ArrayList<Movie> moviePostersList) {
        this.context = context;
        this.moviePostersList = moviePostersList;
        moviePosterPaths = new String[moviePostersList.size()];
        for (int i = 0; i < moviePostersList.size(); i++) {
            moviePosterPaths[i] = NetworkUtilities.MOVIES_POSTER_BASE_URL + moviePostersList.get(i).getPath();

        }
    }


    @Override
    public int getCount() {
        return moviePostersList.size();
    }

    @Override
    public Object getItem(int position) {
        return moviePostersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(354, 278));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        Picasso.with(context).load(moviePosterPaths[position]).into(imageView);
        return imageView;
    }
}
