package jcarklin.co.za.bakingrecipes.repository;

import android.app.Application;
import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class BakingAppIntentService extends IntentService {

    public static final String ACTION_SETUP_RECIPES = "setupRecipes";

    public BakingAppIntentService() {
        super(BakingAppIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        if (action.equals(ACTION_SETUP_RECIPES)) {
            BakingAppRepository.getInstance((Application) getApplicationContext()).setupRecipes();
        }
    }
}
