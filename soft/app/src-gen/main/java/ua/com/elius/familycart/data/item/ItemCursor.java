package ua.com.elius.familycart.data.item;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ua.com.elius.familycart.data.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code item} table.
 */
public class ItemCursor extends AbstractCursor implements ItemModel {
    public ItemCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(ItemColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Item title. For instance, Milk.
     * Can be {@code null}.
     */
    @Nullable
    public String getTitle() {
        String res = getStringOrNull(ItemColumns.TITLE);
        return res;
    }

    /**
     * Item quantity. For instance, 1 bottle
     * Can be {@code null}.
     */
    @Nullable
    public String getQuantity() {
        String res = getStringOrNull(ItemColumns.QUANTITY);
        return res;
    }

    /**
     * Flavor description. For instance, 2.5% fat
     * Can be {@code null}.
     */
    @Nullable
    public String getDescription() {
        String res = getStringOrNull(ItemColumns.DESCRIPTION);
        return res;
    }

    /**
     * List this item belongs to.
     * Can be {@code null}.
     */
    @Nullable
    public List getList() {
        Integer intValue = getIntegerOrNull(ItemColumns.LIST);
        if (intValue == null) return null;
        return List.values()[intValue];
    }

    /**
     * Number to sort by (user customized order).
     * Can be {@code null}.
     */
    @Nullable
    public Integer getCustomOrder() {
        Integer res = getIntegerOrNull(ItemColumns.CUSTOM_ORDER);
        return res;
    }

    /**
     * Creation timestamp.
     * Can be {@code null}.
     */
    @Nullable
    public Long getTimeCreated() {
        Long res = getLongOrNull(ItemColumns.TIME_CREATED);
        return res;
    }

    /**
     * Last modification timestamp.
     * Can be {@code null}.
     */
    @Nullable
    public Long getTimeModified() {
        Long res = getLongOrNull(ItemColumns.TIME_MODIFIED);
        return res;
    }

    /**
     * When item has been bought timestamp.
     * Can be {@code null}.
     */
    @Nullable
    public Long getTimeBought() {
        Long res = getLongOrNull(ItemColumns.TIME_BOUGHT);
        return res;
    }
}
