package jcarklin.co.za.bakingrecipes.ui.stepdetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.repository.model.Step;

public class StepDetailsActivity extends AppCompatActivity implements StepListAdapter.RecipeStepOnClickHandler {

    private Step selectedStep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        getIntent().getIntExtra("selectedStep",0);
    }

    @Override
    public void onClick(int selectedStepIndex) {
        Toast.makeText(this,"You clicked step " + selectedStep.getId(),Toast.LENGTH_SHORT).show();
    }
}
