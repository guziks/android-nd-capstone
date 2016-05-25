package ua.com.elius.familycart.list;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;

import ua.com.elius.familycart.data.item.ItemColumns;
import ua.com.elius.familycart.data.item.ItemCursor;
import ua.com.elius.familycart.data.item.ItemSelection;

public class FetchMaxCustomOrderAsyncTask extends AsyncTask<Void,Void,Void> {

    public static final String MAX_ORDER = "highest_order";
    public static final String VALUE_RECEIVED = "value_received";

    private Context mContext;
    private Bundle mResultDestination;

    public FetchMaxCustomOrderAsyncTask(Context context, Bundle resultDestination) {
        this.mContext = context;
        this.mResultDestination = resultDestination;
    }

    @Override
    protected Void doInBackground(Void... params) {

        ItemCursor cursor = new ItemSelection().query(mContext,
                new String[]{ItemColumns.CUSTOM_ORDER});

        ArrayList<Integer> orderList = new ArrayList<>();

        while (cursor.moveToNext()) {
            orderList.add(cursor.getCustomOrder());
        }

        mResultDestination.putInt(MAX_ORDER, Collections.max(orderList));
        mResultDestination.putBoolean(VALUE_RECEIVED, true);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
