package com.kryptkode.cyberman.recipe.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kryptkode.cyberman.recipe.R;
import com.kryptkode.cyberman.recipe.RecipeActivity;
import com.kryptkode.cyberman.recipe.adapters.PagerAdapter;
import com.kryptkode.cyberman.recipe.model.Ingredients;
import com.kryptkode.cyberman.recipe.model.Recipes;
import com.kryptkode.cyberman.recipe.model.Steps;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment implements StepsFragment.StepFragmentCallbacks{
    public static final String ARG = "bundle";
    private Steps[] steps;
    private Ingredients[] ingredients;
    private String recipeName;
    private ViewPager viewPager;
    private int whichTab;
    private boolean toSelectTab;

    private ContentFragmentCallbacks callback;

    public ContentFragment() {
        // Required empty public constructor
    }

    public interface ContentFragmentCallbacks{
        void onPlayVideoButtonClicked(String videoUrl);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        ingredients = (Ingredients[]) bundle.getParcelableArray(Ingredients.KEY);
        steps = (Steps[]) bundle.getParcelableArray(Steps.KEY);
        recipeName = bundle.getString(Recipes.KEY);

        if (bundle.containsKey(RecipeActivity.DETERMINANT)) {
            whichTab = bundle.getInt(RecipeActivity.DETERMINANT);
            toSelectTab = true;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
//        callback.onSetContentActionBarTitle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(recipeName);
        appCompatActivity.setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.content_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.steps)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.ingredients)));

        Bundle ingredientBundle = new Bundle();
        ingredientBundle.putParcelableArray(Ingredients.KEY, ingredients);
        Bundle bundleSteps = new Bundle();
        bundleSteps.putParcelableArray(Steps.KEY, steps);

        viewPager = (ViewPager) view.findViewById(R.id.content_view_pager);
        final PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        pagerAdapter.setStepsBundle(bundleSteps);
        pagerAdapter.setIngredientsBundle(ingredientBundle);
        pagerAdapter.setCallback(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        if (toSelectTab) {

            selectTab(whichTab);
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }


    private void selectTab (int position) {
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onPlayVideoButtonClicked(String videoUrl) {
        callback.onPlayVideoButtonClicked(videoUrl);
    }

    public void setCallback(ContentFragmentCallbacks callback) {
        this.callback = callback;
    }
}