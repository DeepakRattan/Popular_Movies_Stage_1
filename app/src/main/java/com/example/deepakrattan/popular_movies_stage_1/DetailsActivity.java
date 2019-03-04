package com.example.deepakrattan.popular_movies_stage_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deepakrattan.popular_movies_stage_1.Model.Movie;
import com.example.deepakrattan.popular_movies_stage_1.utilities.NetworkUtilities;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    TextView txtTitle, txt_user_rating, txt_release_date, txt_synopsis;
    ImageView img_poster;
    String poster_path, complete_poster_path, title, overview, releaseDate;
    double userRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //findViewByID
        txtTitle = findViewById(R.id.txtTitle);
        txt_user_rating = findViewById(R.id.txt_user_rating);
        txt_release_date = findViewById(R.id.txt_release_date);
        txt_synopsis = findViewById(R.id.txt_synopsis);
        img_poster = findViewById(R.id.img_poster);

        getSupportActionBar().setTitle(R.string.detail_activity);

        //Getting object containing information of clicked poster
        Movie movie = getIntent().getParcelableExtra("movie_detail");
        if (movie != null) {
            poster_path = movie.getPath();
            complete_poster_path = NetworkUtilities.MOVIES_POSTER_BASE_URL + poster_path;
            title = movie.getOriginal_title();
            overview = movie.getOverview();
            releaseDate = movie.getRelease_date();
            userRating = movie.getVote_average();


            //Setting data to views
            txtTitle.setText(title);
            txt_user_rating.setText("User Rating : " + String.valueOf(userRating));
            txt_release_date.setText("Release Date : " + releaseDate);
            txt_synopsis.setText(overview);
            Picasso.with(this).load(complete_poster_path).into(img_poster);
        } else {
            Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show();
        }
    }
}
