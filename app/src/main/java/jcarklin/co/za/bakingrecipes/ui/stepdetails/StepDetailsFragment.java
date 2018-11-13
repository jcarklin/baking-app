package jcarklin.co.za.bakingrecipes.ui.stepdetails;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ui.PlayerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;
import jcarklin.co.za.bakingrecipes.repository.model.Step;
import jcarklin.co.za.bakingrecipes.ui.recipedetails.RecipeDetailsViewModel;

public class StepDetailsFragment extends Fragment {

    private RecipeDetailsViewModel recipeDetailsViewModel;


    int stepIndex = 0;
    Step selectedStep;
    List<Step> selectedRecipeSteps;

    @BindView(R.id.pv_step_video)
    PlayerView playerView;

    public StepDetailsFragment() {

    }

    public static StepDetailsFragment newInstance() {
        StepDetailsFragment fragment = new StepDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recipeDetailsViewModel = ViewModelProviders.of(getActivity()).get(RecipeDetailsViewModel.class);
        recipeDetailsViewModel.getSelectedRecipe().observe(this, new Observer<RecipeComplete>() {
                @Override
                public void onChanged(@Nullable RecipeComplete recipeComplete) {
                    selectedRecipeSteps = recipeComplete.getSteps();
                }
            });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        playerView.setDefaultArtwork(getResources().getDrawable(R.drawable.ic_cake_black_48dp));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
