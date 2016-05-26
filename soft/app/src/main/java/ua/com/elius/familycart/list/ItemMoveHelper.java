package ua.com.elius.familycart.list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;

import ua.com.elius.familycart.data.item.ItemColumns;
import ua.com.elius.familycart.data.item.ItemCursor;
import ua.com.elius.familycart.data.item.ItemSelection;

public class ItemMoveHelper {

    private Context mContext;
    private Cursor mCursor;
    private int mPrevFromPosition;
    private int mPrevToPosition;
    private int mFromPosition;
    private int mToPosition;

    public ItemMoveHelper(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public void setNextPositions(int fromPosition, int toPosition) {
        mPrevFromPosition = mFromPosition;
        mPrevToPosition = mToPosition;
        mFromPosition = fromPosition;
        mToPosition = toPosition;
    }

    public void move() {
        if (mPrevFromPosition == mFromPosition && mPrevToPosition == mToPosition) {
            return;
        }
//        if (mFromPosition < mToPosition) {
//            for (int i = mFromPosition; i < mToPosition; i++) {
//                swapItems(i, i + 1);
//            }
//        } else {
//            for (int i = mFromPosition; i > mToPosition; i--) {
//                swapItems(i, i - 1);
//            }
//        }
        swapItems(mFromPosition, mToPosition);
    }

    private void swapItems(int fromPosition, int toPosition) {
        long timestamp = System.currentTimeMillis();

        ItemCursor itemCursor = new ItemCursor(mCursor);

        itemCursor.moveToPosition(fromPosition);
        ContentValues fromValues = new ContentValues();
        DatabaseUtils.cursorRowToContentValues(itemCursor, fromValues);
        Integer fromOrder = fromValues.getAsInteger(ItemColumns.CUSTOM_ORDER);
        ItemSelection fromWhere = new ItemSelection().id(itemCursor.getId());

        itemCursor.moveToPosition(toPosition);
        ContentValues toValues = new ContentValues();
        DatabaseUtils.cursorRowToContentValues(itemCursor, toValues);
        Integer toOrder = toValues.getAsInteger(ItemColumns.CUSTOM_ORDER);
        ItemSelection toWhere = new ItemSelection().id(itemCursor.getId());

        // update modified timestamp. TODO decide is it required
        fromValues.put(ItemColumns.TIME_MODIFIED, timestamp);
        toValues.put(ItemColumns.TIME_MODIFIED, timestamp);

        // swap orders
        fromValues.put(ItemColumns.CUSTOM_ORDER, toOrder);
        toValues.put(ItemColumns.CUSTOM_ORDER, fromOrder);

        mContext.getContentResolver().update(ItemColumns.CONTENT_URI,
                fromValues, fromWhere.sel(), fromWhere.args());
        mContext.getContentResolver().update(ItemColumns.CONTENT_URI,
                toValues, toWhere.sel(), toWhere.args());
    }
}
