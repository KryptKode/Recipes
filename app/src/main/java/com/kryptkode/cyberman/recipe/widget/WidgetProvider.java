package com.kryptkode.cyberman.recipe.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.kryptkode.cyberman.recipe.R;
import com.kryptkode.cyberman.recipe.RecipeActivity;
import com.kryptkode.cyberman.recipe.ui.ContentFragment;
import com.kryptkode.cyberman.recipe.ui.RecipeFragment;

/**
 * Implementation of App Widget functionality.
 */
public class WidgetProvider extends AppWidgetProvider {
    public static final String TAG = WidgetProvider.class.getSimpleName();
    public static final String EXTRA_ITEM = "com.kryptkode.cyberman.recipe.widget.EXTRA_ITEM";
    public static final String INGREDIENT_ACTION = "com.kryptkode.cyberman.recipe.widget.action.INGREDIENT_ACTION";
    public static final String RECIPE_ID = "com.kryptkode.cyberman.recipe.widget.RECIPE_ID";
    private static int recipeId;
    private static String recipeName;


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, ListWidgetService.class);
        intent.putExtra(RECIPE_ID, recipeId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        //set the remote views adapter
        views.setRemoteAdapter(R.id.widget_list_view, intent);

        Log.i(TAG, "updateAppWidget: ");

        //set the title text view to reflect the name of the ingredient
        views.setTextViewText(R.id.widget_title, context.getString(R.string.ingredients_widget) +" " +  recipeName);

       //intent to launch the activity
        Intent intent1 = new Intent(context, RecipeActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);
        //TODO Check why the widget does not load when I set the pending intent
       //views.setOnClickPendingIntent(R.id.widget_list_view, pendingIntent);

        //set the remote views empty view
        views.setEmptyView(R.id.widget_list_view, R.id.widget_empty_view);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.shared_prefs_name), Context.MODE_PRIVATE);
        recipeId = preferences.getInt(ContentFragment.LAST_VIEWED, 1);
        recipeName = preferences.getString(ContentFragment.LAST_VIEWED_NAME, "");
        Log.i(TAG, "onUpdate: ");
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Log.i(TAG, "onEnabled: ");
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        Log.i(TAG, "onDisabled: ");
    }
}

