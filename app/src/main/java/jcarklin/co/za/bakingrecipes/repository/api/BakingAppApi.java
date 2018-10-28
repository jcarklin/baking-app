package jcarklin.co.za.bakingrecipes.repository.api;

import java.util.List;

import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;
import retrofit2.Call;
import retrofit2.http.GET;

public interface BakingAppApi {

    @GET("android-baking-app-json")
    Call<List<RecipeComplete>> getRecipes();

}
