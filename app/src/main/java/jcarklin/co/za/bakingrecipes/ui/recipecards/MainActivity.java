package jcarklin.co.za.bakingrecipes.ui.recipecards;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.ButterKnife;
import jcarklin.co.za.bakingrecipes.R;

public class MainActivity extends AppCompatActivity  {

    private RecipeCardsViewModel recipeCardsViewModel;

    private MenuItem refresh;
    private MenuItem shoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        recipeCardsViewModel = ViewModelProviders.of(this).get(RecipeCardsViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        refresh = menu.findItem(R.id.action_refresh);
        shoppingList = menu.findItem(R.id.action_shopping_list);
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Baking Recipes");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                recipeCardsViewModel.refresh();
                return true;
            case R.id.action_shopping_list:
                Toast toast = Toast.makeText(this, "This will show a shopping list", Toast.LENGTH_SHORT);
                toast.show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}