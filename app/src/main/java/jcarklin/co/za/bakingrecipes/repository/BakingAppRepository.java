package jcarklin.co.za.bakingrecipes.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import jcarklin.co.za.bakingrecipes.repository.api.BakingAppRetrofitService;
import jcarklin.co.za.bakingrecipes.repository.db.BakingAppDao;
import jcarklin.co.za.bakingrecipes.repository.db.BakingAppDatabase;
import jcarklin.co.za.bakingrecipes.repository.model.Ingredient;
import jcarklin.co.za.bakingrecipes.repository.model.Recipe;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;
import jcarklin.co.za.bakingrecipes.repository.model.Step;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class BakingAppRepository {

    private static final String TAG = BakingAppRepository.class.getSimpleName();
    private static final String BASE_URL = "http://go.udacity.com/";

    private static BakingAppRepository repository;

    private BakingAppRetrofitService bakingAppRetrofitService;
    private final BakingAppDao bakingAppDao;

    protected LiveData<List<RecipeComplete>> recipesList;

    private BakingAppRepository(Application application) {
        bakingAppDao = BakingAppDatabase.getInstance(application).bakingAppDao();
        setupNetworkApi();
        recipesList = bakingAppDao.fetchAllRecipes();
    }

    public static BakingAppRepository getInstance(Application application) {
        if (repository == null) {
            repository = new BakingAppRepository(application);
        }
        return repository;
    }

    private void setupNetworkApi() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(loggingInterceptor);
        OkHttpClient okHttpClient = okHttpClientBuilder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        bakingAppRetrofitService = retrofit.create(BakingAppRetrofitService.class);
    }

    public void setupRecipes() {

        Response<List<RecipeComplete>> recipeCompletesResponse = null;
        try {
            recipeCompletesResponse = bakingAppRetrofitService.getRecipes().execute();
            if (recipeCompletesResponse.isSuccessful()) {
                bakingAppDao.clearRecipesExceptShoppingList();
                for (RecipeComplete recipeComplete : recipeCompletesResponse.body()) {
                    for (Ingredient ingredient : recipeComplete.getIngredients()) {
                        ingredient.setRecipeId(recipeComplete.getId());
                    }
                    for (Step step : recipeComplete.getSteps()) {
                        step.setRecipeId(recipeComplete.getId());
                    }
                    bakingAppDao.insertRecipeStepsAndIngredients(recipeComplete);
                }
            } else {
                Log.i(TAG,"Unsuccessful attempt to retrieve recipes");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LiveData<List<RecipeComplete>> getRecipesList() {
        return recipesList;
    }
}
