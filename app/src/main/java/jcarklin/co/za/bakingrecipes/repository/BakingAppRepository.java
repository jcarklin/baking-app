package jcarklin.co.za.bakingrecipes.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import jcarklin.co.za.bakingrecipes.repository.api.BakingAppApi;
import jcarklin.co.za.bakingrecipes.repository.db.BakingAppDao;
import jcarklin.co.za.bakingrecipes.repository.db.BakingAppDatabase;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeCardsResponse;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;
import jcarklin.co.za.bakingrecipes.repository.model.Resource;
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

    private BakingAppApi bakingAppApi;
    private final BakingAppDao bakingAppDao;
    private final Executor executor;

    private BakingAppRepository(Application application) {
        bakingAppDao = BakingAppDatabase.getInstance(application).bakingAppDao();
        executor = Executors.newSingleThreadExecutor();
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
    }

    public LiveData<Resource<RecipeCardsResponse>> fetchRecipes() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        });

        final LiveData<List<RecipeComplete>> source = bakingAppDao.fetchAllRecipes();

        /**
         * From https://proandroiddev.com/build-an-app-with-offline-support-1a32c6bab7d2:
         * We will create a mediator to observe the changes. Room will automatically notify
         * all active observers when the data changes.
         * Because it is using LiveData,
         * this will be efficient because it will update the data only if there is at least one active observer.
         *
         */
        final MediatorLiveData mediator = new MediatorLiveData();
        mediator.addSource(source, new Observer<List<RecipeComplete>>() {
            @Override
            public void onChanged(@Nullable List<RecipeComplete> recipes) {
                Log.d("DATA", "Observed recipes list");
                RecipeCardsResponse resp = new RecipeCardsResponse(recipes);
                Resource<RecipeCardsResponse> success = Resource.<RecipeCardsResponse>success(resp);
                mediator.setValue(success);
            }
        });

        return mediator;
    }

    private void refresh() {

        if (bakingAppDao.getNumberOfRecipes()>0) {
            Log.d("DATA", "From cache");
            return;
        }

        try {
            Response<List<RecipeComplete>> response = bakingAppApi.getRecipes().execute();

            List<RecipeComplete> recipeCompleteList = response.body();
            if (recipeCompleteList != null) {
                long[] insertedIds = bakingAppDao.insertCompleteRecipes(recipeCompleteList);
                if (insertedIds == null || insertedIds.length != recipeCompleteList.size()) {
                    Log.e("DATA", "Unable to insert");
                } else {
                    Log.d("DATA", "Data inserted");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void clearRecipes() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                bakingAppDao.clearRecipes();
            }
        });
    }


}
