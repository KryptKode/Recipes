package com.kryptkode.cyberman.recipe;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;

import static com.kryptkode.cyberman.recipe.utils.TestUtils.withRecyclerView;

/**
 * Created by Cyberman on 7/27/2017.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeVideoPlayerTest {

    @Rule
    public ActivityTestRule<RecipeActivity> activityTestRule =
            new ActivityTestRule<>(RecipeActivity.class);

    @Test
    public void onClickPlayVideoButtonPlaysVideo(){
        //check that the recycler view is displayed
        onView(withId(R.id.main_recycler_view)).check(matches(isDisplayed()));

        //find the recycler view and perform a click on the second item
        onView(withId(R.id.main_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        //assert launch of the second screen by verifying if the title on the toolbar is the right one
        matchToolbarTitle("Yellow Cake");

        //check if the recycler view of the recipe steps is showing
        onView(withId(R.id.steps_recycler_view)).check(matches(isDisplayed()));

        //scroll to the  position and find the view with a name as shown and click
        onView(withRecyclerView(R.id.steps_recycler_view).atPositionOnView(0, R.id.steps_play_video)).perform(click());

        //check for play/pause buttons
        onView(withId(R.id.exo_play)).check(matches(isEnabled()));
        onView(withId(R.id.exo_pause)).check(matches(isEnabled()));
        onView(withId(R.id.exo_rew)).check(matches(isDisplayed()));
        onView(withId(R.id.exo_ffwd)).check(matches(isDisplayed()));
        onView(withId(R.id.exo_prev)).check(matches(isEnabled())).check(matches(isDisplayed()));



    }


    private static ViewInteraction matchToolbarTitle(CharSequence title){
        return onView(isAssignableFrom(Toolbar.class)).
                check(matches(withToolbarTitle(is(title))));
    }

    private static Matcher<Object> withToolbarTitle(final Matcher<CharSequence> textMatcher) {
        return new BoundedMatcher<Object, Toolbar>(Toolbar.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("with toolbar title: ");
                textMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(Toolbar toolbar) {
                return textMatcher.matches(toolbar.getTitle());
            }
        };
    }
}
