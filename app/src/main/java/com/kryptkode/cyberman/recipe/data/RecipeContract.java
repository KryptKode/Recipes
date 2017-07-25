package com.kryptkode.cyberman.recipe.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Cyberman on 7/23/2017.
 */

public class RecipeContract {
    public static final String AUTHORITY = "com.kryptkode.cyberman.recipe.data";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);



    //Recipes table contract
    public static class Recipes implements BaseColumns{
        public static final String RECIPE_TABLE_NAME = "recipes";
        public static final Uri RECIPE_CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(RECIPE_TABLE_NAME).build();

        public static final String COLUMN_RECIPE_NAME = "name";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_SERVING = "servings";

        public static Uri buildRecipeUri(long id){
            return ContentUris.withAppendedId(RECIPE_CONTENT_URI, id);
        }


    }
    public static class Ingredients implements BaseColumns{
        public static final String INGREDIENTS_TABLE_NAME = "ingredients";
        public static final Uri INGREDIENTS_CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(INGREDIENTS_TABLE_NAME).build();

        public static final String COLUMN_INGREDIENT_QUANTITY = "quantity";
        public static final String COLUMN_INGREDIENT_MEASURE = "measure";
        public static final String COLUMN_INGREDIENT = "ingredient";
        public static final String COLUMN_RECIPE_ID = "recipe_id";

        public static Uri buildIngredientUri(long id){
            return ContentUris.withAppendedId(INGREDIENTS_CONTENT_URI, id);
        }
    }
    public static class Steps implements BaseColumns{
        public static final String STEPS_TABLE_NAME = "steps";

        public static final Uri STEPS_CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(STEPS_TABLE_NAME).build();

        public static final String COLUMN_STEPS_SHORT_DESCRIPTION = "short_description";
        public static final String COLUMN_STEPS_DESCRIPTION = "description";
        public static final String COLUMN_STEPS_VIDEO_URL = "video_url";
        public static final String COLUMN_STEPS_THUMBNAIL_URL = "thumbnail_url";
        public static final String COLUMN_RECIPE_ID = "recipe_id";

        public static Uri buildStepUri(long id){
            return ContentUris.withAppendedId(STEPS_CONTENT_URI, id);
        }

    }
}
