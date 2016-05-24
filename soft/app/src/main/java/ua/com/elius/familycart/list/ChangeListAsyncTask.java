package ua.com.elius.familycart.list;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Pair;

import ua.com.elius.familycart.LeftToBuyCountWidget;
import ua.com.elius.familycart.data.item.ItemColumns;
import ua.com.elius.familycart.data.item.ItemSelection;

public class ChangeListAsyncTask extends AsyncTask<Pair<ContentValues, ItemSelection>, Void, Void> {

    private Context mContext;

    public ChangeListAsyncTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(Pair<ContentValues, ItemSelection>... params) {
        ContentValues values = params[0].first;
        ItemSelection where = params[0].second;

        mContext.getContentResolver().update(ItemColumns.CONTENT_URI, values, where.sel(), where.args());

        mContext.sendBroadcast(new Intent(LeftToBuyCountWidget.ACTION_WIDGET_UPDATE));

        return null;
    }
}
