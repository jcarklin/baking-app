package jcarklin.co.za.bakingrecipes.ui.recipecards;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.repository.model.Recipe;

public class RecipeCardsAdapter extends RecyclerView.Adapter<RecipeCardsAdapter.RecipeCardViewHolder> {

    private List<Recipe> recipeList = new ArrayList<>();
    private final RecipeCardsOnClickHandler recipeCardsOnClickHandler;
    private Context context;

    public interface RecipeCardsOnClickHandler {
        void onClick(Recipe selectedRecipe);
    }

    public RecipeCardsAdapter(RecipeCardsOnClickHandler recipeCardsOnClickHandler) {
        this.recipeCardsOnClickHandler = recipeCardsOnClickHandler;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipeList = recipes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipe_card, viewGroup, false);
        return new RecipeCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeCardViewHolder viewHolder, int i) {
        Recipe recipe = recipeList.get(i);
        viewHolder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }


    //ViewHolder Class
    public class RecipeCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_recipe_name)
        TextView recipeName;

        @BindView(R.id.iv_recipe_thumbnail)
        ImageView recipeThumbnail;

        @BindView(R.id.tv_number_servings)
        TextView numServings;

//        @BindView(R.id.tv_number_ingredients)
//        TextView numIngredients;
//
//        @BindView(R.id.tv_number_steps)
//        TextView numSteps;


        RecipeCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(Recipe recipe) {
            recipeName.setText(recipe.getName());
            if (!recipe.getImage().isEmpty()) {
                Picasso.get()
                        .load(recipe.getImage())
                        .placeholder(R.drawable.ic_cake_black_48dp)
                        .error(R.drawable.ic_cake_black_48dp)
                        .into(recipeThumbnail);
            }
            numServings.setText(context.getString(R.string.number_of_servings) + recipe.getServings());
//            numIngredients.setText("Number of Ingredients: " + recipe.getIngredients().size());
//            numSteps.setText("Number of Steps: " + recipe.getSteps().size());
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe selectedRecipe = recipeList.get(adapterPosition);
            recipeCardsOnClickHandler.onClick(selectedRecipe);
        }
    }
}
