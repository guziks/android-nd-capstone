package ua.com.elius.familycart.family;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;

import ua.com.elius.familycart.data.person.PersonColumns;
import ua.com.elius.familycart.data.person.PersonContentValues;
import ua.com.elius.familycart.data.person.PersonSelection;

public class SelectPersonAsyncTask extends AsyncTask<Pair<ContentValues, PersonSelection>, Void, Void> {

    private Context mContext;

    public SelectPersonAsyncTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(Pair<ContentValues, PersonSelection>... params) {
        ContentValues values = params[0].first;
        PersonSelection where = params[0].second;

        mContext.getContentResolver().update(PersonColumns.CONTENT_URI, values, where.sel(), where.args());

        return null;
    }
}
