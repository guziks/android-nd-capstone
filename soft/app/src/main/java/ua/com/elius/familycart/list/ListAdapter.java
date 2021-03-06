package ua.com.elius.familycart.list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import ua.com.elius.familycart.R;
import ua.com.elius.familycart.data.item.ItemColumns;
import ua.com.elius.familycart.data.item.ItemCursor;
import ua.com.elius.familycart.data.item.ItemSelection;
import ua.com.elius.familycart.data.item.List;

public class ListAdapter extends RecyclerViewCursorAdapter<ListViewHolder>
        implements ListItemTouchHelperAdapter {

    private static final String TAG = "ListAdapter";

    private final OnStartDragListener mDragStartListener;
    private Context mContext;
    private List mListType;
    private boolean mEditable = true;
    private boolean mSortable = true;

    public ListAdapter(Context context, List listType, OnStartDragListener dragStartListener) {
        super();
        mContext = context;
        mListType = listType;
        mDragStartListener = dragStartListener;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_list_item, parent, false);

        return new ListViewHolder(v);
    }

    @Override
    public void onBindViewHolderCursor(final ListViewHolder holder, Cursor cursor) {
        if (!mEditable) {
            holder.removeOnClickListener();
        }
        ItemCursor itemCursor = new ItemCursor(cursor);
        holder.title.setText(itemCursor.getTitle());
        holder.quantity.setText(itemCursor.getQuantity());
        holder.description.setText(itemCursor.getDescription());
        if (mSortable) {
            holder.handle.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                        mDragStartListener.onStartDrag(holder);
                    }
                    return false;
                }
            });
        } else {
            holder.handle.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Log.i(TAG, "onItemMove");
        if (Math.abs(fromPosition - toPosition) > 1) {
            return;
        }
        swapItems(fromPosition, toPosition);
    }

    private void swapItems(int fromPosition, int toPosition) {
        long timestamp = System.currentTimeMillis();

        ItemCursor itemCursor = new ItemCursor(getCursor());

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

    @Override
    public void onChangeList(List targetList, int position) {
        ItemCursor itemCursor = new ItemCursor(getCursor());
        itemCursor.moveToPosition(position);

        long timestamp = System.currentTimeMillis();

        ContentValues values = new ContentValues();
        DatabaseUtils.cursorRowToContentValues(itemCursor, values);
        values.put(ItemColumns.LIST, targetList.ordinal());
        values.put(ItemColumns.TIME_MODIFIED, timestamp);
        if (targetList == List.BOUGHT) {
            values.put(ItemColumns.TIME_BOUGHT, timestamp);
        }
        ItemSelection where = new ItemSelection().id(itemCursor.getId());

        new ChangeListAsyncTask(mContext, values, where, targetList).execute();
    }

    public List getListType() {
        return mListType;
    }

    public void setEditable(boolean editable) {
        mEditable = editable;
    }

    public void setSortable(boolean sortable) {
        mSortable = sortable;
    }
}
