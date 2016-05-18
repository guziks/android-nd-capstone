package ua.com.elius.familycart.data.item;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ua.com.elius.familycart.data.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code item} table.
 */
public class ItemContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return ItemColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable ItemSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable ItemSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Item title. For instance, Milk.
     */
    public ItemContentValues putTitle(@Nullable String value) {
        mContentValues.put(ItemColumns.TITLE, value);
        return this;
    }

    public ItemContentValues putTitleNull() {
        mContentValues.putNull(ItemColumns.TITLE);
        return this;
    }

    /**
     * Item quantity. For instance, 1 bottle
     */
    public ItemContentValues putQuantity(@Nullable String value) {
        mContentValues.put(ItemColumns.QUANTITY, value);
        return this;
    }

    public ItemContentValues putQuantityNull() {
        mContentValues.putNull(ItemColumns.QUANTITY);
        return this;
    }

    /**
     * Flavor description. For instance, 2.5% fat
     */
    public ItemContentValues putDescription(@Nullable String value) {
        mContentValues.put(ItemColumns.DESCRIPTION, value);
        return this;
    }

    public ItemContentValues putDescriptionNull() {
        mContentValues.putNull(ItemColumns.DESCRIPTION);
        return this;
    }

    /**
     * List this item belongs to.
     */
    public ItemContentValues putList(@Nullable List value) {
        mContentValues.put(ItemColumns.LIST, value == null ? null : value.ordinal());
        return this;
    }

    public ItemContentValues putListNull() {
        mContentValues.putNull(ItemColumns.LIST);
        return this;
    }

    /**
     * Number to sort by (user customized order).
     */
    public ItemContentValues putCustomOrder(@Nullable Integer value) {
        mContentValues.put(ItemColumns.CUSTOM_ORDER, value);
        return this;
    }

    public ItemContentValues putCustomOrderNull() {
        mContentValues.putNull(ItemColumns.CUSTOM_ORDER);
        return this;
    }

    /**
     * Creation timestamp.
     */
    public ItemContentValues putTimeCreated(@Nullable Long value) {
        mContentValues.put(ItemColumns.TIME_CREATED, value);
        return this;
    }

    public ItemContentValues putTimeCreatedNull() {
        mContentValues.putNull(ItemColumns.TIME_CREATED);
        return this;
    }

    /**
     * Last modification timestamp.
     */
    public ItemContentValues putTimeModified(@Nullable Long value) {
        mContentValues.put(ItemColumns.TIME_MODIFIED, value);
        return this;
    }

    public ItemContentValues putTimeModifiedNull() {
        mContentValues.putNull(ItemColumns.TIME_MODIFIED);
        return this;
    }

    /**
     * When item has been bought timestamp.
     */
    public ItemContentValues putTimeBought(@Nullable Long value) {
        mContentValues.put(ItemColumns.TIME_BOUGHT, value);
        return this;
    }

    public ItemContentValues putTimeBoughtNull() {
        mContentValues.putNull(ItemColumns.TIME_BOUGHT);
        return this;
    }
}
