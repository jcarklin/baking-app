package jcarklin.co.za.bakingrecipes.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import java.util.List;

import jcarklin.co.za.bakingrecipes.repository.BakingAppRepository;
import jcarklin.co.za.bakingrecipes.repository.model.ShoppingList;

public class WidgetListViewService extends RemoteViewsService {

     @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
         BakingAppRepository bakingAppRepository = BakingAppRepository.getInstance(getApplication());
         List<ShoppingList> shoppingLists = bakingAppRepository.getShoppingLists();
         return new BakingAppRemoteView(this.getApplicationContext(), shoppingLists);
    }

}

