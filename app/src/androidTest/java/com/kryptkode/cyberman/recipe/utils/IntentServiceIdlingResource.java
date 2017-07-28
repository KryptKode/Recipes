package com.kryptkode.cyberman.recipe.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.support.test.espresso.IdlingResource;

import com.kryptkode.cyberman.recipe.RecipeService;

public class IntentServiceIdlingResource implements IdlingResource {
  private final Context context;
  private ResourceCallback resourceCallback;

  public IntentServiceIdlingResource(Context context) {
    this.context = context;
  }

  @Override
  public String getName() {
    return IntentServiceIdlingResource.class.getName();
  }

  @Override
  public boolean isIdleNow() {
    boolean idle = !isIntentServiceRunning();
    if (idle && resourceCallback != null) {
      resourceCallback.onTransitionToIdle();
    }
    return idle;
  }

  @Override
  public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
    this.resourceCallback = resourceCallback;
  }

  private boolean isIntentServiceRunning() {
    ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    for (ActivityManager.RunningServiceInfo info : manager.getRunningServices(Integer.MAX_VALUE)) {
      if (RecipeService.class.getName().equals(info.service.getClassName())) {
        return true;
      }
    }
    return false;
  }
}