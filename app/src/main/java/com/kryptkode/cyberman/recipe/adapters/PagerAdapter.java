package com.kryptkode.cyberman.recipe.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kryptkode.cyberman.recipe.ui.IngredientsFragment;
import com.kryptkode.cyberman.recipe.ui.StepsFragment;

/**
 * Created by Cyberman on 7/18/2017.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    int tabCount;

    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new StepsFragment();
            case 1:
                return new IngredientsFragment();
            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
