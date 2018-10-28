package jcarklin.co.za.bakingrecipes.ui.recipecards;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import jcarklin.co.za.bakingrecipes.repository.BakingAppRepository;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeCardsResponse;
import jcarklin.co.za.bakingrecipes.repository.model.Resource;

public class RecipeCardsViewModel extends AndroidViewModel {

    private final BakingAppRepository bakingAppRepository;
    private final LiveData<Resource<RecipeCardsResponse>> recipes;

    public RecipeCardsViewModel(@NonNull Application application) {
        super(application);
        bakingAppRepository = BakingAppRepository.getInstance(application);
        recipes = bakingAppRepository.fetchRecipes();
    }

    public LiveData<Resource<RecipeCardsResponse>> getRecipes() {
        return recipes;
    }

    public void clearRecipes() {
        bakingAppRepository.clearRecipes();
    }
}