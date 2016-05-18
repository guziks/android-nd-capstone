package ua.com.elius.familycart.data.person;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import ua.com.elius.familycart.data.base.AbstractSelection;

/**
 * Selection for the {@code person} table.
 */
public class PersonSelection extends AbstractSelection<PersonSelection> {
    @Override
    protected Uri baseUri() {
        return PersonColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code PersonCursor} object, which is positioned before the first entry, or null.
     */
    public PersonCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new PersonCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public PersonCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code PersonCursor} object, which is positioned before the first entry, or null.
     */
    public PersonCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new PersonCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public PersonCursor query(Context context) {
        return query(context, null);
    }


    public PersonSelection id(long... value) {
        addEquals("person." + PersonColumns._ID, toObjectArray(value));
        return this;
    }

    public PersonSelection idNot(long... value) {
        addNotEquals("person." + PersonColumns._ID, toObjectArray(value));
        return this;
    }

    public PersonSelection orderById(boolean desc) {
        orderBy("person." + PersonColumns._ID, desc);
        return this;
    }

    public PersonSelection orderById() {
        return orderById(false);
    }

    public PersonSelection gid(String... value) {
        addEquals(PersonColumns.GID, value);
        return this;
    }

    public PersonSelection gidNot(String... value) {
        addNotEquals(PersonColumns.GID, value);
        return this;
    }

    public PersonSelection gidLike(String... value) {
        addLike(PersonColumns.GID, value);
        return this;
    }

    public PersonSelection gidContains(String... value) {
        addContains(PersonColumns.GID, value);
        return this;
    }

    public PersonSelection gidStartsWith(String... value) {
        addStartsWith(PersonColumns.GID, value);
        return this;
    }

    public PersonSelection gidEndsWith(String... value) {
        addEndsWith(PersonColumns.GID, value);
        return this;
    }

    public PersonSelection orderByGid(boolean desc) {
        orderBy(PersonColumns.GID, desc);
        return this;
    }

    public PersonSelection orderByGid() {
        orderBy(PersonColumns.GID, false);
        return this;
    }

    public PersonSelection displayName(String... value) {
        addEquals(PersonColumns.DISPLAY_NAME, value);
        return this;
    }

    public PersonSelection displayNameNot(String... value) {
        addNotEquals(PersonColumns.DISPLAY_NAME, value);
        return this;
    }

    public PersonSelection displayNameLike(String... value) {
        addLike(PersonColumns.DISPLAY_NAME, value);
        return this;
    }

    public PersonSelection displayNameContains(String... value) {
        addContains(PersonColumns.DISPLAY_NAME, value);
        return this;
    }

    public PersonSelection displayNameStartsWith(String... value) {
        addStartsWith(PersonColumns.DISPLAY_NAME, value);
        return this;
    }

    public PersonSelection displayNameEndsWith(String... value) {
        addEndsWith(PersonColumns.DISPLAY_NAME, value);
        return this;
    }

    public PersonSelection orderByDisplayName(boolean desc) {
        orderBy(PersonColumns.DISPLAY_NAME, desc);
        return this;
    }

    public PersonSelection orderByDisplayName() {
        orderBy(PersonColumns.DISPLAY_NAME, false);
        return this;
    }

    public PersonSelection imageUrl(String... value) {
        addEquals(PersonColumns.IMAGE_URL, value);
        return this;
    }

    public PersonSelection imageUrlNot(String... value) {
        addNotEquals(PersonColumns.IMAGE_URL, value);
        return this;
    }

    public PersonSelection imageUrlLike(String... value) {
        addLike(PersonColumns.IMAGE_URL, value);
        return this;
    }

    public PersonSelection imageUrlContains(String... value) {
        addContains(PersonColumns.IMAGE_URL, value);
        return this;
    }

    public PersonSelection imageUrlStartsWith(String... value) {
        addStartsWith(PersonColumns.IMAGE_URL, value);
        return this;
    }

    public PersonSelection imageUrlEndsWith(String... value) {
        addEndsWith(PersonColumns.IMAGE_URL, value);
        return this;
    }

    public PersonSelection orderByImageUrl(boolean desc) {
        orderBy(PersonColumns.IMAGE_URL, desc);
        return this;
    }

    public PersonSelection orderByImageUrl() {
        orderBy(PersonColumns.IMAGE_URL, false);
        return this;
    }

    public PersonSelection sharingToAllowed(boolean value) {
        addEquals(PersonColumns.SHARING_TO_ALLOWED, toObjectArray(value));
        return this;
    }

    public PersonSelection orderBySharingToAllowed(boolean desc) {
        orderBy(PersonColumns.SHARING_TO_ALLOWED, desc);
        return this;
    }

    public PersonSelection orderBySharingToAllowed() {
        orderBy(PersonColumns.SHARING_TO_ALLOWED, false);
        return this;
    }

    public PersonSelection sharingFromAllowed(boolean value) {
        addEquals(PersonColumns.SHARING_FROM_ALLOWED, toObjectArray(value));
        return this;
    }

    public PersonSelection orderBySharingFromAllowed(boolean desc) {
        orderBy(PersonColumns.SHARING_FROM_ALLOWED, desc);
        return this;
    }

    public PersonSelection orderBySharingFromAllowed() {
        orderBy(PersonColumns.SHARING_FROM_ALLOWED, false);
        return this;
    }

    public PersonSelection timeModified(Long... value) {
        addEquals(PersonColumns.TIME_MODIFIED, value);
        return this;
    }

    public PersonSelection timeModifiedNot(Long... value) {
        addNotEquals(PersonColumns.TIME_MODIFIED, value);
        return this;
    }

    public PersonSelection timeModifiedGt(long value) {
        addGreaterThan(PersonColumns.TIME_MODIFIED, value);
        return this;
    }

    public PersonSelection timeModifiedGtEq(long value) {
        addGreaterThanOrEquals(PersonColumns.TIME_MODIFIED, value);
        return this;
    }

    public PersonSelection timeModifiedLt(long value) {
        addLessThan(PersonColumns.TIME_MODIFIED, value);
        return this;
    }

    public PersonSelection timeModifiedLtEq(long value) {
        addLessThanOrEquals(PersonColumns.TIME_MODIFIED, value);
        return this;
    }

    public PersonSelection orderByTimeModified(boolean desc) {
        orderBy(PersonColumns.TIME_MODIFIED, desc);
        return this;
    }

    public PersonSelection orderByTimeModified() {
        orderBy(PersonColumns.TIME_MODIFIED, false);
        return this;
    }
}
