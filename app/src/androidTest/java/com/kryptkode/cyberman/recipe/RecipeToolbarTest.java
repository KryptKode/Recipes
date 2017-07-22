package com.kryptkode.cyberman.recipe;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Cyberman on 7/22/2017.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeToolbarTest{

    @Rule
    public ActivityTestRule<RecipeActivity> recipeActivityActivityTestRule =
            new ActivityTestRule<>(RecipeActivity.class);

    @Test
    public void checkToolBarTitleOnStart(){
        //get the app name
        String appName = recipeActivityActivityTestRule.getActivity()
                .getResources().getString(R.string.app_name);

        //find the toolbar, check if it is displayed
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));

        //check if the title matches the app's name
        onView(withId(R.id.toolbar)).check(matches(withText(appName)));
    }


}
