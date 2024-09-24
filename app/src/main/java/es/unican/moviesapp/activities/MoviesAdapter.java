package es.unican.moviesapp.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import es.unican.moviesapp.R;
import es.unican.moviesapp.models.Movie;

/**
 * Custom adapter for displaying a list of movies in a ListView.
 */
public class MoviesAdapter extends BaseAdapter {

    /**
     * The list of movie objects to be displayed.
     */
    private final List<Movie> movies;

    /**
     * The context in which the adapter is operating.
     */
    private final Context context;

    /**
     * Flag indicating whether to display the movie title.
     */
    private boolean showTitle = true;

    /**
     * Flag indicating whether to display the movie director.
     */
    private boolean showDirector = true;

    /**
     * Flag indicating whether to display the movie year.
     */
    private boolean showYear = true;

    /**
     * Constructs a new MoviesAdapter.
     *
     * @param context The context in which the adapter is operating.
     * @param movies  The list of movies to be displayed.
     */
    public MoviesAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    /**
     * Gets the number of movies in the list.
     *
     * @return The number of movies.
     */
    @Override
    public int getCount() {
        return movies.size();
    }

    /**
     * Gets the movie object at the specified position.
     *
     * @param position The position of the movie.
     * @return The movie object.
     */
    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    /**
     * Gets the item ID associated with the specified position.
     *
     * @param position The position of the item.
     * @return The item ID.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Sets whether to display the movie title.
     *
     * @param showTitle True to display the title, false otherwise.
     */
    public void setShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
    }

    /**
     * Sets whether to display the movie director.
     *
     * @param showDirector True to display the director, false otherwise.
     */
    public void setShowDirector(boolean showDirector) {
        this.showDirector = showDirector;
    }

    /**
     * Sets whether to display the movie year.
     *
     * @param showYear True to display the year, false otherwise.
     */
    public void setShowYear(boolean showYear) {
        this.showYear = showYear;
    }

    /**
     * Gets a View that displays the data at the specified position in the data set.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view is non-null and of an appropriate type before using. If it is not possible to convert this view to display the correct data, this method can create a new view. Heterogeneous lists can specify their number of view types, so that this View is always of the right type (see getViewTypeCount() and getItemViewType(int)).
     * @param parent      The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Create the view that will contain the elements shown in each row of the ListView
        // Since this view (named "convertView") can be reutilized, only create it when needed
        // If there is no need to create it, just replace its contents
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.movies_list_item_layout, parent, false);
        }

        // TODO: fill the contents of "contentView" with the information of the movie at position "position"
        // The widgets of the view are defined in the layout file "movies_list_item_layout.xml"

        return convertView;

    }

}