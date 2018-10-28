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
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.databinding.ActivityMainBinding;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeCardsResponse;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;
import jcarklin.co.za.bakingrecipes.repository.model.Resource;

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        rvRecipes = binding.rvRecipes;
        recipeCardsAdapter = new RecipeCardsAdapter(this);
        rvRecipes.setAdapter(recipeCardsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvRecipes.setLayoutManager(layoutManager);
        rvRecipes.setHasFixedSize(true);
        setupViewModel();
    }

    private void setupViewModel() {
        recipeCardsViewModel = ViewModelProviders.of(this).get(RecipeCardsViewModel.class);
        recipeCardsViewModel.getRecipes().observe(this, new Observer<Resource<RecipeCardsResponse>>() {
            @Override
            public void onChanged(@Nullable Resource<RecipeCardsResponse> recipesResponse) {
                switch (recipesResponse.getStatus()){
                    case LOADING: //loading
                        break;
                    case ERROR:
                        Log.e("Error", recipesResponse.getException().getMessage(),
                                recipesResponse.getException());
                        Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                        break;
                    case SUCCESS:
                        RecipeCardsResponse data = recipesResponse.getData();
                        recipeCardsAdapter.setRecipes(data.getList());
                        break;
                }
            }
        });
    }

    private void displayRecipeCards(List<RecipeComplete> recipes) {

    }

    @Override
    public void onClick(RecipeComplete selectedRecipe) {
        Toast toast = Toast.makeText(this, "You clicked " + selectedRecipe.getName(), Toast.LENGTH_SHORT);
        toast.show();
    }
}