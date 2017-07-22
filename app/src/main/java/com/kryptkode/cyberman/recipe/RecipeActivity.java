package com.kryptkode.cyberman.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.kryptkode.cyberman.recipe.model.Ingredients;
import com.kryptkode.cyberman.recipe.model.Recipes;
import com.kryptkode.cyberman.recipe.model.Steps;
import com.kryptkode.cyberman.recipe.ui.ContentFragment;
import com.kryptkode.cyberman.recipe.ui.RecipeFragment;

public class RecipeActivity extends AppCompatActivity implements RecipeFragment.RecipeFragmentCallbacks, ContentFragment.ContentFragmentCallbacks{
    public static final String DETERMINANT = "keys";
    private static final String SAVE = "save";
    private int currentPosition;
    private boolean phoneDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        runGetJsonService();

        //check for device type
        RecipeFragment recipeFragment;
        if (savedInstanceState == null && findViewById(R.id.fragment_container) == null){
            phoneDevice = true;

            recipeFragment = new RecipeFragment();
            recipeFragment.setRecipeFragmentCallbacks(this);
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.main_fragment_container, recipeFragment).commit();
        }
        else{
            recipeFragment = (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.recipe_fragment);
            recipeFragment.setRecipeFragmentCallbacks(this);
        }



    }

    private void runGetJsonService() {
        RecipeService.startGetJsonService(this);
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
    public void onPlayVideoButtonClicked(String videoUrl) {
        startVideoPlayerActivity(videoUrl);
    }

    private void startVideoPlayerActivity(String videoUrl) {
        Intent intent = new Intent(this, RecipeVideoPlayerActivity.class);
        intent.putExtra(RecipeVideoPlayerActivity.VIDEO_URL, videoUrl);
        startActivity(intent);
    }


    private void launchContent(Recipes recipe, int whichTab) {
        setSupportActionBar(null);

        if (phoneDevice){
            //on a phone device
           displayRecipe(recipe, whichTab, R.id.main_fragment_container);
        }else{
            displayRecipe(recipe, whichTab, R.id.fragment_container);
        }

    }

    private void displayRecipe(Recipes recipe, int whichTab, int container){
        ContentFragment contentFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Recipes.KEY, recipe.getRecipeName());
        bundle.putParcelableArray(Steps.KEY, recipe.getSteps());
        bundle.putParcelableArray(Ingredients.KEY, recipe.getIngredients());
        if (whichTab == 0 || whichTab == 1) {
            bundle.putInt(DETERMINANT, whichTab);
        }
        contentFragment.setCallback(this);
        contentFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(container, contentFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
