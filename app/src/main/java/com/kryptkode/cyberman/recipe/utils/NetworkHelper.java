package com.kryptkode.cyberman.recipe.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkHelper {

    private static final String TAG = "NetworkHelper";

    //method to return JSON data from the URL string passed as parameter
    public static String getDataFromWeb(String url) {

        Response response;
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            response = client.newCall(request).execute();
            if(response.isSuccessful()){

                return response.body().string(); //returns the JSON data
            }
        } catch (IOException | NullPointerException e) {
            Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null; //if there is an error, return null
    }


    public static boolean isOnline(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


}