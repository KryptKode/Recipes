package com.kryptkode.cyberman.recipe.utils;

import android.content.ContentValues;
import android.content.Context;

import com.kryptkode.cyberman.recipe.data.RecipeContract;
import com.kryptkode.cyberman.recipe.model.Ingredients;
import com.kryptkode.cyberman.recipe.model.Recipes;
import com.kryptkode.cyberman.recipe.model.Steps;

/**
 * Created by Cyberman on 7/23/2017.
 */

public class RecipeServiceHelper {

    public static void loadRecipesTable(Context context, Recipes recipes){
        ContentValues contentValues = new ContentValues();
        contentValues.put(RecipeContract.Recipes._ID, recipes.getId());
        contentValues.put(RecipeContract.Recipes.COLUMN_RECIPE_NAME, recipes.getName());
        contentValues.put(RecipeContract.Recipes.COLUMN_SERVING, recipes.getServings());
        contentValues.put(RecipeContract.Recipes.COLUMN_IMAGE, recipes.getImage());

        context.getContentResolver().insert(RecipeContract.Recipes.RECIPE_CONTENT_URI, contentValues);
    }

    public static void loadIngredientsTable(Context context, Ingredients ingredient, int id, int recipeId){
        ContentValues contentValues = new ContentValues();
        contentValues.put(RecipeContract.Ingredients._ID, id);
        contentValues.put(RecipeContract.Ingredients.COLUMN_INGREDIENT, ingredient.getIngredient());
        contentValues.put(RecipeContract.Ingredients.COLUMN_INGREDIENT_MEASURE, ingredient.getMeasure());
        contentValues.put(RecipeContract.Ingredients.COLUMN_INGREDIENT_QUANTITY, ingredient.getQuantity());
        contentValues.put(RecipeContract.Ingredients.COLUMN_RECIPE_ID, recipeId);
        context.getContentResolver().insert(RecipeContract.Ingredients.INGREDIENTS_CONTENT_URI, contentValues);
    }

    public static void loadStepsTable(Context context, Steps steps, int id, int recipeId){
        ContentValues contentValues = new ContentValues();
        contentValues.put(RecipeContract.Steps._ID, id);
        contentValues.put(RecipeContract.Steps.COLUMN_STEPS_DESCRIPTION, steps.getDescription());
        contentValues.put(RecipeContract.Steps.COLUMN_STEPS_SHORT_DESCRIPTION, steps.getShortDescription());
        contentValues.put(RecipeContract.Steps.COLUMN_STEPS_THUMBNAIL_URL, steps.getThumbnailUrl());
        contentValues.put(RecipeContract.Steps.COLUMN_STEPS_VIDEO_URL, steps.getVideoUrl());
        contentValues.put(RecipeContract.Steps.COLUMN_RECIPE_ID, recipeId);
        context.getContentResolver().insert(RecipeContract.Steps.STEPS_CONTENT_URI, contentValues);
    }
}
