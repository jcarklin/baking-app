package jcarklin.co.za.bakingrecipes.ui.stepdetails;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import jcarklin.co.za.bakingrecipes.R;


public class StepDetailsNavigationFragment extends Fragment {

    @BindView(R.id.btn_next)
    Button nextButton;
    @BindView(R.id.btn_prev)
    Button prevButton;

    private StepDetailNavigationOnClickHandler onNavClickHandler;

    public interface StepDetailNavigationOnClickHandler {
        void onClickNext();
        void onClickPrevious();
    }

    public StepDetailsNavigationFragment() {
        // Required empty public constructor
    }

    public static StepDetailsNavigationFragment newInstance() {
        StepDetailsNavigationFragment fragment = new StepDetailsNavigationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details_navigation, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNavClickHandler.onClickNext();
            }
        });
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNavClickHandler.onClickPrevious();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onNavClickHandler = (StepDetailNavigationOnClickHandler)context;
        } catch (ClassCastException cce) {
            throw new ClassCastException(context.toString() + " must implement StepDetailNavigationOnClickHandler");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void showPrevButton(int visibility) {
        prevButton.setVisibility(visibility);
    }

    public void showNextButton(int visibility) {
        nextButton.setVisibility(visibility);
    }

}
