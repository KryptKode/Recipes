package com.kryptkode.cyberman.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.kryptkode.cyberman.recipe.data.RecipeContract;
import com.kryptkode.cyberman.recipe.model.Ingredients;
import com.kryptkode.cyberman.recipe.model.Recipes;
import com.kryptkode.cyberman.recipe.model.Steps;
import com.kryptkode.cyberman.recipe.ui.ContentFragment;
import com.kryptkode.cyberman.recipe.ui.RecipeFragment;
import com.kryptkode.cyberman.recipe.utils.NetworkHelper;

public class RecipeActivity extends AppCompatActivity implements RecipeFragment.RecipeFragmentCallbacks, ContentFragment.ContentFragmentCallbacks{
    public static final String DETERMINANT = "keys";
    private static final String SAVE = "save";
    private int currentPosition;
    private boolean phoneDevice;
    private FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //check for device type
        RecipeFragment recipeFragment;
        if (savedInstanceState == null && findViewById(R.id.fragment_container) == null){

            recipeFragment = new RecipeFragment();
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.main_fragment_container, recipeFragment).commit();
        }
        else{
            recipeFragment = (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.recipe_fragment);
        }



    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SAVE, currentPosition);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onRecipeItemClicked(Recipes recipe) {launchContent(recipe, 100);
    }

    @Override
    public void onStepsButtonClicked(Recipes recipe) {
        launchContent(recipe, 0);
    }

    @Override
    public void onIngredientsButtonClicked(Recipes recipe) {
        launchContent(recipe, 1);
    }



    @Override
    public void onPlayVideoButtonClicked(String videoUrl, String thumbnail) {
        //if there is no internet connectivity, show an error, else, start the activity
            if(!NetworkHelper.isOnline(this)){
                Snackbar.make(findViewById(R.id.parent), getString(R.string.turn_on_internet_to_play_video), Snackbar.LENGTH_LONG).show();
            }else{

                startVideoPlayerActivity(videoUrl, thumbnail);
            }

    }

    private void startVideoPlayerActivity(String videoUrl, String thumbnail) {
        Intent intent = new Intent(this, RecipeVideoPlayerActivity.class);
        intent.putExtra(RecipeVideoPlayerActivity.VIDEO_URL, videoUrl);
        intent.putExtra(RecipeVideoPlayerActivity.THUMBNAIL, thumbnail);
        startActivity(intent);
    }


    private void launchContent(Recipes recipe, int whichTab) {
        setSupportActionBar(null);

        if (findViewById(R.id.main_fragment_container) != null){
            //on a phone device
           displayRecipe(recipe, whichTab, R.id.main_fragment_container);
        }else{
            displayRecipe(recipe, whichTab, R.id.fragment_container);
        }

    }

    private void displayRecipe(Recipes recipe, int whichTab, int container){
        ContentFragment contentFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Recipes.KEY, recipe.getName());
        bundle.putInt(Recipes.ID, recipe.getId());
        bundle.putParcelableArray(Steps.KEY, recipe.getSteps());
        bundle.putParcelableArray(Ingredients.KEY, recipe.getIngredients());
        if (whichTab == 0 || whichTab == 1) {
            bundle.putInt(DETERMINANT, whichTab);
        }
        contentFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(container, contentFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
