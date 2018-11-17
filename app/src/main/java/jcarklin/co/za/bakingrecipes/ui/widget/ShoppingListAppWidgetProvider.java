package jcarklin.co.za.bakingrecipes.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.widget.RemoteViews;

import java.util.List;

import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.repository.model.ShoppingList;
import jcarklin.co.za.bakingrecipes.service.WidgetUpdateService;

public class ShoppingListAppWidgetProvider extends AppWidgetProvider {

    public static void updateShoppingListWidget(Context context, AppWidgetManager appWidgetManager,
                                                ShoppingList shoppingList, int appWidgetId) {

        Intent refreshIntent = new Intent(context, WidgetUpdateService.class);
        refreshIntent.setAction(WidgetUpdateService.ACTION_REFRESH);
        PendingIntent refreshPendingIntent = PendingIntent.getService(context, 0, refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.shopping_list);

        if (shoppingList != null) {
            views.setTextViewText(R.id.tv_recipe_title, shoppingList.getRecipeName());
            views.setTextViewText(R.id.tv_shopping_list, Html.fromHtml(shoppingList.getShoppingList()));
        }

        views.setOnClickPendingIntent(R.id.tv_refresh,refreshPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        WidgetUpdateService.startActionRefreshShoppingList(context);
    }

    public static void updateShoppingListWidgets(Context context, AppWidgetManager appWidgetManager,
                                                 List<ShoppingList> shoppingLists, int[] appWidgetIds) {
        ShoppingList shoppingList;
        int appWidgetId;
        for (int i=0; i<appWidgetIds.length; i++ ) {
            appWidgetId = appWidgetIds[i];
            if(shoppingLists.size()>=i) {
                shoppingList = shoppingLists.get(i);
            } else {
                shoppingList = shoppingLists.get(shoppingLists.size()-1);
            }
            updateShoppingListWidget(context, appWidgetManager, shoppingList, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {

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

