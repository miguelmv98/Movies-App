package es.unican.moviesapp.activities;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import es.unican.moviesapp.R;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testClickHomeInDrawer() {
        // Open the navigation drawer
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
                .perform(DrawerActions.open());

        // Click on the "Settings" item in the navigation drawer
        Espresso.onView(ViewMatchers.withId(R.id.navigation_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_settings));

    }

}
