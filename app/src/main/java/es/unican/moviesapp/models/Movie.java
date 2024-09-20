package es.unican.moviesapp.models;

import java.util.Objects;

public class Movie {

    private final String title;

    private final int year;

    private final String director;

    private final String coverUrl;

    private final String synopsis;

    private final String actors;

    public Movie(String title, String coverUrl, int year, String director, String synopsis, String actors) {
        this.title = title;
        this.coverUrl = coverUrl;
        this.year = year;
        this.director = director;
        this.synopsis = synopsis;
        this.actors = actors;
    }

    public String getTitle() {
        return title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public int getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getActors() {
        return actors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(title, movie.title) && Objects.equals(year, movie.year) && Objects.equals(director, movie.director);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, year, director);
    }
}
