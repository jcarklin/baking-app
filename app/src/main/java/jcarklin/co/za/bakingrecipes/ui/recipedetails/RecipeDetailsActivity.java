package jcarklin.co.za.bakingrecipes.ui.recipedetails;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.repository.model.FetchStatus;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;
import jcarklin.co.za.bakingrecipes.ui.stepdetails.StepDetailsActivity;
import jcarklin.co.za.bakingrecipes.ui.stepdetails.StepListAdapter;

import static jcarklin.co.za.bakingrecipes.BakingApplication.test;

public class RecipeDetailsActivity extends AppCompatActivity implements StepListAdapter.RecipeStepOnClickHandler{

    private RecipeDetailsViewModel recipeDetailsViewModel;
    private Toast toast;
    private ActionBar actionBar;

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
        recipeDetailsViewModel.getStatus().observe(this, new Observer<FetchStatus>() {
            @Override
            public void onChanged(@Nullable FetchStatus fetchStatus) {
                if (fetchStatus==null || fetchStatus.getStatus().equals(FetchStatus.Status.LOADING)) {
                    //todo showProgressBar();
                } else if (fetchStatus.getStatus().equals(FetchStatus.Status.TOAST)) {
                    showToast(getString(fetchStatus.getStatusMessage()));
                    recipeDetailsViewModel.clearStatus();
                } else if (fetchStatus.getStatus().equals(FetchStatus.Status.CRITICAL_ERROR)) {
                    //showError();
                } else {
                    //showRecipeDetails();
                }
            }
        });//todo
    }

    private void showToast(String message) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onClick(int selectedStepIndex) {
        Intent intent = new Intent(this, StepDetailsActivity.class);
        intent.putExtra("selectedStep",selectedStepIndex);
        startActivity(intent);
    }
}
