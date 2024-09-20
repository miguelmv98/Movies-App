package es.unican.moviesapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import es.unican.moviesapp.R;
import es.unican.moviesapp.data.MoviesDao;
import es.unican.moviesapp.models.Movie;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    private MoviesDao moviesDao;
    private ListView lvMovies;
    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create DAO
        moviesDao = new MoviesDao(this);

        // Fetch layout widgets
        NavigationView navigationView = findViewById(R.id.navigation_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        lvMovies = findViewById(R.id.lvMovies);

        // Set the toolbar as the activity action bar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Add button (hamburger) in actionbar to open drawer
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Set-up drawer
        navigationView.setNavigationItemSelectedListener(this);
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

        getResources().openRawResource(R.raw.movies);

        // Set-up listview
        moviesAdapter = new MoviesAdapter(this, moviesDao.getMovies(), new ListOnClickListener());
        configureAdapter();
        lvMovies.setAdapter(moviesAdapter);
    }

    private void configureAdapter() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean showTitle = sharedPreferences.getBoolean(getString(R.string.show_title), true);
        boolean showYear = sharedPreferences.getBoolean(getString(R.string.show_year), true);
        boolean showDirector = sharedPreferences.getBoolean(getString(R.string.show_director), true);
        moviesAdapter.setShowTitle(showTitle);
        moviesAdapter.setShowYear(showYear);
        moviesAdapter.setShowDirector(showDirector);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        configureAdapter();
        moviesAdapter.notifyDataSetChanged();
    }

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        int id = item.getItemId();

        if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        drawerLayout.closeDrawers();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);

        // handle searchview in actionbar
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
                moviesAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }

    class ListOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            final Movie movie = (Movie) v.getTag();
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater inflater = MainActivity.this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.movie_dialog_layout, null);

            ImageView ivCover = dialogView.findViewById(R.id.ivCover);
            TextView tvTitle = dialogView.findViewById(R.id.tvTitle);
            TextView tvDirector = dialogView.findViewById(R.id.tvDirector);
            TextView tvSynopsis = dialogView.findViewById(R.id.tvSynopsis);
            TextView tvActors = dialogView.findViewById(R.id.tvActors);

            int width = (int) MainActivity.this.getResources().getDimension(R.dimen.cover_width);
            Picasso.get().load(movie.getCoverUrl())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .resize((int) (width*1.4), 0)
                    .centerCrop()
                    .into(ivCover);
            tvTitle.setText(movie.getTitle());
            tvDirector.setText(movie.getDirector());
            tvActors.setText(movie.getActors());
            tvSynopsis.setText(movie.getSynopsis());

            builder
                    .setView(dialogView)
                    .setPositiveButton(R.string.close, (dialog, which) -> {
                        // just close
                    })
            ;

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}