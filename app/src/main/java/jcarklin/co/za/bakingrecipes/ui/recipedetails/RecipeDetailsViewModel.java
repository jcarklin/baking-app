package jcarklin.co.za.bakingrecipes.ui.recipedetails;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import jcarklin.co.za.bakingrecipes.repository.BakingAppRepository;
import jcarklin.co.za.bakingrecipes.repository.model.FetchStatus;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;
import jcarklin.co.za.bakingrecipes.repository.model.ShoppingList;

public class RecipeDetailsViewModel extends AndroidViewModel {

    private final BakingAppRepository bakingAppRepository;
    private final LiveData<RecipeComplete> selectedRecipe;

    public RecipeDetailsViewModel(Application application) {
        super(application);
        bakingAppRepository = BakingAppRepository.getInstance(application);
        selectedRecipe = bakingAppRepository.getSelectedRecipe();
    }

    public LiveData<RecipeComplete> getSelectedRecipe() {
        return selectedRecipe;
    }

    public void addToShoppingList(String ingredients) {
        RecipeComplete recipeComplete = selectedRecipe.getValue();
        ShoppingList shoppingList = new ShoppingList(recipeComplete.getId(), recipeComplete.getName(), ingredients);
        bakingAppRepository.addToShoppingList(shoppingList);
    }

    public LiveData<FetchStatus> getStatus() {
        return bakingAppRepository.getStatus();
    }

    public void clearStatus() {
        bakingAppRepository.clearStatus();
    }

}
