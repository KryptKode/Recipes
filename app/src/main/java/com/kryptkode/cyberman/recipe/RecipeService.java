package com.kryptkode.cyberman.recipe;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.kryptkode.cyberman.recipe.utils.NetworkHelper;

/**
 * Created by Cyberman on 7/20/2017.
 */

public class RecipeService extends IntentService {

    public static final String ACTION_LOAD_JSON = "com.kryptkode.cyberman.recipe.action.get_json";
    public static final String ACTION_RETURN_JSON_TO_ACTIVITY = "com.kryptkode.cyberman.recipe.action.return_json";
    public static final String RECIPE_PAYLOAD = "RecipePayload";

    private static final String URL = "http://go.udacity.com/android-baking-app-json";
    public static final String TAG = RecipeService.class.getSimpleName();
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
        Log.i(TAG, "handleActionGetJson: " + data);
        if (data != null){
            sendData(data);
        }
    }

    private void sendData(String data) {
        Intent intent = new Intent(ACTION_RETURN_JSON_TO_ACTIVITY );
        intent.putExtra(RECIPE_PAYLOAD, data);

        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager
                .getInstance(getApplicationContext());
        localBroadcastManager.sendBroadcast(intent);
    }

    public static void startGetJsonService(Context context){
            Intent intent = new Intent(context, RecipeService.class);
            intent.setAction(ACTION_LOAD_JSON);
            context.startService(intent);
        }
}
