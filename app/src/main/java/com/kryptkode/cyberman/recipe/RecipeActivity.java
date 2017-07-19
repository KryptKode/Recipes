package com.kryptkode.cyberman.recipe;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kryptkode.cyberman.recipe.model.Ingredients;
import com.kryptkode.cyberman.recipe.model.Recipes;
import com.kryptkode.cyberman.recipe.model.Steps;
import com.kryptkode.cyberman.recipe.ui.ContentFragment;
import com.kryptkode.cyberman.recipe.ui.RecipeFragment;
import com.kryptkode.cyberman.recipe.ui.StepsFragment;
import com.kryptkode.cyberman.recipe.utils.JsonHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeActivity extends AppCompatActivity implements RecipeFragment.RecipeFragmentCallbacks, ContentFragment.ContentFragmentCallbacks{
    public static final String DETERMINANT = "keys";
    private RecipeFragment recipeFragment;
    private ContentFragment contentFragment;
    private Recipes[] recipesArray;
    private Bundle bundle;
    private String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recipesArray = JsonHelper.parseJson("baking.json", this);
        Recipes recipe = recipesArray[0];
        Log.i("GSON", "onCreate: " + recipe);

        //for phone device
        recipeFragment = new RecipeFragment();
        recipeFragment.setRecipesArray(recipesArray);
        recipeFragment.setRecipeFragmentCallbacks(this);
        getSupportFragmentManager().beginTransaction().
                add(R.id.main_fragment_container, recipeFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRecipeItemClicked(int position) {
        launchContent(position, 100);
    }

    @Override
    public void onStepsButtonClicked(int position) {
        launchContent(position, 0);
    }

    @Override
    public void onIngredientsButtonClicked(int position) {
        launchContent(position, 1);
    }

    @Override
    public void onSetRecipeActionBarTitle() {
        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle(getString(R.string.recipes));
        }
    }

    @Override
    public void onPlayVideoButtonClicked() {
        Toast.makeText(this, "Video", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSetContentActionBarTitle() {
        if (getSupportActionBar() != null && recipeName != null) {

            getSupportActionBar().setTitle(recipeName);
        }
    }

    private void launchContent(int position, int whichTab) {
        contentFragment = new ContentFragment();
        bundle = new Bundle();
        bundle.putParcelableArray(Steps.KEY, recipesArray[position].getSteps());
        bundle.putParcelableArray(Ingredients.KEY, recipesArray[position].getIngredients());
        recipeName = recipesArray[position].getRecipeName();
        if (whichTab == 0 || whichTab == 1) {
            bundle.putInt(DETERMINANT, whichTab);
        }
        contentFragment.setCallback(this);
        contentFragment.setArguments(bundle);

        //on a phone device
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, contentFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
