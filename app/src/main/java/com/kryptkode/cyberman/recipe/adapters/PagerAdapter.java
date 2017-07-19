package com.kryptkode.cyberman.recipe.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kryptkode.cyberman.recipe.ui.ContentFragment;
import com.kryptkode.cyberman.recipe.ui.IngredientsFragment;
import com.kryptkode.cyberman.recipe.ui.StepsFragment;

/**
 * Created by Cyberman on 7/18/2017.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    private int tabCount;
    private Bundle stepsBundle;
    private Bundle ingredientsBundle;
    private StepsFragment.StepFragmentCallbacks callback;

    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
               StepsFragment stepFragment = new StepsFragment();
                stepFragment.setArguments(stepsBundle);
                stepFragment.setStepFragmentCallbacks(callback);
                return stepFragment;
            case 1:
                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                ingredientsFragment.setArguments(ingredientsBundle);
                return ingredientsFragment;
            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return tabCount;
    }

    public void setStepsBundle(Bundle bundle) {
        this.stepsBundle = bundle;
    }

    public void setIngredientsBundle(Bundle ingredientsBundle) {
        this.ingredientsBundle = ingredientsBundle;
    }

    public void setCallback(StepsFragment.StepFragmentCallbacks callback) {
        this.callback = callback;
    }
}
