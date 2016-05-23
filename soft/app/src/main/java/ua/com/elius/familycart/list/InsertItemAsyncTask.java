package ua.com.elius.familycart.list;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;

import ua.com.elius.familycart.data.item.ItemColumns;
import ua.com.elius.familycart.data.item.ItemSelection;

public class InsertItemAsyncTask extends AsyncTask<ContentValues, Void, Void> {

    private Context mContext;

    public InsertItemAsyncTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(ContentValues... params) {
        ContentValues values = params[0];

        mContext.getContentResolver().insert(ItemColumns.CONTENT_URI, values);

        return null;
    }
}