package jcarklin.co.za.bakingrecipes.ui.recipedetails;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.repository.model.Recipe;
import jcarklin.co.za.bakingrecipes.repository.model.Step;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeStepsViewHolder> {

    private List<Step> stepList = new ArrayList<>();
    private final RecipeStepOnClickHandler recipeStepOnClickHandler;
    private Context context;

    public interface RecipeStepOnClickHandler {
        void onClick(Step selectedStep);
    }

    public RecipeStepsAdapter(RecipeStepOnClickHandler recipeStepOnClickHandler) {
        this.recipeStepOnClickHandler = recipeStepOnClickHandler;
    }

    public void setSteps(List<Step> steps) {
        this.stepList = steps;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeStepsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.step_list_item, viewGroup, false);
        return new RecipeStepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepsViewHolder viewHolder, int i) {
        viewHolder.bind(stepList.get(i));
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }


    //ViewHolder Class
    public class RecipeStepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_short_description)
        TextView shortDescription;

        @BindView(R.id.tv_step_number)
        TextView stepNumber;

        @BindView(R.id.iv_step_arrow)
        ImageView stepArrow;

        RecipeStepsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(Step step) {
            stepNumber.setText(String.valueOf(step.getId()));
            shortDescription.setText(step.getShortDescription());
        }

        @Override
        public void onClick(View v) {
//            int adapterPosition = getAdapterPosition();
//            Recipe selectedRecipe = recipeList.get(adapterPosition);
//            recipeCardsOnClickHandler.onClick(selectedRecipe);
        }
    }
}
