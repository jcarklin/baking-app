package jcarklin.co.za.bakingrecipes.ui.recipecards;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import jcarklin.co.za.bakingrecipes.R;

public class MainActivity extends AppCompatActivity  {

    private RecipeCardsViewModel recipeCardsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recipeCardsViewModel = ViewModelProviders.of(this).get(RecipeCardsViewModel.class);

//        RecipeCardsFragment recipeCardsFragment = new RecipeCardsFragment();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().add(R.id.recipe_cards_fragment_container, recipeCardsFragment).commit();
    }

}