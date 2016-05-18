package ua.com.elius.familycart.data.item;

import android.net.Uri;
import android.provider.BaseColumns;

import ua.com.elius.familycart.data.ListProvider;
import ua.com.elius.familycart.data.item.ItemColumns;
import ua.com.elius.familycart.data.person.PersonColumns;

/**
 * Shopping list item.
 */
public class ItemColumns implements BaseColumns {
    public static final String TABLE_NAME = "item";
    public static final Uri CONTENT_URI = Uri.parse(ListProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Item title. For instance, Milk.
     */
    public static final String TITLE = "title";

    /**
     * Item quantity. For instance, 1 bottle
     */
    public static final String QUANTITY = "quantity";

    /**
     * Flavor description. For instance, 2.5% fat
     */
    public static final String DESCRIPTION = "description";

    /**
     * List this item belongs to.
     */
    public static final String LIST = "list";

    /**
     * Number to sort by (user customized order).
     */
    public static final String CUSTOM_ORDER = "custom_order";

    /**
     * Creation timestamp.
     */
    public static final String TIME_CREATED = "time_created";

    /**
     * Last modification timestamp.
     */
    public static final String TIME_MODIFIED = "time_modified";

    /**
     * When item has been bought timestamp.
     */
    public static final String TIME_BOUGHT = "time_bought";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            TITLE,
            QUANTITY,
            DESCRIPTION,
            LIST,
            CUSTOM_ORDER,
            TIME_CREATED,
            TIME_MODIFIED,
            TIME_BOUGHT
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(TITLE) || c.contains("." + TITLE)) return true;
            if (c.equals(QUANTITY) || c.contains("." + QUANTITY)) return true;
            if (c.equals(DESCRIPTION) || c.contains("." + DESCRIPTION)) return true;
            if (c.equals(LIST) || c.contains("." + LIST)) return true;
            if (c.equals(CUSTOM_ORDER) || c.contains("." + CUSTOM_ORDER)) return true;
            if (c.equals(TIME_CREATED) || c.contains("." + TIME_CREATED)) return true;
            if (c.equals(TIME_MODIFIED) || c.contains("." + TIME_MODIFIED)) return true;
            if (c.equals(TIME_BOUGHT) || c.contains("." + TIME_BOUGHT)) return true;
        }
        return false;
    }

}
