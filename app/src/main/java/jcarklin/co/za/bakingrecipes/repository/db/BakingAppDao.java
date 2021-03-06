package jcarklin.co.za.bakingrecipes.repository.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;

import jcarklin.co.za.bakingrecipes.repository.model.Ingredient;
import jcarklin.co.za.bakingrecipes.repository.model.Recipe;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;
import jcarklin.co.za.bakingrecipes.repository.model.ShoppingList;
import jcarklin.co.za.bakingrecipes.repository.model.Step;

@Dao
public abstract class BakingAppDao {

    @Query("SELECT * FROM recipes")
    public abstract LiveData<List<Recipe>> getRecipesList();

    @Transaction
    @Query("SELECT * FROM recipes where id = :recipeId")
    public abstract RecipeComplete getRecipe(Integer recipeId);


    @Query("SELECT COUNT(id) FROM recipes")
    public abstract int getNumberOfRecipes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long addRecipe(Recipe recipe);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> addIngredients(List<Ingredient> ingredients);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> addSteps(List<Step> steps);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long addShoppingList(ShoppingList shoppingList);

    @Query("DELETE FROM recipes")
    public abstract int clearRecipes();

    @Update
    public abstract int updateShoppingList(Ingredient ingredient);

    @Query("SELECT * FROM shopping_list")
    public abstract List<ShoppingList> getShoppingLists();

    @Query("SELECT * FROM shopping_list")
    public abstract Cursor getShoppingListsCursor();

    @Transaction
    @Query("SELECT * FROM shopping_list where id = :recipeId")
    public abstract Cursor getShoppingList(Long recipeId);

    @Query("DELETE FROM shopping_list")
    public abstract int clearShoppingList();

    @Query("DELETE FROM shopping_list where id = :recipeId")
    public abstract int deleteShoppingListById(Long recipeId);

    @Transaction
    public long[] insertCompleteRecipes(List<RecipeComplete> recipes) {
        long[] ids = new long[recipes.size()];
            RecipeComplete recipeComplete;
            for (int i = 0; i < recipes.size(); i++) {
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
