package jcarklin.co.za.bakingrecipes.repository.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import jcarklin.co.za.bakingrecipes.repository.model.Ingredient;
import jcarklin.co.za.bakingrecipes.repository.model.Recipe;
import jcarklin.co.za.bakingrecipes.repository.model.ShoppingList;
import jcarklin.co.za.bakingrecipes.repository.model.Step;

@Database(entities = {Recipe.class,Step.class,Ingredient.class, ShoppingList.class},version = 1,exportSchema = false)
public abstract class BakingAppDatabase extends RoomDatabase {

    private static BakingAppDatabase ourInstance = null;

    private static final String LOG_TAG = BakingAppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DB_NAME = "baking_recipes_app";

    public static BakingAppDatabase getInstance(Context context) {
        if (ourInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new Database");
                ourInstance = Room.databaseBuilder(context.getApplicationContext(),
                        BakingAppDatabase.class,BakingAppDatabase.DB_NAME)
                        .build();
            }
        }
        return ourInstance;
    }

    public abstract BakingAppDao bakingAppDao();
}
