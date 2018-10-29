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
import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;
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

    private final LiveData<List<RecipeComplete>> recipes;
    private final MutableLiveData<FetchStatus> status = new MutableLiveData<>();

    private final ConnectivityManager connectivityManager;

    private BakingAppRepository(Application application) {
        bakingAppDao = BakingAppDatabase.getInstance(application).bakingAppDao();
        recipes = bakingAppDao.fetchAllRecipes();
        setupNetworkApi();
        executor = Executors.newSingleThreadExecutor();
        connectivityManager = (ConnectivityManager)application.getSystemService(Context.CONNECTIVITY_SERVICE);
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

    private void refresh() {
        if (refresh) {
            bakingAppDao.clearRecipes();
        }

        if (bakingAppDao.getNumberOfRecipes()>0) {
            Log.d(TAG, "Using database");
            return;
        }

        try {
            Response<List<RecipeComplete>> response = bakingAppApi.getRecipes().execute();

            List<RecipeComplete> recipeCompleteList = response.body();
            if (recipeCompleteList != null) {
                long[] insertedIds = bakingAppDao.insertCompleteRecipes(recipeCompleteList);
                if (insertedIds == null || insertedIds.length != recipeCompleteList.size()) {
                    Log.e(TAG, "Unable to insert");
                } else {
                    Log.d(TAG, "Data inserted");
                    refresh = false;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkNetworkAvailability() {
        //Check if network is available
        NetworkInfo networkInfo = connectivityManager==null ? null : connectivityManager.getActiveNetworkInfo();
        boolean available = true;
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            status.setValue(new FetchStatus(FetchStatus.Status.ERROR,R.string.network_unavailable));
            available = false;
        }
        return available;
    }

    public LiveData<FetchStatus> getStatus() {
        return status;
    }

    public LiveData<List<RecipeComplete>> getRecipes() {
        status.postValue(new FetchStatus(FetchStatus.Status.LOADING,null));
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
}
