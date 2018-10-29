package jcarklin.co.za.bakingrecipes.ui.recipecards;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import jcarklin.co.za.bakingrecipes.databinding.RecipeCardBinding;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;

public class RecipeCardsAdapter extends RecyclerView.Adapter<RecipeCardsAdapter.RecipeCardViewHolder> {

    private List<RecipeComplete> recipeList = new ArrayList<>();
    private final RecipeCardsOnClickHandler recipeCardsOnClickHandler;

    public interface RecipeCardsOnClickHandler {
        void onClick(RecipeComplete selectedRecipe);
    }

    public RecipeCardsAdapter(RecipeCardsOnClickHandler recipeCardsOnClickHandler) {
        this.recipeCardsOnClickHandler = recipeCardsOnClickHandler;
    }

    public void setRecipes(List<RecipeComplete> recipes) {
        this.recipeList = recipes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        RecipeCardBinding binding = RecipeCardBinding.inflate(inflater, viewGroup, false);
        return new RecipeCardViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeCardViewHolder viewHolder, int i) {
        RecipeComplete recipeComplete = recipeList.get(i);
        viewHolder.bind(recipeComplete);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }


    //ViewHolder Class
    public class RecipeCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final RecipeCardBinding binding;

        RecipeCardViewHolder(RecipeCardBinding recipeCardBinding) {
            super(recipeCardBinding.getRoot());
            this.binding = recipeCardBinding;
            itemView.setOnClickListener(this);
        }

        public void bind(RecipeComplete recipeComplete) {
            binding.setRecipe(recipeComplete);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            RecipeComplete selectedRecipe = recipeList.get(adapterPosition);
            recipeCardsOnClickHandler.onClick(selectedRecipe);
        }
    }
}
