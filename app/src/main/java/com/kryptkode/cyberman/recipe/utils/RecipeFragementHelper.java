package com.kryptkode.cyberman.recipe.utils;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.kryptkode.cyberman.recipe.data.RecipeContract;
import com.kryptkode.cyberman.recipe.model.Ingredients;
import com.kryptkode.cyberman.recipe.model.Recipes;
import com.kryptkode.cyberman.recipe.model.Steps;
import com.kryptkode.cyberman.recipe.ui.RecipeFragment;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Cyberman on 7/23/2017.
 */

public class RecipeFragementHelper {
    public static final String TAG = RecipeFragment.TAG;

    public static Recipes[] readCursorData(Cursor recipeCursor, Cursor ingredientCursor, Cursor stepsCursor) {
        ArrayList<Recipes> recipesArrayList = new ArrayList<>();
        ArrayList<Ingredients> ingredientsArrayList = new ArrayList<>();
        ArrayList<Steps> stepsArrayList = new ArrayList<>();

        Log.i(TAG, "readCursorData: " + "Start");
        for (int i = 0; i< ingredientCursor.getCount(); i++){
            Log.i("CursorCount", "readCursorData: " + ingredientCursor.getCount());
            Ingredients ingredient = new Ingredients();
            ingredientCursor.moveToPosition(i);
            ingredient.setIngredient(ingredientCursor
                    .getString(ingredientCursor.getColumnIndex(RecipeContract.Ingredients.COLUMN_INGREDIENT)));
            ingredient.setMeasure((ingredientCursor
                    .getString(ingredientCursor.getColumnIndex(RecipeContract.Ingredients.COLUMN_INGREDIENT_MEASURE))));
            ingredient.setQuantity(ingredientCursor
                    .getDouble(ingredientCursor.getColumnIndex(RecipeContract.Ingredients.COLUMN_INGREDIENT_QUANTITY)));
            ingredient.setRecipeId(ingredientCursor
                    .getInt(ingredientCursor.getColumnIndex(RecipeContract.Ingredients.COLUMN_RECIPE_ID)));
            ingredientsArrayList.add(ingredient);
            Log.i(TAG, "readCursorData: " + "Ingredient" +  ingredient.getIngredient()+ "-->"  + ingredient.getRecipeId());
        }
        for (int i = 0; i < stepsCursor.getCount(); i++) {
            Log.i("CursorCount", "readCursorData: " + stepsCursor.getCount());
            Steps step = new Steps();
            stepsCursor.moveToPosition(i);
            step.setDescription(stepsCursor
                    .getString(stepsCursor.getColumnIndex(RecipeContract.Steps.COLUMN_STEPS_DESCRIPTION)));
            step.setShortDescription(stepsCursor
                    .getString(stepsCursor.getColumnIndex(RecipeContract.Steps.COLUMN_STEPS_SHORT_DESCRIPTION)));
            step.setThumbnailUrl(stepsCursor
                    .getString(stepsCursor.getColumnIndex(RecipeContract.Steps.COLUMN_STEPS_THUMBNAIL_URL)));
            step.setVideoUrl(stepsCursor.
                    getString(stepsCursor.getColumnIndex(RecipeContract.Steps.COLUMN_STEPS_VIDEO_URL)));
            step.setRecipeId(stepsCursor.getInt(stepsCursor.getColumnIndex(RecipeContract.Steps.COLUMN_RECIPE_ID)));
            stepsArrayList.add(step);
            Log.i(TAG, "readCursorData: " + "Step" + step.getDescription()+ "-->" + step.getRecipeId());
        }


        for (int i = 0; i < recipeCursor.getCount(); i++) {
            Recipes recipe = new Recipes();
            recipeCursor.moveToPosition(i);
            recipe.setId(recipeCursor.getInt(recipeCursor.getColumnIndex(RecipeContract.Recipes._ID)));
            recipe.setImage(recipeCursor.getString(recipeCursor.getColumnIndex(RecipeContract.Recipes.COLUMN_IMAGE)));
            recipe.setName(recipeCursor.getString(recipeCursor.getColumnIndex(RecipeContract.Recipes.COLUMN_RECIPE_NAME)));
            recipe.setServings(recipeCursor.getInt(recipeCursor.getColumnIndex(RecipeContract.Recipes.COLUMN_SERVING)));
            recipe.setIngredients(getSpecificIngredient(ingredientsArrayList, recipe.getId()));
            recipe.setSteps(getSpecificStep(stepsArrayList, recipe.getId()));
            recipesArrayList.add(recipe);

            Log.i(TAG, "readCursorData: " + "Reciep I-S" + Arrays.toString(recipe.getIngredients()) +"--" + Arrays.toString(recipe.getSteps()));
        }
        return getRecipes(recipesArrayList);
    }

