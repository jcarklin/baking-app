package jcarklin.co.za.bakingrecipes.ui.recipedetails;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;


public class RecipeDetailsHeadingFragment extends Fragment {

    private RecipeDetailsViewModel recipeDetailsViewModel;

    @BindView(R.id.tv_recipe_name)
    TextView recipeName;

    @BindView(R.id.iv_recipe_thumbnail)
    ImageView recipeThumbnail;

    @BindView(R.id.tv_number_servings)
    TextView numServings;

    public static RecipeDetailsHeadingFragment newInstance() {
        return new RecipeDetailsHeadingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_card, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recipeDetailsViewModel = ViewModelProviders.of(getActivity()).get(RecipeDetailsViewModel.class);
        recipeDetailsViewModel.getSelectedRecipe().observe(this, new Observer<RecipeComplete>() {
            @Override
            public void onChanged(@Nullable RecipeComplete recipeComplete) {
                populateUi(recipeComplete);
            }
        });
    }

    private void populateUi(RecipeComplete recipeComplete) {
        recipeName.setText(recipeComplete.getName());
        numServings.setText(String.valueOf(recipeComplete.getServings()));
        if (!recipeComplete.getImage().isEmpty()) {
            Picasso.get()
                    .load(recipeComplete.getImage())
                    .placeholder(R.drawable.ic_cake_black_48dp)
                    .error(R.drawable.ic_cake_black_48dp)
                    .into(recipeThumbnail);
        }
    }


}
