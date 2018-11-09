package jcarklin.co.za.bakingrecipes.ui.recipedetails;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import jcarklin.co.za.bakingrecipes.repository.BakingAppRepository;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;

public class RecipeDetailsViewModel extends AndroidViewModel {

    private final BakingAppRepository bakingAppRepository;

    public RecipeDetailsViewModel(Application application) {
        super(application);
        bakingAppRepository = BakingAppRepository.getInstance(application);
    }

    public LiveData<RecipeComplete> getSelectedRecipe() {
        return bakingAppRepository.getSelectedRecipe();
    }


}
