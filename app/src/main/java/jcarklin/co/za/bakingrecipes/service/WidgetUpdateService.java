package jcarklin.co.za.bakingrecipes.service;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.JobIntentService;

import java.util.ArrayList;
import java.util.List;

import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.repository.model.ShoppingList;
import jcarklin.co.za.bakingrecipes.ui.widget.ShoppingListAppWidgetProvider;

import static jcarklin.co.za.bakingrecipes.provider.BakingAppContract.CONTENT_URI;


public class WidgetUpdateService extends JobIntentService {

    public static final String ACTION_REFRESH = "jcarklin.co.za.bakingrecipes.repository.service.action.refresh_shopping_list";

    public WidgetUpdateService() {
    }

    public static void startActionRefreshShoppingList(Context context) {
        Intent intent = new Intent(context, WidgetUpdateService.class);
        intent.setAction(ACTION_REFRESH);
        enqueueWork(context, WidgetUpdateService.class, 1, intent);
    }


    @Override
    protected void onHandleWork(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_REFRESH.equals(action)) {
                handleActionRefreshShoppingList();
            }
        }
    }

    private void handleActionRefreshShoppingList() {
        Uri SHOPPING_LIST_URI = CONTENT_URI.buildUpon().build();
        List<ShoppingList> shoppingLists = null;

        Cursor cursor = getContentResolver().query(
                SHOPPING_LIST_URI,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.getCount() > 0) {
            shoppingLists = new ArrayList<>(cursor.getCount());
            try {
                while (cursor.moveToNext()) {
                    int recipeIdIndex = cursor.getColumnIndex(ShoppingList.COLUMN_ID);
                    int recipeNameIndex = cursor.getColumnIndex(ShoppingList.COLUMN_RECIPE_NAME);
                    int shoppingListItemsIndex = cursor.getColumnIndex(ShoppingList.COLUMN_SHOPPING_LIST);

                    int recipeId = cursor.getInt(recipeIdIndex);
                    String recipeName = cursor.getString(recipeNameIndex);
                    String shopping = cursor.getString(shoppingListItemsIndex);

                    shoppingLists.add(new ShoppingList(recipeId,recipeName,shopping));
                }
            }finally {
                cursor.close();
            }
        } else {
            String recipeName = "";
            String shopping = getString(R.string.empty_shopping_list_message);
            shoppingLists = new ArrayList<>(1);
            shoppingLists.add(new ShoppingList(null,recipeName,shopping));
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, ShoppingListAppWidgetProvider.class));

        ShoppingListAppWidgetProvider.updateShoppingListWidgets(this, appWidgetManager, shoppingLists, appWidgetIds);
    }
}
