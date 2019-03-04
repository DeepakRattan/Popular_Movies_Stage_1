package com.example.deepakrattan.popular_movies_stage_1;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.deepakrattan.popular_movies_stage_1.Model.Movie;
import com.example.deepakrattan.popular_movies_stage_1.adapter.MoviePosterAdapter;
import com.example.deepakrattan.popular_movies_stage_1.utilities.ConnectivityHelper;
import com.example.deepakrattan.popular_movies_stage_1.utilities.NetworkUtilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private GridView gridView;
    private String sort_type;
    public static final String TAG = "TEST";
    private ArrayList<Movie> moviePostersList;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //findViewByID
        gridView = findViewById(R.id.gridView);
        sharedPreferences = getSharedPreferences("popular_movies", MODE_PRIVATE);
        sort_type = sharedPreferences.getString("sort_type", "popular");

        if (sort_type.equals("popular"))
            getSupportActionBar().setTitle(R.string.popular);
        else if (sort_type.equals("top_rated"))
            getSupportActionBar().setTitle(R.string.top_rated);

        //Handle click event on posters

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("movie_detail", moviePostersList.get(i));
                startActivity(intent);
            }
        });

        //Checking for internet connectivity
        boolean isConnectedToInternet = ConnectivityHelper.isConnectedToNetwork(MainActivity.this);
        if (isConnectedToInternet)
            new FetchMovie().execute(sort_type);
        else {
            Toast.makeText(this, "Please check internet connectivity", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sort_settings) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            int selected = 0;
            sort_type = sharedPreferences.getString("sort_type", "popular");
            if (sort_type.equals("popular"))
                selected = 0;
            else if (sort_type.equals("top_rated"))
                selected = 1;
            builder.setTitle(R.string.dialog_title);
            builder.setSingleChoiceItems(R.array.sort_types, selected, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == 0)
                        editor.putString("sort_type", "popular");
                    else if (i == 1)
                        editor.putString("sort_type", "top_rated");
                }
            });

            builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    editor.commit();

                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //user clicked cancel
                }
            });
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    //refresh activity
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    class FetchMovie extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String sort_type = params[0];
            URL movieRequestURL = NetworkUtilities.buildURL(sort_type);
            try {
                String movieResponse = NetworkUtilities.getResponseFromHttpUrl(movieRequestURL);
                return movieResponse;

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                moviePostersList = getPosters(s);
                //Adapter
                MoviePosterAdapter adapter = new MoviePosterAdapter(MainActivity.this, moviePostersList);
                gridView.setAdapter(adapter);

            }
        }
    }


    ArrayList<Movie> getPosters(String s) {
        moviePostersList = new ArrayList<>();

        //Json parsing
        try {
            JSONObject object = new JSONObject(s);
            JSONArray resultsArray = object.getJSONArray("results");
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject jsonObject = resultsArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String path = jsonObject.getString("poster_path");
                String original_title = jsonObject.getString("original_title");
                String releaseDate = jsonObject.getString("release_date");
                double votAverage = jsonObject.getDouble("vote_average");
                String overview = jsonObject.getString("overview");
                Movie movie = new Movie(id, path, original_title, releaseDate, overview, votAverage);
                moviePostersList.add(movie);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return moviePostersList;
    }


}
