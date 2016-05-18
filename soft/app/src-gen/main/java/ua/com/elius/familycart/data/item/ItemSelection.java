package ua.com.elius.familycart.data.item;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import ua.com.elius.familycart.data.base.AbstractSelection;

/**
 * Selection for the {@code item} table.
 */
public class ItemSelection extends AbstractSelection<ItemSelection> {
    @Override
    protected Uri baseUri() {
        return ItemColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code ItemCursor} object, which is positioned before the first entry, or null.
     */
    public ItemCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new ItemCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public ItemCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code ItemCursor} object, which is positioned before the first entry, or null.
     */
    public ItemCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new ItemCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public ItemCursor query(Context context) {
        return query(context, null);
    }


    public ItemSelection id(long... value) {
        addEquals("item." + ItemColumns._ID, toObjectArray(value));
        return this;
    }

    public ItemSelection idNot(long... value) {
        addNotEquals("item." + ItemColumns._ID, toObjectArray(value));
        return this;
    }

    public ItemSelection orderById(boolean desc) {
        orderBy("item." + ItemColumns._ID, desc);
        return this;
    }

    public ItemSelection orderById() {
        return orderById(false);
    }

    public ItemSelection title(String... value) {
        addEquals(ItemColumns.TITLE, value);
        return this;
    }

    public ItemSelection titleNot(String... value) {
        addNotEquals(ItemColumns.TITLE, value);
        return this;
    }

    public ItemSelection titleLike(String... value) {
        addLike(ItemColumns.TITLE, value);
        return this;
    }

    public ItemSelection titleContains(String... value) {
        addContains(ItemColumns.TITLE, value);
        return this;
    }

    public ItemSelection titleStartsWith(String... value) {
        addStartsWith(ItemColumns.TITLE, value);
        return this;
    }

    public ItemSelection titleEndsWith(String... value) {
        addEndsWith(ItemColumns.TITLE, value);
        return this;
    }

    public ItemSelection orderByTitle(boolean desc) {
        orderBy(ItemColumns.TITLE, desc);
        return this;
    }

    public ItemSelection orderByTitle() {
        orderBy(ItemColumns.TITLE, false);
        return this;
    }

    public ItemSelection quantity(String... value) {
        addEquals(ItemColumns.QUANTITY, value);
        return this;
    }

    public ItemSelection quantityNot(String... value) {
        addNotEquals(ItemColumns.QUANTITY, value);
        return this;
    }

    public ItemSelection quantityLike(String... value) {
        addLike(ItemColumns.QUANTITY, value);
        return this;
    }

    public ItemSelection quantityContains(String... value) {
        addContains(ItemColumns.QUANTITY, value);
        return this;
    }

    public ItemSelection quantityStartsWith(String... value) {
        addStartsWith(ItemColumns.QUANTITY, value);
        return this;
    }

    public ItemSelection quantityEndsWith(String... value) {
        addEndsWith(ItemColumns.QUANTITY, value);
        return this;
    }

    public ItemSelection orderByQuantity(boolean desc) {
        orderBy(ItemColumns.QUANTITY, desc);
        return this;
    }

    public ItemSelection orderByQuantity() {
        orderBy(ItemColumns.QUANTITY, false);
        return this;
    }

    public ItemSelection description(String... value) {
        addEquals(ItemColumns.DESCRIPTION, value);
        return this;
    }

    public ItemSelection descriptionNot(String... value) {
        addNotEquals(ItemColumns.DESCRIPTION, value);
        return this;
    }

    public ItemSelection descriptionLike(String... value) {
        addLike(ItemColumns.DESCRIPTION, value);
        return this;
    }

    public ItemSelection descriptionContains(String... value) {
        addContains(ItemColumns.DESCRIPTION, value);
        return this;
    }

    public ItemSelection descriptionStartsWith(String... value) {
        addStartsWith(ItemColumns.DESCRIPTION, value);
        return this;
    }

    public ItemSelection descriptionEndsWith(String... value) {
        addEndsWith(ItemColumns.DESCRIPTION, value);
        return this;
    }

    public ItemSelection orderByDescription(boolean desc) {
        orderBy(ItemColumns.DESCRIPTION, desc);
        return this;
    }

    public ItemSelection orderByDescription() {
        orderBy(ItemColumns.DESCRIPTION, false);
        return this;
    }

    public ItemSelection list(List... value) {
        addEquals(ItemColumns.LIST, value);
        return this;
    }

    public ItemSelection listNot(List... value) {
        addNotEquals(ItemColumns.LIST, value);
        return this;
    }


    public ItemSelection orderByList(boolean desc) {
        orderBy(ItemColumns.LIST, desc);
        return this;
    }

    public ItemSelection orderByList() {
        orderBy(ItemColumns.LIST, false);
        return this;
    }

    public ItemSelection customOrder(Integer... value) {
        addEquals(ItemColumns.CUSTOM_ORDER, value);
        return this;
    }

    public ItemSelection customOrderNot(Integer... value) {
        addNotEquals(ItemColumns.CUSTOM_ORDER, value);
        return this;
    }

    public ItemSelection customOrderGt(int value) {
        addGreaterThan(ItemColumns.CUSTOM_ORDER, value);
        return this;
    }

    public ItemSelection customOrderGtEq(int value) {
        addGreaterThanOrEquals(ItemColumns.CUSTOM_ORDER, value);
        return this;
    }

    public ItemSelection customOrderLt(int value) {
        addLessThan(ItemColumns.CUSTOM_ORDER, value);
        return this;
    }

    public ItemSelection customOrderLtEq(int value) {
        addLessThanOrEquals(ItemColumns.CUSTOM_ORDER, value);
        return this;
    }

    public ItemSelection orderByCustomOrder(boolean desc) {
        orderBy(ItemColumns.CUSTOM_ORDER, desc);
        return this;
    }

    public ItemSelection orderByCustomOrder() {
        orderBy(ItemColumns.CUSTOM_ORDER, false);
        return this;
    }

    public ItemSelection timeCreated(Long... value) {
        addEquals(ItemColumns.TIME_CREATED, value);
        return this;
    }

    public ItemSelection timeCreatedNot(Long... value) {
        addNotEquals(ItemColumns.TIME_CREATED, value);
        return this;
    }

    public ItemSelection timeCreatedGt(long value) {
        addGreaterThan(ItemColumns.TIME_CREATED, value);
        return this;
    }

    public ItemSelection timeCreatedGtEq(long value) {
        addGreaterThanOrEquals(ItemColumns.TIME_CREATED, value);
        return this;
    }

    public ItemSelection timeCreatedLt(long value) {
        addLessThan(ItemColumns.TIME_CREATED, value);
        return this;
    }

    public ItemSelection timeCreatedLtEq(long value) {
        addLessThanOrEquals(ItemColumns.TIME_CREATED, value);
        return this;
    }

    public ItemSelection orderByTimeCreated(boolean desc) {
        orderBy(ItemColumns.TIME_CREATED, desc);
        return this;
    }

    public ItemSelection orderByTimeCreated() {
        orderBy(ItemColumns.TIME_CREATED, false);
        return this;
    }

    public ItemSelection timeModified(Long... value) {
        addEquals(ItemColumns.TIME_MODIFIED, value);
        return this;
    }

    public ItemSelection timeModifiedNot(Long... value) {
        addNotEquals(ItemColumns.TIME_MODIFIED, value);
        return this;
    }

    public ItemSelection timeModifiedGt(long value) {
        addGreaterThan(ItemColumns.TIME_MODIFIED, value);
        return this;
    }

    public ItemSelection timeModifiedGtEq(long value) {
        addGreaterThanOrEquals(ItemColumns.TIME_MODIFIED, value);
        return this;
    }

    public ItemSelection timeModifiedLt(long value) {
        addLessThan(ItemColumns.TIME_MODIFIED, value);
        return this;
    }

    public ItemSelection timeModifiedLtEq(long value) {
        addLessThanOrEquals(ItemColumns.TIME_MODIFIED, value);
        return this;
    }

    public ItemSelection orderByTimeModified(boolean desc) {
        orderBy(ItemColumns.TIME_MODIFIED, desc);
        return this;
    }

    public ItemSelection orderByTimeModified() {
        orderBy(ItemColumns.TIME_MODIFIED, false);
        return this;
    }

    public ItemSelection timeBought(Long... value) {
        addEquals(ItemColumns.TIME_BOUGHT, value);
        return this;
    }

    public ItemSelection timeBoughtNot(Long... value) {
        addNotEquals(ItemColumns.TIME_BOUGHT, value);
        return this;
    }

    public ItemSelection timeBoughtGt(long value) {
        addGreaterThan(ItemColumns.TIME_BOUGHT, value);
        return this;
    }

    public ItemSelection timeBoughtGtEq(long value) {
        addGreaterThanOrEquals(ItemColumns.TIME_BOUGHT, value);
        return this;
    }

    public ItemSelection timeBoughtLt(long value) {
        addLessThan(ItemColumns.TIME_BOUGHT, value);
        return this;
    }

    public ItemSelection timeBoughtLtEq(long value) {
        addLessThanOrEquals(ItemColumns.TIME_BOUGHT, value);
        return this;
    }

    public ItemSelection orderByTimeBought(boolean desc) {
        orderBy(ItemColumns.TIME_BOUGHT, desc);
        return this;
    }

    public ItemSelection orderByTimeBought() {
        orderBy(ItemColumns.TIME_BOUGHT, false);
        return this;
    }
}
