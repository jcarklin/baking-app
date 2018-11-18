package jcarklin.co.za.bakingrecipes.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import jcarklin.co.za.bakingrecipes.repository.db.BakingAppDao;
import jcarklin.co.za.bakingrecipes.repository.db.BakingAppDatabase;
import jcarklin.co.za.bakingrecipes.repository.model.ShoppingList;

public class BakingAppContentProvider extends ContentProvider {

    public static final int SHOPPING_LISTS = 100;
    public static final int SHOPPING_LIST_WITH_ID = 101;
    private static final UriMatcher URI_MATCHER = buildUriMatcher();

    public BakingAppContentProvider() {
    }

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(BakingAppContract.AUTHORITY, ShoppingList.TABLE_NAME, SHOPPING_LISTS);
        uriMatcher.addURI(BakingAppContract.AUTHORITY, ShoppingList.TABLE_NAME + "/#", SHOPPING_LIST_WITH_ID);
        return uriMatcher;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        int match = URI_MATCHER.match(uri);
        if (match==SHOPPING_LISTS || match==SHOPPING_LIST_WITH_ID) {
            Context context = getContext();
            if (context != null) {
                BakingAppDao bakingAppDao = BakingAppDatabase.getInstance(context).bakingAppDao();
                if (match == SHOPPING_LISTS) {
                    cursor = bakingAppDao.getShoppingListsCursor();
                } else {
                    cursor = bakingAppDao.getShoppingList(ContentUris.parseId(uri));
                }
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
            }

        }
        return cursor;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int match = URI_MATCHER.match(uri);
        int shoppingListsDeleted;
        Context context = getContext();
        BakingAppDao bakingAppDao = BakingAppDatabase.getInstance(context).bakingAppDao();
        int recordsDeleted = 0;
        switch (match) {
            // Handle the single item case, recognized by the ID included in the URI path
            case SHOPPING_LIST_WITH_ID:
                bakingAppDao.deleteShoppingListById(ContentUris.parseId(uri));
            case SHOPPING_LISTS:
                bakingAppDao.clearShoppingList();
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (recordsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return recordsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        return false;
    }



    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
