package jcarklin.co.za.bakingrecipes.service;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.ui.shoppinglist.ShoppingListWidgetProvider;


public class WidgetUpdateService extends IntentService {

    private static final String ACTION_UPDATE_LISTVIEW = "jcarklin.co.za.bakingrecipes.service.action.update_widget_listview";

    public WidgetUpdateService() {
        super("WidgetUpdateService");
    }

    public static void startActionUpdateListView(Context context) {
        Intent intent = new Intent(context, WidgetUpdateService.class);
        intent.setAction(ACTION_UPDATE_LISTVIEW);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            handleActionUpdateListView();
        }
    }

    private void handleActionUpdateListView() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, ShoppingListWidgetProvider.class));
        ShoppingListWidgetProvider.updateAllAppWidget(this, appWidgetManager,appWidgetIds);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_shopping_lists);
    }
}
