package com.kryptkode.cyberman.recipe;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.kryptkode.cyberman.recipe.data.RecipeProvider;
import com.kryptkode.cyberman.recipe.model.Ingredients;
import com.kryptkode.cyberman.recipe.model.Recipes;
import com.kryptkode.cyberman.recipe.model.Steps;
import com.kryptkode.cyberman.recipe.utils.JsonHelper;
import com.kryptkode.cyberman.recipe.utils.NetworkHelper;
import com.kryptkode.cyberman.recipe.utils.RecipeServiceHelper;

/**
 * Created by Cyberman on 7/20/2017.
 */

public class RecipeService extends IntentService {

    public static final String ACTION_LOAD_JSON = "com.kryptkode.cyberman.recipe.action.get_json";
    public static final String ACTION_READ_DATABASE = "com.kryptkode.cyberman.recipe.action.read_database";
    public static final String RECIPE_PAYLOAD = "RecipePayload";

    private static final String URL = "http://go.udacity.com/android-baking-app-json";
    public static final String TAG = RecipeService.class.getSimpleName();

    private Recipes[] recipeArray;

    public RecipeService() {
        super("RecipeService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_LOAD_JSON.equals(action)) {
                handleActionGetJson();
            }
        }
    }

    private void handleActionGetJson() {
        String data = NetworkHelper.getDataFromWeb(URL);
        Log.i(TAG, "handleActionGetJson: ");
        if (data != null) {

            recipeArray = JsonHelper.parseJson(data);
            Log.i(TAG, "handleActionGetJson: " + recipeArray.length);
            storeDataInDatabase();
        }
        else{
            sendData(false);
        }

    }

    private void storeDataInDatabase() {
        int stepId = 0;
        int ingredientId = 0;
        for (Recipes recipe : recipeArray) {
            int recipeId = recipe.getId();
            Steps [] steps = recipe.getSteps();
            Ingredients [] ingredients = recipe.getIngredients();

            RecipeServiceHelper.loadRecipesTable(getApplicationContext(), recipe);
            for (Steps step : steps) {
                RecipeServiceHelper.loadStepsTable(getApplicationContext(), step, stepId, recipeId);
                stepId++;
            }
            for (Ingredients ingredient: ingredients) {
                RecipeServiceHelper.loadIngredientsTable(getApplicationContext(), ingredient, ingredientId, recipeId);
                ingredientId++;
            }
            Log.i(TAG, "storeDataInDatabase: " + recipe.getName());
        }
        sendData(true);
    }

    private void sendData(boolean success) {
        Log.i(TAG, "sendData: " + success);
        Intent intent = new Intent(ACTION_READ_DATABASE);
        intent.putExtra(RECIPE_PAYLOAD, success);

        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager
                .getInstance(getApplicationContext());
        localBroadcastManager.sendBroadcast(intent);
    }

    public static void startGetJsonService(Context context) {
        Intent intent = new Intent(context, RecipeService.class);
        intent.setAction(ACTION_LOAD_JSON);
        context.startService(intent);
    }

}
