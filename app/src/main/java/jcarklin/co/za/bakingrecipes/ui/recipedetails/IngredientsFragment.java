package jcarklin.co.za.bakingrecipes.ui.recipedetails;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.repository.model.Ingredient;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;


public class IngredientsFragment extends Fragment {

    private RecipeDetailsViewModel recipeDetailsViewModel;
    private String ingredients = "";
    private Integer recipeid;

    @BindView(R.id.tv_ingredients_list)
    TextView ingredientsList;

    public static IngredientsFragment newInstance() {
        return new IngredientsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
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
        ingredients = "";
        recipeDetailsViewModel = ViewModelProviders.of(getActivity()).get(RecipeDetailsViewModel.class);
        recipeDetailsViewModel.getSelectedRecipe().observe(this, new Observer<RecipeComplete>() {
            @Override
            public void onChanged(@Nullable RecipeComplete recipeComplete) {
                populateUi(recipeComplete);
            }
        });
    }

    private void populateUi(RecipeComplete recipeComplete) {
        Iterator<Ingredient> ingredientIterator = recipeComplete.getIngredients().iterator();
        Ingredient ingredient;
        recipeid = recipeComplete.getId();
        while (ingredientIterator.hasNext()) {
            ingredient = ingredientIterator.next();
            ingredients += "\u2022  ";
            ingredients += ingredient.getIngredient();
            ingredients += " "+ingredient.getQuantity();
            ingredients += " "+ingredient.getMeasure();
            ingredients += "<br/>";
        }
        ingredientsList.setText(Html.fromHtml(ingredients));
    }

    public void addToShoppingList() {
        recipeDetailsViewModel.addToShoppingList(recipeid, ingredients);
    }


}