package com.kryptkode.cyberman.recipe.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kryptkode.cyberman.recipe.R;
import com.kryptkode.cyberman.recipe.RecipeService;
import com.kryptkode.cyberman.recipe.adapters.RecipeAdapter;
import com.kryptkode.cyberman.recipe.model.Recipes;
import com.kryptkode.cyberman.recipe.utils.JsonHelper;
import com.kryptkode.cyberman.recipe.utils.NetworkHelper;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeFragment extends Fragment implements RecipeAdapter.RecipeAdapterCallbacks{

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecipeAdapter recipeAdapter;
    private Recipes [] recipesArray;
    private RecipeFragmentCallbacks recipeFragmentCallbacks;
    private JsonDataReceiver receiver;
    private ProgressBar progressBar;
    private TextView errorTextView;
    private TextView loadingTextView;
    private ImageView errorImageView;
    private View view;
    private boolean firstLoad;

    public interface RecipeFragmentCallbacks{
        void onRecipeItemClicked(Recipes recipe);
        void onStepsButtonClicked(Recipes recipe);
        void onIngredientsButtonClicked(Recipes recipe);
    }

    public RecipeFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!firstLoad){

            hideLoadingIndicators();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

        AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        appCompatActivity.setSupportActionBar(toolbar);
        receiver = new JsonDataReceiver();
        progressBar = (ProgressBar) view.findViewById(R.id.loading_progress_bar);
        loadingTextView = (TextView) view.findViewById(R.id.loading_text_view);
        errorTextView = (TextView) view.findViewById(R.id.error_text_view);
        errorImageView = (ImageView) view.findViewById(R.id.error_image_view);


        LocalBroadcastManager.getInstance(getContext())
                .registerReceiver(receiver, new IntentFilter(RecipeService.ACTION_RETURN_JSON_TO_ACTIVITY));
        recyclerView = (RecyclerView) view.findViewById(R.id.main_recycler_view);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recipeAdapter = new RecipeAdapter(getContext(), recipesArray);
        recipeAdapter.setRecipeAdapterCallbacks(this);
        recyclerView.setAdapter(recipeAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        if (!NetworkHelper.isOnline(getContext())){
            showNoInternetError();
        }
        else{
            hideNoInternetError();
            showLoadingIndicators();
            firstLoad = true;
        }


        return view;
    }

    public void showNoInternetError(){
        recyclerView.setVisibility(View.INVISIBLE);
        errorImageView.setVisibility(View.VISIBLE);
        errorTextView.setVisibility(View.VISIBLE);
        Snackbar.make(view.findViewById(R.id.recipe_parent), getString(R.string.turn_on_internet), Snackbar.LENGTH_LONG).show();
    }
    public void hideNoInternetError(){
        recyclerView.setVisibility(View.VISIBLE);
        errorImageView.setVisibility(View.INVISIBLE);
        errorTextView.setVisibility(View.INVISIBLE);
    }

    public void showLoadingIndicators(){
        loadingTextView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoadingIndicators(){
        loadingTextView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
    }


    public void setRecipeFragmentCallbacks(RecipeFragmentCallbacks recipeFragmentCallbacks) {
        this.recipeFragmentCallbacks = recipeFragmentCallbacks;
    }

    @Override
    public void onRecipeItemClicked(int position) {
        recipeFragmentCallbacks.onRecipeItemClicked(recipesArray[position]);
    }

    @Override
    public void onStepsButtonClicked(int position) {
        recipeFragmentCallbacks.onStepsButtonClicked(recipesArray[position]);
    }

    @Override
    public void onIngredientsButtonClicked(int position) {
        recipeFragmentCallbacks.onIngredientsButtonClicked(recipesArray[position]);
    }

    private class JsonDataReceiver extends BroadcastReceiver {
            @Override
            public void onReceive(Context context, Intent intent) {
                recipesArray = JsonHelper.parseJson(intent.getStringExtra(RecipeService.RECIPE_PAYLOAD));
                recipeAdapter.setRecipesArray(recipesArray);
                hideLoadingIndicators();
                recipeAdapter.notifyDataSetChanged();
            }
        }

}
