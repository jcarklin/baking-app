package jcarklin.co.za.bakingrecipes.service;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import jcarklin.co.za.bakingrecipes.repository.model.ShoppingList;
import jcarklin.co.za.bakingrecipes.ui.widget.ShoppingListAppWidgetProvider;

import static jcarklin.co.za.bakingrecipes.provider.BakingAppContract.CONTENT_URI;


public class WidgetUpdateService extends IntentService {

    public static final String ACTION_REFRESH = "jcarklin.co.za.bakingrecipes.repository.service.action.refresh_shopping_list";

    public WidgetUpdateService() {
        super("WidgetUpdateService");
    }


    public static void startActionRefreshShoppingList(Context context) {
        Intent intent = new Intent(context, WidgetUpdateService.class);
        intent.setAction(ACTION_REFRESH);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_REFRESH.equals(action)) {
                handleActionRefreshShoppingList();
            }
        }
    }

    private void handleActionRefreshShoppingList() {
        Uri SHOPPING_LIST_URI = CONTENT_URI.buildUpon().build();
        ShoppingList shoppingList = null;

        Cursor cursor = getContentResolver().query(
                SHOPPING_LIST_URI,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            int recipeIdIndex = cursor.getColumnIndex(ShoppingList.COLUMN_ID);
            int recipeNameIndex = cursor.getColumnIndex(ShoppingList.COLUMN_RECIPE_NAME);
            int shoppingListItemsIndex = cursor.getColumnIndex(ShoppingList.COLUMN_SHOPPING_LIST);

            int recipeId = cursor.getInt(recipeIdIndex);
            String recipeName = cursor.getString(recipeNameIndex);
            String shopping = cursor.getString(shoppingListItemsIndex);
            cursor.close();
            shoppingList = new ShoppingList(recipeId,recipeName,shopping);
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, ShoppingListAppWidgetProvider.class));

        ShoppingListAppWidgetProvider.updateShoppingListWidgets(this, appWidgetManager, shoppingList, appWidgetIds);
    }
}
