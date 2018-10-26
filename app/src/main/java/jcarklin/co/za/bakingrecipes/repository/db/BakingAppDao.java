package jcarklin.co.za.bakingrecipes.repository.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import java.util.List;
import java.util.Set;

import jcarklin.co.za.bakingrecipes.repository.model.Ingredient;
import jcarklin.co.za.bakingrecipes.repository.model.Recipe;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;
import jcarklin.co.za.bakingrecipes.repository.model.Step;

@Dao
public abstract class BakingAppDao {

    @Query("SELECT * FROM recipes")
    @Transaction
    public abstract LiveData<List<RecipeComplete>> fetchAllRecipes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long addRecipe(Recipe recipe);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract List<Long> addIngredients(List<Ingredient> ingredients);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> addSteps(List<Step> steps);

    @Query("DELETE FROM recipes")
    public abstract void clearRecipes();

    @Query("SELECT * FROM ingredients WHERE shopping_list = 1")
    public abstract List<Ingredient> getShoppingList();

    @Query("DELETE FROM ingredients WHERE shopping_list = 0")
    public abstract void clearIngredients();

    @Update
    public abstract int updateShoppingList(Ingredient ingredient);

    @Transaction
    public void insertRecipeStepsAndIngredients(RecipeComplete recipe) {
        addRecipe(recipe);
        addIngredients(recipe.getIngredients());
        addSteps(recipe.getSteps());
    }

    @Transaction
    public void clearRecipesExceptShoppingList() {
        clearIngredients();
        clearRecipes();
    }
}
