package ua.com.elius.familycart.data;

import java.util.Arrays;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import ua.com.elius.familycart.BuildConfig;
import ua.com.elius.familycart.data.base.BaseContentProvider;
import ua.com.elius.familycart.data.item.ItemColumns;
import ua.com.elius.familycart.data.person.PersonColumns;

public class ListProvider extends BaseContentProvider {
    private static final String TAG = ListProvider.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "ua.com.elius.familycart.data";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    private static final int URI_TYPE_ITEM = 0;
    private static final int URI_TYPE_ITEM_ID = 1;

    private static final int URI_TYPE_PERSON = 2;
    private static final int URI_TYPE_PERSON_ID = 3;



    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, ItemColumns.TABLE_NAME, URI_TYPE_ITEM);
        URI_MATCHER.addURI(AUTHORITY, ItemColumns.TABLE_NAME + "/#", URI_TYPE_ITEM_ID);
        URI_MATCHER.addURI(AUTHORITY, PersonColumns.TABLE_NAME, URI_TYPE_PERSON);
        URI_MATCHER.addURI(AUTHORITY, PersonColumns.TABLE_NAME + "/#", URI_TYPE_PERSON_ID);
    }

    @Override
    protected SQLiteOpenHelper createSqLiteOpenHelper() {
        return ListSQLiteOpenHelper.getInstance(getContext());
    }

    @Override
    protected boolean hasDebug() {
        return DEBUG;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_ITEM:
                return TYPE_CURSOR_DIR + ItemColumns.TABLE_NAME;
            case URI_TYPE_ITEM_ID:
                return TYPE_CURSOR_ITEM + ItemColumns.TABLE_NAME;

            case URI_TYPE_PERSON:
                return TYPE_CURSOR_DIR + PersonColumns.TABLE_NAME;
            case URI_TYPE_PERSON_ID:
                return TYPE_CURSOR_ITEM + PersonColumns.TABLE_NAME;

        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        return super.insert(uri, values);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        return super.bulkInsert(uri, values);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.update(uri, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.delete(uri, selection, selectionArgs);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + uri.getQueryParameter(QUERY_GROUP_BY) + " having=" + uri.getQueryParameter(QUERY_HAVING) + " limit=" + uri.getQueryParameter(QUERY_LIMIT));
        return super.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    protected QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_ITEM:
            case URI_TYPE_ITEM_ID:
                res.table = ItemColumns.TABLE_NAME;
                res.idColumn = ItemColumns._ID;
                res.tablesWithJoins = ItemColumns.TABLE_NAME;
                res.orderBy = ItemColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_PERSON:
            case URI_TYPE_PERSON_ID:
                res.table = PersonColumns.TABLE_NAME;
                res.idColumn = PersonColumns._ID;
                res.tablesWithJoins = PersonColumns.TABLE_NAME;
                res.orderBy = PersonColumns.DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_ITEM_ID:
            case URI_TYPE_PERSON_ID:
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + res.idColumn + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }
}
