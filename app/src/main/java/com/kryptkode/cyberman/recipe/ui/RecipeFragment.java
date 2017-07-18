package com.kryptkode.cyberman.recipe.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.kryptkode.cyberman.recipe.R;
import com.kryptkode.cyberman.recipe.adapters.RecipeAdapter;
import com.kryptkode.cyberman.recipe.model.Recipes;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeFragment extends Fragment implements RecipeAdapter.RecipeAdapterCallbacks{

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecipeAdapter recipeAdapter;
    private Recipes [] recipesArray;

    public RecipeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.main_recycler_view);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recipeAdapter = new RecipeAdapter(getContext(), recipesArray);

        recyclerView.setAdapter(recipeAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    public void setRecipesArray(Recipes[] recipesArray) {
        this.recipesArray = recipesArray;
    }


    @Override
    public void onRecipeItemClicked(int position) {

    }

    @Override
    public void onStepsButtonClicked(int position) {

    }

    @Override
    public void onIngredientsButtonClicked(int position) {

    }
}
