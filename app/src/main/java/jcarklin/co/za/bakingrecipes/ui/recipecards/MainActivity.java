package jcarklin.co.za.bakingrecipes.ui.recipecards;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.repository.model.FetchStatus;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;

public class MainActivity extends AppCompatActivity implements RecipeCardsAdapter.RecipeCardsOnClickHandler {

    private RecipeCardsAdapter recipeCardsAdapter = null;
    private RecipeCardsViewModel recipeCardsViewModel;

    @BindView(R.id.rv_recipes)
    RecyclerView rvRecipes;

    @BindView(R.id.loading)
    ProgressBar loading;

    @BindView(R.id.error_message)
    TextView errorMessage;

    @BindView(R.id.error_icon)
    ImageView errorIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recipeCardsAdapter = new RecipeCardsAdapter(this);
        rvRecipes.setAdapter(recipeCardsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvRecipes.setLayoutManager(layoutManager);
        rvRecipes.setHasFixedSize(true);
        setupViewModel();
    }

    private void setupViewModel() {
        recipeCardsViewModel = ViewModelProviders.of(this).get(RecipeCardsViewModel.class);
        recipeCardsViewModel.getRecipes().observe(this, new Observer<List<RecipeComplete>>() {
            @Override
            public void onChanged(@Nullable List<RecipeComplete> recipesResponse) {
                recipeCardsAdapter.setRecipes(recipesResponse);
                showRecipes();
            }
        });
        recipeCardsViewModel.getStatus().observe(this, new Observer<FetchStatus>() {
            @Override
            public void onChanged(@Nullable FetchStatus fetchStatus) {
                if (fetchStatus==null || fetchStatus.getStatus().equals(FetchStatus.Status.LOADING)) {
                    showProgressBar();
                } else if (fetchStatus.getStatus().equals(FetchStatus.Status.ERROR)) {
                    showError(getString(fetchStatus.getStatusMessage()));
                } else {
                    showRecipes();
                }
            }
        });
    }

    @Override
    public void onClick(RecipeComplete selectedRecipe) {

    }

    private void showRecipes() {
        rvRecipes.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
        errorMessage.setVisibility(View.GONE);
        errorIcon.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        rvRecipes.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.VISIBLE);
        errorMessage.setVisibility(View.GONE);
        errorIcon.setVisibility(View.GONE);
    }

    private void showError() {
        rvRecipes.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.GONE);
        errorMessage.setVisibility(View.VISIBLE);
        errorIcon.setImageResource(R.drawable.ic_error_outline_red_24dp);
        errorIcon.setVisibility(View.VISIBLE);
    }

    private void showError(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}