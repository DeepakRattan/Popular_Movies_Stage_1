package com.example.deepakrattan.popular_movies_stage_1.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtilities {
    public static final String TAG = NetworkUtilities.class.getName();
    public static final String MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/";
    public static final String MOVIES_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";
    //Your API KEY HERE
    static final String api_key = "";


    public static URL buildURL(String sort_type) {
        Uri builtUri;
        URL url = null;

        try {
            builtUri = Uri.parse(MOVIES_BASE_URL + sort_type + "?api_key=" + api_key);
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
