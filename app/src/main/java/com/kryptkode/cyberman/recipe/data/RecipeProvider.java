package com.kryptkode.cyberman.recipe.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Cyberman on 7/23/2017.
 */

public class RecipeProvider extends ContentProvider {
    public static final int RECIPES = 100;
    public static final int ONE_RECIPE = 101;
    public static final int INGREDIENTS = 200;
    public static final int ONE_INGREDIENT = 201;
    public static final int STEPS = 300;
    public static final int ONE_STEP = 301;

    private static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        //for all the recipes
        sUriMatcher.addURI(RecipeContract.AUTHORITY,
                RecipeContract.Recipes.RECIPE_TABLE_NAME, RECIPES);

        //for one recipe
        sUriMatcher.addURI(RecipeContract.AUTHORITY,
                RecipeContract.Recipes.RECIPE_TABLE_NAME + "/#", ONE_RECIPE);

        //for all the ingredients
        sUriMatcher.addURI(RecipeContract.AUTHORITY,
                RecipeContract.Ingredients.INGREDIENTS_TABLE_NAME, INGREDIENTS);

        //for one ingredient
        sUriMatcher.addURI(RecipeContract.AUTHORITY,
                RecipeContract.Ingredients.INGREDIENTS_TABLE_NAME + "/#", ONE_INGREDIENT);

        //for all the steps
        sUriMatcher.addURI(RecipeContract.AUTHORITY,
                RecipeContract.Steps.STEPS_TABLE_NAME, STEPS);

        //for one step
        sUriMatcher.addURI(RecipeContract.AUTHORITY,
                RecipeContract.Steps.STEPS_TABLE_NAME + "/#", ONE_STEP);
    }


    private RecipeDbHelper mRecipeDbHelper;

    public SQLiteDatabase getReadableDatabase(){ return mRecipeDbHelper.getReadableDatabase();
    }

    public SQLiteDatabase getWritableDatabase(){
        return mRecipeDbHelper.getWritableDatabase();
    }


    @Override
    public boolean onCreate() {
        mRecipeDbHelper = new RecipeDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        //query builder for ingredients table
        SQLiteQueryBuilder ingredientsQueryBuilder = new SQLiteQueryBuilder();

        //query builder for steps table
        SQLiteQueryBuilder stepsQueryBuilder = new SQLiteQueryBuilder();

        //get reference to the readable database
        SQLiteDatabase database = mRecipeDbHelper.getReadableDatabase();


        switch (sUriMatcher.match(uri)) {
            //recipes table
            case ONE_RECIPE:
                queryBuilder.appendWhere(RecipeContract.Recipes._ID + "=" + uri.getLastPathSegment());
            case RECIPES:
                queryBuilder.setTables(RecipeContract.Recipes.RECIPE_TABLE_NAME);
                break;

            //ingredients table
            case ONE_INGREDIENT:
                queryBuilder.appendWhere(RecipeContract.Ingredients._ID + "=" + uri.getLastPathSegment());
            case INGREDIENTS:
                queryBuilder.setTables(RecipeContract.Ingredients.INGREDIENTS_TABLE_NAME);
                break;

            //steps table
            case ONE_STEP:
                queryBuilder.appendWhere(RecipeContract.Steps._ID + "=" + uri.getLastPathSegment());
            case STEPS:
            queryBuilder.setTables(RecipeContract.Steps.STEPS_TABLE_NAME);
            break;
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri.toString());



        }
        Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (values == null) throw new IllegalArgumentException("The content values should not be null");

        SQLiteDatabase database = mRecipeDbHelper.getWritableDatabase();
        long id;
        switch (sUriMatcher.match(uri)){
            case RECIPES:
                String recipeName = values.getAsString(RecipeContract.Recipes.COLUMN_RECIPE_NAME);
                if (recipeName.isEmpty()) {
                    throw new IllegalArgumentException("Recipe Should Have a name");
                }
                id = database.insert(RecipeContract.Recipes.RECIPE_TABLE_NAME, null, values);
                return RecipeContract.Recipes.buildRecipeUri(id);
            case INGREDIENTS:
                id = database.insert(RecipeContract.Ingredients.INGREDIENTS_TABLE_NAME, null, values);
                return RecipeContract.Ingredients.buildIngredientUri(id);
            case STEPS:
                id = database.insert(RecipeContract.Steps.STEPS_TABLE_NAME, null, values);
                return RecipeContract.Steps.buildStepUri(id);
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri.toString());
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        //there is no need to delete entries
        //users cannot manually delete entries
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        //no update to the database for now
        return 0;
    }


}
