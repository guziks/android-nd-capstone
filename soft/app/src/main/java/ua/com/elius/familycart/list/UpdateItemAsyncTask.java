package ua.com.elius.familycart.list;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import ua.com.elius.familycart.LeftToBuyCountWidget;

public class UpdateItemAsyncTask extends AsyncTask<Void, Void, Void> {

    private Context mContext;
    private ContentValues mValues;
    private Uri mItemUri;
    private String mWhere;
    private String[] mSelectionArgs;

    public UpdateItemAsyncTask(Context context, ContentValues values, Uri itemUri) {
        mContext = context;
        mValues = values;
        mItemUri = itemUri;
    }

    @Override
    protected Void doInBackground(Void... params) {
        mContext.getContentResolver().update(mItemUri, mValues, null, null);
        mContext.sendBroadcast(new Intent(LeftToBuyCountWidget.ACTION_WIDGET_UPDATE));
        return null;
    }
}
