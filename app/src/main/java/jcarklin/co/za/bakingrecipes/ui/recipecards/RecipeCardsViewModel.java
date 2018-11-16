package jcarklin.co.za.bakingrecipes.ui.recipecards;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import jcarklin.co.za.bakingrecipes.repository.BakingAppRepository;
import jcarklin.co.za.bakingrecipes.repository.model.FetchStatus;
import jcarklin.co.za.bakingrecipes.repository.model.Recipe;

public class RecipeCardsViewModel extends AndroidViewModel {

    private final BakingAppRepository bakingAppRepository;

    private final LiveData<List<Recipe>> recipes;

    public RecipeCardsViewModel(@NonNull Application application) {
        super(application);
        bakingAppRepository = BakingAppRepository.getInstance(application);
        recipes = bakingAppRepository.getRecipes();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }

    public LiveData<FetchStatus> getStatus() {
        return bakingAppRepository.getStatus();
    }

    public void setSelectedRecipe(Integer id) {
        bakingAppRepository.setSelectedRecipe(id);
    }

    public void refresh() {
        bakingAppRepository.refreshRecipes();
    }
}