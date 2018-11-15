package jcarklin.co.za.bakingrecipes.ui.shoppinglist;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.service.ShoppingListWidgetService;
import jcarklin.co.za.bakingrecipes.ui.recipecards.MainActivity;

public class ShoppingListWidgetProvider extends AppWidgetProvider {
//todo add clear shopping list
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                CharSequence shoppinglist, int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.shopping_list_widget);
        views.setTextViewText(R.id.tv_shopping_list, shoppinglist);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        ShoppingListWidgetService.startActionUpdateShoppingListWidgets(context);
        /**
         * The onUpdate() lifecycle method is called every time:
         *
         * The update interval has elapsed.
         * The user performs an action that triggers the onUpdate() method.
         * The user places a new instance of the widget on their homescreen (unless your widget contains a configuration Activity, which we’ll be covering in part two).
         * The onUpdate() lifecycle method is also called in response to ACTION_APPWIDGET_RESTORED, which is broadcast whenever a widget is restored from backup.
         *
         * For most projects, the onUpdate() method will contain the bulk of the widget provider code, especially since it’s also where you register your widget’s event handlers.
         */
    }

    public static void updateShoppingListWidgets(Context context, AppWidgetManager appWidgetManager,
                                                 CharSequence shoppinglist, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, shoppinglist, appWidgetId);
        }
    }

//    @Override
//    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equals(ShoppingListWidgetService.ACTION_UPDATE_SHOPPING_LIST)) {
//            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, ShoppingListWidgetProvider.class));
//            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.tv_shopping_list);
//        }
//        super.onReceive(context, intent);
//    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        // Todo delete The onEnabled() lifecycle method is called in response to ACTION_APPWIDGET_ENABLED, which is broadcast when the user adds the first instance of your widget to their homescreen. If the user creates two instances of your widget, then onEnabled() is called for the first instance, but not for the second.
        //This lifecycle method is where you perform any setup that only needs to occur once for all widget instances, such as creating a database or setting up a service.
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

