package ua.com.elius.familycart.data;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import ua.com.elius.familycart.BuildConfig;
import ua.com.elius.familycart.data.item.ItemColumns;
import ua.com.elius.familycart.data.person.PersonColumns;

public class ListSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = ListSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "main.db";
    private static final int DATABASE_VERSION = 1;
    private static ListSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final ListSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_ITEM = "CREATE TABLE IF NOT EXISTS "
            + ItemColumns.TABLE_NAME + " ( "
            + ItemColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ItemColumns.TITLE + " TEXT, "
            + ItemColumns.QUANTITY + " TEXT, "
            + ItemColumns.DESCRIPTION + " TEXT, "
            + ItemColumns.LIST + " INTEGER, "
            + ItemColumns.CUSTOM_ORDER + " INTEGER, "
            + ItemColumns.TIME_CREATED + " INTEGER, "
            + ItemColumns.TIME_MODIFIED + " INTEGER, "
            + ItemColumns.TIME_BOUGHT + " INTEGER "
            + " );";

    public static final String SQL_CREATE_TABLE_PERSON = "CREATE TABLE IF NOT EXISTS "
            + PersonColumns.TABLE_NAME + " ( "
            + PersonColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PersonColumns.GID + " TEXT, "
            + PersonColumns.DISPLAY_NAME + " TEXT, "
            + PersonColumns.IMAGE_URL + " TEXT, "
            + PersonColumns.SHARING_TO_ALLOWED + " INTEGER, "
            + PersonColumns.SHARING_FROM_ALLOWED + " INTEGER, "
            + PersonColumns.TIME_MODIFIED + " INTEGER "
            + ", CONSTRAINT unique_gid UNIQUE (gid) ON CONFLICT REPLACE"
            + " );";

    // @formatter:on

    public static ListSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static ListSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static ListSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new ListSQLiteOpenHelper(context);
    }

    private ListSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new ListSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static ListSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new ListSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private ListSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new ListSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_ITEM);
        db.execSQL(SQL_CREATE_TABLE_PERSON);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
