package ua.com.elius.familycart.list;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import ua.com.elius.familycart.data.item.ItemColumns;
import ua.com.elius.familycart.data.item.ItemCursor;
import ua.com.elius.familycart.data.item.ItemSelection;
import ua.com.elius.familycart.data.item.List;

public class FetchMaxCustomOrderAsyncTask extends AsyncTask<Void,Void,Void> {

    public static final String MAX_ORDER = "highest_order";
    public static final String VALUE_RECEIVED = "value_received";

    private Context mContext;
    private List mList;
    private Bundle mResultDestination;

    public FetchMaxCustomOrderAsyncTask(Context context, List list, Bundle resultDestination) {
        mContext = context;
        mList = list;
        mResultDestination = resultDestination;
    }

    @Override
    protected Void doInBackground(Void... params) {

        ItemSelection where = new ItemSelection().list(mList).orderByCustomOrder(true);
        ItemCursor cursor = where.query(mContext, new String[]{ItemColumns.CUSTOM_ORDER});

        if (cursor != null && cursor.moveToFirst() && cursor.getCustomOrder() != null) {
            mResultDestination.putInt(MAX_ORDER, cursor.getCustomOrder());
        } else {
            mResultDestination.putInt(MAX_ORDER, 0);
        }
        mResultDestination.putBoolean(VALUE_RECEIVED, true);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
