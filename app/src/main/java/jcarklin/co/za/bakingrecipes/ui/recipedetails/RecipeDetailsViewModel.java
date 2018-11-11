package jcarklin.co.za.bakingrecipes.ui.recipedetails;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import jcarklin.co.za.bakingrecipes.repository.BakingAppRepository;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;
import jcarklin.co.za.bakingrecipes.repository.model.ShoppingList;

public class RecipeDetailsViewModel extends AndroidViewModel {

    private final BakingAppRepository bakingAppRepository;

    public RecipeDetailsViewModel(Application application) {
        super(application);
        bakingAppRepository = BakingAppRepository.getInstance(application);
    }

    public LiveData<RecipeComplete> getSelectedRecipe() {
        return bakingAppRepository.getSelectedRecipe();
    }

    public void addToShoppingList(Integer recipeid, String ingredients) {
        ShoppingList shoppingList = new ShoppingList(recipeid, ingredients);
        bakingAppRepository.addToShoppingList(shoppingList);
    }
}
