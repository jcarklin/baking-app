package jcarklin.co.za.bakingrecipes.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetListViewService extends RemoteViewsService {

    public WidgetListViewService() {

    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetListViewsFactory (this.getApplicationContext());
    }



}

