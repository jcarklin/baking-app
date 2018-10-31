package jcarklin.co.za.bakingrecipes.ui.recipecards;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.repository.model.FetchStatus;
import jcarklin.co.za.bakingrecipes.repository.model.RecipeComplete;

public class RecipeCardsFragment extends Fragment implements RecipeCardsAdapter.RecipeCardsOnClickHandler {

    private RecipeCardsAdapter recipeCardsAdapter = null;
    private RecipeCardsViewModel recipeCardsViewModel;

    @BindView(R.id.rv_recipes)
    RecyclerView rvRecipes;

    @BindView(R.id.loading)
    ProgressBar loading;

    @BindView(R.id.error_message)
    TextView errorMessage;

//    @BindView(R.id.error_icon)
//    ImageView errorIcon;

    public RecipeCardsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_cards, container, false);
        ButterKnife.bind(this, view);

        recipeCardsAdapter = new RecipeCardsAdapter(this);
        rvRecipes.setAdapter(recipeCardsAdapter);
        RecyclerView.LayoutManager layoutManager;
        if (getString(R.string.screen_type).equals("phone")) {
            layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        } else {
            layoutManager = new GridLayoutManager(getContext(), 4, GridLayoutManager.VERTICAL, false);
        }
        rvRecipes.setLayoutManager(layoutManager);
        rvRecipes.setHasFixedSize(true);

        return view;
    }

    private void setupViewModel() {
        recipeCardsViewModel = ViewModelProviders.of(getActivity()).get(RecipeCardsViewModel.class);
        recipeCardsViewModel.getRecipes().observe(this, new Observer<List<RecipeComplete>>() {
            @Override
            public void onChanged(@Nullable List<RecipeComplete> recipesResponse) {
                recipeCardsAdapter.setRecipes(recipesResponse);
            }
        });
        recipeCardsViewModel.getStatus().observe(this, new Observer<FetchStatus>() {
            @Override
            public void onChanged(@Nullable FetchStatus fetchStatus) {
                if (fetchStatus==null || fetchStatus.getStatus().equals(FetchStatus.Status.LOADING)) {
                    showProgressBar();
                } else if (fetchStatus.getStatus().equals(FetchStatus.Status.CRITICAL_ERROR)) {
                    showError(getString(fetchStatus.getStatusMessage()));
                } else {
                    showRecipes();
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    @Override
    public void onClick(RecipeComplete selectedRecipe) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(getContext(),"You clicked " + selectedRecipe.getName(),Toast.LENGTH_LONG);
        toast.show();
    }

    private void showRecipes() {
        rvRecipes.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
        errorMessage.setVisibility(View.GONE);
 //       errorIcon.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        rvRecipes.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.VISIBLE);
        errorMessage.setVisibility(View.GONE);
//        errorIcon.setVisibility(View.GONE);
    }

    private void showError() {
        rvRecipes.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.GONE);
        errorMessage.setVisibility(View.VISIBLE);
//        errorIcon.setImageResource(R.drawable.ic_error_outline_red_24dp);
//        errorIcon.setVisibility(View.VISIBLE);
    }
    Toast toast;
    private void showError(String msg) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
