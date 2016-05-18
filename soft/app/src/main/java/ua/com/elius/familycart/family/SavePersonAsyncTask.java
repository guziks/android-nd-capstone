package ua.com.elius.familycart.family;

import android.content.ContentResolver;
import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.plus.model.people.PersonBuffer;

import ua.com.elius.familycart.data.person.PersonColumns;
import ua.com.elius.familycart.data.person.PersonContentValues;
import ua.com.elius.familycart.data.person.PersonCursor;
import ua.com.elius.familycart.data.person.PersonSelection;

public class SavePersonAsyncTask extends AsyncTask<PersonBuffer, Void, Void> {

    Context mContext;

    public SavePersonAsyncTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(PersonBuffer... params) {
        PersonBuffer personBuffer = params[0];

        try {
            int count = personBuffer.getCount();
            for (int i = 0; i < count; i++) {
                saveToContentProvider(personBuffer, i);
            }
        } finally {
            personBuffer.release();
        }

        return null;
    }

    private void saveToContentProvider(PersonBuffer personBuffer, int i) {
        ContentResolver resolver = mContext.getContentResolver();

        String id = personBuffer.get(i).getId();
        String displayName = personBuffer.get(i).getDisplayName();
        String imageUrl = personBuffer.get(i).getImage().getUrl();

        PersonContentValues newPersonData = new PersonContentValues()
                .putGid(id)
                .putDisplayName(displayName)
                .putImageUrl(imageUrl);

        PersonSelection whereSameId = new PersonSelection().gid(id);
        PersonCursor currentPersonData = whereSameId.query(resolver);
        if (currentPersonData.getCount() > 0) {
            currentPersonData.moveToFirst();
            newPersonData.putSharingFromAllowed(currentPersonData.getSharingFromAllowed());
            newPersonData.putSharingToAllowed(currentPersonData.getSharingToAllowed());
            newPersonData.putTimeModified(currentPersonData.getTimeModified());
        }

        resolver.insert(PersonColumns.CONTENT_URI, newPersonData.values());
    }
}
