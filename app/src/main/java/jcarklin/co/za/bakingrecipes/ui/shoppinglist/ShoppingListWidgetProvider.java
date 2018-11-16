package jcarklin.co.za.bakingrecipes.ui.shoppinglist;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.service.WidgetListViewService;
import jcarklin.co.za.bakingrecipes.service.WidgetUpdateService;

public class ShoppingListWidgetProvider extends AppWidgetProvider {

    //todo add clear shopping list
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_shopping_list);
        Intent intent = new Intent(context, WidgetListViewService.class);
        views.setTextViewText(R.id.tv_refresh, "Refresh!!!!!");
        views.setRemoteAdapter(R.id.lv_shopping_lists, intent);
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    public static void updateAllAppWidget(Context context, AppWidgetManager appWidgetManager,
                                          int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        WidgetUpdateService.startActionUpdateListView(context);
    }




    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {

    }
}

