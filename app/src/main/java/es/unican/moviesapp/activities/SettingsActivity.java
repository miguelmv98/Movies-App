package es.unican.moviesapp.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceFragmentCompat;

import es.unican.moviesapp.R;

/**
 * Activity representing the settings screen of the Movies App.
 * It hosts a {@link SettingsFragment} to display and manage user preferences.
 */
public class SettingsActivity extends AppCompatActivity {

    /**
     * Fragment responsible for rendering and handling the settings preferences.
     */
    public static class SettingsFragment extends PreferenceFragmentCompat {
        /**
         * Called during fragment creation to inflate the preferences hierarchy from an XML resource.
         *
         * @param savedInstanceState If the fragment is being re-created from a previous saved state, this is the state.
         * @param rootKey            If non-null, this preference fragment should be rooted at the PreferenceScreen with this key.
         */
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);
        }
    }

    /**
     * Called when the activity is starting.
     * Initializes the activity's UI, sets up the toolbar, and renders the settings fragment.
     *
     * @param savedInstanceState If the activity is being re-created from a previous saved state, this is the state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        assert bar != null;
        bar.setDisplayHomeAsUpEnabled(true);

        // render settings fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.settings_container, new SettingsFragment()).commit();
    }

    /**
     * Handles clicks on menu items in the action bar.
     *
     * @param item The menu item that was clicked.
     * @return true if the event was handled, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}