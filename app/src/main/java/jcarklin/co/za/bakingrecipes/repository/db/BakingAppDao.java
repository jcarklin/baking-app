package jcarklin.co.za.bakingrecipes.repository.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
import java.util.Set;

import jcarklin.co.za.bakingrecipes.repository.model.Ingredient;
import jcarklin.co.za.bakingrecipes.repository.model.Recipe;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;
import jcarklin.co.za.bakingrecipes.repository.model.Step;

@Dao
public interface BakingAppDao {

    @Query("SELECT * FROM recipes")
    LiveData<List<RecipeComplete>> fetchAllRecipes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addRecipe(Recipe recipe);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long addIngredients(Ingredient... ingredients);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addSteps(Step... steps);

    @Delete
    void clearRecipes();

    @Query("SELECT * FROM ingredients WHERE shopping_list = 1")
    Set<Ingredient> getShoppingList();

    @Query("DELETE FROM ingredients WHERE shopping_list = 0")
    Set<Ingredient> clearIngredients();

    @Update
    int updateShoppingList(Ingredient ingredient);
}
