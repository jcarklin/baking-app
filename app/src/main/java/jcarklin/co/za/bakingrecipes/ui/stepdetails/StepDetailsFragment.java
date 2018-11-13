package jcarklin.co.za.bakingrecipes.ui.stepdetails;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.PlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.repository.model.Step;

public class StepDetailsFragment extends Fragment {

    private Step selectedStep;

    @BindView(R.id.pv_step_video)
    PlayerView playerView;
    @BindView(R.id.tv_step_description)
    TextView stepDescription;

    public StepDetailsFragment() {

    }

    public static StepDetailsFragment newInstance() {
        StepDetailsFragment fragment = new StepDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        populateUi();
    }

    private void populateUi() {
        playerView.setDefaultArtwork(getResources().getDrawable(R.drawable.ic_cake_black_48dp));
        stepDescription.setText(selectedStep.getDescription());
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setStep(Step step) {
        this.selectedStep = step;
    }
}
