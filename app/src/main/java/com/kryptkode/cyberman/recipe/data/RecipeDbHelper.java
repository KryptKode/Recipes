package com.kryptkode.cyberman.recipe.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.kryptkode.cyberman.recipe.model.*;

import static com.kryptkode.cyberman.recipe.data.RecipeContract.Recipes;
import static com.kryptkode.cyberman.recipe.data.RecipeContract.Ingredients;
import static com.kryptkode.cyberman.recipe.data.RecipeContract.Steps;


/**
 * Created by Cyberman on 7/23/2017.
 */

public class RecipeDbHelper extends SQLiteOpenHelper {
    public static final String TAG = RecipeDbHelper.class.getSimpleName();
    public static final String DATABASE_NAME = "recipes_database.db";
    public static final int DATABASE_VERSION = 1;

    //constants for the SQL commands and syntax
    private static final String CREATE_TABLE = "CREATE TABLE ";
    private static final String COMMA_SEP = ", ";
    private static final String TEXT = " TEXT ";
    public static final String UNIQUE = " UNIQUE ";
    public static final String PRIMARY_KEY = " PRIMARY KEY ";
    public static final String NOT_NULL = " NOT NULL ";
    public static final String INTEGER = " INTEGER ";
    public static final String REAL = " REAL ";
    public static final String DEFAULT = " DEFAULT ";
    public static final String AUTOINCREMENT = " AUTOINCREMENT ";
    public static final String OPEN_PARENTHESIS = "(";
    public static final String CLOSE_PARENTHESIS = " );";

    public RecipeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECIPES_TABLE = CREATE_TABLE + Recipes.RECIPE_TABLE_NAME + OPEN_PARENTHESIS +
                Recipes._ID + INTEGER + PRIMARY_KEY  + COMMA_SEP +
                Recipes.COLUMN_RECIPE_NAME + TEXT + UNIQUE + NOT_NULL + COMMA_SEP +
                Recipes.COLUMN_IMAGE + TEXT + COMMA_SEP +
                Recipes.COLUMN_SERVING + INTEGER + CLOSE_PARENTHESIS;

        Log.i(TAG, "onCreate: -->" + CREATE_RECIPES_TABLE);

        String CREATE_INGREDIENTS_TABLE = CREATE_TABLE + Ingredients.INGREDIENTS_TABLE_NAME + OPEN_PARENTHESIS +
                Ingredients._ID +INTEGER + PRIMARY_KEY  + COMMA_SEP +
                Ingredients.COLUMN_INGREDIENT + TEXT + NOT_NULL + COMMA_SEP +
                Ingredients.COLUMN_INGREDIENT_MEASURE + TEXT + COMMA_SEP +
                Ingredients.COLUMN_INGREDIENT_QUANTITY + REAL + COMMA_SEP +
                Ingredients.COLUMN_RECIPE_ID + INTEGER + CLOSE_PARENTHESIS;

        Log.i(TAG, "onCreate: -->" + CREATE_INGREDIENTS_TABLE);

        String CREATE_STEPS_TABLE = CREATE_TABLE + Steps.STEPS_TABLE_NAME + OPEN_PARENTHESIS +
                Steps._ID + INTEGER + PRIMARY_KEY  +  COMMA_SEP +
                Steps.COLUMN_STEPS_DESCRIPTION + TEXT + COMMA_SEP +
                Steps.COLUMN_STEPS_SHORT_DESCRIPTION + TEXT + COMMA_SEP +
                Steps.COLUMN_STEPS_VIDEO_URL + TEXT + COMMA_SEP +
                Steps.COLUMN_STEPS_THUMBNAIL_URL + TEXT + COMMA_SEP +
                Steps.COLUMN_RECIPE_ID + INTEGER + CLOSE_PARENTHESIS;

        Log.i(TAG, "onCreate: -->" + CREATE_STEPS_TABLE);

        db.execSQL(CREATE_RECIPES_TABLE);
        db.execSQL(CREATE_INGREDIENTS_TABLE);
        db.execSQL(CREATE_STEPS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //for testing purposes only, no update yet
        db.execSQL("DROP TABLE IF EXISTS " + Recipes.RECIPE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Ingredients.INGREDIENTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Steps.STEPS_TABLE_NAME);
        onCreate(db);
    }


}
