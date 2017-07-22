package com.kryptkode.cyberman.recipe.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.kryptkode.cyberman.recipe.model.Ingredients;
import com.kryptkode.cyberman.recipe.model.Recipes;
import com.kryptkode.cyberman.recipe.model.Steps;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Cyberman on 7/17/2017.
 */

public class JsonHelper {
    private static   Recipes [] recipes;
    private static  Ingredients [] ingredients;
    private static Steps [] steps;


    private static String readFileFromAssets(Context context, String filename) {

        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(
                    context.getAssets().open(filename)));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            return builder.toString();
        } catch (FileNotFoundException e) {
            Log.e("FileHelper", "File not found: " + e.getMessage());
        } catch (Exception e) {
            Log.e("FileHelper", e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    Log.e("FileHelper", e.getMessage());
                }
            }
        }

        return null;
    }

    public static Recipes [] parseJson(String filename, Context context){
        Gson gson = new Gson();
        String  jsonText = readFileFromAssets(context, filename);
        recipes = gson.fromJson(jsonText, Recipes[].class);
        return recipes;
    }
    public static Recipes [] parseJson(String data){
        Gson gson = new Gson();
        Log.i("parseJson", "parseJson: " + data);
        recipes = gson.fromJson(data, Recipes[].class);
        return recipes;
    }
}
