package jcarklin.co.za.bakingrecipes.repository.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;
import java.util.Set;

import jcarklin.co.za.bakingrecipes.repository.model.Ingredient;
import jcarklin.co.za.bakingrecipes.repository.model.Recipe;

@Dao
public interface BakingAppDao {

    @Query("SELECT * FROM recipes")
    LiveData<List<Recipe>> fetchAllRecipes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addRecipes(Recipe... recipes);

    @Delete
    void clearRecipes();

    @Query("SELECT * FROM ingredients WHERE shopping_list = 1")
    Set<Ingredient> getShoppingList();

}
