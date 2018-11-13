package jcarklin.co.za.bakingrecipes.ui.recipecards;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.repository.model.FetchStatus;
import jcarklin.co.za.bakingrecipes.repository.model.Recipe;
import jcarklin.co.za.bakingrecipes.ui.recipedetails.RecipeDetailsActivity;

public class RecipeCardsFragment extends Fragment implements RecipeCardsAdapter.RecipeCardsOnClickHandler {

    private RecipeCardsAdapter recipeCardsAdapter = null;
    private Toast toast;


    @BindView(R.id.rv_recipes)
    RecyclerView rvRecipes;

    @BindView(R.id.loading)
    ProgressBar loading;

    @BindView(R.id.error_message)
    TextView errorMessage;

    @BindView(R.id.error_icon)
    ImageView errorIcon;
    private RecipeCardsViewModel recipeCardsViewModel;

    public RecipeCardsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_cards, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recipeCardsAdapter = new RecipeCardsAdapter(this);
        rvRecipes.setAdapter(recipeCardsAdapter);
        RecyclerView.LayoutManager layoutManager;
        if (getString(R.string.screen_type).equals("phone")) {
            layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        } else {
            layoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        }
        rvRecipes.setLayoutManager(layoutManager);
        rvRecipes.setHasFixedSize(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewModel();
    }

    private void setupViewModel() {
        recipeCardsViewModel = ViewModelProviders.of(getActivity()).get(RecipeCardsViewModel.class);
        recipeCardsViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipesResponse) {
                recipeCardsAdapter.setRecipes(recipesResponse);
            }
        });
        recipeCardsViewModel.getStatus().observe(this, new Observer<FetchStatus>() {
            @Override
            public void onChanged(@Nullable FetchStatus fetchStatus) {
                if (fetchStatus==null || fetchStatus.getStatus().equals(FetchStatus.Status.LOADING)) {
                    showProgressBar();
                } else if (fetchStatus.getStatus().equals(FetchStatus.Status.TOAST)) {
                    showToast(getString(fetchStatus.getStatusMessage()));
                } else if (fetchStatus.getStatus().equals(FetchStatus.Status.CRITICAL_ERROR)) {
                    showError();
                } else {
                    showRecipes();
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(Recipe selectedRecipe) {
        Intent selectRecipeIntent = new Intent(getContext(),RecipeDetailsActivity.class);
        recipeCardsViewModel.setSelectedRecipe(selectedRecipe.getId());
        startActivity(selectRecipeIntent);
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

    private void showToast(String message) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
