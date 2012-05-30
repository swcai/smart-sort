package com.stanleycai.smartsort.collaborative;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.stanleycai.utils.StringUtils;

public class Movie {
    /*
     * movie id | movie title | release date | video release date | IMDb URL |
     * unknown | Action | Adventure | Animation | Children's | Comedy | Crime |
     * Documentary | Drama | Fantasy | Film-Noir | Horror | Musical | Mystery |
     * Romance | Sci-Fi | Thriller | War | Western |
     */
    private int mId;
    private String mTitle;
    private String mReleaseDate;
    private String mVideoReleaseDate;
    private String mIMDbURL;
    private Set<String> mTags;
    private static final String[] TAGS = { "unknown", "Action", "Adventure",
            "Animation", "Children's", "Comedy", "Crime", "Documentary",
            "Drama", "Fantasy", "Film-Noir", "Horror", "Musical", "Mystery",
            "Romance", "Sci-Fi", "Thriller", "War", "Western" };

    private Movie(int id, String title, String releaseDate,
            String videoReleaseDate, String IMDbURL, String... tags) {
        mId = id;
        mTitle = title;
        mReleaseDate = releaseDate;
        mVideoReleaseDate = videoReleaseDate;
        mIMDbURL = IMDbURL;
        mTags = new HashSet<String>();
        for (int i = 0; i < tags.length; ++i)
            if (tags[i].equals("1"))
                mTags.add(TAGS[i]);
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(String.format(
                "[id:%d title:%s release:%s videoRelease:%s url:%s", mId,
                mTitle, mReleaseDate, mVideoReleaseDate, mIMDbURL));
        buf.append(" :[");
        buf.append(StringUtils.join(", ", mTags));
        buf.append("]]");
        return buf.toString();
    }

    public static Movie[] loadMoviesFromFile(String itemFilename) {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(itemFilename)));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\|"); // stupid Java regex
                movies.add(new Movie(Integer.valueOf(fields[0]), fields[1],
                        fields[2], fields[3], fields[4], Arrays.copyOfRange(
                                fields, 5, fields.length - 5)));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return movies.toArray(new Movie[0]);
    }
}
