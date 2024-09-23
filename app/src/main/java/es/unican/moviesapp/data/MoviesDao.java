package es.unican.moviesapp.data;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import es.unican.moviesapp.R;
import es.unican.moviesapp.models.Movie;

/**
 * Data Access Object for managing movies. It reads movie data from a JSON file
 * and provides methods to retrieve, add, remove, and update movies.
 */
public class MoviesDao {

    /**
     * Cached list of movies
     */
    private final List<Movie> movies;

    /**
     * Application context used to access resources, such as the json file
     */
    private final Context context;

    /**
     * Constructs a new MoviesDao.
     *
     * @param context The application context used to access resources.
     */
    public MoviesDao(Context context) {
        this.context = context;
        this.movies = createMoviesList();
    }

    /**
     * Reads movie data from a JSON file (R.raw.movies) and creates a list of Movie objects.
     *
     * @return A list of Movie objects or null if an error occurs.
     */
    private List<Movie> createMoviesList() {
        List<Movie> result = null;
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
            result = gson.fromJson(json, movieListType);

        } catch (IOException e) {
            Log.e(MoviesDao.class.getSimpleName(), "Error reading movies.json", e);
        }

        return result;
    }

    /**
     * Returns an unmodifiable list of movies.
     *
     * @return An unmodifiable list of Movie objects.
     */
    public List<Movie> getMovies() {
        return Collections.unmodifiableList(movies);
    }

    /**
     * Adds a new movie to the list.
     *
     * @param movie The movie to be added.
     */
    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    /**
     * Removes a movie from the list.
     *
     * @param movie The movie to be removed.
     */
    public void removeMovie(Movie movie) {
        movies.remove(movie);
    }

}