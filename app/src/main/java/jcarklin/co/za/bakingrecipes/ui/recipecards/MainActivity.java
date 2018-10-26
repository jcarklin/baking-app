package jcarklin.co.za.bakingrecipes.ui.recipecards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.repository.BakingAppIntentService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupRecipes();
    }

    public void setupRecipes() {
        Intent serviceIt = new Intent(this,BakingAppIntentService.class);
        serviceIt.setAction(BakingAppIntentService.ACTION_SETUP_RECIPES);
        startService(serviceIt);
    }
}
