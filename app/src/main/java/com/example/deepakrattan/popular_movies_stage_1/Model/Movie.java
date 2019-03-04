package com.example.deepakrattan.popular_movies_stage_1.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private int id;
    private String path, original_title, release_date, overview;
    private double vote_average;

    public Movie(int id, String path, String original_title, String release_date, String overview, double vote_average) {
        this.id = id;
        this.path = path;
        this.original_title = original_title;
        this.release_date = release_date;
        this.overview = overview;
        this.vote_average = vote_average;
    }

    protected Movie(Parcel in) {
        id = in.readInt();
        path = in.readString();
        original_title = in.readString();
        release_date = in.readString();
        overview = in.readString();
        vote_average = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(path);
        dest.writeString(original_title);
        dest.writeString(release_date);
        dest.writeString(overview);
        dest.writeDouble(vote_average);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getOverview() {
        return overview;
    }

    public double getVote_average() {
        return vote_average;
    }
}
