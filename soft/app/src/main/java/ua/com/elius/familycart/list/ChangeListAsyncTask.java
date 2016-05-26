package ua.com.elius.familycart.list;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import java.util.concurrent.ExecutionException;

import ua.com.elius.familycart.LeftToBuyCountWidget;
import ua.com.elius.familycart.data.item.ItemColumns;
import ua.com.elius.familycart.data.item.ItemSelection;
import ua.com.elius.familycart.data.item.List;

public class ChangeListAsyncTask extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "ChangeListAsyncTask";

    private Context mContext;
    private ContentValues mValues;
    private ItemSelection mWhere;
    private List mTargetList;
    private Integer mMaxOrder = 0;

    public ChangeListAsyncTask(Context context, ContentValues values, ItemSelection where, List targetList) {
        mContext = context;
        mValues = values;
        mWhere = where;
        mTargetList = targetList;

        try {
            mMaxOrder = new FetchMaxCustomOrderAsyncTask(mContext, mTargetList).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    protected Void doInBackground(Void... params) {

        mValues.put(ItemColumns.CUSTOM_ORDER, ++mMaxOrder);

        mContext.getContentResolver().update(ItemColumns.CONTENT_URI, mValues, mWhere.sel(), mWhere.args());

        mContext.sendBroadcast(new Intent(LeftToBuyCountWidget.ACTION_WIDGET_UPDATE));

        return null;
    }
}
