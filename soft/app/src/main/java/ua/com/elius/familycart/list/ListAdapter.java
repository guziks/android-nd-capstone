package ua.com.elius.familycart.list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.v4.view.MotionEventCompat;
import android.util.Pair;
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
        ItemCursor itemCursor = new ItemCursor(cursor);
        holder.title.setText(itemCursor.getTitle());
        holder.quantity.setText(itemCursor.getQuantity());
        holder.description.setText(itemCursor.getDescription());
        holder.handle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
//        Log.i(TAG, "onItemMove");
//        if (fromPosition < toPosition) {
//            for (int i = fromPosition; i < toPosition; i++) {
//                Collections.swap(mDataset, i, i + 1);
//                Log.i(TAG, "swap");
//            }
//        } else {
//            for (int i = fromPosition; i > toPosition; i--) {
//                Collections.swap(mDataset, i, i - 1);
//                Log.i(TAG, "swap");
//            }
//        }
//        notifyItemMoved(fromPosition, toPosition);
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
}
