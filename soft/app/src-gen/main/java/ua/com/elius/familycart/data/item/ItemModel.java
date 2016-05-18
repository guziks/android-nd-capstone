package ua.com.elius.familycart.data.item;

import ua.com.elius.familycart.data.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Shopping list item.
 */
public interface ItemModel extends BaseModel {

    /**
     * Item title. For instance, Milk.
     * Can be {@code null}.
     */
    @Nullable
    String getTitle();

    /**
     * Item quantity. For instance, 1 bottle
     * Can be {@code null}.
     */
    @Nullable
    String getQuantity();

    /**
     * Flavor description. For instance, 2.5% fat
     * Can be {@code null}.
     */
    @Nullable
    String getDescription();

    /**
     * List this item belongs to.
     * Can be {@code null}.
     */
    @Nullable
    List getList();

    /**
     * Number to sort by (user customized order).
     * Can be {@code null}.
     */
    @Nullable
    Integer getCustomOrder();

    /**
     * Creation timestamp.
     * Can be {@code null}.
     */
    @Nullable
    Long getTimeCreated();

    /**
     * Last modification timestamp.
     * Can be {@code null}.
     */
    @Nullable
    Long getTimeModified();

    /**
     * When item has been bought timestamp.
     * Can be {@code null}.
     */
    @Nullable
    Long getTimeBought();
}
