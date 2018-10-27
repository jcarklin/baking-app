package jcarklin.co.za.bakingrecipes.ui.recipecards;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.databinding.ActivityMainBinding;
import jcarklin.co.za.bakingrecipes.repository.BakingAppIntentService;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;

public class MainActivity extends AppCompatActivity implements RecipeCardsAdapter.RecipeCardsOnClickHandler {

    private RecipeCardsAdapter recipeCardsAdapter = null;
    RecyclerView rvRecipes;
    private ActivityMainBinding binding;
    private RecipeCardsViewModel recipeCardsViewModel;
//    ProgressBar pbLoadMovies;
//    TextView errorMessage;
//    ImageView errorIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        rvRecipes = binding.rvRecipes;
        recipeCardsAdapter = new RecipeCardsAdapter(this);
        rvRecipes.setAdapter(recipeCardsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvRecipes.setLayoutManager(layoutManager);
        rvRecipes.setHasFixedSize(true);
        setupRecipes();
        setupViewModel();
    }

    public void setupRecipes() {
        Intent serviceIt = new Intent(this, BakingAppIntentService.class);
        serviceIt.setAction(BakingAppIntentService.ACTION_SETUP_RECIPES);
        startService(serviceIt);
    }

    private void setupViewModel() {
        recipeCardsViewModel = ViewModelProviders.of(this).get(RecipeCardsViewModel.class);
        recipeCardsViewModel.getRecipes().observe(this, new Observer<List<RecipeComplete>>() {
            @Override
            public void onChanged(@Nullable List<RecipeComplete> recipes) {
                displayRecipeCards(recipes);
            }
        });
    }

    private void displayRecipeCards(List<RecipeComplete> recipes) {
        recipeCardsAdapter.setRecipes(recipes);
    }

    @Override
    public void onClick(RecipeComplete selectedRecipe) {
        Toast toast = Toast.makeText(this, "You clicked " + selectedRecipe.getName(), Toast.LENGTH_SHORT);
        toast.show();
    }
}