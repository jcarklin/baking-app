package jcarklin.co.za.bakingrecipes.ui.stepdetails;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;
import jcarklin.co.za.bakingrecipes.repository.model.Step;
import jcarklin.co.za.bakingrecipes.ui.recipedetails.RecipeDetailsViewModel;

public class StepDetailsActivity extends AppCompatActivity implements StepListAdapter.RecipeStepOnClickHandler {

    private static final String FRAGMENT_STEP_DETAIL_WITH_VIDEO = "FRAGMENT_WITH_VIDEO";
    private int selectedStepIndex = 0;
    private RecipeDetailsViewModel recipeDetailsViewModel;
    private ActionBar actionBar;

//    @BindView(R.id.tv_short_description)
//    TextView shortDescription;
    @BindView(R.id.btn_next)
    Button nextButton;
    @BindView(R.id.btn_prev)
    Button prevButton;
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
        StepDetailsFragment stepDetailsFragment;
        if (savedInstanceState != null && savedInstanceState.containsKey("stepIndex")) {
            selectedStepIndex = savedInstanceState.getInt("stepIndex");
        } else {
            selectedStepIndex = getIntent().getIntExtra("selectedStep", 0);
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedStepIndex++;
                if (selectedStepIndex==recipeDetailsViewModel.getSelectedRecipeSteps().size()) {
                    selectedStepIndex--;
                }
                populateUI();
            }
        });
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedStepIndex>0) {
                    selectedStepIndex--;
                }
                populateUI();
            }
        });

    }

    private void populateUI() {
        List<Step> selectedRecipeSteps = recipeDetailsViewModel.getSelectedRecipeSteps();
        int visible = selectedStepIndex==0?View.INVISIBLE:View.VISIBLE;
        prevButton.setVisibility(visible);
        visible = selectedStepIndex==selectedRecipeSteps.size()-1?View.INVISIBLE:View.VISIBLE;
        nextButton.setVisibility(visible);
        Step step = selectedRecipeSteps.get(selectedStepIndex);
        if (actionBar != null) {
            actionBar.setSubtitle(step.getId()+" - " +step.getShortDescription());
        }
 //       shortDescription.setText(step.getId()+" - " +step.getShortDescription());
        manageFragment(StepDetailsFragment.newInstance(), step);
    }

    private void manageFragment(StepDetailsFragment fragment, Step step) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment.setStep(step);
        if (getString(R.string.screen_type).equals("phone")) { //todo make constant
            fragmentManager.beginTransaction()
                    .replace(R.id.step_details_fragment_container,fragment,FRAGMENT_STEP_DETAIL_WITH_VIDEO)
                    .commit();
        }
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
    }
}