package com.kryptkode.cyberman.recipe.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kryptkode.cyberman.recipe.R;
import com.kryptkode.cyberman.recipe.RecipeService;
import com.kryptkode.cyberman.recipe.adapters.RecipeAdapter;
import com.kryptkode.cyberman.recipe.model.Recipes;
import com.kryptkode.cyberman.recipe.utils.NetworkHelper;
import com.kryptkode.cyberman.recipe.utils.RecipeFragementHelper;

import java.util.Arrays;


/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeFragment extends Fragment implements RecipeAdapter.RecipeAdapterCallbacks, LoaderManager.LoaderCallbacks<Cursor>{
    public static final String TAG = RecipeFragment.class.getSimpleName();
    public static final int RECIPE_LOADER = 100;
    public static final int STEPS_LOADER =  200;
    public static final int INGREDIENT_LOADER = 300;
    public static final String FIRST_LOAD = "first_load";

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private Recipes [] recipesArray;
    private RecipeFragmentCallbacks recipeFragmentCallbacks;
    private JsonDataReceiver receiver;
    private ProgressBar progressBar;
    private TextView errorTextView;
    private TextView loadingTextView;
    private ImageView errorImageView;
    private View view;

    private Cursor recipeCursor;
    private Cursor stepsCursor;
    private Cursor ingredientCursor;

    private SharedPreferences preferences;

    public interface RecipeFragmentCallbacks{
        void onRecipeItemClicked(Recipes recipe);
        void onStepsButtonClicked(Recipes recipe);
        void onIngredientsButtonClicked(Recipes recipe);
    }

    public RecipeFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        recipeFragmentCallbacks = (RecipeFragmentCallbacks) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        recipeFragmentCallbacks = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        appCompatActivity.setSupportActionBar(toolbar);


        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean firstLoad = preferences.getBoolean(FIRST_LOAD, true);

        receiver = new JsonDataReceiver();
        progressBar = (ProgressBar) view.findViewById(R.id.loading_progress_bar);
        progressBar.setIndeterminate(true);

        loadingTextView = (TextView) view.findViewById(R.id.loading_text_view);
        errorTextView = (TextView) view.findViewById(R.id.error_text_view);
        errorImageView = (ImageView) view.findViewById(R.id.error_image_view);


        LocalBroadcastManager.getInstance(getContext())
                .registerReceiver(receiver, new IntentFilter(RecipeService.ACTION_READ_DATABASE));


        recyclerView = (RecyclerView) view.findViewById(R.id.main_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recipeAdapter = new RecipeAdapter(getContext(), recipesArray);
        recipeAdapter.setRecipeAdapterCallbacks(this);
        recyclerView.setAdapter(recipeAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.hasFixedSize();

        if (!NetworkHelper.isOnline(getContext()) && firstLoad){
            showNoInternetError();
        }
        else{

            hideNoInternetError();
            if (firstLoad){
                runGetJsonService();
                showLoadingIndicators();
            }
        }
        if (!firstLoad){
            showLoadingIndicators();
            initalizeLoaders();
        }


        return view;
    }
    private void runGetJsonService() {
        RecipeService.startGetJsonService(getContext());
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
        progressBar.setVisibility(View.VISIBLE);
        loadingTextView.setVisibility(View.VISIBLE);

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


/*    public void setRecipeFragmentCallbacks(RecipeFragmentCallbacks recipeFragmentCallbacks) {
        this.recipeFragmentCallbacks = recipeFragmentCallbacks;
    }*/

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
               if (intent.getBooleanExtra(RecipeService.RECIPE_PAYLOAD, false)){
                   Log.i(TAG, "onReceive: ");
                   initalizeLoaders();
                   SharedPreferences.Editor editor = preferences.edit();
                   editor.putBoolean(FIRST_LOAD, false);
                   editor.apply();
               }
               else{
                   Snackbar.make(view.findViewById(R.id.recipe_parent), getString(R.string.error), Snackbar.LENGTH_LONG).show();
               }
            }
        }

    private void initalizeLoaders() {

        if (getLoaderManager() == null){
            Log.i(TAG, "initalizeLoaders: Null");
            getLoaderManager().initLoader(RECIPE_LOADER, null, this);
            getLoaderManager().initLoader(INGREDIENT_LOADER, null, this);
            getLoaderManager().initLoader(STEPS_LOADER, null, this);
        }else{
            Log.i(TAG, "initalizeLoaders: Not null ");
            getLoaderManager().restartLoader(RECIPE_LOADER, null, this);
            getLoaderManager().restartLoader(INGREDIENT_LOADER, null, this);
            getLoaderManager().restartLoader(STEPS_LOADER, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
       switch(id){
           case RECIPE_LOADER:
               return  new RecipeFragementHelper.RecipeLoader(getContext());
           case INGREDIENT_LOADER:
               return new RecipeFragementHelper.IngredientLoader(getContext());
           case STEPS_LOADER:
               return new RecipeFragementHelper.StepsLoader(getContext());
           default:
               return null;
       }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()){
            case RECIPE_LOADER:
                Log.i(TAG, "onLoadFinished: " + "recipe");
               recipeCursor = data;
                break;
            case INGREDIENT_LOADER:
                Log.i(TAG, "onLoadFinished: " + "Ingredient");
                ingredientCursor = data;
                break;
            case STEPS_LOADER:
                Log.i(TAG, "onLoadFinished: " + "steps");
                stepsCursor = data;
                break;
        }

        if (recipeCursor != null && ingredientCursor != null && stepsCursor != null) {
            Log.i(TAG, "onLoadFinished: " + "Read data from Cursor");
            recipesArray =  RecipeFragementHelper.readCursorData(recipeCursor,ingredientCursor,stepsCursor);
            hideLoadingIndicators();
            Log.i(TAG, "onLoadFinished: " + Arrays.toString(recipesArray));
            recipeAdapter.setRecipes(recipesArray);
            recipeAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //blank
    }

}
