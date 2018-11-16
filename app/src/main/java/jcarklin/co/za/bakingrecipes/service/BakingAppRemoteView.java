package jcarklin.co.za.bakingrecipes.service;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.repository.model.ShoppingList;

public class BakingAppRemoteView implements RemoteViewsService.RemoteViewsFactory {

    private List<ShoppingList> shoppingLists;
    private Context context;

    public BakingAppRemoteView(Context applicationContext, List<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
        this.context = applicationContext;
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
        RemoteViews remoteViews =
                new RemoteViews(context.getPackageName(), R.layout.item_shopping_list);

        ShoppingList shoppingList = shoppingLists.get(i);
        remoteViews.setTextViewText(R.id.tv_recipe_name, shoppingList.getRecipeName());
        remoteViews.setTextViewText(R.id.tv_shopping_list, shoppingList.getShoppingList());

        return remoteViews;
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
