package ua.com.elius.familycart.list;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import ua.com.elius.familycart.LeftToBuyCountWidget;
import ua.com.elius.familycart.data.item.ItemColumns;

public class InsertItemAsyncTask extends AsyncTask<ContentValues, Void, Void> {

    private Context mContext;

    public InsertItemAsyncTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(ContentValues... params) {
        ContentValues values = params[0];

        mContext.getContentResolver().insert(ItemColumns.CONTENT_URI, values);

        mContext.sendBroadcast(new Intent(LeftToBuyCountWidget.ACTION_WIDGET_UPDATE));

        return null;
    }
}