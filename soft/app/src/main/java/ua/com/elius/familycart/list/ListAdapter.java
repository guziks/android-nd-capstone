package ua.com.elius.familycart.list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.annotation.IntDef;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import ua.com.elius.familycart.R;
import ua.com.elius.familycart.data.item.ItemColumns;
import ua.com.elius.familycart.data.item.ItemCursor;
import ua.com.elius.familycart.data.item.ItemSelection;
import ua.com.elius.familycart.data.item.List;
import ua.com.elius.familycart.data.person.PersonCursor;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder>
        implements ListItemTouchHelperAdapter {

    private static final String TAG = "ListAdapter";

    private final OnStartDragListener mDragStartListener;
    private Context mContext;
    private ItemCursor mCursor;
    private List mListType;
    private int mLastListChangePosition;

    public ListAdapter(Context context, List listType, OnStartDragListener dragStartListener) {
        mContext = context;
        mListType = listType;
        mDragStartListener = dragStartListener;
        setHasStableIds(true);
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_list_item, parent, false);

        return new ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, int position) {
        if (mCursor == null || mCursor.isClosed()) {
            return;
        }
        mCursor.moveToPosition(position);

        holder.title.setText(mCursor.getTitle());
        holder.quantity.setText(mCursor.getQuantity());
        holder.description.setText(mCursor.getDescription());
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
        mLastListChangePosition = position;
        mCursor.moveToPosition(position);

        long timestamp = System.currentTimeMillis();

        ContentValues values = new ContentValues();
        DatabaseUtils.cursorRowToContentValues(mCursor, values);
        values.put(ItemColumns.LIST, targetList.ordinal());
        values.put(ItemColumns.TIME_MODIFIED, timestamp);
        if (targetList == List.BOUGHT) {
            values.put(ItemColumns.TIME_BOUGHT, timestamp);
        }
        ItemSelection where = new ItemSelection().id(mCursor.getId());

        new ChangeListAsyncTask(mContext).execute(new Pair<>(values, where));
//        mContext.getContentResolver().update(ItemColumns.CONTENT_URI, values, where.sel(), where.args());
    }

//    @Override
//    public void onItemDismiss(int position) {
//        Log.i(TAG, "onItemDismiss");
//        mDataset.remove(position);
//        notifyItemRemoved(position);
//    }

    @Override
    public int getItemCount() {
        if ( null == mCursor ) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(ItemCursor newCursor) {
        ItemCursor oldCursor = mCursor;
        mCursor = newCursor;

        if (oldCursor != null && newCursor != null) {
            if (newCursor.getCount() < oldCursor.getCount()) {
                notifyItemRemoved(mLastListChangePosition);
            } else if (newCursor.getCount() > oldCursor.getCount()) {
                notifyItemInserted(oldCursor.getCount());
            }
//            notifyItemRemoved(mLastListChangePosition);
//            notifyItemRangeChanged(0, oldCursor.getCount());
        } else {
            notifyDataSetChanged();
        }

        if (oldCursor != null) {
            oldCursor.close();
        }
    }

    public ItemCursor getCursor() {
        return mCursor;
    }

    public List getListType() {
        return mListType;
    }

    @Override
    public long getItemId(int position) {
        long id;
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.moveToPosition(position);
            id = mCursor.getId();
        } else {
            id = RecyclerView.NO_ID;
        }
        return id;
    }
}
