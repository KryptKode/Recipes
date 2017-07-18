package com.kryptkode.cyberman.recipe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.kryptkode.cyberman.recipe.model.Ingredients;
import com.kryptkode.cyberman.recipe.model.Recipes;
import com.kryptkode.cyberman.recipe.ui.RecipeFragment;
import com.kryptkode.cyberman.recipe.utils.JsonHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeActivity extends AppCompatActivity {
    private RecipeFragment recipeFragment;
    private Recipes [] recipesArray;

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
}
