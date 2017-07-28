package com.kryptkode.cyberman.recipe;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.kryptkode.cyberman.recipe.adapters.RecipeAdapter;
import com.kryptkode.cyberman.recipe.utils.IntentServiceIdlingResource;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Cyberman on 7/27/2017.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeActivityFirstRunTest {
    @Rule
    public ActivityTestRule<RecipeActivity> activityActivityTestRule = new ActivityTestRule<>(RecipeActivity.class);

    private IntentServiceIdlingResource intentServiceIdlingResource;

    @Before
    public void registerIntentServiceIdlingResource(){ //register the idling resource before the test
        Instrumentation  instrumentation = InstrumentationRegistry.getInstrumentation();
        intentServiceIdlingResource = new IntentServiceIdlingResource(instrumentation.getContext());
        Espresso.registerIdlingResources(intentServiceIdlingResource);
    }
    @After
    public void unregisterIntentServiceIdlingResource(){ //unregister the idling resource after the test
        Espresso.unregisterIdlingResources(intentServiceIdlingResource);
    }

    @Test
    public void verifyActualContentsLoaded(){
        //check if the recycler view is showing
        onView(withId(R.id.main_recycler_view)).check(matches(isDisplayed()));

        //scroll to the  position and find the view with a name as shown
        onView(withId(R.id.main_recycler_view)).perform(RecyclerViewActions.scrollToHolder(withNameOfRecipe("Cheesecake")));

    }

    private Matcher<RecyclerView.ViewHolder> withNameOfRecipe(final String nameOfRecipe) {
        return new BoundedMatcher<RecyclerView.ViewHolder, RecipeAdapter.RecipeViewHolder>(RecipeAdapter.RecipeViewHolder.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("No view found with name: " + nameOfRecipe);
            }

            @Override
            protected boolean matchesSafely(RecipeAdapter.RecipeViewHolder item) {
                TextView nameTextView = (TextView) item.itemView.findViewById(R.id.main_title_textView);
                return nameTextView != null && nameTextView.getText().toString().contains(nameOfRecipe);
            }
        };
    }
}
