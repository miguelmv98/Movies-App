package es.unican.moviesapp.data;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import es.unican.moviesapp.R;
import es.unican.moviesapp.models.Movie;

public class MoviesDao {

    private final List<Movie> movies = new ArrayList<>();
    private final Context context;

    public MoviesDao(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        try {
            InputStream is = context.getResources().openRawResource(R.raw.movies);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convert buffer to string
            String json = new String(buffer, StandardCharsets.UTF_8);

            // Parse JSON using Gson
            Gson gson = new Gson();
            Type movieListType = new TypeToken<List<Movie>>() {
            }.getType();
            List<Movie> _movies = gson.fromJson(json, movieListType);
            movies.addAll(_movies);

            //        movies.add(new Movie("The Godfather", R.drawable.godfather, 1972", "Francis Ford Coppola"));
            //        movies.add(new Movie("Pulp Fiction", R.drawable.pulp_fiction, "1994", "Quentin Tarantino"));
            //        movies.add(new Movie("Inception", R.drawable.dark_knight, "2010", "Christopher Nolan"));
            //        movies.add(new Movie("The Dark Knight", R.drawable.inception, "2008", "Christopher Nolan"));

        } catch (IOException e) {
            Log.e(MoviesDao.class.getSimpleName(), "Error reading movies.json", e);
        }
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void removeMovie(Movie movie) {
        movies.remove(movie);
    }

    public void updateMovie(Movie movie) {
        movies.set(movies.indexOf(movie), movie);
    }

}
