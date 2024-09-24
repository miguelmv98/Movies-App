package es.unican.moviesapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.preference.PreferenceManager;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import es.unican.moviesapp.R;
import es.unican.moviesapp.data.MoviesDao;
import es.unican.moviesapp.models.Movie;

/**
 * MainActivity class represents the main activity of the Movies app.
 * It manages the UI, user interactions, and displays a list of movies.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * DrawerLayout for the navigation drawer (menu that slides-in from the left)
     */
    private DrawerLayout drawerLayout;

    /**
     * ActionBarDrawerToggle to manage the hamburger button for the drawer.
     */
    private ActionBarDrawerToggle toggle;

    /**
     * MoviesDao to handle data access for movies.
     */
    private MoviesDao moviesDao;

    /**
     * MoviesAdapter to connect the list of movies to the ListView.
     */
    private MoviesAdapter moviesAdapter;

    /**
     * List of movies currently shown in the ListView.
     */
    private final List<Movie> shownMovies = new ArrayList<>();

    /**
     * List of all movies fetched from the MoviesDao.
     */
    private final List<Movie> allMovies = new ArrayList<>();

    /**
     * Called when the activity is created. Initializes the UI components,
     * sets up the navigation drawer, toolbar, and fetches the list of movies.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     * this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create DAO
        moviesDao = new MoviesDao(this);

        // Get layout widgets
        drawerLayout = findViewById(R.id.drawer_layout);  // left-side drawer
        NavigationView navigationView = findViewById(R.id.navigation_view);  // controls the drawer
        Toolbar toolbar = findViewById(R.id.toolbar);  // upper toolbar
        ListView lvMovies = findViewById(R.id.lvMovies);  // listview to show the list of movies

        // Set the toolbar in the layout as the activity action bar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Add button (hamburger) in actionbar, and make it open the drawer
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Set-up drawer
        navigationView.setNavigationItemSelectedListener(this);
        // Close drawer when back button is pressed
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    finish();
                }
            }
        });

        // Get movies from the DAO, store them in allMovies and shownMovies
        fetchMovies();

        // Set-up Adapter for the ListView
        moviesAdapter = new MoviesAdapter(this, shownMovies);  // attaches shownMovies to the adapter
        configureAdapter(moviesAdapter);  // configures adapter with persisted settings

        // Set-up the ListView with the Adapter created above

        // TODO: find the reference to the ListView, and set its adapter
    }

    /**
     * Fetches movies from the MoviesDao and updates the shownMovies and allMovies lists.
     */
    private void fetchMovies() {
        shownMovies.clear();
        allMovies.clear();
        allMovies.addAll(moviesDao.getMovies());  // DAO returns an unmodifiable list
        shownMovies.addAll(allMovies);
    }

    /**
     * Configures the MoviesAdapter based on user settings from SharedPreferences.
     * Does not trigger a refresh of the movie list view
     *
     * @param moviesAdapter The MoviesAdapter to be configured.
     */
    private void configureAdapter(MoviesAdapter moviesAdapter) {
        // retrieve the persisted settings
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean showTitle = sharedPreferences.getBoolean(getString(R.string.show_title), true);
        boolean showYear = sharedPreferences.getBoolean(getString(R.string.show_year), true);
        boolean showDirector = sharedPreferences.getBoolean(getString(R.string.show_director), true);

        // update the adapter settings accordingly
        moviesAdapter.setShowTitle(showTitle);
        moviesAdapter.setShowYear(showYear);
        moviesAdapter.setShowDirector(showDirector);
    }

    /**
     * Called when the activity is restarted. Perhaps the settings have changed.
     * Reconfigures the adapter and refreshes the movie list according to the current settings
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        configureAdapter(moviesAdapter);
        moviesAdapter.notifyDataSetChanged();
    }

    /**
     * Handles item selections in the options menu.
     *
     * @param item The selected menu item.
     * @return true if the item was handled, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        } else if (itemId == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Handles item selections in the navigation drawer.
     *
     * @param item The selected menu item.
     * @return true if the item was handled, false otherwise.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        drawerLayout.closeDrawers();
        return true;
    }

    /**
     * Creates the options menu, including a SearchView for filtering the movies list.
     *
     * @param menu The options menu.
     * @return true if the menu was created successfully.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);

        // Handle SearchView in actionbar
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        assert searchView != null;

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterMoviesList(newText);
                moviesAdapter.notifyDataSetChanged();
                return true;
            }
        });
        return true;
    }

    /**
     * Shows a popup dialog with detailed information about the selected movie.
     *
     * @param movie The movie to display details for.
     */
    public void showMoviePopupDetails(Movie movie) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.movie_dialog_layout, null);

        // TODO fill the contents of "dialogView" with the information of the movie

    }

    /**
     * Filters the list of movies based on a search string.
     * Modifies the {@code shownMovies} list to show only the movies that match the search.
     *
     * @param substring The substring to filter the movie titles, years, and directors by.
     */
    private void filterMoviesList(String substring) {

        // TODO filter the elements of "shownMovies" according to "substring"

    }

}
