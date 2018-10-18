package jcarklin.co.za.bakingrecipes.repository;

import java.util.List;

import jcarklin.co.za.bakingrecipes.repository.api.BakingAppService;
import jcarklin.co.za.bakingrecipes.repository.model.Recipe;
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
    private BakingAppService bakingAppService;
    protected List<Recipe> recipesList;

    private BakingAppRepository() {
        setupNetworkApi();
        getRecipes();
    }

    public static BakingAppRepository getInstance() {
        if (repository == null) {
            repository = new BakingAppRepository();
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
        bakingAppService = retrofit.create(BakingAppService.class);
    }

    private void getRecipes() {
        bakingAppService.getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if(response.isSuccessful()) {
                    recipesList = response.body();
                } else {
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
    }


}
