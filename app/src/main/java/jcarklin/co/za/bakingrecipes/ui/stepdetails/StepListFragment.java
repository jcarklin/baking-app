package jcarklin.co.za.bakingrecipes.ui.stepdetails;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;
import jcarklin.co.za.bakingrecipes.ui.recipedetails.RecipeDetailsViewModel;


public class StepListFragment extends Fragment  {

    private RecipeDetailsViewModel recipeDetailsViewModel;
    private StepListAdapter stepsListingAdapter;
    private StepListAdapter.RecipeStepOnClickHandler onClickHandler;

    @BindView(R.id.rv_recipe_steps)
    RecyclerView recipeSteps;

    public static StepListFragment newInstance() {
        return new StepListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recipeDetailsViewModel = ViewModelProviders.of(getActivity()).get(RecipeDetailsViewModel.class);
        recipeDetailsViewModel.getSelectedRecipe().observe(this, new Observer<RecipeComplete>() {
            @Override
            public void onChanged(@Nullable RecipeComplete recipeComplete) {
                stepsListingAdapter.setSteps(recipeComplete.getSteps());
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stepsListingAdapter = new StepListAdapter(onClickHandler);
        recipeSteps.setAdapter(stepsListingAdapter);
        recipeSteps.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recipeSteps.setHasFixedSize(true);
        recipeSteps.setNestedScrollingEnabled(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onClickHandler = (StepListAdapter.RecipeStepOnClickHandler)context;
        } catch (ClassCastException cce) {
            throw new ClassCastException(context.toString() + " must implement RecipeStepOnClickHandler");
        }

    }
}
