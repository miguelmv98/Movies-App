package es.unican.moviesapp.models;

import java.util.Objects;

/**
 * Represents a movie with details such as title, year, director, cover image, synopsis, and actors.
 */
public class Movie {

    /**
     * The title of the movie.
     */
    private final String title;

    /**
     * The year the movie was released.
     */
    private final int year;

    /**
     * The director of the movie.
     */
    private final String director;

    /**
     * The URL of the movie's cover image.
     */
    private final String coverUrl;

    /**
     * A brief synopsis of the movie's plot.
     */
    private final String synopsis;

    /**
     * A list of actors starring in the movie, separated by commas.
     */
    private final String actors;

    /**
     * Constructs a new Movie object.
     *
     * @param title    The title of the movie.
     * @param coverUrl The URL of the movie's cover image.
     * @param year     The year the movie was released.
     * @param director The director of the movie.
     * @param synopsis A brief synopsis of the movie's plot.
     * @param actors   A list of actors starring in the movie, separated by commas.
     */
    public Movie(String title, String coverUrl, int year, String director, String synopsis, String actors) {
        this.title = title;
        this.coverUrl = coverUrl;
        this.year = year;
        this.director = director;
        this.synopsis = synopsis;
        this.actors = actors;
    }

    /**
     * Gets the title of the movie.
     *
     * @return The title of the movie.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the URL of the movie's cover image.
     *
     * @return The URL of the movie's cover image.
     */
    public String getCoverUrl() {
        return coverUrl;
    }

    /**
     * Gets the year the movie was released.
     *
     * @return The year the movie was released.
     */
    public int getYear() {
        return year;
    }

    /**
     * Gets the director of the movie.
     *
     * @return The director of the movie.
     */
    public String getDirector() {
        return director;
    }

    /**
     * Gets a brief synopsis of the movie's plot.
     *
     * @return A brief synopsis of the movie's plot.
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * Gets a list of actors starring in the movie.
     *
     * @return A list of actors starring in the movie, separated by commas.
     */
    public String getActors() {
        return actors;
    }

    /**
     * Checks if this Movie object is equal to another object.
     * Two Movie objects are considered equal if they have the same title, year, and director.
     *
     * @param o The object to compare to.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(title, movie.title) && Objects.equals(year, movie.year) && Objects.equals(director, movie.director);
    }

    /**
     * Generates a hash code for this Movie object.
     * The hash code is based on the title, year, and director.
     *
     * @return The hash code for this Movie object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(title, year, director);
    }
}