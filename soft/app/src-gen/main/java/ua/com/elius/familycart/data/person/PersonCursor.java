package ua.com.elius.familycart.data.person;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ua.com.elius.familycart.data.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code person} table.
 */
public class PersonCursor extends AbstractCursor implements PersonModel {
    public PersonCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(PersonColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Google ID. Corresponds to gms...Person.getId().
     * Can be {@code null}.
     */
    @Nullable
    public String getGid() {
        String res = getStringOrNull(PersonColumns.GID);
        return res;
    }

    /**
     * Member name. Corresponds to gms...Person.getDisplayName().
     * Can be {@code null}.
     */
    @Nullable
    public String getDisplayName() {
        String res = getStringOrNull(PersonColumns.DISPLAY_NAME);
        return res;
    }

    /**
     * Photo, avatar, etc. Corresponds to gms...Person.getImage().
     * Can be {@code null}.
     */
    @Nullable
    public String getImageUrl() {
        String res = getStringOrNull(PersonColumns.IMAGE_URL);
        return res;
    }

    /**
     * Is this person selected for sharing.
     * Can be {@code null}.
     */
    @Nullable
    public Boolean getSharingToAllowed() {
        Boolean res = getBooleanOrNull(PersonColumns.SHARING_TO_ALLOWED);
        return res;
    }

    /**
     * Does this person selected me for sharing
     * Can be {@code null}.
     */
    @Nullable
    public Boolean getSharingFromAllowed() {
        Boolean res = getBooleanOrNull(PersonColumns.SHARING_FROM_ALLOWED);
        return res;
    }

    /**
     * Last modification timestamp.
     * Can be {@code null}.
     */
    @Nullable
    public Long getTimeModified() {
        Long res = getLongOrNull(PersonColumns.TIME_MODIFIED);
        return res;
    }
}
