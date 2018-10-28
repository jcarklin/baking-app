package jcarklin.co.za.bakingrecipes.repository.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import java.util.List;

import jcarklin.co.za.bakingrecipes.repository.model.Ingredient;
import jcarklin.co.za.bakingrecipes.repository.model.Recipe;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;
import jcarklin.co.za.bakingrecipes.repository.model.Step;

@Dao
public abstract class BakingAppDao {

    @Query("SELECT * FROM recipes")
    @Transaction
    public abstract LiveData<List<RecipeComplete>> fetchAllRecipes();

    @Query("SELECT COUNT(id) FROM recipes")
    public abstract int getNumberOfRecipes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long addRecipe(Recipe recipe);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> addIngredients(List<Ingredient> ingredients);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> addSteps(List<Step> steps);

    @Query("DELETE FROM recipes")
    public abstract void clearRecipes();

    //@Query("SELECT * FROM ingredients WHERE shopping_list = 1")
    //public abstract List<Ingredient> getShoppingList();

    @Update
    public abstract int updateShoppingList(Ingredient ingredient);

    @Transaction
    public long[] insertCompleteRecipes(List<RecipeComplete> recipes) {
        long[] ids = new long[recipes.size()];
        RecipeComplete recipeComplete;
        for (int i=0; i<recipes.size(); i++) {
            recipeComplete = recipes.get(i);
            for (Ingredient ingredient : recipeComplete.getIngredients()) {
                ingredient.setRecipeId(recipeComplete.getId());
            }
            for (Step step : recipeComplete.getSteps()) {
                step.setRecipeId(recipeComplete.getId());
            }
            ids[i] = addRecipe(recipeComplete);
            addIngredients(recipeComplete.getIngredients());
            addSteps(recipeComplete.getSteps());
        }
        return ids;
    }
}
