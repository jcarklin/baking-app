package jcarklin.co.za.bakingrecipes.service;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.text.Html;

import jcarklin.co.za.bakingrecipes.repository.BakingAppRepository;
import jcarklin.co.za.bakingrecipes.ui.shoppinglist.ShoppingListWidgetProvider;

public class ShoppingListWidgetService extends IntentService {

    private static final String ACTION_UPDATE_SHOPPING_LIST = "jcarklin.co.za.bakingrecipes.service.action.update_shopping_list";

    public ShoppingListWidgetService() {
        super("ShoppingListWidgetService");
    }

    public static void startActionUpdateShoppingListWidgets(Context context) {
        Intent intent = new Intent(context, ShoppingListWidgetService.class);
        intent.setAction(ACTION_UPDATE_SHOPPING_LIST);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            BakingAppRepository bakingAppRepository = BakingAppRepository.getInstance(getApplication());
            String shoppingListhtml = bakingAppRepository.getShoppingList();
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, ShoppingListWidgetProvider.class));
            ShoppingListWidgetProvider.updateShoppingListWidgets(this, appWidgetManager, Html.fromHtml(shoppingListhtml), appWidgetIds);
        }
    }

}
