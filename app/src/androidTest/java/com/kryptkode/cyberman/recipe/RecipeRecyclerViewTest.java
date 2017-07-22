package com.kryptkode.cyberman.recipe;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/**
 * Created by Cyberman on 7/22/2017.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeRecyclerViewTest {
    @Rule
   public ActivityTestRule<RecipeActivity> activityActivityTestRule =
            new ActivityTestRule<>(RecipeActivity.class);

    @Test
    public void clickItemInRecyclerViewOpensDetailActivity(){

        //check that the recycler view is displayed
        onView(withId(R.id.main_recycler_view)).check(matches(isDisplayed()));

        //find the recycler view and perform a click on the second item
        onView(withId(R.id.main_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        //assert launch of the second screen by verifying if the title on the toolbar is the right one
       // onView(withId(R.id.toolbar)).check(matches(withText("Brownies")));
    }

}
