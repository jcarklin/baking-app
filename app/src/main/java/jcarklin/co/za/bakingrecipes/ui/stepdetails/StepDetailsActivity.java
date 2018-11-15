package jcarklin.co.za.bakingrecipes.ui.stepdetails;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import java.util.List;

import butterknife.ButterKnife;
import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;
import jcarklin.co.za.bakingrecipes.repository.model.Step;
import jcarklin.co.za.bakingrecipes.ui.recipedetails.RecipeDetailsViewModel;

public class StepDetailsActivity extends AppCompatActivity implements
        StepListAdapter.RecipeStepOnClickHandler,
        StepDetailsNavigationFragment.StepDetailNavigationOnClickHandler {

    private static final String FRAGMENT_STEP_DETAIL_WITH_VIDEO = "FRAGMENT_WITH_VIDEO";
    private int selectedStepIndex = 0;
    private RecipeDetailsViewModel recipeDetailsViewModel;
    private ActionBar actionBar;
    private boolean createNewFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
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
                populateUI();
            }
        });

        createNewFragment = savedInstanceState==null;

        if (savedInstanceState != null && savedInstanceState.containsKey("stepIndex")) {
            selectedStepIndex = savedInstanceState.getInt("stepIndex");
        } else {
            selectedStepIndex = getIntent().getIntExtra("selectedStep", 0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateUI();
    }

    private void populateUI() {
        List<Step> selectedRecipeSteps = recipeDetailsViewModel.getSelectedRecipeSteps();
        Step step = selectedRecipeSteps.get(selectedStepIndex);
        if (actionBar != null) {
            actionBar.setSubtitle(step.getId()+" - " +step.getShortDescription());
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        StepDetailsFragment stepDetailsFragment = (StepDetailsFragment) fragmentManager.findFragmentByTag(FRAGMENT_STEP_DETAIL_WITH_VIDEO);
        if (stepDetailsFragment == null || createNewFragment) {
            stepDetailsFragment = StepDetailsFragment.newInstance();
        }
        stepDetailsFragment.setStep(step);
        int orientation = getResources().getConfiguration().orientation;
        if (getString(R.string.screen_type).equals("phone")) { //todo make constant
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                StepDetailsNavigationFragment navigation = (StepDetailsNavigationFragment) fragmentManager.findFragmentById(R.id.btns_next_prev);
                if (navigation != null) {
                    navigation.showPrevButton(selectedStepIndex==0?View.INVISIBLE:View.VISIBLE);
                    navigation.showNextButton(selectedStepIndex==selectedRecipeSteps.size()-1?View.INVISIBLE:View.VISIBLE);
                }
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (Build.VERSION.SDK_INT < 16) {
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                } else {
                    View decorView = getWindow().getDecorView();
                    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    actionBar.hide();
                }
            }
        }
        fragmentManager.beginTransaction()
            .replace(R.id.step_details_fragment_container,stepDetailsFragment,FRAGMENT_STEP_DETAIL_WITH_VIDEO)
            .commitNow();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("stepIndex", selectedStepIndex);
    }

    @Override
    public void onClick(int selectedStepIndex) {
        this.selectedStepIndex = selectedStepIndex;
        populateUI();
        createNewFragment = true;
    }

    @Override
    public void onClickNext() {
        selectedStepIndex++;
        if (selectedStepIndex==recipeDetailsViewModel.getSelectedRecipeSteps().size()) {
            selectedStepIndex--;
        }
        createNewFragment = true;
        populateUI();
    }

    @Override
    public void onClickPrevious() {
        if (selectedStepIndex>0) {
            selectedStepIndex--;
        }
        createNewFragment = true;
        populateUI();
    }
}