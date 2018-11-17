package jcarklin.co.za.bakingrecipes.provider;

import android.net.Uri;

import jcarklin.co.za.bakingrecipes.repository.model.ShoppingList;

public class BakingAppContract {

    public static final String AUTHORITY = "jcarklin.co.za.bakingrecipes";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY+"/"+ShoppingList.TABLE_NAME);




}
