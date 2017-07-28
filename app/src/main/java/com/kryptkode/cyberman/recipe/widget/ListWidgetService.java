package com.kryptkode.cyberman.recipe.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pGroup;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.kryptkode.cyberman.recipe.R;
import com.kryptkode.cyberman.recipe.data.RecipeContract;

import java.util.Locale;

/**
 * Created by Cyberman on 7/24/2017.
 */

public class ListWidgetService extends RemoteViewsService {

    public static final String TAG = ListWidgetService.class.getSimpleName();
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    public class ListWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
        private Context context;
        private Cursor cursor;
        private int recipeId = 1;

        public ListWidgetRemoteViewsFactory(Context context, Intent intent) {
            this.context = context;
            if (intent.hasExtra(WidgetProvider.RECIPE_ID)){

                this.recipeId = intent.getIntExtra(WidgetProvider.RECIPE_ID, 1);
            }

        }

        @Override
        public void onCreate() {
            Uri ingredientUri = RecipeContract.Ingredients.INGREDIENTS_CONTENT_URI;
            cursor = context.getContentResolver().query(ingredientUri, null,
                    RecipeContract.Ingredients.COLUMN_RECIPE_ID + "=?", new String[]{String.valueOf(recipeId)}, null);

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
          if (cursor != null){
              return cursor.getCount();
          }else {
              return  0;
          }
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (cursor == null || cursor.getCount() == 0){
                Log.i(TAG, "getViewAt: null " );
                return null;
            }
            cursor.moveToPosition(position);

            Log.i(TAG, "getViewAt: " + cursor.getCount());
            String ingredientName = cursor.getString(cursor
                    .getColumnIndex(RecipeContract.Ingredients.COLUMN_INGREDIENT));
            String quantity = cursor.getString(cursor
                    .getColumnIndex(RecipeContract.Ingredients.COLUMN_INGREDIENT_QUANTITY));
            String measure = cursor.getString(cursor
                    .getColumnIndex(RecipeContract.Ingredients.COLUMN_INGREDIENT_MEASURE));

            String firstLetter = ingredientName.substring(0, 1).toUpperCase(Locale.US);
            ingredientName = firstLetter + ingredientName.substring(1, ingredientName.length());

            String ingredientText = ingredientName + " -" + quantity + measure;

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_item);

            views.setTextViewText(R.id.widget_ingredient_number, String.valueOf(position + 1));
            views.setTextViewText(R.id.widget_ingredient, ingredientText);

            final Intent fillInIntent = new Intent();
            final Bundle extras = new Bundle();
            extras.putInt(TAG, recipeId);
            fillInIntent.putExtras(extras);
            views.setOnClickFillInIntent(R.id.widget_item_container, fillInIntent);

            return  views;


        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1; //treat ll ingredients as the same
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
