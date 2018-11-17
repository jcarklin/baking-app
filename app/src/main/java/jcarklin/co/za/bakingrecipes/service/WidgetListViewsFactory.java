package jcarklin.co.za.bakingrecipes.service;

import android.content.Context;
import android.text.Html;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.repository.model.ShoppingList;

public class WidgetListViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<ShoppingList> shoppingLists;

    public WidgetListViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return shoppingLists.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.shopping_list_item);

        views.setTextViewText(R.id.tv_recipe_title, shoppingLists.get(i).getRecipeName());
        views.setTextViewText(R.id.tv_shopping_list, Html.fromHtml(shoppingLists.get(i).getShoppingList()));

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
