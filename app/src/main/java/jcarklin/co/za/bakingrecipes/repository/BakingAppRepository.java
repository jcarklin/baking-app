package jcarklin.co.za.bakingrecipes.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.repository.api.BakingAppApi;
import jcarklin.co.za.bakingrecipes.repository.db.BakingAppDao;
import jcarklin.co.za.bakingrecipes.repository.db.BakingAppDatabase;
import jcarklin.co.za.bakingrecipes.repository.model.FetchStatus;
import jcarklin.co.za.bakingrecipes.repository.model.Recipe;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;
import jcarklin.co.za.bakingrecipes.repository.model.ShoppingList;
import jcarklin.co.za.bakingrecipes.service.ShoppingListWidgetService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class BakingAppRepository {

    private static final String TAG = BakingAppRepository.class.getSimpleName();
    private static final String BASE_URL = "http://go.udacity.com/";

    private static BakingAppRepository repository;

    private BakingAppApi bakingAppApi;
    private final BakingAppDao bakingAppDao;
    private final Executor executor;
    private boolean refresh = true;

    private final MutableLiveData<FetchStatus> status = new MutableLiveData<>();

    private final LiveData<List<Recipe>> recipes;
    private final MutableLiveData<RecipeComplete> selectedRecipe = new MutableLiveData<>();

    private final ConnectivityManager connectivityManager;

    private List<ShoppingList> shoppingLists = null;
    private Context context;

    private BakingAppRepository(Application application) {
        context = application;
        refresh = true;
        bakingAppDao = BakingAppDatabase.getInstance(application).bakingAppDao();
        recipes = bakingAppDao.getRecipesList();
        executor = Executors.newSingleThreadExecutor();
        connectivityManager = (ConnectivityManager)application.getSystemService(Context.CONNECTIVITY_SERVICE);
        setupNetworkApi();
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
        bakingAppApi = retrofit.create(BakingAppApi.class);
        refreshRecipes();
    }

    private void refresh() {
        status.postValue(new FetchStatus(FetchStatus.Status.LOADING,null));
        if (refresh) {
            bakingAppDao.clearRecipes();
        }
        if (bakingAppDao.getNumberOfRecipes() > 0) {
            Log.d(TAG, "Using database");
            status.postValue(new FetchStatus(FetchStatus.Status.SUCCESS,null));
        } else {
            try {
                Response<List<RecipeComplete>> response = bakingAppApi.getRecipes().execute();

                List<RecipeComplete> recipeCompleteList = response.body();
                if (recipeCompleteList != null) {
                    long[] insertedIds = bakingAppDao.insertCompleteRecipes(recipeCompleteList);
                    if (insertedIds == null || insertedIds.length != recipeCompleteList.size()) {
                        Log.e(TAG, "Unable to insert");
                        status.postValue(new FetchStatus(FetchStatus.Status.TOAST,R.string.error));
                    } else {
                        Log.d(TAG, "Data inserted");
                        status.postValue(new FetchStatus(FetchStatus.Status.SUCCESS,null));
                    }
                }
                //refreshShoppingList
                shoppingLists = bakingAppDao.getShoppingLists();
            } catch (IOException e) {
                e.printStackTrace();
                status.postValue(new FetchStatus(FetchStatus.Status.CRITICAL_ERROR,R.string.error));
            }
        }
        refresh = false;
    }

    private boolean checkNetworkAvailability() {
        //Check if network is available
        NetworkInfo networkInfo = connectivityManager==null ? null : connectivityManager.getActiveNetworkInfo();
        boolean available = true;
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            status.setValue(new FetchStatus(FetchStatus.Status.TOAST,R.string.network_unavailable));
            available = false;
        }
        return available;
    }

    public LiveData<FetchStatus> getStatus() {
        return status;
    }

    public LiveData<List<Recipe>> getRecipes() {
        if (checkNetworkAvailability()) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    refresh();
                }
            });
        }
        return recipes;
    }

    public void refreshRecipes() {
        refresh = true;
        getRecipes();
    }

    public void setSelectedRecipe(final Integer recipeId) {
        if (checkNetworkAvailability()) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    RecipeComplete recipeComplete = bakingAppDao.getRecipe(recipeId);
                    if (recipeComplete != null) {
                        selectedRecipe.postValue(recipeComplete);
                    }
                }
            });
        }

    }

    public LiveData<RecipeComplete> getSelectedRecipe() {
        return selectedRecipe;
    }

    public void addToShoppingList(final ShoppingList shoppingList) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                long inserted = bakingAppDao.addShoppingList(shoppingList);
                if (inserted > 0) {
                    status.postValue(new FetchStatus(FetchStatus.Status.TOAST,R.string.added_to_shopping_list));
                } else {
                    status.postValue(new FetchStatus(FetchStatus.Status.TOAST,R.string.error_adding_to_shopping_list));
                }
                shoppingLists = bakingAppDao.getShoppingLists();
                ShoppingListWidgetService.sendRefreshBroadcast(context);//todo does this work
            }
        });
    }

    public String getShoppingList() {//todo change to list so it can scroll in widget

        StringBuilder shoppingList = new StringBuilder();
        for (ShoppingList shList:shoppingLists) {
            shoppingList.append("<div>")
            .append("<h3>"+shList.getRecipeName()+"<h3>")
            .append(shList.getShoppingList())
            .append("</div>");
        }

        return shoppingList.toString();
    }

    public void clearStatus() {
        status.setValue(new FetchStatus(FetchStatus.Status.SUCCESS,null));
    }


}
