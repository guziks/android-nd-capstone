package ua.com.elius.familycart;

import android.content.Context;

import ua.com.elius.familycart.data.item.ItemCursor;
import ua.com.elius.familycart.data.item.ItemSelection;
import ua.com.elius.familycart.data.item.List;

public class Util {

    public static int getLeftToBuyItemsCount(Context context) {
        ItemSelection where = new ItemSelection();
        where.list(List.TO_BUY);
        ItemCursor cursor = where.query(context.getContentResolver());

        return cursor.getCount();
    }
}
