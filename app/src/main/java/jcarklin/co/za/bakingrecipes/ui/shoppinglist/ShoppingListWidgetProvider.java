package jcarklin.co.za.bakingrecipes.ui.shoppinglist;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.service.ShoppingListWidgetService;
import jcarklin.co.za.bakingrecipes.ui.recipecards.MainActivity;

public class ShoppingListWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                CharSequence shoppinglist, int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.shopping_list_widget);
        views.setTextViewText(R.id.tv_shopping_list, shoppinglist);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        ShoppingListWidgetService.startActionUpdateShoppingListWidgets(context);
    }

    public static void updateShoppingListWidgets(Context context, AppWidgetManager appWidgetManager,
                                                 CharSequence shoppinglist, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, shoppinglist, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

