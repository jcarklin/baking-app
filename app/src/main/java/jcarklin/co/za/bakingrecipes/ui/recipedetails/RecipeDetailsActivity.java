package jcarklin.co.za.bakingrecipes.ui.recipedetails;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;

public class RecipeDetailsActivity extends AppCompatActivity {

    private RecipeDetailsViewModel recipeDetailsViewModel;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        actionBar = this.getSupportActionBar();

        recipeDetailsViewModel = ViewModelProviders.of(this).get(RecipeDetailsViewModel.class);
        recipeDetailsViewModel.getSelectedRecipe().observe(this, new Observer<RecipeComplete>() {
            @Override
            public void onChanged(@Nullable RecipeComplete recipe) {
                if (actionBar != null) {
                    actionBar.setTitle(recipe.getName());
                    actionBar.setSubtitle(getString(R.string.number_of_servings)+" "+recipe.getServings());
                }
            }
        });
        //ToDo
//        recipeCardsViewModel.getStatus().observe(this, new Observer<FetchStatus>() {
//            @Override
//            public void onChanged(@Nullable FetchStatus fetchStatus) {
//                if (fetchStatus==null || fetchStatus.getStatus().equals(FetchStatus.Status.LOADING)) {
//                    showProgressBar();
//                } else if (fetchStatus.getStatus().equals(FetchStatus.Status.CRITICAL_ERROR)) {
//                    showError(getString(fetchStatus.getStatusMessage()));
//                } else {
//                    showRecipes();
//                }
//            }
//        });



//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.recipe_heading, RecipeDetailsHeadingFragment.newInstance())
//                    .replace(R.id.recipe_ingredients, RecipeDetailsIngredientsFragment.newInstance())
//                    .commitNow();
//        }
    }
}
