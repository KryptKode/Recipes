package com.kryptkode.cyberman.recipe;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anything;

/**
 * Created by Cyberman on 7/22/2017.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeRecyclerViewOpensRightContentFragmentTest {
    @Rule
   public ActivityTestRule<RecipeActivity> activityActivityTestRule =
            new ActivityTestRule<>(RecipeActivity.class);

    @Test
    public void clickItemInRecyclerViewOpensDetailScreen(){

        //check that the recycler view is displayed
        onView(withId(R.id.main_recycler_view)).check(matches(isDisplayed()));

        //find the recycler view and perform a click on the second item
        onView(withId(R.id.main_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        //assert launch of the second screen by verifying if the title on the toolbar is the right one
        matchToolbarTitle("Brownies");
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