    private static Recipes[] getRecipes(ArrayList<Recipes> recipesArrayList) {
        Recipes [] recipes = new Recipes[recipesArrayList.size()];
        int i = 0;
        for (Recipes recipe: recipesArrayList) {
            recipes[i] = recipe;
            i++;
        }
        return  recipes;
    }


    private static Ingredients [] getSpecificIngredient(ArrayList<Ingredients> ingredients, int recipeId){
        ArrayList<Ingredients> arrayList = new ArrayList<>();
        int i = 0;
        for (Ingredients ingredient:ingredients) {
            if(ingredient.getRecipeId() == recipeId){
                arrayList.add(ingredient);
            }
        }

        Ingredients [ ] ingredientses = new Ingredients[arrayList.size()];
        for (Ingredients ingre: arrayList ) {
            ingredientses[i] = ingre;
            i++;
        }


        return  ingredientses;
    }

    private static Steps [] getSpecificStep(ArrayList<Steps> steps, int recipeId){
        ArrayList<Steps> arrayList = new ArrayList<>();
        int i = 0;
        for (Steps step:steps) {
            if(step.getRecipeId() == recipeId){
               arrayList.add(step);
            }
        }

        Steps [] stepses = new Steps[arrayList.size()];
        for (Steps step: arrayList) {
            stepses[i] = step;
            i++;
        }
        return  stepses;
    }


    public static class RecipeLoader extends AsyncTaskLoader<Cursor> {
        private static final String NAME = RecipeLoader.class.getSimpleName();
        private Cursor cursor;
        private Context context;

        public RecipeLoader(Context context) {
            super(context);
            this.context = context;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            Log.i(TAG, "onStartLoading: " + NAME);
            if (cursor != null) {
                deliverResult(cursor);
            } else {

                forceLoad();
            }
        }

        @Override
        public Cursor loadInBackground() {
            try {
                cursor = context.getContentResolver().query(
                        RecipeContract.Recipes.RECIPE_CONTENT_URI, null, null, null, null);
                Log.i(TAG, "loadInBackground: " + NAME);
                return cursor;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        public void deliverResult(Cursor data) {
            cursor = data;
            Log.i(TAG, "deliverResult: " + NAME);
            super.deliverResult(data);
        }
    }

    public static class IngredientLoader extends AsyncTaskLoader<Cursor> {
        public static final String NAME = IngredientLoader.class.getSimpleName();
        private Cursor cursor;

        private Context context;

        public IngredientLoader(Context context) {
            super(context);
            this.context = context;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            Log.i(TAG, "onStartLoading: " + NAME);
            if (cursor != null) {
                deliverResult(cursor);
            } else {

                forceLoad();
            }
        }

        @Override
        public Cursor loadInBackground() {
            try {
                cursor = context.getContentResolver().query(
                        RecipeContract.Ingredients.INGREDIENTS_CONTENT_URI, null, null, null, null);
                Log.i(TAG, "loadInBackground: " + NAME);
                return cursor;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }
        @Override
        public void deliverResult(Cursor data) {
            cursor = data;
            Log.i(TAG, "deliverResult: " + NAME);
            super.deliverResult(data);
        }
    }

    public static class StepsLoader extends AsyncTaskLoader<Cursor> {
        public static final String NAME = StepsLoader.class.getSimpleName();
        private Cursor cursor;
        private Context context;

        public StepsLoader(Context context) {
            super(context);
            this.context = context;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            Log.i(TAG, "onStartLoading: " + NAME);
            if (cursor != null) {
                deliverResult(cursor);
            } else {

                forceLoad();
            }
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor;
            try {
                Log.i(TAG, "loadInBackground: " + NAME);
                cursor = context.getContentResolver().query(
                        RecipeContract.Steps.STEPS_CONTENT_URI, null, null, null, null);
                return cursor;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }
        @Override
        public void deliverResult(Cursor data) {
            Log.i(TAG, "deliverResult: " + NAME);
            cursor = data;
            super.deliverResult(data);
        }
    }
}
