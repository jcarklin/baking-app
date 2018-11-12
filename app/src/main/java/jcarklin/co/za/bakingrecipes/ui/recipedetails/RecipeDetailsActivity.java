package jcarklin.co.za.bakingrecipes.ui.recipedetails;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;

import static jcarklin.co.za.bakingrecipes.BakingApplication.test;

public class RecipeDetailsActivity extends AppCompatActivity {

    private RecipeDetailsViewModel recipeDetailsViewModel;
    ActionBar actionBar;
    @BindView(R.id.background_image)
    ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);
        actionBar = this.getSupportActionBar();

        recipeDetailsViewModel = ViewModelProviders.of(this).get(RecipeDetailsViewModel.class);
        recipeDetailsViewModel.getSelectedRecipe().observe(this, new Observer<RecipeComplete>() {
            @Override
            public void onChanged(@Nullable RecipeComplete recipe) {
                if (actionBar != null) {
                    actionBar.setTitle(recipe.getName());
                    actionBar.setSubtitle(getString(R.string.number_of_servings)+" "+recipe.getServings());
                }
                if (test){
                    recipe.setImage("https://food.fnr.sndimg.com/content/dam/images/food/fullset/2013/12/9/0/FNK_Cheesecake_s4x3.jpg.rend.hgtvcom.826.620.suffix/1387411272847.jpeg");
                }
                if (recipe.getImage() != null && !recipe.getImage().isEmpty()) {
                    Picasso.get()
                            .load(recipe.getImage())
                            .placeholder(R.drawable.ic_cake_black_48dp)
                            .error(R.drawable.ic_cake_black_48dp)
                            .into(background);
                }
            }
        });
        //ToDo
//        recipeCardsViewModel.getStatus().observe(this, new Observer<FetchStatus>() {
//            @Override
//            public void onChanged(@Nullable FetchStatus fetchStatus) {
//                if (fetchStatus==null || fetchStatus.getStatus().equals(FetchStatus.Status.LOADING)) {
//                    showProgressBar();
//                } else if (fetchStatus.getStatus().equals(FetchStatus.Status.CRITICAL_ERROR)) {
//                    showError(getString(fetchStatus.getStatusMessage()));
//                } else {
//                    showRecipes();
//                }
//            }
//        });



//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.recipe_heading, RecipeDetailsHeadingFragment.newInstance())
//                    .replace(R.id.recipe_ingredients, RecipeDetailsIngredientsFragment.newInstance())
//                    .commitNow();
//        }
    }


}
