package ua.com.elius.familycart.data.person;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ua.com.elius.familycart.data.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code person} table.
 */
public class PersonContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return PersonColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable PersonSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable PersonSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Google ID. Corresponds to gms...Person.getId().
     */
    public PersonContentValues putGid(@Nullable String value) {
        mContentValues.put(PersonColumns.GID, value);
        return this;
    }

    public PersonContentValues putGidNull() {
        mContentValues.putNull(PersonColumns.GID);
        return this;
    }

    /**
     * Member name. Corresponds to gms...Person.getDisplayName().
     */
    public PersonContentValues putDisplayName(@Nullable String value) {
        mContentValues.put(PersonColumns.DISPLAY_NAME, value);
        return this;
    }

    public PersonContentValues putDisplayNameNull() {
        mContentValues.putNull(PersonColumns.DISPLAY_NAME);
        return this;
    }

    /**
     * Photo, avatar, etc. Corresponds to gms...Person.getImage().
     */
    public PersonContentValues putImageUrl(@Nullable String value) {
        mContentValues.put(PersonColumns.IMAGE_URL, value);
        return this;
    }

    public PersonContentValues putImageUrlNull() {
        mContentValues.putNull(PersonColumns.IMAGE_URL);
        return this;
    }

    /**
     * Is this person selected for sharing.
     */
    public PersonContentValues putSharingToAllowed(boolean value) {
        mContentValues.put(PersonColumns.SHARING_TO_ALLOWED, value);
        return this;
    }


    /**
     * Does this person selected me for sharing
     */
    public PersonContentValues putSharingFromAllowed(boolean value) {
        mContentValues.put(PersonColumns.SHARING_FROM_ALLOWED, value);
        return this;
    }


    /**
     * Last modification timestamp.
     */
    public PersonContentValues putTimeModified(@Nullable Long value) {
        mContentValues.put(PersonColumns.TIME_MODIFIED, value);
        return this;
    }

    public PersonContentValues putTimeModifiedNull() {
        mContentValues.putNull(PersonColumns.TIME_MODIFIED);
        return this;
    }
}
